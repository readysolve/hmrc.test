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
    val cart = ShoppingCart().add( Orange() ).add( Apple() )
    cart.price should be ( BigDecimal( "0.85" ) )
  }

  "A shopping cart's price when filled in one go" should "be calculated correctly" in {
    val cart = ShoppingCart( List( Orange(), Apple() ) )
    cart.price should be ( BigDecimal( "0.85" ) )
  }

  "A buy one get one free" should "be applied where appropriate" in {
    val cart = ShoppingCart( List( Apple(), Orange(), Apple() ) )
    // With a "buy one get one free" on Apples, one of the apples should be free.
    val expectedPrice: BigDecimal = Apple().price + Orange().price
    cart.price should be ( expectedPrice )
  }

  "A buy one get one free" should "not be applied to an odd apple" in {
    val cart = ShoppingCart( List( Apple(), Apple(), Orange(), Apple() ) )
    // With a "buy one get one free" on Apples, one of the apples should be free.
    val expectedPrice: BigDecimal = 2 * Apple().price + Orange().price
    cart.price should be ( expectedPrice )
  }

  "A buy one get one free" should "can be applied multiple times" in {
    val cart = ShoppingCart( List( Apple(), Apple(), Apple(), Orange(), Apple() ) )
    // With a "buy one get one free" on Apples, two of the apples should be free.
    val expectedPrice: BigDecimal = 2 * Apple().price + Orange().price
    cart.price should be ( expectedPrice )
  }

  "A buy one get one free" should "can be applied multiple times but not to an odd apple" in {
    val cart = ShoppingCart( List( Apple(), Apple(), Apple(), Orange(), Apple(), Apple() ) )
    // With a "buy one get one free" on Apples, two of the apples should be free.
    val expectedPrice: BigDecimal = 3 * Apple().price + Orange().price
    cart.price should be ( expectedPrice )
  }

  "A three for the price of two" should "be applied where appropriate" in {
    val cart = ShoppingCart( List( Apple(), Orange(), Orange(), Orange() ) )
    // With a "three for the price of two" on Oranges, one of the oranges should be free.
    val expectedPrice: BigDecimal = Apple().price + 2 * Orange().price
    cart.price should be ( expectedPrice )
  }

  "A three for the price of two" should "not be applied to two odd oranges" in {
    val cart = ShoppingCart( List( Apple(), Orange(), Orange() ) )
    // With a "three for the price of two" on Oranges, no discount is available.
    val expectedPrice: BigDecimal = Apple().price + 2 * Orange().price
    cart.price should be ( expectedPrice )
  }

  "A three for the price of two" should "can be applied multiple times" in {
    val cart = ShoppingCart( List( Orange(), Orange(), Apple(), Orange(), Orange(), Orange(), Orange() ) )
    // With a "three for the price of two" on Oranges, two of the oranges should be free.
    val expectedPrice: BigDecimal = Apple().price + 4 * Orange().price
    cart.price should be ( expectedPrice )
  }

}
