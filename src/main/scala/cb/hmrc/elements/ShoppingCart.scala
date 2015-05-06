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
   * It now applies these special offers:
   *    -   Two for the price of one on Apples.
   *    -   Three for the price of two on Oranges.
   *
   * @return The sum of all of prices of all of the Products in the cart.
   */
  def price: BigDecimal = {
    val discountOnApples  = contents.filter( _.isInstanceOf[Apple]  ).size / 2 * Apple().price
    val discountOnOranges = contents.filter( _.isInstanceOf[Orange] ).size / 3 * Orange().price
    contents.map( product => product.price ).sum - discountOnApples - discountOnOranges
  }

}
