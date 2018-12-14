@/*
    select标签中各个参数的说明:
    name : select的名称
    id : select的id
    underline : 是否带分割线
@*/
<div class="form-group">
    <label class="col-sm-3 control-label">${name}</label>
    <div class="col-sm-9">
        <input type="text" class="form-control layer-date" id="${id}"
               @if(isNotEmpty(value)){
               value="${tool.dateType(value)}"
               @}
        />
    </div>
</div>
@if(isNotEmpty(underline) && underline == 'true'){
    <div class="hr-line-dashed"></div>
@}
<script>
    var domid='${id}';
    var pattern='${pattern}';
    laydate.render({
        elem: '#'+domid
        ,format: pattern,
    });
</script>


