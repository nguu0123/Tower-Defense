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
val scene = new Scene(root) //Scene acts as a container for the scene graph
val canvas = new Canvas(1200, 720) //Give width and height as a parameter.
val VBox = new VBox
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
val player = new Player(Gold(10000), grid,Health(20), gameGroup, waveManager)
waveManager.setPlayer(player)
player.buildTower(Pos(60, 240))
player.buildTower(Pos(180, 240))
val font = Font.loadFont("file:src/res/font.ttf", 40)
VBox.setSpacing(30)
val waveSystem = new WaveSystem(waveManager, font, VBox)
val goldSystem = new GoldSystem(player, VBox, font)
val playerHealth = new PlayerHealth(player, VBox)
val hBox = new HBox {
   padding = Insets(0, 20, 0, 20)
   spacing = 30
}
 hBox.getChildren.addAll(shop, shop1, shop2)
VBox.getChildren.add(hBox)
root.setCenter(gameGroup)
root.setRight(VBox)
var nextSecond = System.currentTimeMillis() + 1000
var frameLastSecond = 0
var frameCurrentSecond = 0
val inputManager = new InputManager(player)
this.waveManager.spawnWave()
  def update() = {
      if(!player.health.isDead){
         goldSystem.update()
         waveSystem.update()
         playerHealth.update()
         waveManager.update()
         player.update()
         inputManager.handleInput(shop1, gameGroup)
       }
        val currentSecond = System.currentTimeMillis()
       if(currentSecond > nextSecond) {
         nextSecond += 1000
         frameLastSecond = frameCurrentSecond
         frameCurrentSecond = 0
       }
       frameCurrentSecond += 1
  }
}
