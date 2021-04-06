import scala.collection.mutable.Buffer
import scala.util.Try
import java.io.FileReader
import scala.util.Failure
import java.io.FileNotFoundException
import scala.util.Success
import java.io.BufferedReader
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.parser.decode
import javafx.util.Pair
import scalafx.scene.image.{Image, ImageView}
import java.io._
import scala.io.Source
object FileManager {

  val jsonString = """
   [
   {
      "map": [
      [0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0],
      [0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0],
      [0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0],
      [0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0],
      [0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0]
      ]
      }
   ]
  """
  case class gridMap(map: Array[Array[Int]])
  case class playerAndTowerLoc(gold: Int, currentHealth: Int, maxHealth: Int, towerLocs: List[Pos])
  def readJson(text: String ) = decode[List[gridMap]](text).toTry
  def readWaveManager(text: String) = decode[WaveManager](text).toTry
  def readPlayer(text: String) = decode[playerAndTowerLoc](text).toTry
  def readText(source: scala.io.Source ) = source.getLines().mkString("\n")
  val fileSource   = util.Try(scala.io.Source.fromFile("foo.bar"))
  val stringSource = util.Try(scala.io.Source.fromString(jsonString))
  def loadMap(grid: Grid) = {
    val maps = {
    val temp = Buffer[gridMap]()
    for {
      source <- stringSource     // Get successful source result
      text   =  readText(source) //Get text from source
      maps   <- readJson(text)   //Parse JSON into list
      map <- maps
    } temp += map

    temp
  }
    grid.loadMap(maps.head.map)
 }
  def createImageView(imageLink: String): ImageView = {
     val imageView = new ImageView(new Image(imageLink))
    imageView
  }
  def saveWaveManager(waveManager: WaveManager): String = {
     s"""
      {
      "totalWave": ${waveManager.totalWave},
      "minEnemiesPerWave": ${waveManager.minEnemiesPerWave},
      "startWave": ${waveManager.getWaveNumber - 1}

    """
  }
  def savePlayer(player: Player): String = {
      var ans =  s"""
   {
      "gold": ${player.gold.currentGold},
      "currentHealth" : ${player.health.currentHealth},
      "maxHealth": ${player.health.maxHealth},
      "towerLocs": [
  """
   for(tower <- player.getTower) {
    ans  += s"""
        {
          "x": ${tower.pos.x},
          "y": ${tower.pos.y}
        },
       """
   }
   if(player.getTower.nonEmpty) ans = ans.dropRight(10)
   ans = ans + "]" + "\n" + "}"
   ans  + "\n" + "ENDOFFILE"
  }
  def saveGame(filePath: String,game: Game) = {
     val fileContent = Buffer[String]()
     fileContent += this.saveWaveManager(game.waveManager)
     game.player.gold = game.player.gold - game.waveManager.getWave.getGoldEarned
     game.player.health.currentHealth += game.waveManager.getWave.getDamageDealt
     fileContent += this.savePlayer(game.player)
       try {
                val fileWriter = new FileWriter(filePath)
                val buffWriter = new BufferedWriter(fileWriter)

                try {
                    buffWriter.write(fileContent.mkString("} END"))
                } finally {
                    buffWriter.close()
                }
            } catch {
                case _: FileNotFoundException => println("Error with saving game data: Save file not found")
                case _: IOException => println("Error with saving game data: IOException")
                case _: Throwable => println("Error with saving game data: Unexpected exception.")
            }
   }
   def loadGame(filePath: String, game: Game) = {
    val source = Source.fromFile(filePath)
    val lines = source.getLines()
    var currentLine = ""
    var gameData = ""
    while(currentLine != "ENDOFFILE" && lines.hasNext) {
         currentLine = lines.next().trim()
         gameData += "\n" + currentLine
    }
    source.close()
    gameData = gameData.dropRight(10)
    val fileSplitIndex = gameData.indexOf("END")
    val waveManagerData = this.readWaveManager(gameData.substring(0, fileSplitIndex)).get
    val playerData = this.readPlayer(gameData.substring(fileSplitIndex + 3, gameData.length)).get
    game.waveManager = waveManagerData
    game.waveManager.setGrid(game.grid)
    game.waveManager.setGroup(game.gameGroup)
    val player = new Player(Gold(playerData.gold), Health(playerData.currentHealth, playerData.maxHealth))
    player.setGrid(game.grid)
    player.setGroup(game.gameGroup)
    player.setWaveManager(game.waveManager)
    game.waveManager.setPlayer(player)
    game.player = player
    game.waveManager.spawnWave()
    for(towerLoc <- playerData.towerLocs) {
      player.addTowerAt(towerLoc)
    }
   }
}
