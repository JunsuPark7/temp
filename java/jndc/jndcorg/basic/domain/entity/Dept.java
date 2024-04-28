package jndc.jndcorg.basic.domain.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_DEPARTMENT")
@Data
public class Dept {

    @Id
    @Column(name = "DN_KEYNO")
    private String deptId;

    @Column(name = "DN_NAME")
    private String deptName;

    @Column(name = "DN_MAINKEY")
    private String deptMainKey;

    @Column(name = "DN_DEL_YN")
    private String deptDelYN;

    @Column(name = "DN_CONTENTS")
    private String deptContents;

    @Column(name = "DN_TEMP")
    private String deptTemp;

    @Column(name = "DN_ORDER")
    private String deptOrder;



}
