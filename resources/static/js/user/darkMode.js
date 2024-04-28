$(document).ready(function() {

    if ($('#darkModeToggle').is(':checked')) {
        $('.two i').html('<i class="fas fa-moon"></i>');
    } else {
        $('.two i').html('<i class="fas fa-sun"></i>');
    }




    $(".two").click(function() {
       // 체크박스 상태를 반전시킵니다.
        $("#darkModeToggle").prop("checked", function(i, value) {
            return !value;
        });

        if ($('#darkModeToggle').prop("checked")) {
            $.ajax({
                type: "GET",
                url: "/darkmode", // 여기에 서버 URL을 입력해주세요.
                data: {
                    darkModeFlag : "dark"
                },
                success: function(response){
                    // 다크 모드 클래스 추가
                    $("body").addClass("dark-mode");
                    $('.two i').html('<i class="fas fa-moon"></i>');
                },
                error: function(xhr, status, error){
                    // 요청이 실패했을 때 실행되는 함수
                    console.error(error); // 에러를 콘솔에 출력
                }
            });
        } else {
             $.ajax({
                type: "GET",
                url: "/darkmode", // 여기에 서버 URL을 입력해주세요.
                data: {
                    darkModeFlag : "light"
                },
                success: function(response){
                    // 다크 모드 클래스 제거
                    $("body").removeClass("dark-mode");
                    $('.two i').html('<i class="fas fa-sun"></i>');
                },
                error: function(xhr, status, error){
                    // 요청이 실패했을 때 실행되는 함수
                    console.error(error); // 에러를 콘솔에 출력
                }
            });

        }



    });
});