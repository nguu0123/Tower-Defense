import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.layout.{Background, BackgroundFill, BorderPane, CornerRadii, HBox, VBox}
import scalafx.geometry.Insets
import scalafx.scene.{Group, Scene}
import scalafx.scene.paint.Color
import scalafx.Includes._
import scalafx.scene.text.Font
class Game {
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
val waveManager = new WaveManager(gameGroup, grid, 3, 5, 0)
val player = new Player(Gold(1000), grid,Health(20), gameGroup, waveManager)
waveManager.setPlayer(player)
val font = Font.loadFont("file:src/res/font.ttf", 40)
val waveSystem = new WaveSystem(waveManager, font, VBox)
val pauseAndContinueButtons = new HBox {
  padding = Insets(0, 40, 0, 40)
  spacing = 70
}
val pauseButton = FileManager.createImageView("file:src/res/pauseButton.png")
val continueButton = FileManager.createImageView("file:src/res/continueButton.png")
pauseAndContinueButtons.getChildren.addAll(continueButton, pauseButton)
VBox.getChildren.add(pauseAndContinueButtons)
val goldSystem = new GoldSystem(player, VBox, font)
val playerHealth = new PlayerHealth(player, VBox)
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
val inputManager = new InputManager(player)
val castle = FileManager.createImageView("file:src/res/Castle.png")
castle.relocate(1020,120)
gameGroup.getChildren.add(castle)
for(i <- 0 to 2) {
  for(j <- 0 to 2) {
    grid.setNotBuildable( Pos(1020,120) + Pos(i * 60, j* 60))
  }
}
this.waveManager.spawnWave()
  def update() = {
      inputManager.handleInput(shop1, gameGroup, pauseButton, continueButton)
      goldSystem.update()
      if(!player.health.isDead && player.stopGame == 0){
         waveSystem.update()
         playerHealth.update()
         waveManager.update()
         player.update()
       }
      else if (player.stopGame == 1) {
        waveManager.updateTime()
        player.updateTime()
      }
        val currentSecond = System.currentTimeMillis()
       if(currentSecond > nextSecond) {
         nextSecond += 1000
         frameLastSecond = frameCurrentSecond
         frameCurrentSecond = 0
       }
       frameCurrentSecond += 1
       println(frameLastSecond)
  }
}
