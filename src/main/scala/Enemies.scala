import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.Group
class Enemies(var pos:Pos, var velocity: Velocity, val health: Health, val grid: Grid)  {
  lazy val image = {
    val imageView = new ImageView(new Image("file:src/res/Enemy.png"))
    imageView.relocate(this.pos.x, this.pos.y)
    imageView
  }
  var currentSquare = grid.elementAt(this.pos)
  def reachGoal: Boolean = this.pos.inRange(1920, 1080)
  def isAlive: Boolean = !this.health.isDead && !this.reachGoal
  def draw(group: Group) = {
    group.getChildren.add(this.image)
  }
  def remove(group: Group) = {
    group.getChildren.remove(this.image)
  }
  def nextDirection: Direction = {
     val up   = grid.getTexture(this.pos + Pos(0.0, -60.0))
     val down = grid.getTexture(this.pos + Pos(0.0, 60.0))
     val left = grid.getTexture(this.pos + Pos(-60.0, 0.0))
     val right = grid.getTexture(this.pos + Pos(60.0, 0.0))
     val currentSquareTexture = grid.getTexture(this.pos)
    if(this.velocity.movingUp && this.pos.y != this.currentSquare.pos.y) Direction.Up
    else
    {
      if (currentSquareTexture == right && !this.velocity.movingLeft) Direction.Right
      else if (currentSquareTexture == up && !this.velocity.movingDown) Direction.Up
      else if (currentSquareTexture == down && !this.velocity.movingUp) Direction.Down
      else Direction.Left
    }

  }
  def update(): Unit = {
    this.velocity = this.velocity.changeDirection(this.nextDirection)
     this.currentSquare = grid.elementAt(this.pos)
    this.pos = this.pos.nextPos(this.velocity)
    this.image.relocate(this.pos.x, this.pos.y)
  }
}
object Enemies {
 def apply(pos: Pos, velocity: Velocity, health: Health, grid: Grid) = new Enemies(pos, velocity, health, grid)
}
