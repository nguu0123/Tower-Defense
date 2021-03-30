import scalafx.scene.Group
import scalafx.scene.layout.VBox
import scalafx.scene.control.Label
import scalafx.scene.text.Font
import scalafx.scene.paint.Color
class WaveSystem(waveManager: WaveManager, font: Font, VBox: VBox){
  val waveSystem = new Group()
  val wave = new Label()
  val totalWave = waveManager.totalWave
  wave.setFont(font)
  wave.setTextFill(Color.web("#ffff3f"));
  VBox.getChildren.add(wave)
  def update() = {
    wave.setText(s"WAVE ${waveManager.getWaveNumber} / $totalWave" )
  }
}
