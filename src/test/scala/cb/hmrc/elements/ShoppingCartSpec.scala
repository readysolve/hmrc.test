package cb.hmrc.elements

import org.scalatest._


/**
 * Test of the ShoppingCart class.
 */
class ShoppingCartSpec extends FlatSpec with Matchers {

  "A shopping cart's contents" should "be correct" in {
    val emptyCart = ShoppingCart()
    emptyCart.contents.isEmpty should be ( true )
    val cartWithOneAppleAdded = emptyCart.add( Apple() )
    cartWithOneAppleAdded.contents should be ( List( Apple() ) )
    val cartWithTwoApplesAdded = cartWithOneAppleAdded.add( Apple() )
    cartWithTwoApplesAdded.contents should be ( List( Apple(), Apple() ) )
    val cartWithTwoApplesAndOneOrangeAdded = cartWithTwoApplesAdded.add( Orange() )
    cartWithTwoApplesAndOneOrangeAdded.contents should be ( List( Orange(), Apple(), Apple() ) )
  }

  "A shopping cart's price when filled one item at a time" should "be calculated correctly" in {
    val cart = ShoppingCart().add( Apple() ).add( Apple() ).add( Orange() ).add( Apple() )
    cart.price should be ( BigDecimal( "2.05" ) )
  }

  "A shopping cart's price when filled in one go" should "be calculated correctly" in {
    val cart = ShoppingCart( List( Apple(), Apple(), Orange(), Apple() ) )
    cart.price should be ( BigDecimal( "2.05" ) )
  }

}
