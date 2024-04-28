package jndc.jndcorg.basic.service;

import jndc.jndcorg.basic.domain.entity.User;
import jndc.jndcorg.basic.domain.webDTO.UserUpdateForm;
import jndc.jndcorg.basic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProfileService {

    private final UserRepository userRepository;
    private static final String UPLOAD_DIR = "src/main/resources/static/photos/";


    public void uploadProfile(MultipartFile file, String userId) throws IOException {

        //확장자 분리
        String ext = extractExt(file.getOriginalFilename());

        String fileName = userId + "." + ext;
        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOAD_DIR + fileName);


        // 파일이 이미 존재하는지 확인 ,있다면 삭제
        String profileImg = userRepository.getProfileImg(userId);
        if(profileImg != null){
            Files.delete(Paths.get(UPLOAD_DIR + profileImg));
        }

        //서버에 저장
        Files.write(path, bytes);

        //db에 저장
        userRepository.uploadProfile(userId, fileName);
    }



    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    public boolean update(UserUpdateForm userUpdateForm) {


        // 원래 값 == form에서 온 update 값이 일치. => 값 변동 없음.
        // 이메일, 휴대전화 둘다 체크 => 둘다 체크해야 값이 변동 안되었다는게 확인 가능.

        //값 변동 없음 체크.
        User user = userRepository.findById(userUpdateForm.getUserId()).orElse(null);
        if(Objects.equals(user.getEmail(), userUpdateForm.getEmail())
           && Objects.equals(user.getPhoneNumber(), userUpdateForm.getPhoneNumber())){
            return false;
        }

        //T_USER 업데이트
        userRepository.updateUser(userUpdateForm.getUserId(), userUpdateForm);
        return true;
    }
}

