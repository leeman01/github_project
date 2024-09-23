/*
 * 회원 정보 수정을 위한 검증
 */

// 전역변수
let pwdCheck = false;

$(function () {
    $('#userPwd').on('focus', function () {
        $('#userPwdCheck').val('');
    })
    $('#submitBtn').on('click', update);
});

// 비밀번호, 비밀번호 확인, 이름, 이메일 입력 확인 (idCheck, pwdCheck = true일 경우에만 가입 가능)
function update() {
    let userPwd = $('#userPwd').val();
    if (userPwd.trim().length < 3 || userPwd.trim().length > 5) {
        $("#confirmPwd").css('color', 'red');
        $("#confirmPwd").html('길이는 3~5자 이내로 입력');
        pwdCheck = false;
        return;
    }

    let userPwdCheck = $('#userPwdCheck').val();

    if (userPwd.trim() != userPwdCheck.trim()) {
        $("#confirmPwd").css('color', 'red');
        $("#confirmPwd").html('비밀번호와 입력된 값이 다릅니다.');
        pwdCheck = false;
        return;
    }

    $('#confirmPwd').html('');
    pwdCheck = true;

    // 이메일 체크
    let email = $('#email').val();
    if (email.trim().length == 0) {
        $("#emailCheck").css('color', 'red');
        $("#emailCheck").html('이메일을 입력하세요.');
        return;
    }
    $('#emailCheck').html('');

    // 수정 진행 전에 확인 절차
    if (!pwdCheck) {
        alert("정보가 정확하게 입력되지 않았습니다.");
        return;
    }

    $('#updateForm').submit();
}

