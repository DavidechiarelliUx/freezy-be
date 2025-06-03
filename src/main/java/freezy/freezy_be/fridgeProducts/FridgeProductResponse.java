package freezy.freezy_be.fridgeProducts;

import freezy.freezy_be.productTemplates.ProductTemplate;

import java.time.LocalDate;

public record FridgeProductResponse(
        Long id,
        LocalDate dataScadenza,
        int quantita,
        String nomeProdotto,
        String immagine,
        String conservazione,
        String tipo
) {}
