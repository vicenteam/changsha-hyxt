/**
 * 初始化新增积分详情对话框
 */
var IntegralrecordInfoDlg = {
    integralrecordInfoData : {},
    validateFields: {
        integral: {
            validators: {
                notEmpty: {
                    message: '新增积分不能为空'
                },
                numeric: {message: '新增积分只能输入数字'},
                greaterThan: {
                    value: 0.001,
                    message: "新增积分最小输入值为 0.001"
                }
            }
        }
    }
};

/**
 * 清除数据
 */
IntegralrecordInfoDlg.clearData = function() {
    this.integralrecordInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
IntegralrecordInfoDlg.set = function(key, val) {
    this.integralrecordInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
IntegralrecordInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
IntegralrecordInfoDlg.close = function() {
    parent.layer.close(window.parent.Integralrecord.layerIndex);
}

/**
 * 收集数据
 */
IntegralrecordInfoDlg.collectData = function() {
    this
    .set('integral')
    .set('productname')
    .set('consumptionNum')
    .set('typeId')
    .set('memberId');
}


/**
 * 验证数据是否为空
 */
IntegralrecordInfoDlg.validate = function () {
    $('#integralrecordInfoTable').bootstrapValidator('validate');
    return $("#integralrecordInfoTable").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
IntegralrecordInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/integralrecord/add", function(data){
        Feng.success("操作成功!");
        $("#introducerName").val("");
        $("#name").val("");
        $("#memberId").val("");
        $("#tel").val("");
        $("#address").val("");
        $("#integralSum").val("");
        $("#countPrice").val("");
        $("#levelID").val("");
    },function(data){
        Feng.error("操作成功!" + data.responseJSON.message + "!");
    });
    ajax.set(this.integralrecordInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
IntegralrecordInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/integralrecord/update", function(data){
        Feng.success("修改成功!");
        window.parent.Integralrecord.table.refresh();
        IntegralrecordInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.integralrecordInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("integralrecordInfoTable", IntegralrecordInfoDlg.validateFields);

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

    });;
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
    ajax.set("limit", 100);
    ajax.start();
}
function findbyid() {
    var ajax = new $ax(Feng.ctxPath + "/integralrecordtype/detail/"+ $("#productname").val(), function (data) {
       console.log(data)
        $("#integral").val(data.productjifen);
        temp_integral=data.productjifen;
        $("#consumptionNum").val(1)
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

