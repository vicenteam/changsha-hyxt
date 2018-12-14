/**
 * 新增积分管理初始化
 */
var Integralrecord = {
    id: "IntegralrecordTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Integralrecord.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '积分记录编号', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '获得积分的值', field: 'integral', visible: true, align: 'center', valign: 'middle'},
            {title: '附加参数，如果是本人或新人购物获得积分，则该列的值是该购物记录的ID，如果是带新人或新人签到获得积分，则是所带新人的ID', field: 'target', visible: true, align: 'center', valign: 'middle'},
            {title: '积分类型(0购买新增 1推荐新人 2兑换)', field: 'type', visible: true, align: 'center', valign: 'middle'},
            {title: '会员id', field: 'memberid', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Integralrecord.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Integralrecord.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加新增积分
 */
Integralrecord.openAddIntegralrecord = function () {
    var index = layer.open({
        type: 2,
        title: '添加新增积分',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/integralrecord/integralrecord_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看新增积分详情
 */
Integralrecord.openIntegralrecordDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '新增积分详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/integralrecord/integralrecord_update/' + Integralrecord.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除新增积分
 */
Integralrecord.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/integralrecord/delete", function (data) {
            Feng.success("删除成功!");
            Integralrecord.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("integralrecordId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询新增积分列表
 */
Integralrecord.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Integralrecord.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Integralrecord.initColumn();
    var table = new BSTable(Integralrecord.id, "/integralrecord/list", defaultColunms);
    //table.setPaginationType("client");
    table.setPaginationType("server");
    Integralrecord.table = table.init();
});
