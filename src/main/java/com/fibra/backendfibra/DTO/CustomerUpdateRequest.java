package com.fibra.backendfibra.DTO;

public class CustomerUpdateRequest {
    private String fullName;
    private String phone;
    private Integer age;
    private String address;
    private String photoUrl;
    private Long customerTypeId;

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    public Long getCustomerTypeId() { return customerTypeId; }
    public void setCustomerTypeId(Long customerTypeId) { this.customerTypeId = customerTypeId; }
}

