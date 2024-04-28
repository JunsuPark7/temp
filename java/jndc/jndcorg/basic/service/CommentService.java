package jndc.jndcorg.basic.service;

import jndc.jndcorg.basic.domain.entity.Comment;
import jndc.jndcorg.basic.domain.entity.Log;
import jndc.jndcorg.basic.domain.entity.User;
import jndc.jndcorg.basic.repository.AdminRepository;
import jndc.jndcorg.basic.repository.CommentRepository;
import jndc.jndcorg.basic.repository.LogRepository;
import jndc.jndcorg.web.utils.ClientUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommentService {

    // 고유 아이디 패턴 (예: CO_0000005423)
    private final CommentRepository commentRepository;

    private static final String UNIQUE_ID_PATTERN = "^([A-Z]+)_([0-9]+)$";

    public void createComment(User user, String content, String category) {
        //로그 테이블 추가.
        // 새로운 고유 아이디 생성
        String newUniqueId = generateUniqueId(getLastUniqueId());
        String currentTime = ClientUtils.getCurrentTime();

        Comment comment = new Comment(
                newUniqueId,
                user,
                content,
                currentTime,
                category,
                "N"
        );

        commentRepository.saveComment(comment);
    }
    private String getLastUniqueId(){
        Optional<Comment> comment = commentRepository.findLastCommentById();
        final String firstCommentValue = "CO_0000000000";
        if(comment.isPresent()){
            return comment.get().getCommentId();
        } else {
            return firstCommentValue;
        }
    }


    // 최신 고유 아이디 조회 메서드
    private String generateUniqueId(String latestUniqueId) {
        // 패턴에 맞는 고유 아이디인지 검증
        Pattern pattern = Pattern.compile(UNIQUE_ID_PATTERN);
        Matcher matcher = pattern.matcher(latestUniqueId);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("올바른 형식의 고유 아이디가 아닙니다.");
        }
        // 마지막 숫자 가져오기
        String prefix = matcher.group(1);
        int lastNumber = Integer.parseInt(matcher.group(2));

        // 증가된 숫자로 고유 아이디 생성
        lastNumber++;
        String newNumber = String.format("%010d", lastNumber); // 5자리로 포맷팅

        // 새로운 고유 아이디 반환
        return prefix + "_" + newNumber;
    }




    public List<Comment> getCommentList(String searchCon, String searchInput, String searchDate) {

        String startTime = null;
        String endTime = null;

        if (!searchDate.isEmpty()){
            // 정규 표현식 패턴 정의
            String pattern = "(\\d{4}-\\d{2}-\\d{2})"; // "YYYY-MM-DD" 형식의 날짜 패턴
            Pattern regex = Pattern.compile(pattern);

            // 패턴 매칭을 통한 날짜 추출
            Matcher matcher = regex.matcher(searchDate);
            LocalDate startDate = null;
            LocalDate endDate = null;
            if (matcher.find()) {
                startDate = LocalDate.parse(matcher.group(1));
                if (matcher.find()) {
                    endDate = LocalDate.parse(matcher.group(1));
                }
            }

            // 추출된 날짜를 시작과 종료 시간으로 변환
            LocalDateTime startDateTime= LocalDateTime.of(startDate, LocalTime.MIN);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            startTime = startDateTime.format(formatter);

            LocalDateTime endDateTime = null;
            if (endDate == null) {
                endDateTime = LocalDateTime.of(startDate, LocalTime.MAX);
            } else {
                endDateTime = LocalDateTime.of(endDate, LocalTime.MAX);
            }
            endTime = endDateTime.format(formatter);

        }

        return commentRepository.findComments(searchCon,searchInput,startTime,endTime);
    }


    public void complte(String commentId) {
        commentRepository.updateYN(commentId);
    }
}

