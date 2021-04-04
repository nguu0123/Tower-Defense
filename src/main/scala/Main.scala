import scalafx.scene.canvas.{Canvas, GraphicsContext}
import scalafx.scene.layout.{BorderPane, VBox, Background, BackgroundFill, CornerRadii}
import scalafx.geometry.Insets
import scalafx.application.JFXApp
import scalafx.scene.{Group, Scene}
import scalafx.scene.paint.Color
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import scalafx.Includes._
import scalafx.scene.text.Font

import scalafx.scene.control.Label
object test extends JFXApp {
val game = new Game
val gameGUI = new GameGui(game)
stage = new JFXApp.PrimaryStage {
    title.value = "Tower Defense"
    resizable = false
     scene = gameGUI

}


}


