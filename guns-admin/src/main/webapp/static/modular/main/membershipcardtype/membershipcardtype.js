/**
 * 会员配置管理初始化
 */
var Membershipcardtype = {
    id: "MembershipcardtypeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Membershipcardtype.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '会员等级名称', field: 'cardname', visible: true, align: 'center', valign: 'middle'},
            // {title: '签到积分', field: 'signin', visible: true, align: 'center', valign: 'middle'},
            // {title: '购物积分', field: 'shopping', visible: true, align: 'center', valign: 'middle'},
            // {title: '带新人奖励积分', field: 'newpoints', visible: true, align: 'center', valign: 'middle'},
            // {title: '新人签到积分', field: 'signinnew', visible: true, align: 'center', valign: 'middle'},
            // {title: '新人购物积分', field: 'shoppingnew', visible: true, align: 'center', valign: 'middle'},
            {title: '升级所需积分', field: 'upamount', visible: true, align: 'center', valign: 'middle'},
            {title: '部门id', field: 'deptid', visible: false, align: 'center', valign: 'middle'},
            {title: '备注', field: 'tips', visible: true, align: 'center', valign: 'middle'},
            {title: '状态(0.启用,1.冻结)', field: 'status', visible: false, align: 'center', valign: 'middle'},
            {title: '创建日期', field: 'createdt', visible: true, align: 'center', valign: 'middle'},
            {title: '更新日期', field: 'updatedt', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Membershipcardtype.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Membershipcardtype.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加会员配置
 */
Membershipcardtype.openAddMembershipcardtype = function () {
    var index = layer.open({
        type: 2,
        title: '添加会员配置',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/membershipcardtype/membershipcardtype_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看会员配置详情
 */
Membershipcardtype.openMembershipcardtypeDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '会员配置详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/membershipcardtype/membershipcardtype_update/' + Membershipcardtype.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除会员配置
 */
Membershipcardtype.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/membershipcardtype/delete", function (data) {
            Feng.success("删除成功!");
            Membershipcardtype.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("membershipcardtypeId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询会员配置列表
 */
Membershipcardtype.search = function () {
    var queryData = {};
    queryData['cardname'] = $("#cardname").val();
    queryData['deptid'] = $("#deptid").val();
    Membershipcardtype.table.refresh({query: queryData});
};

Membershipcardtype.params = function(){
    var queryData = {};
    queryData['cardname'] = $("#cardname").val();
    queryData['deptid'] = $("#deptid").val();
    return queryData;
}

$(function () {
    var defaultColunms = Membershipcardtype.initColumn();
    var table = new BSTable(Membershipcardtype.id, "/membershipcardtype/list", defaultColunms);
    // table.setPaginationType("client");
    table.setPaginationType("server");
    table.setQueryParams(Membershipcardtype.params());
    Membershipcardtype.table = table.init();
});
