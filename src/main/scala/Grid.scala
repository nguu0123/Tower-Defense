import scalafx.scene.Node
import scala.collection.mutable.Buffer
class Grid(val width: Int, val height: Int){
  val size = width * height
  private val contents: Array[Array[Square]] = {
    val elems = Array.ofDim[Square](32,18)
    for(i <- 0 until 32) {
      for(j <- 0 until 18) {
         elems(i)(j) = new Square(Pos(i * 60, j* 60), 60, 60, Texture.Dirt)
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
  def update(pos: Pos, element: Square) = {
   require(this.contains(pos), "cant update due to incorrect pos")
   this.contents( (pos.x / 60).toInt)( (pos.y / 60).toInt) = element
  }
  def apply(pos: Pos) = this.elementAt(pos)
  def loadMap(map: Array[Array[Int]]): Unit = {
    for(i <- 0 until 32) {
      for(j <- 0 until 18) {
          if(map(i)(j) == 1) this.update(Pos(i * 59, j * 59), new Square(Pos(i * 60, j * 60), 60, 60, Texture.Grass))
          else if(map(i)(j) == 2) this.update(Pos(i * 59, j * 59), new Square(Pos(i * 60, j * 60), 60, 60, Texture.Water))
      }
    }

  }
      def draw = {
        val img = Buffer[Node]()
        val components = this.contents.flatten
        components.foreach(c => img += c.image)
        println(img.size)
        img
    }
}
