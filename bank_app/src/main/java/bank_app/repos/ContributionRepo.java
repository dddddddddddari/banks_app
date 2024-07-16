package bank_app.repos;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContributionRepo extends JpaRepository<bank_app.models.Contribution, Long> {
    List<bank_app.models.Contribution> findAll(Sort sort);
}
