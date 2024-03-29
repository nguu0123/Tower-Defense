package GUI

import GameComponent.{Player, FileManager}
import scalafx.scene.Group
import scalafx.scene.layout.VBox
class PlayerHealth(var player: Player, VBox: VBox)  {
  val healthBar = new Group()
  val healthIcon       = FileManager.createImageView("file:src/res/healthicon.png")
  val healthBackground = FileManager.createImageView("file:src/res/healthbackground.png")
  val healthPercentage = FileManager.createImageView("file:src/res/healthpercentage.png")
  healthBar.getChildren.add(healthIcon)
  healthBackground.relocate(92,2)
  healthPercentage.relocate(100,20)
  healthBar.getChildren.add(healthBackground)
  healthBar.getChildren.add(healthPercentage)
  VBox.getChildren.add(healthBar)

  def update() = {
    if(this.player.health.percent > 0.04) healthPercentage.setFitWidth(278 * this.player.health.percent)
    else  healthPercentage.setFitWidth(278 * 0.001)
  }
  def setPlayer(player: Player) = {
   this.player = player
  }
}
