package freezy.freezy_be.productTemplates;

import freezy.freezy_be.auth.AppUser;
import freezy.freezy_be.auth.Role;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductTemplateService {

    private final ProductTemplateRepository productTemplateRepository;


    private ProductTemplate toEntity(ProductTemplateRequest  request) {

        ProductTemplate productTemplate = new ProductTemplate();
        productTemplate.setName(request.getName());
        productTemplate.setConservazione(request.getConservazione());
        productTemplate.setTipo(request.getTipo());
        productTemplate.setUnitaDiMisura(request.getUnitaDiMisura());
        productTemplate.setImage(request.getImage());
        return productTemplate;
    }

    private ProductTemplateResponse toDTO(ProductTemplate product){
        return new ProductTemplateResponse(
                product.getId(),
                product.getName(),
                product.getTipo(),
                product.getConservazione(),
                product.getUnitaDiMisura(),
                product.getImage()
        );
    }

    public ProductTemplateResponse  create(ProductTemplateRequest request, AppUser adminLoggato) {
        boolean isAdmin = adminLoggato.getRoles().contains(Role.ROLE_ADMIN);
        if (!isAdmin) {
            throw new RuntimeException("Non hai i permessi per modificare questa fattura");
        } else {
            ProductTemplate productTemplate = toEntity(request);

            productTemplateRepository.save(productTemplate);
            return toDTO(productTemplate);
        }
    }
    public Page<ProductTemplateResponse> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<ProductTemplate> productTemplates = productTemplateRepository.findAll(pageable);
        return productTemplates.map(this::toDTO);
    }

    public ProductTemplateResponse findById(Long id) {
        ProductTemplate productTemplate = productTemplateRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Template non trovato con id: " + id));
        return toDTO(productTemplate);
    }

    public ProductTemplateResponse update(Long id, ProductTemplateRequest request, AppUser adminLoggato) {
        ProductTemplate productTemplate = productTemplateRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Template non trovato con id: " + id));

        boolean isAdmin = adminLoggato.getRoles().contains(Role.ROLE_ADMIN);
        if (!isAdmin) {
            throw new RuntimeException("Non hai i permessi per modificare il template");
        } else {
            productTemplate.setName(request.getName());
            productTemplate.setConservazione(request.getConservazione());
            productTemplate.setTipo(request.getTipo());
            productTemplate.setUnitaDiMisura(request.getUnitaDiMisura());
            productTemplate.setImage(request.getImage());
            productTemplateRepository.save(productTemplate);
            return toDTO(productTemplate);
        }
    }

    public void delete(Long id, AppUser adminLoggato) {
        boolean isAdmin = adminLoggato.getRoles().contains(Role.ROLE_ADMIN);
        if (!isAdmin) {
            throw new RuntimeException("Non hai i permessi per eliminare il template");
        } else {
            ProductTemplate productTemplate = productTemplateRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Template non trovato con id: " + id));
            productTemplateRepository.delete(productTemplate);
        }
    }

}
