
    function previewImage(event) {
        var input = event.target;
        var reader = new FileReader();

        reader.onload = function() {
            var imgElement = document.getElementById('previewImage');
            imgElement.src = reader.result;
            imgElement.style.filter = 'none'; // filter 속성 제거
            document.getElementById('chooseFileButton').style.display = 'none'; // 수정 버튼 숨기기
        };

        reader.readAsDataURL(input.files[0]);
    }


    document.addEventListener('DOMContentLoaded', function() {

        document.getElementById('chooseFileButton').addEventListener('click', function() {
            document.getElementById('fileInput').click();
        });

        document.querySelector('input[name="phoneNumber"]').addEventListener('input', function(e) {
            let phoneNumber = e.target.value.replace(/\D/g, ''); // 숫자가 아닌 문자 제거

            if (phoneNumber.length > 3 && phoneNumber.length <= 7) {
                phoneNumber = phoneNumber.replace(/(\d{3})(\d{1,4})/, '$1-$2');
            } else if (phoneNumber.length > 7) {
                phoneNumber = phoneNumber.replace(/(\d{3})(\d{4})(\d{1,4})/, '$1-$2-$3');
            }

            e.target.value = phoneNumber;
        });


        document.getElementById('domain').addEventListener('change', function() {
            var emailInput = document.querySelector('input[name="email"]');
            var selectedDomain = this.value;

            if (selectedDomain === 'direct_input') {
                // 직접입력 옵션 선택 시 아무 작업도 하지 않음
                return;
            }

            // 현재 이메일 값이 있는지 확인
            var currentEmailValue = emailInput.value.trim();
            var atIndex = currentEmailValue.indexOf('@'); // @의 위치 확인

            if (atIndex !== -1) {
                // 이메일 값에 @이 포함되어 있으면 도메인을 선택한 값으로 대체
                emailInput.value = currentEmailValue.substring(0, atIndex) + selectedDomain;
            } else {
                // 이메일 값이 비어있을 경우 도메인만 추가
                emailInput.value = currentEmailValue + selectedDomain;
            }
        });

         $('#confirmButton').click(function(event) {
                event.preventDefault(); // 기본 동작 방지

                // 확인 메시지 표시
                var confirmMsg = "수정 요청 하시겠습니까?";
                if (confirm(confirmMsg)) {
                    // 확인 버튼을 누른 경우 수정 폼 제출
                    $('#updateForm').submit();
                }
            });

    });

