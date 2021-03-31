import javafx.scene.effect.{BoxBlur, ColorAdjust}
import scalafx.scene.Group
import scalafx.scene.image.{Image, ImageView}
import scalafx.Includes._
import scalafx.scene.paint.Color
class InputManager(val player: Player) {

    // Sets do not allow duplicates, so they are useful here
    val brightnessChange = new ColorAdjust()
    val colorChange = new ColorAdjust()
    val green =  Color.Green
    var mouseClicked = 0
    val shop1 = FileManager.createImageView("file:src/res/tower1.png")
    def handleInput(imageView: ImageView, group: Group) = {
        imageView.onMouseClicked = event => {
            mouseClicked = mouseClicked ^ 1
            group.getChildren.remove(shop1)
            if(mouseClicked == 1){
              brightnessChange.setBrightness(-0.5)
              imageView.setEffect(brightnessChange)
            }
            else {
              brightnessChange.setBrightness(0)
              brightnessChange.setHue(0)
              imageView.setEffect(brightnessChange)
            }
        }
      if(mouseClicked == 1) {
         group.onMouseClicked = event => {
            mouseClicked = mouseClicked ^ 1
            group.getChildren.remove(shop1)
            brightnessChange.setBrightness(0)
            if(player.canBuild(Pos(event.getSceneX, event.getSceneY))) player.buildTower(Pos(event.getSceneX, event.getSceneY))
         }
         group.onMouseMoved = event => {
          group.getChildren.remove(shop1)
          val pos = (event.getSceneX - 60, event.getSceneY - 60)
           if(player.canBuild(Pos(event.getSceneX, event.getSceneY))) {
             colorChange.setHue(0.4)
           }
           else {
            colorChange.setHue(-0.4)
           }
           shop1.setEffect(colorChange)
           shop1.setOpacity(0.6)
          shop1.relocate(pos._1, pos._2)
          if(pos._1 <= 1080 && pos._1 >= 0 && pos._2 >=0 && pos._2 <= 600) group.getChildren.add(shop1)
         }
      }
      else {
         group.onMouseMoved = null
         group.onMouseClicked = null
      }
        // Get mouse screen coordinates
    }

}