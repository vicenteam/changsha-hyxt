@/*
    选择查询条件标签的参数说明:

    name : 查询条件的名称
    id : 查询内容的input框id
@*/
<div class="input-group">
    <div class="input-group-btn">
        <button data-toggle="dropdown" class="btn btn-white dropdown-toggle" type="button">
            ${name}
        </button>
    </div>
    <select class="form-control" id="${id}">

    </select>
</div>
<script>
    $(function () {
        var ajax = new $ax(Feng.ctxPath + "/dept/findDeptLists", function (data) {
            $.each(data, function (i, n) {
                // i表示objects的索引， n表示该索引对应的对象，即objects[i]
                $("#deptId").append("<option value='"+n.id+"'>"+n.name+"</option>")
            });
        }, function (data) {
        });
        ajax.setData({deptId:${val}})
        ajax.start();
    })
</script>