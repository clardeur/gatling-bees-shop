<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>${product.name}</title>

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico">
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
</head>
<body>

<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <div class="span9">
                <a class="brand" style="padding: 0; padding-top: 10px; padding-right: 5px" href="${pageContext.request.contextPath}/"> <img alt='cloudbees logo' height='28'
                                                                                  src='${pageContext.request.contextPath}/img/logo.png'/> Bees Shop
                </a>
                <ul class="nav">
                    <li><a href="${pageContext.request.contextPath}/">Home</a></li>
                    <li class="active"><a href="${pageContext.request.contextPath}/product/">Products</a></li>
                </ul>
                <form class="navbar-search pull-left" action="${pageContext.request.contextPath}/product/">
                    <input id="searchProduct" name="name" type="text" class="search-query input-medium"
                           placeholder="Search by name">
                </form>
            </div>
            <div class="span3 pull-right">
                <p class="nav">
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/cart/" title="Shopping Cart">
                        <i class="icon-shopping-cart"></i>
                        ${shoppingCart.itemsCount} items
                        ${shoppingCart.prettyPrice}
                    </a>
                </p>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <div class="page-header">
        <h1>${product.name}</h1>
    </div>

    <div class="row">
        <div class="span2">
            <c:if test="${not empty product.photoUrl}">
                <img src="${product.photoUrl}" width="100"/>
            </c:if>
        </div>
        <div class="span5">
            <h2>Instructions</h2>

            <p>${product.instructionsAsHtml}</p>
        </div>
        <div class="span3">
            <h2>Ingredients</h2>
            <ul>
                <c:forEach items="${product.ingredients}" var="ingredient">
                    <li>${ingredient.quantity} ${ingredient.name}</li>
                </c:forEach>
            </ul>
        </div>
        <div class="span2">
            <div class="well">
                <p>Price: ${product.prettyPrice}</p>

                <form class="form-inline" action="${pageContext.request.contextPath}/cart/add" method="post">
                    <input name="product" value="${product.id}" type="hidden">
                    <select name="quantity" style="width: 50px" class="btn js-btn">
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                        <option>4</option>
                        <option>5</option>
                    </select>
                    <button type="submit" class="btn js-btn">
                        <i class="icon-shopping-cart"></i> Add
                    </button>
                </form>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="span8 offset2">
            <em><a href="${product.recipeUrl}" target="_blank">${product.recipeUrl}</a></em>
        </div>
    </div>
    <div class="row">
        <div class="span8 offset2">
            <hr/>
            <h4>Comments</h4>
            <c:forEach items="${product.comments}" var="comment">
                <em>"${comment}"</em>
                <br/>
            </c:forEach>
            <form action="${pageContext.request.contextPath}/product/${id}/comment" method="post" class="form-inline">
                <fieldset>
                    <input id="comment" name="comment" type="text" placeholder="Add a comment..."/>
                    <button type="submit" class="btn js-btn">
                        <i class="icon-comment"></i> Comment
                    </button>
                </fieldset>
            </form>
        </div>
    </div>
</div>
</body>
</html>
