------------------------------------------------------------------------------
-- 직원번호, 직원명, 부서명을 한 테이블로 붙여서  출력하시오
------------------------------------------------------------------------------

select
    e.emp_no
    , e.emp_name
    , d.dep_name
                                       --- 에러 발생 이유: 서로 다른 테이블을 join 없이 같은 테이블에 놓으려고 해서   (dept 테이블까지 써줘야함.)
    from employee e  , dept d          --- 테이블 명 뒤에 알리야스(별칭)을 붙이면 컬럼명 앞에 알리야스로 테이블 명을 표현할 수 있음.
                                        --- 결과: inner join인 동시에 outer join. (결과가 겹치지 않으면서 다 중복되었기 때문)

    where
    e.dep_no = d.dep_no       --- join. 각기 다른 테이블에 있는 컬럼들을 골라서 좌우(횡)로 한 테이블에 붙여 넣는 것

------------------------------------------------------------------------------
-- 고객번호, 고객명, 직원번호, 직원명을 한 테이블에 합쳐서 출력하시오.
------------------------------------------------------------------------------

    select
           c.cus_no
           , c.cus_name
           , c.emp_no
           , e.emp_name                   --- employee 테이블에 있음. join 문 필요함.
           , d.dep_name

           from
           customer c, employee e, dept d   --- 3개의 테이블에서 join문 사용 => 3중 join

    where                               ---- 오라클에선 where 절이 서로 다른 행을 붙이기 위해서 사용하기도 함.
         c.emp_no=e.emp_no              ----    customer에 있는   emp_name 가 employee 테이블에 있는 것과 같다는 의미
    and
          e.dep_no=d.dep_no             ----    customer에 있는   dep_no 가 employee 테이블에 있는 것과 같다는 의미

                                        ----   결과 : inner join이 나오게 됨.
                                        ----   join 인데 오라클에서만 사용 가능.
------------------------------------------------------------------------------
 -- 고객번호, 고객명, 직원번호, 직원명 을 한번에 출력하시오
 ------------------------------------------------------------------------------

 select
           c.cus_no
           , c.cus_name
           , c.emp_no
           , e.emp_name
  from
           customer c inner join employee e     --- ANSI 방식에선 inner join 을 직접 사용
  on                                            --- where 이 아닌 on이 있음.
            c.emp_no = e.emp_no


------------------------------------------------------------------------------
--직원번호        직원명         연봉          총 연봉에서 내 연봉이 차지하는 비율     찾기
------------------------------------------------------------------------------
select
        emp_no
        , emp_name
        , salary                                                          --- 비상관 쿼리문!!!
        , salary/( select sum(salary) from employee ) * 100||'%'          --- salary/총 연봉 합 * 100||'%'   <- 내 연봉 비율
                                                                          ---  select문 안에 또 다른 select문 = !!!subquery!!!
from                                                                      --- select 문 없이 sum(salary)만 나오면 어느 테이블에서 어떤 조건으로 sum을 사용하는지 모르게 때문에 sum(salary)만 나와선 안됨.
    employee


                                                                          
                                                                          
------------------------------------------------------------------------------
 --  employee 테이블에서 [직원번호], [직원명], [직급], [출생년대(4자리)] 검색해서 출력하면? 
------------------------------------------------------------------------------	/// 나머지 여러가지 형태로 나올 수 있는 거는 내가 다 만들어보기 
	------------------------------------------------------------------------------------
	select
		emp_no			as "직원번호"
		,emp_name			as "직원명"
		,jikup				as "직급"
		,case 
			 when substr (jumin_num, 7, 1) = '1' then '19'
			 when substr (jumin_num, 7, 1) = '2' then '19'
					else '20'						--- '19' 또는 '20'이 리턴되는 case구문을 만들기.		

	end ||	  substr (jumin_num, 1, 1) || '0년대생'					-- 주민번호 맨 앞의 것만 낚아챈다음에 '0년대생'를 적기
	from
		employee;
	------------------------------------------------------------------------------------

	------------------------------------------------------------------------------------
	select
		emp_no			as "직원번호"
		,emp_name			as "직원명"
		,jikup				as "직급"
		, decode( substr (jumin_num, 7, 1)
				, '1', '19'
				, '2', '19'
				,  '20'
			)	||substr (jumin_num, 1, 1)	|| '0년대생'				-- 주민번호 맨 앞의 것만 낚아챈다음에 '0년대'를 적기
	from
		employee;
	------------------------------------------------------------------------------------

	------------------------------------------------------------------------------------
	select
		emp_no			as "직원번호"
		,emp_name			as "직원명"
		,jikup				as "직급"
		,case 
			 when substr (jumin_num, 7, 1) in('1', '2') then '19'		--- in 연산자로 나열하는 것도 가능함. (나중에 배울 거임. or과 같은 거임.)
					else '20'						--- '19' 또는 '20'이 리턴되는 case구문을 만들기.

		end ||substr (jumin_num, 1, 1)	|| '0년대생'	
	from
		employee;
	------------------------------------------------------------------------------------

	------------------------------------------------------------------------------------
	select
		emp_no			as "직원번호"
		,emp_name			as "직원명"
		,jikup				as "직급"
		,case 
			 when substr (jumin_num, 7, 1) = '1' then '19' or when substr (jumin_num, 7, 1) = '2' then '19'			--- or연산자로 나열하는 것도 가능함.
					else '20'						--- '19' 또는 '20'이 리턴되는 case구문을 만들기.

		end ||substr (jumin_num, 1, 1)	|| '0년대생'	
	from
		employee;
	------------------------------------------------------------------------------------

	------------------------------------------------------------------------------------
	select
		emp_no			as "직원번호"
		,emp_name			as "직원명"
		,jikup				as "직급"
		,case 
			 substr (jumin_num, 7, 1) 
					when '1' then '19'
					when '2' then '19'
					else '20'						--- '19' 또는 '20'이 리턴되는 case구문을 만들기.

		end ||substr (jumin_num, 1, 1)	|| '0년대생'	
	from
		employee;
	------------------------------------------------------------------------------------                                                                          

----------------------------------------------
-- <56> employee 테이블에서 직원번호, 직원명, 100일잔치(년-월-일) 날짜를 검색해서 출력하면?		/// to_date: 숫자문자를 날짜 데이터로 바꿔서 사칙연산하려고 사용하는 변환함수
----------------------------------------------
	--------------------------------------------------------------------------------------------------------------------------------------------------
		select
			emp_no					"직원번호"
			, emp_name					"직원명"		
			, to_char(

				to_date(
						case 
                      				 substr(jumin_num, 7,1)					
							when '1' then '19'
							when '2' then '19'
						         else '20'
						end || substr(jumin_num, 1,6) 
						, 'YYYYMMDD'	---------------------------------- 출생년도 데이터와 같음.					
					)+100								--- sysdate에 숫자를 더하면 그 숫자를 더한만큼의 일수로 인식하고 그 결과가 출력됨. (날짜-날짜(숫자) = 일수(return값 실수),		날짜+날짜(숫자)=일수(return값 날짜 데이터) )
					, 'YYYY-MM-DD'
			)	as	"100일 잔치일"						--- 날짜 데이터로 바꿔서 이를 통해 사칙연산을 할 수 있도록 하게.
		from employee;									만약 to_date로 연산한 데이터를 다른 방식으로 쉽게 데이터를 바꾸고 싶다면 변환함수 to_char를 사용해서 다양한 방법으로 바꿀 수 있음.
	----------------------------------------------------------


-----------------------------------------
-- employee 테이블에서 수요일에 태어난 직원을 검색하여 출력하려면?
-----------------------------------------

	------------------------------------------------------------------------------
	select * from employee					
	where
		to_char(
			to_date(
				decode(substr(jumin_num, 7, 1), '1', '19', '2', '19' , '20')||substr(jumin_num, 1, 6)
					, 'YYYYMMDD'					
				), 'DAY' 						
				, 'nls_date_language=Korean'				
			) = '수요일'				---- 날짜 데이터를 to_char가 끌어안아서 문자 자료형이 나옴.
	------------------------------------------------------------------------------
	------------------------------------------------------------------------------
	select * from employee					
	where
		to_char(
			to_date(
				decode(substr(jumin_num, 7, 1), '1', '19', '2', '19' , '20')||substr(jumin_num, 1, 6)
					, 'YYYYMMDD'					
				), 'DAY' 						
				, 'nls_date_language=Korean'				
			) = '수'				
	------------------------------------------------------------------------------
	------------------------------------------------------------------------------
	select * from employee					
	where
		to_char(
			to_date(
				decode(substr(jumin_num, 7, 1), '1', '19', '2', '19' , '20')||substr(jumin_num, 1, 6)
					, 'YYYYMMDD'					
				), 'DAY' 						
				, 'nls_date_language=Korean'				
			) = '4'				---- 요일숫자 (일~토 순서대로 0~6)
	------------------------------------------------------------------------------
	
	
------------------------------------------------------------------------------
--- employee 테이블에서 1970년대생 남자 직원이 먼저 나오게 검색하면?					--- 70년대생 이라고만 하면 1970, 2070년대생 다 포함이기 때문에 문제가 디테일하게 나와야함.
------------------------------------------------------------------------------
	------------------------------------------------------------------------------
	select * from employee	
	where	
		 substr(jumin_num, 1, 1) = '7'
		 and
		( substr(jumin_num, 7, 1) = '1' or substr(jumin_num, 7, 1) = '3')	--- 주민번호 뒤 7자리 중 첫번째가 1, 3(남성)이 나오는 사람을 먼저 연산을 해준 후, 70년대생을 가리는 문제
	------------------------------------------------------------------------------
	------------------------------------------------------------------------------
	select * from employee	
	where	
		 substr(jumin_num, 1, 1) = '7' and substr(jumin_num, 7, 1) in ('1', '3')
	------------------------------------------------------------------------------
	
	
	
------------------------------------------------------------------------------
-- employee 테이블에서 직원번호, 직원명, 다가올생일날(년-월-일), 생일 D-day 를 검색해서 출력하면?		
------------------------------------------------------------------------------				
select 

	emp_no		"직원번호"
	, emp_name	"직원명"
	, to_char(
		case when sysdate 
		- to_date( to_char( (sysdate, YYYY)||substr(jumin_num, 3, 4), 'YYYYMMDD'))>0
			
			then  to_date( to_char( (sysdate, YYYY)||substr(jumin_num, 3, 4), 'YYYYMMDD'))
			else to_date( to_char( to_number ((sysdate, YYYY)+1) ||substr(jumin_num, 3, 4), 'YYYYMMDD')) 
			,  'YYYY-MM-DD') "다가올생일날"
	
	, floor(case when sysdate 
			- to_date( to_char( (sysdate, YYYY)||substr(jumin_num, 3, 4), 'YYYYMMDD'))>0
		
		then to_date( to_char( (sysdate, YYYY)||substr(jumin_num, 3, 4), 'YYYYMMDD')) - sysdate
		else to_date( to_char( to_number ((sysdate, YYYY)+1) ||substr(jumin_num, 3, 4), 'YYYYMMDD')) - sysdate
			)||일	"생일 D-day"


from employee 
 order by "생일 D-day"



