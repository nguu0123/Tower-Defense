import scalafx.application.JFXApp
object TowerDefense extends JFXApp {
val game = new Game
val gameGUI = new GameGui(game)
stage = new JFXApp.PrimaryStage {
    title.value = "AttackTower Defense"
    resizable = false
     scene = gameGUI

}


}


