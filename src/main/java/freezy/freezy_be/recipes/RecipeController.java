package freezy.freezy_be.recipes;

import freezy.freezy_be.auth.AppUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipesService recipesService;

    @PostMapping
    public ResponseEntity<RecipeResponse> createRecipe(@RequestBody @Valid RecipeRequest request,
                                                       @AuthenticationPrincipal AppUser adminLoggato) {
        RecipeResponse response = recipesService.create(request, adminLoggato);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<RecipeResponse>> getAllRecipes(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size,
                                                              @RequestParam(defaultValue = "id") String sortBy) {
        Page<RecipeResponse> responsePage = recipesService.findAll(page, size, sortBy);
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> getRecipeById(@PathVariable Long id) {
        RecipeResponse response = recipesService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponse> updateRecipe(@PathVariable Long id,
                                                       @RequestBody @Valid RecipeRequest request,
                                                       @AuthenticationPrincipal AppUser adminLoggato) {
        RecipeResponse response = recipesService.update(id, request, adminLoggato);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id,
                                             @AuthenticationPrincipal AppUser adminLoggato) {
        recipesService.delete(id, adminLoggato);
        return ResponseEntity.noContent().build();
    }
}