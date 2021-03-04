
class Health(val maxHealth: Int) {
  var currentHealth = maxHealth
  def update(damage: Int) = {
    this.currentHealth -= damage
  }
  def isDead: Boolean = this.currentHealth < 0

}
