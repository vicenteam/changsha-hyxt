/**
 * 用户考勤管理初始化
 */
var UserAttendance = {
    id: "UserAttendanceTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
UserAttendance.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'userId', visible: true, align: 'center', valign: 'middle'},
            {title: '签到年月', field: 'checkYearMonth', visible: true, align: 'center', valign: 'middle'},
            {title: '当天考勤时间1', field: 'checkTime1', visible: true, align: 'center', valign: 'middle'},
            {title: '当天考勤时间2', field: 'checkTime2', visible: true, align: 'center', valign: 'middle'},
            {title: '门店', field: 'deptId', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
UserAttendance.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        UserAttendance.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加用户考勤
 */
UserAttendance.openAddUserAttendance = function () {
    var index = layer.open({
        type: 2,
        title: '添加用户考勤',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/userAttendance/userAttendance_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看用户考勤详情
 */
UserAttendance.openUserAttendanceDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '用户考勤详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/userAttendance/userAttendance_update/' + UserAttendance.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除用户考勤
 */
UserAttendance.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/userAttendance/delete", function (data) {
            Feng.success("删除成功!");
            UserAttendance.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("userAttendanceId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询用户考勤列表
 */
UserAttendance.search = function () {
    var queryData = {};
    queryData['deptId'] = $("#deptId").val();
    queryData['name'] = $("#name").val();
    queryData['begindate'] = $("#begindate").val();
    queryData['enddate'] = $("#enddate").val();
    UserAttendance.table.refresh({query: queryData});
};

UserAttendance.init = function () {
    var queryData = {};
    queryData['deptId'] = $("#deptId").val();
    queryData['name'] = $("#name").val();
    queryData['begindate'] = $("#begindate").val();
    queryData['enddate'] = $("#enddate").val();
    return queryData;
};

$(function () {
    var defaultColunms = UserAttendance.initColumn();
    var table = new BSTable(UserAttendance.id, "/userAttendance/list", defaultColunms);
    //table.setPaginationType("client");
    table.setPaginationType("server");
    table.setQueryParams(UserAttendance.init())
    UserAttendance.table = table.init();
});
