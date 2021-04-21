package GameComponent

import Utils.Pos
import scalafx.scene.image.Image
class Square(val pos: Pos, val width: Int, val height: Int, val texture: Texture, var buildable: Boolean) {
  lazy val image = new Image(texture.filePath)
  def ==(another: Square): Boolean = this.pos.x == another.pos.x && this.pos.y == this.pos.y
  def !=(another: Square): Boolean = this.pos.x != another.pos.x && this.pos.y != this.pos.y
  override def toString = s"$pos $width $height ${texture.filePath}"
}
