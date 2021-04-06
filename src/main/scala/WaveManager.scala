import scalafx.scene.Group
class WaveManager(val totalWave: Int, val minEnemiesPerWave: Int, val startWave: Int){
  private var currentWave: Wave = null
  private var numberOfWave = startWave
  private var player: Player = null
  private var group: Group = null
  private var grid: Grid = null
  def setPlayer(player: Player) = {
     this.player = player
  }
  def setGroup(group: Group) = {
    this.group = group
  }
  def setGrid(grid: Grid) = {
   this.grid = grid
  }
  def getWave = this.currentWave
  def getWaveNumber = this.numberOfWave
  def spawnWave() = {
   this.currentWave = new Wave(this.group, this.minEnemiesPerWave + 3 * this.numberOfWave, this.grid ,1000, this.player)
   this.numberOfWave += 1
  }
 def update(): Unit = {
    if(this.currentWave.isCompleted && this.numberOfWave < totalWave) this.spawnWave()
    else this.currentWave.update()
 }
 def updateTime() = {
   this.currentWave.updateTime()
 }
 def restart() = {
  this.numberOfWave = 0
  this.currentWave.deleteWave()
  this.spawnWave()
 }
 def levelCompleted: Boolean = this.numberOfWave == this.totalWave && this.currentWave.isCompleted
}
