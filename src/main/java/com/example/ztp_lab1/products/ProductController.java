package com.example.ztp_lab1.products;

import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ztp_lab1.products.DTO.ProductDTO;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    // Pobiera wszystkie produkty
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productDTOList = productService.getAllProductsBasicInfo();
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    // Pobiera produkt po nazwie
    @GetMapping("/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable String name) {
        return productService.getProductByName(name)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Product with name " + name + " not found"));
    }

    // Tworzy nowy produkt
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    // Aktualizuje istniejący produkt po nazwie
    @PutMapping("/{name}")
    public ResponseEntity<Product> updateProduct(@PathVariable String name, @RequestBody Product productDetails) {
        Product updatedProduct = productService.updateProduct(name, productDetails);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // Usuwa produkt po name
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String name) {
        productService.deleteProduct(name);
        return ResponseEntity.noContent().build();
    }

    // Pobiera listę zmian
    @GetMapping("/products/{name}/changes")
    public ResponseEntity<List<ChangeHistory>> getProductChangeHistory(@PathVariable String name) {
        return productService.getProductByName(name)
                .map(product -> ResponseEntity.ok(product.getChangeHistory()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
