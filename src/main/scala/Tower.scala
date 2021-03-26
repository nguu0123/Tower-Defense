import scalafx.scene.image.{Image, ImageView}
import scala.util.control.Breaks._
import scalafx.scene.Group
class Tower(val pos: Pos, val damage: Int, val shootRange: Double, val shootRate: Int, val group: Group, waveManager: WaveManager){
  val towerImage = FileManager.createImageView("file:src/res/Tower1.png")
  private var currentWave: Wave = null
  private var currentEnemy: Enemies = null
  private var currentTime = System.currentTimeMillis()
  private var projectiles = List[Projectile]()
  towerImage.relocate(this.pos.x - 60.0, this.pos.y - 60.0)
  group.getChildren.add(towerImage)
  def setWave(wave: Wave) = {
    this.currentWave = wave
  }
  def setEnemy() = {
  if(this.currentWave != null)  {
      if (this.currentWave.getEnemies.nonEmpty) this.currentEnemy = {
       val ans = this.currentWave.getEnemies.find(x => x.pos.distance(this.pos) <= this.shootRange && x.isAlive)
        if(ans.nonEmpty) ans.get
        else null
      }
    }
  }
  def canShoot: Boolean = this.currentEnemy != null && this.currentEnemy.pos.distance(this.pos) <= this.shootRange && this.currentEnemy.canBeShooted
  def shoot() = {
    this.projectiles = this.projectiles ++ List(Projectile(this.pos, 2.0, 2.0, this.currentEnemy, this.damage))
  }
  def update() = {
      this.setWave(this.waveManager.getWave)
      this.setEnemy()
      if(this.currentEnemy != null && System.currentTimeMillis() - this.currentTime > this.shootRate) {
       this.shoot()
       this.currentTime = System.currentTimeMillis()
     }
     for(projectile <- this.projectiles) if(!projectile.stopUpdate) projectile.update(this.group)
  }
}
object Tower {
 def apply(pos: Pos, damage: Int, shootRange: Double, shootRate: Int, group: Group, waveManager: WaveManager) = new Tower(pos, damage, shootRange, shootRate, group, waveManager)
}
