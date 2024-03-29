package GameComponent

import javafx.animation.AnimationTimer

class Ticker(function: => Unit) extends AnimationTimer {
  var previousUpdate = System.nanoTime()
  val nsPerUpdate: Long = 8333333
  override def handle(now: Long): Unit = {
    if(now - previousUpdate >= nsPerUpdate) {
        previousUpdate = now
        function
    }
  }

}