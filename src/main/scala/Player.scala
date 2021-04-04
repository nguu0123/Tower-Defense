import scalafx.scene.Group
class Player(var gold: Gold, val health: Health) {
   var stopGame = 0
   private var towers = Array[Tower]()
   private var grid: Grid = null
   private var group: Group = null
   private var waveManager: WaveManager = null
   def setGrid(grid: Grid) = {
     this.grid = grid
   }
   def setGroup(group: Group) = {
     this.group = group
   }
  def setWaveManager(waveManager: WaveManager) = {
   this.waveManager = waveManager
  }
   def getTower = this.towers
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
       for(i <- 0 to 1) {
         for(j <- 0 to 1) {
           grid.setNotBuildable(pos - Pos(i * 60, j * 60))
         }
       }
       this.gold = this.gold - Gold(100)
    }
  }
  def deleteTower(tower: Tower) = {
       for(i <- 0 to 1) {
         for(j <- 0 to 1) {
           grid.setBuildable(tower.pos - Pos(i * 60, j * 60))
         }
       }
     this.gold = this.gold + tower.goldNeeded * 0.7
     tower.destroy()
     this.towers = this.towers.filter(tower => !tower.isDestroyed)
  }
  def update() = {
   for(tower <- this.towers) {
      tower.update()
   }
  }
  def updateTime() = {
    for(tower <- this.towers) {
       tower.updateTime()
    }
  }
}
