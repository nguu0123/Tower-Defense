package GameComponent

import Utils.{Gold, Health, Pos}
import scalafx.scene.Group
class Player(var gold: Gold, var health: Health) {
   var stopGame = 0
   var currentMapPlaying = 1
   /** Array is used as I think there will be more tower was built than tower was destroyed.
    *  Array is great choice when I need to concat and foreach a lot */
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
  def setTowers(towers: Array[Tower]) = {
   this.towers.foreach(tower => tower.destroy())
   this.towers = towers
  }
   def getTower = this.towers
   def addTower(tower: Tower) = {
      tower.build()
      for(i <- 0 to 1) {
         for(j <- 0 to 1) {
           grid.setNotBuildable(tower.pos - Pos(i * 60, j * 60))
         }
       }
      this.towers = this.towers ++ Array(tower)
   }
   def canBuild(pos: Pos): Boolean = {
     grid.canBuildAt(pos) && grid.canBuildAt(pos - Pos(60, 0)) && grid.canBuildAt(pos - Pos(0, 60)) && grid.canBuildAt(pos - Pos(60, 60))
   }
  def addTowerAt(towerCode: Int, pos: Pos) = {
     if     (towerCode == 1) {
       this.addTower(AttackTower.createFireTower(pos, group, this.waveManager, this))
     }
     else if(towerCode == 0) {
       this.addTower(AttackTower.createRockTower(pos, group, this.waveManager, this))
     }
     else if(towerCode == 2) {
       this.addTower(GoldTower.createTower(pos, group, this))
     }
  }
  def buildTower(towerChosen:Int, pos: Pos) = {
    if(towerChosen == 1 && this.gold.canBuild(Gold(150))) {
       val buildPos = pos.approximate
       this.addTower((AttackTower.createFireTower(buildPos, group, this.waveManager, this)) )
       this.gold = this.gold - Gold(150)
    }
    else if(towerChosen == 0 && this.gold.canBuild(Gold(100))) {
       val buildPos = pos.approximate
       this.addTower((AttackTower.createRockTower(buildPos, group, this.waveManager, this)) )
       this.gold = this.gold - Gold(100)
    }
    else if(towerChosen == 2 && this.gold.canBuild(Gold(50))){
      val buildPos = pos.approximate
      this.addTower((GoldTower.createTower(buildPos, group, this)) )
      this.gold = this.gold - Gold(50)
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
    this.towers.foreach(tower => tower.update())
  }
  def updateTime() = {
    this.towers.foreach(tower => tower.updateTime())
  }
  def restart() = {
    this.towers.foreach(tower => this.deleteTower(tower))
    this.gold = Gold(400)
    this.health = Health(20, 20)
  }
}
