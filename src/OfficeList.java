import DTOs.CustomerInformationDTO;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Properties;

public class OfficeList {
    Properties identity = new Properties();
    String username = "";
    String password = "";
    String propertyFilename = "me.prop";

    OfficeList() {

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

    void testConnection(){
        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connect = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false", username, password);
            statement = connect.createStatement();
            statement.execute("use alen;");
            String stat = "call `alen`.`GET_CUSTOMER_LIST`('2003-02-12', '2003-02-19')";
            resultSet = statement.executeQuery(" select productName, quantityOrdered, customerName, orderDate, productLine from customers \n" +
                    "\tjoin orders on customers.customerNumber = orders.customerNumber\n" +
                    "    join orderdetails on orderdetails.orderNumber = orders.orderNumber\n" +
                    "    join products on products.productCode = orderdetails.productCode\n" +
                    "\t\twhere orders.orderDate >= '2003-02-12' and orders.orderDate <= '2003-02-19'\n" +
                    "        and products.productName = '1980s Black Hawk Helicopter'\n" +
                    "        order by productName;");

            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }

            resultSet.close();
            statement.close();
            connect.close();
        } catch (Exception e) {
            System.out.println("Connection failed");
            System.out.println(e.getMessage());
        }
    }
}
