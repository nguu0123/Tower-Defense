import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.Group
class Enemies(var pos:Pos, var velocity: Velocity, val health: Health, val grid: Grid)  {
  private lazy val aliveImages = (0 until 5).map(x => FileManager.createImageView("file:src/res/run" + x + ".png"))
  private lazy val deadImages = (0 until 5).map(x => FileManager.createImageView("file:src/res/dead" + x + ".png"))
  private lazy val images = Seq(deadImages, aliveImages)
  var currentSquare = grid.elementAt(this.pos)
  var index = 0
  var time = 0
  var state = 1
  var stopUpdate = false
  /// state = 1 if the enemy is alive and 0 if it is dead///
  def reachGoal: Boolean = this.pos.inRange(1800, 1080)
  def isAlive: Boolean = !this.health.isDead
  def draw(group: Group) = {
    group.getChildren.add(this.images(state)(index))
  }
  def remove(group: Group) = {
    group.getChildren.remove(this.images(state)(index))
    time = (time + 1) % 15
    if(time == 0) index = (index + 1) % 5
    if(state == 0 && index == 4) stopUpdate = true
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
  def update(group: Group): Unit = {
    this.remove(group)
    this.velocity = this.velocity.changeDirection(this.nextDirection)
     this.currentSquare = grid.elementAt(this.pos)
    this.pos = this.pos.nextPos(this.velocity)
    if(!this.isAlive && state == 1) {
      state = 0
      index = 0
    }
    this.images(state)(index).relocate(this.pos.x, this.pos.y)
    this.draw(group)
  }
}
object Enemies {
 def apply(pos: Pos, velocity: Velocity, health: Health, grid: Grid) = new Enemies(pos, velocity, health, grid)
}
