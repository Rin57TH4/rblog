<#include "/admin/utils/ui.ftl"/>
<@layout>

<section class="content-header">
    <h1>Sửa đổi vai trò</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">Trang chủ</a></li>
        <li><a href="${base}/admin/user/list">Quản lý người dùng</a></li>
        <li class="active">Sửa đổi vai trò</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <form id="qForm" class="form-horizontal form-label-left" method="post" action="update_role">
                <div class="box">
                    <div class="box-header with-border">
                        <h3 class="box-title">Sửa đổi vai trò</h3>
                    </div>
                    <div class="box-body">
                        <#include "/admin/message.ftl">
                        <input type="hidden" name="id" value="${view.id}" />

                        <div class="form-group">
                            <label class="col-sm-2 control-label">Tên đăng nhập</label>
                            <div class="col-sm-8">
                                <input class="form-control" type="text" value="${view.username}" disabled style="width:200px;">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Vai trò</label>
                            <div class="col-sm-8">
                                <#list roles as role>
                                    <#assign hasRole ="false">
                                    <label class="checkbox-inline">
                                        <#list view.roles as userRole>
                                            <#if userRole.id == role.id>
                                                <#assign hasRole ="true">
                                                <#break>
                                            </#if>
                                        </#list>
                                        <#if hasRole == "true">
                                            <input type="checkbox" name="roleIds" value="${role.id}" checked="checked"> ${role.name}
                                        <#else>
                                            <input type="checkbox" name="roleIds" value="${role.id}"> ${role.name}
                                        </#if>
                                    </label>
                                </#list>
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