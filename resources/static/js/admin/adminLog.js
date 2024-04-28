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



    //초기값 세팅.
    var searchCon = "";
    var searchInput = "";
    var today = new Date();
    var year = today.getFullYear(); // 연도 가져오기
    var month = ("0" + (today.getMonth() + 1)).slice(-2); // 월 가져오기 (0부터 시작하므로 +1), 두 자릿수로 변환
    var day = ("0" + today.getDate()).slice(-2); // 일 가져오기
    var searchDate = year + "-" + month + "-" + day; // yyyy-mm-dd 형식으로 조합
    var page = 0;
    var size = 10;



    $.ajax({
        url: "/admin/log/search",
        type: "GET",
        data: {
            searchCon: searchCon,
            searchInput: searchInput,
            searchDate: searchDate,
            page: page,
            size: size
        },
        success: function(data) {
            // 검색 결과를 처리하는 코드 작성
            updateTable(data); // 테이블 업데이트 함수 호출
            updateLink(data);
        },
        error: function(xhr, status, error) {
            console.error("Error:", error);
        }
    });

    $("#logSearchBtn").click(function() {
        searchCon = $("#searchCon").val();
        searchInput = $("#searchInput").val();
        searchDate = $("#searchDate").val();
        var page = 0; // 첫 번째 페이지로 설정
        var size = 10; // 페이지 크기

        $.ajax({
            url: "/admin/log/search",
            type: "GET",
            data: {
                searchCon: searchCon,
                searchInput: searchInput,
                searchDate: searchDate,
                page: page,
                size: size
            },
            success: function(data) {
                // 검색 결과를 처리하는 코드 작성
                updateTable(data); // 테이블 업데이트 함수 호출
                updateLink(data);
            },
            error: function(xhr, status, error) {
                console.error("Error:", error);
            }
        });
    });

    // 페이지 번호 클릭 이벤트 핸들러
    $(document).on("click", ".page-link", function(e) {
        e.preventDefault(); // 기본 동작(링크 이동) 방지

        var page = $(this).data("page"); // 클릭된 페이지 번호
        var size = 10; // 페이지 크기

        // AJAX 요청
        $.ajax({
            url: "/admin/log/search",
            type: "GET",
            data: {
                searchCon: searchCon,
                searchInput: searchInput,
                searchDate: searchDate,
                page: page,
                size: size
            },
            success: function(data) {
                // 검색 결과를 처리하는 코드 작성
                updateTable(data); // 테이블 업데이트 함수 호출
                updateLink(data); // 페이징 링크 업데이트 함수 호출
            },
            error: function(xhr, status, error) {
                console.error("Error:", error);
            }
        });
    });

});

// 페이징 링크 업데이트 함수
function updateLink(data) {
    var totalPages = data.totalPages; // 전체 페이지 수
    var currentPage = data.number; // 현재 페이지 번호 (0부터 시작)

    var linkContainer = $("#paginationLinks");
    linkContainer.empty(); // 이전에 생성된 링크들을 모두 지웁니다.

    // "First" 링크 생성
    var firstPageLink = $("<a>").addClass("page-link").attr("href", "#").data("page", 0).text("처음");
    linkContainer.append(firstPageLink);

    // "Previous" 링크 생성
    if (currentPage > 0) {
        var previousPageLink = $("<a>").addClass("page-link").attr("href", "#").data("page", currentPage - 1).text("이전");
        linkContainer.append(previousPageLink);
    }

    // 페이지 번호 링크 생성
    for (var i = 0; i < totalPages; i++) {
        var pageLink = $("<a>").addClass("page-link").attr("href", "#").data("page", i).text(i + 1);
        if (i === currentPage) {
            pageLink.addClass("active"); // 현재 페이지는 활성 상태로 표시
        }
        linkContainer.append(pageLink);
    }

    // "Next" 링크 생성
    if (currentPage < totalPages - 1) {
        var nextPageLink = $("<a>").addClass("page-link").attr("href", "#").data("page", currentPage + 1).text("다음");
        linkContainer.append(nextPageLink);
    }

    // "Last" 링크 생성
    var lastPageLink = $("<a>").addClass("page-link").attr("href", "#").data("page", totalPages - 1).text("마지막");
    linkContainer.append(lastPageLink);
}


function updateTable(data) {
    var tableBody = $("#logTableBody");
    tableBody.empty(); // 기존 테이블 내용 비우기

    // 검색 결과 반복 처리
    $.each(data.content, function(index, log) {
        var row = $("<tr>").addClass("table-row");
        if (log.logErrorStatus === 'F') {
            row.addClass("error-tr");
        }
        row.append($("<td>").text(index + 1)); // 순번 추가
        row.append($("<td>").addClass("clickable").attr("data-user-id", log.logUser.userId).text(log.logUser.name));
        row.append($("<td>").addClass("clickable").attr("data-user-id", log.logTargetUser.userId).text(log.logTargetUser.name));
        row.append($("<td>").text(log.logWork));
        var statusText = log.logErrorStatus === 'T' ? '성공' : '실패';
        var statusCell = $("<td>").text(statusText);
        if (log.logErrorStatus === 'F') {
            statusCell.addClass("error-value");
        }
        row.append(statusCell);
        row.append($("<td>").text(log.logDate));
        row.append($("<td>").text(log.logIP));
        row.append($("<td>").text(log.logUser.name + "님이 " + log.logDate + " [" + log.logWork + "] 작업을 하였습니다."));

        tableBody.append(row); // 테이블에 행 추가
    });
}


