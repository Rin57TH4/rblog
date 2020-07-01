<#include "/admin/utils/ui.ftl"/>
<@layout>

<section class="content-header">
    <h1>Cấu hình hệ thống</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">Trang chủ</a></li>
        <li class="active">Cấu hình hệ thống</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <#include "/admin/message.ftl">
            <div class="nav-tabs-custom">
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#sites" data-toggle="tab" aria-expanded="true">Thông tin trang web</a></li>
                    <li class=""><a href="#mail" data-toggle="tab" aria-expanded="false">Dịch vụ thư</a></li>
                    <li class=""><a href="#oauth" data-toggle="tab" aria-expanded="false">Đăng nhập của bên thứ ba</a></li>
                    <li class=""><a href="#storage" data-toggle="tab" aria-expanded="false">Lưu trữ hình ảnh</a></li>
                    <li class="pull-right header"><i class="fa fa-cogs"></i></li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane active" id="sites">
                        <#include "/admin/options/sites.ftl">
                    </div>
                    <div class="tab-pane" id="mail">
                        <#include "/admin/options/mail.ftl">
                    </div>
                    <div class="tab-pane" id="oauth">
                        <#include "/admin/options/oauths.ftl">
                    </div>
                    <div class="tab-pane" id="storage">
                        <#include "/admin/options/storages.ftl">
                    </div>
                </div>
                <!-- /.tab-content -->
            </div>
        </div>
    </div>
</section>
<script type="text/javascript">
$(function() {
})
</script>
</@layout>