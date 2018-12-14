/**
 * 初始化商品类型维护详情对话框
 */
var IntegralrecordtypeInfoDlg = {
    integralrecordtypeInfoData : {},
    validateFields: {
        productname: {
            validators: {
                notEmpty: {
                    message: '商品名称不能为空'
                }
            }
        },
        productjifen: {
            validators: {
                notEmpty: {
                    message: '商品积分不能为空'
                },
                numeric:{
                    message: '只能为数字'
                }
            }
        } ,
        productnum: {
            validators: {
                notEmpty: {
                    message: '"商品数量不能为空'
                },
                numeric:{
                    message: '只能为数字'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
IntegralrecordtypeInfoDlg.clearData = function() {
    this.integralrecordtypeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
IntegralrecordtypeInfoDlg.set = function(key, val) {
    this.integralrecordtypeInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
IntegralrecordtypeInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
IntegralrecordtypeInfoDlg.close = function() {
    parent.layer.close(window.parent.Integralrecordtype.layerIndex);
}
IntegralrecordtypeInfoDlg.validate = function() {
    $('#activityId').data("bootstrapValidator").resetForm();
    $('#activityId').bootstrapValidator('validate');
    return $("#activityId").data('bootstrapValidator').isValid();
}
/**
 * 收集数据
 */
IntegralrecordtypeInfoDlg.collectData = function() {
    this
    .set('id')
    .set('names')
    .set('productname')
    .set('producttype')
    .set('productspecification')
    .set('productnum')
    .set('productbalance')
    .set('productjifen')
    .set('producteatingdose')
    .set('deptid')
    .set('createtime')
    .set('updatetime')
    .set('createuserid')
    .set('updateuserid');
}

/**
 * 提交添加
 */
IntegralrecordtypeInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/integralrecordtype/add", function(data){
        Feng.success("添加成功!");
        window.parent.Integralrecordtype.table.refresh();
        IntegralrecordtypeInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.integralrecordtypeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
IntegralrecordtypeInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/integralrecordtype/update", function(data){
        Feng.success("修改成功!");
        window.parent.Integralrecordtype.table.refresh();
        IntegralrecordtypeInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.integralrecordtypeInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("activityId", IntegralrecordtypeInfoDlg.validateFields);
});
