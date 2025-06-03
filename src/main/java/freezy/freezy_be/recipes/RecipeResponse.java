package freezy.freezy_be.recipes;

import java.util.List;
public record RecipeResponse(
        Long id,
        String titolo,
        String descrizione,
        String immagine,
        String difficolta,
        int tempoCottura,
        int calorie,
        String conservazione,
        String consiglio,
        String ingredientePrincipale,
        String[] ingredienti,
        List<AllergenResponse> allergeni,
        List<String> preparazione
) {
    public record AllergenResponse(Long id, String name, String icona) {}
}
