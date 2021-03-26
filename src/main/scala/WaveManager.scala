import scalafx.scene.Group
class WaveManager(group: Group, grid: Grid, totalWave: Int, minEnemiesPerWave: Int, startWave: Int){
  private var currentWave: Wave = null
  private var currentTime = System.currentTimeMillis()
  private var numberOfWave = startWave
  def getWave = this.currentWave
  def spawnWave() = {
   this.currentWave = new Wave(this.group, this.minEnemiesPerWave + 3 * this.numberOfWave, this.grid ,1000)
   this.numberOfWave += 1
  }
 def update(): Unit = {
    if(this.currentWave.isCompleted) this.spawnWave()
    else this.currentWave.update()
 }
 def levelCompleted: Boolean = this.numberOfWave == this.totalWave && this.currentWave.isCompleted
}
