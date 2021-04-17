package GUI

import scalafx.geometry.Pos
import scalafx.scene.control.{Button, Label}
import scalafx.scene.image.Image
import scalafx.scene.layout._
import scalafx.scene.paint.Color
import scalafx.scene.text.Font
class MapChoosingMenu extends VBox {
  this.setPrefWidth(1600)
  this.setPrefHeight(720)
  this.alignment = Pos.Center
  this.spacing = 100
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
  val header = new Label("MAP")
  header.setTextFill(Color.web("#ffff3f"))
  header.font =  Font.loadFont("file:src/res/font.ttf", 100)
  val mapSelector = new HBox {
      spacing = 50
      alignment = Pos.Center
  }
  val map1 = new Button {
       prefWidth = 300
       prefHeight = 300
       background = new Background(
           Array(
               new BackgroundImage(
                   new Image("file:src/res/map1.png"),
                   BackgroundRepeat.NoRepeat,
                   BackgroundRepeat.NoRepeat,
                   BackgroundPosition.Center,
                   new BackgroundSize(300, 300, true, true, true, true)
               )
           )
       )
   }
    val map2 = new Button {
      prefWidth = 300
      prefHeight = 300
      background = new Background(
          Array(
              new BackgroundImage(
                  new Image("file:src/res/map2.png"),
                  BackgroundRepeat.NoRepeat,
                  BackgroundRepeat.NoRepeat,
                  BackgroundPosition.Center,
                  new BackgroundSize(300, 300, true, true, true, true)
              )
          )
      )
   }
    val map3 = new Button {
      prefWidth = 300
      prefHeight = 300
      background = new Background(
          Array(
              new BackgroundImage(
                  new Image("file:src/res/map3.png"),
                  BackgroundRepeat.NoRepeat,
                  BackgroundRepeat.NoRepeat,
                  BackgroundPosition.Center,
                  new BackgroundSize(300, 300, true, true, true, true)
              )
           )
       )
   }

   mapSelector.children = Array(map1, map2, map3)
   this.children = Array(header, mapSelector)

}