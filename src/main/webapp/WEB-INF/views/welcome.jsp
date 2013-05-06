<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Bees Shop Demo</title>

    <link rel="shortcut icon" href=${pageContext.request.contextPath}/img/favicon.ico
    ">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/favicon.png">

    <!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.0/css/bootstrap-combined.min.css" rel="stylesheet">
    <link href="//netdna.bootstrapcdn.com/font-awesome/3.0.2/css/font-awesome.css" rel="stylesheet">
    <link href="//code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css" rel="Stylesheet" type="text/css"/>

    <script src="//code.jquery.com/jquery-1.9.1.min.js"></script>
    <script src="//code.jquery.com/ui/1.10.1/jquery-ui.js" type="text/javascript"></script>
    <script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("input#searchProduct").autocomplete({
                minLength: 2,
                source: "${pageContext.request.contextPath}/product/suggest"
            });
        });
    </script>
<body>
<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <div class="span9">
                <a class="brand" style="padding: 0; padding-top: 10px; padding-right: 5px"
                   href="${pageContext.request.contextPath}/"> <img alt='cloudbees logo' height='28'
                                                                    src='${pageContext.request.contextPath}/img/logo.png'/> Bees Shop
                </a>
                <ul class="nav">
                    <li class="active"><a href="${pageContext.request.contextPath}/">Home</a></li>
                    <li><a href="${pageContext.request.contextPath}/product/">Products</a></li>
                </ul>
                <form class="navbar-search pull-left" action="${pageContext.request.contextPath}/product/">
                    <input id="searchProduct" name="name" type="text" class="search-query input-medium"
                           placeholder="Search by name">
                </form>
            </div>
            <div class="span3 pull-right">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/cart/" title="Shopping Cart">
                    <i class="icon-shopping-cart"></i>
                    ${shoppingCart.itemsCount} items
                    ${shoppingCart.prettyPrice}
                </a>
            </div>
        </div>
    </div>
</div>

<div class="container">

    <div class="row">
        <div class="span6">
            <div class="hero-unit">
                <h2>Enter the bees shop!</h2>

                <p>
                    <a class="btn btn-primary btn-large" href="${pageContext.request.contextPath}/product/"> Shopping
                        zone! </a>
                </p>
            </div>
        </div>
        <div class="span6">
            <div class="hero-unit">
                <img src="img/bee-7179227681_2b3da2bb7a_n.jpg"/><br/>
                <a style="font-size: xx-small" href="http://www.flickr.com/photos/80180331@N02/7179227681">&copy; All Rights Reserved - AAUACC</a>
            </div>
        </div>
    </div>

</div>
<a href="https://github.com/CloudBees-community/bees-shop-clickstart"><img
        style="position: absolute; top: 0; right: 0; border: 0;"
        src="https://s3.amazonaws.com/github/ribbons/forkme_right_gray_6d6d6d.png" alt="Fork me on GitHub"></a>
</body>
</html>