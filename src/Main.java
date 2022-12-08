import DTOs.CustomerInformationDTO;
import DTOs.OfficeListDTO;
import DTOs.ProductListDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        /**
         * Takes the user input and calls the xml builder
         */
        XMLBuilder xmlBuilder = new XMLBuilder();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Start date");
        String startDate = sc.nextLine();

        System.out.println("Enter end date");
        String endDate = sc.nextLine();

        startDate = "\"" + startDate + "\"";
        endDate = "\"" + endDate + "\"";

        System.out.println("Name of the XML");
        String fileName = sc.nextLine();
        xmlBuilder.generateReports(startDate, endDate, fileName);

    }
}