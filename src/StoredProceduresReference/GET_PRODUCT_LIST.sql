CREATE DEFINER=`alen`@`129.173.0.0/255.255.0.0` PROCEDURE `GET_PRODUCT_LIST`(
in startDate date,
in endDate date,
in prodName varchar(255)
)
BEGIN
select productName, quantityOrdered, customerName, orderDate, productLine from customers
	join orders on customers.customerNumber = orders.customerNumber
    join orderdetails on orderdetails.orderNumber = orders.orderNumber
    join products on products.productCode = orderdetails.productCode
		where orders.orderDate >= 'startDate' and orders.orderDate <= 'endDate'
        and products.productName = prodName
        order by productName;
END