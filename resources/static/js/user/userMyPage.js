 document.addEventListener('DOMContentLoaded', function() {
        const urlParams = new URLSearchParams(window.location.search);
        const status = urlParams.get('updateStatus');

        if (status === 'true') {
            alert('관리자에게 수정 요청을 보냈습니다.');
        }
    });