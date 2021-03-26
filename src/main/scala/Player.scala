class Player(val gold: Gold, val grid: Grid, val health: Health) {
   private var towers = Array[Tower]()
   def addTower(tower: Tower) = {
      this.towers = this.towers ++ Array(tower)
   }
  def setAllTowers(towers: Array[Tower]) = {
    this.towers = towers
  }
  def update() = {
   for(tower <- this.towers) {
      tower.update()
   }
  }
}
