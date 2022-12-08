
/**
 * Software Development Concepts
 * 
 * @author Alen Santosh John
 * @author B00930528
 * 
 *     
 */
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
    // reference for connection from CSCI 3901 Lab work
    Properties identity = new Properties();
    List<CustomerInformationDTO> customerListDTO = new ArrayList<>();
    String username = "";
    String password = "";
    String propertyFilename = "me.prop";

    /**
     * DB Connection configuration, User and password
     * Set by constructor
     */
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

    /**
     *
     * @param startDate
     * @param endDate
     * @return
     *
     *         get the customer list using the stored procedure
     */
    List<CustomerInformationDTO> customerList(String startDate, String endDate) {
        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connect = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false",
                    username, password);
            statement = connect.createStatement();
            statement.execute("use alen;");
            /*
             * Stored procedure for getting the customer list
             */
            String stat = "call `alen`.`GET_CUSTOMER_LIST`(" + startDate + ", " + endDate + ")";
            resultSet = statement.executeQuery(stat);

            while (resultSet.next()) {
                String customerName = resultSet.getString("customerName");
                String address1 = resultSet.getString("addressLine1");
                String address2 = resultSet.getString("addressLine2");
                String country = resultSet.getString("country");
                String city = resultSet.getString("city");
                String postalCode = resultSet.getString("postalCode");
                /*
                 * get order value from a different query
                 */
                String orderValue = getOrderValue(startDate, endDate, customerName);
                if (address1 == null) {
                    address1 = ""; // changing null to "" to avoid nullAlen
                } else if (address2 == null) {
                    address2 = "";
                }
                String streetAddress = address1 + address2;
                /**
                 * Getting the total amount paid till date (end date)
                 */
                String totalPaidAmt = getAmountPaidTillDate(endDate,customerName);
                if(totalPaidAmt == null){
                    totalPaidAmt = "0";
                }
                Double outstandingAmount = (Double.parseDouble(totalPaidAmt) - Double.parseDouble(orderValue));
                outstandingAmount = Math.abs(outstandingAmount);
                /* insert for total order outstanding */
                CustomerInformationDTO customerInformationDTO = new CustomerInformationDTO(customerName, streetAddress,
                        city, country, postalCode, orderValue, outstandingAmount.toString());
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

    String getOrderValue(String startDate, String endDate, String custName) {
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
            /*
             * Get the order value from the stored procedure
             */
            orderValue = statement.executeQuery("call `alen`.`GET_CUSTOMER_ORDER_VALUE_FOR_TIME`(" + startDate + ", "
                    + endDate + "," + "\"" + custName + "\"" + ")");

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

    String getAmountPaidTillDate(String endDate, String custName) {
        Connection connect = null;
        Statement statement = null;
        ResultSet orderValue = null;
        String totalAmountPaidTillDate = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connect = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false",
                    username, password);
            statement = connect.createStatement();
            statement.execute("use alen;");
            /*
             * Get the order value from the stored procedure
             */
            orderValue = statement.executeQuery("call `alen`.`GET_CUSTOMER_OUTSTANDING_BALANCE`("+endDate+"," + "\"" + custName + "\"" + ")");
            while (orderValue.next()) {
                totalAmountPaidTillDate = orderValue.getString("outstandingBalance");
            }

            orderValue.close();
            statement.close();
            connect.close();
        } catch (Exception e) {
            System.out.println("Connection failed");
            System.out.println(e.getMessage());
        }
        return totalAmountPaidTillDate;
    }

    List<CustomerInformationDTO> test(){
        return customerListDTO;
    }
}
