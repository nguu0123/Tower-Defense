package GUI

import Utils._
import GameComponent._
import scalafx.Includes._
import scalafx.geometry.Insets
import scalafx.scene.Group
import scalafx.scene.canvas.Canvas
import scalafx.scene.layout._
import scalafx.scene.paint.Color
import scalafx.scene.text.Font
class Game {
var backToMenu = false
var gameLose = false
var gameWon = false
val grid = new Grid(1200, 720)
/** gameGroup for storing game components (tower, enemy, projectile) */
val gameGroup = new Group()
/** root will be a borderpane to easily putting the game in the middle and the in game menu on the right */
val root = new BorderPane()
/** canvas is used to draw the map */
val canvas = new Canvas(1200, 720)
/** in game menu will be a VBOx as every components in this menu will be put in vertical comlumn */
val ingameMenu = new VBox {
  spacing = 30
  padding = Insets(40, 0, 40,0)
}
ingameMenu.setPrefWidth(300)
ingameMenu.setPrefHeight(720)
ingameMenu.setBackground(new Background(Array(new BackgroundFill(Color.Black, CornerRadii.Empty, Insets.Empty) ) ) )
val shop0 = FileManager.createImageView("file:src/res/Shop1.png")
val shop1 = FileManager.createImageView("file:src/res/Shop2.png")
val shop2 = FileManager.createImageView("file:src/res/Shop3.png")
val g = canvas.graphicsContext2D
FileManager.loadMap(grid, 1)
grid.draw(g)
gameGroup.getChildren.add(canvas)
var waveManager = new WaveManager(5, 5, 0)
this.waveManager.setGrid(grid)
this.waveManager.setGroup(gameGroup)
var player = new Player(Gold(1000), Health(20,20))
this.player.setGrid(grid)
this.player.setGroup(gameGroup)
this.player.setWaveManager(this.waveManager)
this.waveManager.setPlayer(this.player)
val font = Font.loadFont("file:src/res/font.ttf", 40)
val waveSystem = new WaveSystem(this.waveManager, font, ingameMenu)
val pauseAndContinueButtons = new HBox {
  spacing = 20
}
val pauseButton = FileManager.createImageView("file:src/res/pauseButton.png")
val continueButton = FileManager.createImageView("file:src/res/continueButton.png")
val saveButton = FileManager.createImageView("file:src/res/saveButton.png")
pauseButton.onMouseClicked = event => {
        this.player.stopGame = 1
    }
continueButton.onMouseClicked = event => {
      this.player.stopGame = 0
    }
saveButton.onMouseClicked = event => {
   FileManager.saveGame("src/data/saveGame.json", this)
   this.backToMenu = true
}
pauseAndContinueButtons.getChildren.addAll(saveButton, continueButton, pauseButton)
ingameMenu.getChildren.add(pauseAndContinueButtons)
val goldSystem = new GoldSystem(this.player, ingameMenu, font)
val playerHealth = new PlayerHealth(this.player, ingameMenu)
val shopImages = new HBox {
   padding = Insets(0, 20, 0, 20)
   spacing = 30
}
shopImages.getChildren.addAll(shop0, shop1, shop2)
val shopImage = Vector(shop0, shop1, shop2)
ingameMenu.getChildren.add(shopImages)
root.setCenter(gameGroup)
root.setRight(ingameMenu)
val inputManager = new InputManager(this.player)
val castle = FileManager.createImageView("file:src/res/Castle.png")
castle.relocate(1020,120)
gameGroup.getChildren.add(castle)
this.waveManager.spawnWave()
var nextSecond = System.currentTimeMillis() + 1000
var frameLastSecond = 0
var frameCurrentSecond = 0
  def restart() = {
    this.player.restart()
    this.waveManager.restart()
  }
  def update() = {
      inputManager.handleInput(shopImage, gameGroup)
      goldSystem.update()
      if(!this.player.health.isDead && this.player.stopGame == 0){
         waveSystem.update()
         playerHealth.update()
         this.waveManager.update()
         this.player.update()
         if(this.waveManager.levelCompleted) {
           this.gameWon = true
        }
       }
      else if (this.player.stopGame == 1) {
        this.waveManager.updateTime()
        this.player.updateTime()
      }
      else if (this.player.health.isDead) {
          this.gameLose = true
          playerHealth.update()
        }
    val currentSecond = System.currentTimeMillis()
       if(currentSecond > nextSecond) {
         nextSecond += 1000
         frameLastSecond = frameCurrentSecond
         frameCurrentSecond = 0
       }
       frameCurrentSecond += 1
       println(frameLastSecond)
  }
  def drawNewMap(mapNumer: Int) = {
    this.player.currentMapPlaying = mapNumer
       mapNumer match {
        case 1 => this.waveManager.setSpawnLoc(Pos(0, 300))
        case 2 => this.waveManager.setSpawnLoc(Pos(0, 540))
        case 3 => this.waveManager.setSpawnLoc(Pos(0, 120))
      }
    FileManager.loadMap(grid, this.player.currentMapPlaying)
    for(i <- 0 to 2) {
       for(j <- 0 to 2) {
          this.grid.setNotBuildable( Pos(1020,120) + Pos(i * 60, j* 60))
    }
}
    grid.draw(g)
  }
  def newGame() = {
    waveSystem.setWaveManager(this.waveManager)
    goldSystem.setPlayer(this.player)
    playerHealth.setPlayer(this.player)
    inputManager.setPlayer(this.player)
  }
}
