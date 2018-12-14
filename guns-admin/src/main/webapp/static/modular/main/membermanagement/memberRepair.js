/**
 * 初始化新增积分详情对话框
 */
var MemberRepair = {
    memberRepair : {}
};

/**
 * 清除数据
 */
MemberRepair.clearData = function() {
    this.memberRepair = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MemberRepair.set = function(key, val) {
    this.memberRepair[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MemberRepair.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MemberRepair.close = function() {
    parent.layer.close(window.parent.Integralrecord.layerIndex);
}

/**
 * 收集数据
 */
MemberRepair.collectData = function() {
    this
        .set('screenings');
}


/**
 * 验证数据是否为空
 */
MemberRepair.validate = function () {
    $('#memberRepairInfoTable').bootstrapValidator('validate');
    return $("#memberRepairInfoTable").data('bootstrapValidator').isValid();
};

/**
 * 提交补签
 */
MemberRepair.addSubmit = function() {
    var datas = {};
    datas['memberId'] = $("#introducerId").val();
    datas['time'] = $("#time").val();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/memberRepair/repair", function(data){
        Feng.success("补签成功");
    },function(data){
        Feng.error("补签失败!" + data.responseJSON.message + "!");
    });
    ajax.set(datas);
    ajax.start();
}

$(function() {
    Feng.initValidator("memberRepairInfoTable", MemberRepair.validateFields);
});
