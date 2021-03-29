class Player(var gold: Gold, val grid: Grid, val health: Health) {
   private var towers = Array[Tower]()
   def addTower(tower: Tower) = {
      tower.build()
      this.towers = this.towers ++ Array(tower)
   }
  def setAllTowers(towers: Array[Tower]) = {
    this.towers = towers
  }
  def buildTower(tower: Tower) = {
    if(this.gold.canBuild(tower.goldNeeded)) {
       this.addTower(tower)
       this.gold = this.gold - tower.goldNeeded
    }
  }
  def update() = {
   for(tower <- this.towers) {
      tower.update()
   }
  }
}
