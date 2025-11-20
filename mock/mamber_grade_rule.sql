INSERT INTO member_grade_rule (
	id,
    grade, 
    min_use_count_last1year,    
    min_use_count_last5years,
    min_max_price, 
    min_total_price_last5years,
    priority
) 
VALUES 
(1,	'BLACK',   1,  5,  300000,    1000000,   1),
(2,	'PURPLE',  1,  4,  200000,    500000,    2),
(3,	'BLUE',    1,  3,  150000,    300000,    3),
(4,	'GREEN',   1,  2,  100000,    150000,    4);
