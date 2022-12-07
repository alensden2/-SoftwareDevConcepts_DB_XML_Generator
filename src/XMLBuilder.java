import DTOs.CustomerInformationDTO;

import java.util.List;

public class XMLBuilder {
    CustomerData customerData = new CustomerData();
    ProductList productList = new ProductList();
    List<CustomerInformationDTO> customerList;

    void generateReports(String startDate, String endDate){
        customerList = customerData.customerList(startDate,endDate);
//        for(int i = 0; i<customerList.size(); i++){
////            System.out.println(customerList.get(i).customerName);
//        }
        //customerData.getOrderValue(startDate,endDate);

        // assembling the product list
//        List<String> name = productList.testMethod();
//        productList.productList(startDate,endDate);
//        for(int i = 0; i<name.size(); i++){
////           System.out.println(name.get(i));
//        }
    }


}
