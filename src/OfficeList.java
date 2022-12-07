
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

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OfficeList {
    Properties identity = new Properties();
    String username = "";
    String password = "";
    String propertyFilename = "me.prop";
    List<String> officeCities = new ArrayList<>();
    List<OfficeListDTO> officeListDTOList = new ArrayList<>();

    OfficeList() {

        // reference for connection from CSCI 3901 Lab work
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
     *         Takes the start and end date and uses stored procedure to get desired
     *         output
     *         Returns a list of all the objects casted into a DTO
     *         Data-transfer-object
     */
    List<OfficeListDTO> officeList(String startDate, String endDate) {
        Connection connectForCities = null;
        Statement statementForCities = null;
        ResultSet resultSetForCities = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connectForCities = DriverManager.getConnection(
                    "jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false", username, password);
            statementForCities = connectForCities.createStatement();
            statementForCities.execute("use alen;");

            /*
             * Stored procedure for office list
             * (refer all the stored procedure from
             * StoredProcedures File in the main directory)
             */
            String storedProcForNamesOfCity = "call `alen`.`GET_OFFICE_LIST_NAMES`(" + startDate + ", " + endDate + ")";
            resultSetForCities = statementForCities.executeQuery(storedProcForNamesOfCity);

            /*
             * Casting cities into the city list
             * This is done so that it can be used by the second query
             * The second stored procedure is specific to each city
             */
            while (resultSetForCities.next()) {
                String cityName = resultSetForCities.getString("city");
                officeCities.add(cityName);
            }

            resultSetForCities.close();
            statementForCities.close();
            connectForCities.close();

            /*
             * For the detailed list
             */
            Connection connectForOfficeList = null;
            Statement statementForOfficeList = null;
            ResultSet resultSetForOfficeList = null;
            connectForOfficeList = DriverManager.getConnection(
                    "jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false",
                    username, password);
            statementForOfficeList = connectForOfficeList.createStatement();
            statementForOfficeList.execute("use alen;");

            /*
             * The loop loops through each city captured by the previous stored procedure
             */
            for (int i = 0; i < officeCities.size(); i++) {
                String currentCityName = "\"" + officeCities.get(i) + "\"";
                String storedProcedureForOfficeList = "call `alen`.`GET_OFFICE_LIST_CITES_CUSTOMERS`(" + startDate
                        + ", " + endDate + "," + currentCityName + ")";
                /*
                 * Stored procedure for the detailed list (refer all the stored procedure from
                 * StoredProcedures File in the main directory)
                 */
                resultSetForOfficeList = statementForOfficeList.executeQuery(storedProcedureForOfficeList);

                while (resultSetForOfficeList.next()) {

                    String custSalesVal = resultSetForOfficeList.getString("customerSalesValue");
                    String territory = resultSetForOfficeList.getString("territory");
                    String city = resultSetForOfficeList.getString("city");
                    String custName = resultSetForOfficeList.getString("customerName");
                    String employeeCount = resultSetForOfficeList.getString("employeeCount");

                    OfficeListDTO officeListDTO = new OfficeListDTO(custSalesVal, territory, city, custName,
                            employeeCount);
                    officeListDTOList.add(officeListDTO);
                }
            }

            resultSetForOfficeList.close();
            statementForOfficeList.close();
            connectForOfficeList.close();

        } catch (Exception e) {
            System.out.println("Connection failed");
            System.out.println(e.getMessage());
        }
        return officeListDTOList;
    }

    List<String> testCon() {
        return officeCities;
    }

    List<OfficeListDTO> testCon2() {
        return officeListDTOList;
    }
}
