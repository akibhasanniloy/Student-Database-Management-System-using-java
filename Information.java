import java.io.Serializable;

public class Information implements Serializable {
    private String ID;
    private String name;
    private String cls;
    private String branch;
    private String address;
    private String email;
    private String rollno;
    private String phoneno;
    private String countryCode;
    private String addedByAdmin;
    private double tuitionFee;
    private double amountPaid;
    private String paymentStatus;

    public String getID() { return ID; }
    public void setID(String ID) { this.ID = ID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCls() { return cls; }
    public void setCls(String cls) { this.cls = cls; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRollno() { return rollno; }
    public void setRollno(String rollno) { this.rollno = rollno; }

    public String getPhoneno() { return phoneno; }
    public void setPhoneno(String phoneno) { this.phoneno = phoneno; }

    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }

    public String getAddedByAdmin() { return addedByAdmin; }
    public void setAddedByAdmin(String addedByAdmin) { this.addedByAdmin = addedByAdmin; }

    public double getTuitionFee() { return tuitionFee; }
    public void setTuitionFee(double tuitionFee) { this.tuitionFee = tuitionFee; }

    public double getAmountPaid() { return amountPaid; }
    public void setAmountPaid(double amountPaid) { this.amountPaid = amountPaid; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}