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

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author <a href="mailto:cleclerc@cloudbees.com">Cyrille Le Clerc</a>
 */
@Repository
public class ProductRepository {

    private Map<Long, Product> products = new HashMap<Long, Product>();
    private AtomicLong productsSequence = new AtomicLong();

    public ProductRepository() {
        Product sexOnTheBeach = buildSexOnTheBeachCocktail();
        products.put(sexOnTheBeach.getId(), sexOnTheBeach);
        Product longIslandIcedTea = buildLongIslandCocktail();
        products.put(longIslandIcedTea.getId(), longIslandIcedTea);
    }

    /**
     * @param name
     * @return
     */
    public Collection<Product> find(@Nullable String name) {
        SortedSet<Product> result = new TreeSet<Product>();
        for (Product product : products.values()) {
            if (name == null && name == null) {
                result.add(product);
            }
            if (StringUtils.hasLength(name)) {
                if (product.getName().toLowerCase().contains(name.toLowerCase())) {
                    result.add(product);
                    break;
                }
            }
        }
        return result;
    }

    public Product get(long id) {
        return products.get(id);
    }

    public List<String> suggestProductKeywords(String query) {
        return Collections.singletonList("#TODO#");
    }

    public List<String> suggestProductWords(String query) {
        return Collections.singletonList("#TODO#");
    }

    public void update(Product product) {
        this.products.put(product.getId(), product);
    }

    protected Product buildSexOnTheBeachCocktail() {
        Product sexOnTheBeach = new Product()
                .withId(productsSequence.incrementAndGet())
                .withName("Sex On The Beach")
                .withPriceInCents(550)
                .withIngredient("1 shot", "vodka")
                .withIngredient("1 shot", "peach schnapps (archers)")
                .withIngredient("200 ml", "orange juice")
                .withIngredient("200 ml", "cranberry juice")
                .withIngredient("2 shots", "raspberry syrup")
                .withPhotoUrl("http://bees-shop.s3-website-us-east-1.amazonaws.com/5115940004_2825a4548e.jpg")
                .withPhotoCredit("http://www.flickr.com/photos/elv/5115940004/")
                .withRecipeUrl("http://www.cocktailmaking.co.uk/displaycocktail.php/321-Sex-On-The-Beach")
                .withInstructions(
                        "Add ice to glass pour in shot of vodka add peach shnapps mix with orange, cranberry and raspberry\n" //
                                + "\n" //
                                + "Serve with an umbrella and a mixer stick and a fancy straw and an orange slice on side of "
                                + "glass this one is gorgeous can't believe you don't already have it on here!");
        return sexOnTheBeach;
    }

    protected Product buildLongIslandCocktail() {
        Product longIslandIcedTea = new Product()
                .withId(productsSequence.incrementAndGet())
                .withName("Long Island Iced tea")
                .withPriceInCents(650)
                .withIngredient("1 Measure", "vodka")
                .withIngredient("1 Measure", "gin")
                .withIngredient("1 Measure", "white rum")
                .withIngredient("1 Measure", "tequila")
                .withIngredient("1 Measure", "triple sec")
                .withIngredient("3 measures", "orange juice")
                .withIngredient("to top up the glass", "coke")
                .withPhotoUrl("http://bees-shop.s3-website-us-east-1.amazonaws.com/340757408_d3cbdba2f2.jpg")
                .withPhotoCredit("http://www.flickr.com/photos/alisdair/340757408/")
                .withRecipeUrl("http://www.cocktailmaking.co.uk/displaycocktail.php/1069-Long-Island-Iced-tea")
                .withInstructions(
                        "In a tall glass , add ice and all the ingredients and stir well. It should have the appearance of cloudy tea. Top with a piece of lemon\n"
                                + "\n"
                                + "Very yummy & very very deceiving. It will get you hammered after only about 2 so drink with caution");
        return longIslandIcedTea;
    }
}
