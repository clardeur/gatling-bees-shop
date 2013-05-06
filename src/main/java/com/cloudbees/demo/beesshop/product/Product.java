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
package com.cloudbees.demo.beesshop.product;


import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

/**
 * @author <a href="mailto:cleclerc@cloudbees.com">Cyrille Le Clerc</a>
 */
public class Product implements Comparable<Product>, Serializable {

    private List<Ingredient> ingredients = new ArrayList<Ingredient>();
    private String instructions;
    private String name;
    private long id;
    private String photoUrl;
    private String photoCredit;

    /**
     * URL of the product recipe that has been used
     */
    private String recipeUrl;
    private Deque<String> comments = new LinkedList<String>();

    private int priceInCents;

    public String getInstructionsAsHtml() {
        return instructions == null ? "" : instructions.replace("\n", "<br />\n");
    }

    public Collection<String> getIngredientNames() {
        List<String> ingredientNames = new ArrayList<String>();
        for (Ingredient ingredient : this.ingredients) {
            ingredientNames.add(ingredient.getName());
        }
        return ingredientNames;
    }

    @Override
    public int compareTo(Product other) {
        if (this.name == null) {
            return other.name == null ? 0 : -1;
        }
        return this.name.compareTo(other.name);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Product withPhotoCredit(String photoCredit) {
        this.photoCredit = photoCredit;
        return this;
    }

    public String getPhotoCredit() {
        return photoCredit;
    }

    public void setPhotoCredit(String photoCredit) {
        this.photoCredit = photoCredit;
    }

    public String getRecipeUrl() {
        return recipeUrl;
    }

    public void setRecipeUrl(String recipeUrl) {
        this.recipeUrl = recipeUrl;
    }

    public Deque<String> getComments() {
        return comments;
    }

    public void setComments(Deque<String> comments) {
        this.comments = comments;
    }

    public Product withId(long id) {
        setId(id);
        return this;
    }

    public Product withIngredient(String quantity, String name) {
        this.ingredients.add(new Ingredient(quantity, name));
        return this;
    }

    public Product withInstructions(String instructions) {
        setInstructions(instructions);
        return this;
    }

    public Product withName(String name) {
        setName(name);
        return this;
    }

    public Product withPhotoUrl(String photoUrl) {
        setPhotoUrl(photoUrl);
        return this;
    }

    public Product withRecipeUrl(String recipeUrl) {
        setRecipeUrl(recipeUrl);
        return this;
    }

    public int getPriceInCents() {
        return priceInCents;
    }

    /**
     * Price in dollars
     */
    public String getPrettyPrice(){
        BigDecimal priceInDollars = new BigDecimal(getPriceInCents()).movePointLeft(2);
        return NumberFormat.getCurrencyInstance(Locale.US).format(priceInDollars);
    }

    public void setPriceInCents(int priceInCents) {
        this.priceInCents = priceInCents;
    }

    public Product withPriceInCents(int priceInCents) {
        this.priceInCents = priceInCents;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if (id != product.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public static class Ingredient implements Serializable {

        private String name;
        private String quantity;

        public Ingredient(String quantity, String name) {
            this.quantity = quantity;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return "Ingredient{" +
                    "quantity='" + quantity + '\'' +
                    '}';
        }
    }
}
