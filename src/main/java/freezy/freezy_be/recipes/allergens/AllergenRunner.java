package freezy.freezy_be.recipes.allergens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AllergenRunner implements CommandLineRunner {

    @Autowired
    private AllergenRepository allergenRepository;

    @Override
    public void run(String... args) {
        if (allergenRepository.count() == 0) {
            allergenRepository.save(new Allergen( "Glutine", "gluten.svg"));
            allergenRepository.save(new Allergen("Lattosio", "Milk.svg"));
            allergenRepository.save(new Allergen("Uovo", "egg.svg"));
            allergenRepository.save(new Allergen("Noccioline", "peanut.svg"));
            allergenRepository.save(new Allergen("Pesce", "fish.svg"));
            allergenRepository.save(new Allergen("Crostacei", "Shrimp.svg"));
        }
    }
}
