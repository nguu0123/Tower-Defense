import scalafx.scene.Group
class Wave( group: Group,  numberOfEnemies: Int,  grid: Grid) {
 private var enemies = List[Enemies]()
 private var currentTime = System.currentTimeMillis()
 private var enemiesSpawned = 0
 def getEnemies = this.enemies
 def addEnemy(enemies: Enemies) = {
   this.enemies = this.enemies ++ List(enemies)
 }
 def spawn(grid: Grid) = {
  val newEnemy = Enemies(Pos(0,0), Velocity(Direction.Down, 1), Health(100), grid)
  this.addEnemy(newEnemy)
 }
 def update(): Unit = {
  for(enemy <- this.enemies) {
      if(!enemy.stopUpdate && !enemy.reachGoal) enemy.update(group)
      if(enemy.stopUpdate || enemy.reachGoal) enemy.remove(group)
  }
    if(System.currentTimeMillis() - this.currentTime > 1000 && enemiesSpawned < numberOfEnemies) {
     this.spawn(grid)
     this.currentTime = System.currentTimeMillis()
     enemiesSpawned += 1
    }
 }
}
