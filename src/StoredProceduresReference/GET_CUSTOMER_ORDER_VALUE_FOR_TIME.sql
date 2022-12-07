CREATE DEFINER=`alen`@`129.173.0.0/255.255.0.0` PROCEDURE `GET_CUSTOMER_ORDER_VALUE_FOR_TIME`(
in startDate date,
in endDate date,
in customerNameCurrent varchar(255)
)
BEGIN
select sum(orderdetails.quantityOrdered*orderdetails.priceEach) as orderValue from customers
	join orders on customers.customerNumber = orders.customerNumber
		join orderdetails on orderdetails.orderNumber = orders.orderNumber
			where orders.orderDate >= startDate and orders.orderDate <= endDate and customerName = customerNameCurrent;
END