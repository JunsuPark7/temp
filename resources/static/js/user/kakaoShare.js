// 카카오 링크를 공유하는 함수
function shareKakao() {

    $.ajax({
        // 요청 설정
        url: '/api/data', // 요청 URL
        method: 'GET', // 요청 방식 (GET, POST, PUT, DELETE 등)
        success: function(data) { // 요청 성공 시 실행될 함수
            Kakao.init(data);

            Kakao.Link.sendDefault({
                objectType: 'feed',
                content: {
                    title: '전남개발공사 조직도', // 공유될 제목
                    description: '전남을 잇고 행복을 짓는 도민 공기업! 전남개발공사 전직원 조직도 입니다.', // 공유될 설명
                    imageUrl: 'https://www.jndc.co.kr/resources/img/sub/ci_img1.png', // 이미지 URL
                    link: {
                        mobileWebUrl: 'http://localhost:8090/', // 모바일 웹 페이지 URL
                        webUrl: 'http://localhost:8090/' // PC 웹 페이지 URL
                    }
                },
                buttons: [
                    {
                        title: '웹으로 보기',
                        link: {
                            mobileWebUrl: 'http://localhost:8090/', // 모바일 웹 페이지 URL
                            webUrl: 'http://localhost:8090/' // PC 웹 페이지 URL
                        }
                    }
                ],
                installTalk: true
            });
        },
        error: function(error) { // 요청 실패 시 실행될 함수
            // 오류 처리
            console.log(error);
        }
    });













}
