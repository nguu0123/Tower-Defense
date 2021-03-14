
class Health(val maxHealth: Int) {
  var currentHealth = maxHealth
  def update(damage: Int) = {
    this.currentHealth -= damage
  }
  def isDead: Boolean = this.currentHealth < 0
}
object Health {
  def apply(maxHealth :Int): Health = new Health(maxHealth)
}
