<#include "/classic/inc/layout.ftl"/>

<@layout user.name + "Bài viết">
<div class="row users-show">
    <div class="col-xs-12 col-md-3 side-left">
		<#include "/classic/inc/user_sidebar.ftl"/>
    </div>
    <div class="col-xs-12 col-md-9 side-right">
        <div class="panel panel-default">
            <div class="panel-heading">Bài viết dự thảo</div>
            <@user_contents userId=user.id pageNo=pageNo status=1>
                <div class="panel-body">
                    <ul class="list-group">
                        <#list results.content as row>
                            <li class="list-group-item" el="loop-${row.id}">
                                <a href="${base}/post/${row.id}" class="remove-padding-left">${row.title}</a>
                                <span class="meta">
                                     Đã tạo <span class="timeago">${timeAgo(row.created)}</span>
                                </span>

                                <div class="pull-right hidden-xs">
                                    <#if owner>
                                        <a class="act_edit" href="javascript:void(0);" data-evt="edit" data-id="${row.id}" data-toggle="tooltip" title="Chỉnh sửa bài viết">
                                            <i class="icon icon-note"></i>
                                        </a>
                                        <a class="act_delete" href="javascript:void(0);" data-evt="trash" data-id="${row.id}" data-toggle="tooltip" title="Xóa bài viết">
                                            <i class="icon icon-close"></i>
                                        </a>
                                    </#if>
                                </div>
                            </li>
                        </#list>

                        <#if results.content?size == 0>
                            <li class="list-group-item ">
                                <div class="infos">
                                    <div class="media-heading">Không có nội dung trong thư mục này!</div>
                                </div>
                            </li>
                        </#if>
                    </ul>
                </div>
                <div class="panel-footer">
                    <@utils.pager request.requestURI!'', results, 5/>
                </div>
            </@user_contents>
        </div>
    </div>
</div>
<!-- /end -->

<script type="text/javascript">
$(function() {
	// delete
	$('a[data-evt=trash]').click(function () {
		var id = $(this).attr('data-id');

		layer.confirm('Bạn có chắc chắn muốn xóa mục này?', {
            icon: 3,
            title: 'Thông báo',
            btn: ['Đồng ý','Hủy'],
            shade: false
        }, function(){
			jQuery.getJSON('${base}/post/delete/' + id, function (ret) {
				layer.msg(ret.message, {icon: 1});
				if (ret.code >=0) {
					location.reload();
				}
			});

        }, function(){

        });
	});
	
	// edit
	$('a[data-evt=edit]').click(function () {
		var id = $(this).attr('data-id');
		window.location.href='${base}/post/editing?id=' + id;
	});
})
</script>
</@layout>