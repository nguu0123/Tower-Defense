import javafx.animation.AnimationTimer

//This class calls function given as a parameter repeatedly.
class Ticker(function: => Unit) extends AnimationTimer {
    var previousUpdate = System.nanoTime()
    val updatesPerSecond: Double = 120
    val nsPerUpdate: Long = 8333333
    override def handle(now: Long): Unit = {
      if(now - previousUpdate >= nsPerUpdate) {
          previousUpdate = now
          function
      }
    }

}