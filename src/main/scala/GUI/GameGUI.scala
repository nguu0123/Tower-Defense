package GUI

import GameComponent.FileManager
import scalafx.scene.Scene
class GameGui(game: Game) extends Scene {
  val gameOver = FileManager.createImageView("file:src/res/GAMEOVER.png")
  this.gameOver.relocate(400, 200)
  val gameWon = FileManager.createImageView("file:src/res/GAMEWON.png")
  this.gameWon.relocate(200, 100)

  val guideMenu = new GuideMenu(this)
  guideMenu.nextButton.onAction = (event) => guideMenu.next()
  guideMenu.backButton.onAction = (event) => guideMenu.back()

  val mainMenu = new MainMenu
  mainMenu.playButton.onAction  = (event) => this.toMapChoosing()
  mainMenu.guideButton.onAction = (event) => this.toGuide()
  mainMenu.loadButton.onAction  = (event) => try{
    this.loadGame()
  } catch {
    case e: Throwable => FileManager.showError("Load game failed", e)
  }
  
  val mapChoosingMenu = new MapChoosingMenu
  mapChoosingMenu.map1.onAction = (event) => try{
    this.toGame(1)
  }
  catch {
    case e: Throwable => FileManager.showError("Load map 1 failed", e)
  }
  mapChoosingMenu.map2.onAction = (event) => try{
    this.toGame(2)
  }
  catch {
    case e: Throwable => FileManager.showError("Load map 2 failed", e)
  }
  mapChoosingMenu.map3.onAction = (event) => try{
    this.toGame(3)
  }
  catch {
    case e: Throwable => FileManager.showError("Load map 3 failed", e)
  }

  def mainLoop() = {
       if(game.backToMenu) {
         this.ticker.stop()
         this.toMenu()
       }
       else if(game.gameLose) {
        this.ticker.stop()
        game.gameGroup.getChildren.add(this.gameOver)
        game.root.onMouseClicked = event => {
         game.gameLose = false
         this.toMenu()
        }
       }
       else if(game.gameWon) {
         this.ticker.stop()
         game.gameGroup.getChildren.add(this.gameWon)
        game.root.onMouseClicked = event => {
         game.gameWon = false
         this.toMenu()
        }
       }

       game.update()
    }
   val ticker = new Ticker(mainLoop())
   this.root = mainMenu

   def toGame(mapNumber: Int) = {
       this.game.drawNewMap(mapNumber)
       this.game.player.stopGame = 0
       this.game.restart()
       this.root = game.root
       game.backToMenu = false
       ticker.start()
   }
   def toMenu() = {
       game.gameGroup.getChildren.remove(this.gameOver)
       game.gameGroup.getChildren.remove(this.gameWon)
       game.root.onMouseClicked = null
       this.game.restart()
       this.root = mainMenu
   }
  def toMapChoosing() = {
    this.root = mapChoosingMenu
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