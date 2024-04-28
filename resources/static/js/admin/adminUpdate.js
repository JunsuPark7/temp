$(document).ready(function() {

//    업데이트 완료 요청메시지
    const urlParams = new URLSearchParams(window.location.search);
    const status = urlParams.get('updateStatus');

    if (status === 'true') {
        alert('수정을 완료 했습니다.');
    }


    var userIds = []; // 선택된 사용자의 userId 값을 저장할 배열
    // 체크박스 변경 시 이벤트 처리
    $('.update-checkBox').change(function() {
        var userId = $(this).closest('tr').find('#table-userId').text(); // 체크된 행의 userId 값 추출
        if ($(this).is(':checked')) {
            // 체크된 경우 배열에 추가
            userIds.push(userId);
        } else {
            // 체크 해제된 경우 배열에서 제거
            var index = userIds.indexOf(userId);
            if (index !== -1) {
                userIds.splice(index, 1);
            }
        }
    });

    // 전체 선택 체크박스 클릭 이벤트 처리
    $('.all-update-checkBox').change(function() {
        // 전체 선택 체크박스의 상태 가져오기
        var isChecked = $(this).is(':checked');
        // 개별 선택 체크박스 상태 업데이트
        $('.update-checkBox').prop('checked', isChecked);

        // 전체 선택이 체크되었을 때
        if (isChecked) {
            // 각 사용자의 userId 값을 가져와서 userIds 배열에 추가
            $('.update-checkBox').each(function() {
                var userId = $(this).closest('tr').find('#table-userId').text(); // 사용자의 userId 값 추출
                userIds.push(userId); // 배열에 userId 추가
            });
        } else {
            // 전체 선택이 해제되었을 때 userIds 배열 비우기
            userIds = [];
        }
    });

  $('#updateAll').click(function(event) {
        event.preventDefault(); // 기본 이벤트 제거 (폼 제출 방지)

        if(userIds.length === 0 ){
            alert("직원을 선택해주세요.");
            return;
        }
        // 확인 메시지 표시
        var confirmMessage = "일괄 수정하시겠습니까?";
        if (confirm(confirmMessage)) {
            // AJAX 요청 보내기
            $.ajax({
                url: '/admin/update/userUpdateAll',
                type: 'POST',
                contentType: 'application/json', // JSON 형식으로 데이터를 전송
                data: JSON.stringify({ userIds: userIds }), // 선택된 사용자의 userId 배열을 JSON 문자열로 변환하여 전달
                success: function(data) {
                    // 요청이 성공적으로 처리될 때 실행할 코드
                    alert(data + "개 요청이 처리 되었습니다.");
                    // 페이지 리로드
                    location.reload();
                },
                error: function(xhr, status, error) {
                    // 요청이 실패했을 때 실행할 코드
                    alert("요청이 실패 했습니다.");
                    // 페이지 리로드
                    location.reload();
                    console.error('요청이 실패했습니다:', status, error);
                }
            });
        }
    });



    $('#exampleModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);


        var userIdValue = button.siblings('#table-userId').text();
        var nameValue = button.siblings('#table-name').text();
        var deptNameValue = button.siblings('#table-deptName').text();
        var phoneNumberValue = button.siblings('#table-phoneNumber').text();
        var emailValue = button.siblings('#table-email').text();
        var updatePhoneNumber = button.siblings('#table-updatePhoneNumber').text();
        var updateEmail = button.siblings('#table-updateEmail').text();


        $(this).find('.modal-body input[name="userId"]').val(userIdValue);
        $(this).find('.modal-body input[name="name"]').val(nameValue);
        $(this).find('.modal-body input[name="deptName"]').val(deptNameValue);
        $(this).find('.modal-body input[name="phoneNumber"]').val(phoneNumberValue);
        $(this).find('.modal-body input[name="updatePhoneNumber"]').val(updatePhoneNumber);
        $(this).find('.modal-body input[name="email"]').val(emailValue);
        $(this).find('.modal-body input[name="updateEmail"]').val(updateEmail);
    });


//    수정 확인.
    $('#confirmButton').click(function(event) {
        event.preventDefault(); // 기본 동작 방지

        // 확인 메시지 표시
        var confirmMsg = "수정 하시겠습니까?";
        if (confirm(confirmMsg)) {
            // 확인 버튼을 누른 경우 수정 폼 제출
            $('#updateForm').submit();
        }
    });




});