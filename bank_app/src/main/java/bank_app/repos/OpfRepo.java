package bank_app.repos;

import bank_app.models.Banks;
import bank_app.models.opf;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpfRepo extends JpaRepository<opf, Long> {
    List<opf> findAll(Sort sort);
}
