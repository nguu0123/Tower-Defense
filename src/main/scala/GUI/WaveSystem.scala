package GUI

import scalafx.geometry.Insets
import scalafx.scene.control.Label
import scalafx.scene.layout.VBox
import scalafx.scene.paint.Color
import scalafx.scene.text.Font
import GameComponent.WaveManager
class WaveSystem(var waveManager: WaveManager, font: Font, VBox: VBox){
  val waveLabel = new Label()
  var totalWave = this.waveManager.totalWave
  waveLabel.setFont(font)
  waveLabel.setTextFill(Color.web("#ffff3f"))
  waveLabel.setPadding(Insets(0, 60, 0, 60))
  VBox.getChildren.add(waveLabel)
  def update() = {
    waveLabel.setText(s"WAVE ${this.waveManager.getWaveNumber} / ${this.totalWave}" )
  }
  def setWaveManager(waveManager: WaveManager) = {
   this.waveManager = waveManager
   this.totalWave = waveManager.totalWave
  }
}
