import scalafx.scene.Group
import scalafx.scene.image.{ImageView, Image}
import scalafx.Includes._
import scalafx.scene.input.MouseButton

import scala.collection.mutable.Set
object InputManager {

    // Sets do not allow duplicates, so they are useful here
    val mousePressed = Set[MouseButton]()
    var mouseClicked = 0
    val shop1 = FileManager.createImageView("file:src/res/Shop1.png")
    // Handle keyboard and mouse input
    def handleInput(imageView: ImageView, group: Group) = {
        imageView.onMouseClicked = event => {
            mouseClicked = mouseClicked ^ 1
            group.getChildren.remove(shop1)

        }
      if(mouseClicked == 1) {
         group.onMouseClicked = event => {
            mouseClicked = mouseClicked ^ 1
            group.getChildren.remove(shop1)
         }
         group.onMouseMoved = event => {
          group.getChildren.remove(shop1)
          val pos = (event.getSceneX - 30, event.getSceneY - 30)
          shop1.relocate(pos._1, pos._2)
          if(pos._1 <= 1140 && pos._1 >= 0 && pos._2 >=0 && pos._2 <= 634) group.getChildren.add(shop1)
         }
      }
      else {
         group.onMouseMoved = null
      }
        // Get mouse screen coordinates
    }

}