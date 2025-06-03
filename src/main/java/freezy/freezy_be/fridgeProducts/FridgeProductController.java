package freezy.freezy_be.fridgeProducts;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fridgeProducts")
@Validated
@RequiredArgsConstructor
public class FridgeProductController {

    private final FridgeProductService fridgeProductService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FridgeProductResponse> create(@RequestBody @Valid FridgeProductRequest request) {
        return ResponseEntity.ok(fridgeProductService.create(request));
    }

    @GetMapping
    public Page<FridgeProductResponse> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return fridgeProductService.findAll(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FridgeProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(fridgeProductService.findById(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<FridgeProductResponse> update(@PathVariable Long id, @RequestBody @Valid FridgeProductRequest request) {
        return ResponseEntity.ok(fridgeProductService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        fridgeProductService.delete(id);
    }
}
