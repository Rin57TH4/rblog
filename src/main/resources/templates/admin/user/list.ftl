<#include "/admin/utils/ui.ftl"/>
<@layout>

<section class="content-header">
    <h1>Quản lý người dùng</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">Trang chủ</a></li>
        <li class="active">Quản lý người dùng</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">Danh sách người dùng</h3>
                </div>
                <div class="box-body">
                    <form id="qForm" class="form-inline search-row">
                        <input type="hidden" name="pageNo" value="${page.number + 1}"/>
                        <div class="form-group">
                            <input type="text" name="name" class="form-control" value="${name}" placeholder="Vui lòng nhập từ khóa">
                        </div>
                        <button type="submit" class="btn btn-default">Truy vấn</button>
                    </form>
                    <div class="table-responsive">
                        <table id="dataGrid" class="table table-striped table-bordered">
                            <thead>
                            <tr>
                                <th width="80">#</th>
                                <th>Tên đăng nhập</th>
                                <th>Biệt danh</th>
                                <th>Hộp thư</th>
                                <th>Vai trò</th>
                                <th>Tình trạng</th>
                                <th width="300"></th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list page.content as row>
                                <tr>
                                    <td class="text-center">${row.id}</td>
                                    <td>${row.username}</td>
                                    <td>${row.name}</td>
                                    <td>${row.email}</td>
                                    <td>
                                        <#list row.roles as role>
                                ${role.name}
                                </#list>
                                    </td>
                                    <td>
                                        <#if (row.status == 0)>
                                            <span class="label label-success">Kích hoạt</span>
                                        <#else>
                                            <span class="label label-default">Vô hiệu hóa</span>
                                        </#if>
                                    </td>
                                    <td class="text-center">
                                        <#if row.id != 1>
                                            <#if row.status == 0>
                                                <a href="javascript:void(0);" class="btn btn-xs btn-default" data-id="${row.id}" data-action="close">Đóng</a>
                                            <#else>
                                                <a href="javascript:void(0);" class="btn btn-xs btn-success" data-id="${row.id}" data-action="open">Kích hoạt</a>
                                            </#if>
                                            <a href="${base}/admin/user/pwd?id=${row.id}" class="btn btn-xs btn-success">Thay đổi mật khẩu</a>

                                            <a href="${base}/admin/user/view?id=${row.id}" class="btn btn-xs btn-primary">Sửa đổi vai trò</a>
                                        <#else>
                                            <a href="javascript:void(0);" class="btn btn-xs disabled"><i class="fa fa-check-square-o"></i> Không thể chỉnh sửa</a>
                                        </#if>
                                    </td>
                                </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="box-footer">
                    <@pager "list" page 5 />
                </div>
            </div>
        </div>
    </div>
</section>
<script type="text/javascript">
var J = jQuery;

function ajaxReload(json){
    if(json.code >= 0){
        if(json.message != null && json.message != ''){
			layer.msg(json.message, {icon: 1});
        }
        $('#qForm').submit();
    }else{
		layer.msg(json.message, {icon: 2});
    }
}

$(function() {

    $('#dataGrid a[data-action="close"]').bind('click', function(){
		var that = $(this);
		layer.confirm('Sau khi tài khoản này bị vô hiệu hóa, bạn sẽ không thể đăng nhập vào hệ thống. Bạn có chắc chắn muốn tắt nó không?', {
            icon: 3,
            title: 'Thông báo',
		    btn: ['Đồng ý','Hủy'],
            shade: false
        }, function(){
			 J.getJSON('${base}/admin/user/close', {id: that.attr('data-id'), active: false}, ajaxReload);
        }, function(){
        });
        return false;
    });

    $('#dataGrid a[data-action="open"]').bind('click', function(){
		var that = $(this);
		layer.confirm('Sau khi tài khoản được kích hoạt, bạn sẽ có quyền truy cập vào các chức năng được ủy quyền trong hệ thống. Bạn có chắc chắn muốn kích hoạt không?', {
            icon: 3,
            title: 'Thông báo',
            btn: ['Đồng ý','Hủy'],
            shade: false
        }, function(){
			 J.getJSON('${base}/admin/user/open', {id: that.attr('data-id'), active: true}, ajaxReload);
        }, function(){
        });
        return false;
    });
})
</script>
</@layout>