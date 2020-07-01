<#include "/admin/utils/ui.ftl"/>
<@layout>

<section class="content-header">
    <h1>Quản lý bình luận</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">Trang chủ</a></li>
        <li class="active">Quản lý bình luận</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">Danh sách bình luận</h3>
                    <div class="box-tools">
                        <a class="btn btn-default btn-sm" href="javascrit:;" data-action="batch_del">Xóa hàng loạt</a>
                    </div>
                </div>
                <div class="box-body">
                    <form id="qForm" class="form-inline">
                        <input type="hidden" name="pageNo" value="${page.number + 1}"/>
                    </form>
                    <div class="table-responsive">
                        <table id="dataGrid" class="table table-striped table-bordered">
                            <thead>
                            <tr>
                                <th width="50"><input type="checkbox" class="checkall"></th>
                                <th width="80">#</th>
                                <th>Nội dung</th>
                                <th>Id mục tiêu</th>
                                <th>Tác giả</th>
                                <th>Tác giả</th>
                                <th width="50">Hoạt động</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list page.content as row>
                                <tr>
                                    <td>
                                        <input type="checkbox" name="id" value="${row.id}">
                                    </td>
                                    <td>${row.id}</td>
                                    <td>${row.content}</td>
                                    <td>${row.toId}</td>
                                    <td>${row.author.username}</td>
                                    <td>${row.created?string('yyyy-MM-dd')}</td>
                                    <td>
                                        <a href="javascript:void(0);" class="btn btn-xs btn-primary" data-id="${row.id}"
                                           data-action="delete">Xóa
                                        </a>
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
        J.getJSON('${base}/admin/comment/delete', J.param({'id': ids}, true), ajaxReload);
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

        $('a[data-action="batch_del"]').click(function () {
            var check_length = $("input[type=checkbox][name=id]:checked").length;

            if (check_length == 0) {
                layer.msg("Vui lòng chọn ít nhất một ", {icon: 2,title: 'Thông báo'});
                return false;
            }

            var ids = [];
            $("input[type=checkbox][name=id]:checked").each(function () {
                ids.push($(this).val());
            });

            layer.confirm('Bạn có chắc chắn muốn xóa mục này?', {
                icon: 3,
                title: 'Thông báo',
                btn: ['Đồng ý','Hủy'],
                shade: false
            }, function () {
                doDelete(ids);
            }, function () {
            });
        });
    })
</script>
</@layout>