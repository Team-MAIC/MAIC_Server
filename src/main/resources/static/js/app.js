var stompClient = null;

function setConnected(connected) {
	document.getElementById("connect").disabled = connected;
	document.getElementById("disconnect").disabled = !connected;
}

function connect() {
    var centerId = document.getElementById("centerId").value;
    var passage = document.getElementById("passage").value;
    var basketNum = document.getElementById("basketNum").value;
    var socket = new SockJS('/wss');

    stompClient = Stomp.over(socket);
    stompClient.debug = f => f;
    stompClient.connect({}, function (frame) {
        setConnected(true);
        stompClient.subscribe(`/sub/das/todos/${centerId}/${passage}/${basketNum}`, function (message) {
            var json = JSON.parse(message.body);

            setData(json.data)
        });
    });

}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }

    setConnected(false);
}

function weightChange() {
    if (document.getElementById("dasTodoId").value == "") {
        return
    }

    fetch("/das/todos/baskets/refresh", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            "dasTodoId": document.getElementById("dasTodoId").value, 
            "centerId": document.getElementById("centerId").value,
            "passage": document.getElementById("passage").value,
            "roundId": document.getElementById("roundId").value, 
            "basketNum": document.getElementById("basketNum").value, 
            "productId": document.getElementById("productId").value,
            "productName": document.getElementById("productName").value,
            "basketWeight": document.getElementById("basketWeight").value, 
            "currentBasketWeight": document.getElementById("currentBasketWeight").value,
            "productAmount": document.getElementById("productAmount").value,
            "productWeight": document.getElementById("productWeight").value,
            "status": document.getElementById("status").value,
            "color": document.getElementById("color").value
        }),
      }).then((response) => response.json())
        .then((json) => setData(json.data));
}

function submit() {
    if (document.getElementById("dasTodoId").value == "") {
        return
    }

    fetch("/das/todos/baskets", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            "dasTodoId": document.getElementById("dasTodoId").value,
            "centerId": document.getElementById("centerId").value,
            "passage": document.getElementById("passage").value,
            "roundId": document.getElementById("roundId").value,
            "basketNum": document.getElementById("basketNum").value,
            "productId": document.getElementById("productId").value,
            "productName": document.getElementById("productName").value,
            "basketWeight": document.getElementById("basketWeight").value,
            "currentBasketWeight": document.getElementById("currentBasketWeight").value,
            "productAmount": document.getElementById("productAmount").value,
            "productWeight": document.getElementById("productWeight").value,
            "status": document.getElementById("status").value,
            "color": document.getElementById("color").value
        }),
    }).then((response) => response.json())
        .then((json) => setData(json.data));
}

function setData(data) {
    if (data == null) {
        document.getElementById("dasTodoId").value = ""
        document.getElementById("roundId").value = ""
        document.getElementById("productId").value = ""
        document.getElementById("productName").value = ""
        document.getElementById("basketWeight").value = ""
        document.getElementById("productAmount").value = ""
        document.getElementById("productWeight").value = ""
        document.getElementById("status").value = ""
        document.getElementById("color").value = ""
        document.getElementById('send').style.backgroundColor = "";
    } else {
        document.getElementById("dasTodoId").value = data.dasTodoId
        document.getElementById("roundId").value = data.roundId
        document.getElementById("productId").value = data.productId
        document.getElementById("productName").value = data.productName
        document.getElementById("basketWeight").value = data.basketWeight
        document.getElementById("productAmount").value = data.productAmount
        document.getElementById("productWeight").value = data.productWeight
        document.getElementById("status").value = data.status
        document.getElementById("color").value = data.color

        if (data.status == "WRONG") {
            document.getElementById('submit').style.backgroundColor = "BLACK";
        } else {
            document.getElementById('submit').style.backgroundColor = data.color;
        }
    }
}
