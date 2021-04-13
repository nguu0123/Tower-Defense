package Utils


class Health(var currentHealth: Int, val maxHealth: Int) {

  def update(damage: Int) = {
    this.currentHealth -= damage
  }
  def isDead: Boolean = this.currentHealth <= 0
  def percent = this.currentHealth * 1.0/ this.maxHealth
}
object Health {
  def apply(currentHealth:Int, maxHealth :Int): Health = new Health(currentHealth,maxHealth)
}
