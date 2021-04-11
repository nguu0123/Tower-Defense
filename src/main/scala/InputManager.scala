import javafx.scene.effect.{BoxBlur, ColorAdjust}
import scalafx.scene.Group
import scalafx.scene.image.{Image, ImageView}
import scalafx.Includes._
import scalafx.scene.input.MouseButton
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
class InputManager(var player: Player) {
    val brightnessChange = new ColorAdjust()
    val colorChange = new ColorAdjust()
    val green =  Color.Green
    var mouseClicked = 0
    var towerChosen = 1
    val shop0 = FileManager.createImageView("file:src/res/tower0.png")
    val shop1 = FileManager.createImageView("file:src/res/tower1.png")
    val shop2 = FileManager.createImageView("file:src/res/tower2.png")
    val shop = Vector(shop0, shop1, shop2)
    //can add circle that show tower shootRange => dont know how to do that//
    def setPlayer(player: Player) = {
     this.player = player
    }
    def handleInput(towers: Vector[ImageView], group: Group) = {
        towers(1).onMouseClicked = event => {
            mouseClicked = mouseClicked ^ 1
            towers(towerChosen).effect = null
            group.getChildren.remove(shop(towerChosen))
            towerChosen = 1
            group.getChildren.remove(shop(towerChosen))
        }
          towers(0).onMouseClicked = event => {
            mouseClicked = mouseClicked ^ 1
            towers(towerChosen).effect = null
            group.getChildren.remove(shop(towerChosen))
            towerChosen = 0
            group.getChildren.remove(shop(towerChosen))
        }
           towers(2).onMouseClicked = event => {
            mouseClicked = mouseClicked ^ 1
             towers(towerChosen).effect = null
             group.getChildren.remove(shop(towerChosen))
            towerChosen = 2
        }

      if(mouseClicked == 1) {
         brightnessChange.setBrightness(-0.5)
         towers(towerChosen).effect = (brightnessChange)
         group.onMouseClicked = event => {
            mouseClicked = 0
            group.getChildren.remove(shop(towerChosen))
            brightnessChange.setBrightness(0)
            if(this.player.canBuild(Pos(event.getSceneX, event.getSceneY))) this.player.buildTower(towerChosen, Pos(event.getSceneX, event.getSceneY))
         }
         group.onMouseMoved = event => {
          group.getChildren.remove(shop(towerChosen))
          val pos = (event.getSceneX - 60, event.getSceneY - 60)
           if(this.player.canBuild(Pos(event.getSceneX, event.getSceneY))) {
             colorChange.setHue(0.4)
           }
           else {
            colorChange.setHue(-0.4)
           }
           shop(towerChosen).setEffect(colorChange)
           shop(towerChosen).setOpacity(0.6)
          shop(towerChosen).relocate(pos._1, pos._2)
          if(pos._1 <= 1060 && pos._1 >= 0 && pos._2 >=0 && pos._2 <= 620) group.getChildren.add(shop(towerChosen))
         }
      }
      else {
         group.onMouseMoved = null
         group.onMouseClicked = null
         towers(towerChosen).effect = null
      }
        // Get mouse screen coordinates
    }

}