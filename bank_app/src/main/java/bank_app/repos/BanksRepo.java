package bank_app.repos;

import bank_app.models.Banks;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BanksRepo extends JpaRepository<Banks, Long> {
    List<Banks> findAll(Sort sort);
}