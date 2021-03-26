import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.layout.VBox
import scalafx.application.JFXApp
import scalafx.scene.{Group, Scene}
import scalafx.scene.paint.Color
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import scalafx.Includes._
import scala.collection.mutable.Set
import scalafx.event.EventHandler
object test extends JFXApp {
stage = new JFXApp.PrimaryStage {
    title.value = "Test"
    resizable = false
}
val grid = new Grid(1920, 1080)
val root = new Group() //Simple pane component
val scene = new Scene(root) //Scene acts as a container for the scene graph
val canvas = new Canvas(1920, 1080) //Give width and height as a parameter.
val VBox = new VBox
VBox.getChildren.add(new ImageView(new Image("file:src/res/7.png")))
VBox.getChildren.add(new ImageView(new Image("file:src/res/17.png")))
root.getChildren.add(VBox)
val g = canvas.graphicsContext2D
FileManager.loadMap(grid)
grid.draw(g)
root.getChildren.add(canvas)
 val waveManager = new WaveManager(root, grid, 3, 5, 0)
val firstTower = Tower(Pos(120.0, 60.0), 10, 200.0, 500, root, waveManager)
val secondTower = Tower(Pos(120.0, 180.0), 10, 200.0, 500, root, waveManager)
val thirdTower = Tower(Pos(120.0, 300.0), 10, 200.0, 500, root, waveManager)
val fourthTower = Tower(Pos(120.0, 420.0), 10, 200.0, 500, root, waveManager)
val player = new Player(Gold(100), grid,Health(100))
player.addTower(firstTower)
player.addTower(secondTower)
player.addTower(thirdTower)
player.addTower(fourthTower)
//root.getChildren.add(VBox)
//VBox.setAlignment(ca)
val imageTest = FileManager.createImageView("file:src/res/Tower1.png")
val imageCopy = FileManager.createImageView("file:src/res/Tower1.png")
imageTest.relocate(1800,900)
imageTest.onMouseDragged  = (mouse: MouseEvent) => {
  imageTest.onMouseReleased = (releaseMouse: MouseEvent) => {
      player.addTower(Tower(Pos(mouse.sceneX, mouse.sceneY), 10, 200.0, 500, root, waveManager))
  }
   root.getChildren.remove(imageCopy)
}
root.getChildren.add(imageTest)
var nextSecond = System.currentTimeMillis() + 1000
var frameLastSecond = 0
var frameCurrentSecond = 0
this.waveManager.spawnWave()
    var previousUpdate = System.nanoTime() / 1000000
    var lag = 0.0
    val updatesPerSecond: Double = 120 // Not real time, but granularity.
    val msPerUpdate = (1.0/updatesPerSecond)*1000 // How much lag value a logic update eats up.


    def mainLoop = () => {

        val now = System.nanoTime() / 1000000
        val elapsed = now - previousUpdate
        previousUpdate = now
        lag += elapsed
        while(lag >= msPerUpdate) {
            waveManager.update()
            player.update()
             val currentSecond = System.currentTimeMillis()
       if(currentSecond > nextSecond) {
         nextSecond += 1000
         frameLastSecond = frameCurrentSecond
         frameCurrentSecond = 0
       }
            lag = lag - msPerUpdate
        }
              frameCurrentSecond += 1
       println(frameLastSecond)
           stage.scene = scene
    }
    val ticker = new Ticker(mainLoop)
    ticker.start()

}


