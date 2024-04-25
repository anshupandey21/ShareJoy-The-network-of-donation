package comman1;

public class UserDetails {
    private String DName;
    private String DAdd;
    private String DMob;
    private String DEmail;
    private int DId;
    UserDetails(){}


    public String getDName() {
        return DName;
    }


    public void setDId(int DId) {
        this.DId = DId;
    }
    public int getDId()
    {
        return DId;
    }
    public void setDName(String DName) {
        this.DName = DName;
    }


    public String getDAdd() {
        return DAdd;
    }


    public void setDAdd(String DAdd) {
        this.DAdd = DAdd;
    }


    public String getDMob() {
        return DMob;
    }


    public void setDMob(String DMob) {
        this.DMob = DMob;
    }


    public String getDEmail() {
        return DEmail;
    }


    public void setDEmail(String DEmail) {
        this.DEmail = DEmail;
    }
}

