let scrapdelete = document.querySelectorAll("#delete");
scrapdelete.forEach(function(element) {
    element.addEventListener('click', function() {
        if(window.confirm("정말로 삭제하시겠습니까?")) {
            let uri = this.dataset.uri;
            let userid = this.dataset.userid;
            var xhr = new XMLHttpRequest();
            /* Post 방식으로 요청 */
            let url = uri;
            console.log(url);
            xhr.open('DELETE', url, true);
            /* Response Type을 Json으로 사전 정의 */
            // xhr.responseType = "json";
            //
            // xhr.setRequestHeader('Content-type', 'application/json');
            xhr.send();
            xhr.onload = function () { //통신 성공
                if (xhr.DONE) {
                    console.log(xhr.response);
                    console.log("통신 성공");
                    alert('삭제되었습니다.');
                    window.location.href = '/auth/user/scrap/' + userid;
                } else { //통신 실패
                    alert("삭제에 실패했습니다.")
                    console.log("삭제 실패");
                }
            }


        };
    });
});