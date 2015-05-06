package cb.hmrc.elements

/**
 * Products that may be placed in a ShoppingCart.
 */
sealed class Product( val name: String, val price: BigDecimal )

case class Apple() extends Product( "Apple", BigDecimal("0.60") )
case class Orange() extends Product( "Orange", BigDecimal("0.25") )
