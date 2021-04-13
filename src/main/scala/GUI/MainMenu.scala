package GUI

import javafx.scene.effect.ColorAdjust
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, Label}
import scalafx.scene.image.Image
import scalafx.scene.layout._
import scalafx.scene.paint.Color
import scalafx.scene.text.Font
class MainMenu extends VBox {
   val brightnessChange = new ColorAdjust()
   brightnessChange.setBrightness(0.1)
   this.setPrefWidth(1600)
   this.setPrefHeight(720)
   this.alignment = Pos.Center
   this.background = new Background(
        Array(
            new BackgroundImage(
                new Image("file:src/res/mainMenu.png"),
                BackgroundRepeat.NoRepeat,
                BackgroundRepeat.NoRepeat,
                BackgroundPosition.Center,
                new BackgroundSize(1600, 720, true, true, true, true)
            )
        )
    )
   val header = new Label("TOWER DEFENSE")
   header.padding = Insets(50, 25, 10, 25)
   header.setTextFill(Color.web("#ffff3f"))
   header.font =  Font.loadFont("file:src/res/font.ttf", 100)
   val buttonContainer = new VBox {
       spacing = 30
       alignment = Pos.Center
   }

   val playButton = new Button {
       prefWidth = 270
       prefHeight = 135
       background = new Background(
           Array(
               new BackgroundImage(
                   new Image("file:src/res/playButton.png"),
                   BackgroundRepeat.NoRepeat,
                   BackgroundRepeat.NoRepeat,
                   BackgroundPosition.Center,
                   new BackgroundSize(270, 135, true, true, true, true)
               )
           )
       )
   }

   val guideButton = new Button {
      prefWidth = 270
      prefHeight = 135
      background = new Background(
           Array(
               new BackgroundImage(
                   new Image("file:src/res/guideButton.png"),
                   BackgroundRepeat.NoRepeat,
                   BackgroundRepeat.NoRepeat,
                   BackgroundPosition.Center,
                   new BackgroundSize(270, 135, true, true, true, true)
               )
           )
       )
   }

   val loadButton = new Button {
       prefWidth = 270
       prefHeight = 135
       background =  new Background(
           Array(
               new BackgroundImage(
                   new Image("file:src/res/loadButton.png"),
                   BackgroundRepeat.NoRepeat,
                   BackgroundRepeat.NoRepeat,
                   BackgroundPosition.Center,
                   new BackgroundSize(270, 135, true, true, true, true)
               )
           )
       )

   }

   buttonContainer.children = Array(playButton, loadButton, guideButton)
   this.children = Array(header,buttonContainer)

}