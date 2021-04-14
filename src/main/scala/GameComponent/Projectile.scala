package GameComponent

import Utils.{Direction, Pos, Velocity}
import scalafx.scene.Group
class Projectile(var pos: Pos, val speed: Double, val radious: Double, val target: Enemies, val damage: Int, filePath: String) {
  var velocity = Velocity(Direction.Up, this.speed)
  val radiousSq = radious * radious
  var stopUpdate = false
  var haveFired = false
  val projectileImage = FileManager.createImageView(filePath)
  val rotate = !(filePath == "file:src/res/Rock.png")

  def draw(group: Group) = {
    if(!haveFired) {
      group.getChildren.add(this.projectileImage)
      haveFired = true
    }
    else {
      this.projectileImage.relocate(this.pos.x, this.pos.y)
    }
  }
  def remove(group: Group) = {
    group.getChildren.remove(this.projectileImage)
  }
  def hitTarget: Boolean = this.pos.distance(target.center) < (this.radiousSq + 2)
  def update(group: Group) = {
      if(target.stopUpdate || target.reachGoal ) this.remove(group)
      else {
         val direction = target.center - this.pos
         val newDirection = Direction.fromDeltas(direction.x, direction.y)
         this.velocity = this.velocity.changeDirection(newDirection)
         if(rotate) this.projectileImage.rotate = -newDirection.toDegree + 30
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
 def apply(pos: Pos, speed: Double, radious: Double, target: Enemies, damage: Int, filePath: String) = new Projectile(pos, speed, radious, target, damage, filePath)
}
