package freezy.freezy_be.recipes;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeRequest {

    @NotNull(message = "Titolo is required")
    private String titolo;
    @NotNull(message = "Descrizione is required")
    private String descrizione;
    @NotNull(message = "Immagine is required")
    private String immagine;
    @NotNull(message = "Difficolta is required")
    private String difficolta;
    @Min(value = 0, message = "Il tempo di cottura deve essere maggiore o uguale di zero")
    private int tempoCottura;
    @Min(value = 1, message = "Le calorie devono essere maggiori di zero")
    private int calorie;
    @NotNull(message = "Conservazione is required")
    private String conservazione;
    @NotNull(message = "Consiglio is required")
    private String consiglio;
    @NotNull(message = "Ingredienti is required")
    private String[] ingredienti;
    @NotNull(message = "IngredientiPrincipale is required")
    private String ingredientePrincipale;
    private Long[] allergeni;
    private String[] preparazione;

}
