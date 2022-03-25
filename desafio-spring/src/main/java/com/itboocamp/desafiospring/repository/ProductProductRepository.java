package com.itboocamp.desafiospring.repository;

import com.itboocamp.desafiospring.entity.Product;
import com.itboocamp.desafiospring.util.JsonFileUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Locale;


@Repository
public class ProductProductRepository implements IProductRepository<Long, Product> {
    private static final String FILENAME = "arquivo.json";

    @Override
    public Product findById(Long id) {
        JsonFileUtil<Product> jsonFile = new JsonFileUtil<Product>(FILENAME);
        Optional<Product> productOptional = this.findAll().stream()
                .filter(product -> product.getProductId().equals(id)).findFirst();
        return productOptional.orElse(null);
    }

    @Override
    public List<Product> findAll() {
        JsonFileUtil<Product> jsonFile = new JsonFileUtil<Product>(FILENAME);
        return jsonFile.read(Product.class);
    }

    @Override
    public Product insert(Product entity) {
        JsonFileUtil<Product> jsonFile = new JsonFileUtil<Product>(FILENAME);
        return jsonFile.append(entity, Product.class);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Product findByName(String name) {
        List<Product> products = findAll();
        return  products.stream().filter(p -> p.getName()
                .equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    @Override
    public Product findByCategory(String category) {
        List<Product> products = findAll();
        return  products.stream().filter(p -> p.getCategory()
                .equalsIgnoreCase(category)).findFirst().orElse(null);
    }

    @Override
    public Product findByNameAndCategory(String name, String category) {
        List<Product> products = findAll();

        return  products.stream().filter(p -> {
            if(p.getName().equalsIgnoreCase(name) && p.getCategory().equalsIgnoreCase(category)) {
                return true;
            }
            return  false;
        }).findFirst().orElse(null);
    }
}