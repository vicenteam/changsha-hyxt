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
            {title: '姓名', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '会员等级', field: 'levelID', visible: true, align: 'center', valign: 'middle'},
            {title: '当前可用积分', field: 'integral', visible: true, align: 'center', valign: 'middle'},
            {title: '签到总场次', field: 'count', visible: true, align: 'center', valign: 'middle'},
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
    queryData['name'] = $("#cardname").val();
    queryData['id'] = $("#deptid").val();
    return queryData;
}

$(function () {
    var defaultColunms = Membershipcardtype.initColumn();
    var table = new BSTable(Membershipcardtype.id, "/membermanagement/openintroducerdata/"+$("#memberId").val(), defaultColunms);
    // table.setPaginationType("client");
    table.setPaginationType("server");
    table.setQueryParams(Membershipcardtype.params());
    Membershipcardtype.table = table.init();
});
