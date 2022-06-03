let postdelete = document.querySelectorAll("#deletebtn");
postdelete.forEach(function(element) {
    element.addEventListener('click', function() {
        if(window.confirm("정말로 삭제하시겠습니까?")) {
            location.href = this.dataset.uri; //data-uri를 통해 url 호출.
        };
    });
});