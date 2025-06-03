package freezy.freezy_be.productTemplates;

import freezy.freezy_be.auth.AppUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product-templates")
@RequiredArgsConstructor
@Validated
public class ProductTemplateController {

    @Autowired
    private ProductTemplateService productTemplateService;

    //create post :
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductTemplateResponse> create(@RequestBody @Valid ProductTemplateRequest request, @AuthenticationPrincipal AppUser admin) {
        return ResponseEntity.ok(productTemplateService.create(request, admin));
    }

    @GetMapping
    public Page<ProductTemplateResponse> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return productTemplateService.findAll(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductTemplateResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productTemplateService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ProductTemplateResponse update(@PathVariable Long id, @RequestBody @Valid ProductTemplateRequest request, @AuthenticationPrincipal AppUser admin) {
        return productTemplateService.update(id, request, admin);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, @AuthenticationPrincipal AppUser admin) {
        productTemplateService.delete(id, admin);
    }
}
