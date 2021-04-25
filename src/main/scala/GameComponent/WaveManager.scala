package GameComponent

import Utils.{Pos, Gold}
import scalafx.scene.Group
  class WaveManager(val totalWave: Int, val minEnemiesPerWave: Int, val startWave: Int){
  private var currentWave: Wave = null
  private var numberOfWave = startWave
  private var player: Player = null
  private var group: Group = null
  private var grid: Grid = null
  private var spawnLoc: Pos = null
  /** using previous gold and towers to store game progress of the player */
  private var previousPlayerGold = Gold(0)
  private var previousTowers = Array[Tower]()

  def setPlayer(player: Player) = {
     this.player = player
  }
  def setGroup(group: Group) = {
    this.group = group
  }
  def setGrid(grid: Grid) = {
   this.grid = grid
  }
  def setSpawnLoc(pos: Pos) = {
   this.spawnLoc = pos
  }
  def getWave       = this.currentWave
  def getWaveNumber = this.numberOfWave
  def getPlayerGold = this.previousPlayerGold
  def getTowers     = this.previousTowers
  def spawnWave() = {
   this.previousPlayerGold = this.player.gold
   this.previousTowers     = this.player.getTower
   this.currentWave        = new Wave(this.group, this.minEnemiesPerWave + 2 * this.numberOfWave, this.grid , 1000 - 100 * this.numberOfWave, this.player, this.spawnLoc)
   this.numberOfWave       += 1
  }
  def update(): Unit = {
    if(this.currentWave.isCompleted && this.numberOfWave < totalWave) {
      this.spawnWave()
    }
    else {
      this.currentWave.update()
    }
  }
  /** Like towers, when the game is paused. The time related variable should still be updated because if the game is paused and the wave's time is not updated,
   *  new monster will be spawned immediately when the game is resumed */
  def updateTime() = {
    this.currentWave.updateTime()
  }
  def restart() = {
   this.numberOfWave = 0
   this.currentWave.deleteWave()
   this.spawnWave()
  }
  def levelCompleted: Boolean = {
    this.numberOfWave == this.totalWave && this.currentWave.isCompleted
  }
}
