package dsl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jdk.jfr.internal.StringPool;
import model.*;
import org.apache.commons.lang.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class HelloQueryDSL02 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        try {
            // 쿼리 객체 준비
            QEmployee qemp = QEmployee.employee;
            QDepartment qdept = QDepartment.department;
            JPAQueryFactory query = new JPAQueryFactory(em);

            // 조회 - 전체 사원
            /*List<Employee> emps = query.selectFrom(qemp).fetch();

            for(Employee e : emps)
                System.out.println(e);*/


            // 조회 - 일부 사원, 페이징(offset, limit)
            /*List<Employee> emps = query.selectFrom(qemp)
                    .offset(30).limit(15).fetch(); // 15개씩 끊어서 3페이지에 해당하는 데이터가 나온다.

            for(Employee e : emps)
                System.out.println(e);*/

            // 사원 데이터 조회 - Query : 이름, 부서번호, 입사일
            //query.selectFrom(qemp); // selectFrom은 전체 다 받기.
            // select는 부분 컬럼 데이터 가져오기. (이전에 특정 컬럼만 받을때는 object로 받았죠?)
           /* List<Tuple> items = query.select(qemp.fname, qemp.deptid, qemp.hdate)
                    .from(qemp).fetch();

            for(Tuple item : items)
                System.out.println(item);*/

            // 정렬 : orderby, 부서번호 기준
           /* query = new JPAQueryFactory(em);
            List<Employee> emps = query.selectFrom(qemp).orderBy(qemp.deptid.desc()).fetch();
            System.out.println(emps);*/


            // 조건검색 : where - 직책인 IT_PROG인 사원 조회,
            /*query = new JPAQueryFactory(em);
            List<Employee> emps = query.selectFrom(qemp).where(qemp.jobid.eq("IT_PROG")).fetch();
            for(Employee e : emps)
                System.out.println(e);*/


            // 조건검색 : 연봉이 20000 이상인 사원 조회
           /* query = new JPAQueryFactory(em);
            List<Employee> emps = query.selectFrom(qemp).where(qemp.sal.goe(12000)).fetch();
            for(Employee e : emps)
                System.out.println(e);*/


            // 직책 수 조회1
            query = new JPAQueryFactory(em);
            List<Long> cnt = query.select(qemp.jobid.count()).from(qemp).fetch();
            System.out.println(cnt);

            // 직책 수 조회2
            query = new JPAQueryFactory(em);
            Long cnt2 = query.select(qemp.jobid).from(qemp).fetchCount();
            System.out.println(cnt2);

            // 직책 수 조회3
            query = new JPAQueryFactory(em);
            cnt = query.select(qemp.jobid.countDistinct()).from(qemp).fetch();
            System.out.println(cnt);

            // 직책 수 조회4 (함수로 행수를 가져오는 것보다, 가져와서 행수만 카운트 하는 것이 나으므로 2, 4번을 권장)
            /*cnt2 = query.select(qemp.jobid).distinct().from(qemp).fetchCount();
            System.out.println(cnt2);*/

            // 그룹핑 : 직책별 최대, 최소, 평균 연봉, 직책수 조회
            query = new JPAQueryFactory(em);
            List<Tuple> grouping = query.select(qemp.jobid, qemp.sal.max(), qemp.sal.min(), qemp.sal.avg(), qemp.jobid.count()).from(qemp).groupBy(qemp.jobid).fetch();
            System.out.println(grouping);
            for(Tuple e : grouping)
                System.out.println(e);

            // 그룹핑 : 직책별 최대, 최소, 평균 연봉, 직책수 정렬 조회
            StringPath jbcnt = Expressions.stringPath("jbcnt"); //별칭정의
            grouping = query.select(qemp.jobid, qemp.sal.max(), qemp.sal.min(), qemp.sal.avg(),
                            qemp.jobid.count().as("jbcnt"))
                    .from(qemp).groupBy(qemp.jobid).orderBy(jbcnt.desc()).fetch();
            System.out.println(grouping);
            for(Tuple e : grouping)
                System.out.println(e);

            // 서브쿼리1 : 평균연봉보다 적게 받는 사원들 조회
/*            query = new JPAQueryFactory(em);

            // 서브쿼리 - 평균연봉은?
            JPQLQuery<Double> subqry = JPAExpressions.select(qemp.sal.avg()).from(qemp);

            // 주쿼리 - 사원조회
            List<Employee> sub = query.selectFrom(qemp).where(qemp.sal.loe(subqry)).fetch();
            for(Employee s : sub)
                System.out.println(sub);*/

            // 서브쿼리2 : IT 부서에 근무중인 사원들의 이름, 직책, 급여 조회

            /*query = new JPAQueryFactory(em);
            JPQLQuery<String> subqry2 = JPAExpressions
                    .select(qdept.deptid)
                    .from(qdept).where(qdept.dname.eq("IT"));

            List<Tuple> items = query.select(qemp.fname, qemp.jobid, qemp.sal)
                    .from(qemp).where(qemp.jobid.eq(subqry2)).fetch();

            for(Tuple e : items)
                System.out.println(e);*/

            // join : 부서번호가 60번인 사원들의 이름, 직책, 부서명 조회
  /*          query = new JPAQueryFactory(em);

            List<Tuple> items = query.select(qemp.fname, qemp.jobid, qdept.dname)
                    .from(qemp).join(qemp.department, qdept)
                    .where(qemp.deptid.eq(60L)) *//*new Long(60)*//*
                    .fetch();
            for(Tuple e : items)
                System.out.println(e);
*/

            // 동적쿼리
            // 연봉이 10000이상인 사원 조회
            // 직책이 IT_PROG이고 연봉이 6000 이상인 사원 조회
            String fname = "Ste";
            String jobid = null;
            Integer sal = null;

            query = new JPAQueryFactory(em);
            List<Employee> emps = query.selectFrom(qemp)
                    .where(
                            (fname != null) ? qemp.fname.contains(fname) : null,
                            StringUtils.isNotEmpty(jobid) ? qemp.jobid.eq(jobid) : null,
                            //StringUtils.isNotEmpty(sal+"") ? qemp.sal.gt(sal) : null
                            (sal != null) ? qemp.sal.gt(sal) : null
                    ).fetch();



            for(Employee e : emps)
                System.out.println("스티븐"+ e);






        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}