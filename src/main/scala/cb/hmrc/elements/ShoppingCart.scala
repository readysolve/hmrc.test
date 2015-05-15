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
   *    -   Two for the price of one on Apples and Bananas taken together, cheapest first.
   *    -   Three for the price of two on Oranges and Melons (taken separately).
   *
   * @return The sum of all of the prices of all of the Products in the cart.
   */
  def price: BigDecimal = {
    val applesAndBananasCheapestFirst = contents.filter( p => p.isInstanceOf[Apple] || p.isInstanceOf[Banana] ).sortBy( _.price )
    val discountOnApplesAndBananas = discountProductOnQuantity( applesAndBananasCheapestFirst, 2 )
    val discountOnOranges = discountProductOnQuantity( contents.filter( _.isInstanceOf[Orange] ), 3 )
    val discountOnMelons  = discountProductOnQuantity( contents.filter( _.isInstanceOf[Melon]  ), 3 )
    contents.map( product => product.price ).sum - discountOnApplesAndBananas - discountOnOranges - discountOnMelons
  }

  /*
   * Returns the discount for a list of Products for which one free one will be given for
   * every 'quantity' purchased.
   *
   * If the products list is heterogenous then it must be sorted so that the discount will
   * be taken from the products at the head of the list, ie cheapest first if the discount
   * will be given only for the cheapest products in the list.
   */
  def discountProductOnQuantity( products: Seq[Product], quantity: Int ) : BigDecimal = {
    products.take( products.size / quantity ).map( _.price ).sum
  }

}
