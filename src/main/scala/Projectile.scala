import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color._
import scalafx.scene.Group
class Projectile(var pos: Pos, val speed: Double, val radious: Double, val target: Enemies, val damage: Int) {
   var velocity = Velocity( Direction.Up, this.speed)
   val radiousSq = radious * radious
   var stopUpdate = false
    val rectangle = new Rectangle {
        width = 10
        height = 10
        x = 10
        y = 10
        fill = Blue
    }
  def draw(group: Group) = {
    group.getChildren.add(this.rectangle)
  }
  def remove(group: Group) = {
    group.getChildren.remove(this.rectangle)
  }
  def hitTarget: Boolean = this.pos.distance(target.center) < this.radiousSq + 2
   def update(group: Group) = {
      if(target.stopUpdate || target.reachGoal ) this.remove(group)
      else {
         val direction = target.center - this.pos
         this.velocity = this.velocity.changeDirection(Direction.fromDeltas(direction.x, direction.y))
         this.pos = this.pos.nextPos(this.velocity)
         this.rectangle.relocate(this.pos.x, this.pos.y)
         if(this.hitTarget) {
            this.remove(group)
            this.stopUpdate = true
            target.health.update(this.damage)
         }
         else {
           this.remove(group)
           this.draw(group)
         }
      }
   }
}
object Projectile {
 def apply(pos: Pos, speed: Double, radious: Double, target: Enemies, damage: Int) = new Projectile(pos, speed, radious, target, damage)
}
