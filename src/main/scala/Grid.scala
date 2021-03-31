import scalafx.scene.Node
import scalafx.scene.canvas.GraphicsContext

import scala.collection.mutable.Buffer
class Grid(val width: Int, val height: Int){
  val size = width * height
  val xWidth = width / 60
  val yHeight = height / 60
  private val contents: Array[Array[Square]] = {
    val elems = Array.ofDim[Square](xWidth,yHeight)
    for(i <- 0 until xWidth) {
      for(j <- 0 until yHeight) {
         elems(i)(j) = new Square(Pos(i * 60, j* 60), 60, 60, Texture.Dirt, true)
      }
    }
    elems
  }
  def info() = {
       for(i <- this.contents)
         for(j <- i) println(j)
  }
  private def contains(x: Double, y: Double): Boolean = (x >= 0 && x < width && y >= 0 && y < height)
  def contains(pos: Pos): Boolean = this.contains(pos.x, pos.y)
  def elementAt(pos: Pos): Square = {
    require(this.contains(pos), "there is no square there")
    this.contents( (pos.x / 60).toInt)( (pos.y / 60).toInt)
  }
  def getTexture(pos: Pos): Texture = {
     if(!this.contains(pos)) Texture.Nothing
     else this.elementAt(pos).texture
  }
  def update(pos: Pos, element: Square) = {
   require(this.contains(pos), "cant update due to incorrect pos")
   this.contents( (pos.x / 60).toInt)( (pos.y / 60).toInt) = element
  }
  def apply(pos: Pos) = this.elementAt(pos)
  def loadMap(map: Array[Array[Int]]): Unit = {
    for(i <- 0 until xWidth) {
      for(j <- 0 until yHeight) {
          if(map(i)(j) == 1) this.update(Pos(i * 60, j * 60), new Square(Pos(i * 60, j * 60), 60, 60, Texture.Grass, false))
          else if(map(i)(j) == 2) this.update(Pos(i * 60, j * 60), new Square(Pos(i * 60, j * 60), 60, 60, Texture.Water, false))
      }
    }

  }
      def draw(gc: GraphicsContext) = {
        val components = this.contents.flatten
        for(square <- components) gc.drawImage(square.image, square.pos.x, square.pos.y, square.width, square.height)
        gc
    }
  def canBuildAt(pos: Pos): Boolean = {
   if(this.contains(pos))   this.elementAt(pos).buildable
   else false
  }
  def setNotBuildable(pos: Pos) = {
     require(this.contains(pos))
     this.contents( (pos.x / 60).toInt)( (pos.y / 60).toInt).buildable = false
  }
  def setBuildable(pos: Pos) = {
     require(this.contains(pos))
     this.contents( (pos.x / 60).toInt)( (pos.y / 60).toInt).buildable = true
  }
}
