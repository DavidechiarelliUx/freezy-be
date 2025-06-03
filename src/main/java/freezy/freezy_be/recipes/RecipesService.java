package freezy.freezy_be.recipes;

import freezy.freezy_be.auth.AppUser;
import freezy.freezy_be.auth.Role;
import freezy.freezy_be.productTemplates.ProductTemplate;
import freezy.freezy_be.productTemplates.ProductTemplateRepository;
import freezy.freezy_be.recipes.allergens.Allergen;
import freezy.freezy_be.recipes.allergens.AllergenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecipesService {
    private final RecipeRepository recipesRepository;
    private final ProductTemplateRepository productTemplateRepository;
    private final AllergenRepository allergenRepository;

    private Recipe toEntity(RecipeRequest request){
        Recipe recipe = new Recipe();
        recipe.setTitolo(request.getTitolo());
        recipe.setDescrizione(request.getDescrizione());
        recipe.setImmagine(request.getImmagine());
        recipe.setDifficolta(request.getDifficolta());
        recipe.setTempoCottura(request.getTempoCottura());
        recipe.setCalorie(request.getCalorie());
        recipe.setConservazione(request.getConservazione());
        recipe.setConsiglio(request.getConsiglio());
        recipe.setIngredientePrincipale(request.getIngredientePrincipale());
        recipe.setIngredienti(List.of(request.getIngredienti()));
        Set<Allergen> allergeni = Arrays.stream(request.getAllergeni())
                .map(id -> allergenRepository.findById(id).orElseThrow(() -> new RuntimeException("Allergene non trovato: " + id)))
                .collect(Collectors.toSet());
        recipe.setAllergeni(allergeni);
        recipe.setPreparazione(List.of(request.getPreparazione()));
        return recipe;
    }


    private RecipeResponse toDTO(Recipe recipe) {
        return new RecipeResponse(
                recipe.getId(),
                recipe.getTitolo(),
                recipe.getDescrizione(),
                recipe.getImmagine(),
                recipe.getDifficolta(),
                recipe.getTempoCottura(),
                recipe.getCalorie(),
                recipe.getConservazione(),
                recipe.getConsiglio(),
                recipe.getIngredientePrincipale(),
                recipe.getIngredienti().toArray(new String[0]),
                recipe.getAllergeni().stream()
                        .map(a -> new RecipeResponse.AllergenResponse(a.getId(), a.getName(), a.getIcona()))
                        .collect(Collectors.toList()),
                recipe.getPreparazione()
        );
    }

    public RecipeResponse create(RecipeRequest request, AppUser adminLoggato) {
        boolean isAdmin = adminLoggato.getRoles().contains(Role.ROLE_ADMIN);
        if (!isAdmin) {
            throw new RuntimeException("Non hai i permessi per modificare questa fattura");
        } else {
            Recipe recipe = toEntity(request);
            recipesRepository.save(recipe);
            return toDTO(recipe);
        }
    }

    public Page<RecipeResponse> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Recipe> recipes = recipesRepository.findAll(pageable);
        return recipes.map(this::toDTO);
    }
    public RecipeResponse findById(Long id) {
        Recipe recipe = recipesRepository.findById(id).orElseThrow(() -> new RuntimeException("Ricetta non trovata con id: " + id));
        return toDTO(recipe);
    }
    public RecipeResponse update(Long id, RecipeRequest request, AppUser adminLoggato) {
        boolean isAdmin = adminLoggato.getRoles().contains(Role.ROLE_ADMIN);
        if (!isAdmin) {
            throw new RuntimeException("Non hai i permessi per modificare questa fattura");
        } else {
            Recipe recipe = recipesRepository.findById(id).orElseThrow(() -> new RuntimeException("Ricetta non trovata con id: " + id));
            recipe.setTitolo(request.getTitolo());
            recipe.setDescrizione(request.getDescrizione());
            recipe.setImmagine(request.getImmagine());
            recipe.setDifficolta(request.getDifficolta());
            recipe.setTempoCottura(request.getTempoCottura());
            recipe.setCalorie(request.getCalorie());
            recipe.setConservazione(request.getConservazione());
            recipe.setConsiglio(request.getConsiglio());
            recipe.setIngredientePrincipale(request.getIngredientePrincipale());
            recipe.setIngredienti(new ArrayList<>(List.of(request.getIngredienti())));
            Set<Allergen> allergeni = Arrays.stream(request.getAllergeni())
                    .map(allergene -> allergenRepository.findById(allergene).orElseThrow(() -> new RuntimeException("Allergene non trovato: " + allergene)))
                    .collect(Collectors.toSet());
            recipe.setAllergeni(allergeni);
            recipe.setPreparazione(new ArrayList<>(List.of(request.getPreparazione())));
            recipesRepository.save(recipe);
            return toDTO(recipe);
        }
    }

    public void delete(Long id, AppUser adminLoggato) {
        boolean isAdmin = adminLoggato.getRoles().contains(Role.ROLE_ADMIN);
        if (!isAdmin) {
            throw new RuntimeException("Non hai i permessi per eliminare questa fattura");
        } else {
            Recipe recipe = recipesRepository.findById(id).orElseThrow(() -> new RuntimeException("Ricetta non trovata con id: " + id));
            recipesRepository.delete(recipe);
        }
    }

}
