let postdelete = document.querySelectorAll("#deletebtn");
postdelete.forEach(function(element) {
    element.addEventListener('click', function() {
        if(window.confirm("정말로 삭제하시겠습니까?")) {
            location.href = this.dataset.uri; //data-uri를 통해 url 호출.
        };
    });
});



let cnturl = window.location.href;

let addscrap = document.querySelector("#scrap");
if(addscrap != null){
    addscrap.addEventListener('click', function (event) {
        if(window.confirm("이글을 스크랩합니까?")) {
            let uri = this.dataset.uri;
            console.log(uri);
            var xhr = new XMLHttpRequest();
            /* Post 방식으로 요청 */
            let url = uri;
            console.log(url);
            xhr.open('POST', url, true);
            xhr.send();
            xhr.onload = function () { //통신 성공
                if (xhr.DONE) {
                    console.log(xhr.response);
                    console.log("통신 성공");
                    alert('스크랩되었습니다.');
                    window.location.href = cnturl
                } else { //통신 실패
                    alert("스크랩에 실패했습니다.")
                    console.log("스크랩 실패");
                }
            }
        }
    });
}

let deletescrap = document.querySelector("#deletescrap");
if(deletescrap != null){
    deletescrap.addEventListener('click', function (event) {
        if(window.confirm("스크랩을 취소합니까?")) {
            let uri = this.dataset.uri;
            var xhr = new XMLHttpRequest();
            /* Post 방식으로 요청 */
            let url = uri;
            console.log(url);
            xhr.open('POST', url, true);
            /* Response Type을 Json으로 사전 정의 */
            // xhr.responseType = "json";
            //
            // xhr.setRequestHeader('Content-type', 'application/json');
            xhr.send();
            xhr.onload = function () { //통신 성공
                if (xhr.DONE) {
                    console.log(xhr.response);
                    console.log("통신 성공");
                    alert('스크랩이 취소되었습니다.');
                    window.location.href = cnturl;
                } else { //통신 실패
                    alert("스크랩에 실패했습니다.")
                    console.log("스크랩 실패");
                }
            }
        }
    });
}
