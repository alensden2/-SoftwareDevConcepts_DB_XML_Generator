CREATE DEFINER=`alen`@`129.173.0.0/255.255.0.0` PROCEDURE `GET_OFFICE_LIST_CITES_CUSTOMERS`(
in startDate date,
in endDate date,
in city varchar(255)
)
BEGIN
select sum(priceEach*quantityOrdered) as customerSalesValue, offices.territory, offices.city, customerName, count(employeeNumber) as employeeCount from customers
	join employees on customers.salesRepEmployeeNumber = employees.employeeNumber
    join orders on customers.customerNumber = orders.customerNumber
    join orderdetails on orders.orderNumber = orderdetails.orderNumber
    join offices on employees.officeCode = offices.officeCode where
    orders.orderDate >= startDate and orders.orderDate <= endDate and
    offices.city=city;
END