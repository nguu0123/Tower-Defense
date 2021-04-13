package GUI

import GameComponent.{Player, FileManager}
import scalafx.scene.Group
import scalafx.scene.control.Label
import scalafx.scene.layout.VBox
import scalafx.scene.paint.Color
import scalafx.scene.text.Font
class GoldSystem(var player: Player, VBox: VBox, font: Font) {
 val moneyDisplay = new Group(FileManager.createImageView("file:src/res/money.png"))
 val currentGold = new Label()
 moneyDisplay.getChildren.add(currentGold)
 currentGold.setText(player.gold.currentGold.toString)
 currentGold.setFont(font)
 currentGold.setTextFill(Color.web("#ffff3f"))
 currentGold.relocate(100,20)
 VBox.getChildren.add(moneyDisplay)

  def update() = {
     currentGold.setText(this.player.gold.currentGold.toString)
  }
  def setPlayer(player: Player) = {
   this.player = player
  }
}
