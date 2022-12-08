CREATE DEFINER=`alen`@`129.173.0.0/255.255.0.0` PROCEDURE `GET_CUSTOMER_OUTSTANDING_BALANCE`(
in prodName varchar(255),
in endDate date
)
BEGIN
select sum(payments.amount) as outstandingBalance from customers join orders on customers.customerNumber = orders.customerNumber
join payments on payments.customerNumber = customers.customerNumber where customerName = prodName  and payments.paymentDate<=endDate;
END