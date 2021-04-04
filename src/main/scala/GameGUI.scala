import scalafx.scene.Scene
import scalafx.scene.Group
class GameGui(game: Game) extends Scene {
    val guideMenu = new GuideMenu(this)
     guideMenu.nextButton.onAction = event => guideMenu.next()
    guideMenu.backButton.onAction = event => guideMenu.back()

    val mainMenu = new MainMenu {
        playButton.onAction = (event) => toGame()
         guideButton.onAction = event => toGuide()
    }
  def mainLoop = () => {
        game.update()
        this.root = game.root
    }
    val ticker = new Ticker(mainLoop)


    this.root = mainMenu
    def toGame() = {
        this.root = game.root
        ticker.start()
    }
    def toMenu() = {
        ticker.stop()
        this.root = mainMenu
    }
   def toGuide() = {
     this.root = guideMenu.content
   }
}