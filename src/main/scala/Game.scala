import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.layout.{Background, BackgroundFill, BorderPane, CornerRadii, HBox, VBox}
import scalafx.geometry.Insets
import scalafx.scene.{Group, Scene}
import scalafx.scene.paint.Color
import scalafx.Includes._
import scalafx.scene.text.Font
class Game {
var backToMenu = false
val grid = new Grid(1200, 720)
val gameGroup = new Group() //Simple pane component
val root = new BorderPane()
val canvas = new Canvas(1200, 720) //Give width and height as a parameter.
val VBox = new VBox {
  spacing = 30
  padding = Insets(40, 0, 40,0)
}
VBox.setPrefWidth(300)
VBox.setPrefHeight(720)
VBox.setBackground(new Background(Array(new BackgroundFill(Color.Black, CornerRadii.Empty, Insets.Empty) ) ) )
val shop = FileManager.createImageView("file:src/res/Shop1.png")
val shop1 = FileManager.createImageView("file:src/res/Shop2.png")
val shop2 = FileManager.createImageView("file:src/res/Shop3.png")
val g = canvas.graphicsContext2D
FileManager.loadMap(grid)
grid.draw(g)
gameGroup.getChildren.add(canvas)
var waveManager = new WaveManager(3, 5, 0)
this.waveManager.setGrid(grid)
this.waveManager.setGroup(gameGroup)
var player = new Player(Gold(1000), Health(20))
this.player.setGrid(grid)
this.player.setGroup(gameGroup)
this.player.setWaveManager(this.waveManager)
this.waveManager.setPlayer(this.player)
val font = Font.loadFont("file:src/res/font.ttf", 40)
val waveSystem = new WaveSystem(this.waveManager, font, VBox)
val pauseAndContinueButtons = new HBox {
  spacing = 20
}
val pauseButton = FileManager.createImageView("file:src/res/pauseButton.png")
val continueButton = FileManager.createImageView("file:src/res/continueButton.png")
val saveButton = FileManager.createImageView("file:src/res/saveButton.png")
 pauseButton.onMouseClicked = event => {
        this.player.stopGame = 1
    }
continueButton.onMouseClicked = event => {
      this.player.stopGame = 0
    }
saveButton.onMouseClicked = event => {
   FileManager.saveGame("src/data/saveGame.json", this)
   this.backToMenu = true
}
pauseAndContinueButtons.getChildren.addAll(saveButton, continueButton, pauseButton)
VBox.getChildren.add(pauseAndContinueButtons)
val goldSystem = new GoldSystem(this.player, VBox, font)
val playerHealth = new PlayerHealth(this.player, VBox)
val towerImages = new HBox {
   padding = Insets(0, 20, 0, 20)
   spacing = 30
}
towerImages.getChildren.addAll(shop, shop1, shop2)
VBox.getChildren.add(towerImages)
root.setCenter(gameGroup)
root.setRight(VBox)
var nextSecond = System.currentTimeMillis() + 1000
var frameLastSecond = 0
var frameCurrentSecond = 0
val inputManager = new InputManager(this.player)
val castle = FileManager.createImageView("file:src/res/Castle.png")
castle.relocate(1020,120)
gameGroup.getChildren.add(castle)
for(i <- 0 to 2) {
  for(j <- 0 to 2) {
    grid.setNotBuildable( Pos(1020,120) + Pos(i * 60, j* 60))
  }
}
this.waveManager.spawnWave()
  def restart() = {
    this.player.restart()
    this.waveManager.restart()
  }
  def update() = {
      inputManager.handleInput(shop1, gameGroup)
      goldSystem.update()
      if(!this.player.health.isDead && this.player.stopGame == 0){
         waveSystem.update()
         playerHealth.update()
         this.waveManager.update()
         this.player.update()
       }
      else if (this.player.stopGame == 1) {
        this.waveManager.updateTime()
        this.player.updateTime()
      }
       val currentSecond = System.currentTimeMillis()
       if(currentSecond > nextSecond) {
         nextSecond += 1000
         frameLastSecond = frameCurrentSecond
         frameCurrentSecond = 0
       }
       frameCurrentSecond += 1
  }
  def newGame() = {
    waveSystem.setWaveManager(this.waveManager)
    goldSystem.setPlayer(this.player)
    playerHealth.setPlayer(this.player)
    inputManager.setPlayer(this.player)
  }
}
