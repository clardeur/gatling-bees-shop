
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import bootstrap._

object AddProductsInCartScenario {

  val products = csv("products.csv").random.build

  val scn = scenario("Add 3 products in cart")
    .exec(
      http("Home page")
        .get("/")
        .check(status.is(200))
        .check(
          regex("""(\d+) items""").find.transform((n : Option[String]) => Option(n.get.toInt)).is(0)
          .saveAs("numberOfProductsInCart")))
    .feed(products)
    .asLongAs(session => {session.get[Int]("numberOfProductsInCart").get < 3}) {
      exec(
        http("View a product")
          .get("/product/${productId}")
          .check(status.is(200)))
      .randomSwitch(
        70 -> pause(1 second, 5 seconds),
        30 -> exec(
          http("Add a product in cart")
            .post("/cart/add")
            .param("product", "${productId}")
            .param("quantity", "1")
            .check(status.is(200))
            .check(
              regex("""(\d+) items""").find.transform((n : Option[String]) => Option(n.get.toInt)).exists
              .saveAs("numberOfProductsInCart"))))}
}