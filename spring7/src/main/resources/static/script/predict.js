/**
 * 
 */
$(function () {
    $('#predictBtn').on('click', irisPredict);
});

function irisPredict() {
    let petalLength = $('#petalLength').val();
    let petalWidth = $('#petalWidth').val();
    let sepalLength = $('#sepalLength').val();
    let sepalWidth = $('#sepalWidth').val();
	
    // 문자열이 입력되지 않도록 코드 작성
    if (isNaN(petalLength) || isNaN(petalWidth) || isNaN(sepalLength) || isNaN(sepalWidth)) {
        alert("데이터는 숫자로 입력하세요.");
        return;
    }

    let sendData = {
        "petalLength": petalLength, "petalWidth": petalWidth
        , "sepalLength": sepalLength, "sepalWidth": sepalWidth
    };
	
//	alert(JSON.stringify(sendData));
	
	$.ajax({
		url: 'predict'
		, method: 'POST'
		, data: sendData
		, success: function(resp) { // resp: {'predict_result' : 'setosa'}
			$('#result').text(resp["predict_result"]);
		}
		, error: function(resp) {
			alert("err: ", JSON.stringify(resp));
		}
	});
}