package jndc.jndcorg.basic.repository;



import jndc.jndcorg.basic.domain.entity.User;
import jndc.jndcorg.basic.domain.webDTO.UserUpdateForm;
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
public class UserRepository {

    private final EntityManager em;

    public Optional<User> findById(String id) {
        User user = em.find(User.class, id);
        return Optional.of(user);
    }



//    public Optional<User> findUserAndDeptByUserId(String id) {
//
//        String query = "SELECT u FROM User u " +
//                "LEFT JOIN FETCH u.dept " +
//                "WHERE u.userId = :userId";
//
//        User user = em.createQuery(query, User.class)
//                .setParameter("userId", id)
//                .getSingleResult();
//
//        return Optional.of(user);
//    }




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


    public List<User> findUserByDeptId(String deptId) {
        String query = "SELECT u FROM User u " +
                "LEFT JOIN u.dept d " +
                "WHERE u.delYN = :delYn " +
                "AND d.deptDelYN = :delYn " +
                "AND d.deptId = :deptId " +
                "ORDER BY d.deptId, u.Lev";

        List<User> resultList = em.createQuery(query, User.class)
                .setParameter("delYn", "N")
                .setParameter("deptId", deptId)
                .getResultList();

        return resultList;
    }



    public void updateUser(String userId, UserUpdateForm userUpdateForm) {
        User user = findById(userId).orElseThrow();

        user.setUpdateYN("N");
        user.setUpdatePhoneNumber(userUpdateForm.getPhoneNumber());
        user.setUpdateEmail(userUpdateForm.getEmail());
    }

    public void uploadProfile(String userId,String fileName){
        User user = em.find(User.class, userId);
        user.setImg(fileName);
    }


    public String getProfileImg(String userId) {
        User user = em.find(User.class, userId);
        //값x
        if(user.getImg() == null){
            return null;
        } else {
            return user.getImg();
        }
    }


}
