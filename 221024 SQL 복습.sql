--------------------------------------------------------
-- 고객번호, 고객명, 고객전화번호, 담당직원명, 담당직원직급, 담당직원연봉등급 을 출력하면?
-- 	 <조건>담당직원이 없는 고객도 포함		
--------------------------------------------------------
select
       c.cus_ no          "고객번호"
       , c.cus_name       "고객명"
       , c.tel_num        "고객전화번호"
       , e.emp_name       "담당직원명"
       , e.jikup          "담당직원직급"
       , s.sal_grade_no    "담당직원연봉등급"


from customer c, employee e, salary_grade s
where c.emp_no = e.emp_no(+)
and e.salary between s.min_salary(+) and s.max_salary(+)


-------------------
-- ANSI outer join
-------------------
select
       c.cus_ no          "고객번호"
       , c.cus_name       "고객명"
       , c.tel_num        "고객전화번호"
       , e.emp_name       "담당직원명"
       , e.jikup          "담당직원직급"
       , s.sal_grade_no    "담당직원연봉등급"


from ( customer c left outer join employee e )   left outer join  salary_grade s

on ( c.emp_no = e.emp_no ) and e.salary between s.min_salary and s.max_salary


--------------------------------------------------------
-- 고객번호, 고객명, 고객주민번호 를 출력하면?
-- 단, 연봉이 3000 이상인 담당직원이 담당한 고객이어야 한다.	
--------------------------------------------------------
select 
      c.cus_no             "고객번호"
      , c.cus_name         "고객명"
      , c.jumin_num        "고객주민번호"
 from customer c, employee e
 
where c.emp_no = e.emp_no
and e.salary >=3000


-------------------
-- ANSI inner join
-------------------
select 
      c.cus_no             "고객번호"
      , c.cus_name         "고객명"
      , c.jumin_num        "고객주민번호"
 from customer c inner join employee e

on c.emp_no = e.emp_no
where e.salary >=3000


--------------------------------------------------------
-- 고객번호, 고객명, 고객주민번호 를 출력하면?
-- 단, 40살 이상인 담당직원이 담당한 고객이어야 한다.	
--------------------------------------------------------
select 
      c.cus_no             "고객번호"
      , c.cus_name         "고객명"
      , c.jumin_num        "고객주민번호"
from customer c, employee e
where c.emp_no = e.emp_no
and 

( to_number (to_char (sysdate, 'YYYY' ))
      - 
   to_number( case substr(e.jumin_num, 7, 1) 
    when '1' then '19'
    when '2' then '19'
    else '20'
    end
    || substr(e.jumin_num, 1, 6))+1
    ) 
     >= 40
-------------------
-- ANSI inner join
-------------------
select 
      c.cus_no             "고객번호"
      , c.cus_name         "고객명"
      , c.jumin_num        "고객주민번호"
from customer c inner join employee e on c.emp_no = e.emp_no

where 
( to_number ( to_char( sysdate, 'YYYY') )
- 
to_number( decode ( substr(e.jumin_num, 7, 1), '1', '19', '2', '19', '20' ) 
|| substr(e.jumin_num, 1, 6)  ) +1
) >= 40
