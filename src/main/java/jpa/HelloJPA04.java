package jpa;

import model.Department;
import model.Employee;
import model.SungJuk;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class HelloJPA04 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        try {
            // Criteria 사용준비
            CriteriaBuilder cb = em.getCriteriaBuilder();

            // 사원 데이터 조회

            CriteriaQuery<Employee> query = cb.createQuery(Employee.class); // 조회대상 지정
            /*Root<Employee> e = query.from(Employee.class);*/

            /*CriteriaQuery<Employee> cq = query.select(e);*/
            /*List<Employee> emps = em.createQuery(cq.select(e)).getResultList();*/

          /*  for(Employee emp : emps)
                System.out.println(emp);*/

            // 사원 데이터 조회 - 이름, 부서번호, 입사일 : multiselect
            // 컬럼 지정 : 객체.get(변수명)
            /*CriteriaQuery<Object[]> mcq = cb.createQuery(Object[].class);
            Root<Employee> me = mcq.from(Employee.class);

            mcq.multiselect(me.get("fname"), me.get("deptid"), me.get("hdate"));
            List<Object[]> items = em.createQuery(mcq).getResultList();

            for(Object[] item : items)
                System.out.println(item[0] + "/" + item[1] + "/" + item[2]);*/

            // 정렬 조회 : 부서번호 기준, orderby
            /*Order deptid = cb.desc(e.get("deptid"));
            cq = query.select(e).orderBy(deptid);
            emps = em.createQuery(cq).getResultList();

            for(Employee emp : emps)
                System.out.println(emp);*/

            // 조건검색 : 직책이 IT_PROG인 사원 조회, where
           /* Predicate jobid = cb.equal(e.get("jobid"), "IT_PROG");
            cq = query.select(e).where(jobid);

            emps = em.createQuery(cq).getResultList();

            for(Employee emp : emps)
                System.out.println(emp);*/

            // 조건검색 : 연봉이 20000 이상인 사원 조회
            /*Predicate salGE = cb.ge(e.get("sal"), 20000);
            cq = query.select(e).where(salGE);

            List<Employee> emps = em.createQuery(cq).getResultList();

            for(Employee emp : emps)
                System.out.println(emp);*/

            // 직책 수 조회 1
            cb = em.getCriteriaBuilder(); // cb 초기화
            query = cb.createQuery(Employee.class); // query 초기화
            //e = query.from(Employee.class); // 다시 전체 데이터를 가져온 상태에서 실행해야한다. orElse 연봉이 20000 이상인 사원 조건이 따라 다님. 코드 겹침~

            /*Expression cntJob = cb.count(e.get("jobid"));
            cq = query.select(cntJob);
            List<Employee> cnt = em.createQuery(cq).getResultList();

            System.out.println(cnt);*/

            // 직책 수 조회 2 : distinct
            /*cq = query.select(e.get("jobid")).distinct(true);
            cnt = em.createQuery(cq).getResultList();

            System.out.println(cnt);*/

            // 직책 수 조회 3 : countDistinct
            /*cntJob = cb.countDistinct(e.get("jobid"));
            cq = query.select(cntJob);
            cnt = em.createQuery(cq).getResultList();

            System.out.println(cnt);*/

            // 그룹핑 : 직책별 최대, 평균 급여, 직책수 조회
            /*CriteriaBuilder gcb = em.getCriteriaBuilder();
            CriteriaQuery<Object[]> gcq = gcb.createQuery(Object[].class);
            Root<Employee> ge = gcq.from(Employee.class);

            Expression maxSal = cb.max(ge.get("sal"));
            Expression minSal = cb.min(ge.get("sal"));
            Expression avgSal = cb.avg(ge.get("sal"));
            Expression cntSal = cb.count(ge.get("sal"));

            gcq.multiselect(ge.get("jobid"), maxSal, minSal, avgSal, cntSal);
            gcq.groupBy(ge.get("jobid"));

            List<Object[]> items = em.createQuery(gcq).getResultList();

            for (Object[] item : items)
                System.out.println(item[0] + "/" + item[1] + "/" + item[2] + "/" + item[3] + "/" + item[4]);*/

            /*// 서브쿼리 : 평균연봉보다 적게 받는 사원들 조회
            cb = em.getCriteriaBuilder();
            query = cb.createQuery(Employee.class);

            // 하위 쿼리
            Subquery<Double> qryASal = query.subquery(Double.class);
            Root<Employee> s = qryASal.from(Employee.class); // 변경된 부분
            qryASal.select(cb.avg(s.get("sal")));

            // 주 쿼리
            Root<Employee> m = query.from(Employee.class);
            query.select(m).where(cb.lt(m.get("sal"), qryASal));
            List<Employee> emps = em.createQuery(query).getResultList();

            for(Employee emp : emps)
                System.out.println(emp);*/

            // 서브쿼리2 : 부서번호가 60번인 사원들의 이름, 직책, 부서명 조회



            // join : 부서번호가 60번인 사원들의 이름, 직책, 부서명 조회 : join
            /*CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
            Root<Employee> e = cq.from(Employee.class);*/

            // 2개의 객체를 조인함
           /* Join<Employee, Department> jntb = e.join("department", JoinType.INNER);

            // 조인 결과에서 원하는 컬럼 추출하는 질의문 작성
            CriteriaQuery<Object[]> jnq =
                    cq.multiselect(e.get("fname"), e.get("jobid"), jntb.get("dname"))
                    .where(cb.equal(e.get("deptid"), 60));

            List<Object[]> items = em.createQuery(jnq).getResultList();
            for(Object[] item : items)
                System.out.println(item[0] + "/" + item[1] + "/" + item[2]);*/


            // 제공된 이름, 직책, 연봉으로 사원 조회 : 동적 쿼리
            // 직책이 IT_PROG 인 사원 조회
            // 연봉이 10000 이상인 사원 조회
            // 직책이 IT_PROG이고 연봉이 6000 이상인 사원 조회
            String fname = null;
            String jobid = "SA_REP";
            Integer sal = 9000; // null 체크를 위해 클래스형으로 선언

            // 초기화
            cb = em.getCriteriaBuilder();
            query = cb.createQuery(Employee.class);
            Root<Employee> e = query.from(Employee.class);

            List<Predicate> predicates = new ArrayList<>(); // 조건절 저장 변수

            if (fname != null) {
                //  fname like concat('%', :fname, '%')
                predicates.add(cb.like(e.get("fname"), "%" + fname + "%"));
            }

            if(jobid != null){
                predicates.add(cb.equal(e.get("jobid"), jobid));
            }
            if(sal != null){
                predicates.add(cb.ge(e.get("sal"), sal));
            }

            query.where(predicates.toArray(new Predicate[0]));

            List<Employee> emps = em.createQuery(query).getResultList();

            for(Employee emp : emps)
                System.out.println(emp);



        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }

    }
}