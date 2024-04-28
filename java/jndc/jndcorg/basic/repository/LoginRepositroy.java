//package jndc.jndcorg.basic.repository;
//
//import jndc.jndcorg.basic.domain.LoginUser;
//import jndc.jndcorg.basic.domain.entity.User;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import javax.transaction.Transactional;
//import java.util.List;
//import java.util.Optional;
//
//@Slf4j
//@Repository
//@Transactional
//@RequiredArgsConstructor
//public class LoginRepositroy {
//
//    private final EntityManager em;
//
////    public Optional<LoginUser> findByLoginId(String UiId) {
////        return findAll().stream()
////                .filter(u -> u.getUiId().equals(UiId))
////                .findFirst();
////    }
//
//
//    public Optional<User> findByLoginId(String loginId) {
//
//        String query = "SELECT user " +
//                "FROM User user " +
//                "LEFT JOIN user.dept dept " +
//                "LEFT JOIN user.loginUser loginUser " +
//                "WHERE user.delYN = 'N' " +
//                "AND dept.deptDelYN = 'N' " +
//                "AND loginUser.uiId = :loginId";
//
//        return Optional.ofNullable(em.createQuery(query, User.class)
//                .setParameter("loginId", loginId)
//                .getSingleResult());
//
////        return Optional.of(user);
//    }
//
//
//
//
//
//
//    public List<LoginUser> findAll() {
//        return em.createQuery("select u from LoginUser u", LoginUser.class)
//                .getResultList();
//    }
//
////    public Optional<LoginUser> findByUIKeyno(String UiKeyno) {
////        LoginUser loginUser = em.find(LoginUser.class, UiKeyno);
////        return Optional.of(loginUser);
////    }
//
//}
