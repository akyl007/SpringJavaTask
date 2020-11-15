package cz.cvut.kbss.ear.eshop.rest;

import cz.cvut.kbss.ear.eshop.exception.NotFoundException;
import cz.cvut.kbss.ear.eshop.model.Category;
import cz.cvut.kbss.ear.eshop.model.Product;
import cz.cvut.kbss.ear.eshop.rest.util.RestUtils;
import cz.cvut.kbss.ear.eshop.service.CategoryService;
import cz.cvut.kbss.ear.eshop.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/categories")
public class CategoryController {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService service;

    private final ProductService productService;

    @Autowired
    public CategoryController(CategoryService service, ProductService productService) {
        this.service = service;
        this.productService = productService;
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Category> getCategories() {
        return service.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCategory(@RequestBody Category category){
        service.persist(category);
        HttpHeaders header = RestUtils.createLocationHeaderFromCurrentUri("/{id}", category.getId());
        return new ResponseEntity<>(header, HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("{id}")
    public Category getCategory(@PathVariable Integer id) {
        Category category = service.find(id);
        if (category == null) {
            throw NotFoundException.create(Category.class.getSimpleName(), id);
        }
        return category;
    }

    @PostMapping(value = "{id}/products", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void AddToCategory(@PathVariable Integer id, @RequestBody Product product){
        Category category = service.find(id);
        if (category == null) {
            throw NotFoundException.create(Category.class.getSimpleName(), id);
        }
        service.addProduct(category, product);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("{id}/products")
    public List<Product> getProduct(@PathVariable Integer id) {
        Category category = service.find(id);
        if (category == null) {
            throw NotFoundException.create(Category.class.getSimpleName(), id);
        }
        return productService.findAll(category);
    }

    @DeleteMapping(value = "{id}/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void RemoveFromCategory(@PathVariable Integer id, @PathVariable Integer productId){
        Category category = service.find(id);
        if (category == null) {
            throw NotFoundException.create(Category.class.getSimpleName(), id);
        }
        Product product = productService.find(productId);
        if (product == null) {
            throw NotFoundException.create(Product.class.getSimpleName(), id);
        }
        service.removeProduct(category, product);

    }




}
