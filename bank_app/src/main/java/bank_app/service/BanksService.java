package bank_app.service;

import bank_app.models.Banks;
import bank_app.repos.BanksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BanksService {

    private BanksRepo banksRepo;

    @Autowired
    public BanksService(BanksRepo banksRepo) {
        this.banksRepo = banksRepo;
    }

    public Banks updateBank(Long id, Banks newBank) {
        return banksRepo.findById(id)
                .map(bank -> {
                    bank.setBic(newBank.getBic());
                    return banksRepo.save(bank);
                })
                .orElseThrow(() -> new RuntimeException("Client not found with id " + id));
    }
}
