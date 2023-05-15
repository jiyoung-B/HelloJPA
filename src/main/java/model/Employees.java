package model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;


@Data
@Table(name="employees")
@Entity
public class Employees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_ID")
    private Long empid;
    @Column(name = "FIRST_NAME")
    private String fname;
    @Column(name = "LAST_NAME")
    private String lname;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PHONE_NUMBER")
    private String phone;
    @Column(name = "HIRE_DATE")
    private Date hdate;
    @Column(name = "JOB_ID")
    private String jobid;
    @Column(name = "SALARY")
    private Integer sal;
    @Column(name = "COMMISSION_PCT", precision = 5, scale = 2)
    private BigDecimal comm;

    @Column(name = "MANAGER_ID")
    private Integer mgrid;
    @Column(name = "DEPARTMENT_ID")
    @JoinColumn(name="departments_id")
    private Long deptid;

//    private Date regdate;
//
//    // persist 호출전에 regdate 컬럼에 현재 날짜/시간 대입
//    @PrePersist
//    protected void onCreate(){
//        regdate = new Date();
//    }

}
