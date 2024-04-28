package jndc.jndcorg.web.controller.user;

import jndc.jndcorg.basic.domain.entity.Dept;
import jndc.jndcorg.basic.domain.webDTO.DeptTitle;
import jndc.jndcorg.basic.domain.entity.User;
import jndc.jndcorg.basic.repository.DeptRepository;
import jndc.jndcorg.basic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.LinkedList;
import java.util.List;
@Controller
@RequiredArgsConstructor
@Slf4j
public class UserDeptController {


    private final UserRepository userRepository;
    private final DeptRepository deptRepository;


    @GetMapping({"/userDepartment", "/userDepartment/{deptId}"})
    public String userDepartment(
            @PathVariable(value = "deptId", required = false) String deptId,
            Model model
    ){
        if(deptId == null){
            deptId = deptRepository.initDept();
        }

        LinkedList<DeptTitle> deptTitles = getDeptTitles(deptId);
        List<Dept> depts = deptRepository.findDeptByDeptId(deptId);
        List<User> users = userRepository.findUserByDeptId(deptId);

        model.addAttribute("users", users);
        model.addAttribute("deptTitles", deptTitles);
        model.addAttribute("depts",depts);

        return "user/userDepartment";
    }


    // 조직도 타이틀 가져오는 로직.
    private LinkedList<DeptTitle> getDeptTitles(String deptId) {
        LinkedList<DeptTitle> deptTitles = new LinkedList<>();
        Dept currentDept = deptRepository.findDeptNameByDeptId(deptId);
        makeDeptTitle(currentDept.getDeptId(), currentDept.getDeptName(), deptTitles);
        String deptMainKey = currentDept.getDeptMainKey();

        //메인쪽으로 갈 때 오류처리.
        if(deptMainKey != null){
            addTitleByDeptMainKey(deptMainKey, deptTitles);
        }
        return deptTitles;
    }

    private void addTitleByDeptMainKey(String deptMainKey, LinkedList<DeptTitle> deptTitles) {
        while(true){
            Dept dept = deptRepository.findDeptNameByDeptMainKey(deptMainKey);
            makeDeptTitle(dept.getDeptId(),dept.getDeptName(), deptTitles);
            deptMainKey = dept.getDeptMainKey();
            if(deptMainKey == null){
                break;
            }
        }
    }


    private void makeDeptTitle(String deptId, String title, LinkedList<DeptTitle> deptTitles) {
        DeptTitle deptTitle = new DeptTitle();
        deptTitle.setDeptId(deptId);
        deptTitle.setDeptTitle(title);
        deptTitles.addFirst(deptTitle);
    }




}
