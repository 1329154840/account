
var deviceType={
    airCondition : "空调",
    light : "电灯",
    door : "门",
    waterHeater : "热水器",
    humidifier : "加湿器",
    curtain : "窗帘"
};
//
// $(document).ready(function(){ //初始化执行
//     alert("dwada");
//     var div1 = document.getElementById("addHere");
//     var div2 = document.createElement("div");
//     div2.addClass("col-md-12");
//     div1.appendChild(div2);
//     var div3 = document.createElement("div");
//     div3.addClass("card");
//     div2.appendChild(div3);
//     var div4 = document.createElement("div");
//     div4.addClass("card-header");
//     div3.appendChild(div4);
//     var h4 = document.createElement("h4");
//     h4.innerText="id:";
//     var p = document.createElement("p");
//     p.innerText="name:";
//     div4.appendChild(h4);
//     div4.appendChild(p);
// });
//
var deviceTypes = [];
function init(){ //初始化执行
    deviceTypes.push(deviceType.airCondition);
    deviceTypes.push(deviceType.light);
    deviceTypes.push(deviceType.door);
    deviceTypes.push(deviceType.waterHeater);
    deviceTypes.push(deviceType.humidifier);
    deviceTypes.push(deviceType.curtain);
    for(var i in deviceTypes){
        var div1 = document.getElementById("addHere");
        var div2 = document.createElement("div");
        div2.className="col-md-12";
        div1.appendChild(div2);
        var div3 = document.createElement("div");
        div3.className="card";
        //div3.style.color="card";
        div2.appendChild(div3);
        var div4 = document.createElement("div");
        div4.className="card-header";
        div3.appendChild(div4);
        var h4 = document.createElement("h4");
        h4.innerText="MODEL:"+deviceTypes[i];
        h4.className="card-title";
        var p = document.createElement("p");
        p.className="card-category";
        p.innerText="version:"+"1.0";
        div4.appendChild(h4);
        div4.appendChild(p);
        var div5 = document.createElement("div");
        div5.className="card-body";
        div3.appendChild(div5);
        var p2 = document.createElement("p");
        p2.className="demo";
        div5.appendChild(p2);
        var button = document.createElement("button");
        button.className="btn btn-success";
        button.innerText="添加";
        (function(i) {
            button.onclick = function () {
                addDevice(i);
            };
        })(i);
        p2.appendChild(button);
    }

}
function addDevice(index) { //添加设备
    layer.open({
        type: 1,
        closeBtn: false,
        shift: 7,
        shadeClose: true,
        content: "<div style='width:350px;'><div style='width:320px;margin-left: 3%;' class='form-group has-feedback'><p>MODEL</p><input id='deviceModel' class='form-control' type='text' name='deviceModel'/></div>" +
            "<div style='width:320px;margin-left: 3%;' class='form-group has-feedback'><p>NAME</p><input id='deviceName' class='form-control' type='text' name='deviceName'/></div>"+
            "<div style='width:320px;margin-left: 3%;' class='form-group has-feedback'><p>NICKNAME</p><input id='deviceNickname' class='form-control' type='text' name='deviceNickname' /></div>"+
            "<div style='width:320px;margin-left: 3%;' class='form-group has-feedback'><p>STATUS</p><select id='deviceStatus' class='form-control' name='deviceStatus'><option value='ON'>ON</option><option value='OFF' selected>OFF</option></select></div>" +
            "<button style='margin-top:5%;' type='button' class='btn btn-block btn-success btn-lg' onclick='addDeviceCheck()'>提交</button></div>"
    });
    document.getElementById("deviceModel").value = deviceTypes[index];
    document.getElementById("deviceModel").disabled = 'disabled';
}


function addDeviceCheck(){//发送ajax 创建新的设备
    var token = getCookie("token");
// //alert(token);
    var tokenList = token.split("-");
    var openId = tokenList[0];
    console.log(token+openId);
    var model = $("#deviceModel").val();
    var name = $("#deviceName").val();
    var nickname = $("#deviceNickname").val();
    var status = $("#deviceStatus option:selected").val();
    if(!model||!name){
        layer.msg("空",{time: 1000});
        return;
    }
    $.ajax({
        type: "get",
        url: "/user/insert",
        dataType: 'json',
        data: {
            "name":name,
            "model":model,
            "nickname":nickname,
            "status":status,
            "openId":openId
        },
        success : function (data) {
            if (data){
                layer.msg("创建成功",{time: 2000});
                // allDecice.push(data.data);
                // showAllDevice(allDecice);
            }
        },
        error : function (data) {
            layer.msg("创建失败",{time: 2000});
        }

    });
    layer.closeAll();
}


function getCookie(name) {
    var cookies = document.cookie;
    if(!cookies){
        alert("无cookie");
    }
    var list = cookies.split("; ");          // 解析出名/值对列表

    for(var i = 0; i < list.length; i++) {
        var arr = list[i].split("=");          // 解析出名和值
        if(arr[0] == name)
            return arr[1];   // 对cookie值解码
    }
    return "";
}