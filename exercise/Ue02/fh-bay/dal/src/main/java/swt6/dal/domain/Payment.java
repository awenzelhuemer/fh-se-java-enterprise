package swt6.dal.domain;

import javax.persistence.*;
import java.io.Serializable;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "payment_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("base")
@Entity
public class Payment extends BaseEntity implements Serializable {

    private String owner;

    public Payment() {
    }

    public Payment(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
