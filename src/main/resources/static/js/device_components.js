
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

function init(){ //初始化执行

    var deviceTypes = new Array();
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
        h4.innerText="id:";
        h4.className="card-title";
        var p = document.createElement("p");
        p.className="card-category";
        p.innerText="name:"+deviceTypes[i];
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
        p2.appendChild(button);
    }

}