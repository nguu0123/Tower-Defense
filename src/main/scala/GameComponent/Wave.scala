package GameComponent

import Utils.Pos
import scalafx.scene.Group
class Wave( group: Group, numberOfEnemies: Int, grid: Grid, spawnRate: Int, player: Player, spawnLoc: Pos) {
 private val ran = scala.util.Random
 private var enemies = List[Enemies]()
 /** I used havePassed and lastUpdate to compute how much time is actually passed in my game */
 private var lastUpdate = System.currentTimeMillis()
 private var havePassed = 0f
 private var enemiesSpawned = 0
 private var nextSpawn = System.currentTimeMillis()
 private var havePaused = false
 /** damageDealt is used to compute the health of the player before this wave if the game is saved in this wave */
 private var damageDealt = 0

 def getDamageDealt = this.damageDealt
 def getEnemies = this.enemies
 def isCompleted = this.numberOfEnemies == this.enemiesSpawned && this.enemies.forall( enemy => enemy.stopUpdate || enemy.reachGoal)
 def addEnemy(enemies: Enemies) = {
   this.enemies = this.enemies ++ List(enemies)
 }
 def spawn() = {
  val newEnemy = {
  if(this.ran.nextInt(20) == 9)
    Enemies.createBlackMonster(this.spawnLoc, this.grid)
  else if (this.ran.nextInt(2) == 0)
    Enemies.createBrainlessMonster(this.spawnLoc, this.grid)
  else
    Enemies.createGreenMonster(this.spawnLoc, this.grid)
  }
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

