import scala.swing._
import java.awt.Color
import javax.imageio.ImageIO
import java.io.File

object SimpleGraphics extends SimpleSwingApplication {

  def top = new MainFrame {
    title    = "Ball"
    contents = new BallPanel
    size     = new Dimension(1920, 1080)
  }

  class BallPanel extends Panel {

    override def paintComponent(g : Graphics2D) = {
      g.setColor(Color.blue)
      val example = ImageIO.read(new File("src/res/Dirt.png"))
      g.drawImage(example, 0 , 0, null)
      g.drawImage(example, 0, 60, null)
      g.drawImage(example, 60 , 60, null)
      g.drawImage(example, 60, 0, null)
      g.setColor(Color.black)
    }
  }
}