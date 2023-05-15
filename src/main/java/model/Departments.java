package model;

import lombok.Data;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.*;



@Data
@Table(name="departments")
@Entity
public class Departments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="DEPARTMENT_ID")
    private Long deptid;
    @Column(name="DEPARTMENT_NAME")
    private String dname;
    @Column(name="MANAGER_ID")
    private Integer mgrid;
    @Column(name="LOCATION_ID")
    private Integer locid;







}
