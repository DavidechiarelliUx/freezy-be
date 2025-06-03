package freezy.freezy_be.productTemplates;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductTemplateRequest {

    @NotNull(message = "Name is required")
    private String name;
    @NotNull(message = "Type is required")
    private TipoProdotto tipo;
    @NotNull(message = "Conservation is required")
    private String conservazione;
    @NotNull(message = "Unit of measure is required")
    private UnitaDiMisura unitaDiMisura;

    private String image;
}
