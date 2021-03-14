import scalafx.application.JFXApp
import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.layout.Pane
import scalafx.application.JFXApp
import scalafx.scene.{Scene,Group}
import scalafx.scene.paint.Color
import scalafx.scene.image.Image
object test extends JFXApp {
stage = new JFXApp.PrimaryStage {
    title.value = "Test"
    resizable = false
}
val grid = new Grid(1920, 1080)
val root = new Group() //Simple pane component
val scene = new Scene(root) //Scene acts as a container for the scene graph
val canvas = new Canvas(1920, 1080) //Give width and height as a parameter.

//Getting the GraphicsContext
val g = canvas.graphicsContext2D
JsonFileManger.loadMap(grid)
grid.draw(g)
root.getChildren.add(canvas)
val firstEnemy = Enemies(Pos(0,1020), Velocity(Direction.Down, 5), Health(100), grid)
val seconEnemy = Enemies(Pos(0,0), Velocity(Direction.Down, 5), Health(100), grid)
firstEnemy.draw(root)
seconEnemy.draw(root)
     def mainLoop = () => {

        firstEnemy.update()
       seconEnemy.update()
        stage.scene = scene

    }

    val ticker = new Ticker(mainLoop)
    ticker.start()





}

