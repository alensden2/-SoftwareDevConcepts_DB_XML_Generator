import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Properties;

public class CustomerData {
    // reference from lab 9
        Properties identity = new Properties();
        String username = "";
        String password = "";
        String propertyFilename = "me.prop";
        void dataBaseConnection(){
            try {
                InputStream stream = new FileInputStream( propertyFilename );

                identity.load(stream);

                username = identity.getProperty("username");
                password = identity.getProperty("password");
            } catch (Exception e) {
                return;
            }

            // Do the actual database work now

            Connection connect = null;
            Statement statement = null;
            ResultSet resultSet = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                connect = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false", username, password );
                statement = connect.createStatement();
                statement.execute("use alen;");
                resultSet = statement.executeQuery("call `alen`.`GET_CUSTOMER_NAMES`('2003-02-12', '2003-02-19')");

                while (resultSet.next()) {
                  System.out.print(resultSet.getString("customerName"));
                  System.out.print(resultSet.getString("orderDate"));

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


