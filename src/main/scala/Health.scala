
class Health(val maxHealth: Int) {
  var currentHealth = maxHealth
  def update(damage: Int) = {
    this.currentHealth -= damage
  }
  def isDead: Boolean = this.currentHealth <= 0
  def percent = this.currentHealth * 1.0/ this.maxHealth
}
object Health {
  def apply(maxHealth :Int): Health = new Health(maxHealth)
}
