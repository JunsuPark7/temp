$(document).ready(function() {
    $('.info-value input[type="text"]').click(function() {
        var offsetTop = $(this).offset().top; // input 상자의 위쪽 가장자리 위치
        var scrollTop = $(window).scrollTop(); // 현재 스크롤 위치
        var scrollPosition = offsetTop - scrollTop; // input 상자가 현재 스크롤 위치에서 얼마나 떨어져 있는지

        // input 상자가 현재 뷰포트 안에 없을 경우에만 스크롤 이동
        if (scrollPosition > $(window).height() || scrollPosition < 0) {
            $('html, body').animate({
                scrollTop: offsetTop - ($(window).height() / 2) // input 상자를 뷰포트의 중앙에 위치하도록 스크롤 이동
            }, 300); // 스크롤 애니메이션 지속 시간 (밀리초)
        }
    });
});