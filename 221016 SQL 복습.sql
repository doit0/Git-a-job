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


--------------------------------------------------------
-- 평균 연봉 이상이고 최대 연봉 미만의 [직원명], [연봉], [전체평균연봉], [전체최대연봉]을 출력하면?	
--------------------------------------------------------     
select  emp_name
	, salary
	, (select  avg(salary) from employee)
	, (select  max(salary) from employee)
from employee
where salary >=  (select  avg(salary) from employee)
and salary <  (select  max(salary) from employee)



--------------------------------------------------------
-- 최고 연봉 직원의 관리하는 [직원번호], [직원명], [부서명], [연봉] 을 검색하면?
--------------------------------------------------------    
select e.emp_no			"직원번호"	
	, e.emp_name		"직원명"
	, d.dep_name		"부서명"
	, e.salary		"연봉"
from employee e, dept d
where e.dep_no = d.dep_no
and e.salary = (select max(salary) from employee )



-- or

select   e.emp_no			"직원번호"	
	, e.emp_name			"직원명"
	,(select dep_name from dept d where e.dep_no = d.dep_no)		"부서명"
	, e.salary		"연봉"
from employee e
where e.salary = (select max(salary) from employee )



--------------------------------------------------------
--  담당 고객이 2명 이상인 [직원번호], [직원명], [직급] 을 검색하면?
--------------------------------------------------------    

select 
	e.emp_no
	, e.emp_name
	, e.jikup

from employee e
where (select count(*)  from customer c where c.emp_no = e.emp_no) >= 2


--------------------------------------------------------
-- [직원번호], [직원명], [연봉], [연봉 순위] 을 출력하면?	 단 [연봉 순위]를 오름차순 유지
--------------------------------------------------------	

	select
		e1.emp_no
		, e1.emp_name
		, e1.salary
		, (select count (*)+1 from employee e2 where e2.salary > e1.salary)		
												
												

	from employee e1							
	order by 4;

--------------------------------------------------------
-- [직원번호], [직원명], [담당고객수]를 출력하면? 단 담당고객 수를 내림차순으로 
--------------------------------------------------------    
select
	e.emp_no
	, e.emp_name
	, (select count(*) from customer c where c.emp_no = e.emp_no )

from employee e
order by (select count(*) from customer c where c.emp_no = e.emp_no ) desc;

--------------------------------------------------------
--  [부서명], [부서직원수], [부서담당고객수]를 출력하면? 단 부서직원수가 내림차순
--------------------------------------------------------    
select 
	d.dep_name
	, (select count(*) from employee e where e.dep_no = d.dep_no)
	, (select count (*) from customer c where  c.emp_no=e.emp_no and e.dep_no=d.dep_no )
	
from dept d
order by  (select count(*) from employee e where e.dep_no = d.dep_no) desc;




--------------------------------------------------------
-- [고객번호], [고객명], [고객전화번호], [담당직원명], [담당직원직급], [부서번호] 를 출력하면?	<조건>담당직원이 없는 고객도 포함	
--------------------------------------------------------    

select 
	c.cus_no
	, c.cus_name
	,  c.tel_num	
	, nvl( (select e.emp_name from employee where c.emp_no = e. emp_no), '없음')
	, nvl( (select e.jikup from employee where c.emp_no = e. emp_no), '없음')
	, nvl( (select d.dep_no from dept where c.emp_no = e. emp_no), '없음')
	
from customer c


--------------------------------------------------------
--  [직원번호], [직원명], [직급], [주민번호], [직급서열순위]를 출력하면?
--  단, 직급이 같으면 나이 많은 직원이 [직급서열순위]이다. 그리고 [직급서열순위]를 오름차순으로 유지
--------------------------------------------------------   
select 
	e.emp_no
	, emp_name
	, jikup
	, jumin_num
	, (
		select count(*) from employee 
		where decode(e2.jikup where '사장', 1, '부장', 2, '과장', 3, '대리', 4, '주임', 5, 6)
			<
			decode (e1.jikup, '사장', 1, '부장', 2, '과장', 3, '대리', 4, '주임', 5, 6)	
	)

				or 
			( e2.jikup = e1.jikup			
					and
				to_number
					( decode(  substr(e2.jumin_num, 7,1), '1', '19', '2', '19', '20') ||  substr(e2.jumin_num, 1, 6)
				)
				<
				to_number 
					( decode(  substr(e1.jumin_num, 7,1), '1', '19', '2', '19', '20') ||  substr(e1.jumin_num, 1, 6)
				)								
			)									
		)  "직급서열순위"
		--------------------------------------서브쿼리-------------------------------------------
							
	from employee e1		
	order by "직급서열순위";


--------------------------------------------------------
--  부서별로 [부서번호], [연봉합], [평균연봉], [인원수]를 출력하면? 단 평균연봉은 소수 2째자리에서 반올림 할 것	
--------------------------------------------------------   

select
	dep_no
	, sum(salary) 
	, round(avg(salary),1)
	, count(*) ||'명'
from employee 

--------------------------------------------------------
-- 직급별로 [직급], [연봉합], [평균연봉], [인원수]를 출력하면? 단 평균연봉은 소수 2째자리에서 반올림 할 것
--------------------------------------------------------   
select
	jikup
	, sum(salary) 
	, round(avg(salary),1)
	, count(*) ||'명'
from employee 

--------------------------------------------------------
--  부서별, 직급별로 [부서번호], [직급], [연봉합], [평균연봉], [인원수]를 출력하면? 단 평균연봉은 소수 2째자리에서 반올림 할 것
--------------------------------------------------------   
  select
	dep_no			"부서번호"
    	, jikup			"직급"
	, sum (salary)      		"연봉합"          			
   	, round ( avg (salary), 1)      "평균연봉"		
   	, count(*)||'명'        		"인원수"
  from employee  
  
group by dep_no, jikup	
order by 1;



--------------------------------------------------------
-- 부서별로 직급별 [부서번호], [직급], [연봉합], [평균연봉], [인원수]를 출력하되 인원수는 3명 이상을 출력하면?	
-------------------------------------------------------- 
 	   select
		dep_no			"부서번호"
    		   , jikup			"직급"
   		     , sum (salary)  		"연봉합"          			
   		     , round ( avg (salary), 1)      "평균연봉"		
   		     , count(*)||'명'     		"인원수"
  	   from employee  
	group by dep_no, jikup	
	having 	count(*)>=3			
