<#include "/admin/utils/ui.ftl"/>
<@layout>

<section class="content-header">
    <h1>Quản lý vai trò</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">Trang chủ</a></li>
        <li class="active">Quản lý vai trò</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">Danh sách vai trò</h3>
                    <div class="box-tools">
                        <a class="btn btn-default btn-sm" href="${base}/admin/role/view">Thêm vai trò</a>
                    </div>
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
                        <table id="dataGrid" class="table table-striped table-bordered text-small">
                            <thead>
                            <tr>
                                <th width="80" style="text-align: center">#</th>
                                <th>Tên đăng nhập</th>
                                <th width="300" style="text-align: center">Hoạt động</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list page.content as row>
                                <tr>
                                    <td class="text-center">${row.id}</td>
                                    <td>${row.name}</td>
                                    <td class="text-center">
                                        <#if row.id != 1>
                                            <a href="${base}/admin/role/view?id=${row.id}" class="btn btn-xs btn-success">
                                                <i class="fa fa-check-square-o"></i> Sửa đổi
                                            </a>
                                            <a href="javascript:void(0);" class="btn btn-xs btn-primary" data-id="${row.id}"
                                               data-action="delete">
                                                <i class="fa fa-bitbucket"></i> Xóa
                                            </a>
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

    function ajaxReload(json) {
        if (json.code >= 0) {
            if (json.message != null && json.message != '') {
                layer.msg(json.message, {icon: 1});
            }
            $('#qForm').submit();
        } else {
            layer.msg(json.message, {icon: 2});
        }
    }

    function doDelete(ids) {
        J.getJSON('${base}/admin/role/delete', J.param({'id': ids}, true), ajaxReload);
    }

    $(function () {

        $('#dataGrid a[data-action="delete"]').bind('click', function () {
            var that = $(this);
            layer.confirm('Bạn có chắc chắn muốn xóa mục này?', {
                icon: 3,
                title: 'Thông báo',
                btn: ['Đồng ý','Hủy'],
                shade: false
            }, function () {
                doDelete(that.attr('data-id'));
            }, function () {
            });
            return false;
        });
    })
</script>
</@layout>