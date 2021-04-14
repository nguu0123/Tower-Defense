package GameComponent

import Utils.{Gold, Pos}
import scalafx.scene.Group
class AttackTower(towerNumber: Int,goldNeeded: Gold, pos: Pos, val damage: Int, val shootRange: Double, val shootRate: Int, group: Group, waveManager: WaveManager, player: Player, towerFile: String, projectileFile: String) extends Tower(towerNumber,goldNeeded, pos, group, waveManager, player, towerFile) {
  private var currentWave: Wave = null
  private var currentEnemy: Enemies = null
  /** using List as each tower usually shoot 1 or 2 shoot until the first shoot hit => List has performance that concat and deconstruct small and foreeach little */
  private var projectiles = List[Projectile]()

  def setWave(wave: Wave) = {
    this.currentWave = wave
  }
  def setEnemy() = {
  if(this.currentWave != null)  {
      if (this.currentWave.getEnemies.nonEmpty) this.currentEnemy = {
       val ans = this.currentWave.getEnemies.find(x => ( (x.center.distance(this.pos) <= this.shootRange) && (x.isAlive && !x.reachGoal) ) )
        if(ans.nonEmpty) ans.get
        else null
      }
    }
  }
  def shoot() = {
    this.projectiles = this.projectiles ++ List(Projectile(this.pos, 5.0, 2.0, this.currentEnemy, this.damage, projectileFile))
  }
  def destroy() = {
    this.isDestroyed = true
    group.getChildren.remove(this.towerImage)
    for(projectile <- this.projectiles) {
      projectile.remove(this.group)
    }

    group.getChildren.remove(this.button)
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
}
object AttackTower {
 def apply(towerNumber:Int, goldNeeded: Gold ,pos: Pos, damage: Int, shootRange: Double, shootRate: Int, group: Group, waveManager: WaveManager, player: Player, towerFile: String, projectileFile: String) = new AttackTower(towerNumber, goldNeeded, pos, damage, shootRange, shootRate, group, waveManager, player, towerFile, projectileFile)
 def createFireTower(pos: Pos, group: Group, waveManager: WaveManager, player: Player) = new AttackTower(1, Gold(150) ,pos, 10, 200.0, 500, group, waveManager, player, "file:src/res/Tower1.png" , "file:src/res/Fireball1.png")
 def createRockTower(pos: Pos, group: Group, waveManager: WaveManager, player: Player) = new AttackTower(0, Gold(100),pos, 20, 200.0, 1000, group, waveManager, player, "file:src/res/Tower0.png", "file:src/res/Rock.png")
}
