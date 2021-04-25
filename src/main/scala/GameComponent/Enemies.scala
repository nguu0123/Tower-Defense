package GameComponent

import Utils._
import scalafx.scene.Group
class Enemies(var pos:Pos, var velocity: Velocity, val health: Health, val grid: Grid, val gold: Gold, filePath: String)  {
  private val aliveImages = (1 to 20).map(x => FileManager.createImageView(filePath + "run (" + x + ").png")).toArray
  private val deadImages  = (1 to 20).map(x => FileManager.createImageView(filePath + "die (" + x + ").png")).toArray
  private val healtPercent     = FileManager.createImageView("file:src/res/Health0.png")
  private val healthBorder     = FileManager.createImageView("file:src/res/Health1.png")
  private val healthBackground = FileManager.createImageView("file:src/res/Health2.png")
  private val images = Array(deadImages, aliveImages)
  private var haveSpawn = false
  var canDamage = true
  var canGiveGold = true
  private var currentSquare = grid.elementAt(this.pos)
  private var imageIndex = 0
  private var time  = 0
  private var state = 1
  var stopUpdate = false

  def center: Pos        = this.pos + Pos(20.0, 20.0)
  def reachGoal: Boolean = this.pos.inRange(1100, 720)
  def isAlive: Boolean   = !this.health.isDead
  def removeHealthBar(group: Group) = {
    group.getChildren.removeAll(this.healthBackground, this.healtPercent, this.healthBorder)
  }
  def draw(group: Group) = {
    group.getChildren.add(this.images(state)(imageIndex))
    if(!this.haveSpawn) {
      group.getChildren.addAll(this.healthBackground, this.healtPercent, this.healthBorder)
      this.haveSpawn = true
    }
    if(this.health.percent > 0.05) {
      this.healtPercent.setFitWidth(32 * this.health.percent)
    }
    else {
      /** If I write setFitWidth(0), it will actually make the health bar fulll */
      this.healtPercent.setFitWidth(32 * 0.01)
    }
  }
  def remove(group: Group) = {
    group.getChildren.remove(this.images(state)(imageIndex))
    this.time = (this.time + 1) % 3
    if(this.time == 0) {
      this.imageIndex = (this.imageIndex + 1) % 20
    }
    if(this.state == 0 && this.imageIndex == 19) {
      this.stopUpdate = true
    }
  }

  /** monster can only go in the path with the same texture so monster will find the next square with the same texture to go at that direction */
  def nextDirection: Direction = {
    val up    = grid.getTexture(this.pos + Pos(0.0, -60.0))
    val down  = grid.getTexture(this.pos + Pos(0.0, 60.0))
    val left  = grid.getTexture(this.pos + Pos(-60.0, 0.0))
    val right = grid.getTexture(this.pos + Pos(60.0, 0.0))
    val currentSquareTexture = grid.getTexture(this.pos)
    if(this.velocity.movingUp && this.pos.y != this.currentSquare.pos.y) Direction.Up
    else
    {
      if (currentSquareTexture == right && !this.velocity.movingLeft)   Direction.Right
      else if (currentSquareTexture == up && !this.velocity.movingDown) Direction.Up
      else if (currentSquareTexture == down && !this.velocity.movingUp) Direction.Down
      else Direction.Left
    }
  }
  /** Every update, the monster will have a new image so I will remove the old, update logic (health, pos), and then add the new image.
   *  The health bar is relocated */
  def update(group: Group): Unit = {
    this.remove(group)
    this.velocity = this.velocity.changeDirection(this.nextDirection)
    this.currentSquare = grid.elementAt(this.pos)
    this.pos = this.pos.nextPos(this.velocity)
    if(!this.isAlive && this.state == 1) {
      this.state = 0
      this.imageIndex = 0
    }
    this.images(state)(imageIndex).relocate(this.pos.x, this.pos.y)
    this.healthBorder.relocate(this.pos.x + 7, this.pos.y - 4)
    this.healtPercent.relocate(this.pos.x + 7, this.pos.y - 4)
    this.healthBackground.relocate(this.pos.x + 7, this.pos.y - 4)
    this.draw(group)
  }
}
object Enemies {
 def apply(pos: Pos, velocity: Velocity, health: Health, grid: Grid, gold: Gold, filePath: String) = new Enemies(pos, velocity, health, grid, gold, filePath)
 def createGreenMonster(pos: Pos, grid: Grid)     =  Enemies(pos, Velocity(Direction.Down, 1.5), Health(80, 80)  , grid, Gold(10), "file:src/res/greenMonster/")
 def createBrainlessMonster(pos: Pos, grid: Grid) =  Enemies(pos, Velocity(Direction.Down, 1.5), Health(130, 130), grid, Gold(15), "file:src/res/brainlessMonster/")
 def createBlackMonster(pos: Pos, grid: Grid)     =  Enemies(pos, Velocity(Direction.Down, 1.5), Health(250, 250), grid, Gold(50), "file:src/res/blackMonster/")
}
