var result = document.querySelector("#file");

result.addEventListener('change', function (){
    console.log("변화가 일어남.")
    readURL(this);
})

function readURL(input) {
    if (input.files && input.files[0]) {
        let reader = new FileReader(); //파일리더 객체로 사용자 파일을 객체로 받아오자.
        reader.onload = function (e) {
            let view = document.querySelector("#Preview");
            let pastview = document.querySelector("#pastview");
            if(pastview != null){
                pastview.hidden = true;
            }
            view.hidden = false;
            console.dir(view);
            view.src = e.target.result;
        }
        reader.readAsDataURL(input.files[0]);
    }
}