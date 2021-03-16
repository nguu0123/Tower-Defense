import scalafx.scene.Group
class Wave(group: Group) {
 private var enemies = List[Enemies]()
 def addEnemy(enemies: Enemies) = {
   this.enemies = this.enemies ++ List(enemies)
 }
 def update(): Unit = {
  for(enemy <- this.enemies) {
      if(!enemy.stopUpdate && !enemy.reachGoal) enemy.update(group)
      if(enemy.stopUpdate || enemy.reachGoal) enemy.remove(group)
  }
 }
}
