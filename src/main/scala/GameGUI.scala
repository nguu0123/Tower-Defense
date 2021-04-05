import scalafx.scene.Scene
class GameGui(game: Game) extends Scene {
    val guideMenu = new GuideMenu(this)
     guideMenu.nextButton.onAction = event => guideMenu.next()
    guideMenu.backButton.onAction = event => guideMenu.back()

   val mainMenu = new MainMenu
  mainMenu.playButton.onAction = (event) => this.toGame()
  mainMenu.guideButton.onAction = event => this.toGuide()
  mainMenu.loadButton.onAction = event => this.loadGame()
  def mainLoop = {
        if(game.backToMenu) {
          this.ticker.stop()
          this.toMenu()
        }
        game.update()
    }
    val ticker = new Ticker(mainLoop)
    this.root = mainMenu
    def toGame() = {
        this.game.restart()
        this.root = game.root
        game.backToMenu = false
        ticker.start()
    }
    def toMenu() = {
        this.game.restart()
        this.root = mainMenu
    }
   def toGuide() = {
     this.root = guideMenu.content
   }
  def loadGame() = {
    FileManager.loadGame("src/data/saveGame.json", game)
    game.newGame()
    this.root = game.root
    game.backToMenu = false
    ticker.start()
  }
}