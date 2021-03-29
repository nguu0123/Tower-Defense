import scalafx.scene.Group
import scalafx.scene.layout.VBox
import scalafx.scene.control.Label
import scalafx.scene.text.Font
class WaveSystem(waveManager: WaveManager, font: Font, VBox: VBox){
  val waveSystem = new Group()
  val wave = new Label()
  wave.setFont(font)
  VBox.getChildren.add(wave)
  def update() = {
    wave.setText(s"WAVE ${waveManager.getWaveNumber}")
  }
}
