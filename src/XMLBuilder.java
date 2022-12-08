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

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Attr;
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

    void generateReports(String startDate, String endDate) {
        customerListFromDB = customerData.customerList(startDate, endDate);
        productListFromDB = productList.productList(startDate, endDate);
        officeListFromDB = officeList.officeList(startDate,endDate);

        final String xmlFilePath = "/home/cynos/IdeaProjects/assignment_5/alen/alen.xml";
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
//            root.appendChild(customerList);
            Element productList = document.createElement("product_list");
//            root.appendChild(productList);
            Element officeList = document.createElement("office_list");
//            root.appendChild(officeList);


            //you can also use staff.setAttribute("id", "1") for this
            // firstname element
            Element firstName = document.createElement("firstname");
            firstName.appendChild(document.createTextNode("James"));
            //employee.appendChild(firstName);

            // lastname element
            Element lastname = document.createElement("lastname");
            lastname.appendChild(document.createTextNode("Harley"));
            //employee.appendChild(lastname);

            // create the xml file
            //transform the DOM Object to an XML File

            for(int i = 0; i<customerListFromDB.size(); i++){
                Element customer = document.createElement("customer");
//                customerList.appendChild(customer);

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
