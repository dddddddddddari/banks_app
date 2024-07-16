package bank_app.service;

import bank_app.repos.BanksRepo;
import bank_app.repos.ContributionRepo;
import bank_app.repos.CustomersRepo;
import org.springframework.stereotype.Service;

@Service
public class ContributionService {

    private bank_app.repos.ContributionRepo contributionRepoRepo;
    private BanksRepo banksRepo;
    private CustomersRepo customersRepo;

    public ContributionService(bank_app.repos.ContributionRepo contributionRepoRepo, BanksRepo banksRepo, CustomersRepo customersRepo) {
        this.contributionRepoRepo = contributionRepoRepo;
        this.banksRepo = banksRepo;
        this.customersRepo = customersRepo;
    }

    public bank_app.models.Contribution updateDeposit(Long id, bank_app.models.Contribution newDepositData) {
        if (!customersRepo.existsById(newDepositData.getCustomersId()))
        if (!banksRepo.existsById(newDepositData.getBankId()))
        return contributionRepoRepo.findById(id)
                .map(deposit -> {
                    deposit.setBankId(newDepositData.getBankId());
                    deposit.setPercent(newDepositData.getPercent());
                    deposit.setOpenDate(newDepositData.getOpenDate());
                    deposit.setTermDate(newDepositData.getTermDate());
                    deposit.setCustomersId(newDepositData.getCustomersId());
                    return contributionRepoRepo.save(deposit);
                })
                .orElseThrow(() -> new RuntimeException("Client not found with id " + id));
        return newDepositData;
    }
}
