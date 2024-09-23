/**
 * 검색 기능을 처리하는 스크립트
*/
// 이벤트 소스
let search = document.getElementById("search");

// 이벤트 핸들러와 연결
search.addEventListener('click', goto);

// 이벤트 핸들러
function goto() {
    // alert("검색 버튼 클릭");
    let searchItem = document.getElementById("searchItem").value;
    let searchWord = document.getElementById("searchWord").value;
    let searchForm = document.getElementById("searchForm");

    searchForm.action = '/board/boardList';
    searchForm.submit();
}

/**
 * 페이징 시, current 페이지 요청 함수
 */
function pageFormSubmit(page) {
    // alert(요청 페이지: + page);
    document.getElementById("requestPage").value = page;
    document.getElementById("searchForm").submit();
}