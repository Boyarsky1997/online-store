select *
from `order`
where `order`.buyer_id = ? and `order`.status = 'PAID';