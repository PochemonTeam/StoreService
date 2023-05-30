package pochemon.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.StoreTransaction;

public interface StoreTransactionRepository extends JpaRepository<StoreTransaction, Integer> {
    List<StoreTransaction> findAll();
}
