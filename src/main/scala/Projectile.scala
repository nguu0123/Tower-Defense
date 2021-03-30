import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color._
import scalafx.scene.Group
class Projectile(var pos: Pos, val speed: Double, val radious: Double, val target: Enemies, val damage: Int) {
   var velocity = Velocity( Direction.Up, this.speed)
   val radiousSq = radious * radious
   var stopUpdate = false
   var haveFired = false
    val projectileImage = FileManager.createImageView("file:src/res/Fireball1.png")
  def draw(group: Group) = {
    if(!haveFired) {
      group.getChildren.add(this.projectileImage)
      haveFired = true
    }
    else this.projectileImage.relocate(this.pos.x, this.pos.y)
  }
  def remove(group: Group) = {
    group.getChildren.remove(this.projectileImage)
  }
  def hitTarget: Boolean = this.pos.distance(target.center) < this.radiousSq + 2
   def update(group: Group) = {
      if(target.stopUpdate || target.reachGoal ) this.remove(group)
      else {
         val direction = target.center - this.pos
         val newDirection = Direction.fromDeltas(direction.x, direction.y)
         this.velocity = this.velocity.changeDirection(newDirection)
         this.projectileImage.rotate = -newDirection.toDegree + 30
         this.pos = this.pos.nextPos(this.velocity)
         this.projectileImage.relocate(this.pos.x, this.pos.y)
         if(this.hitTarget) {
            this.remove(group)
            this.stopUpdate = true
            target.health.update(this.damage)
         }
         else {
           this.draw(group)
         }
      }
   }
}
object Projectile {
 def apply(pos: Pos, speed: Double, radious: Double, target: Enemies, damage: Int) = new Projectile(pos, speed, radious, target, damage)
}
