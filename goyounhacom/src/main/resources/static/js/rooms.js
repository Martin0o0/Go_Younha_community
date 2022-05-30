document.addEventListener("DOMContentLoaded", function() {

    let  username = document.querySelector("#userinfo");
    console.dir(username);
    username = username.dataset.rol;

    let btncreate = document.querySelector("#createroombtn");

    btncreate.addEventListener("click", function(e){
        e.preventDefault();

        let roomname = document.querySelector("input[name='name']");
        if(rooname = ""){
            alert("방이름을 지어주세요.");
        }
        else{
            document.querySelector("#createform").submit();
        }
    })

});