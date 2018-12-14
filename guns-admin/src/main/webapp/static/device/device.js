


    function OpenDevice()//打开设备
    {
        var st = CZx_32Ctrl.HBOpen();
        if((st == 0 || st < 0) && CZx_32Ctrl.lErrorCode != 0)
        {
            Version.value = "";
            Serial.value = "";
            DeviceHandle.value = "-1";
            // alert("打开设备失败");
        }
        else
        {
            DeviceHandle.value = st;
            var sver = CZx_32Ctrl.GetVersion(DeviceHandle.value);
            if(CZx_32Ctrl.lErrorCode == 0)
                Version.value = sver;
            else
                Version.value = "";

            var snr = CZx_32Ctrl.DevReadsnr(DeviceHandle.value,20);
            if(CZx_32Ctrl.lErrorCode == 0)
                Serial.value = snr;
            else
                Serial.value = "";
            console.log("打开设备成功");
        }
    }

function CloseDevice()//关闭设备
{
    var ret = CZx_32Ctrl.HBClose();
    Version.value = "";
    Serial.value = "";
    DeviceHandle.value = "-1";
    if(ret == 0)
        console.log("关闭设备成功");
    else
        console.log("关闭设备失败，错误码为：" + ret);
}

function DevBeep()//蜂鸣器蜂鸣
{
    var ret = CZx_32Ctrl.DevBeep(DeviceHandle.value,BeepTime.value,BeepSpaceTime.value,BeepNum.value);
    if(ret == 0)
        alert("设备操作成功");
    else
        alert("设备操作失败，错误码为：" + CZx_32Ctrl.lErrorCode);
}

//----------------------------------------------------------					工具函数				-----------------------------------------------//

//asc_hex
function AscHex()
{
    var ret = CZx_32Ctrl.AscHex(WriteDataASCHEX.value,WriteDataASCHEX.value.length/2);
    if(CZx_32Ctrl.lErrorCode == 0)
    {
        ReadDataASCHEX.value = ret;
        alert("ASC码转HEX码成功");
    }
    else
    {
        ReadDataASCHEX.value = "";
        alert("ASC码转HEX码失败，错误码为：" + CZx_32Ctrl.lErrorCode);
    }
}

//hex_asc
function HexAsc()
{
    var ret = CZx_32Ctrl.HexAsc(WriteDataASCHEX.value,WriteDataASCHEX.value.length);
    if(CZx_32Ctrl.lErrorCode == 0)
    {
        ReadDataASCHEX.value = ret;
        alert("HEX码转ASC码成功");
    }
    else
    {
        ReadDataASCHEX.value = "";
        alert("HEX码转ASC码失败，错误码为：" + CZx_32Ctrl.lErrorCode);
    }
}

//----------------------------------------------------------				eeprom操作				------------------------------------------------//
// 说明： 读设备备注区（总长200字节）
// 调用：icdev: 通讯设备标识符
// offset: 备注区偏移地址0～199
// len: 读出信息长度1～200
// 	 *data_buffer: 存放读出的备注信息
// 	 返回：<0 错误
// 	 =0 正确
function DevReadeeprom()
{
    var ret = CZx_32Ctrl.DevReadeeprom(DeviceHandle.value,AddressEeprom.value,LengthEeprom.value);
    if(CZx_32Ctrl.lErrorCode == 0)
    {
        ReadDataEeprom.value = ret;
        alert("读设备Eeprom成功");
    }
    else
    {
        ReadDataEeprom.value = "";
        alert("读设备Eeprom失败，错误码为：" + CZx_32Ctrl.lErrorCode);
    }
}


// 说明： 写设备备注区（总长200字节）
// 调用： icdev: 通讯设备标识符
// offset: 备注区偏移地址0～199
// len: 写入信息长度1～200
// 	 *data_buffer: 写入信息
// 	 返回：<0 错误
// 	 =0 正确
//写入的是ASC字符，如果要写16进制数据可以使用AscHex转换函数转换之后再写入，长度为16进制数据长度
function DevWriteeeprom()
{
    var ret = CZx_32Ctrl.DevWriteeeprom(DeviceHandle.value,AddressEeprom.value,LengthEeprom.value,WriteDataEeprom.value);
    if(ret == 0)
        alert("写设备Eeprom成功");
    else
        alert("写设备Eeprom失败，错误码为：" + CZx_32Ctrl.lErrorCode);
}


//----------------------------------------------			M1卡操作			-----------------------------------------------//
function RfCard()
{
    var ret = CZx_32Ctrl.RfCard(DeviceHandle.value,0);
    if(CZx_32Ctrl.lErrorCode == 0)
    {
        //UidM1.value = CZx_32Ctrl.HexAsc(ret,4);
        UidM1.value = ret;
        alert("寻卡成功");
    }
    else
    {
        UidM1.value = "";
        alert("寻卡失败，错误码为：" + CZx_32Ctrl.lErrorCode);
    }
}

function RfAuthenticationKey()
{
    var keytype = document.getElementById('keytypem1').value;	//获取下拉框当前选值
    var ret = CZx_32Ctrl.RfAuthenticationKey(DeviceHandle.value,keytype,BlockM1.value,PwdM1.value);
    if(CZx_32Ctrl.lErrorCode == 0)
    {
        // alert("校验密码成功");
        // DevBeep();
    }
    else
    {
        alert("校验密码失败，错误码为：" + CZx_32Ctrl.lErrorCode);
    }
}

function RfRead()
{
    var ret = CZx_32Ctrl.RfRead(DeviceHandle.value,BlockM1.value);
    if(CZx_32Ctrl.lErrorCode == 0)
    {
        ReadDataM1.value = ret;//CZx_32Ctrl.HexAsc(ret,17)
        alert("读数据成功");
    }
    else
    {
        ReadDataM1.value = "";
        alert("读数据失败，错误码为：" + CZx_32Ctrl.lErrorCode);
    }
}

function RfWrite()
{
    var ret = CZx_32Ctrl.RfWrite(DeviceHandle.value,BlockM1.value,WriteDataM1.value);
    if(ret == 0)
    {
        alert("写数据成功");
    }
    else
    {
        alert("写数据失败，错误码为：" + CZx_32Ctrl.lErrorCode);
    }
}

function RfInitval()
{
    var ret = CZx_32Ctrl.RfInitval(DeviceHandle.value,BlockM1.value,NewValueM1.value);
    if(ret == 0)
    {
        alert("初始化值成功");
    }
    else
    {
        alert("初始化值失败，错误码为：" + CZx_32Ctrl.lErrorCode);
    }
}

function RfReadval()
{
    var ret = CZx_32Ctrl.RfReadval(DeviceHandle.value,BlockM1.value);
    if(CZx_32Ctrl.lErrorCode == 0)
    {
        NowValueM1.value = ret;
        alert("读值成功");
    }
    else
    {
        NowValueM1.value = "";
        alert("读值失败，错误码为：" + CZx_32Ctrl.lErrorCode);
    }
}

function RfIncrement()
{
    var ret = CZx_32Ctrl.RfIncrement(DeviceHandle.value,BlockM1.value,NewValueM1.value);
    if(ret == 0)
        alert("加值成功");
    else
        alert("加值失败，错误码为：" + CZx_32Ctrl.lErrorCode);
}

function RfDecrement()
{
    var ret = CZx_32Ctrl.RfDecrement(DeviceHandle.value,BlockM1.value,NewValueM1.value);
    if(ret == 0)
        alert("减值成功");
    else
        alert("减值失败，错误码为：" + CZx_32Ctrl.lErrorCode);
}

function RfTransfer()
{
    var ret = CZx_32Ctrl.RfTransfer(DeviceHandle.value,BlockM1.value);
    if(ret == 0)
        alert("传送成功");
    else
        alert("传送失败，错误码为：" + CZx_32Ctrl.lErrorCode);
}

function RfRestore()
{
    var ret = CZx_32Ctrl.RfRestore(DeviceHandle.value,BlockM1.value);
    if(ret == 0)
        alert("回传成功");
    else
        alert("回传失败，错误码为：" + CZx_32Ctrl.lErrorCode);
}

function RfTerminal()
{
    var ret = CZx_32Ctrl.RfTerminal(DeviceHandle.value);
    if(ret == 0)
        alert("中止卡片成功");
    else
        alert("中止卡片失败，错误码为：" + CZx_32Ctrl.lErrorCode);
}

//----------------------------------------------			非接触式CPU卡				-----------------------------------------//
//根据COS指令下拉框选择来改变写入数据内容
function discpumycos()
{
    var ncos = document.getElementById('cosdiscpu').value;	//获取下拉框当前选值
    if(ncos == 0)
        WriteDataDiscpu.value = "0084000008";
    else if(ncos == 1)
        WriteDataDiscpu.value = "00a40000023f00";
}

function OpenCard5()//激活卡片
{
    var ret = CZx_32Ctrl.OpenCard5(DeviceHandle.value,0);
    if(CZx_32Ctrl.lErrorCode == 0)
    {
        ReadDataDiscpu.value = ret;
        alert("激活卡片成功");
    }
    else
    {
        ReadDataDiscpu.value = "";
        alert("激活卡片失败，错误码为：" + CZx_32Ctrl.lErrorCode);
    }
}

function ExchangePro5()//发送命令
{
    var ret = CZx_32Ctrl.ExchangeProHex(DeviceHandle.value,WriteDataDiscpu.value);
    if(CZx_32Ctrl.lErrorCode == 0)
    {
        ReadDataDiscpu.value = ret;
        alert("发送命令成功");
    }
    else
    {
        ReadDataDiscpu.value = "";
        alert("发送命令失败，错误码为：" + CZx_32Ctrl.lErrorCode);
    }
}



CloseDevice();
//打开设备
OpenDevice();

//读取卡片UID
function readDeviceCard() {
    var ret = CZx_32Ctrl.RfCard(DeviceHandle.value,0);
    if(CZx_32Ctrl.lErrorCode == 0)
    {
        //UidM1.value = CZx_32Ctrl.HexAsc(ret,4);
        document.getElementById("readDeviceCard").value = ret;

    }
    else if(CZx_32Ctrl.lErrorCode == -22)
    {
        document.getElementById("readDeviceCard").value = "";
        readDeviceCard();
        // alert("读取卡片UID，错误码为：" + CZx_32Ctrl.lErrorCode);
    }else {
        OpenDevice();
    }
    return ret;

}
    function readDeviceCard2() {
        var ret = CZx_32Ctrl.RfCard(DeviceHandle.value,0);
        if(CZx_32Ctrl.lErrorCode == 0)
        {
            //UidM1.value = CZx_32Ctrl.HexAsc(ret,4);
            $("#otherCardUUID").val(ret)
        }
        else if(CZx_32Ctrl.lErrorCode == -22)
        {
            readDeviceCard2();
            // alert("读取卡片UID，错误码为：" + CZx_32Ctrl.lErrorCode);
        }else {
            OpenDevice();
        }
        return ret;

    }
    function readDeviceCard3() {
        var ret = CZx_32Ctrl.RfCard(DeviceHandle.value,0);
        if(CZx_32Ctrl.lErrorCode == 0)
        {
            //UidM1.value = CZx_32Ctrl.HexAsc(ret,4);
            $("#otherCardUUID2").val(ret)
        }
        else if(CZx_32Ctrl.lErrorCode == -22)
        {
            readDeviceCard3();
            // alert("读取卡片UID，错误码为：" + CZx_32Ctrl.lErrorCode);
        }else {
            OpenDevice();
        }
        return ret;

    }
//操作卡片数据
function readData() {
    readDeviceCard();
    //校验密码
    RfAuthenticationKey();
    var ret = CZx_32Ctrl.RfRead(DeviceHandle.value,BlockM1.value);
    if(CZx_32Ctrl.lErrorCode == 0)
    {
        document.getElementById("readData").value = ret;//CZx_32Ctrl.HexAsc(ret,17)
        // alert("读数据成功");
        DevBeep();
    }
    else
    {
        document.getElementById("readData").value = "";
        alert("读数据失败，错误码为：" + CZx_32Ctrl.lErrorCode);
    }
}
    function readData2() {
       var uuid= readDeviceCard2();
        //校验密码
        RfAuthenticationKey();
        var ret = CZx_32Ctrl.RfRead(DeviceHandle.value,BlockM1.value);
        if(CZx_32Ctrl.lErrorCode == 0)
        {
            document.getElementById("readData").value = ret;//CZx_32Ctrl.HexAsc(ret,17)
            // alert("读数据成功");
            DevBeep();
        }
        else
        {
            document.getElementById("readData").value = "";
            alert("读数据失败，错误码为：" + CZx_32Ctrl.lErrorCode);
        }
        return uuid;
    }
    function readData3() {
        var uuid= readDeviceCard3();
        //校验密码
        RfAuthenticationKey();
        var ret = CZx_32Ctrl.RfRead(DeviceHandle.value,BlockM1.value);
        if(CZx_32Ctrl.lErrorCode == 0)
        {
            document.getElementById("readData").value = ret;//CZx_32Ctrl.HexAsc(ret,17)
            // alert("读数据成功");
            DevBeep();
        }
        else
        {
            document.getElementById("readData").value = "";
            alert("读数据失败，错误码为：" + CZx_32Ctrl.lErrorCode);
        }
        return uuid;
    }
function writeData() {
    readDeviceCard();
    //校验密码
    RfAuthenticationKey();
    var ret = CZx_32Ctrl.RfWrite(DeviceHandle.value,BlockM1.value,document.getElementById("writeData").value);
    if(ret == 0)
    {
        // alert("写数据成功");
        DevBeep();
    }
    else
    {
        alert("写数据失败，错误码为：" + CZx_32Ctrl.lErrorCode);
    }
}



/**
 * 从服务端获取写卡数据
 */
function getXieKaVal() {
    readDeviceCard();
   var carduuid= document.getElementById("readDeviceCard").value;
   if(carduuid.length==0){
       Feng.error("请重试!");
       return;
   }
//校验密码
    RfAuthenticationKey();
    var ret = CZx_32Ctrl.RfRead(DeviceHandle.value,BlockM1.value);
    if(CZx_32Ctrl.lErrorCode == 0)
    {
        if(ret.length>0){
            $.ajax({
                url: '/membermanagement/getUserInfo',
                // data: {value:ret},
                data: {value:carduuid},
                type: 'POST',
                contentType: 'application/x-www-form-urlencoded;charset=utf-8',
                async: false,
                success: function (data) {
                    if(data.id!=undefined){
                        Feng.error("该卡已经绑定用户请换张卡试试!");
                        return
                    }else {
                        if(data=="202"){
                            Feng.error("该卡已挂失无法执行该操作!");
                        }else {
                            var ajax = new $ax(Feng.ctxPath + "/membermanagement/getXieKaVal", function (data) {
                                $("#writeData").val(data)
                                $("#cardCode").val(data)
                                //执行写数据
                                writeData();
                            }, function (data) {
                                Feng.error("获取数据失败!" + data.responseJSON.message + "!");
                            });
                            ajax.set("code",carduuid);
                            ajax.start();
                        }

                    }
                }
            });
        }
    }
}

/**
 * 补卡获取卡片uuid
 */
function getBuKaUUID() {
    readDeviceCard();
    var carduuid= document.getElementById("readDeviceCard").value;
    if(carduuid.length==0){
        Feng.error("请重试!");
        return;
    }
//校验密码
    RfAuthenticationKey();
    var ret = CZx_32Ctrl.RfRead(DeviceHandle.value,BlockM1.value);
    if(CZx_32Ctrl.lErrorCode == 0)
    {
        if(ret.length>0){
            $.ajax({
                url: '/membermanagement/getUserInfo',
                // data: {value:ret},
                data: {value:carduuid},
                type: 'POST',
                contentType: 'application/x-www-form-urlencoded;charset=utf-8',
                async: false,
                success: function (data) {
                    if(data.id!=undefined){
                        Feng.error("该卡以及绑定用户请换张卡试试!");
                        return
                    }else {
                        if(data=="202"){
                            Feng.error("该卡已挂失无法执行该操作!");
                        }else {
                            //绑定uuid
                            $("#cardID").val(carduuid)
                        }

                    }
                }
            });
        }
    }
}

    /**
 * 根据卡片数据获取用户信息
 */
function getUserInfo() {
        readData();
    var ajax = new $ax(Feng.ctxPath + "/membermanagement/getUserInfo", function (data) {
        $("#introducerId").val(data.memberid);
        $("#introducerName").val(data.name);
    }, function (data) {
        Feng.error("获取数据失败!" + data.responseJSON.message + "!");
    });
    // ajax.setData({value:$("#readData").val()})
    ajax.setData({value:$("#readDeviceCard").val()})
    ajax.start();
}
function getUserInfo2() {
    var uuid=readData2();
    var ajax = new $ax(Feng.ctxPath + "/membermanagement/getUserInfo", function (data) {
        $("#introducerId").val(data.memberid);
        $("#introducerName").val(data.name);
    }, function (data) {
        Feng.error("获取数据失败!" + data.responseJSON.message + "!");
    });
    // ajax.setData({value:$("#readData").val()})
    ajax.setData({value:$("#otherCardUUID").val()})
    ajax.start();
}
function getUserInfo3() {
    readData3();
    var ajax = new $ax(Feng.ctxPath + "/membermanagement/getUserInfo", function (data) {
        $("#otherMemberId").val(data.memberid); //关联会员id
        $("#anotherName").val(data.name);
    }, function (data) {
        Feng.error("获取数据失败!" + data.responseJSON.message + "!");
    });
    // ajax.setData({value:$("#readData").val()})
    ajax.setData({value:$("#otherCardUUID2").val()})
    ajax.start();
}
