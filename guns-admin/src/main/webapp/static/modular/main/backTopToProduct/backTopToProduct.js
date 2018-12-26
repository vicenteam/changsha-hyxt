/**
 * 退回商品记录管理初始化
 */
var BackTopToProduct = {
    id: "BackTopToProductTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
BackTopToProduct.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '退回商品名称', field: 'backTopToProductName', visible: true, align: 'center', valign: 'middle'},
        {title: '退回数量', field: 'backTopToNum', visible: true, align: 'center', valign: 'middle'},
        {title: '退回时间', field: 'backTopToCreateTime', visible: true, align: 'center', valign: 'middle'},

        // {title: '退回商品id', field: 'backTopToProductId', visible: true, align: 'center', valign: 'middle'},
        //     {title: '退回自己商品id', field: 'backTopToMyProductId', visible: true, align: 'center', valign: 'middle'},
        {title: '操作人', field: 'createUserId', visible: true, align: 'center', valign: 'middle'},
        // {title: '', field: 'deptId', visible: true, align: 'center', valign: 'middle'},

    ];
};

/**
 * 检查是否选中
 */
BackTopToProduct.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        BackTopToProduct.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加退回商品记录
 */
BackTopToProduct.openAddBackTopToProduct = function () {
    var index = layer.open({
        type: 2,
        title: '添加退回商品记录',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/backTopToProduct/backTopToProduct_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看退回商品记录详情
 */
BackTopToProduct.openBackTopToProductDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '退回商品记录详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/backTopToProduct/backTopToProduct_update/' + BackTopToProduct.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除退回商品记录
 */
BackTopToProduct.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/backTopToProduct/delete", function (data) {
            Feng.success("删除成功!");
            BackTopToProduct.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("backTopToProductId", this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询退回商品记录列表
 */
BackTopToProduct.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    BackTopToProduct.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = BackTopToProduct.initColumn();
    var table = new BSTable(BackTopToProduct.id, "/backTopToProduct/list", defaultColunms);
    //table.setPaginationType("client");
    table.setPaginationType("server");
    BackTopToProduct.table = table.init();
});
