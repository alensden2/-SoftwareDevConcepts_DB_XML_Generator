/**
 * Software Development Concepts
 * 
 * @author Alen Santosh John
 * @author B00930528
 * 
 *     
 */
import DTOs.CustomerInformationDTO;
import DTOs.OfficeListDTO;
import DTOs.ProductListDTO;

import java.util.ArrayList;
import java.util.List;

public class XMLBuilder {
    CustomerData customerData = new CustomerData();
    ProductList productList = new ProductList();
    OfficeList officeList = new OfficeList();

    List<CustomerInformationDTO> customerListFromDB = new ArrayList<>();
    List<ProductListDTO> productListFromDB = new ArrayList<>();
    List<OfficeListDTO> officeListFromDB = new ArrayList<>();

    void generateReports(String startDate, String endDate) {
        customerListFromDB = customerData.customerList(startDate, endDate);
        productListFromDB = productList.productList(startDate, endDate);
        officeListFromDB = officeList.officeList(startDate,endDate);
    }

}
