// (1) 회원정보 수정

function update(userId, event) {
    event.preventDefault();

    console.log('이벤트정보:', event);

    //form의 profileUpdate테그를 찾아서 그 폼테그가 들고 있는 정보를 시리얼라이즈
    let data = $("#profileUpdate").serialize();

    console.log(data);
    $.ajax({
        type:"put",
		url : `/api/user/${userId}`,
        data: data,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json"
    }).done(res =>{ //Http상태코드 200번대 done이 뜬다.
        console.log("update 성공", res);
        alert("update 성공");
        location.href = `/user/${userId}`;
    }).fail(error => {
       if(error.data ==null){
           alert(error.responseJSON.message);
       }else{
           //json object를 Json 문자열로 변환
           alert(JSON.stringify(error.responseJSON.data));
           }
    });

}