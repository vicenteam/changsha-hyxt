/**
 * 会员基础信息管理初始化
 */
var Membermanagement = {
    id: "MembermanagementTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Membermanagement.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '病况', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '数量', field: 'isactive', visible: true, align: 'center', valign: 'middle'},

    ];
};

/**
 * 检查是否选中
 */
Membermanagement.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Membermanagement.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加会员基础信息
 */
Membermanagement.openMedicalHistory = function () {
    var index = layer.open({
        type: 2,
        title: '病史详情',
        area: ['400px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/membermanagement/membermanagement1_medicalHistory'
    });
    this.layerIndex = index;
};

/**
 * 打开查看会员基础信息详情
 */
Membermanagement.openMembermanagementDetail = function (id) {
        var index = layer.open({
            type: 2,
            title: '会员基础信息详情',
            area: ['800px', '800px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/membermanagement/membermanagement_update/' + id
        });
        this.layerIndex = index;
};
/**
 * 打开签到记录页面
 * @param id
 */
Membermanagement.opencheckHistory = function (id) {
        var index = layer.open({
            type: 2,
            title: '签到记录',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/membermanagement/membermanagementcheckHistory/' + id
        });
        this.layerIndex = index;
};
/**
 * 介绍人查询
 * @param id
 */
Membermanagement.openintroducer = function (id) {
        var index = layer.open({
            type: 2,
            title: '我推荐的人',
            area: ['850px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/membermanagement/openintroducer/' + id
        });
        this.layerIndex = index;
};

/**
 * 删除会员基础信息
 */
Membermanagement.delete = function (id) {
    layer.confirm('您确定要删除本条数据吗？', {btn: ['确定', '取消']}, function () {
        layer.closeAll('dialog');
        var ajax = new $ax(Feng.ctxPath + "/membermanagement/delete", function (data) {
            Feng.success("删除成功!");
            Membermanagement.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("membermanagementId",id);
        ajax.start();
    });
};

/**
 * 查询会员基础信息列表
 */
Membermanagement.search = function () {
    var queryData = {};
    queryData['name'] = $("#name").val();
    queryData['address'] = $("#address").val();
    queryData['fstatus'] = $("#fstatus").val();
    queryData['sex'] = $("#sex").val();
    queryData['idcard'] = $("#idcard").val();
    queryData['phone'] = $("#phone").val();
    queryData['stafff'] = $("#stafff").val();
    queryData['deptid'] = $("#deptid").val();
    queryData['province'] = $("#province").val();
    queryData['city'] = $("#city").val();
    queryData['year'] = $("#year").val();
    queryData['month'] = $("#month").val();
    queryData['day'] = $("#day").val();
    queryData['medicalHistory'] = $("#medicalHistory").val();
    queryData['memberid'] = "";
    Membermanagement.table.refresh({query: queryData});
};
Membermanagement.search1 = function () {
    var queryData1 = {};
    readDeviceCard();
//校验密码
    RfAuthenticationKey();
    var ret = CZx_32Ctrl.RfRead(DeviceHandle.value,BlockM1.value);
    if(CZx_32Ctrl.lErrorCode == 0){
        DevBeep();
        $.ajax({
            url: '/membermanagement/getUserInfo',
            // data: {value:ret},
            data: {value:$("#readDeviceCard").val()},
            type: 'POST',
            contentType: 'application/x-www-form-urlencoded;charset=utf-8',
            async: false,
            success: function (data) {
                if(data.id!=undefined){
                    queryData1['memberid'] = data.memberid;
                }else {
                    if(data=="202"){
                        Feng.error("该卡已挂失无法执行该操作!");
                    }else {
                        queryData1['memberid'] = -1;

                    }
                }

                Membermanagement.table.refresh({query: queryData1});
            }})
    }


};

$(function () {
    var defaultColunms = Membermanagement.initColumn();
    var table = new BSTable(Membermanagement.id, "/membermanagement/medicalHistoryPage", defaultColunms);
    //table.setPaginationType("client");
    table.setPaginationType("server");
    Membermanagement.table = table.init();
});

Membermanagement.exportExcel = function () {
    window.location.href=Feng.ctxPath +"/membermanagement/export_excel?" +
        "address="+ $("#address").val() +
        "&city="+ $("#city").val() +
        "&deptid="+ $("#deptid").val() +
        "&fstatus="+ $("#fstatus").val() +
        "&idcard="+ $("#idcard").val() +
        // "&memberid="+ $("#memberid").val() +
        // "&townshipid="+ $("#townshipid").val() +
        "&name="+ $("#name").val() +
        "&district="+ $("#district").val() +
        "&phone="+ $("#phone").val() +
        "&province="+ $("#province").val() +
        "&sex="+ $("#sex").val() +
        "&stafff="+ $("#stafff").val();
};

Membermanagement.importExcel = function () {
    var index = layer.open({
        type: 2,
        title: '导入会员客户资料',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/membermanagement/import_excel'
    });
    this.layerIndex = index;
};