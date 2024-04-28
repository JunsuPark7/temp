package jndc.jndcorg.basic.repository;

import jndc.jndcorg.basic.domain.entity.Dept;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class DeptRepository {
    private final EntityManager em;

    // 전남개발공사 최상위 코드값 가져오기.
    public String initDept(){
        String initData = "전남개발공사";
        String query = "select d from Dept d where d.deptName = :deptName";
        Dept dept = em.createQuery(query, Dept.class)
                .setParameter("deptName", initData)
                .getSingleResult();
        return dept.getDeptId();
    }


    public List<Dept> renderDept(){
        String deptId = initDept();
        String query = "select d from Dept d where " +
                "d.deptMainKey = :deptId AND " +
                "d.deptDelYN = :deptDelYN " +
                "ORDER BY CAST(d.deptOrder AS int)";
        return em.createQuery(query, Dept.class)
                .setParameter("deptId", deptId)
                .setParameter("deptDelYN", "N")
                .getResultList();
    }

    public List<Dept> findDeptByDeptId(String deptId){

        String query = "select d from Dept d where " +
                "d.deptMainKey = :deptId AND " +
                "d.deptDelYN = :deptDelYN " +
                "ORDER BY CAST(d.deptOrder AS int)";
        return em.createQuery(query, Dept.class)
                .setParameter("deptId", deptId)
                .setParameter("deptDelYN", "N")
                .getResultList();
    }

    public Dept findDeptNameByDeptId(String deptId){
        String query = "select d from Dept d where " +
                "d.deptId = :deptId";
        return em.createQuery(query, Dept.class)
                .setParameter("deptId", deptId)
                .getSingleResult();
    }

    //    상위키 조회용.
    public Dept findDeptNameByDeptMainKey(String deptMainKey){
        String query = "select d from Dept d where " +
                "d.deptId = :deptMainKey";
        return em.createQuery(query, Dept.class)
                .setParameter("deptMainKey", deptMainKey)
                .getSingleResult();
    }


    public List<Dept> findAll() {

        String query = "select d from Dept d where " +
                "d.deptDelYN = :deptDelYN " +
                "ORDER BY CAST(d.deptOrder AS int)";
        return em.createQuery(query, Dept.class)
                .setParameter("deptDelYN", "N")
                .getResultList();
    }



}
