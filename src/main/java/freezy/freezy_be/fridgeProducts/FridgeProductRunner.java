package freezy.freezy_be.fridgeProducts;

import freezy.freezy_be.productTemplates.ProductTemplate;
import freezy.freezy_be.productTemplates.ProductTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class FridgeProductRunner implements CommandLineRunner {

    @Autowired
    private FridgeProductRepository fridgeProductRepository;

    @Autowired
    private ProductTemplateRepository productTemplateRepository;

    @Override
    public void run(String... args) throws Exception {

        if (fridgeProductRepository.count() == 0) {
            ProductTemplate template = productTemplateRepository.findById(2L)
                    .orElseThrow(() -> new RuntimeException("Template non trovato"));

            FridgeProduct fridgeProduct = new FridgeProduct();
            fridgeProduct.setProductTemplate(template);
            fridgeProduct.setQuantita(100);
            fridgeProduct.setDataScadenza(LocalDate.now().plusDays(7));

            fridgeProductRepository.save(fridgeProduct);
            System.out.println("Prodotto salvato con successo");
        }
    }
}
