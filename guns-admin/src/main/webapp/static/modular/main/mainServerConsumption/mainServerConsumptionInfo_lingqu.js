/**
 * 初始化增值服务详情对话框
 */
var MainServerConsumptionInfoDlg = {
    mainServerConsumptionInfoData : {}
};

/**
 * 清除数据
 */
MainServerConsumptionInfoDlg.clearData = function() {
    this.mainServerConsumptionInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MainServerConsumptionInfoDlg.set = function(key, val) {
    this.mainServerConsumptionInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MainServerConsumptionInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MainServerConsumptionInfoDlg.close = function() {
    parent.layer.close(window.parent.MainServerConsumption.layerIndex);
}

/**
 * 收集数据
 */
MainServerConsumptionInfoDlg.collectData = function() {
    this
    .set('id')
    .set('serverName')
    .set('serverContent')
    .set('createTime')
    .set('updateTime')
    .set('consumptionJiFen');
}

/**
 * 提交添加
 */
MainServerConsumptionInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    var memberid= $("#memberid").val()
    if(memberid.length==0){
        Feng.error("请先读卡!");
        return
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/mainServerConsumption/lingqu", function(data){
        Feng.success("领取成功!");
        MainServerConsumptionInfoDlg.close();
    },function(data){
        Feng.error("领取失败!" + data.responseJSON.message + "!");
    });
    ajax.set({memberId:memberid,serverId:$("#id").val()});
    ajax.start();
}

/**
 * 提交修改
 */
MainServerConsumptionInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/mainServerConsumption/update", function(data){
        Feng.success("修改成功!");
        window.parent.MainServerConsumption.table.refresh();
        MainServerConsumptionInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.mainServerConsumptionInfoData);
    ajax.start();
}

$(function() {

});
