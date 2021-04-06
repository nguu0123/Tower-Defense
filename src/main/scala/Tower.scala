import scalafx.scene.image.{Image, ImageView}
import scala.util.control.Breaks._
import scalafx.scene.Group
class Tower(val pos: Pos, val damage: Int, val shootRange: Double, val shootRate: Int, val group: Group, waveManager: WaveManager, player: Player){

  private var currentWave: Wave = null
  private var currentEnemy: Enemies = null
  private var lastUpdate = System.currentTimeMillis()
  private var havePassed = System.currentTimeMillis()
  private var havePaused = false
  /** using List as each tower usually shoot 1 or 2 shoot until the first shoot hit => concat and deconstruct small and foreeach little */
  private var projectiles = List[Projectile]()
  private var towerImage: ImageView = null
  private var clicked = 0
  private val button = FileManager.createImageView("file:src/res/deleteButton.png")
   var isDestroyed = false
  val goldNeeded = Gold(150)
  def build() = {
    this.towerImage = FileManager.createImageView("file:src/res/Tower1.png")
    this.towerImage.relocate(this.pos.x - 60.0, this.pos.y - 60.0)
    this.button.relocate(this.pos.x + 30, this.pos.y - 60)
    group.getChildren.add(this.towerImage)
    this.towerImage.onMouseClicked = event => {
       if(clicked == 0) {
         group.getChildren.add(button)
         clicked = clicked ^ 1
       }
       else {
         group.getChildren.remove(button)
         clicked = clicked ^ 1
       }
      }
    this.button.onMouseClicked = event => {
      player.deleteTower(this)
      for(projectile <- this.projectiles)  projectile.remove(this.group)
      group.getChildren.remove(button)
    }
  }
  def setWave(wave: Wave) = {
    this.currentWave = wave
  }
  def setEnemy() = {
  if(this.currentWave != null)  {
      if (this.currentWave.getEnemies.nonEmpty) this.currentEnemy = {
       val ans = this.currentWave.getEnemies.find(x => x.center.distance(this.pos) <= this.shootRange && x.isAlive && !x.reachGoal)
        if(ans.nonEmpty) ans.get
        else null
      }
    }
  }
  def shoot() = {
    this.projectiles = this.projectiles ++ List(Projectile(this.pos, 5.0, 2.0, this.currentEnemy, this.damage))
  }
  def destroy() = {
    this.isDestroyed = true
    group.getChildren.remove(this.towerImage)
     for(projectile <- this.projectiles) if(!projectile.stopUpdate) {
       projectile.remove(this.group)
     }
  }
  def update() = {
    this.setWave(this.waveManager.getWave)
    this.setEnemy()
    this.havePassed += System.currentTimeMillis() - this.lastUpdate
    this.lastUpdate = System.currentTimeMillis()
    if(this.currentEnemy != null && this.havePassed > this.shootRate) {
       this.shoot()
       this.havePassed = 0
     }
     this.projectiles = this.projectiles.filter(projectile => !projectile.stopUpdate)
     for(projectile <- this.projectiles) {
         projectile.update(this.group)
     }
  }

  def updateTime() = {
    if(!this.havePaused) {
      this.havePassed += System.currentTimeMillis() - this.lastUpdate
      this.havePaused = true
    }
    this.lastUpdate = System.currentTimeMillis()
  }
}
object Tower {
 def apply(pos: Pos, damage: Int, shootRange: Double, shootRate: Int, group: Group, waveManager: WaveManager, player: Player) = new Tower(pos, damage, shootRange, shootRate, group, waveManager, player)
}
