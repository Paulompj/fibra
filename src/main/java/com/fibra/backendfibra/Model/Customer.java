package com.fibra.backendfibra.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nome completo é obrigatório")
    @Size(max = 100)
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @NotBlank(message = "Telefone é obrigatório")
    @Size(max = 15)
    private String phone;
    @Min(0)
    @Max(150)
    private Integer age;
    @Size(max = 100)
    private String address;
    @Size(max = 200)
    @Column(name = "photo_url")
    private String photoUrl;
    @ManyToOne
    @JoinColumn(name = "customer_type", nullable = false)
    private CustomerType customerType;

    public Customer() {}
    public Customer(Long id, String fullName, String phone, Integer age, String address, String photoUrl, CustomerType customerType) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.age = age;
        this.address = address;
        this.photoUrl = photoUrl;
        this.customerType = customerType;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    public CustomerType getCustomerType() {
        return customerType;
    }
    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }
}
