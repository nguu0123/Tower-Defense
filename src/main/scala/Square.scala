import javax.imageio.ImageIO
import java.io.File
class Square(val pos: Pos, val width:Int, val height: Int, val texture: Texture) {
  override def toString = s"$pos $width $height ${texture.filePath}"
}
