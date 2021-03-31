import scalafx.scene.Group
class Player(var gold: Gold, val grid: Grid, val health: Health, group: Group, waveManager: WaveManager) {
   private var towers = Array[Tower]()
   def addTower(tower: Tower) = {
      tower.build()
      this.towers = this.towers ++ Array(tower)
   }
   def canBuild(pos: Pos): Boolean = {
     grid.canBuildAt(pos) && grid.canBuildAt(pos - Pos(60, 0)) && grid.canBuildAt(pos - Pos(0, 60)) && grid.canBuildAt(pos - Pos(60, 60))
   }
  def setAllTowers(towers: Array[Tower]) = {
    this.towers = towers
  }
  def buildTower(pos: Pos) = {
    if(this.gold.canBuild(Gold(100))) {
       val buildPos = pos.approximate
       this.addTower((Tower(buildPos, 10, 200.0, 500, group, waveManager, this)) )
       this.grid.setNotBuildable(buildPos)
       this.grid.setNotBuildable(buildPos - Pos(60, 0))
       this.grid.setNotBuildable(buildPos - Pos(0, 60))
       this.grid.setNotBuildable(buildPos - Pos(60, 60))
       this.gold = this.gold - Gold(100)
    }
  }
  def deleteTower(tower: Tower) = {
     this.grid.setBuildable(tower.pos)
     this.grid.setBuildable(tower.pos - Pos(60, 0))
     this.grid.setBuildable(tower.pos - Pos(0, 60))
     this.grid.setBuildable(tower.pos - Pos(60, 60))
     this.gold = this.gold + tower.goldNeeded * 0.7
     tower.destroy()
     this.towers = this.towers.filter(tower => !tower.isDestroyed)
  }
  def update() = {
   for(tower <- this.towers) {
      tower.update()
   }
  }
}
