$(document).ready(function() {

    if ($('#darkModeToggle').is(':checked')) {
        $(".table").addClass("table-dark");
    } else {
        $(".table").removeClass("table-dark");
    }

    // 다크 모드 토글 버튼 클릭 시 이벤트 처리
    $("#darkModeToggle").click(function() {

        if ($(this).prop("checked")) {

            $.ajax({
                type: "GET",
                url: "/darkmode", // 여기에 서버 URL을 입력해주세요.
                data: {
                    darkModeFlag : "dark"
                },
                success: function(response){
                    // 다크 모드 클래스 추가
                    $("body").addClass("dark-mode");
                    $(".table").addClass("table-dark");
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
                    $(".table").removeClass("table-dark");
                },
                error: function(xhr, status, error){
                    // 요청이 실패했을 때 실행되는 함수
                    console.error(error); // 에러를 콘솔에 출력
                }
            });

        }
    });
});