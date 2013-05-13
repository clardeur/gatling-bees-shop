
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import bootstrap._

object SearchProductsScenario {

  val products = csv("products.csv").random.build

  val scn =
    scenario("Search and consult 5 products")
      .exec(
      http("Home page")
        .get("/")
        .check(status.is(200)))
      .repeat(5) {
      exec(
        http("Search a random product")
          .get("/product/")
          .queryParam("name", value2Expression(products.next().getOrElse("name", "")))
          .check(status.is(200))
      )
    }
}