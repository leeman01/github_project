/*
 * 회원 가입 할 때 Validation 제어
 */

// 전역변수 설정
let idCheck = false; // false의 경우 가입 불가능
let pwdCheck = false; // 아이디와 비밀번호가 올바른 경우에만 가입 가능하도록 설정

$(function () {
    $('#userId').on('keyup', confirm);
    $('#userId').on('blur', function () {
        $('#confirmId').html('');
    })
    $('#userPwd').on('focus', function () {
        $('#userPwdCheck').val('');
    })
    $('#submitBtn').on('click', join);
});

// 비밀번호, 비밀번호 확인, 이름, 이메일 입력 확인 (idCheck, pwdCheck = true일 경우에만 가입 가능)
function join() {
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

    // 사용자 이름 체크
    let userName = $('#userName').val();
    if (userName.trim().length == 0) {
        $("#userNameCheck").css('color', 'red');
        $("#userNameCheck").html('이름을 입력하세요.');
        return;
    }
    $('#userNameCheck').html('');

    // 이메일 체크
    let email = $('#email').val();
    if (email.trim().length == 0) {
        $("#emailCheck").css('color', 'red');
        $("#emailCheck").html('이메일을 입력하세요.');
        return;
    }
    $('#emailCheck').html('');

    // 가입 전 확인 절차
    if (!idCheck || !pwdCheck) {
        alert("정보가 정확하게 입력되지 않았습니다.");
        return;
    }

    // 가입
    $('#joinForm').submit();
}

// 사용 가능한 아이디인지 여부 판단 (ajax로 작업)
function confirm() {
    // 회원 가입 버튼 = 불가능 상태로 세팅
    // $('#submitBtn').prop('disabled', true);
    // let joinFlag = false; // false = 가입 불가능 상태로 세팅

    let userId = $('#userId').val();

    if (userId.trim().length < 3 || userId.trim().length > 5) {
        $("#confirmId").css('color', 'red');
        $("#confirmId").html('길이는 3~5자 이내로 입력');
        return;
    }

    // 중복 아이디 여부 체크
    $.ajax({
        url: '/user/confirmId'
        , method: 'POST'
        , data: { 'userId': userId }
        , success: function (resp) { // resp가 true일 때만 가입 가능
            // resp = true 이면 사용 가능한 아이디
            if (resp) {
                $("#confirmId").css('color', 'blue');
                // $('#submitBtn').prop('disabled', false);
                $("#confirmId").html('사용 가능한 아이디입니다.');
                idCheck = true;
            } else {
                $("#confirmId").css('color', 'red');
                // $('#submitBtn').prop('disabled', true);
                $("#confirmId").html('중복된 아이디입니다.');
                idCheck = false;
            }
        }
    });
    // $('#confirmId').html('');
}