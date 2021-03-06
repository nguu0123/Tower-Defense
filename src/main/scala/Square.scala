import javafx.geometry.Rectangle2D
import scalafx.scene.image.{Image, ImageView}

import javax.imageio.ImageIO
import java.io.File
import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color._
class Square(val pos: Pos, val width:Int, val height: Int, val texture: Texture) {
     lazy val image = {
       val rec = new ImageView()
       rec.setImage(new Image(texture.filePath))
       rec.setViewport(new Rectangle2D(pos.x, pos.y, 60, 60))
       rec
     }
  override def toString = s"$pos $width $height ${texture.filePath}"
}
