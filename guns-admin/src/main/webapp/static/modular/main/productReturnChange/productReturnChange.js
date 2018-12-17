/**
 * 商品退换货管理初始化
 */
var ProductReturnChange = {
    id: "ProductReturnChangeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ProductReturnChange.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '退换货人姓名', field: 'memberName', visible: true, align: 'center', valign: 'middle'},
        // {title: '退换货人id', field: 'memberId', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'},
        {title: '提交申请人', field: 'createuserid', visible: true, align: 'center', valign: 'middle'},
        {title: '商品名称', field: 'productName',width:"300px",visible: true, align: 'center', valign: 'middle'},
        {
            title: '退换货类型',
            field: 'returnchangeType',
            visible: true,
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                if (value == 1) {
                    return '换货';
                } else {
                    return '退货';
                }
            }
        },
        {title: '退换货商品id', field: 'returnchangeproductId', visible: false, align: 'center', valign: 'middle'},
        {title: '退换货商品名称', field: 'returnchangeproductName',width:"300px", visible: true, align: 'center', valign: 'middle'},
        {title: '退换货数量', field: 'returnchangeNum', visible: true, align: 'center', valign: 'middle'},
        {
            title: '状态',
            field: 'status',
            visible: true,
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                if (value == 1) {
                    return '已处理';
                } else {
                    return '待处理';
                }
            }
        },
        {title: '积分记录表id', field: 'integralrecodeId', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ProductReturnChange.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        ProductReturnChange.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加商品退换货
 */
ProductReturnChange.openAddProductReturnChange = function () {
    var index = layer.open({
        type: 2,
        title: '添加商品退换货',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/productReturnChange/productReturnChange_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看商品退换货详情
 */
ProductReturnChange.openProductReturnChangeDetail = function () {
    if (this.check()) {
        if(ProductReturnChange.seItem.status==1){
            Feng.error("操作失败 已处理状态无法进行该操作!");
            return;
        }
        var index = layer.open({
            type: 2,
            title: '商品退换货详情',
            area: ['900px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/productReturnChange/productReturnChange_update/' + ProductReturnChange.seItem.id
        });
        this.layerIndex = index;
    }
};
ProductReturnChange.openProductReturnChangeDetail2 = function () {
    if (this.check()) {
        if(ProductReturnChange.seItem.status==0){
            Feng.error("操作失败 待处理状态无法进行该操作!");
            return;
        }
        var index = layer.open({
            type: 2,
            title: '商品退换货详情',
            area: ['900px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/productReturnChange/productReturnChange_update2/' + ProductReturnChange.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 删除商品退换货
 */
ProductReturnChange.delete = function () {
    layer.confirm('您确定要删除本条数据吗？', {btn: ['确定', '取消']}, function () {
        layer.closeAll('dialog');
        if (this.check()) {
            var ajax = new $ax(Feng.ctxPath + "/productReturnChange/delete", function (data) {
                Feng.success("删除成功!");
                ProductReturnChange.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("productReturnChangeId", this.seItem.id);
            ajax.start();
        }
    });
};

/**
 * 查询商品退换货列表
 */
ProductReturnChange.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['productName'] = $("#productName").val();
    queryData['returnchangeType'] = $("#returnchangeType").val();
    queryData['status'] = $("#status").val();
    ProductReturnChange.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ProductReturnChange.initColumn();
    var table = new BSTable(ProductReturnChange.id, "/productReturnChange/list", defaultColunms);
    //table.setPaginationType("client");
    table.setPaginationType("server");
    ProductReturnChange.table = table.init();
});
