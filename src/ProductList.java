import DTOs.ProductListDTO;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProductList {
    Properties identity = new Properties();
    String username = "";
    String password = "";
    String propertyFilename = "me.prop";
    List<String> productNames = new ArrayList<>();
    List<ProductListDTO> productListDTO = new ArrayList<>();

    ProductList() {
        // user and pass for the connection
        try {
            InputStream stream = new FileInputStream(propertyFilename);

            identity.load(stream);

            username = identity.getProperty("username");
            password = identity.getProperty("password");
        } catch (Exception e) {
            return;
        }
    }

    List<ProductListDTO> productList(String startDate, String endDate) {
        // for finding the list o products
        Connection connectForProdNames = null;
        Statement statementForProdNames = null;
        ResultSet resultSetForProdNames = null;



        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // for prod names
            connectForProdNames = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false",
                    username, password);
            statementForProdNames = connectForProdNames.createStatement();
            statementForProdNames.execute("use alen;");


            String storedProcedureNames = "call `alen`.`GET_PRODUCT_NAMES`(" + startDate + ", " + endDate + ")";


            // sp for finding prod names
            resultSetForProdNames = statementForProdNames.executeQuery(storedProcedureNames);

            while (resultSetForProdNames.next()) {
                String productName = resultSetForProdNames.getString("productName");
                productNames.add(productName);
            }

            // closing the connection for the product name
            resultSetForProdNames.close();
            statementForProdNames.close();
            connectForProdNames.close();

            // for prod list
            // for finding the product list details
            Connection connectForProdList = null;
            Statement statementForProdList = null;
            ResultSet resultSetForProdList = null;
            connectForProdList = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false",
                    username, password);
            statementForProdList= connectForProdList.createStatement();
            statementForProdList.execute("use alen;");
            for(int i = 0; i<productNames.size(); i++){
                String productName = productNames.get(i);

                resultSetForProdList = statementForProdList.executeQuery(
                        " select productName, quantityOrdered, customerName, orderDate, productLine from customers \n" +
                                "\tjoin orders on customers.customerNumber = orders.customerNumber\n" +
                                "    join orderdetails on orderdetails.orderNumber = orders.orderNumber\n" +
                                "    join products on products.productCode = orderdetails.productCode\n" +
                                "\t\twhere orders.orderDate >= "+startDate+" and orders.orderDate <= "+endDate+"\n" +
                                "        and products.productName = \""+ productName +"\"\n" +
                                "        order by productName;"
                );

                while (resultSetForProdList.next()) {

                    String produceName = resultSetForProdList.getString("productName");
                    String quantityOrdered =  resultSetForProdList.getString("quantityOrdered");
                    String customerName =  resultSetForProdList.getString("customerName");
                    String orderDate =  resultSetForProdList.getString("orderDate");
                    String productLine =  resultSetForProdList.getString("productLine");

                    ProductListDTO productListDTO1 = new ProductListDTO(produceName, quantityOrdered, customerName, orderDate, productLine);
                    productListDTO.add(productListDTO1);
                }
            }

            resultSetForProdList.close();
            statementForProdList.close();
            connectForProdList.close();

        } catch (Exception e) {
            System.out.println("Connection failed");
            System.out.println(e.getMessage());
        }

        return productListDTO;
    }

    List<ProductListDTO> testMethod() {
        return productListDTO;
    }
}
