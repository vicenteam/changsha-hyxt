/**
 * 初始化签到场次详情对话框
 */
var CheckinInfoDlg = {
    checkinInfoData : {}
};

/**
 * 清除数据
 */
CheckinInfoDlg.clearData = function() {
    this.checkinInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CheckinInfoDlg.set = function(key, val) {
    this.checkinInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CheckinInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CheckinInfoDlg.close = function() {
    parent.layer.close(window.parent.Checkin.layerIndex);
}

/**
 * 收集数据
 */
CheckinInfoDlg.collectData = function() {
    this
    .set('id')
    .set('screenings')
    .set('memberCount')
    .set('newCount')
    .set('startDate')
    .set('status')
    .set('endDate')
    .set('createDate')
    .set('deptId')
    .set('isActive');
}

/**
 * 提交添加
 */
CheckinInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/checkin/add", function(data){
        Feng.success("添加成功!");
        window.parent.Checkin.table.refresh();
        CheckinInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.checkinInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CheckinInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/checkin/update", function(data){
        Feng.success("修改成功!");
        window.parent.Checkin.table.refresh();
        CheckinInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.checkinInfoData);
    ajax.start();
}

$(function() {

});
