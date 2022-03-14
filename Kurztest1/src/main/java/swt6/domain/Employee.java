package swt6.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    @Transient
    private String zwievel;

    @ElementCollection
    @CollectionTable(name = "EMPL_PHONES", joinColumns = @JoinColumn(name = "EMPL_ID"))
    @Column(name = "PHONE_NR")
    private Set<String> phones = new HashSet<>();

    // @JoinTable(name = "ADDRESS_RELATIONS",
    //         joinColumns = @JoinColumn(name = "EMPL_ID"),
    //         inverseJoinColumns = @JoinColumn(name = "ADDRESS_ID"))
    // @OneToMany(
    //         orphanRemoval = true,
    //         fetch = FetchType.EAGER,
    //         cascade = CascadeType.MERGE
    // )
    // @JoinColumn(name = "EMPL_ID")
    @Embedded
    @AttributeOverride(name = "address", column = @Column(name = "addressline1"))
    private Address address1;

    @Embedded
    @AttributeOverride(name = "address", column = @Column(name = "addressline2"))
    private Address address2;

    @ManyToMany(mappedBy = "employees", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Channel> channels = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress1() {
        return address1;
    }

    public void setAddress1(Address address1) {
        this.address1 = address1;
    }

    public Address getAddress2() {
        return address2;
    }

    public void setAddress2(Address address2) {
        this.address2 = address2;
    }

    public void addChannel(Channel channel) {
        this.channels.add(channel);
    }

    public Set<String> getPhones() {
        return phones;
    }

    public void setPhones(Set<String> phones) {
        this.phones = phones;
    }

    public Set<Channel> getChannels() {
        return channels;
    }

    public void setChannels(Set<Channel> channels) {
        this.channels = channels;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phones=" + phones +
                ", address1=" + address1 +
                ", address2=" + address2 +
                ", channels=" + channels +
                '}';
    }
}
