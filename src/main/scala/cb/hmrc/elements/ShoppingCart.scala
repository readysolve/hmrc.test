package cb.hmrc.elements

/**
 * A ShoppingCart that holds Products.
 */
case class ShoppingCart( contents: List[Product] = List.empty ) {

  /**
   * Returns a new ShoppingCart consisting of the contents of this ShoppingCart
   * with the specified Product added.
   *
   * @param product A Product that is to be added to the ShoppingCart.
   * @return An updated ShoppingCart with the Product added.
   */
  def add( product: Product ) : ShoppingCart = {
    ShoppingCart( product :: contents )
  }

  /**
   * Returns the total price of all of the Products in this ShoppingCart.
   *
   * @return The sum of all of prices of all of the Products in the cart.
   */
  def price: BigDecimal = contents.map( product => product.price ).sum

}
