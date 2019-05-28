var allRule = [];
var allDevice = [];
function init() { //初始获取rule数据

    var token = getCookie("token");
    var tokenList = token.split("-");
    userId = tokenList[0];
    $.ajax({
        type: "post",
        url: "/admin/rule/findAll",
        dataType: 'json',
        data: {},
        success : function (data) {
            if(data){
                allRule = data.data;
                showAllRule();
            }
        },
        error : function (data) {
            layer.msg("规则获取失败",{time:2000});
        }
    });
    //获取此用户所有设备信息
    allDevice =  JSON.parse(sessionStorage.getItem('allDevice'));
    console.log(allDevice);
}

var table = document.getElementById("table");
var tbody = document.createElement("tbody");
table.appendChild(tbody);
function showAllRule() { //显示所有规则
    while(tbody.hasChildNodes()){
        tbody.removeChild(tbody.firstChild);
    }
    for(i = 0;i<allRule.length;i++){
        var tr = document.createElement("tr");
        tr.id = "tr"+i;
        tbody.appendChild(tr);
        var tds = [];
        for(var j = 0;j<8;j++){
            tds[j] = document.createElement("td");
            tr.appendChild(tds[j]);
        }
        tds[0].innerText = i+1;
        tds[1].innerText = allRule[i].ruleId;
        tds[2].innerText = allRule[i].op;
        var timestamp = allRule[i].date;
        var newDate = new Date();
        newDate.setTime(timestamp);

        tds[3].innerText = newDate.toLocaleString();
        tds[4].innerText = allRule[i].deviceId;
        tds[5].innerText = allRule[i].status;
        //
        // "                                            <td class=\"td-actions text-right\">\n" +
        // "                                                <div class=\"form-button-action\">\n" +
        // "                                                    <button type=\"button\" data-toggle=\"tooltip\" title=\"\" class=\"btn btn-link <btn-simple-primary\" data-original-title=\"Edit Task\">\n" +
        // "                                                        <i class=\"la la-edit\"></i>\n" +
        // "                                                    </button>\n" +
        // "                                                    <button type=\"button\" data-toggle=\"tooltip\" title=\"\" class=\"btn btn-link btn-simple-danger\" data-original-title=\"Remove\">\n" +
        // "                                                        <i class=\"la la-times\"></i>\n" +
        // "                                                    </button>\n" +
        // "                                                </div>\n" +
        // "                                            </td>";

        tds[6].setAttribute('class','td-actions text-right');
        var div = document.createElement("div");
        div.setAttribute('class','form-button-action');
        tds[6].appendChild(div);

        var updateButton = document.createElement("button");
        updateButton.type = 'button';
        updateButton.dataset.toggle = 'tooltip';
        updateButton.setAttribute('class','btn btn-link <btn-simple-primary');
        updateButton.setAttribute('data-original-title','Edit Task');
        var updatei = document.createElement("i");
        updatei.setAttribute('class','la la-edit');
        updateButton.appendChild(updatei);
        (function(i) {
            updateButton.onclick = function () {
                updateRule(i);
            };
        })(i);

        var deleteButton = document.createElement("button");
        deleteButton.type = 'button';
        deleteButton.dataset.toggle = 'tooltip';
        deleteButton.setAttribute('class','btn btn-link <btn-simple-danger');
        deleteButton.setAttribute('data-original-title','Remove');
        var deletei = document.createElement("i");
        deletei.setAttribute('class','la la-times');
        deleteButton.appendChild(deletei);
        (function(i) {
            deleteButton.onclick = function () {
                deleteRule(i);
            };
        })(i);
        div.appendChild(updateButton);
        div.appendChild(deleteButton);

        tds[7].setAttribute('class','td-actions text-right');
        var div1 = document.createElement("div");
        div1.setAttribute('class','form-button-action');
        tds[7].appendChild(div1);

        var uploadButton = document.createElement("button");
        uploadButton.type = 'button';
        uploadButton.dataset.toggle = 'tooltip';
        uploadButton.setAttribute('class','btn btn-link <btn-simple-success');
        uploadButton.setAttribute('data-original-title','Edit Task');
        var uploadi = document.createElement("i");
        uploadi.setAttribute('class','la la-edit');
        uploadButton.appendChild(uploadi);
        (function(i) {
            uploadButton.onclick = function () {
                uploadRule(i);
            };
        })(i);

        var downloadButton = document.createElement("button");
        downloadButton.type = 'button';
        downloadButton.dataset.toggle = 'tooltip';
        downloadButton.setAttribute('class','btn btn-link <btn-simple-danger');
        downloadButton.setAttribute('data-original-title','Remove');
        var downloadi = document.createElement("i");
        downloadi.setAttribute('class','la la-times');
        downloadButton.appendChild(downloadi);
        (function(i) {
            downloadButton.onclick = function () {
                dowloadRule(i);
            };
        })(i);


        div1.appendChild(uploadButton);
        div1.appendChild(downloadButton);
    }
}
function uploadRule(index) {//上传单个定时任务
    //TODO:此处可优化取消mysql查询
    var url = "/admin/rule/upload";
    var ruleId = allRule[index].ruleId;
    $.ajax({
        type: "post",
        url: url,
        dataType: 'json',
        data:{
            "ruleId": ruleId
        },
        success: function (data) {
            if(data.errorCode==0){
                layer.msg(data.data,{time:2000});
                allRule[index].status = "UP";
                showAllRule();
            }
        },
        error: function (data) {
            layer.msg("错误",{time:2000});
        }
    });
}
function dowloadRule(index) { //移除单个定时任务
    //TODO:此处可优化取消mysql查询
    var url = "/admin/rule/download";
    var ruleId = allRule[index].ruleId;
    $.ajax({
        type: "post",
        url: url,
        dataType: 'json',
        data:{
            "ruleId": ruleId
        },
        success: function (data) {
            if(data.errorCode==0){
                layer.msg("成功移除",{time:2000});
                allRule[index].status = "DOWN";
                showAllRule();
            }else {
                layer.msg("系统繁忙，请稍后",{time:2000});
            }
        },
        error: function (data) {
            layer.msg("系统繁忙，请稍后",{time:2000});
        }
    });
}



function deleteRule(index) {
    if(allRule[index].status=="UP"){
        layer.msg("请先移除此任务的上传状态");
        return;
    }
    $.ajax({
        url: "/admin/rule/deleteRuleByRuleId",
        type:"post",
        dataType: "json",
        data:{
            "ruleId": allRule[index].ruleId
        },
        success: function (data) {
            if(data.errorCode == '0'){
                layer.msg("删除成功",{time:2000});
                allRule.splice(index,1);
                showAllRule();
            }else {
                layer.msg("删除失败",{time:2000});
            }
        },
        error: function (data) {
            layer.msg("删除失败",{time:2000});
        }
    })
}
function updateRule(index) {
    if(allRule[index].status=="UP"){
        layer.msg("请先移除此任务的上传状态");
        return;
    }
    layer.open({
        type: 1,
        closeBtn: false,
        shift: 7,
        shadeClose: true,
        content: "<div style='width:350px;'><div style='width:320px;margin-left: 3%;' class='form-group has-feedback'><p>Device</p><input id='device' class='form-control' readonly='readonly'/></div>" +
            "<div style='width:320px;margin-left: 3%;' class='form-group has-feedback'><p>Time</p><input id='ruleTime'  type='datetime-local' step='1' name='ruleTime'/></div>"+
            "<div style='width:320px;margin-left: 3%;' class='form-group has-feedback'><p>OP</p><select id='ruleOp' class='form-control' name='ruleOp'><option value='ON'>ON</option><option value='OFF' selected>OFF</option></select></div>" +
            "<button style='margin-top:5%;'type='button' class='btn btn-block btn-success btn-lg' onclick='updateRuleCheck(\""+ index + "\")'>提交</button></div>"
    });
    document.getElementById("device").value = allRule[index].deviceId;

    document.getElementById("ruleOp").value = allRule[index].op;

}
function updateRuleCheck(index) { //更新规则
    if(allRule[index].status=="UP"){
        layer.msg("请先移除此任务的上传状态");
        return;
    }
    var op = $("#ruleOp option:selected").val();
    var date = $("#ruleTime").val();
    console.log(date);
    if(date==null||date==""){
        layer.msg("请输入时间");
        return;
    }
    $.ajax({
        type: "post",
        url: "/admin/rule/update",
        dataType: 'json',
        data:{
            "ruleId":allRule[index].ruleId,
            "userId": userId,
            "deviceId": allRule[index].deviceId,
            "op": op,
            "date":date
        },
        success: function (data) {
            if(data.errorCode==0){
                showAllRule();
            }else {
                layer.msg(data.data,{time:2000});
                return;
            }
        },
        error: function (data) {
            layer.msg("更新失败",{time:2000});
        }
    });
    layer.closeAll();
}
function addRule() { //添加规则
    layer.open({
        type: 1,
        closeBtn: false,
        shift: 7,
        shadeClose: true,
        content: "<div style='width:350px;'><div style='width:320px;margin-left: 3%;' class='form-group has-feedback'><p>Device</p><select id='device' class='form-control' name='deviceid'/></div>" +
            "<div style='width:320px;margin-left: 3%;' class='form-group has-feedback'><p>Time</p><input id='ruleTime'  type='datetime-local' step='1' name='ruleTime'/></div>"+
            "<div style='width:320px;margin-left: 3%;' class='form-group has-feedback'><p>OP</p><select id='ruleOp' class='form-control' name='ruleOp'><option value='ON'>ON</option><option value='OFF' selected>OFF</option></select></div>" +
            "<button style='margin-top:5%;'type='button' class='btn btn-block btn-success btn-lg' onclick='addRuleCheck()'>提交</button></div>"
    });
    var deviceSelect = document.getElementById('device');
    var options = [];
    for(var i = 0;i<allDevice.length;i++){
        options[i] = document.createElement('option');
        options[i].value = i;
        options[i].innerText = allDevice[i].id+'_'+allDevice[i].model+'_'+allDevice[i].name;
        deviceSelect.appendChild(options[i]);
    }

    // var form = layui.form;
    // form.render();
    // var tableSelect = layui.tableSelect;
    //
    // tableSelect.render({
    //     elem: '#device',
    //     checkedKey: 'DeviceId', //表格的唯一建值，非常重要，影响到选中状态 必填
    //     searchKey: 'name',	//搜索输入框的name值 默认keyword
    //     searchPlaceholder: 'name',	//搜索输入框的提示文字 默认关键词搜索
    //     table: {
    //         url: 'table.json',
    //         data: allDevice,
    //         cols: [[
    //             { type: 'checkbox' },
    //             { field: 'id', title: 'ID', width: 100 },
    //             { field: 'username', title: '姓名', width: 300 },
    //             { field: 'sex', title: '性别', width: 100 }
    //         ]]
    //     },
    //     done: function (elem, data) {
    //         var NEWJSON = [];
    //         layui.each(data.data, function (index, item) {
    //             NEWJSON.push(item.username)
    //         });
    //         elem.val(NEWJSON.join(","))
    //     }
    // })
}
function addRuleCheck() {
    var deviceindex = $("#device option:selected").val();
    var date = $("#ruleTime").val();
    var op = $("#ruleOp  option:selected").val();
    //TODO: 判断目前时间
    if(date==null||date==""){
        layer.msg("请输入时间");
        return;
    }
    var deviceId = allDevice[deviceindex].id;
    $.ajax({
        type: "post",
        url: "/admin/rule/create",
        dataType: 'json',
        data:{
            "userId": userId,
            "deviceId": deviceId,
            "op": op,
            "date":date
        },
        success: function (data) {
            if(data.errorCode==0){
                allRule.push(data.data);
                showAllRule();
            }else {
                layer.msg(data.data,{time:2000});
                return;
            }
        },
        error: function (data) {
            layer.msg("创建失败",{time:2000});
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