/**
 * 初始化商品退换货详情对话框
 */
var ProductReturnChangeInfoDlg = {
    productReturnChangeInfoData : {}
};

/**
 * 清除数据
 */
ProductReturnChangeInfoDlg.clearData = function() {
    this.productReturnChangeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProductReturnChangeInfoDlg.set = function(key, val) {
    this.productReturnChangeInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProductReturnChangeInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProductReturnChangeInfoDlg.close = function() {
    parent.layer.close(window.parent.ProductReturnChange.layerIndex);
}

/**
 * 收集数据
 */
ProductReturnChangeInfoDlg.collectData = function() {
    this
    .set('id')
    .set('memberName')
    .set('memberId')
    .set('createtime')
    .set('deptId')
    .set('createuserid')
    .set('productId')
    .set('productName')
    .set('returnchangeType')
    .set('returnchangeproductId')
    .set('returnchangeproductName')
    .set('returnchangeNum')
    .set('status')
    .set('integralrecodeId');
}

/**
 * 提交添加
 */
ProductReturnChangeInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/productReturnChange/add", function(data){
        Feng.success("添加成功!");
        window.parent.ProductReturnChange.table.refresh();
        ProductReturnChangeInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.productReturnChangeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ProductReturnChangeInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    if(isNaN($("#returnchangeNum").val())){
        Feng.error("确认退换货数量必须是数字!");
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/productReturnChange/update", function(data){
        Feng.success("处理成功!");
        window.parent.ProductReturnChange.table.refresh();
        ProductReturnChangeInfoDlg.close();
    },function(data){
        Feng.error("处理失败!" + data.responseJSON.message + "!");
    });
    ajax.set("id",$("#id").val());
    ajax.set("productname",$("#productname").val());
    ajax.set("isInsert",$("#isInsert").val());
    ajax.set("returnchangeNum",$("#returnchangeNum").val());
    ajax.start();
}

$(function() {

//绑定商品搜索
    $('.selectpicker').selectpicker({
        'selectedText': 'cat'
    });
    $(".selectpicker" ).selectpicker('refresh');
    $(".bs-searchbox input").keyup(function(event){
        // if(event.keyCode == "13") {//判断如果按下的是回车键则执行下面的代码
        var search=($(".bs-searchbox input").val())
        //进行搜索 integralrecordtype/list
        var ajax = new $ax(Feng.ctxPath + "/integralrecordtype/list", function (data) {
            console.log(data)
            $("#productname").empty();
            if(data.rows.length>0){
                for(var i=0;i<data.rows.length;i++){
                    $("#productname").append("<option value='"+data.rows[i].id+"'>【"+data.rows[i].productname+"】 库存("+data.rows[i].productnum+")</option>");
                }
            }
            $(".selectpicker" ).selectpicker('refresh');
        }, function (data) {
        });
        ajax.set("condition", search);
        ajax.set("producttype", $("#typeId").val());
        ajax.set("limit", 9999);
        ajax.start();
        // }

    });
    $("#typeId").change(function () {
        changeselect();


    })
    $("#productname").change(function () {
        //查询商品
        findbyid();

    })
    changeselect();
    findbyid();
    $("#play").keyup(function(event){
        console.log($("#play").val())
        changejifen();

    })

});
function changeselect() {
    var ajax = new $ax(Feng.ctxPath + "/integralrecordtype/list", function (data) {
        $("#productname").empty();
        if(data.rows.length>0){
            for(var i=0;i<data.rows.length;i++){
                $("#productname").append("<option value='"+data.rows[i].id+"' obj='"+data.rows[i].producttype+"'>【"+data.rows[i].productname+"】 库存("+data.rows[i].productnum+")</option>");
            }
        }
        $(".selectpicker" ).selectpicker('refresh');
        findbyid();
    }, function (data) {
    });
    ajax.set("producttype", $("#typeId").val());
    if($("#returnchangeproductId").val().length>0){
        ajax.set("id", $("#returnchangeproductId").val());
    }
    ajax.set("limit", 100);
    ajax.start();
}
function findbyid() {
    var ajax = new $ax(Feng.ctxPath + "/integralrecordtype/detail/"+ $("#productname").val(), function (data) {
        console.log(data)
        $("#integral").val(data.productjifen);
        if(data.producttype==2||data.producttype==3){
            $("#divplay").css("display","");
        }else {
            $("#divplay").css("display","none");
        }
    }, function (data) {
    });
    ajax.set("producttype", $("#typeId").val());
    ajax.start();
}
function changejifen() {
    if($("#typeId").val()==3){
        var tempIntegral= $("#play").val()*10;
        var ajax = new $ax(Feng.ctxPath + "/integralrecordtype/detail/"+ $("#productname").val(), function (data) {
            console.log(data)
            $("#integral").val((parseInt(data.productjifen)+tempIntegral))
        }, function (data) {
        });
        ajax.start();
    }
}