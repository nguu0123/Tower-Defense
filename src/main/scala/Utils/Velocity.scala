package Utils

import scala.math._
final case class Velocity(val direction: Direction, val speed: Double) {
  require(speed >= 0, "speed cant be negative")
  lazy val dx = this.direction.dx * speed
  lazy val dy = this.direction.dy * speed
  def +(another: Velocity): Velocity = Velocity(this.dx + another.dx, this.dy + another.dy)
  def -(another: Velocity): Velocity = Velocity(this.dx - another.dx, this.dy - another.dy)
  def *(multiplier: Double): Velocity = this.copy(speed = this.speed * multiplier)
  def /(dividor: Double): Velocity = this.copy(speed = this.speed / dividor)
  def changeDirection(newDirection: Direction): Velocity = this.copy(direction = newDirection)
  def changeSpeed(newSpeed: Double): Velocity = this.copy(speed = newSpeed)
  def faster(amount: Double): Velocity = this.copy(speed = max(this.speed + amount, 0) )
  def slower(amount: Double): Velocity = this.copy(speed = max(this.speed - amount, 0) )
  def movingUp   : Boolean  = this.direction == Direction.Up
  def movingDown : Boolean  = this.direction == Direction.Down
  def movingLeft : Boolean  = this.direction == Direction.Left
  def movingRight: Boolean  = this.direction == Direction.Right
  def moveFrom(pos: Pos): Pos = pos + this.toPos
  def toPos: Pos = Pos(this.dx, this.dy)
}
object Velocity {
  def apply(dx: Double, dy: Double): Velocity = Velocity(Direction.fromDeltas(dx, dy), hypot(dx, dy))
  def aplly(direction: Direction, speed: Double): Velocity = Velocity(direction, speed)
}
