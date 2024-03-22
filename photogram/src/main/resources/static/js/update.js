// (1) 회원정보 수정
function update(userId, event) {
	
	event.preventDefault(); // 폼태그 액션을 막기 
	
	let data = $("#profileUpdate").serialize();  // jquery 로 id 찾기
	console.log(data);

	$.ajax({
		type: "put",
		url : `/api/user/${userId}`,  // ' 가 아니고 ` 
		data : data,
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		dataType : "json"
	}).done(res=>{   // HttpStatus 상태코드 200번대 
		console.log("update 성공", res);
		location.href=`/user/${userId}`; 
	}).fail(error=>{  // HttpStatus 상태코드 200번대 아닐때 
//		alert(error.responseJSON.data.name); // 1. 글자만 그대로  
		alert(JSON.stringify(error.responseJSON.data)); // 2. json을 문자열로 바꿔줌 
//		console.log("update 실패" , error.responseJSON.data);
	});
		
}