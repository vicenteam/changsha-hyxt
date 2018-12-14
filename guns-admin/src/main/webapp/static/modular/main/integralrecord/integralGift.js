/**
 * 初始化积分赠送详情对话框
 */
var IntegralGiftInfoDlg = {
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
IntegralGiftInfoDlg.clearData = function() {
    this.integralrecordInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
IntegralGiftInfoDlg.set = function(key, val) {
    this.integralrecordInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
IntegralGiftInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
IntegralGiftInfoDlg.close = function() {
    parent.layer.close(window.parent.Integralrecord.layerIndex);
}

/**
 * 收集数据
 */
IntegralGiftInfoDlg.collectData = function() {
    this
        .set('id')
        .set('integral')
        .set('deptid')
}


/**
 * 验证数据是否为空
 */
IntegralGiftInfoDlg.validate = function () {
    $('#integralGiftInfoTable').bootstrapValidator('validate');
    return $("#integralGiftInfoTable").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
IntegralGiftInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/integralgift/add", function(data){
        Feng.success("添加成功!");
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.integralrecordInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("integralGiftInfoTable", IntegralGiftInfoDlg.validateFields);
});
