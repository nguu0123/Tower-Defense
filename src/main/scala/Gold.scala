class Gold(startingGold: Int) {
  var currentGold = startingGold
  def canBuild(another: Gold): Boolean = this.currentGold >= another.currentGold
  def +(another: Gold): Gold =  Gold(this.currentGold + another.currentGold)
  def -(another: Gold): Gold =  Gold(this.currentGold - another.currentGold)
}
object Gold {
  def apply(startingGold: Int) = new Gold(startingGold)
}
