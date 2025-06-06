package freezy.freezy_be.fridgeProducts;


import freezy.freezy_be.auth.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FridgeProductRepository extends JpaRepository<FridgeProduct, Long> {
    List<FridgeProduct> findByOwner(AppUser owner);
}
