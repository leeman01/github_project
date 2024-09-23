// 이벤트 소스
let deleteBtn = document.getElementById("deleteBtn");
let updateBtn = document.getElementById("updateBtn");

// // 이벤트 핸들러와 연결
// deleteBtn.addEventListener('click', deleteFunc);
// updateBtn.addEventListener('click', updateFunc);

// // 이벤트 핸들러
// function deleteFunc() {
//     let boardNum = this.getAttribute("data-no");
//     let url = `/board/boardDelete?boardNum=${boardNum}`;
//     // alert("삭제버튼 클릭 " + url);
//     location.replace(url);
// }

// function updateFunc() {
//     let boardNum = this.getAttribute("data-no");
//     let url = `/board/boardUpdate?boardNum=${boardNum}`;
//     // alert("수정버튼 클릭 " + url);
//     location.replace(url);
// }

// 이벤트 핸들러와 연결
// 로그인한 사람과 글쓴이가 다르면 deleteBtn과 updateBtn이 없기 때문에 이벤트 설정을 할 수 없음 (null)
if (deleteBtn != null) deleteBtn.addEventListener('click', goto);
if (updateBtn != null) updateBtn.addEventListener('click', goto);

// 이벤트 핸들러
function goto() {
    let target = this.getAttribute("id"); // deleteBtn, updateBtn
    // let boardNum = this.getAttribute("data-no");
    let boardNum = document.getElementById("boardNum").value;
    let searchItem = document.getElementById("searchItem").value;
    let searchWord = document.getElementById("searchWord").value;

    let go = '';

    switch (target) {
        case "updateBtn": go = "boardUpdate"; break;
        case "deleteBtn":
            if (!confirm("삭제하시겠습니까?")) return;
            go = "boardDelete";
            break;
    }

    let modifyForm = document.getElementById("modifyForm");
    // let url = `/board/${go}?boardNum=${boardNum}&searchItem=${searchItem}&searchWord=${searchWord}`;
    modifyForm.action = `/board/${go}`;

    // location.replace(url); // GET 방식
    modifyForm.submit();
}