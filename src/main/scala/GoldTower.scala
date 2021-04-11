import scalafx.scene.image.{Image, ImageView}
import scala.util.control.Breaks._
import scalafx.scene.Group
class GoldTower(towerNumber: Int, goldNeeded: Gold, pos: Pos, val giveGold: Int, group: Group, waveManager: WaveManager, player: Player, towerFile: String) extends Tower(towerNumber,goldNeeded, pos, group, waveManager, player, towerFile) {
  var justBuilt = true
  def destroy() = {
    this.isDestroyed = true
    group.getChildren.remove(this.towerImage)
    group.getChildren.remove(this.button)
  }
  def update() = {
    this.havePassed += System.currentTimeMillis() - this.lastUpdate
    this.lastUpdate = System.currentTimeMillis()
    if(this.havePassed > this.giveGold) {
       player.gold += Gold(10)
       this.havePassed = 0
     }
  }
}
object  GoldTower {
   def createTower(pos: Pos, group: Group, waveManager: WaveManager, player: Player) = new GoldTower(1, Gold(50), pos, 5000, group, waveManager, player, "file:src/res/Tower2.png")
}

