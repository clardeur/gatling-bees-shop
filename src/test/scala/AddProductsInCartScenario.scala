import io.gatling.core.Predef._
import io.gatling.core.validation.Validation
import io.gatling.http.Predef._
import scala.concurrent.duration._
import bootstrap._

object AddProductsInCartScenario {

  val products = csv("products.csv").random.build
  val numberOfProductsRegex: (Session) => Validation[String] = """(\d+) items"""

  // TODO #4: Scenario 'Add 3 products in cart'
  /**
   *   Scenario name: 'Add 3 products in cart'
   *     - view the home page and verify that the cart is empty
   *     - randomly view a product as long as the number of products in the cart is not 3
   *        - 30% chance to add a product into the cart when consulting it
   *        - else pause 1 to 5 seconds on it
   */
  val scn = scenario("Add 3 products in cart")

}