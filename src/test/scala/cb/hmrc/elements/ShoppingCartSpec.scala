package cb.hmrc.elements

import org.scalatest._


/**
 * Test of the ShoppingCart class.
 */
class ShoppingCartSpec extends FlatSpec with Matchers {

  "The discountProductOnQuantity method" should
    "return the right discount for 3 for price of 2" in {
      val products: List[Product] = List.fill( 3 )( Banana() )
      ShoppingCart().discountProductOnQuantity( products, 3 ) should be( Banana().price )
  }

  "The price of a Banana" should "be calculated correctly" in {
    val cart = ShoppingCart().add( Banana() )
    cart.price should be ( BigDecimal( "0.20" ) )
  }

  "A cart with two Bananas" should "give one banana for free" in {
    val cart = ShoppingCart().add( Banana() ).add( Banana() )
    cart.price should be ( BigDecimal( "0.20" ) )
  }

  "A cart with two Bananas and one Apple" should "give one banana for free" in {
    val cart = ShoppingCart().add( Banana() ).add( Banana() ).add( Apple() )
    val expectedPrice: BigDecimal = Apple().price + Banana().price
    cart.price should be ( expectedPrice )
  }

  "A cart with two Apples and one Banana" should "give one banana for free" in {
    val cart = ShoppingCart().add( Banana() ).add( Apple() ).add( Apple() )
    val expectedPrice: BigDecimal = 2 * Apple().price
    cart.price should be ( expectedPrice )
  }

  "The price of a Melon" should "be calculated correctly" in {
    val cart = ShoppingCart().add( Melon() )
    cart.price should be ( BigDecimal( "1.00" ) )
  }

  "The price of two Melons" should "be calculated correctly" in {
    val cart = ShoppingCart().add( Melon() ).add( Melon() )
    val expectedPrice: BigDecimal = 2 * Melon().price
    cart.price should be ( expectedPrice )
  }

  "A cart with three Melons" should "be two for the price of one" in {
    val cart = ShoppingCart().add( Melon() ).add( Melon() ).add( Melon() )
    val expectedPrice: BigDecimal = 2 * Melon().price
    cart.price should be ( expectedPrice )
  }



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
