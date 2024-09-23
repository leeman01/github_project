/**
 * 댓글 관련 ajax 코드
 */
$(function () {
    // alert('ㅇㅋ');
    init();
    $('#replyBtn').on('click', replyWrite);
});

// 보고 있는 게시글의 모든 댓글
// Thymeleaf 문법 사용 불가 - 혼용하지 않도록 주의
function init() {
    let boardNum = $('#boardNum').val(); // th:text = "${board.boardNum}"; 불가능

    $.ajax({
        url: '/reply/replyAll'
        , method: 'GET'
        , data: { 'boardNum': boardNum }
        , success: output
    });
}

// 댓글 목록 출력
function output(resp) { // let resp = [{}, {}];
    if (resp.length == 0) return;
    let tag = `
    <table>
        <tr>
        <th>작성자</th>
        <th>내용</th>
        <th>작성일</th>
        <th>버튼</th>
        </tr>
    `;

    $.each(resp, function (index, item) {
        tag += `
        <tr>
            <td class="reply-writer">${item["replyWriter"]}</td>
            <td class="reply-text">${item["replyText"]}</td>
            <td class="reply-date">${item["createDate"]}</td>
            <td class="btns">`;

        // 로그인한 아이디(html에서 읽어온 값)와 댓글 쓴 사용자
        if ($("#loginId").val() == item["replyWriter"]) {
            tag += `<input type="button" class="deleteBtn btn btn-light" data-num="${item['replyNum']}" value="삭제">`
        }

        tag += `
			</td>
            </tr>
            `;
    });

    tag += `</table>`;

    $('#reply-list').html(tag);
    $('.deleteBtn').on('click', deleteReply) // 이벤트 설정
}

// 댓글 삭제 (댓글 작성자가 삭제)
function deleteReply() {
    let replyNum = $(this).attr('data-num');

    $.ajax({
        url: '/reply/replyDelete'
        , method: 'GET'
        , data: { "replyNum": replyNum }
        , success: function (resp) {
            init();
        }
    })
}

// 댓글 작성
function replyWrite() {
    let writer = $('#loginId').val();       // 로그인한 사람의 이름
    let replyText = $('#replyText').val();  // 댓글을 입력하지 않고 전송 버튼을 누른 경우 -> 실행되지 않도록 설정할 필요 有
    let boardNum = $('#boardNum').val();

    // POST, replyInsert
    let sendData = {
        "replyWriter": writer
        , "replyText": replyText
        , "boardNum": boardNum
    }

    $.ajax({
        url: '/reply/replyInsert'
        , method: 'POST'
        , data: sendData
        , success: function () {
            $('#replyText').val("");
            init();
        }
    })
}