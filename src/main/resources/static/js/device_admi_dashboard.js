
var allDecice = []; //所有设备

// function test() {
//     var table = document.getElementById("test");
//     var tbody = document.createElement("tbody");
//     table.appendChild(tbody);
//     var tr = document.createElement("tr");
//     tbody.appendChild(tr);
//     var tds = new Array();
//     for(var i = 0;i<6;i++){
//         tds[i] = document.createElement("td");
//         tds[i].innerText = i;
//         tr.appendChild(tds[i]);
//     }
// }

function init() { //初始化执行
    // var token = getCookie("token");
    // //alert(token);
    // var tokenList = token.split("-");
    admiFindAllDevice();

}

function admiFindAllDevice() { //管理员使用，获取所有信息
    $.ajax({
        url : "/admin/findAll",
        //data :{},
        type : "get",
        dataType:"json",  //数据格式设置为jsonp
        // jsonp:"callback",  //Jquery生成验证参数的名称
        // jsonpCallback:"success",
        // async: false,
        // //dataType: "jsonp",
        // xhrFields: {withCredentials: true},
        // crossDomain: true,
        success : function(data) {
            if (data){
                allDecice = data.data;
                showAllDevice(allDecice);
                // alert(data.code);
                // if (data.code == "200"){
                //     showAllDevice(data.value);
                //     return true;
                // }else {
                //     alert(data.errorMessage);
                //     return true;
                // }
            }
        },
        error : function(data){
            layer.msg(data.errorMessage)
        }
    });

}
function userFindDevice(id) {
    console.log(id);
    $.ajax({
        type: "get",
        url: "/admin/findByOpenId",
        dataType: 'json',
        data: {
            "id":id
        },
        success : function (data) {
            if (data){
                console.log(data.data);
                //layer.msg("查询成功",{time: 2000});
            }
        },
        error : function (data) {
            layer.msg("查询失败",{time: 2000});
        }

    });
}
function addDevice() { //添加设备
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
}
function addDeviceCheck(){//发送ajax 创建新的设备
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
        url: "/admin/insert",
        dataType: 'json',
        data: {
            "name":name,
            "model":model,
            "nickname":nickname,
            "status":status
        },
        success : function (data) {
            if (data){
                layer.msg("创建成功",{time: 2000});
                allDecice.push(data.data);
                showAllDevice(allDecice);
            }
        },
        error : function (data) {
            layer.msg("创建失败",{time: 2000});
        }

    });
    layer.closeAll();
}

function updateDevice(index) {
    layer.open({
        type: 1,
        closeBtn: false,
        shift: 7,
        shadeClose: true,
        content: "<div style='width:350px;'><div style='width:320px;margin-left: 3%;' class='form-group has-feedback'><p>MODEL</p><input  readonly='readonly' id='deviceModel' class='form-control' type='text' name='deviceModel'/></div>" +
            "<div style='width:320px;margin-left: 3%;' class='form-group has-feedback'><p>NAME</p><input id='deviceName' class='form-control' type='text' name='deviceName'/></div>"+
            "<div style='width:320px;margin-left: 3%;' class='form-group has-feedback'><p>NICKNAME</p><input id='deviceNickname' class='form-control' type='text' name='deviceNickname' /></div>"+
            "<div style='width:320px;margin-left: 3%;' class='form-group has-feedback'><p>OPENID</p><input id='deviceOpenid' class='form-control' type='text' name='deviceOpenid' /></div>"+
            "<div style='width:320px;margin-left: 3%;' class='form-group has-feedback'><p>STATUS</p><select id='deviceStatus' class='form-control' name='deviceStatus'><option value='ON'>ON</option><option value='OFF' selected>OFF</option></select></div>" +
            "<button style='margin-top:5%;' type='button' class='btn btn-block btn-success btn-lg' onclick='updateDeviceCheck(\""+ index + "\")'>提交</button></div>"
    });
    document.getElementById("deviceModel").value = allDecice[index].model;
    document.getElementById("deviceName").value = allDecice[index].name;
    document.getElementById("deviceNickname").value = allDecice[index].nickname;
    document.getElementById("deviceOpenid").value = allDecice[index].openId;
    document.getElementById("deviceStatus").value = allDecice[index].status;
}
function updateDeviceCheck(index){//发送ajax 更新设备
    var model = $("#deviceModel").val();
    var name = $("#deviceName").val();
    var nickname = $("#deviceNickname").val();
    var status = $("#deviceStatus option:selected").val();
    var openId = $("#deviceOpenid").val();
    var id = allDecice[index].id;
    if(!model||!name){
        layer.msg("空",{time: 2000});
        return;
    }
    $.ajax({
        type: "get",
        url: "/admin/update",
        dataType: 'json',
        data: {
            "id":id,
            "name":name,
            "model":model,
            "nickname":nickname,
            "status":status,
            "openId":openId
        },
        success : function (data) {
            if (data){
                layer.msg("更新成功",{time: 2000});
                allDecice[index].model = data.data.model;
                allDecice[index].name = data.data.name;
                allDecice[index].nickname = data.data.nickname;
                allDecice[index].status = data.data.status;
                allDecice[index].openId = data.data.openId;
                showAllDevice(allDecice);
            }
        },
        error : function (data) {
            layer.msg("更新失败",{time: 2000});
        }

    });
    layer.closeAll();
}

function deleteDevice(index) {
    var id = allDecice[index].id;
    $.ajax({
        type: "get",
        url: "/admin/deleteById",
        dataType: 'json',
        data: {
            "id":id
        },
        success : function (data) {
            if (data){
                layer.msg("删除成功",{time: 2000});
                allDecice.splice(index,1);
                showAllDevice(allDecice);
            }
        },
        error : function (data) {
            layer.msg("删除失败",{time: 2000});
        }

    });

}

var table = document.getElementById("test");
var tbody = document.createElement("tbody");
table.appendChild(tbody);
function showAllDevice(deviceinfo) {//显示所有设备

    while(tbody.hasChildNodes()){
        tbody.removeChild(tbody.firstChild);
    }

    for(var i = 0;i<deviceinfo.length;i++){
        var tr = document.createElement("tr");
        tr.id = "tr"+i;
        tbody.appendChild(tr);
        var tds = [];
        for(var j = 0;j<9;j++){
            tds[j] = document.createElement("td");
            tr.appendChild(tds[j]);
        }
        tds[0].innerText = i+1;
        tds[1].innerText = deviceinfo[i].openId=='-1'?"NO USER":deviceinfo[i].openId;
        // if(deviceinfo[i].openId=='-1'){
        //     tds[1].innerText = "NO USER";
        // }else tds[1].innerText = deviceinfo[i].openId;
        tds[2].innerText = deviceinfo[i].id;
        tds[3].innerText = deviceinfo[i].model;
        tds[4].innerText = deviceinfo[i].name;
        tds[5].innerText = deviceinfo[i].nickname;
        tds[6].innerText = deviceinfo[i].status;

        var updateButton = document.createElement("input");
        updateButton.value = "update";
        updateButton.type = "button";
        (function(i) {
            updateButton.onclick = function () {
                updateDevice(i);
            };
        })(i);
        var deleteButton = document.createElement("input");
        deleteButton.value = "delete";
        deleteButton.type = "button";
        (function(i) {
            deleteButton.onclick = function () {
                deleteDevice(i);
            };
        })(i);
        tds[7].appendChild(updateButton);
        tds[8].appendChild(deleteButton);

    }
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