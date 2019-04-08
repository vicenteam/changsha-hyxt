/**
 * 初始化识别源数据详情对话框
 */
var UserAttendanceSourceInfoDlg = {
    userAttendanceSourceInfoData : {}
};

/**
 * 清除数据
 */
UserAttendanceSourceInfoDlg.clearData = function() {
    this.userAttendanceSourceInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserAttendanceSourceInfoDlg.set = function(key, val) {
    this.userAttendanceSourceInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserAttendanceSourceInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
UserAttendanceSourceInfoDlg.close = function() {
    parent.layer.close(window.parent.UserAttendanceSource.layerIndex);
}

/**
 * 收集数据
 */
UserAttendanceSourceInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('img')
    .set('sex')
    .set('phone')
    .set('deptId')
    .set('createdt');
}

/**
 * 提交添加
 */
UserAttendanceSourceInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/userAttendanceSource/add", function(data){
        Feng.success("添加成功!");
        window.parent.UserAttendanceSource.table.refresh();
        UserAttendanceSourceInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userAttendanceSourceInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
UserAttendanceSourceInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/userAttendanceSource/update", function(data){
        Feng.success("修改成功!");
        window.parent.UserAttendanceSource.table.refresh();
        UserAttendanceSourceInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userAttendanceSourceInfoData);
    ajax.start();
}

$(function() {
    // 初始化头像上传
    var avatarUp = new $WebUpload("img");
    avatarUp.setUploadBarId("progressBar");
    avatarUp.init();
});
