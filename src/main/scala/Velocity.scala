import scala.math._
final case class Velocity(val direction: Direction, val speed: Double) {
  require(speed >= 0, "speed cant be negative")
  lazy val dx = this.direction.dx * speed
  lazy val dy = this.direction.dy * speed
  def +(another: Velocity): Velocity = Velocity(this.dx + another.dx, this.dy + another.dy)
  def -(another: Velocity): Velocity = Velocity(this.dx - another.dx, this.dy - another.dy)
  def *(multiplier: Double): Velocity = this.copy(speed = this.speed * multiplier)
  def /(multiplier: Double): Velocity = this.copy(speed = this.speed / multiplier)
  def changeDirection(newDirection: Direction): Velocity = this.copy(direction = newDirection)
  def changeSpeed(newSpeed: Double): Velocity = this.copy(speed = newSpeed)
  def faster(amount: Double): Velocity = this.copy(speed = max(this.speed + amount, 0) )
  def slower(amount: Double): Velocity = this.copy(speed = max(this.speed - amount, 0) )
}
object Velocity {
  def apply(dx: Double, dy: Double): Velocity = Velocity(Direction.fromRad(atan2(-dy, dx)), hypot(dx, dy))
}
