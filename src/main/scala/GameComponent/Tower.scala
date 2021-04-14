package GameComponent

import Utils.{Gold, Pos}
import scalafx.scene.Group
import scalafx.scene.image.ImageView

abstract class Tower(val towerNumer: Int,  val goldNeeded: Gold, val pos: Pos, val group: Group, waveManager: WaveManager, player: Player, towerFile: String){
   var lastUpdate = System.currentTimeMillis()
   var havePassed:Long = 0
   var havePaused = false
   var towerImage: ImageView = null
   var clicked = 0
   val button = FileManager.createImageView("file:src/res/deleteButton.png")
   var isDestroyed = false

   def build() = {
     this.towerImage = FileManager.createImageView(towerFile)
     this.towerImage.relocate(this.pos.x - 60.0, this.pos.y - 60.0)
     this.button.relocate(this.pos.x + 30, this.pos.y - 60)
     group.getChildren.add(this.towerImage)
     this.towerImage.onMouseClicked = event => {
        if(clicked == 0) {
          group.getChildren.add(button)
          clicked = clicked ^ 1
        }
        else {
          group.getChildren.remove(button)
          clicked = clicked ^ 1
        }
       }
     this.button.onMouseClicked = event => {
       player.deleteTower(this)
       group.getChildren.remove(button)
     }
   }
  
    def destroy(): Unit
    def update(): Unit
    /** The towers has update time method because when the game is paused, the timeline is still updated but the time related variable of towers is not.
     *  That can result in continous shooting if the player paused exactly when the tower just shoot */
    def updateTime() = {
      if(!this.havePaused) {
        this.havePassed += System.currentTimeMillis() - this.lastUpdate
        this.havePaused = true
      }
      this.lastUpdate = System.currentTimeMillis()
   }
}


