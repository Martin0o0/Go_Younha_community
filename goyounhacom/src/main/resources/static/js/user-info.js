const update = document.querySelector("#updateholics");
update.addEventListener('click', function (event) {
    if(window.confirm("홀릭스로 등업시킵니까?")){
        location.href = this.dataset.uri;
    }
});

const updateadmin = document.querySelector("#updateadmin");
updateadmin.addEventListener('click', function (event) {
    if(window.confirm("관리자로 승급시킵니까?")){
        location.href = this.dataset.uri;
    }
});