package swt6.domain;

import javax.persistence.*;

// @Entity
@Embeddable
public class Address {

    private String address;

    public Address() {
    }

    public Address(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Address{address='" + address + '\'' +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
