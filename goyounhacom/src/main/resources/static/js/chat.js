document.addEventListener("DOMContentLoaded", function() {



    let  username = document.querySelector("#userinfo");
    console.dir(username);
    username = username.dataset.rol;

    let disconn = document.querySelector("#disconn");
    disconn.addEventListener("click", function(e){
        disconnect();
        location.href = "/";

    })


    let sendbtn = document.querySelector("#button-send");
    sendbtn.addEventListener("click", function(e){
        send();
    })

    const websocket = new WebSocket("ws://localhost:8090/ws/chat"); //웹소켓 객체 생성
    //var sockJs = new SockJS("/ws/chat", null, {transports: ["websocket", "xhr-streaming", "xhr-polling"]});


    websocket.onmessage = onMessage;
    websocket.onopen = onOpen;
    websocket.onclose = onClose;

    function disconnect(){
        onClose();


    }


    function send(){

        let msg = document.getElementById("msg");

        console.log(username + ":" + msg.value);
        websocket.send(username + ":" + msg.value);
        msg.value = '';
    }

    //채팅창에서 나갔을 때
    function onClose(evt) {
        var str = username + ": 님이 방을 나가셨습니다.";
        websocket.send(str);
    }

    //채팅창에 들어왔을 때
    function onOpen(evt) {
        var str = username + ": 님이 입장하셨습니다.";
        websocket.send(str);
    }

    function onMessage(msg) {
        var data = msg.data;
        var sessionId = null;
        //데이터를 보낸 사람
        var message = null;
        var arr = data.split(":");

        for(var i=0; i<arr.length; i++){
            console.log('arr[' + i + ']: ' + arr[i]);
        }

        var cur_session = username;

        //현재 세션에 로그인 한 사람
        console.log("cur_session : " + cur_session);
        sessionId = arr[0];
        message = arr[1];

        console.log("sessionID : " + sessionId);
        console.log("cur_session : " + cur_session);

        let msgarea = document.querySelector("#msgArea");
        //로그인 한 클라이언트와 타 클라이언트를 분류하기 위함
        if(sessionId == cur_session){
            let colnode = document.createElement("div");
            colnode.classList.add("col-6");
            let alertnode = document.createElement("div");
            alertnode.classList.add("alert", "alert-secondary");
            let messagenode = document.createElement("b");
            let messagetext = document.createTextNode(sessionId + " : " + message);
            messagenode.appendChild(messagetext);
            alertnode.appendChild(messagenode);
            colnode.appendChild(alertnode);

            msgarea.appendChild(colnode);
        }
        else{
            let colnode = document.createElement("div");
            colnode.classList.add("col-6");
            let alertnode = document.createElement("div");
            alertnode.classList.add("alert", "alert-danger");
            let messagenode = document.createElement("b");
            let messagetext = document.createTextNode(sessionId + " : " + message);
            messagenode.appendChild(messagetext);
            alertnode.appendChild(messagenode);
            colnode.appendChild(alertnode);

            msgarea.appendChild(colnode);
        }
    }
});
