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
//root.getChildren.add(VBox)
//VBox.setAlignment(ca)
val firstEnemy = Enemies(Pos(0,1020), Velocity(Direction.Down, 1), Health(100), grid)
val seconEnemy = Enemies(Pos(0,0), Velocity(Direction.Down, 5), Health(100), grid)
val wave = new Wave(root)
wave.addEnemy(firstEnemy)
wave.addEnemy(seconEnemy)
     def mainLoop = () => {
        if(firstEnemy.pos.x > 500) firstEnemy.health.update(100)
        wave.update()
        stage.scene = scene
    }

    val ticker = new Ticker(mainLoop)
    ticker.start()





}

