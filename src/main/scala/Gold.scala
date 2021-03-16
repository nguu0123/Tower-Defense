class Gold(startingGold: Int) {
  var currentGold = startingGold
  def canBuild(goldNeeded: Int): Boolean = this.currentGold >= goldNeeded
  def +(another: Gold) =  this.currentGold + another.currentGold
  def -(another: Gold) =  this.currentGold - another.currentGold
}
object Gold {
  def apply(startingGold: Int) = new Gold(startingGold)
}
