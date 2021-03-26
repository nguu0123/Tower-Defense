import scalafx.scene.Group
class Wave( group: Group,  numberOfEnemies: Int,  grid: Grid, spawnRate: Int) {
 private var enemies = List[Enemies]()
 private var currentTime = System.currentTimeMillis()
 private var enemiesSpawned = 0
 def getEnemies = this.enemies
 def isCompleted = this.numberOfEnemies == this.enemiesSpawned && this.enemies.forall( enemy => enemy.stopUpdate || enemy.reachGoal)
 def addEnemy(enemies: Enemies) = {
   this.enemies = this.enemies ++ List(enemies)
 }
 def spawn() = {
  val newEnemy = Enemies(Pos(0,0), Velocity(Direction.Down, 1), Health(100), this.grid)
    this.currentTime = System.currentTimeMillis()
  this.addEnemy(newEnemy)
 }
 def update(): Unit = {
  for(enemy <- this.enemies) {
      if(!enemy.stopUpdate && !enemy.reachGoal) enemy.update(group)
      if(enemy.stopUpdate || enemy.reachGoal) enemy.remove(group)
  }
    if(System.currentTimeMillis() - this.currentTime > spawnRate && enemiesSpawned < numberOfEnemies) {
     this.spawn()
     enemiesSpawned += 1
    }
 }
}

