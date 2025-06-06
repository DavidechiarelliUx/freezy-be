package freezy.freezy_be.fridgeProducts;

import freezy.freezy_be.auth.AppUser;
import freezy.freezy_be.productTemplates.ProductTemplate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fridgeProducts")

public class FridgeProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "dataScadenza", nullable = false)
    private LocalDate dataScadenza;

    @Column(name = "quantita", nullable = false)
    private int quantita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private ProductTemplate productTemplate;

    //quando avremo utente lo aggiungeremo metteremo qui
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser owner;
}
