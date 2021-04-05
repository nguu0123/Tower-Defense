import scalafx.geometry.Insets
import scalafx.scene.Group
import scalafx.scene.layout.VBox
import scalafx.scene.control.Label
import scalafx.scene.text.Font
import scalafx.scene.paint.Color
class WaveSystem(var waveManager: WaveManager, font: Font, VBox: VBox){
  val waveSystem = new Group()
  val wave = new Label()
  var totalWave = this.waveManager.totalWave
  wave.setFont(font)
  wave.setTextFill(Color.web("#ffff3f"))
  wave.setPadding(Insets(0, 60, 0, 60))
  VBox.getChildren.add(wave)
  def update() = {
    wave.setText(s"WAVE ${this.waveManager.getWaveNumber} / ${this.totalWave}" )
  }
  def setWaveManager(waveManager: WaveManager) = {
   this.waveManager = waveManager
   this.totalWave = waveManager.totalWave
  }
}
