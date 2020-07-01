<#include "/admin/utils/ui.ftl"/>
<@layout>

<section class="content-header">
    <h1>Đặt lại mật khẩu</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">Trang chủ</a></li>
        <li><a href="${base}/admin/user/list">Quản lý người dùng</a></li>
        <li class="active">Đặt lại mật khẩu</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <form id="qForm" class="form-horizontal form-label-left" method="post">
                <div class="box">
                    <div class="box-header with-border">
                        <h3 class="box-title">Đặt lại mật khẩu</h3>
                    </div>
                    <div class="box-body">
                        <#include "/admin/message.ftl">
                        <div class="form-group">
                            <label class="col-lg-3 control-label">${view.username} Mật khẩu mới：</label>
                            <div class="col-lg-4">
                                <input type="text" class="input-small form-control" required name="newPassword" placeholder="Mật khẩu mới">
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
})
</script>
</@layout>