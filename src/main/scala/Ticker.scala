import javafx.animation.AnimationTimer

//This class calls function given as a parameter repeatedly.
class Ticker(function: () => Unit) extends AnimationTimer {
    var previousUpdate = System.nanoTime()
    var lag = 0.0
    val updatesPerSecond: Double = 120 // Not real time, but granularity.
    val nsPerUpdate = (1.0/updatesPerSecond)*1000000000 // How much lag value a logic update eats up
    //Override from animation timer
    override def handle(now: Long): Unit = {
      if(now - previousUpdate >= nsPerUpdate) {
          function()
          previousUpdate = now
      }
    }


}