const update = document.querySelector("#updateholics");
update.addEventListener('click', function (event) {
    if(window.confirm("홀릭스로 등업시킵니까?")){
        location.href = this.dataset.uri;
        window.alert("등업이 완료되었습니다.")
    }
});
