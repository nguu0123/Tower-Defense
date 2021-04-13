package Utils

import scala.math.{Pi, atan2, cos, sin}
case class Direction(val dx: Double, val dy: Double) {
  def opposite:  Direction = Direction(-dx, -dy)
  def oppositeX: Direction = Direction(-dx, dy)
  def oppositeY: Direction = Direction(dx, -dy)
  def toRad: Double = {
    val atan = atan2(-this.dy, this.dx)
    if (atan < 0) atan + 2 * Pi else atan
  }
  def toDegree: Double = this.toRad.toDegrees
  def +(another: Direction): Direction = Direction.fromDegree(this.toDegree + another.toDegree)
  def ==(another: Direction): Boolean = this.toRad == another.toRad

  override def toString: String = s"${this.dx} ${this.dy}"
}
object Direction {
  val Up:    Direction = new Direction(0.0, -1.0) {override val toString = "Utils.Direction.Up"}
  val Down:  Direction = new Direction(0.0, 1.0)  {override val toString = "Utils.Direction.Down"}
  val Left:  Direction = new Direction(-1.0, 0.0) {override val toString = "Utils.Direction.Left"}
  val Right: Direction = new Direction(1.0, 0.0)  {override val toString = "Utils.Direction.Right"}
  def fromRad(angle: Double): Direction = Direction( cos(angle), -sin(angle))
  def fromDegree(angle: Double): Direction = fromRad(angle.toRadians)
  def fromDeltas(dx: Double, dy: Double) =  fromRad(atan2(-dy, dx))
}
