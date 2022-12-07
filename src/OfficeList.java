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

    List<OfficeListDTO> officeList(String startDate, String endDate){
        Connection connectForCities = null;
        Statement statementForCities = null;
        ResultSet resultSetForCities = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connectForCities = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false", username, password);
            statementForCities = connectForCities.createStatement();
            statementForCities.execute("use alen;");

            String storedProcForNamesOfCity = "call `alen`.`GET_OFFICE_LIST_NAMES`(" + startDate + ", " + endDate + ")";
            resultSetForCities = statementForCities.executeQuery(storedProcForNamesOfCity);

            while (resultSetForCities.next()) {
                String cityName = resultSetForCities.getString("city");
                officeCities.add(cityName);
            }

            resultSetForCities.close();
            statementForCities.close();
            connectForCities.close();

            // for the detailed list
            Connection connectForOfficeList = null;
            Statement statementForOfficeList = null;
            ResultSet resultSetForOfficeList = null;
            connectForOfficeList = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false",
                    username, password);
            statementForOfficeList= connectForOfficeList.createStatement();
            statementForOfficeList.execute("use alen;");
            for(int i = 0; i<officeCities.size(); i++){
                String currentCityName = "\""+officeCities.get(i)+"\"";
                String storedProcedureForOfficeList = "call `alen`.`GET_OFFICE_LIST_CITES_CUSTOMERS`(" + startDate + ", " + endDate + ","+currentCityName+")";
                resultSetForOfficeList = statementForOfficeList.executeQuery(storedProcedureForOfficeList);

                while (resultSetForOfficeList.next()) {

                    String custSalesVal = resultSetForOfficeList.getString("customerSalesValue");
                    String territory =  resultSetForOfficeList.getString("territory");
                    String city = resultSetForOfficeList.getString("city");
                    String custName = resultSetForOfficeList.getString("customerName");
                    String employeeCount = resultSetForOfficeList.getString("employeeCount");

                    OfficeListDTO officeListDTO = new OfficeListDTO(custSalesVal,territory,city,custName,employeeCount);
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

    List<String> testCon(){
        return officeCities;
    }
    List<OfficeListDTO> testCon2(){
        return officeListDTOList;
    }
}
