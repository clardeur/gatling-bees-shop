import io.gatling.core.Predef._
import io.gatling.http.Predef._
import bootstrap._

object ConsultProductsScenario {

  val products = csv("products.csv").random

  // TODO #1: Scenario 'View 5 random products'
  /**
   *  Scenario name: 'View 5 random products'
   *   - view the home page
   *   - view the list of products
   *   - view 5 random products
   */
  val scn = scenario("View 5 random products")
}
