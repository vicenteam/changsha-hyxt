/**
 * 复签记录管理初始化
 */
var QiandaoCheckin = {
    id: "QiandaoCheckinTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
QiandaoCheckin.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '会员id', field: 'memberid', visible: true, align: 'center', valign: 'middle'},
            {title: '签到时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'},
            {title: '复签时间', field: 'updatetime', visible: true, align: 'center', valign: 'middle'},
            {title: '签到场次id', field: 'checkinid', visible: true, align: 'center', valign: 'middle'},
            {title: '门店id', field: 'deptid', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'status', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
QiandaoCheckin.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        QiandaoCheckin.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加复签记录
 */
QiandaoCheckin.openAddQiandaoCheckin = function () {
    var index = layer.open({
        type: 2,
        title: '添加复签记录',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/qiandaoCheckin/qiandaoCheckin_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看复签记录详情
 */
QiandaoCheckin.openQiandaoCheckinDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '复签记录详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/qiandaoCheckin/qiandaoCheckin_update/' + QiandaoCheckin.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除复签记录
 */
QiandaoCheckin.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/qiandaoCheckin/delete", function (data) {
            Feng.success("删除成功!");
            QiandaoCheckin.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("qiandaoCheckinId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询复签记录列表
 */
QiandaoCheckin.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    QiandaoCheckin.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = QiandaoCheckin.initColumn();
    var table = new BSTable(QiandaoCheckin.id, "/qiandaoCheckin/list", defaultColunms);
    //table.setPaginationType("client");
    table.setPaginationType("server");
    QiandaoCheckin.table = table.init();
});
