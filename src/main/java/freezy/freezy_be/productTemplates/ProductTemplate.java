package freezy.freezy_be.productTemplates;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "productTemplates")

public class ProductTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "conservazione", nullable = false)
    private String conservazione;

    @Enumerated(EnumType.STRING)
    private TipoProdotto tipo;


    @Enumerated(EnumType.STRING)
    private UnitaDiMisura unitaDiMisura;

    @Column(name = "image", nullable = false)
    private String image;


    public ProductTemplate(String name, String conservazione, TipoProdotto tipo, UnitaDiMisura unitaDiMisura, String image) {
        this.name = name;
        this.conservazione = conservazione;
        this.tipo = tipo;
        this.unitaDiMisura = unitaDiMisura;
        this.image = image;
    }
}
