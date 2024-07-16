package bank_app.repos;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomersRepo extends JpaRepository<bank_app.models.Customers, Long> {
    List<bank_app.models.Customers> findAll(Sort sort);
}
