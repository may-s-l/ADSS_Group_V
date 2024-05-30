package dev.src.domain;

public class Branch {
    private static int numBranch=0;
    private String name;
    private String address;
    private ManagerEmployee managerEmployee;
    private int branchNum;

    public Branch(String name, String address, ManagerEmployee managerEmployee) {
        this.name = name;
        this.address = address;
        this.managerEmployee = managerEmployee;
        this.branchNum=numBranch+1;
        numBranch=+1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ManagerEmployee getManagerEmployee() {
        return managerEmployee;
    }

    public void setManagerEmployee(ManagerEmployee managerEmployee) {
        this.managerEmployee = managerEmployee;
    }

    public int getBranchNum() {
        return branchNum;
    }


    @Override
    public String toString() {
        return "Branch{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", managerEmployee=" + managerEmployee +
                ", branchNum=" + branchNum +
             '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o instanceof Branch) {
            return false;
        }
        Branch othre = (Branch) o;
        return this.getBranchNum()==othre.getBranchNum();
    }
}