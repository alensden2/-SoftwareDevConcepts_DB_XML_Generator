/**
 * Data transfer object for product list
 */
package DTOs;

public class ProductListDTO {
    public String productName;
    public String unitsSold;
    public String customerName;
    public String date;
    public String productLine;

    public ProductListDTO(String productName, String unitsSold, String customerName, String date, String productLine) {
        this.productName = productName;
        this.unitsSold = unitsSold;
        this.customerName = customerName;
        this.date = date;
        this.productLine = productLine;
    }
}
