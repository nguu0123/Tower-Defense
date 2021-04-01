import scalafx.scene.image.{Image, ImageView}
import scala.util.control.Breaks._
import scalafx.scene.Group
class Tower(val pos: Pos, val damage: Int, val shootRange: Double, val shootRate: Int, val group: Group, waveManager: WaveManager, player: Player){

  private var currentWave: Wave = null
  private var currentEnemy: Enemies = null
  private var currentTime = System.currentTimeMillis()
  private var projectiles = List[Projectile]()
  private var  towerImage: ImageView = null
  private var clicked = 0
  private val button = FileManager.createImageView("file:src/res/button.png")
   var isDestroyed = false
  val goldNeeded = Gold(100)
  def build() = {
    this.towerImage = FileManager.createImageView("file:src/res/Tower1.png")
    this.towerImage.relocate(this.pos.x - 60.0, this.pos.y - 60.0)
    this.button.relocate(this.pos.x + 30, this.pos.y - 60)
    group.getChildren.add(this.towerImage)
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
  }
  def update() = {
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
      for(projectile <- this.projectiles) if(!projectile.stopUpdate) projectile.remove(this.group)
      group.getChildren.remove(button)
    }

    this.setWave(this.waveManager.getWave)
    this.setEnemy()
      if(this.currentEnemy != null && System.currentTimeMillis() - this.currentTime > this.shootRate) {
       this.shoot()
       this.currentTime = System.currentTimeMillis()
     }
     for(projectile <- this.projectiles) if(!projectile.stopUpdate) {
       projectile.update(this.group)
     }
  }
}
object Tower {
 def apply(pos: Pos, damage: Int, shootRange: Double, shootRate: Int, group: Group, waveManager: WaveManager, player: Player) = new Tower(pos, damage, shootRange, shootRate, group, waveManager, player)
}
