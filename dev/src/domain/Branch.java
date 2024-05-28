package dev.src.domain;

public class Branch {
    String branch_name;

    public Branch(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null ||!(o instanceof Branch)) {
            return false;
        }
        Branch othre = (Branch) o;
        return this.getBranch_name()==othre.getBranch_name();
    }
}
