import DTOs.CustomerInformationDTO;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CustomerData {
    // reference from lab 9
    Properties identity = new Properties();
    List<CustomerInformationDTO> customerListDTO = new ArrayList<>();
    String username = "";
    String password = "";
    String propertyFilename = "me.prop";

    CustomerData() {
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

    // get the query outputs
    List<CustomerInformationDTO> customerList(String startDate, String endDate) {
        // Do the actual database work now

        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connect = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false",
                    username, password);
            statement = connect.createStatement();
            statement.execute("use alen;");
            String stat = "call `alen`.`GET_CUSTOMER_LIST`(" + startDate + ", " + endDate + ")";
            resultSet = statement.executeQuery(stat);

            while (resultSet.next()) {
                String customerName = resultSet.getString("customerName");
                String address1 = resultSet.getString("addressLine1");
                String address2 = resultSet.getString("addressLine2");
                String country = resultSet.getString("country");
                String city = resultSet.getString("city");
                String postalCode = resultSet.getString("postalCode");
                String orderValue = getOrderValue(startDate, endDate);
                if (address1 == null) {
                    address1 = ""; //changing null to "" to avoid nullAlen
                } else if (address2 == null) {
                    address2 = "";
                }
                String streetAddress = address1 + address2;
                /* insert for total order outstanding */
                CustomerInformationDTO customerInformationDTO = new CustomerInformationDTO(customerName, streetAddress, city, country, postalCode, orderValue,orderValue);
                customerListDTO.add(customerInformationDTO);
            }

            resultSet.close();
            statement.close();
            connect.close();
        } catch (Exception e) {
            System.out.println("Connection failed");
            System.out.println(e.getMessage());
        }
        return customerListDTO;
    }

    String getOrderValue(String startDate, String endDate) {
        Connection connect = null;
        Statement statement = null;
        ResultSet orderValue = null;
        String orderValueFromDB = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connect = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false",
                    username, password);
            statement = connect.createStatement();
            statement.execute("use alen;");
            orderValue = statement.executeQuery("call `alen`.`GET_CUSTOMER_ORDER_VALUE_FOR_TIME`(" + startDate + ", "
                    + endDate + ",'Rovelli Gifts')");

            while (orderValue.next()) {
                orderValueFromDB = orderValue.getString("orderValue");
            }

            orderValue.close();
            statement.close();
            connect.close();
        } catch (Exception e) {
            System.out.println("Connection failed");
            System.out.println(e.getMessage());
        }
        return orderValueFromDB;
    }
}
