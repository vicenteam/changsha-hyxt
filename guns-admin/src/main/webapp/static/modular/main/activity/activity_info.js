/**
 * 初始化活动管理详情对话框
 */
var ActivityInfoDlg = {
    activityInfoData : {},
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '活动名称不能为空'
                }
            }
        },
        content: {
            validators: {
                notEmpty: {
                    message: '活动内容不能为空'
                }
            }
        },
        begindate: {
            validators: {
                notEmpty: {
                    message: '开始时间不能为空'
                }
            }
        },
        enddate: {
            validators: {
                notEmpty: {
                    message: '结束时间不能为空'
                }
            }
        },
        qiandaonum: {
            validators: {
                notEmpty: {
                    message: '签到次数不能为空'
                },
                numeric:{
                    message: '只能为数字'
                }
            }
        }
        ,
        maxgetnum: {
            validators: {
                notEmpty: {
                    message: '最大领取次数不能为空'
                },
                numeric:{
                    message: '只能为数字'
                }
            }
        } ,
        jifen: {
            validators: {
                notEmpty: {
                    message: '"消耗积分不能为空'
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
ActivityInfoDlg.clearData = function() {
    this.activityInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ActivityInfoDlg.set = function(key, val) {
    this.activityInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ActivityInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ActivityInfoDlg.close = function() {
    parent.layer.close(window.parent.Activity.layerIndex);
}

/**
 * 收集数据
 */
ActivityInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('content')
    .set('creater')
    .set('deptid')
    .set('ruleexpression')
    .set('begindate')
    .set('enddate')
    .set('createtime')
    .set('status')
    .set('jifen')
    .set('qiandaonum')
    .set('maxgetnum');
}
ActivityInfoDlg.validate = function () {
    $('#activityId').data("bootstrapValidator").resetForm();
    $('#activityId').bootstrapValidator('validate');
    return $("#activityId").data('bootstrapValidator').isValid();
};
/**
 * 提交添加
 */
ActivityInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    if($("#begindate").val().length==0){
        Feng.error("请选择活动开始时间")
        return;
    }if($("#enddate").val().length==0){
        Feng.error("请选择活动结束时间")
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/activity/add", function(data){
        Feng.success("添加成功!");
        //清除数据
        $("#name").val(null);
        $("#content").val("");
        $("#begindate").val("");
        $("#enddate").val("");
        $("#maxgetnum").val("");
        $("#qiandaonum").val("");
        $("#jifen").val("");
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.activityInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ActivityInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/activity/update", function(data){
        Feng.success("修改成功!");
        window.parent.Activity.table.refresh();
        ActivityInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.activityInfoData);
    ajax.start();
}
ActivityInfoDlg.lingqu = function() {

   var memberid= $("#memberid").val()
    if(memberid.length==0){
        Feng.error("请先读卡!");
       return
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/activity/lingqu", function(data){
        Feng.success("领取成功!");
        window.parent.Activity.table.refresh();
    },function(data){
        Feng.error("领取失败!" + data.responseJSON.message + "!");
    });
    ajax.set({activityId:$("#id").val(),memberId:$("#memberid").val()});
    ajax.start();
}

$(function() {
    Feng.initValidator("activityId", ActivityInfoDlg.validateFields);
});