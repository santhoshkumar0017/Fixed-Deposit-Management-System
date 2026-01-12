package com.fdms.service;

import com.fdms.dao.FDAccountDAO;
import com.fdms.model.FDAccount;

import java.time.LocalDate;
import java.util.List;

public class FDAccountService {

    private final FDAccountDAO fdAccountDAO = new FDAccountDAO();

    public static Long openFD(FDAccount fd) throws Exception {


        if (fd.getPrincipalAmount() <= 0) {
            throw new IllegalArgumentException("Principal amount must be > 0");
        }
        if (fd.getInterestRate() <= 0) {
            throw new IllegalArgumentException("Interest rate must be > 0");
        }


        if (fd.getMaturityDate() == null) {
            fd.setMaturityDate(fd.getStartDate().plusMonths(12)); // 1 year default
        }

        fd.setStatus("OPEN");
        return FDAccountDAO.create(fd);
    }

    public FDAccount getFDById(Long id) throws Exception {
        return fdAccountDAO.findById(id);
    }

    public static List<FDAccount> getFDsByCustomerId(Long customerId) throws Exception {
        return FDAccountDAO.findByCustomerId(customerId);
    }

    public boolean closeFD(Long id) throws Exception {
        return fdAccountDAO.updateStatus(id, "CLOSED");
    }


    public double calculateMaturityAmount(FDAccount fd) {
        double principal = fd.getPrincipalAmount();
        double rate = fd.getInterestRate() / 100;

        long months = java.time.temporal.ChronoUnit.MONTHS.between(fd.getStartDate(), fd.getMaturityDate());
        double years = months / 12.0;

        return principal + (principal * rate * years);
    }
}
