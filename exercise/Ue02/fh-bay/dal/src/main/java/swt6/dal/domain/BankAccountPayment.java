package swt6.dal.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("bank")
public class BankAccountPayment extends Payment {
    private String bic;
    private String iban;

    public BankAccountPayment() {
    }

    public BankAccountPayment(String owner, String bic, String iban) {
        super(owner);
        this.bic = bic;
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
