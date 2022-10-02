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



