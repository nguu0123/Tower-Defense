package GUI

import GameComponent.FileManager
import scalafx.scene.Group
import scalafx.scene.control.Button
import scalafx.scene.image.Image
import scalafx.scene.layout._
class GuideMenu(gameGui: GameGui) {
  val pages = (0 to 3).map(index => FileManager.createImageView("file:src/res/guide" + index + ".png"))
  private var index = 0
  val backButton = new Button {
       prefWidth = 90
       prefHeight = 60
       background = new Background(
           Array(
               new BackgroundImage(
                   new Image("file:src/res/backButton.png"),
                   BackgroundRepeat.NoRepeat,
                   BackgroundRepeat.NoRepeat,
                   BackgroundPosition.Center,
                   new BackgroundSize(90, 60, true, true, true, true)
               )
           )
       )

   }
  val nextButton = new Button {
        prefWidth = 90
        prefHeight = 60
        background = new Background(
            Array(
                new BackgroundImage(
                    new Image("file:src/res/nextButton1.png"),
                    BackgroundRepeat.NoRepeat,
                    BackgroundRepeat.NoRepeat,
                    BackgroundPosition.Center,
                    new BackgroundSize(90, 60, true, true, true, true)
                )
            )
        )
    }

  backButton.relocate(1100,600)
  nextButton.relocate(1200,600)
  val content = new Group(pages(index), backButton, nextButton)

  def next() = {
    if(index < 3){
      this.content.getChildren.removeAll(pages(index), backButton, nextButton)
      index += 1
      this.content.getChildren.addAll(pages(index), backButton, nextButton)
    }
    else {
      gameGui.toMenu()
      this.content.getChildren.removeAll(pages(index), backButton, nextButton)
      index = 0
      this.content.getChildren.addAll(pages(index), backButton, nextButton)
    }
  }
  def back() = {
    if(index > 0){
      this.content.getChildren.removeAll(pages(index), backButton, nextButton)
      index -=1
      this.content.getChildren.addAll(pages(index), backButton, nextButton)
    }
    else {
     gameGui.toMenu()
    }
  }
}
