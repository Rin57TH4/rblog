<#include "/admin/utils/ui.ftl"/>
<@layout>

<section class="content-header">
    <h1>Quản lý kênh</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">Trang chủ</a></li>
        <li class="active">Quản lý kênh</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">Danh sách kênh</h3>
                    <div class="box-tools">
                        <a class="btn btn-default btn-sm" href="${base}/admin/channel/view">Thêm kênh</a>
                    </div>
                </div>
                <div class="box-body">
                    <div class="table-responsive">
                        <table id="dataGrid" class="table table-striped table-bordered">
                            <thead>
                            <tr>
                                <th width="80">#</th>
                                <th>Tên</th>
                                <th>Key</th>
                                <th>Tình trạng</th>
                                <th width="140">Hoạt động</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list list as row>
                                <tr>
                                    <td>${row.id}</td>
                                    <td>${row.name}</td>
                                    <td>${row.key}</td>
                                    <td>
                                        <#if (row.status == 0)>
                                            Hiển thị
                                        <#else>
                                            Ẩn
                                        </#if>
                                    </td>
                                    <td>
                                        <a href="javascript:void(0);" class="btn btn-xs btn-default" data-id="${row.id}" data-action="weight">Top</a>
                                        <a href="view?id=${row.id}" class="btn btn-xs btn-success">Sửa đổi</a>
                                        <a href="javascript:void(0);" class="btn btn-xs btn-primary" data-id="${row.id}"
                                           data-action="delete">Xóa</a>
                                    </td>
                                </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div>
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
            window.location.reload();
        } else {
            layer.msg(json.message, {icon: 2});
        }
    }

    function doUpdateWeight(id, weight) {
        J.getJSON('${base}/admin/channel/weight', J.param({'id': id, 'weight': weight}, true), ajaxReload);
    }

    $(function () {
        $('#dataGrid a[data-action="weight"]').bind('click', function(){
            var that = $(this);
            layer.confirm('Bạn có chắc chắn muốn xếp hạng mục đầu tiên?', {
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


        $('#dataGrid a[data-action="delete"]').bind('click', function () {
            var that = $(this);

            layer.confirm('Bạn có chắc chắn muốn xếp hạng mục đầu tiên?',{
                icon: 3,
                title: 'Thông báo',
                btn: ['Đồng ý','Hủy'],
                shade: false
            }, function () {
                J.getJSON('${base}/admin/channel/delete', {id: that.attr('data-id')}, ajaxReload);
            }, function () {
            });
            return false;
        });

    })
</script>
</@layout>