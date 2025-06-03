package freezy.freezy_be.fridgeProducts;

import freezy.freezy_be.productTemplates.ProductTemplate;
import freezy.freezy_be.productTemplates.ProductTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FridgeProductService {
    private final FridgeProductRepository fridgeProductRepository;
    private final ProductTemplateRepository productTemplateRepository;

    private FridgeProduct toEntity(FridgeProductRequest request) {

        ProductTemplate template = productTemplateRepository.findById(request.getProductTemplateId())
                .orElseThrow(() -> new RuntimeException("Template not found"));

        FridgeProduct fridgeProduct = new FridgeProduct();
        fridgeProduct.setDataScadenza(request.getDataScadenza());
        fridgeProduct.setQuantita(request.getQuantita());
        fridgeProduct.setProductTemplate(template);
        return fridgeProduct;
    }

    private FridgeProductResponse toDTO(FridgeProduct fridgeProduct) {
        return new FridgeProductResponse(
                fridgeProduct.getId(),
                fridgeProduct.getDataScadenza(),
                fridgeProduct.getQuantita(),
                fridgeProduct.getProductTemplate().getName(),
                fridgeProduct.getProductTemplate().getImage(),
                fridgeProduct.getProductTemplate().getConservazione(),
                fridgeProduct.getProductTemplate().getTipo().name()
        );
    }

    public FridgeProductResponse create(FridgeProductRequest request) {
        FridgeProduct fridgeProduct = toEntity(request);
        fridgeProductRepository.save(fridgeProduct);
        return toDTO(fridgeProduct);
    }

    public Page<FridgeProductResponse> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<FridgeProduct> fridgeProducts = fridgeProductRepository.findAll(pageable);
        return fridgeProducts.map(this::toDTO);
    }

    public FridgeProductResponse findById(Long id) {
        FridgeProduct fridgeProduct = fridgeProductRepository.findById(id).orElseThrow(() -> new RuntimeException("Prodotto non trovato con id: " + id));
        return toDTO(fridgeProduct);
    }
    public FridgeProductResponse update(Long id, FridgeProductRequest request) {
        FridgeProduct fridgeProduct = fridgeProductRepository.findById(id).orElseThrow(() -> new RuntimeException("Prodotto non trovato con id: " + id));
        fridgeProduct.setDataScadenza(request.getDataScadenza());
        fridgeProduct.setQuantita(request.getQuantita());
        fridgeProductRepository.save(fridgeProduct);
        return toDTO(fridgeProduct);
    }
    public void delete(Long id) {
        FridgeProduct fridgeProduct = fridgeProductRepository.findById(id).orElseThrow(() -> new RuntimeException("Prodotto non trovato con id: " + id));
        fridgeProductRepository.delete(fridgeProduct);
    }
}
