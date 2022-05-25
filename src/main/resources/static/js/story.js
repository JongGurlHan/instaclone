/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */

// (1) 스토리 로드하기


function getStoryItem(image) {
    let item =
    `<div class="story-list__item">
         <div class="sl__item__header">
             <div>
                 <img class="profile-image" th:src="/upload/${image.user.profileImageUrl}"
                      onerror="this.src='/images/person.jpeg'" />
             </div>
             <div>${image.user.username}</div>
         </div>

         <div class="sl__item__img">
             <img src="/${image.postImageUrl}" />
         </div>

         <div class="sl__item__contents">
             <div class="sl__item__contents__icon">

                 <button>`;
                      if(image.likeState){
                        item += `<i class="fas fa-heart active" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;

                      }else{
                        item += `<i class="far fa-heart" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;

                      }

                 item += `
                 </button>
             </div>

             <span class="like"><b id="storyLikeCount-${image.id}">${image.likeCount} </b>likes</span>

             <div class="sl__item__contents__content">
                 <p>${image.caption}</p>
             </div>

             <div id="storyCommentList-1">

                 <div class="sl__item__contents__comment" id="storyCommentItem-1">
                 <p>
                     <b>Lovely :</b> 부럽습니다.
                 </p>

                 <button>
                     <i class="fas fa-times"></i>
                 </button>

             </div>

         </div>

         <div class="sl__item__input">
             <input type="text" placeholder="댓글 달기..." id="storyCommentInput-1" />
             <button type="button" onClick="addComment()">게시</button>
         </div>

     </div>
     </div>`;


    return item

}

// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {

});


// (3) 좋아요, 안좋아요
function toggleLike(imageId) {
	let likeIcon = $(`#storyLikeIcon-${imageId}`);

	if (likeIcon.hasClass("far")) { //빈하트-> LIKE하겠다

	    $.ajax({
        			type: "post",
        			url: `/api/image/${imageId}/likes`,
        			dataType: "json"
        		}).done(res=>{

        			let likeCountStr = $(`#storyLikeCount-${imageId}`).text(); //b태그 내용의 text부분을 가져온다
        			let likeCount = Number(likeCountStr) + 1;
        			$(`#storyLikeCount-${imageId}`).text(likeCount);

        			likeIcon.addClass("fas");
        			likeIcon.addClass("active");
        			likeIcon.removeClass("far");
        		}).fail(error=>{
        			console.log("오류", error);
        		});

	} else {  //빨간하트 ->UNLIKE 하겠다.

	     $.ajax({
         			type: "delete",
         			url: `/api/image/${imageId}/likes`,
         			dataType: "json"
         		}).done(res=>{

         			let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
         			let likeCount = Number(likeCountStr) - 1;
         			$(`#storyLikeCount-${imageId}`).text(likeCount);

         			likeIcon.removeClass("fas");
         			likeIcon.removeClass("active");
         			likeIcon.addClass("far");
         		}).fail(error=>{
         			console.log("오류", error);
         		});

	}
}

// (4) 댓글쓰기
function addComment() {

	let commentInput = $("#storyCommentInput-1");
	let commentList = $("#storyCommentList-1");

	let data = {
		content: commentInput.val()
	}

	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}

	let content = `
			  <div class="sl__item__contents__comment" id="storyCommentItem-2""> 
			    <p>
			      <b>GilDong :</b>
			      댓글 샘플입니다.
			    </p>
			    <button><i class="fas fa-times"></i></button>
			  </div>
	`;
	commentList.prepend(content);
	commentInput.val("");
}

// (5) 댓글 삭제
function deleteComment() {

}







