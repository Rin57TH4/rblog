<#include "/admin/utils/ui.ftl"/>
<@layout>
<section class="content-header">
    <h1>Chỉnh sửa phần</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">Trang chủ</a></li>
        <li><a href="${base}/admin/channel/list">Bộ phận quản lý</a></li>
        <li class="active">Chỉnh sửa phần</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <form id="qForm" class="form-horizontal form-label-left" method="post" action="update">
                <#if view??>
                    <input type="hidden" name="id" value="${view.id}" />
                </#if>
                <input type="hidden" name="weight" value="${view.weight!0}">
                <input type="hidden" id="thumbnail" name="thumbnail" value="${view.thumbnail}">
                <div class="box">
                    <div class="box-header with-border">
                        <h3 class="box-title">Chỉnh sửa phần</h3>
                    </div>
                    <div class="box-body">
                        <div class="form-group">
                            <label class="col-lg-2 control-label">Tên</label>
                            <div class="col-lg-3">
                                <input type="text" name="name" class="form-control" value="${view.name}" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">Nhận dạng duy nhất</label>
                            <div class="col-lg-3">
                                <input type="text" name="key" class="form-control" value="${view.key}" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">Trạng thái thanh điều hướng</label>
                            <div class="col-lg-3">
                                <select name="status" class="form-control" data-select="${view.status}">
                                    <option value="0">Hiển thị</option>
                                    <option value="1">Ẩn</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">Hình thu nhỏ</label>
                            <div class="col-lg-3">
                                <div class="thumbnail-box">
                                    <div class="convent_choice" id="thumbnail_image" <#if view.thumbnail?? && view.thumbnail?length gt 0> style="background: url(${base + view.thumbnail}) no-repeat scroll top;" </#if>>
                                        <div class="upload-btn">
                                            <label>
                                                <span>Nhấn vào đây để chọn một hình ảnh</span>
                                                <input id="upload_btn" type="file" name="file" accept="image/*" title="Nhấn vào đây để thêm hình ảnh">
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="box-footer">
                        <button type="submit" class="btn btn-primary">Gửi</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</section>
<script type="text/javascript">
var J = jQuery;

$(function() {
    $('#upload_btn').change(function(){
        $(this).upload('${base}/post/upload?crop=thumbnail_channel_size', function(data){
            if (data.status == 200) {
                var path = data.path;
                $("#thumbnail_image").css("background", "url(" + path + ") no-repeat scroll center 0 rgba(0, 0, 0, 0)");
                $("#thumbnail").val(path);
            }
        });
    });
})
</script>
</@layout>