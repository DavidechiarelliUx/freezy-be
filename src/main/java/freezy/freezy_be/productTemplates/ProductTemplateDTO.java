package freezy.freezy_be.productTemplates;

import lombok.Data;

@Data
public class ProductTemplateDTO {
    private String name;
    private TipoProdotto tipo;
    private String conservazione;
    private UnitaDiMisura unitaDiMisura;
    private String image;
}
