/*
 * Copyright 2010-2013, CloudBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cloudbees.demo.beesshop.cart;

import com.cloudbees.demo.beesshop.product.Product;
import com.cloudbees.demo.beesshop.product.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedMetric;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:cleclerc@cloudbees.com">Cyrille Le Clerc</a>
 */
@ManagedResource("product:type=ShoppingCartController,name=ShoppingCartController")
@Controller
public class ShoppingCartController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final AtomicInteger shoppingCartsPriceInCents = new AtomicInteger();
    protected final AtomicInteger salesRevenueInCentsCounter = new AtomicInteger();
    protected final AtomicInteger salesItemsCounter = new AtomicInteger();
    protected final AtomicInteger salesOrdersCounter = new AtomicInteger();

    @Autowired
    ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(method = RequestMethod.POST, value = "/cart/add")
    public String addItem(@RequestParam("product") long productId, @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity, HttpServletRequest request) {

        Product product = productRepository.get(productId);
        if (product == null) {
            logger.warn("No product found with id " + productId + ". Silently redirect to home page");
            return "redirect:/";
        }

        ShoppingCart shoppingCart = shoppingCartRepository.getCurrentShoppingCart(request);
        shoppingCartsPriceInCents.addAndGet(quantity * product.getPriceInCents());
        shoppingCart.addItem(product, quantity);

        return "redirect:/product/" + productId;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cart/")
    public String view(HttpServletRequest request) {
        return "cart/view";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cart/buy")
    public String buy(HttpServletRequest request) {
        ShoppingCart shoppingCart = shoppingCartRepository.getCurrentShoppingCart(request);

        salesRevenueInCentsCounter.addAndGet(shoppingCart.getPriceInCents());
        salesItemsCounter.addAndGet(shoppingCart.getItemsCount());
        salesOrdersCounter.incrementAndGet();

        shoppingCartRepository.resetCurrentShoppingCart(request);
        return "redirect:/";
    }

    @ManagedMetric
    public int getShoppingCartsPriceInCents() {
        return shoppingCartsPriceInCents.get();
    }

    @ManagedMetric
    public int getSalesRevenueInCentsCounter() {
        return salesRevenueInCentsCounter.get();
    }

    @ManagedMetric
    public int getSalesItemsCounter() {
        return salesItemsCounter.get();
    }

    @ManagedMetric
    public int getSalesOrdersCounter() {
        return salesOrdersCounter.get();
    }
}
