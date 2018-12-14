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
        {title: '活动名称', field: 'name', visible: true, align: 'center', width: '170px', valign: 'middle'},
        {
            title: '活动内容',
            field: 'content',
            visible: true,
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                var data = "";
                var textItems = new Array();
                if (value.length > 25) {
                    for (var i = 0; i < Math.ceil(value.length / 25); i++) {
                        textItems.push(value.substring(i * 25, (i + 1) * 25));
                    }

                } else {
                    textItems.push(value);
                }
                console.log(textItems)
                for (var i = 0; i < textItems.length; i++) {
                    if (i == (textItems.length - 1)) {
                        data += textItems[i];
                    } else {
                        data += textItems[i] + "</br>";
                    }
                }
                return "<span style='font-size: 11px'>" + data + "</span>"
            }
        },
        {title: '活动创建者', field: 'creater', visible: true, width: '100px', align: 'center', valign: 'middle'},
        {
            title: '活动规则',
            field: 'ruleexpression',
            visible: true,
            align: 'center',
            width: '100px',
            valign: 'middle',
            formatter: function (value, row, index) {
                if (value == 0) {
                    return '累计签到';
                } else if (value == 1) {
                    return '连续签到';
                } else if (value == 2) {
                    return '积分兑换';
                } else if (value == 3) {
                    return '推荐奖励';
                } else if (value == 4) {
                    return '被推荐奖励';
                }
            }
        },
        {title: '签到次数', field: 'qiandaonum', visible: true, align: 'center', width: '80px', valign: 'middle'},
        {
            title: '活到开始时间',
            field: 'begindate',
            visible: true,
            align: 'center',
            width: '100px',
            valign: 'middle',
            formatter: function (value, row, index) {
                if (value.length >= 10) {
                    return value.substring(0, 10);
                } else {
                    return value;
                }
            }
        },
        {
            title: '活动结束时间',
            field: 'enddate',
            visible: true,
            align: 'center',
            width: '100px',
            valign: 'middle',
            formatter: function (value, row, index) {
                if (value.length >= 10) {
                    return value.substring(0, 10);
                } else {
                    return value;
                }
            }
        },
        {
            title: '活动状态',
            field: 'status',
            visible: true,
            align: 'center',
            valign: 'middle',
            width: '100px',
            formatter: function (value, row, index) {
                if (value == 0) {
                    return '<span style="color: #8c8c8c">未开始</span>';
                } else if (value == 1) {
                    return '<span style="color:red">已过期</span>';
                } else {
                    return '<span style="color: green">进行中</span>';
                }
            }
        },
        {title: '最大领取次数', field: 'maxgetnum', visible: true, align: 'center', width: '100px', valign: 'middle'},
        {title: '当前领取总数', field: 'jifen', visible: true, align: 'center', width: '100px', valign: 'middle'},
        {
            title: '操作',
            field: 'id',
            visible: true,
            align: 'center',
            valign: 'middle',
            width: '280px',
            formatter: function (value, row, index) {
                var doma = '<button type="button" class="btn btn-primary button-margin" onclick="Activity.lingqu(' + row.id + ')" id="" '
                if (row.status != 2) {
                    doma += ' disabled="disabled"'
                } else if (row.ruleexpression == 3) {
                    // doma += ' disabled="disabled"'
                }
                doma += '><i class="fa fa-edit"></i>领取</button>'
                doma += '<button type="button" class="btn btn-primary button-margin" onclick="Activity.openActivityDetail(' + row.id + ')" id=""'
                if (row.status != 1) {
                    if (row.ruleexpression == 3) {

                    } else if (row.ruleexpression == 4) {

                    } else {
                        doma += ' disabled="disabled"'

                    }
                } else {
                    doma += ' disabled="disabled"'
                }
                doma += '><i class="fa fa-arrows-alt"></i>修改</button>';
                doma += '<button type="button" class="btn btn-danger button-margin" onclick="Activity.delete(' + row.id + ')" id="" '
                if (row.status != 0) {
                    doma += ' disabled="disabled"'
                }
                doma += '><i class="fa fa-edit"></i>删除</button>'

                return doma;
            }
        },
    ];
};

/**
 * 检查是否选中
 */
Activity.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Activity.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加活动管理
 */
Activity.openAddActivity = function () {
    var index = layer.open({
        type: 2,
        title: '添加活动管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/activity/activity_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看活动管理详情
 */
Activity.openActivityDetail = function (id) {
    var index = layer.open({
        type: 2,
        title: '修改活动',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/activity/activity_update/' + id
    });
    this.layerIndex = index;
};
/**
 * 领取活动奖励
 */
Activity.lingqu = function (id) {
    var index = layer.open({
        type: 2,
        title: '活动管理详情',
        area: ['900px', '620px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/activity/activity_lingqu/' + id
    });
    this.layerIndex = index;
};
/**
 * 删除活动管理
 */
Activity.delete = function (id) {
    var ajax = new $ax(Feng.ctxPath + "/activity/delete", function (data) {
        Feng.success("删除成功!");
        Activity.table.refresh();
    }, function (data) {
        Feng.error("删除失败!" + data.responseJSON.message + "!");
    });
    ajax.set("activityId", id);
    ajax.start();
};

/**
 * 查询活动管理列表
 */
Activity.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Activity.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Activity.initColumn();
    var table = new BSTable(Activity.id, "/activity/list", defaultColunms);
    //table.setPaginationType("client");
    table.setPaginationType("server");
    Activity.table = table.init();
});
