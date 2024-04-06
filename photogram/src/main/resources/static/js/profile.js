/**
  1. 유저 프로파일 페이지
  (1) 유저 프로파일 페이지 구독하기, 구독취소
  (2) 구독자 정보 모달 보기
  (3) 구독자 정보 모달에서 구독하기, 구독취소
  (4) 유저 프로필 사진 변경
  (5) 사용자 정보 메뉴 열기 닫기
  (6) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
  (7) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달 
  (8) 구독자 정보 모달 닫기
 */

// (1) 유저 프로파일 페이지 구독하기, 구독취소
function toggleSubscribe(toUserId, obj) {
	if ($(obj).text() === "구독취소") {
		
		// ajax 모양
//		$.ajax({
//		}).done(res=>{
//		}).fail(error=>{
//		});
		
		$.ajax({
			type : "delete"
			, url : "/api/subscribe/" +  toUserId
			, dataType : "json"
		
		}).done(res=>{
			$(obj).text("구독하기");
			$(obj).toggleClass("blue");
			
		}).fail(error=>{
			console.log("구독 취소 실패", error);
		});
		
	} else {
		
			$.ajax({
			type : "post"
			, url : "/api/subscribe/" +  toUserId
			, dataType : "json"
		
		}).done(res=>{
			$(obj).text("구독취소");
			$(obj).removeClass("blue");
			
		}).fail(error=>{
			console.log("구독 하기 실패", error);
		});
		
	}
}

// (2) 구독자 정보  모달 보기
function subscribeInfoModalOpen(pageUserId) {
	
//	alert(pageUserId);
	$(".modal-subscribe").css("display", "flex");
	
	$.ajax({
		url : `/api/user/${pageUserId}/subscribe`, 
		dataType: "json"
	}).done(res=>{
		console.log(res.data);
		
		// 구독정보리스트 foreach
		res.data.forEach((u)=>{
			let item = getSubscribeModalItem(u);  // 아래 그리기 함수로 보내기
			$("#subscribeModalList").append(item);
		});
		
	}).fail(error=>{
		console.log("모달 구독정보 불러오기 오류", error);
	});
	
}

// 구독정보 모달 리스트 그리기 
function getSubscribeModalItem(u) {
	
	let item = `<div class="subscribe__item" id="subscribeModalItem-${u.id}">
	<div class="subscribe__img">
		<img src="/upload/${u.profileImageUrl}" onerror="this.src='/images/person.jpeg'"/>
	</div>
	<div class="subscribe__text">
		<h2>${u.username}</h2>
	</div>
	<div class="subscribe__btn">`;
	
	 if(!u.equalUserState){  // 동일한 유저아닐때 버튼이 만들어져야함 
		
		if(u.subscribeState){  // 구독한 상태 여부 버튼
			item += `<button class="cta" onclick="toggleSubscribe(${u.id}, this)">구독취소</button>`;
		}else{
			item += `<button class="cta blue" onclick="toggleSubscribe(${u.id}, this)">구독하기</button>`;
		}
	 }
		
		item += `
	</div>
</div>`;
	
	return item;

}


// (3) 구독자 정보 모달에서 구독하기, 구독취소 - toggleSubscribe() 이랑 동일함 
//function toggleSubscribeModal(obj) {
//	if ($(obj).text() === "구독취소") {
//		$(obj).text("구독하기");
//		$(obj).toggleClass("blue");
//	} else {
//		$(obj).text("구독취소");
//		$(obj).toggleClass("blue");
//	}
//}

	
// (3) 유저 프로파일 사진 변경 -> 버튼 클릭시 input file 실행되도록 함 
function profileImageUpload(pageUserId, principalId) {
	
	//	console.log("pageUserId : ", pageUserId );
	//	console.log("principalId : ", principalId );
	
	 if(pageUserId != principalId){  // 유저와 로그인 유저가 다르면 아무 것도 진행되지 않는다.
	 	alert("프로필 사진을 수정할 수 없는 유저입니다.");
		 return;
	 }
	
	$("#userProfileImageInput").click();

	$("#userProfileImageInput").on("change", (e) => {
		let f = e.target.files[0];

		if (!f.type.match("image.*")) {
			alert("이미지를 등록해야 합니다.");
			return;
		}

		// 서버에 이미지 전송 
		let profileImageForm = $("#userProfileImageForm")[0];
		//		console.log(profileImageForm);
		// FormData 객체를 이용하면 form 태그의 필드와 그 값을 나타내는 일련의 key/value 쌍을 담을 수 있다. 
		let formData = new FormData(profileImageForm);  // 폼태그 자체를 넣으면 값으로 담긴다.
		
		$.ajax({
			type : "put" 
			, url : `/api/user/${principalId}/profileImageUrl`
			, data: formData
			, contentType : false   	// 지정을안하면 기본 x-www-form-urlencoded 이렇게 파싱 되서 파일전송이 안됨 (필수)
			, processData: false   		// contentType을 false로 줬을 때 QueryString 자동설정되는 것을 해제 (필수)
			, enctype : "multipart/form-data"  // 폼태그 옆에 쓰면 여기 안써도 됨. 여기 쓰는게 좋음 
			, dataType : "json"  			// 리턴은 json
			
		}).done(res=>{
				// 사진 전송 성공시 이미지 변경
				let reader = new FileReader();
				reader.onload = (e) => {
					$("#userProfileImage").attr("src", e.target.result);
				}
				reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
			
		}).fail(error=>{
			console.log("프로필사진 변경 오류", error);
		});
		
	});  // $("#userProfileImageInput").on("change", ~ 
}	


// (4) 사용자 정보 메뉴 열기 닫기
function popup(obj) {
	$(obj).css("display", "flex");
}

function closePopup(obj) {
	$(obj).css("display", "none");
}


// (5) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
function modalInfo() {
	$(".modal-info").css("display", "none");
}

// (6) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달
function modalImage() {
	$(".modal-image").css("display", "none");
}

// (7) 구독자 정보 모달 닫기
function modalClose() {
	$(".modal-subscribe").css("display", "none");
	location.reload();
}






