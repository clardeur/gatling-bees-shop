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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/**
 * @author <a href="mailto:cleclerc@cloudbees.com">Cyrille Le Clerc</a>
 */
@Controller
public class ProductController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "welcome";
    }

    @RequestMapping(value = "/product/{id}/comment", method = RequestMethod.POST)
    public String addComment(@PathVariable long id, @RequestParam("comment") String comment, HttpServletRequest request) {

        Product product = productRepository.get(id);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        logger.debug("Add comment: '{}' to {}", comment, product);
        product.getComments().addFirst(comment);
        productRepository.update(product);

        return "redirect:/product/{id}";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/product/suggest")
    @ResponseBody
    public List<String> suggestProductWord(@RequestParam("term") String term) {
        List<String> words = this.productRepository.suggestProductWords(term);
        logger.trace("autocomplete word for {}:{}", term, words);
        return words;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/product/{id}")
    public String view(@PathVariable long id, Model model) {
        Product product = productRepository.get(id);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        model.addAttribute(product);

        return "product/view";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/product")
    public ModelAndView find(@RequestParam(value = "name", required = false) String name) {

        Collection<Product> products = productRepository.find(name);

        return new ModelAndView("product/view-all", "products", products);
    }


    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Product not found")
    public static class ProductNotFoundException extends RuntimeException {

        private long id;

        public ProductNotFoundException(long id) {
            super("Resource: " + id);
            this.id = id;
        }

        public long getId() {
            return id;
        }
    }
}
