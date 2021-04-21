package GameComponent

import Utils.{Gold, Pos}
import scalafx.scene.Group
class GoldTower(towerNumber: Int, goldNeeded: Gold, pos: Pos, val giveGold: Int, group: Group, player: Player, towerFile: String) extends Tower(towerNumber,goldNeeded, pos, group, player, towerFile) {
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
   def createTower(pos: Pos, group: Group, player: Player) = new GoldTower(2, Gold(50), pos, 5000, group, player, "file:src/res/Tower2.png")
}

