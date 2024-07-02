package dev.src.Domain.Repository;

import dev.src.Data.DAO.BranchDao;
import dev.src.Domain.Branch;

public class BranchRep {

    private BranchDao branchDao;

    public BranchRepositoryImpl() {
        this.branchDao = BranchDao.getInstance();
    }

    @Override
    public void insert(Branch branch) {
        branchDao.insert(branch);
    }

    @Override
    public Branch select(String address) {
        return branchDao.select(address);
    }

    @Override
    public void update(Branch branch) {
        branchDao.update(branch);
    }

    @Override
    public void delete(String address) {
        branchDao.delete(address);
    }
}
