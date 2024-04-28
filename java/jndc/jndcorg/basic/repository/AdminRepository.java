package jndc.jndcorg.basic.repository;

import jndc.jndcorg.basic.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class AdminRepository {

    private final EntityManager em;

    public void create(User updateParam) {
        em.persist(updateParam);
    }

    public Optional<User> findUserById(String userId) {
        User user = em.find(User.class, userId);
        return Optional.ofNullable(user);
    }

    public List<User> findUserByInputValue(String inputValue) {
        String query = "SELECT u FROM User u " +
                "LEFT JOIN u.dept d " +
                "WHERE u.delYN = :delYn " +
                "AND d.deptDelYN = :delYn " +
                "AND (u.name LIKE :name OR d.deptName LIKE :dept) " +
                "ORDER BY d.deptId, u.Lev";

        List<User> resultList = em.createQuery(query, User.class)
                .setParameter("delYn", "N")
                .setParameter("name", "%" + inputValue + "%")
                .setParameter("dept", "%" + inputValue + "%")
                .getResultList();

        return resultList;
    }

    public List<User> findUserByUserName(String userName) {
        String query = "SELECT u FROM User u " +
                "LEFT JOIN u.dept d " +
                "WHERE u.delYN = :delYn " +
                "AND d.deptDelYN = :delYn " +
                "AND u.name LIKE :name " +
                "ORDER BY d.deptId, u.Lev";

        List<User> resultList = em.createQuery(query, User.class)
                .setParameter("delYn", "N")
                .setParameter("name", "%" + userName + "%")
                .getResultList();

        return resultList;
    }


    public List<User> findUserByDeptName(String deptName) {
        String query = "SELECT u FROM User u " +
                "LEFT JOIN u.dept d " +
                "WHERE u.delYN = :delYn " +
                "AND d.deptDelYN = :delYn " +
                "AND d.deptName LIKE :dept " +
                "ORDER BY d.deptId, u.Lev";

        List<User> resultList = em.createQuery(query, User.class)
                .setParameter("delYn", "N")
                .setParameter("dept", "%" + deptName + "%")
                .getResultList();

        return resultList;
    }

    public List<User> findUserByRole(String roleName) {
        String query = "SELECT u FROM User u " +
                "LEFT JOIN u.dept d " +
                "WHERE u.delYN = :delYn " +
                "AND d.deptDelYN = :delYn " +
                "AND u.role = :role " +
                "ORDER BY d.deptId, u.Lev";

        List<User> resultList = em.createQuery(query, User.class)
                .setParameter("delYn", "N")
                .setParameter("role", roleName)
                .getResultList();

        return resultList;
    }



    public List<User> findUserUpdateList() {
        String query = "SELECT u FROM User u " +
                "LEFT JOIN u.dept d " +
                "WHERE u.delYN = :delYn " +
                "AND d.deptDelYN = :delYn " +
                "AND u.updateYN = :updateYN " +
                "ORDER BY d.deptId, u.Lev";

        List<User> resultList = em.createQuery(query, User.class)
                .setParameter("delYn", "N")
                .setParameter("updateYN", "N")
                .getResultList();

        return resultList;
    }


    public List<User> findUserUpdateList(String inputValue) {
        String query = "SELECT u FROM User u " +
                "LEFT JOIN u.dept d " +
                "WHERE u.delYN = :delYn " +
                "AND d.deptDelYN = :delYn " +
                "AND u.updateYN = :updateYN " +
                "AND (u.name LIKE :name OR d.deptName LIKE :dept) " +
                "ORDER BY d.deptId, u.Lev";

        List<User> resultList = em.createQuery(query, User.class)
                .setParameter("delYn", "N")
                .setParameter("updateYN", "N")
                .setParameter("name", "%" + inputValue + "%")
                .setParameter("dept", "%" + inputValue + "%")
                .getResultList();

        return resultList;
    }


    public int findUserUpdateCount() {
        String query = "SELECT COUNT(u) FROM User u " +
                "LEFT JOIN u.dept d " +
                "WHERE u.delYN = :delYn " +
                "AND d.deptDelYN = :delYn " +
                "AND u.updateYN = :updateYN " +
                "ORDER BY d.deptId, u.Lev";

        Long count = em.createQuery(query, Long.class)
                .setParameter("delYn", "N")
                .setParameter("updateYN", "N")
                .getSingleResult();

        return count.intValue();
    }


    public void updateCheck(String userId) {
        User user = em.find(User.class, userId);
        user.setUpdateYN("Y");
        user.setPhoneNumber(user.getUpdatePhoneNumber());
        user.setEmail(user.getUpdateEmail());
    }




    public void updateFlag(String userId,int flag){
        //실패로직.
//        throw new RuntimeException();
        User user = em.find(User.class, userId);
        user.setFlag(flag);
    }

    public void updateUser(User updateParam){
        User user = em.find(User.class, updateParam.getUserId());
        user.setName(updateParam.getName());
        user.setDept(updateParam.getDept());
        user.setRole(updateParam.getRole());
        user.setLev(updateParam.getLev());
        user.setNumber(updateParam.getNumber());
        user.setWork(updateParam.getWork());
        user.setEmail(updateParam.getEmail());
        user.setPhoneNumber(updateParam.getPhoneNumber());
    }










}
