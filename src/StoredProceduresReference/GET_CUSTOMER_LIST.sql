CREATE DEFINER=`alen`@`129.173.0.0/255.255.0.0` PROCEDURE `GET_CUSTOMER_LIST`(
in startDate date,
in endDate date
)
BEGIN
select DISTINCT customers.addressLine1, customers.addressLine2, customers.country,
city,
customers.postalCode, customers.country,customers.customerName
 from customers
 join orders on customers.customerNumber = orders.customerNumber
 join orderdetails on orderdetails.orderNumber = orders.orderNumber
 where orders.orderDate >= startDate and orders.orderDate <= endDate;
END