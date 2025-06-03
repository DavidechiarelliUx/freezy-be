package freezy.freezy_be.productTemplates;

public record ProductTemplateResponse(
        Long  id,
        String name,
        TipoProdotto tipo,
        String conservazione,
        UnitaDiMisura unitaDiMisura,
        String image
) {
}
