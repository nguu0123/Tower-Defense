import scala.math.{hypot, max}

final case class Pos(val x: Double, val y: Double) {
  @inline def +(another: Pos): Pos = Pos(this.x + another.x, this.y + another.y)
  @inline def -(another: Pos): Pos = Pos(this.x - another.x, this.y - another.y)
  @inline def xDiff(another: Pos): Double = this.x - another.x
  @inline def yDiff(another: Pos): Double = this.y - another.y
  @inline def distance(another: Pos): Double = hypot(this.xDiff(another), this.yDiff(another))
  @inline def nextPos(velocity: Velocity): Pos = this + velocity.toPos
  @inline def inRange(maxX: Double, maxY: Double): Boolean = this.x > maxX || this.y > maxY
  @inline def approximate: Pos = Pos(this.x - this.x % 60, this.y - this.y % 60)
  @inline override def toString = s"($x, $y)"
}
object Pos {
  def apply(x: Double, y: Double): Pos = new Pos(x, y)
}
