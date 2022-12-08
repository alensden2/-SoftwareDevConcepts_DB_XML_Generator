CREATE DEFINER=`alen`@`129.173.0.0/255.255.0.0` PROCEDURE `GET_OFFICE_LIST_NAMES`(
in startDate date,
in endDate date
)
BEGIN
select distinct offices.city from customers
	join employees on customers.salesRepEmployeeNumber = employees.employeeNumber
    join orders on customers.customerNumber = orders.customerNumber
    join offices on employees.officeCode = offices.officeCode where orders.orderDate >= startDate and orders.orderDate <= endDate;

END