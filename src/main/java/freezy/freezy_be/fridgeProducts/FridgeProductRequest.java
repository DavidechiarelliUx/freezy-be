package freezy.freezy_be.fridgeProducts;

import freezy.freezy_be.productTemplates.ProductTemplate;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FridgeProductRequest {
    @NotNull(message = "Data scadenza is required")
    private LocalDate dataScadenza;
    @NotNull(message = "Quantita is required")
    private int quantita;
    @NotNull(message = "Product template is required")
    private Long productTemplateId;

}
