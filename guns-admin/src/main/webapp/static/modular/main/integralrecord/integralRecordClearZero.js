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
            {title: '积分记录编号', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '部门', field: 'deptName', visible: true, align: 'center', valign: 'middle'},
            {title: '时间', field: 'createdt', visible: true, align: 'center', valign: 'middle'},
            {title: '操作', field: 'operation', visible: true, align: 'center', valign: 'middle',formatter: function (value, row, index) {
                console.log(row.id);
                    return '<button type="button" class="btn btn-primary button-margin" onclick="Integralrecord.recovery(' + row.id + ')" id=""><i class="fa fa-edit"></i>&nbsp;恢复</button>'
                }}
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
 * 积分清零
 */
Integralrecord.delete = function () {
    var ajax = new $ax(Feng.ctxPath + "/integralrecordclearzero/clear", function (data) {
        Feng.success("删除成功!");
        Integralrecord.table.refresh();
    }, function (data) {
        Feng.error("删除失败!" + data.responseJSON.message + "!");
    });
    // ajax.set("integralrecordId",this.seItem.id);
    ajax.start();
};

/**
 * 查询新增积分列表
 */
Integralrecord.search = function () {
    var queryData = {};
    queryData['deptid'] = $("#deptid").val();
    Integralrecord.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Integralrecord.initColumn();
    var table = new BSTable(Integralrecord.id, "/integralrecordclearzero/list", defaultColunms);
    //table.setPaginationType("client");
    table.setPaginationType("server");
    Integralrecord.table = table.init();
});


Integralrecord.recovery = function (id) {
    var ajax = new $ax(Feng.ctxPath + "/integralrecordclearzero/rollBack", function (data) {
        Feng.success("操作成功!");
        Integralrecord.table.refresh();
    }, function (data) {
        Feng.error("操作失败!" + data.responseJSON.message + "!");
    });
    ajax.set("integralRecordId",id);
    ajax.start();
};