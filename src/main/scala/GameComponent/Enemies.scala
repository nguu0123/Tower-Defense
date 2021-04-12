package GameComponent

import Utils._
import GameComponent.FileManager
import scalafx.scene.Group
class Enemies(var pos:Pos, var velocity: Velocity, val health: Health, val grid: Grid, val gold: Gold, filePath: String)  {
  private lazy val aliveImages = (1 to 20).map(x => FileManager.createImageView(filePath + "run (" + x + ").png"))
  private lazy val deadImages  = (1 to 20).map(x => FileManager.createImageView(filePath + "die (" + x + ").png"))
  private val healtPercent = FileManager.createImageView("file:src/res/Health0.png")
  private val healthBorder = FileManager.createImageView("file:src/res/Health1.png")
  private val healthBackground = FileManager.createImageView("file:src/res/Health2.png")
  private lazy val images = Seq(deadImages, aliveImages)
  private var haveSpawn = false
  var canDamage = true
  var canGiveGold = true
  var currentSquare = grid.elementAt(this.pos)
  var index = 0
  var time = 0
  var state = 1
  var stopUpdate = false
  def center = this.pos + Pos(20.0, 20.0)
  def reachGoal: Boolean = this.pos.inRange(1100, 720)
  def isAlive: Boolean = !this.health.isDead
  def canBeShooted: Boolean = !this.stopUpdate || !this.reachGoal
  def removeHealthBar(group: Group) = {
    group.getChildren.removeAll(healthBackground, healtPercent, healthBorder)
  }
  def draw(group: Group) = {
    group.getChildren.add(this.images(state)(index))
    if(!haveSpawn) {
      group.getChildren.addAll(healthBackground, healtPercent, healthBorder)
      haveSpawn = true
    }
    if(this.health.percent > 0.05) this.healtPercent.setFitWidth(32 * this.health.percent)
    else this.healtPercent.setFitWidth(32 * 0.01)
  }
  def remove(group: Group) = {
    group.getChildren.remove(this.images(state)(index))
    time = (time + 1) % 3
    if(time == 0) index = (index + 1) % 20
    if(state == 0 && index == 19)   stopUpdate = true

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
    this.healthBorder.relocate(this.pos.x + 7, this.pos.y - 4)
    this.healtPercent.relocate(this.pos.x + 7, this.pos.y - 4)
    this.healthBackground.relocate(this.pos.x + 7, this.pos.y - 4)
    this.draw(group)
  }
}
object Enemies {
 def apply(pos: Pos, velocity: Velocity, health: Health, grid: Grid, gold: Gold, filePath: String) = new Enemies(pos, velocity, health, grid, gold, filePath)
 def createGreenMonster(pos: Pos, grid: Grid) = new Enemies(pos, Velocity(Direction.Down, 1.5), Health(80, 80), grid, Gold(10), "file:src/res/greenMonster/")
 def createBrainlessMonster(pos: Pos, grid: Grid) = new Enemies(pos, Velocity(Direction.Down, 1.5), Health(130, 130), grid, Gold(15), "file:src/res/brainlessMonster/")
}
