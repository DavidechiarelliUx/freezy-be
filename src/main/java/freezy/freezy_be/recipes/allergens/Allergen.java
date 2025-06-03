package freezy.freezy_be.recipes.allergens;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Allergen")

public class Allergen {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String icona;

    public Allergen(String name, String icona) {
        this.name = name;
        this.icona = icona;
    }
}
