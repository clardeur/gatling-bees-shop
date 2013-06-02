
import io.gatling.core.Predef._
import io.gatling.http.Predef._

object SearchAndCommentProductsScenario {

  val products = csv("products.csv").random

  val scn = scenario("Search a product")
    .exec(
      http("Home page")
        .get("/")
        .check(status.is(200)))
    .feed(products)
    .exec(
      http("Search a random product by name")
        .get("/product/")
        .queryParam("name", "${productName}")
        .check(status.is(200))
        .check(regex("""href=".*/product/(\d+)"""").find.exists.saveAs("productIdFound")))
    .exec(
      http("View a product")
        .get("/product/${productIdFound}")
        .check(status.is(200)))
    .exec(
      http("Comment a product")
        .post("/product/${productIdFound}/comment")
        .param("comment", "My 2 cents!")
        .check(status.is(200)))
}