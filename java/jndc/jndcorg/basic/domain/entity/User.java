package jndc.jndcorg.basic.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "T_USER")
@Data
public class User {

    @Id
    @Column(name = "DU_KEYNO")
    private String userId;
    @Column(name = "DU_NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "DU_DN_KEYNO")
    private Dept dept;

    @Column(name = "DU_DEL_YN")
    private String delYN;
    @Column(name = "DU_ROLE")
    private String role;
    @Column(name = "DU_LEV")
    private long Lev;
    @Column(name = "DU_CONTENTS")
    private String comments;
    @Column(name = "DU_TEL")
    private String number;


//    @ManyToOne
//    @JoinColumn(name = "UI_KEYNO")
//    private LoginUser loginUser;

    @Column(name = "UI_KEYNO")
    private String uiKey;


    @Column(name = "DU_DEPT_WORK")
    private String work;
    @Column(name = "DU_PHONE_TEL")
    private String phoneNumber;
    @Column(name = "DU_EMAIL")
    private String email;
    @Column(name = "DU_ADMIN_FLAG")
    private int flag;
    @Column(name = "DU_IMG_PATH")
    private String img;
    @Column(name = "DU_UPDATE_CHECK_YN")
    private String updateYN;

    @Column(name = "DU_UPDATE_PHONE_TEL")
    private String updatePhoneNumber;

    @Column(name = "DU_UPDATE_EMAIL")
    private String updateEmail;

}
