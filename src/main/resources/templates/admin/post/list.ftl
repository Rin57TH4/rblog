<#include "/admin/utils/ui.ftl"/>
<@layout>

<section class="content-header">
    <h1>Quản lý bài viết</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">Trang chủ</a></li>
        <li class="active">Quản lý bài viết</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">Danh sách bài viết</h3>
                    <div class="box-tools">
                        <a class="btn btn-default btn-sm" href="${base}/admin/post/view">Mới</a>
                        <a class="btn btn-default btn-sm" href="javascrit:;" data-action="batch_del">Xóa hàng loạt</a>
                    </div>
                </div>
                <div class="box-body">
                    <form id="qForm" class="form-inline search-row">
                        <input type="hidden" name="pageNo" value="${page.number + 1}"/>
                        <div class="form-group">
                            <select class="form-control" name="channelId" data-select="${channelId}">
                                <option value="0">Truy vấn tất cả các phần</option>
                                <#list channels as row>
                                    <option value="${row.id}">${row.name}</option>
                                </#list>
                            </select>
                        </div>
                        <div class="form-group">
                            <input type="text" name="title" class="form-control" value="${title}" placeholder="Vui lòng nhập một từ khóa tiêu đề">
                        </div>
                        <button type="submit" class="btn btn-default">Truy vấn</button>
                    </form>
                    <div class="table-responsive">
                        <table id="dataGrid" class="table table-striped table-bordered">
                            <thead>
                            <tr>
                                <th width="30"><input type="checkbox" class="checkall"></th>
                                <th width="80">#</th>
                                <th>Tiêu đề</th>
                                <th width="120">Tác giả</th>
                                <th width="100">Ngày đăng</th>
                                <th width="80">Truy cập</th>
                                <th width="80">Tình trạng</th>
                                <th width="80">Xuất bản</th>
                                <th width="180">Hoạt động</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list page.content as row>
                                <tr>
                                    <td>
                                        <input type="checkbox" name="id" value="${row.id}">
                                    </td>
                                    <td>
                                        <img src="<@resource src=row.thumbnail/>" style="width: 80px;">
                                    </td>
                                    <td>
                                        <a href="${base}/post/${row.id}" target="_blank">${row.title}</a>
                                    </td>
                                    <td>${row.author.username}</td>
                                    <td>${row.created?string('yyyy-MM-dd')}</td>
                                    <td><span class="label label-default">${row.views}</span></td>
                                    <td>
                                        <#if (row.featured > 0)>
                                            <span class="label label-danger">Đề xuất</span>
                                        </#if>
                                        <#if (row.weight > 0)>
                                            <span class="label label-warning">Top</span>
                                        </#if>
                                    </td>
                                    <td>
                                        <#if (row.status = 0)>
                                            <span class="label label-default">Đã xuất bản</span>
                                        </#if>
                                        <#if (row.status = 1)>
                                            <span class="label label-warning">Dự thảo</span>
                                        </#if>
                                    </td>
                                    <td>
                                        <#if (row.featured == 0)>
                                            <a href="javascript:void(0);" class="btn btn-xs btn-default" data-id="${row.id}" rel="featured">Đề xuất</a>
                                        <#else>
                                            <a href="javascript:void(0);" class="btn btn-xs btn-danger" data-id="${row.id}" rel="unfeatured">Đề nghị</a>
                                        </#if>

                                        <#if (row.weight == 0)>
                                            <a href="javascript:void(0);" class="btn btn-xs btn-default" data-id="${row.id}" rel="weight">Top</a>
                                        <#else>
                                            <a href="javascript:void(0);" class="btn btn-xs btn-warning" data-id="${row.id}" rel="unweight">Down</a>
                                        </#if>

                                        <a href="${base}/admin/post/view?id=${row.id}" class="btn btn-xs btn-info">Sửa</a>
                                        <a href="javascript:void(0);" class="btn btn-xs btn-primary" data-id="${row.id}" rel="delete">Xóa</a>
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

function doDelete(ids) {
	J.getJSON('${base}/admin/post/delete', J.param({'id': ids}, true), ajaxReload);
}

function doUpdateFeatured(id, featured) {
    J.getJSON('${base}/admin/post/featured', J.param({'id': id, 'featured': featured}, true), ajaxReload);
}

function doUpdateWeight(id, weight) {
    J.getJSON('${base}/admin/post/weight', J.param({'id': id, 'weight': weight}, true), ajaxReload);
}

$(function() {
    $('#dataGrid a[rel="delete"]').bind('click', function(){
        var that = $(this);
		layer.confirm('Bạn có chắc chắn muốn xóa mục này?', {
            icon: 3,
            title: 'Thông báo',
            btn: ['Đồng ý','Hủy'],
            shade: false
        }, function(){
			doDelete(that.attr('data-id'));
        }, function(){
        });
        return false;
    });


    $('#dataGrid a[rel="featured"]').bind('click', function(){
        var that = $(this);
        layer.confirm('Bạn có chắc chắn về khuyến nghị?',{
            icon: 3,
            title: 'Thông báo',
            btn: ['Đồng ý','Hủy'],
            shade: false
        }, function(){
            doUpdateFeatured(that.attr('data-id'), 1);
        }, function(){
        });
        return false;
    });


    $('#dataGrid a[rel="unfeatured"]').bind('click', function(){
        var that = $(this);
        layer.confirm('Bạn có chắc chắn để hủy bỏ?', {
            icon: 3,
            title: 'Thông báo',
            btn: ['Đồng ý','Hủy'],
            shade: false
        }, function(){
            doUpdateFeatured(that.attr('data-id'), 0);
        }, function(){
        });
        return false;
    });

    $('#dataGrid a[rel="weight"]').bind('click', function(){
        var that = $(this);
        layer.confirm('Bạn có chắc chắn để ghim mặt hàng này', {
            icon: 3,
            title: 'Thông báo',
            btn: ['Đồng ý','Hủy'],
            shade: false
        }, function(){
            doUpdateWeight(that.attr('data-id'), 1);
        }, function(){
        });
        return false;
    });


    $('#dataGrid a[rel="unweight"]').bind('click', function(){
        var that = $(this);
        layer.confirm('Bạn có chắc chắn để hủy bỏ?' , {
            icon: 3,
            title: 'Thông báo',
            btn: ['Đồng ý','Hủy'],
            shade: false
        }, function(){
            doUpdateWeight(that.attr('data-id'), 0);
        }, function(){
        });
        return false;
    });

    $('a[data-action="batch_del"]').click(function () {
		var check_length=$("input[type=checkbox][name=id]:checked").length;

		if (check_length == 0) {
			layer.msg("Vui lòng chọn ít nhất một ", {icon: 2, title: 'Thông báo'});
			return false;
		}

		var ids = [];
		$("input[type=checkbox][name=id]:checked").each(function(){
			ids.push($(this).val());
		});

		layer.confirm('Bạn có chắc chắn muốn xóa mục này?', {
            icon: 3,
            title: 'Thông báo',
            btn: ['Đồng ý','Hủy'],
            shade: false
        }, function(){
			doDelete(ids);
        }, function(){
        });
    });
})
</script>
</@layout>
