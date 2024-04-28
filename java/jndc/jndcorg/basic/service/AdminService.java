package jndc.jndcorg.basic.service;

import jndc.jndcorg.basic.domain.entity.User;
import jndc.jndcorg.basic.repository.AdminRepository;
import jndc.jndcorg.web.aop.LogManage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdminService {
    private final AdminRepository adminRepository;

    public List<User> getUsers(String searchCon, String searchInput){
        List<User> users = new ArrayList<>();
        if(searchCon.equals("전체")){
            users = adminRepository.findUserByInputValue(searchInput);
        } else if (searchCon.equals("이름")) {
            users = adminRepository.findUserByUserName(searchInput);
        } else if (searchCon.equals("부서")) {
            users = adminRepository.findUserByDeptName(searchInput);
        } else if (searchCon.equals("직급")) {
            users = adminRepository.findUserByRole(searchInput);
        }
        return users;
    }

    public List<User> getUsers(){
        return adminRepository.findUserByInputValue("");
    }


    public List<User> getUserUpdateList() {
        return adminRepository.findUserUpdateList();
    }
    public List<User> getUserUpdateList(String searchInput) {
        return adminRepository.findUserUpdateList(searchInput);
    }

    public int getUserUpdateCount() {

        return adminRepository.findUserUpdateCount();
    }




    public void createUser(User updateParam) {
        //기본 DelYn값
        updateParam.setDelYN("N");
        updateParam.setUpdateYN("Y");
        adminRepository.create(updateParam);
    }

    public void updateCheck(String userId) {
        adminRepository.updateCheck(userId);
    }

//    멀티체크.
    @LogManage
    public void updateCheck(HttpServletRequest request, String userId) {
        adminRepository.updateCheck(userId);
    }


    public void updateUser(User updateParam) {
        adminRepository.updateUser(updateParam);
    }



    public void changeFlag(String userId, String flag) {

        //유저 플래그 변경.
        final int userFlag = 0;
        final int adminFlag = 1;
        if(flag.equals("userFlag")){
            adminRepository.updateFlag(userId,userFlag);
        } else if (flag.equals("adminFlag")) {
            adminRepository.updateFlag(userId,adminFlag);
        }

    }



    public boolean isUserDuplicate(String userId){
        Optional<User> userById = adminRepository.findUserById(userId);
        if(userById.isPresent()){
            return true;
        }
        return false;
    }





}
