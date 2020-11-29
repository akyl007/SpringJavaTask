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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/categories")
public class CategoryController {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);
    private final ProductService pService;
    private final CategoryService catService;

    @Autowired
    public CategoryController(CategoryService catService, ProductService pService) {
        this.catService = catService;
        this.pService = pService;
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Category> getCategories() {
        return catService.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    // If the parameter name matches parameter in the mapping value, it is not necessary to explicitly provide it
    public Category getById(@PathVariable Integer id) {
        final Category category = catService.find(id);
        if (category == null) {
            throw NotFoundException.create("Category", id);
        }
        return category;
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/{id}/products", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addProductToCategory(@PathVariable Integer id, @RequestBody Product product) {
        final Category category = getById(id);
        catService.addProduct(category, product);
        LOG.debug("Product {} added into category {}.", product, category);
    }
    @GetMapping(value = "/{id}/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getProductsByCategory(@PathVariable Integer id) {
        return pService.findAll(getById(id));
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCategory(@RequestBody Category category) {
        catService.persist(category);
        LOG.debug("Created category {}.", category);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", category.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{categoryId}/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProductFromCategory(@PathVariable Integer categoryId,
                                          @PathVariable Integer productId) {
        final Category category = getById(categoryId);
        final Product toRemove = pService.find(productId);
        if (toRemove == null) {
            throw NotFoundException.create("Product", productId);
        }
        catService.removeProduct(category, toRemove);
        LOG.debug("Product {} removed from category {}.", toRemove, category);
    }
}
