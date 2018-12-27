/**
 * 体验服务记录管理初始化
 */
var ServerMemberinfo = {
    id: "ServerMemberinfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ServerMemberinfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '体验人', field: 'memberName', visible: true, align: 'center', valign: 'middle'},
        {title: '', field: 'memberId', visible: false, align: 'center', valign: 'middle'},
        {title: '服务名称', field: 'serverName', visible: true, align: 'center', valign: 'middle'},
        {title: '', field: 'serverId', visible: false, align: 'center', valign: 'middle'},
        {title: '体验时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
        {title: '消耗积分', field: 'jifen', visible: true, align: 'center', valign: 'middle'},
        {title: '', field: 'deptid', visible: false, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
ServerMemberinfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        ServerMemberinfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加体验服务记录
 */
ServerMemberinfo.openAddServerMemberinfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加体验服务记录',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/serverMemberinfo/serverMemberinfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看体验服务记录详情
 */
ServerMemberinfo.openServerMemberinfoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '体验服务记录详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/serverMemberinfo/serverMemberinfo_update/' + ServerMemberinfo.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除体验服务记录
 */
ServerMemberinfo.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/serverMemberinfo/delete", function (data) {
            Feng.success("删除成功!");
            ServerMemberinfo.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("serverMemberinfoId", this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询体验服务记录列表
 */
ServerMemberinfo.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ServerMemberinfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ServerMemberinfo.initColumn();
    var table = new BSTable(ServerMemberinfo.id, "/serverMemberinfo/list", defaultColunms);
    //table.setPaginationType("client");
    table.setPaginationType("server");
    ServerMemberinfo.table = table.init();
});
