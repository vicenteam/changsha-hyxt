@/*
    select标签中各个参数的说明:
    name : select的名称
    id : select的id
    underline : 是否带分割线
@*/
<script>
    function province(domId) {
        // $("#"+domId).empty();
        var ajax = new $ax(Feng.ctxPath + "/provCityDist/province", function (data) {
            $("#"+domId).append("<option value=''>全部</option>")
            $.each(data, function (i, n) {
                // i表示objects的索引， n表示该索引对应的对象，即objects[i]
                $("#"+domId).append("<option value='"+n.id+"'>"+n.province+"</option>")
            });
        }, function (data) {
        });
        ajax.set(this.userInfoData);
        ajax.start();
    }
    function city(domId) {
        $("#"+domId).empty();
        var ajax = new $ax(Feng.ctxPath + "/provCityDist/city", function (data) {
            $("#"+domId).append("<option value=''>全部</option>")
            $.each(data, function (i, n) {
                // i表示objects的索引， n表示该索引对应的对象，即objects[i]
                $("#"+domId).append("<option value='"+n.id+"'>"+n.city+"</option>")
            });
        }, function (data) {
        });
        ajax.setData({provinceId:$("#province").val()})
        ajax.start();
    }
    function district(domId) {
        $("#"+domId).empty();
        var ajax = new $ax(Feng.ctxPath + "/provCityDist/district", function (data) {
            $("#"+domId).append("<option value=''>全部</option>")
            $.each(data, function (i, n) {
                // i表示objects的索引， n表示该索引对应的对象，即objects[i]
                $("#"+domId).append("<option value='"+n.id+"'>"+n.district+"</option>")
            });
        }, function (data) {
        });
        ajax.setData({cityId:$("#city").val()})
        ajax.start();
    }

</script>
<script>

</script>
<div class="form-group">
    <label class="col-sm-3 control-label">${name}</label>
    <div class="col-sm-9">
        <select class="form-control" id="${id}" name="${id}">
            @if(isNotEmpty(province)){
                <script>
                    province('${id}');
                    //绑定change事件
                    $("#province").change(function () {
                        city('city');
                        district('district');
                    })
                </script>
            @}
            @if(isNotEmpty(city)){
            <script>
                city('${id}');
                //绑定change事件
                $("#city").change(function () {
                    district('district');
                })
            </script>
            @}
            @if(isNotEmpty(district)){
            <script>
                district('${id}');
            </script>
            @}
        </select>
    </div>
</div>
@if(isNotEmpty(underline) && underline == 'true'){
    <div class="hr-line-dashed"></div>
@}


