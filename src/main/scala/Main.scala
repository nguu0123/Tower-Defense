import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.layout.{BorderPane, VBox}
import scalafx.application.JFXApp
import scalafx.scene.{Group, Scene}
import scalafx.scene.paint.Color
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import scalafx.Includes._
import scalafx.scene.text.Font

import scalafx.scene.control.Label
object test extends JFXApp {
stage = new JFXApp.PrimaryStage {
    title.value = "Test"
    resizable = false
}
val grid = new Grid(1620, 1080)
val gameGroup = new Group() //Simple pane component
val root = new BorderPane()
val scene = new Scene(root) //Scene acts as a container for the scene graph
val canvas = new Canvas(1620, 1080) //Give width and height as a parameter.
val VBox = new VBox
VBox.setPrefWidth(300)
VBox.setPrefHeight(1080)
val shop = FileManager.createImageView("file:src/res/Shop1.png")
shop.onMouseDragged  = (mouse: MouseEvent) => {
  shop.onMouseReleased = (release: MouseEvent) => {
    player.buildTower(Tower(Pos(mouse.sceneX, mouse.sceneY), 10, 200.0, 500, gameGroup, waveManager))
  }
}
VBox.getChildren.add(shop)
val g = canvas.graphicsContext2D
FileManager.loadMap(grid)
grid.draw(g)
gameGroup.getChildren.add(canvas)
val player = new Player(Gold(100), grid,Health(20))
val waveManager = new WaveManager(gameGroup, grid, 3, 5, 0, player)
val firstTower = Tower(Pos(120.0, 60.0), 10, 200.0, 1000, gameGroup, waveManager)
val secondTower = Tower(Pos(120.0, 180.0), 10, 200.0, 1000, gameGroup, waveManager)
val thirdTower = Tower(Pos(120.0, 300.0), 10, 200.0, 1000, gameGroup, waveManager)
val fourthTower = Tower(Pos(120.0, 420.0), 10, 200.0, 1000, gameGroup, waveManager)
val playerHealth = new PlayerHealth(player, VBox)
player.addTower(firstTower)
player.addTower(secondTower)
player.addTower(thirdTower)
player.addTower(fourthTower)
//root.getChildren.add(VBox)
//VBox.setAlignment(ca)
/*val imageTest = FileManager.createImageView("file:src/res/Tower1.png")
val imageCopy = FileManager.createImageView("file:src/res/Tower1.png")
imageTest.relocate(1800,900)
imageTest.onMouseClicked   = (mouse: MouseEvent) => {
   player.health.update(1)

}
gameGroup.getChildren.add(imageTest)*/
val font = Font.loadFont("file:src/res/font.ttf", 40)
val goldSystem = new GoldSystem(player, VBox, font)
val waveSystem = new WaveSystem(waveManager, font, VBox)
root.setCenter(gameGroup)
root.setRight(VBox)
var nextSecond = System.currentTimeMillis() + 1000
var frameLastSecond = 0
var frameCurrentSecond = 0
this.waveManager.spawnWave()
     def mainLoop = () => {
        goldSystem.update()
        waveSystem.update()
        playerHealth.update()
        waveManager.update()
        player.update()
        stage.scene = scene
        val currentSecond = System.currentTimeMillis()
       if(currentSecond > nextSecond) {
         nextSecond += 1000
         frameLastSecond = frameCurrentSecond
         frameCurrentSecond = 0
       }
       frameCurrentSecond += 1
       println(frameLastSecond)
    }
    val ticker = new Ticker(mainLoop)
    ticker.start()

}


