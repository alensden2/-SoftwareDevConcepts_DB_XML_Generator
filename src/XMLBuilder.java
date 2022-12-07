import DTOs.CustomerInformationDTO;

import java.util.List;

public class XMLBuilder {
    CustomerData customerData = new CustomerData();
    List<CustomerInformationDTO> customerList;

    void generateReports(String startDate, String endDate){
        customerList = customerData.customerList(startDate,endDate);
        for(int i = 0; i<customerList.size(); i++){
            System.out.println(customerList.get(i).customerName);
        }
        //customerData.getOrderValue(startDate,endDate);
    }
}
