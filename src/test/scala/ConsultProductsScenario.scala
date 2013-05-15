
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import bootstrap._

object ConsultProductsScenario {

  val products = csv("products.csv").random

  val scn = scenario("View 0 or n products")
    .exec(
      http("Home page")
        .get("/")
        .check(status.is(200)))
    .exec(
      http("View the list of products")
        .get("/product")
        .check(status.is(200)))
    .repeat(5) {
      feed(products)
      .exec(
        http("View a random product")
          .get("/product/${id}")
          .check(status.is(200)))
    }
}
