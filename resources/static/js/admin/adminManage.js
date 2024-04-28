

$(document).ready(function() {

    //    업데이트 완료 요청메시지
    const urlParams = new URLSearchParams(window.location.search);
    const status = urlParams.get('updateStatus');
    const createStatus = urlParams.get('createStatus');

    if (status === 'true') {
        alert('수정 완료 했습니다.');
    }
    if (createStatus === 'true'){
        alert('등록 완료 했습니다.');
    } else if(createStatus === 'false') {
        alert('등록 실패 했습니다. (아이디 중복)');
    }

    //정렬
    $('.sortable').click(function() {
        var $table = $(this).closest('table');
        var columnIndex = $(this).index();
        var sortOrder = $(this).data('sort-order') || 'asc';
        var dataType = $(this).data('key'); // 열의 데이터 유형 가져오기

        // 열 제목의 화살표를 모두 초기화
        $table.find('.sort-arrow').html('&#x25B2;');

        // 현재 클릭된 열의 화살표를 업데이트
        var arrow = sortOrder === 'asc' ? '&#x25BC;' : '&#x25B2;';
        $(this).find('.sort-arrow').html(arrow);


        var rows = $table.find('tbody > tr').get();
        rows.sort(function(a, b) {
            var textA = $(a).children().eq(columnIndex).text().toUpperCase();
            var textB = $(b).children().eq(columnIndex).text().toUpperCase();

            // 데이터 유형에 따라 비교 방법 선택
            if (dataType === 'name') {
                return (sortOrder === 'asc') ? (textA < textB ? -1 : textA > textB ? 1 : 0) : (textB < textA ? -1 : textB > textA ? 1 : 0);
            } else if (dataType === 'role') {
                var textA = $(a).children().eq(columnIndex + 1).text().toUpperCase();
                var textB = $(b).children().eq(columnIndex + 1).text().toUpperCase();// 숫자로 비교하기 위해 문자열을 숫자로 변환
                textA = parseInt(textA, 10);
                textB = parseInt(textB, 10);
                return (sortOrder === 'asc') ? textA - textB : textB - textA;
            }
        });

        if (sortOrder === 'asc') {
            $(this).data('sort-order', 'desc');
        } else {
            $(this).data('sort-order', 'asc');
        }

        $.each(rows, function(index, row) {
            $table.children('tbody').append(row);
        });
    });


    //행클릭시 모달
    $('.table-manage-row').click(function() {
        // 데이터를 가져옵니다.
        var userData = {
            img: $(this).find('.table-img').text(),
            userId: $(this).find('.table-userId').text(),
            name: $(this).find('.table-name').text(),
            deptName: $(this).find('.table-deptName').text(),
            deptId: $(this).find('.table-deptId').text(),
            role: $(this).find('.table-role').text(),
            lev: $(this).find('.table-lev').text(),
            number: $(this).find('.table-number').text(),
            phoneNumber: $(this).find('.table-phoneNumber').text(),
            email: $(this).find('.table-email').text(),
            work: $(this).find('.table-work').text()
        };
        console.log(userData);

        // 모달에 데이터 설정
          $('#preview-Image').attr('src', userData.img ? '/images/' + userData.img : '/images/default_profile.png');
          $('#update-userId').val(userData.userId);
          $('#update-name').val(userData.name);
          $('#update-deptName').val(userData.deptId);
          $('#update-role').val(userData.role);
          $('#update-lev').val(userData.lev);
          $('#update-number').val(userData.number);
          $('#update-phoneNumber').val(userData.phoneNumber);
          $('#update-email').val(userData.email);
          $('#update-work').val(userData.work);

        // 모달 보이기
        $('#updateModal').modal('show');
    });


    //직원 등록 폼
    $('#createBtn').click(function(event) {
        event.preventDefault(); // 기본 동작 방지
        $('#create-userId').val('DU_0000000000');
        // 모달 보이기
        $('#createModal').modal('show');
    });



    $("#check-userId").click(function(){
        event.preventDefault(); // 기본 동작 방지
        var userId = $("#create-userId").val(); // 아이디 입력 필드에서 값을 가져옵니다.

        // AJAX POST 요청 보내기
        $.ajax({
            type: "POST",
            url: "/admin/manage/checkUserId", // 여기에 서버 URL을 입력해주세요.
            data: {
                userId : userId
            }, // JSON.stringify를 사용하여 JSON 형식으로 변환
            success: function(response){
                // 서버로부터 응답을 받았을 때 실행되는 함수
                // 응답에 따른 동작을 추가해주세요.
                 alert(response);
                if(response === "고유 아이디 값이 중복입니다. 값을 바꿔주세요."){
                    $('#create-userId').css('border', '1px solid red'); // 중복이면 테두리를 빨간색으로 변경
                } else {
                    $('#create-userId').css('border', '1px solid green'); // 중복이 아니면 테두리를 초록색으로 변경
                }


            },
            error: function(xhr, status, error){
                // 요청이 실패했을 때 실행되는 함수
                console.error(error); // 에러를 콘솔에 출력
            }
        });
    });

//    uiKey는 어떤식으로 받을지..
//    $("#check-uiKey").click(function(){
//        event.preventDefault(); // 기본 동작 방지
//        var userId = $("#create-uiKey").val(); // 아이디 입력 필드에서 값을 가져옵니다.
//
//        // AJAX POST 요청 보내기
//        $.ajax({
//            type: "POST",
//            url: "/admin/manage/checkUserId", // 여기에 서버 URL을 입력해주세요.
//            data: { userId: userId }, // userId를 POST 요청으로 보냅니다.
//            success: function(response){
//                // 서버로부터 응답을 받았을 때 실행되는 함수
//                // 응답에 따른 동작을 추가해주세요.
//                alert(response);
//                if(response === "고유 아이디 값이 중복입니다. 값을 바꿔주세요."){
//                    $('#create-uiKey').css('border', '1px solid red'); // 중복이면 테두리를 빨간색으로 변경
//                } else {
//                    $('#create-uiKey').css('border', '1px solid green'); // 중복이 아니면 테두리를 초록색으로 변경
//                }
//            },
//            error: function(xhr, status, error){
//                // 요청이 실패했을 때 실행되는 함수
//                console.error(error); // 에러를 콘솔에 출력
//            }
//        });
//    });







    //전화번호
    $('input[name="phoneNumber"]').on('input', function(e) {
        let phoneNumber = $(this).val().replace(/\D/g, ''); // 숫자가 아닌 문자 제거

        if (phoneNumber.length > 3 && phoneNumber.length <= 7) {
            phoneNumber = phoneNumber.replace(/(\d{3})(\d{1,4})/, '$1-$2');
        } else if (phoneNumber.length > 7) {
            phoneNumber = phoneNumber.replace(/(\d{3})(\d{4})(\d{1,4})/, '$1-$2-$3');
        }

        $(this).val(phoneNumber);
    });

    //이메일
    $('input[name="domain"]').on('change', function() {
        var emailInput = $('input[name="email"]');
        var selectedDomain = $(this).val();

        if (selectedDomain === 'direct_input') {
            // 직접입력 옵션 선택 시 아무 작업도 하지 않음
            return;
        }

        // 현재 이메일 값이 있는지 확인
        var currentEmailValue = emailInput.val().trim();
        var atIndex = currentEmailValue.indexOf('@'); // @의 위치 확인

        if (atIndex !== -1) {
            // 이메일 값에 @이 포함되어 있으면 도메인을 선택한 값으로 대체
            emailInput.val(currentEmailValue.substring(0, atIndex) + selectedDomain);
        } else {
            // 이메일 값이 비어있을 경우 도메인만 추가
            emailInput.val(currentEmailValue + selectedDomain);
        }
    });



    $('#chooseFileButton').click(function() {
        $('#fileInput').click();
    });

    $('#fileInput').change(function(event) {
        previewImage(event);
    });

    $('#create-chooseFileButton').click(function() {
        $('#create-fileInput').click();
    });

    $('#create-fileInput').change(function(event) {
        createPreviewImage(event);
    });


    // 수정 확인.
    $('#confirmButton').click(function(event) {
        event.preventDefault(); // 기본 동작 방지

        // 확인 메시지 표시
        var confirmMsg = "수정 하시겠습니까?";
        if (confirm(confirmMsg)) {
            // 확인 버튼을 누른 경우 수정 폼 제출
            $('#updateForm').submit();
        }
    });

    // 등록 확인.
    $('#confirmButton2').click(function(event) {
        event.preventDefault(); // 기본 동작 방지

        // 확인 메시지 표시
        var confirmMsg = "등록 하시겠습니까?";
        if (confirm(confirmMsg)) {
            // 확인 버튼을 누른 경우 수정 폼 제출
            $('#createForm').submit();
        }
    });

});

function previewImage(event) {
    var input = event.target;
    var reader = new FileReader();

    reader.onload = function() {
        $('#preview-Image').attr('src', reader.result);
    };

    reader.readAsDataURL(input.files[0]);
}

function createPreviewImage(event) {
    var input = event.target;
    var reader = new FileReader();

    reader.onload = function() {
        $('#create-preview-Image').attr('src', reader.result);
    };

    reader.readAsDataURL(input.files[0]);
}

