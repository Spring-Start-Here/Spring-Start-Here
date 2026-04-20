package com.example.ssh_ch8_ex5.controllers;

import com.example.ssh_ch8_ex5.model.Product;
import com.example.ssh_ch8_ex5.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductsController {

    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    //    @RequestMapping("/products")
    @GetMapping("/products")
    public String viewProducts(Model model) {
        var products = productService.findAll();
        model.addAttribute("products", products);

        return "products.html";
    }

    //    @RequestMapping(path = "/products", method = RequestMethod.POST)
    @PostMapping("/products")
    public String addProduct(
            @RequestParam String name, @RequestParam double price, Model model
    ) {
        Product p = new Product();
        p.setName(name);
        p.setPrice(price);
        productService.addProduct(p);

        var products = productService.findAll();
        model.addAttribute("products", products);

        return "products.html";
    }

}
