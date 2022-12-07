CREATE DEFINER=`alen`@`129.173.0.0/255.255.0.0` PROCEDURE `GET_PRODUCT_NAMES`(
in startDate date,
in endDate date
)
BEGIN
select distinct productName from customers
	join orders on customers.customerNumber = orders.customerNumber
    join orderdetails on orderdetails.orderNumber = orders.orderNumber
    join products on products.productCode = orderdetails.productCode
		where orders.orderDate >= startDate and orders.orderDate <= endDate;
END