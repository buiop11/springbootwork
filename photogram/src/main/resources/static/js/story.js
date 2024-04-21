/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */

// (0) 현재 로그인한 사용자 아이디 ( header.jsp 에 저장)
let principalId = $("#principalId").val();

// 페이지에 필요한 함수 
let page = 0;

// (1) 스토리(구독 사진들) 로드하기
function storyLoad(page) {addComment

	$.ajax({
		url : `/api/image?page=${page}`
		, dataType : "json"
	}).done(res=>{
		console.log(res);		
		
//		res.data.forEach((image)=>{
		res.data.content.forEach((image)=>{  // 페이징 추가 후 변경 
			let storyItem = getStoryItem(image);
			$("#storyList").append(storyItem);
		});
		
	}).fail(error=>{
		console.log("스토리로드 실패", error);		
	});
}


// 스토리 정보 호출
storyLoad();


// 스토리 이미지 출력 
function getStoryItem(image) {
	
	let item = `<div class="story-list__item">
	<a href="/user/${ image.user.id }">
		<div class="sl__item__header">
			<div>
				<img class="profile-image" src="/upload/${ image.user.profileImageUrl }"
					onerror="this.src='/images/person.jpeg'" />
			</div>
			<div>${ image.user.username }</div>
		</div>
	</a>	

	<div class="sl__item__img">
		<img src="/upload/${ image.postImageUrl }" />
	</div>

	<div class="sl__item__contents">
		<div class="sl__item__contents__icon">

			<button>`;
			
			// 좋아요 본인 여부 
			if(image.likeState){
				item+= `<i class="fas fa-heart active" id="storyLikeIcon-${ image.id }" onclick="toggleLike(${ image.id })"></i>`;
			}else{
				item += `<i class="fas fa-heart" id="storyLikeIcon-${ image.id }" onclick="toggleLike(${ image.id })"></i>`;
			}
		
		item += `
		</button>
	</div>

	<span class="like"><b id="storyLikeCount-${ image.id }">${ image.likeCount } </b>likes</span>

	<div class="sl__item__contents__content">
		<p>${ image.caption }</p>
	</div>

	<div id="storyCommentList-${ image.id }">`;

	// 이미지에 따른 코멘트 처리 
	 image.comments.forEach((comment)=>{
		 item += `<div class="sl__item__contents__comment" id="storyCommentItem-${ comment.id }">
			<p>
				<b>${ comment.user.username } :</b> ${ comment.content }
			</p>`;

		if(principalId == comment.user.id){
			 item += `<button onclick="deleteComment(${ comment.id })">
								<i class="fas fa-times"></i>
							</button>`;
		}	
		
		item += `</div>`;
	 });
	


	item += `
		</div>
	
		<div class="sl__item__input">
			<input type="text" placeholder="댓글 달기..." id="storyCommentInput-${ image.id }" />
			<button type="button" onClick="addComment(${ image.id })">게시</button>
			</div>
	
		</div>
	</div>`;
	
	return item;

}

// (2) 스토리 스크롤 페이징하기 (스크롤 할때마다 추가로 페이지를 불러온다.)
// window. scroll 이벤트 
$(window).scroll(() => {  

//	console.log("스크롤 중");
//	console.log("윈도우 scrollTop", $(window).scrollTop());
//	console.log("문서의 높이", $(document).height()); // 고정
//	console.log("윈도우 높이", $(window).height());     // 고정

	let checkNum = $(window).scrollTop() - ($(document).height() - $(window).height());
//	console.log(checkNum);
	
	if(checkNum < 1  && checkNum > -1){
		page++;
		storyLoad(page); // 스토리 정보 호출 
	}

});


// (3) 좋아요, 안좋아요
function toggleLike(imageId) {
	let likeIcon = $(`#storyLikeIcon-${imageId}`);  // ${} 가 "" 쌍따옴표 안에 있으면 안먹는다. 백팁으로 `` 변경해야함
	
	// 누를때 api 작동해야함. 
	if (likeIcon.hasClass("far")) { // 좋아요 없는 경우 - 좋아요 하겠다.
		
		$.ajax({
				type : "post"
				, url : `/api/image/${imageId}/likes`			
				, dataType : "json"
		}).done(res=>{
			
			// 카운트 가져오기 
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text();  // 태그에 접근해서 1 추가하려고함
//			console.log("좋아요 카운트", likeCountStr);
			let likeCount = Number(likeCountStr + 1);
//			console.log("좋아요 카운트 증가", likeCount);
			$(`#storyLikeCount-${imageId}`).text(likeCount); // 증가 수 넣기 
			
			likeIcon.addClass("fas");
			likeIcon.addClass("active");
			likeIcon.removeClass("far");
			
		}).fail(error=>{
			console.log("좋아요 오류", error);
		});
		
		
	} else {  // 좋아요 있어서 취소하는 경우
		
		$.ajax({
				type : "delete"
				, url : `/api/image/${imageId}/unlikes`			
				, dataType : "json"
		}).done(res=>{
			
			// 카운트 가져오기 
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text();  // 태그에 접근해서 1 추가하려고함
			let likeCount = Number(likeCountStr - 1);
			$(`#storyLikeCount-${imageId}`).text(likeCount); // 뺀 수 넣기 
			
			likeIcon.removeClass("fas");
			likeIcon.removeClass("active");
			likeIcon.addClass("far");
			
		}).fail(error=>{
			console.log("좋아요 취소 오류", error);
		});
		
	}
}


// (4) 댓글쓰기
function addComment(imageId) {

	let commentInput = $(`#storyCommentInput-${imageId}`);
	let commentList = $(`#storyCommentList-${imageId}`);

	let data = {
		imageId : imageId,
		content: commentInput.val()
	}
	
//	console.log(data.content);
//	console.log(data);  // js object
//	console.log(JSON.stringify(data)); // data값을 json으로 변환
//	return;

	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}
	
	// 댓글 데이터 통신 
	$.ajax({
		type: "post"
		, url : "/api/comment"
		, data : JSON.stringify(data)
		, contentType: "application/json; charset=utf-8"
		, dataType : "json"
		
	}).done(res=>{
		
//		console.log("댓글 작성 성공", res);
		
			let comment = res.data;
		
			let content = `
			  <div class="sl__item__contents__comment" id="storyCommentItem-${ comment.id }"> 
			    <p>
			      <b>${ comment.user.username } :</b>
			      ${ comment.content }
			    </p> 
				    <button onclick="deleteComment(${ comment.id })">
				    	<i class="fas fa-times"></i>
				    </button>
			   </div>`;
			  
			commentList.prepend(content);
		
	}).fail(error=>{
		console.log("댓글 작성 오류 + ", error.responseJSON.data.content);
		alert(error.responseJSON.data.content);
	});

	commentInput.val("");   // 인풋필드를 비워준다. 
	
}

// (5) 댓글 삭제
function deleteComment(commentId) {

	$.ajax({
		type : "delete"
		, url : `/api/comment/${commentId}`
		, dataType : "json"
	}).done(res=>{
		console.log("댓글 삭제 성공", res);
		
		$(`#storyCommentItem-${ commentId }`).remove();
		
	}).fail(error=>{
		 console.log("댓글 삭제 실패", error);
	});

}







