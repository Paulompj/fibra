package com.fibra.backendfibra.DTO;

import java.util.List;

public class CustomersWithAppointmentsResponseDTO {
    private int number;
    private List<CustomerWithAppointmentsDTO> data;
    private int size;
    private int totalPages;
    private long totalElements;

    public CustomersWithAppointmentsResponseDTO(int number, List<CustomerWithAppointmentsDTO> data, int size, int totalPages, long totalElements) {
        this.number = number;
        this.data = data;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }
    public List<CustomerWithAppointmentsDTO> getData() { return data; }
    public void setData(List<CustomerWithAppointmentsDTO> data) { this.data = data; }
    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }
    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }
    public long getTotalElements() { return totalElements; }
    public void setTotalElements(long totalElements) { this.totalElements = totalElements; }
}

