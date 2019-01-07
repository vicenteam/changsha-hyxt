/**
 * 初始化用户考勤详情对话框
 */
var UserAttendanceInfoDlg = {
    userAttendanceInfoData : {}
};

/**
 * 清除数据
 */
UserAttendanceInfoDlg.clearData = function() {
    this.userAttendanceInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserAttendanceInfoDlg.set = function(key, val) {
    this.userAttendanceInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserAttendanceInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
UserAttendanceInfoDlg.close = function() {
    parent.layer.close(window.parent.UserAttendance.layerIndex);
}

/**
 * 收集数据
 */
UserAttendanceInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('checkYearMonth')
    .set('checkTime1')
    .set('checkTime2')
    .set('deptId');
}

/**
 * 提交添加
 */
UserAttendanceInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/userAttendance/add", function(data){
        Feng.success("添加成功!");
        window.parent.UserAttendance.table.refresh();
        UserAttendanceInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userAttendanceInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
UserAttendanceInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/userAttendance/update", function(data){
        Feng.success("修改成功!");
        window.parent.UserAttendance.table.refresh();
        UserAttendanceInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userAttendanceInfoData);
    ajax.start();
}

$(function() {

});
