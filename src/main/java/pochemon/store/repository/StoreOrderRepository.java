package pochemon.store.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import pochemon.store.entity.StoreOrder;

public interface StoreOrderRepository extends JpaRepository<StoreOrder, Integer> {
    List<StoreOrder> findAll();
    StoreOrder findByCardId(Integer id);
}
