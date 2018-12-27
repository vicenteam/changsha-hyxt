/**
 * 增值服务管理初始化
 */
var MainServerConsumption = {
    id: "MainServerConsumptionTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MainServerConsumption.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '服务名称', field: 'serverName', visible: true, align: 'center', valign: 'middle'},
        {title: '服务介绍', field: 'serverContent', visible: true, align: 'center', valign: 'middle'},
        {title: '消耗积分', field: 'consumptionJiFen', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
        {title: '修改时间', field: 'updateTime', visible: true, align: 'center', valign: 'middle'},

    ];
};

/**
 * 检查是否选中
 */
MainServerConsumption.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        MainServerConsumption.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加增值服务
 */
MainServerConsumption.openAddMainServerConsumption = function () {
    var index = layer.open({
        type: 2,
        title: '添加增值服务',
        area: ['400px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/mainServerConsumption/mainServerConsumption_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看增值服务详情
 */
MainServerConsumption.openMainServerConsumptionDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '增值服务详情',
            area: ['400px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/mainServerConsumption/mainServerConsumption_update/' + MainServerConsumption.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除增值服务
 */
MainServerConsumption.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/mainServerConsumption/delete", function (data) {
            Feng.success("删除成功!");
            MainServerConsumption.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("mainServerConsumptionId", this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询增值服务列表
 */
MainServerConsumption.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    MainServerConsumption.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = MainServerConsumption.initColumn();
    var table = new BSTable(MainServerConsumption.id, "/mainServerConsumption/list", defaultColunms);
    //table.setPaginationType("client");
    table.setPaginationType("server");
    MainServerConsumption.table = table.init();
});
