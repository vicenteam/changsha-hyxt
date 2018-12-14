/**
 * 初始化复签记录详情对话框
 */
var QiandaoCheckinInfoDlg = {
    qiandaoCheckinInfoData : {}
};

/**
 * 清除数据
 */
QiandaoCheckinInfoDlg.clearData = function() {
    this.qiandaoCheckinInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
QiandaoCheckinInfoDlg.set = function(key, val) {
    this.qiandaoCheckinInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
QiandaoCheckinInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
QiandaoCheckinInfoDlg.close = function() {
    parent.layer.close(window.parent.QiandaoCheckin.layerIndex);
}

/**
 * 收集数据
 */
QiandaoCheckinInfoDlg.collectData = function() {
    this
    .set('id')
    .set('memberid')
    .set('createtime')
    .set('updatetime')
    .set('checkinid')
    .set('deptid')
    .set('status');
}

/**
 * 提交添加
 */
QiandaoCheckinInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/qiandaoCheckin/add", function(data){
        Feng.success("添加成功!");
        window.parent.QiandaoCheckin.table.refresh();
        QiandaoCheckinInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.qiandaoCheckinInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
QiandaoCheckinInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/qiandaoCheckin/update", function(data){
        Feng.success("修改成功!");
        window.parent.QiandaoCheckin.table.refresh();
        QiandaoCheckinInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.qiandaoCheckinInfoData);
    ajax.start();
}

$(function() {

});
