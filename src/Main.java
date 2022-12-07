import DTOs.ProductListDTO;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        XMLBuilder xmlBuilder = new XMLBuilder();

        //xmlBuilder.generateReports("'2003-02-12'", "'2003-02-19'");

//        OfficeList officeList = new OfficeList();
//        officeList.testConnection();
        ProductList productList = new ProductList();
        productList.productList("\"2003-02-12\"", "\"2003-09-19\"");
        List<ProductListDTO> productListDTO = new ArrayList<>();
        productListDTO = productList.testMethod();
        for(int i = 0; i<productListDTO.size(); i++){
          System.out.println(productListDTO.get(i).customerName);
            System.out.println(productListDTO.get(i).productLine);
            System.out.println(productListDTO.get(i).date);
            System.out.println(productListDTO.get(i).unitsSold);System.out.println(productListDTO.get(i).productName);
            System.out.println("---");

      }

    }
}