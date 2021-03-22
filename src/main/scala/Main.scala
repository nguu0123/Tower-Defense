import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.layout.VBox
import scalafx.application.JFXApp
import scalafx.scene.{Group, Scene}
import scalafx.scene.paint.Color
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.input.MouseEvent
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
//Getting the GraphicsContext
val g = canvas.graphicsContext2D
FileManager.loadMap(grid)
grid.draw(g)
root.getChildren.add(canvas)
val towerImage = new ImageView(new Image("file:src/res/Tower1.png"))
towerImage.relocate(60.0, 0.0)
val secondTowerImage = new ImageView(new Image("file:src/res/Tower1.png"))
secondTowerImage.relocate(60.0, 120.0)
val thirdTowerImage = new ImageView(new Image("file:src/res/Tower1.png"))
thirdTowerImage.relocate(60.0, 240.0)
val fourthTowerImage = new ImageView(new Image("file:src/res/Tower1.png"))
fourthTowerImage.relocate(60.0, 360.0)
val firstTower = Tower(Pos(120.0, 60.0), 10, 200.0, 500, root)
val secondTower = Tower(Pos(120.0, 180.0), 10, 200.0, 500, root)
val thirdTower = Tower(Pos(120.0, 300.0), 10, 200.0, 500, root)
val fourthTower = Tower(Pos(120.0, 420.0), 10, 200.0, 500, root)
root.getChildren.add(towerImage)
root.getChildren.add(secondTowerImage)
  root.getChildren.add(thirdTowerImage)
  root.getChildren.add(fourthTowerImage)
//root.getChildren.add(VBox)
//VBox.setAlignment(ca)
val wave = new Wave(root, 10, grid)
firstTower.setWave(wave)
secondTower.setWave(wave)
thirdTower.setWave(wave)
fourthTower.setWave(wave)
var nextSecond = System.currentTimeMillis() + 1000
var frameLastSecond = 0
var frameCurrentSecond = 0
     def mainLoop = () => {
        wave.update()
        firstTower.update()
        secondTower.update()
        thirdTower.update()
        fourthTower.update()
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

