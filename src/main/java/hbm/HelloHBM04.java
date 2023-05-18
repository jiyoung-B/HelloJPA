package hbm;


import model.Department;
import notmap.Empinfo;
import model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class HelloHBM04 {
    public static void main(String[] args) {

        // SessionFactory 초기화
        Configuration cfg = new Configuration().configure();
        SessionFactory sf = cfg.addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Department.class).buildSessionFactory();
        Session sess = sf.openSession();

        try {
            // 조회 - 전체 사원
            /*Query query = sess.createQuery("from Employee");
            List<Employee> emps = query.getResultList();
            System.out.println(emps);*/

            // 조회 - 부분조회1: 이름, 연봉
            /*Query query = sess.createQuery(
                    "select fname, sal from Employee");
            List<Objects[]> items = query.getResultList();

            for(Object[] item : items)
                System.out.println(item[0] + "/" + item[1]);*/

            // 조회 - 부분조회1: 이름, 연봉
            /*Query query = sess.createQuery(
                    "select new notmap.Empinfo(fname, sal) from Employee");

            List<Empinfo> items = query.getResultList();
            for(Empinfo e : items)
                System.out.println(e);*/

            //조건검색 : where, 직책이 IT_PROG인 사원조회
            /*Query query = sess.createQuery("from Employee where jobid = ?1");
            query.setParameter(1, "IT_PROG");
            List<Employee> emps = query.getResultList();
            for(Employee e : emps)
                System.out.println(e);*/


            // 연봉이 20000 이상인 사원
           /* Query query = sess.createQuery("from Employee where sal >= ?1");
            query.setParameter(1, 20000);
            List<Employee> emps = query.getResultList();
            for(Employee e : emps)
                System.out.println(e);*/

            // 정렬 : orderby, 부서번호 기준
            /*Query query = sess.createQuery("from Employee order by deptid desc");
            List<Employee> emps = query.getResultList();

            for(Employee e : emps)
                System.out.println(e);*/

            // 직책 수 조회
            /*String hql ="select count(distinct jobid) from Employee";
            Query query = sess.createQuery(hql);
            List emps = query.getResultList();
            System.out.println(emps);*/

            // 그룹핑 : 직책별 최대, 최소, 평균 연봉, 직책수 조회
            /*hql ="select jobid, max(sal), min(sal), avg(sal), " +
                    "count(jobid) from Employee group by jobid";
            query = sess.createQuery(hql);
            List<Object[]> items = query.getResultList();
            for(Object[] item : items)
                System.out.println(item[0] + "/" + item[1]);*/

            // 그룹핑 : 직책별 최대, 최소, 평균 연봉, 직책수 정렬조회(해보기)
            /*String hql ="select jobid, max(sal), min(sal), avg(sal), " +
                    "count(jobid) from Employee group by jobid order by jobid";
            Query query = sess.createQuery(hql);
            List<Object[]> items = query.getResultList();
            for(Object[] item : items)
                System.out.println(item[0] + "/" + item[1]);*/

            // 서브쿼리1 : 평균연봉보다 적게 받는 사원들 조회
            /*String hql =
                    " select fname, sal from Employee where sal < ( " +
                            "select avg(sal) from Employee )";
            Query query = sess.createQuery(hql);
            List<Object[]> items = query.getResultList();
            for(Object[] item : items)
                System.out.println(item[0] + "/" + item[1]);*/

            // 서브쿼리2 : IT 부서에 근무중인 사원들의 이름, 직책, 급여 조회(해보기)


            // join : 부서번호가 60번인 사원들의 이름, 직책, 부서명 조회
            String hql =
                    " select e.fname, e.jobid, d.dname from Employee e " +
                    " inner join Department d on e.deptid = d.deptid " +
                    " where e.deptid = 60 ";
            Query query = sess.createQuery(hql);
            List<Object[]> items = query.getResultList();
            //List<Object[]> items = query.getResultList();
            for(Object[] item : items)
                System.out.println(item[0] + "/" + item[1]);





        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            sess.close();
            sf.close();
        }
    }
}