
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import bootstrap._

object ConsultProductsScenario {

  val products = csv("products.csv").random.build

  val scn = scenario("View 0 or n products")
    .exec(
      http("Home page")
        .get("/")
        .check(status.is(200)))
    .exec(
      session => {
        session.set("continueShopping", true)
      }
    )
    .asLongAs("${continueShopping}") {
      randomSwitch(
        60 -> exec(
          http("View a random product")
            .get("/product/".concat(products.next().getOrElse("id", "")))
            .check(status.is(200))
        ),
        20 -> exec(
          http("View the list of products")
            .get("/product")
            .check(status.is(200))
        ),
        20 -> exec(
          session => {
            session.set("continueShopping", false)
          }
        )
      )
    }
}