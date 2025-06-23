package com.fibra.backendfibra.DTO;

public class CustomerWithAppointmentsDTO {
    private String id;
    private String fullName;
    private String phone;
    private Integer age;
    private String address;
    private String photoUrl;
    private CustomerTypeDTO customerType;
    private int appointmentsCount;

    public CustomerWithAppointmentsDTO(String id, String fullName, String phone, Integer age, String address, String photoUrl, CustomerTypeDTO customerType, int appointmentsCount) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.age = age;
        this.address = address;
        this.photoUrl = photoUrl;
        this.customerType = customerType;
        this.appointmentsCount = appointmentsCount;
    }

    public CustomerWithAppointmentsDTO(com.fibra.backendfibra.Model.Customer customer) {
        this.id = customer.getId() != null ? customer.getId().toString() : null;
        this.fullName = customer.getFullName();
        this.phone = customer.getPhone();
        this.age = customer.getAge();
        this.address = customer.getAddress();
        this.photoUrl = customer.getPhotoUrl();
        if (customer.getCustomerType() != null) {
            this.customerType = new CustomerTypeDTO(
                customer.getCustomerType().getId() != null ? customer.getCustomerType().getId().toString() : null,
                customer.getCustomerType().getName()
            );
        }
        // Por padrão, appointmentsCount = 0. Se precisar, ajuste para buscar a contagem real.
        this.appointmentsCount = 0;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
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
    public CustomerTypeDTO getCustomerType() { return customerType; }
    public void setCustomerType(CustomerTypeDTO customerType) { this.customerType = customerType; }
    public int getAppointmentsCount() { return appointmentsCount; }
    public void setAppointmentsCount(int appointmentsCount) { this.appointmentsCount = appointmentsCount; }

    public static class CustomerTypeDTO {
        private String id;
        private String name;

        public CustomerTypeDTO(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}
