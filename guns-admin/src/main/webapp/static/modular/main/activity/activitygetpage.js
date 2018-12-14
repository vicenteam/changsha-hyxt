/**
 * 活动管理管理初始化
 */
var Activity = {
    id: "ActivityTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Activity.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '活动编号', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '活动名称', field: 'name', visible: true, width:"300px",align: 'center' , valign: 'middle'},
        {title: '领取时间', field: 'createtime', visible: true, align: 'center', valign: 'middle',formatter: function (value, row, index) {
                if(value.length>=10){
                    return value.substring(0,10);
                }else {
                    return value;
                }
            }},
    ];
};

/**
 * 活动管理管理初始化
 */
var ActivityMember = {
    id: "ActivityTable2",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ActivityMember.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '活动编号', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '名称', field: 'name', visible: true, align: 'center' , valign: 'middle'},
        {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle',formatter: function (value, row, index) {
            if(value.length>=10){
                return value.substring(0,10);
            }else {
                return value;
            }
            }},
        {title: '查看活动领取详情', field: 'status', visible: true, align: 'center', valign: 'middle',formatter: function (value, row, index) {
                return '<button type="button" class="btn btn-primary button-margin" onclick="Activity3.search(' + row.id + ')" id="">查看详情</button>'
            }},
    ];
};

var Activity3 = {
    id: "ActivityTable3",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Activity3.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '活动编号', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '活动名称', field: 'name', visible: true,width:"300px", align: 'center' , valign: 'middle'},
        {title: '领取时间', field: 'createtime', visible: true, align: 'center', valign: 'middle',formatter: function (value, row, index) {
                if(value.length>=10){
                    return value.substring(0,10);
                }else {
                    return value;
                }
            }},
    ];
};


/**
 * 查询活动管理列表
 */
Activity.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['type'] = 0;
    Activity.table.refresh({query: queryData});

    //加载我推荐的人信息
    ActivityMember.table.refresh({query: queryData});

    var queryData1 = {};
    queryData1['condition'] = -1;
    queryData1['type'] = 2;
    Activity3.table.refresh({query: queryData1});
};
Activity3.search = function (id) {
    var queryData = {};
    queryData['condition'] =id;
    queryData['type'] = 2;//id查询 0身份证 1读卡查询 2id查询
    Activity3.table.refresh({query: queryData});
};


$(function () {
    var defaultColunms = Activity.initColumn();
    var table = new BSTable(Activity.id, "/activity/activitygetpagelist", defaultColunms);
    //table.setPaginationType("client");
    table.setPaginationType("server");
    Activity.table = table.init();

    //table2
    var defaultColunms2 = ActivityMember.initColumn();
    var table2 = new BSTable(ActivityMember.id, "/activity/activitygetpagelist2", defaultColunms2);
    //table.setPaginationType("client");
    table2.setPaginationType("server");
    ActivityMember.table = table2.init();
    //table3
    var defaultColunms3 = Activity3.initColumn();
    var table3 = new BSTable(Activity3.id, "/activity/activitygetpagelist", defaultColunms3);
    //table.setPaginationType("client");
    table3.setPaginationType("server");
    Activity3.table = table3.init();
});
