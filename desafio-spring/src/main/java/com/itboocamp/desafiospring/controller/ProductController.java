package com.itboocamp.desafiospring.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.util.UriComponentsBuilder;
import com.itboocamp.desafiospring.controller.exception.product.DuplicateProductException;
import com.itboocamp.desafiospring.controller.validator.IValidator;
import com.itboocamp.desafiospring.controller.validator.product.CategoryProductValidator;
import com.itboocamp.desafiospring.controller.validator.product.NameProductValidator;
import com.itboocamp.desafiospring.controller.validator.product.QuantityProductValidator;
import com.itboocamp.desafiospring.dto.mapper.ProductDTOMapper;
import com.itboocamp.desafiospring.dto.response.ProductResponseDTO;
import com.itboocamp.desafiospring.dto.request.ProductRequestDTO;
import com.itboocamp.desafiospring.entity.Product;
import com.itboocamp.desafiospring.entity.filter.ProductFilter;
import com.itboocamp.desafiospring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Arrays;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "products")
@Api(value = "products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @ApiOperation(value = "Realize a criação de um produto.")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody ProductRequestDTO request, UriComponentsBuilder uriBuilder) {
        List<IValidator> validators = Arrays.asList(
                new CategoryProductValidator(request),
                new NameProductValidator(request),
                new QuantityProductValidator(request)
        );

        validators.forEach(v -> v.validator());
        Product productAlreadyExist = productService.findByNameAndCategory(request.getName(), request.getCategory());

        if (productAlreadyExist != null) {
            throw new DuplicateProductException("Product already registered.");
        }

        Product product = productService.create(request);
        ProductResponseDTO productResponseDTO = new ProductDTOMapper().mapDTO(product);

        URI uri = uriBuilder
                .path("{id}")
                .buildAndExpand(product.getProductId())
                .toUri();

        return ResponseEntity.created(uri).body(productResponseDTO);
    }


    @ApiOperation(value = "Liste todos os produtos cadastrados")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "sort",
                    value = "Escolha 0 para alfabético crescente; escolha 1 para alfabético decrescente; escolha 2 para maior a menor preço; e escolha 3 para menor a maior preço."
            )
    })
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> listProducts(@RequestParam(required = false) Long id,
                                                                 @RequestParam(required = false) String name,
                                                                 @RequestParam(required = false) String category,
                                                                 @RequestParam(required = false) String brand,
                                                                 @RequestParam(required = false) BigDecimal price,
                                                                 @RequestParam(required = false) Integer quantity,
                                                                 @RequestParam(required = false) Boolean freeShipping,
                                                                 @RequestParam(required = false) Double prestige,
                                                                 @RequestParam(required = false) Integer sort) {
        ProductFilter productFilter = ProductFilter.builder().productId(id).name(name).category(category).brand(brand).price(price)
                .quantity(quantity).freeShipping(freeShipping).prestige(prestige).build();
        ProductDTOMapper mapper = new ProductDTOMapper();
        return ResponseEntity.ok().body(mapper.mapDTO(productService.listProducts(productFilter, sort)));
    }
}
