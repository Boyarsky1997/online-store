SELECT *
FROM product
         INNER JOIN product_in_order
                    on product.id = product_in_order.product_id
where product_in_order.order_id = ?