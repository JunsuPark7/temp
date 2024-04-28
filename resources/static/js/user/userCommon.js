$(document).ready(function() {
   var flag=0;
   $('.share').on('click',function(){

    if(flag == 0)
     {
       $(this).siblings('.one').animate({
       bottom:'100px',
       right:'0%',
       },200);

         $(this).siblings('.one i').animate({
         bottom:'100px',
         right:'0%',
         },200);

      $(this).siblings('.two').delay(200).animate({
       bottom:'150px',
       right:'0%',
     },200);

      $(this).siblings('.three').delay(300).animate({
       bottom:'200px',
       right:'0%',
     },200);

     $('.one i,.two i, .three i').delay(500).fadeIn(200);
       flag = 1;
       $('.one div,.two div, .three div').delay(400).fadeIn(200)
     }


   else{
     $('.one, .two, .three').animate({
         bottom:'50px',
         right:'0%'
       },200);

       $('.one i,.two i, .three i').delay(500).fadeOut(200);
       $('.one div,.two div, .three div').delay(100).fadeOut(10);
       flag = 0;
     }

   });

    //행클릭시 모달
    $('#modal_comment').click(function() {
        // 모달 보이기
        $('#commentModal').modal('show');
    });


    $('#CommentConfirmButton').click(function() {

         var commentCategory = $("#commentCategory").val();
         var commentContent = $("#commentContent").val();

         // 확인 메시지 표시
        var confirmMessage = "문의 하시겠습니까?";
        if (confirm(confirmMessage)) {
            // AJAX 요청 보내기
            $.ajax({
                url: '/user/comment',
                type: 'POST',
                data: {
                    commentCategory: commentCategory,
                     commentContent: commentContent
                },
                success: function(data) {
                    // 요청이 성공적으로 처리될 때 실행할 코드
                    alert("문의를 보냈습니다.");
                    // 페이지 리로드
                    location.reload();
                },
                error: function(xhr, status, error) {
                    // 요청이 실패했을 때 실행할 코드
                    alert("요청이 실패 했습니다.");
                    console.error('요청이 실패했습니다:', status, error);
                    // 페이지 리로드
                    location.reload();

                }
            });
        }

    });




});
