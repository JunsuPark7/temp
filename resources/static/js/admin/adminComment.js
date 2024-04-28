$(document).ready(function() {

    $(document).on("click", ".clickable", function(e) {
    var userId = $(this).data('user-id'); // 올바른 데이터 속성 키 이름 사용

        $.ajax({
            url: "/admin/log/searchUserId",
            type: "POST",
            data: {
                userId: userId
            },
            success: function(userData) {

                 // 입력 폼에 데이터 채우기
                $('#log-userId').val(userData.userId);
                $('#log-name').val(userData.name);
                $('#log-deptName').val(userData.dept.deptName);
                $('#log-role').val(userData.role);
                $('#log-lev').val(userData.lev);
                $('#log-number').val(userData.number);
                $('#log-phoneNumber').val(userData.phoneNumber);
                $('#log-email').val(userData.email);
                $('#log-work').val(userData.work);

                // 프로필 이미지 업데이트
                $('#preview-Image').attr('src', userData.img != null ? '/images/' + userData.img : '/images/default_profile.png');

                // 모달 보이기
                $('#logModal').modal('show');
            },
            error: function(xhr, status, error) {
                console.error("Error:", error);
            }
        });
    });
    $(document).on("click", ".comment-btn", function(e) {
        var userId = $(this).data('user-id'); // 올바른 데이터 속성 키 이름 사용
        var commentId = $(this).data('comment-id'); // 올바른 데이터 속성 키 이름 사용

        var confirmMessage = "완료 처리 하시겠습니까?";
        if (confirm(confirmMessage)) {
            $.ajax({
                url: "/admin/comment/complete",
                type: "POST",
                data: {
                    userId: userId,
                    commentId: commentId
                },
                success: function(userData) {
                    // 요청이 성공적으로 처리될 때 실행할 코드
                    alert("처리가 완료 되었습니다.");
                    // 페이지 리로드
                    location.reload();

                },
                error: function(xhr, status, error) {
                    alert("요청이 실패 했습니다.");
                    console.error('요청이 실패했습니다:', status, error);
                    // 페이지 리로드
                    location.reload();
                }
            });
        }
    });





    // Flatpickr 초기화
      flatpickr("#searchDate", {
        mode: "range", // 범위 선택 모드
        dateFormat: "Y-m-d", // 날짜 형식 설정 (yyyy-mm-dd)
        locale: "ko", // 한국어 설정
        maxDate: "today",
        // 범위 선택 제한: 시작날짜가 종료날짜 이후로 선택되지 않도록 설정
        onClose: function(selectedDates, dateStr, instance) {
          if (selectedDates[0] > selectedDates[1]) {
            instance.clear(); // 선택한 날짜들을 지움
          }
        }
      });






});


