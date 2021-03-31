import scalafx.scene.Group
class Player(var gold: Gold, val grid: Grid, val health: Health, group: Group, waveManager: WaveManager) {
   private var towers = Array[Tower]()
   def addTower(tower: Tower) = {
      tower.build()
      this.towers = this.towers ++ Array(tower)
   }
   def canBuild(pos: Pos): Boolean = {
     grid.getTexture(pos).buildable && grid.getTexture(pos - Pos(60, 0)).buildable && grid.getTexture(pos - Pos(0, 60)).buildable && grid.getTexture(pos - Pos(60, 60)).buildable
   }
  def setAllTowers(towers: Array[Tower]) = {
    this.towers = towers
  }
  def buildTower(pos: Pos) = {
    if(this.gold.canBuild(Gold(100))) {
       this.addTower((Tower(Pos(pos.x, pos.y), 10, 200.0, 500, group, waveManager)) )
       this.grid.setNotBuildable(pos)
       this.grid.setNotBuildable(pos - Pos(60, 0))
       this.grid.setNotBuildable(pos - Pos(0, 60))
       this.grid.setNotBuildable(pos - Pos(60, 60))
       this.gold = this.gold - Gold(100)
    }
  }
  def update() = {
   for(tower <- this.towers) {
      tower.update()
   }
  }
}
