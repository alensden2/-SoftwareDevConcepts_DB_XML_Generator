package DTOs;

public class OfficeListDTO {
    public String customerSalesValue;
    public String territory;
    public String city;
    public String customerName;
    public String employeeCount;

    public OfficeListDTO(String customerSalesValue, String territory, String city, String customerName, String employeeCount) {
        this.customerSalesValue = customerSalesValue;
        this.territory = territory;
        this.city = city;
        this.customerName = customerName;
        this.employeeCount = employeeCount;
    }
}
