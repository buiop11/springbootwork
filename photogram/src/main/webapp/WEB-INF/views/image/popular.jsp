<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<!--인기 게시글-->
<main class="popular">
	<div class="exploreContainer">

		<!--인기게시글 갤러리(GRID배치)-->
		<div class="popular-gallery">

			<c:forEach items="${images }"  var="image">
					<div class="p-img-box">
						<a href="/user/${ image.user.id }"> <img src="/upload/${ image.postImageUrl }" /> <!-- 사진 클릭시 그 유저 게시물로이동 -->
						</a>
					</div>
			</c:forEach>
			
<!-- 			<div class="p-img-box"> -->
<!-- 				<a href="/user/profile"> <img src="/images/home.jpg" /> -->
<!-- 				</a> -->
<!-- 			</div> -->
			
		</div>

	</div>
</main>

<%@ include file="../layout/footer.jsp"%>

