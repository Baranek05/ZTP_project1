package com.example.ztp_lab1;

import com.example.ztp_lab1.products.ChangeHistory;
import com.example.ztp_lab1.products.Product;
import com.example.ztp_lab1.products.ProductService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;

import java.util.List;

@CucumberContextConfiguration
@SpringBootTest(classes = ZtpLab1Application.class)
public class ProductSteps {

    @Autowired
    private ProductService productService;

    private Product product;
    private Product savedProduct;
    private Exception exception;
    private List<ChangeHistory> changeHistoryList;

    //1
    @Given("the product name is {string}")
    public void the_product_name_is(String productName) {
        product = new Product();
        product.setName(productName);
    }

    @Given("the product price is {double}")
    public void the_product_price_is(Double productPrice) {
        product.setPrice(productPrice);
    }

    @Given("the product quantity is {int}")
    public void the_product_quantity_is(Integer productQuantity) {
        product.setQuantity(productQuantity);
    }

    @When("I save the new product")
    public void i_save_the_new_product() {
        try {
            savedProduct = productService.saveProduct(product);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the product should be saved successfully")
    public void the_product_should_be_saved_successfully() {
        Assertions.assertNull(exception, "No exception should be thrown");
        Assertions.assertNotNull(savedProduct, "Product should be saved");
        Assertions.assertEquals(product.getName(), savedProduct.getName(), "Product name should match");
        Assertions.assertEquals(product.getPrice(), savedProduct.getPrice(), "Product price should match");
        Assertions.assertEquals(product.getQuantity(), savedProduct.getQuantity(), "Product quantity should match");
    }

    //2
    @When("I try to save the new product")
    public void i_try_to_save_the_new_product() {
        try {
            savedProduct = productService.saveProduct(product);
        } catch (IllegalArgumentException e) {
            exception = e;
        }
    }

    @Then("the product should not be saved")
    public void the_product_should_not_be_saved() {
        Assertions.assertNull(savedProduct, "Product should not be saved");
    }

    @Then("I should be informed that the price cannot be negative")
    public void i_should_be_informed_that_the_price_cannot_be_negative() {
        Assertions.assertNotNull(exception, "An exception should be thrown");
        Assertions.assertTrue(exception.getMessage().contains("Product price cannot be less than zero"),
                "Exception message should indicate that the price cannot be negative");
    }
}
