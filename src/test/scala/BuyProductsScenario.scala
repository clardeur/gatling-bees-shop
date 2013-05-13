
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import bootstrap._

object BuyProductsScenario {

  val products = csv("products.csv").random.build

  val scn = scenario("Buy 3 products")
    .exec(
      session => {
        session.set("needAnOtherItem", true)
      }
    )
    .asLongAs("${needAnOtherItem}") {
      exec(
        http("View a product")
          .get("/product/".concat(products.next().getOrElse("id", "")))
          .check(status.is(200))
      )
      .exec(
        http("Add an item to the cart")
          .post("/cart/add")
          .param("product", value2Expression(products.next().getOrElse("id", "")))
          .param("quantity", "1")
          .header("Content-Type", "application/x-www-form-urlencoded")
          .check(status.is(200))
          .check(regex( """(\d+) items""").find.exists.saveAs("numberOfItemsInCart"))
      )
      .doIf(session => {session.get("numberOfItemsInCart").get == "3"}) {
        exec(
          session => {
            session.set("needAnOtherItem", false)
          }
        )
      }
    }
}