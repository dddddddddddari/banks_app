package bank_app.controller;

import bank_app.repos.BanksRepo;
import bank_app.repos.CustomersRepo;
import bank_app.service.ContributionService;
import bank_app.util.SortUtil;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@Validated
@RestController
@RequestMapping("/contribution")
public class ContributionController {

    private final ContributionService contributionService;
    private final bank_app.repos.ContributionRepo contributionRepo;
    private final BanksRepo banksRepo;
    private final CustomersRepo customersRepo;
    final List<String> validSortFields = Arrays.asList("clientId", "bankId", "depositId", "openDate", "percent", "retentionPeriod");

    public ContributionController(ContributionService contributionService, bank_app.repos.ContributionRepo contributionRepo, BanksRepo banksRepo, CustomersRepo customersRepo) {
        this.contributionService = contributionService;
        this.contributionRepo = contributionRepo;
        this.banksRepo = banksRepo;
        this.customersRepo = customersRepo;
    }


    @PutMapping("/{id}")
    public bank_app.models.Contribution updateContribution(@PathVariable Long id, @Valid @RequestBody bank_app.models.Contribution contributionDetails) {
        return contributionService.updateDeposit(id, contributionDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContribution(@PathVariable Long id) {
        if (contributionRepo.existsById(id)) {
            contributionRepo.deleteById(id);
            return ResponseEntity.ok("Удалён");
        }
        return null;
    }
}
