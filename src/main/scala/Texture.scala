
case class Texture(val filePath: String, val buildable: Boolean) {
  def ==(another: Texture): Boolean = this.filePath == another.filePath
}
object Texture {
 val Dirt: Texture =  new Texture("file:src/res/Dirt.png", false) { override val toString = "Dirt"}
 val Grass: Texture = new Texture("file:src/res/Grass.png", true) {override val toString = "Grass"}
 val Water: Texture = new Texture("file:src/res/Water.png", false) {override val toString = "Water"}
 val Nothing: Texture = new Texture("Nothing",false)               {override val toString = "Nothing"}
}
