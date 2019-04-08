/**
 * 识别源数据管理初始化
 */
var UserAttendanceSource = {
    id: "UserAttendanceSourceTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
UserAttendanceSource.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '考勤人员名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '考勤识别图', field: 'img', visible: true, align: 'center', valign: 'middle',formatter: function (value, row, index) {
                return "<img src='/kaptcha/"+value+"' style='width: 60px;height: 60px;border-radius:100px;'>"
                }},
            {title: '性别', field: 'sex', visible: true, align: 'center', valign: 'middle',formatter: function (value, row, index) {
                    if(value==1){
                        return '男';
                    }else {
                        return '女';
                    }
                }},
            {title: '电话', field: 'phone', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createdt', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
UserAttendanceSource.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        UserAttendanceSource.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加识别源数据
 */
UserAttendanceSource.openAddUserAttendanceSource = function () {
    var index = layer.open({
        type: 2,
        title: '添加识别源数据',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/userAttendanceSource/userAttendanceSource_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看识别源数据详情
 */
UserAttendanceSource.openUserAttendanceSourceDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '识别源数据详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/userAttendanceSource/userAttendanceSource_update/' + UserAttendanceSource.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除识别源数据
 */
UserAttendanceSource.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/userAttendanceSource/delete", function (data) {
            Feng.success("删除成功!");
            UserAttendanceSource.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("userAttendanceSourceId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询识别源数据列表
 */
UserAttendanceSource.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    UserAttendanceSource.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = UserAttendanceSource.initColumn();
    var table = new BSTable(UserAttendanceSource.id, "/userAttendanceSource/list", defaultColunms);
    //table.setPaginationType("client");
    table.setPaginationType("server");
    UserAttendanceSource.table = table.init();
});
