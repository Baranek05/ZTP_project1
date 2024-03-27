package com.example.ztp_lab1.products;

import com.example.ztp_lab1.products.DTO.ProductDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // Zwraca listę wszystkich produktów z informacjami bazowymi
    public List<ProductDTO> getAllProductsBasicInfo() {
        return productRepository.findAll().stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());
    }

    // Pobiera produkt po name
    public Optional<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    // Zapisuje nowy produkt
    public Product saveProduct(Product product) {
        validateProduct(product);
        return productRepository.save(product);
    }

    // Aktualizuje istniejący produkt
    public Product updateProduct(String name, Product productDetails) {
        validateProduct(productDetails);
        return productRepository.findByName(name)
                .map(existingProduct -> {
                    logChange(existingProduct, productDetails, "Update"); // Logowanie zmian przed aktualizacją produktu

                    return updateExistingProduct(existingProduct, productDetails);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Product with name " + name + " not found"));
    }

    // Aktualizuje dane istniejącego produktu
    private Product updateExistingProduct(Product existingProduct, Product productDetails) {
        // Teraz, zamiast bezpośredniego ustawiania, można użyć setterów i w nich logować zmiany, jeśli to konieczne
        existingProduct.setName(productDetails.getName());
        existingProduct.setQuantity(productDetails.getQuantity());
        existingProduct.setPrice(productDetails.getPrice());
        return productRepository.save(existingProduct);
    }

    // Loguje zmiany do historii produktu
    private void logChange(Product existingProduct, Product productDetails, String changeType) {
        if (!Objects.equals(existingProduct.getName(), productDetails.getName())) {
            addChangeHistory(existingProduct, "name", existingProduct.getName(), productDetails.getName());
        }
        if (!Objects.equals(existingProduct.getPrice(), productDetails.getPrice())) {
            addChangeHistory(existingProduct, "price", String.valueOf(existingProduct.getPrice()), String.valueOf(productDetails.getPrice()));
        }
        if (!Objects.equals(existingProduct.getQuantity(), productDetails.getQuantity())) {
            addChangeHistory(existingProduct, "quantity", String.valueOf(existingProduct.getQuantity()), String.valueOf(productDetails.getQuantity()));
        }
    }

    // Tworzy i dodaje wpis historii zmian
    private void addChangeHistory(Product product, String fieldName, String oldValue, String newValue) {
        ChangeHistory changeHistory = new ChangeHistory();
        changeHistory.setChangeDate(new Date());
        changeHistory.setChangeType("Update");
        changeHistory.setFieldName(fieldName);
        changeHistory.setOldValue(oldValue);
        changeHistory.setNewValue(newValue);
        changeHistory.setDescription(String.format("%s changed from %s to %s", fieldName, oldValue, newValue));
        product.getChangeHistory().add(changeHistory);
    }


    // Usuwa produkt o danym ID
    public void deleteProduct(String name) {
        productRepository.deleteByName(name);
    }

    // Konwertuje encję Product na DTO
    private ProductDTO convertToProductDTO(Product product) {
        return new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getQuantity());
    }

    // Waliduje produkt przed zapisem
    private void validateProduct(Product product) {
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Product price cannot be less than zero");
        }

    }
}

