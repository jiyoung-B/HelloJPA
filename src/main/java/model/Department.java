package model;

import lombok.Data;

import javax.persistence.*;



@Data
@Table(name="departments")
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="DEPARTMENT_ID")
    private Long deptid;
    @Column(name="DEPARTMENT_NAME")
    private String dname;
    @Column(name="MANAGER_ID")
    private Integer mgrid;
    @Column(name="LOCATION_ID")
    private Long locid;

}
