import DTOs.CustomerInformationDTO;
import DTOs.OfficeListDTO;
import DTOs.ProductListDTO;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        XMLBuilder xmlBuilder = new XMLBuilder();

        xmlBuilder.generateReports("'2003-02-12'", "'2003-12-19'");

//        CustomerData customerData = new CustomerData();
//        customerData.customerList("'2003-02-12'", "'2003-02-19'");
//        List<CustomerInformationDTO> a = customerData.test();
//    for(int i =0 ; i< a.size(); i++){
//        System.out.println(a.get(i).customerName);
//        System.out.println(a.get(i).orderValue);
//        System.out.println(a.get(i).country);
//        System.out.println(a.get(i).city);
//        System.out.println(a.get(i).address);

//    }


}
}