import javafx.geometry.Rectangle2D
import scalafx.scene.image.{Image, ImageView}

import javax.imageio.ImageIO
import java.io.File
import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color._
class Square(val pos: Pos, val width:Int, val height: Int, val texture: Texture) {
     lazy val image = new Image(texture.filePath)
  override def toString = s"$pos $width $height ${texture.filePath}"
}
