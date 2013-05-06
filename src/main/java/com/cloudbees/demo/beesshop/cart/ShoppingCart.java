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
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author <a href="mailto:cleclerc@cloudbees.com">Cyrille Le Clerc</a>
 */
public class ShoppingCart implements Serializable {

    /**
     * Number of added products by product
     */
    private SortedMap<Product, ShoppingCartItem> items = new TreeMap<Product, ShoppingCartItem>();

    public void addItem(Product product, int quantity) {
        ShoppingCartItem item = items.get(product);
        if (item == null) {
            items.put(product, new ShoppingCartItem(product, quantity));
        } else {
            item.incrementQuantity(quantity);
        }
    }

    @Nonnull
    public Collection<ShoppingCartItem> getItems() {
        return items.values();
    }

    public int getPriceInCents() {
        int totalPriceInCents = 0;
        for (ShoppingCartItem item : getItems()) {
            totalPriceInCents += item.getTotalPriceInCents();
        }
        return totalPriceInCents;
    }

    /**
     * Price in dollars
     */
    public String getPrettyPrice(){
        BigDecimal priceInDollars = new BigDecimal(getPriceInCents()).movePointLeft(2);
        return NumberFormat.getCurrencyInstance(Locale.US).format(priceInDollars);
    }

    public int getItemsCount() {
        int itemsCount = 0;
        for (ShoppingCartItem item : getItems()) {
            itemsCount += item.getQuantity();
        }
        return itemsCount;
    }

    public static class ShoppingCartItem implements Comparable<ShoppingCartItem>, Serializable {

        @Nonnull
        private final Product product;
        private int quantity;

        public ShoppingCartItem(@Nonnull Product product, int quantity) {
            Assert.notNull(product, "given Product can NOT be null");
            this.product = product;
            this.quantity = quantity;
        }

        public int getTotalPriceInCents() {
            return quantity * product.getPriceInCents();
        }

        @Override
        public int compareTo(ShoppingCartItem o) {
            return this.product.compareTo(o.product);
        }

        public Product getProduct() {
            return product;
        }

        /**
         * @param quantity quantity to add for the item
         */
        public void incrementQuantity(int quantity) {
            this.quantity += quantity;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ShoppingCartItem)) return false;

            ShoppingCartItem that = (ShoppingCartItem) o;

            if (!product.equals(that.product)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return product.hashCode();
        }
    }
}

