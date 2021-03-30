import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.layout.{BorderPane, VBox, Background, BackgroundFill, CornerRadii}
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
val g = canvas.graphicsContext2D
FileManager.loadMap(grid)
grid.draw(g)
gameGroup.getChildren.add(canvas)
val player = new Player(Gold(100), grid,Health(20))
val waveManager = new WaveManager(gameGroup, grid, 3, 5, 0, player)
val firstTower = Tower(Pos(60, 240), 10, 200.0, 1000, gameGroup, waveManager)
val secondTower = Tower(Pos(180, 240), 10, 200.0, 1000, gameGroup, waveManager)
player.addTower(firstTower)
player.addTower(secondTower)
val font = Font.loadFont("file:src/res/font.ttf", 40)
 VBox.setSpacing(15)
val waveSystem = new WaveSystem(waveManager, font, VBox)
val playerHealth = new PlayerHealth(player, VBox)
val goldSystem = new GoldSystem(player, VBox, font)
 VBox.getChildren.add(shop)
root.setCenter(gameGroup)
root.setRight(VBox)
var nextSecond = System.currentTimeMillis() + 1000
var frameLastSecond = 0
var frameCurrentSecond = 0
 val shop1 = FileManager.createImageView("file:src/res/Shop1.png")
this.waveManager.spawnWave()
  def update() = {
      if(!player.health.isDead){
         goldSystem.update()
         waveSystem.update()
         playerHealth.update()
         waveManager.update()
         player.update()
         InputManager.handleInput(shop, gameGroup)
       }
  }
}
