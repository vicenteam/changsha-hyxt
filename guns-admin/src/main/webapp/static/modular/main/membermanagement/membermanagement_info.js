/**
 * 初始化会员基础信息详情对话框
 */
var MembermanagementInfoDlg = {
    membermanagementInfoData : {},
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '姓名不能为空'
                }
            }
        },
        phone: {
            validators: {
                notEmpty: {
                    message: '联系方式不能为空'
                }
            }
        }
    },
    layerIndex: -1
};

/**
 * 清除数据
 */
MembermanagementInfoDlg.clearData = function() {
    this.membermanagementInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MembermanagementInfoDlg.set = function(key, val) {
    this.membermanagementInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MembermanagementInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MembermanagementInfoDlg.close = function() {
    parent.layer.close(window.parent.Membermanagement.layerIndex);
}
/**
 * 验证数据是否为空
 */
MembermanagementInfoDlg.validate = function () {
    // $('#membermanagementInfoTable').data("bootstrapValidator").resetForm();
    $('#membermanagementInfoTable').bootstrapValidator('validate');
    return $("#membermanagementInfoTable").data('bootstrapValidator').isValid();
};
/**
 * 收集数据
 */
MembermanagementInfoDlg.collectData = function() {
    this
    .set('id')
    .set('cadID')
    .set('name')
    .set('telphone')
    .set('sex')
    .set('email')
    .set('phone')
    .set('state')
    .set('integral')
    .set('levelID')
    .set('cardID')
    .set('createTime')
    .set('isoldsociety')
    .set('birthday')
    .set('deptName')
    .set('introducerId')
    .set('province')
    .set('city')
    .set('district')
    .set('medicalHistory')
    .set('familyStatusID')
    .set('staffID')
    .set('countyID')
    .set('townshipid')
    .set('healthStatus')
    .set('CheckINTime1')
    .set('CheckINTime2')
    .set('recommendMember')
    .set('address')
    .set('countPrice')
    .set('deptId')
    .set('cardCode')
    .set('avatar')
    .set("otherMemberId")
    .set("anotherName")
    .set('token');
}

/**
 * 提交添加
 */
MembermanagementInfoDlg.addSubmit = function() {
if($("#readDeviceCard").val().length==0){
    Feng.error("请先进行写卡操作!" );
    return;
}
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    if( $("#staffID").val().length==0){
        Feng.error("请选择服务员工!");
        return;
    }
    var baMedicals="";
    baMedicals = $("input:checkbox[name='baMedicals']:checked").map(function(index,elem) {
        return $(elem).val();
    }).get().join(',');
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/membermanagement/add", function(data){
        Feng.success("添加成功!");
        //清除页面值
        $("#cadID").val("")
        $("#introducerName").val("")
        $("#staffID").val("")
        $("#name").val("")
        $("#sex").val("")
        $("#email").val("")
        $("#phone").val("")
        $("#isoldsociety").val("")
        $("#province").val("")
        $("#city").val("")
        $("#district").val("")
        $("#address").val("")
        $("#medicalHistory").val("")
        $("#birthday").val("")
        $("#readDeviceCard").val("")
        $("#otherMemberId").val("")
        $("#anotherName").val("")

    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.membermanagementInfoData);
    ajax.set("baMedicals",baMedicals);
    ajax.set("code",$("#readDeviceCard").val());
    ajax.start();
}

/**
 * 提交修改
 */
MembermanagementInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    var baMedicals="";
    baMedicals = $("input:checkbox[name='baMedicals']:checked").map(function(index,elem) {
        return $(elem).val();
    }).get().join(',');
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/membermanagement/update", function(data){
        Feng.success("修改成功!");
        window.parent.Membermanagement.table.refresh();
        MembermanagementInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.membermanagementInfoData);
    ajax.set("baMedicals",baMedicals);
    ajax.start();
}
/**
 * 补卡
 */
MembermanagementInfoDlg.buka = function() {
    if($("#cadID").val() == null || $("#cadID").val() == ""){
        alert("身份证编号不能为空！")
        return;
    }

    this.clearData();
    this.collectData();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/membermanagement/buka", function(data){
        Feng.success("补卡成功!");
        window.parent.Membermanagement.table.refresh();
        MembermanagementInfoDlg.close();
    },function(data){
        Feng.error("补卡失败!" + data.responseJSON.message + "!");
    });
    ajax.set("memberId",$("#id").val());
    ajax.set("cardID",$("#cardID").val());
    ajax.set("cadID",$("#cadID").val());
    ajax.start();
}
/**
 * 打卡摄像头
 */
var imgData;
MembermanagementInfoDlg.eaa = function() {
    $("#avatars").attr("src","data:image/png;base64,"+imgData);
    //执行文件上传
    var data=imgData;

    var ajax = new $ax(Feng.ctxPath + "/mgr/upload1", function(data){
        $("#avatar").val(data);
    },function(data){
        Feng.error("操作失败!" + data.responseJSON.message + "!");
    });
    ajax.set({"file":data});
    ajax.start();

}
$("#getUserPhoto").click(function () {
    var index = layer.open({
        type: 2,
        title: '拍照',
        area: ['800px', '700px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/membermanagement/membermanagement_userPhotoPage'
    });
    this.layerIndex = index;
})
$(function() {
    Feng.initValidator("membermanagementInfoTable", MembermanagementInfoDlg.validateFields);
});
