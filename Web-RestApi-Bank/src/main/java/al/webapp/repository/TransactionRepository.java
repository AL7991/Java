package al.webapp.repository;

import al.webapp.objects.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findAllByUserAccountIdOrderByIdDesc(Long id);
    List<Transaction> findAllByUserAccountIdOrderByIdDesc(Long id, Pageable pageable);

}
