package DTOs;

public class CustomerInformationDTO {
    public String customerName;
    public String address;
    public String city;
    public String country;
    public String postal;
    public String orderValue;
    public String outstandingBalance;

    public CustomerInformationDTO(String customerName, String address, String city, String country, String postal, String orderValue, String outstandingBalance) {
        this.customerName = customerName;
        this.address = address;
        this.city = city;
        this.country = country;
        this.postal = postal;
        this.orderValue = orderValue;
        this.outstandingBalance = outstandingBalance;
    }
}
