import scalafx.scene.Group
class Wave( group: Group,  numberOfEnemies: Int,  grid: Grid, spawnRate: Int, player: Player) {
 private var enemies = List[Enemies]()
 private var lastUpdate = System.currentTimeMillis()
 private var havePassed = 0f
 private var enemiesSpawned = 0
 private var nextSpawn = System.currentTimeMillis()
 private var havePaused = false
 private var goldEarned = Gold(0)
 private var damageDealt = 0
 def getGoldEarned = this.goldEarned
 def getDamageDealt = this.damageDealt
 def getEnemies = this.enemies
 def isCompleted = this.numberOfEnemies == this.enemiesSpawned && this.enemies.forall( enemy => enemy.stopUpdate || enemy.reachGoal)
 def addEnemy(enemies: Enemies) = {
   this.enemies = this.enemies ++ List(enemies)
 }
 def spawn() = {
  val newEnemy = Enemies(Pos(0,300), Velocity(Direction.Down, 1.5), Health(80,80), this.grid, Gold(10))
  this.havePassed = 0
  this.lastUpdate = System.currentTimeMillis()
  this.addEnemy(newEnemy)
 }
 def update(): Unit = {
  for(enemy <- this.enemies) {
      if(!enemy.stopUpdate && !enemy.reachGoal) enemy.update(group)
      if(enemy.stopUpdate || enemy.reachGoal) {
        enemy.remove(group)
        enemy.removeHealthBar(group)
        if(enemy.canGiveGold ) {
          this.player.gold = this.player.gold + enemy.gold
          this.goldEarned = this.goldEarned + enemy.gold
          enemy.canGiveGold = false
        }
        if(enemy.canDamage && enemy.reachGoal) {
          this.player.health.update(1)
          this.damageDealt += 1
          enemy.canDamage = false
        }
      }
  }
  this.havePassed += System.currentTimeMillis() - this.lastUpdate
  this.lastUpdate = System.currentTimeMillis()
    if(this.havePassed > spawnRate && enemiesSpawned < numberOfEnemies) {
     this.spawn()
     enemiesSpawned += 1
    }
    if(numberOfEnemies == enemiesSpawned) {
     this.havePassed = 0
    }
 }
  def updateTime() = {
   if(havePaused) {
     this.havePassed += System.currentTimeMillis() - this.lastUpdate
     this.havePaused = true
   }
   this.lastUpdate = System.currentTimeMillis()
  }
  def deleteWave() = {
    for(enemy <- this.enemies) {
        enemy.remove(group)
        enemy.removeHealthBar(group)
     }
  }
}

