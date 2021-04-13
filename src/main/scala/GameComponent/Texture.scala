package GameComponent

case class Texture(val filePath: String) {
  def ==(another: Texture): Boolean = this.filePath == another.filePath
}
object Texture {
 val Dirt:    Texture = new Texture("file:src/res/Dirt.png"){ override val toString = "Dirt"}
 val Grass:   Texture = new Texture("file:src/res/Grass.png") {override val toString = "Grass"}
 val Water:   Texture = new Texture("file:src/res/Water.png") {override val toString = "Water"}
 val Nothing: Texture = new Texture("Nothing")                {override val toString = "Nothing"}
}
