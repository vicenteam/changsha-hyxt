/**
 * 商品类型维护管理初始化
 */
var Integralrecordtype = {
    id: "IntegralrecordtypeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Integralrecordtype.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '积分类型', field: 'names', visible: true, align: 'center',visible: false, valign: 'middle'},
        {title: '商品名称', field: 'productname', visible: true, align: 'center', valign: 'middle'},
        {
            title: '商品类别',
            field: 'producttype',
            visible: true,
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                    if(value==0){
                        return "礼品类";
                    }else if(value==1){
                        return "积分兑换类";
                    }
                    else if(value==2){
                        return "销售类";
                    }
                    else if(value==3){
                        return "积分+金额类";
                    }else {
                        return ""
                    }
            }
        },
        {title: '商品规格', field: 'productspecification', visible: true, align: 'center', valign: 'middle'},
        {title: '商品数量', field: 'productnum', visible: true, align: 'center', valign: 'middle'},
        {title: '商品结余', field: 'productbalance', visible: true, align: 'center', valign: 'middle'},
        {title: '商品积分', field: 'productjifen', visible: true, align: 'center', valign: 'middle'},
        {title: '食用剂量', field: 'producteatingdose', visible: true, align: 'center', valign: 'middle'},
        // {title: '', field: 'deptid', visible: true, align: 'center', valign: 'middle'},
        // {title: '', field: 'createtime', visible: true, align: 'center', valign: 'middle'},
        // {title: '', field: 'updatetime', visible: true, align: 'center', valign: 'middle'},
        // {title: '', field: 'createuserid', visible: true, align: 'center', valign: 'middle'},
        // {title: '', field: 'updateuserid', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Integralrecordtype.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Integralrecordtype.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加商品类型维护
 */
Integralrecordtype.openAddIntegralrecordtype = function () {
    var index = layer.open({
        type: 2,
        title: '添加商品类型维护',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/integralrecordtype/integralrecordtype_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看商品类型维护详情
 */
Integralrecordtype.openIntegralrecordtypeDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '商品类型维护详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/integralrecordtype/integralrecordtype_update/' + Integralrecordtype.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除商品类型维护
 */
Integralrecordtype.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/integralrecordtype/delete", function (data) {
            Feng.success("删除成功!");
            Integralrecordtype.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("integralrecordtypeId", this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询商品类型维护列表
 */
Integralrecordtype.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['producttype'] = $("#producttype").val();
    Integralrecordtype.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Integralrecordtype.initColumn();
    var table = new BSTable(Integralrecordtype.id, "/integralrecordtype/list", defaultColunms);
    //table.setPaginationType("client");
    table.setPaginationType("server");
    Integralrecordtype.table = table.init();
});
