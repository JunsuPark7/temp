
 $(document).ready(function() {

    //    업데이트 완료 요청메시지
        const urlParams = new URLSearchParams(window.location.search);
        const status = urlParams.get('updateStatus');

        if (status === 'true') {
            alert('수정을 완료 했습니다.');
        }

    // 모달이 열릴 때 실행될 함수
    $('#exampleModal').on('show.bs.modal', function (event) {
        // 모달 열릴 때 발생하는 이벤트에서 이벤트를 발생시킨 요소(버튼) 가져오기
        var button = $(event.relatedTarget);
        // 해당 버튼의 부모 요소(td)에서 user.flag 값을 가져오기
        var flagValue = button.siblings('#table-flag').text();
        var userIdValue = button.siblings('#table-userId').text();
        // 가져온 flagValue 값을 모달 내부의 적절한 위치에 표시
        $(this).find('.modal-body input#modal-userId').val(userIdValue);
        if (flagValue === '0') {
            $('#userFlag').prop('checked', true);
        } else if (flagValue === '1') {
            $('#adminFlag').prop('checked', true);
        }
    });
});