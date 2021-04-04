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
  val playerData = """
   {
      "gold": 1000,
      "health": 20,
      "towerLocs": [
         {
            "x": 720.0,
            "y": 60.0
         },
         {
           "x": 120.0,
           "y":60.0
         }
      ]
  }
  """
  val waveManagerData = """
  {
      "totalWave": 3,
      "minEnemiesPerWave": 5,
      "startWave": 0
  }
  """
  case class gridMap(map: Array[Array[Int]])
  case class playerAndTowerLoc(gold: Int, health: Int, towerLocs: List[Pos])
  def readJson(text: String ) = decode[List[gridMap]](text).toTry
  def readWaveManager(text: String) = decode[WaveManager](text).toTry
  def readPlayer(text: String) = decode[playerAndTowerLoc](text).toTry
  def readText(source: scala.io.Source ) = source.getLines().mkString("\n")
  val fileSource   = util.Try(scala.io.Source.fromFile("foo.bar"))
  val stringSource = util.Try(scala.io.Source.fromString(jsonString))
  val waveManagerSource = util.Try(scala.io.Source.fromString(waveManagerData))
  val playerSource = util.Try(scala.io.Source.fromString(playerData))
 /* for(line <- scala.io.Source.fromFile("src/data/WaveManagerData.json").getLines()) {
    println(line)
  }*/

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
    grid.loadMap(maps(0).map)
 }
  def loadWaveManager(): WaveManager = {
   val waveManager =  this.readWaveManager(this.readText(this.waveManagerSource.get))
   waveManager.get
  }
  def saveWaveManager(waveManager: WaveManager): String = {
     s"""
      {
      "totalWave": ${waveManager.totalWave},
      "minEnemiesPerWave": ${waveManager.minEnemiesPerWave},
      "startWave": ${waveManager.getWaveNumber}

    """
  }
  def savePlayer(player: Player): String = {
      var ans =  s"""
   {
      "gold": ${player.gold.currentGold},
      "health": ${player.health.currentHealth},
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
   ans = ans.dropRight(10)
   ans + "]" + "\n" + "}"
  }
  def loadPlayer(): Player = {
    val playerData = this.readPlayer(this.readText(this.playerSource.get)).get
    new Player(Gold(playerData.gold), Health(playerData.health))
  }
  def createImageView(imageLink: String): ImageView = {
     val imageView = new ImageView(new Image(imageLink))
    imageView
  }
  def saveGame(filePath: String,game: Game) = {
     val fileContent = Buffer[String]()
     fileContent += this.saveWaveManager(game.waveManager)
     fileContent += this.savePlayer(game.player)
       try {
                val fileWriter = new FileWriter(filePath)
                val buffWriter = new BufferedWriter(fileWriter)

                try {
                    buffWriter.write(fileContent.mkString("}"))
                } finally {
                    buffWriter.close()
                }
            } catch {
                // Exceptions are handled by printing.
                // This is ok during development but not optimal. This will be improved in a future code example.
                case _: FileNotFoundException => println("Error with saving game data: Save file not found")
                case _: IOException => println("Error with saving game data: IOException")
                case _: Throwable => println("Error with saving game data: Unexpected exception.")
            }

        }

}
