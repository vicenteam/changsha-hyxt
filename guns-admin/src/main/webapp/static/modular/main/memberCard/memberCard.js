/**
 * 管理初始化
 */
var MemberCard = {
    id: "MemberCardTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MemberCard.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '会员ID', field: 'memberid', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '卡片标识码', field: 'code', visible: true, align: 'center', valign: 'middle'},
            {title: '开卡日期', field: 'createtime', visible: true, align: 'center', valign: 'middle'},
            {title: '状态(0可用 1不可用)', field: 'state', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
MemberCard.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MemberCard.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
MemberCard.openAddMemberCard = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/memberCard/memberCard_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
MemberCard.openMemberCardDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/memberCard/memberCard_update/' + MemberCard.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
MemberCard.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/memberCard/delete", function (data) {
            Feng.success("删除成功!");
            MemberCard.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("memberCardId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询列表
 */
MemberCard.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    MemberCard.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = MemberCard.initColumn();
    var table = new BSTable(MemberCard.id, "/memberCard/list", defaultColunms);
    table.setPaginationType("client");
    MemberCard.table = table.init();
});



