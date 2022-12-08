
/**
 * Software Development Concepts
 *
 * @author Alen Santosh John
 * @author B00930528
 */

import DTOs.CustomerInformationDTO;
import DTOs.OfficeListDTO;
import DTOs.ProductListDTO;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XMLBuilder {
    CustomerData customerData = new CustomerData();
    ProductList productList = new ProductList();
    OfficeList officeList = new OfficeList();

    List<CustomerInformationDTO> customerListFromDB = new ArrayList<>();
    List<ProductListDTO> productListFromDB = new ArrayList<>();
    List<OfficeListDTO> officeListFromDB = new ArrayList<>();

    /**
     * Generates the XML Document
     * 
     * @param startDate
     * @param endDate
     * @param xmlName
     */
    void generateReports(String startDate, String endDate, String xmlName) {
        /**
         * reference from -
         * https://examples.javacodegeeks.com/core-java/xml/parsers/documentbuilderfactory/create-xml-file-in-java-using-dom-parser-example/
         */

        customerListFromDB = customerData.customerList(startDate, endDate);
        productListFromDB = productList.productList(startDate, endDate);
        officeListFromDB = officeList.officeList(startDate, endDate);

        final String xmlFilePath = "/home/cynos/IdeaProjects/assignment_5/alen/" + xmlName;
        ;
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = null;

            try {
                documentBuilder = documentFactory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            }

            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("time_period_summary");
            document.appendChild(root);

            // employee element
            Element year = document.createElement("year");

            root.appendChild(year);
            Element startTime = document.createElement("start_date");

            Element endTime = document.createElement("end_date");
            year.appendChild(startTime);
            year.appendChild(endTime);
            startTime.appendChild(document.createTextNode(startDate));
            endTime.appendChild(document.createTextNode(endDate));

            Element customerList = document.createElement("customer_list");

            // Generates the Customer List
            for (int i = 0; i < customerListFromDB.size(); i++) {
                Element customer = document.createElement("customer");
                Element customerName = document.createElement("customer_name");
                Element address = document.createElement("address");
                Element orderValue = document.createElement("order_value");
                Element outstandingBalance = document.createElement("outstanding_balance");
                customerName.appendChild(document.createTextNode(customerListFromDB.get(i).customerName));
                address.appendChild(document.createTextNode(customerListFromDB.get(i).address));
                orderValue.appendChild(document.createTextNode(customerListFromDB.get(i).orderValue));
                outstandingBalance.appendChild(document.createTextNode(customerListFromDB.get(i).outstandingBalance));
                customer.appendChild(outstandingBalance);
                customer.appendChild(orderValue);
                customer.appendChild(address);
                customer.appendChild(customerName);
                customerList.appendChild(customer);
            }
            root.appendChild(customerList);
            Element productList = document.createElement("product_list");

            // Generated the product list
            for (int i = 0; i < productListFromDB.size(); i++) {
                Element prdName = document.createElement("product_name");
                Element prdLine = document.createElement("product_line_name");
                Element introDate = document.createElement("introduction_date");
                Element CustSales = document.createElement("customer_sales");
                prdName.appendChild(document.createTextNode(productListFromDB.get(i).productName));
                prdLine.appendChild(document.createTextNode(productListFromDB.get(i).productLine));
                introDate.appendChild(document.createTextNode(productListFromDB.get(i).date));
                CustSales.appendChild(document.createTextNode(productListFromDB.get(i).unitsSold));
                productList.appendChild(prdName);
                productList.appendChild(prdLine);
                productList.appendChild(introDate);
                productList.appendChild(CustSales);
            }
            Element officeList = document.createElement("office_list");
            root.appendChild(officeList);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = null;
            try {
                transformer = transformerFactory.newTransformer();
            } catch (TransformerConfigurationException e) {
                throw new RuntimeException(e);
            }
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            // If you use
            // StreamResult result = new StreamResult(System.out);
            // the output will be pushed to the standard output ...
            // You can use that for debugging

            try {
                transformer.transform(domSource, streamResult);
            } catch (TransformerException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Done creating XML File");

        } finally {
            System.out.println("");
        }
    }

}
