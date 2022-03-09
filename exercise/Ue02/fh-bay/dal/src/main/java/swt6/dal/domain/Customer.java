// package swt6.dal.domain;
//
// import javax.persistence.*;
// import java.util.HashSet;
// import java.util.Set;
//
// @Entity
// public class Customer extends BaseEntity {
//     private String firstName;
//     private String lastName;
//
//     @Embedded
//     @AttributeOverride(name = "zipCode", column = @Column(name = "billingAddress_zipCode"))
//     @AttributeOverride(name = "city", column = @Column(name = "billingAddress_city"))
//     @AttributeOverride(name = "street", column = @Column(name = "billingAddress_street"))
//     private Address billingAddress;
//
//     @Embedded
//     @AttributeOverride(name = "zipCode", column = @Column(name = "deliveryAddress_zipCode"))
//     @AttributeOverride(name = "city", column = @Column(name = "deliveryAddress_city"))
//     @AttributeOverride(name = "street", column = @Column(name = "deliveryAddress_street"))
//     private Address deliveryAddress;
//
//     @OneToMany
//     private Set<Payment> payments = new HashSet<>();
//
//     public Customer() {
//     }
//
//     public Customer(String firstName, String lastName, Address billingAddress, Address deliveryAddress) {
//         this.firstName = firstName;
//         this.lastName = lastName;
//         this.billingAddress = billingAddress;
//         this.deliveryAddress = deliveryAddress;
//     }
//
//     public String getFirstName() {
//         return firstName;
//     }
//
//     public void setFirstName(String firstName) {
//         this.firstName = firstName;
//     }
//
//     public String getLastName() {
//         return lastName;
//     }
//
//     public void setLastName(String lastName) {
//         this.lastName = lastName;
//     }
//
//     public Address getBillingAddress() {
//         return billingAddress;
//     }
//
//     public void setBillingAddress(Address billingAddress) {
//         this.billingAddress = billingAddress;
//     }
//
//     public Address getDeliveryAddress() {
//         return deliveryAddress;
//     }
//
//     public void setDeliveryAddress(Address deliveryAddress) {
//         this.deliveryAddress = deliveryAddress;
//     }
//
//     public Set<Payment> getPayments() {
//         return payments;
//     }
//
//     public void setPayments(Set<Payment> payments) {
//         this.payments = payments;
//     }
// }
