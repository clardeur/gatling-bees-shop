import io.gatling.core.Predef._
import io.gatling.http.Predef._

object SearchAndCommentProductsScenario {

  val products = csv("products.csv").random

  // TODO #3: Scenario 'Search and comment a product'
  /**
   *  Scenario name: 'Search and comment a product'
   *    - view the home page
   *    - search a random product by name
   *    - view a product found in the search randomly
   *    - add a comment to this product
   */
  val scn = scenario("Search and comment a product")
}