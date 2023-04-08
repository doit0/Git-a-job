--------------------------------------------------------
-- 부하직원명, 부하직원직급, 소속부서명, 연봉등급, 직속상관명, 직속상관직급, 담당고객명  을 출력하면? <조건> 상관이 있는 직원만 포함.
--------------------------------------------------------
select 
      e1.emp_name           "부하직원명"
      , e1.jikup            "부하직원직급"
      , d.dep_name          "소속부서명"
      , s.salary_grade      "연봉등급"
      , e2.emp_name         "직속상관명"
      , e2.jikup            "직속상관직급"
      , c.cus_name          "담당고객명"

from employee e1, employee e2, dept d, salary s, customer c
where e2.emp_name is not null
and e1.mgr_emp_no = e2.emp_no
and e1.dep_no = d.dep_no
and e1.emp_no = c.emp_no
and e1.salary between (s.min_salary and s.max_salary);


-------------------
-- ANSI join
-------------------
select 
      e1.emp_name           "부하직원명"
      , e1.jikup            "부하직원직급"
      , d.dep_name          "소속부서명"
      , s.salary_grade      "연봉등급"
      , e2.emp_name         "직속상관명"
      , e2.jikup            "직속상관직급"
      , c.cus_name          "담당고객명"

from 
((((employee e1 inner join  employee e2 on  e1.mgr_emp_no = e2.emp_no)
inner join dept d               on e1.dep_no = d.dep_no)
inner join salary s             on e1.salary between (s.min_salary and s.max_salary))
inner join customer c           on e1.emp_no = c.emp_no)

where e2.emp_name is not null



--------------------------------------------------------
-- 고객명, 고객전화번호, 담당직원명, 담당직원직급을 출력하면? <조건>담당직원이 없는 고객도 포함	
--------------------------------------------------------
select 
    c.cus_name                    "고객명"
    , c.tel_num                   "고객전화번호"
    , nvl(e.emp_name, "없음")     "담당직원명" 
    , nvl(e.jikup, "없음")        "담당직원직급"

from customer c, employee 
where c.emp_no = e.emp_no(+)


-------------------
-- ANSI join
-------------------
select 
    c.cus_name                    "고객명"
    , c.tel_num                   "고객전화번호"
    , nvl(e.emp_name, "없음")     "담당직원명" 
    , nvl(e.jikup, "없음")        "담당직원직급"

from customer c left outer join employee on c.emp_no = e.emp_no


--------------------------------------------------------
-- 고객명, 고객전화번호, 담당직원명, 담당직원직급을 출력할 때 담당직원 부서가 10번인 경우만 출력하면?
--	 <조건>담당직원이 없는 고객도 포함	
--------------------------------------------------------
select 
    c.cus_name             "고객명"
    , c.tel_num            "고객전화번호"
    , nvl(e.emp_name, "없음")     "담당직원명" 
    , nvl(e.jikup, "없음")        "담당직원직급"

from customer c, employee e
where c.emp_name = e.emp_name (+)
and e.dep_no(+) = 10;

-------------------
-- ANSI join
-------------------
select 
    c.cus_name                    "고객명"
    , c.tel_num                   "고객전화번호"
    , nvl(e.emp_name, "없음")     "담당직원명" 
    , nvl(e.jikup, "없음")        "담당직원직급"

from customer c left outer join employee e on c.emp_name = e.emp_name
where e.dep_no = 10;

--------------------------------------------------------
-- 고객번호, 고객명, 고객전화번호, 담당직원명, 담당직원직급, 담당직원연봉등급 을 출력하면?
-- 	 <조건>담당직원이 없는 고객도 포함
--------------------------------------------------------
select 
		c.cus_no		"고객번호"
		, c.cus_name		"고객명"
		 ,c.tel_num		"고객전화번호" 
		 ,e.emp_name		"담당직원명"
		 ,e.jikup		"담당직원직급"
		,s.sal_grade_no	"담당직원연봉등급"

	from 
		customer c, employee e, salary_grade s 
	where 
		c.emp_no = e.emp_no(+)	
		and 	
			e.salary>= s.min_money(+) and e.salary <= s.max_salary (+)		--- 항상 딸려 나오는 쪽에 붙어야함. 
		 (e.salary  between s.min_salary(+) and s.max_salary(+) )


--------------------------------------------------------
-- 고객번호, 고객명, 고객주민번호 를 출력하면?
--  단, 연봉이 3000 이상인 담당직원이 담당한 고객이어야 한다.	
--------------------------------------------------------
select 
		c.cus_no		"고객번호"
		, c.cus_name		"고객명"
		, c.jumin_num	"고객주민번호" 

	from customer c, employee e							
	
	where c.emp_no = e.emp_no and e.salary >=3000 		


--------------------------------------------------------
-- ANSI join
--------------------------------------------------------
select 
		c.cus_no		"고객번호"
		, c.cus_name		"고객명"
		, c.jumin_num	"고객주민번호" 

	from customer c inner join employee e on  c.emp_no = e.emp_no
	
	where e.salary >=3000 	


--------------------------------------------------------
-- 고객번호, 고객명, 고객주민번호 를 출력하면?
-- 단, 40살 이상인 담당직원이 담당한 고객이어야 한다.
--------------------------------------------------------

-- ANSI join 으로 풀기
select 
	c.cus_no 		"고객번호"
	, c.cus_name 		"고객명"
	, c.jumin_num		"고객주민번호"
	
	from customer c inner join employee e on c.emp_no = e.emp_no 
	where 
		to_number( to_char(sysdate, 'YYYY'))
		-
		to_number (case substr(e.jumin_num, 7, 1)
			   when '1' then '19'
			   when '2' then '19'
			   else '20'
			   end)
			   || substr(e.jumin_num, 1, 2) ) +1
		>=40
		
		
--------------------------------------------------------		
select 
		c.cus_no		"고객번호"		
		, c.cus_name		"고객명"		
		, c.jumin_num	"고객주민번호" 
	from customer c, employee e	
	where c.emp_no = e.emp_no 
		and
			(to_number( to_char(sysdate, 'YYYY') ) 					
			- 
			to_number( case substr (e.jumin_num, 7,1)
						when '1' then '19'
						when '2' then '19'
					                       else '20'
					end
					||substr(e.jumin_num, 1,2) ) 	+1				
			) >= 40					


--------------------------------------------------------
-- 10번 부서 또는 30번 부서 직원이 담당하는 고객을 검색하면?
--------------------------------------------------------
select 
	c.*
from customer c , employee e on
where  c.emp_no = e.emp_no and (e.dep_no = 10 or e.dep_no =30) 


--------------------------------------------------------
-- 직원명, 직원전화번호 와  고객명, 고객전화번호를 종으로 붙여 출력하라. 조건은 중복하지 말 것.
--------------------------------------------------------
select emp_name "직원명",  	phone	"직원전화번호", 	'직원'	"직원/고객" from employee 			
	union																		
	select cus_name "고객명", tel_num "고객전화번호", '고객'	"직원/고객" from customer



--------------------------------------------------------
--직원명, 직원전화번호 와  고객명, 고객전화번호를 종으로 붙여 출력하라.  조건은 중복 허락.	
--------------------------------------------------------
select emp_name "직원명",  	phone	"직원전화번호", 	'직원'	"직원/고객" from employee 			
	union all																	
	select cus_name "고객명", tel_num "고객전화번호", '고객'	"직원/고객" from customer



--------------------------------------------------------
-- 최고 연봉을 받는 직원을 검색하라.
--------------------------------------------------------
select * from employee 
where salary = (select max(salary) from employee  )


--------------------------------------------------------
-- 평균 연봉을 받는 직원을 검색하라.
--------------------------------------------------------
select * from employee 
where salary = (select avg(salary) from employee  )


--------------------------------------------------------
-- 20번 부서에서 최고 연봉자 직원을 검색하라
--------------------------------------------------------
select * from employee 
where salary = (select  max(salary) from employee where dep_no = 20 ) 
and dep_no = 20;


--------------------------------------------------------
-- 20번 부서의 최고 연봉자와 동일한 연봉을 받는 모든 직원을 검색하라
--------------------------------------------------------
select * from employee 
where salary = (select  max(salary) from employee where dep_no = 20 ) 



--------------------------------------------------------
-- [직원명], [직급], [연봉], [전체연봉에서 차지하는 비율]을 검색하라. 
-- 단 [전체연봉에서 차지하는 비율]은 소수점 버림하고 %로 표현하라
-- 단 높은 비율이 먼저 나오게 정렬하라
--------------------------------------------------------
 select emp_name		
 	, dept
	, floor (salary / (select sum(salary) from employee ) *100) || '%'
	from employee 
order by  floor (salary / (select sum(salary) from employee ) *100) desc
      
     
--------------------------------------------------------
-- 10번 부서 직원들이 관리하는 [고객번호], [고객명], [담당직원번호] 를 검색하면?		
--------------------------------------------------------     

select c.cus_name
	, c.cus_no
	, e.emp_no
	from employee e, customer c
	
where dep_no = 10



--- 혹은


select cus_name
	, cus_no
	, emp_no
	from customer 
	
where emp_no in (select emp_no from employee where dep_no = 10)




