<#include "/classic/inc/layout.ftl"/>

<@layout user.name + "Bình luận">
<div class="row users-show">
    <div class="col-xs-12 col-md-3 side-left">
		<#include "/classic/inc/user_sidebar.ftl"/>
    </div>
    <div class="col-xs-12 col-md-9 side-right">
        <div class="panel panel-default">
            <div class="panel-heading">Nhận xét</div>
            <@user_comments userId=user.id pageNo=pageNo>
                <div class="panel-body">
                    <ul class="list-group">
                        <#list results.content as row>
                            <li class="list-group-item" el="loop-${row.id}">
                                <#if row.post??>
                                    <a href="${base}/post/${row.post.id}" class="remove-padding-left">${row.post.title}</a>
                                <#else>
                                    <a href="javascript:;" class="remove-padding-left">Bài viết đã bị xóa</a>
                                </#if>
                                <span class="meta">
                                    <span class="timeago">${timeAgo(row.created)}</span>
                                </span>

                                <div class="pull-right hidden-xs">
                                    <#if owner>
                                        <a class="act" href="javascript:void(0);" data-evt="trash" data-id="${row.id}" data-toggle="tooltip" title="Xóa bình luận">
                                            <i class="icon icon-close"></i>
                                        </a>
                                    </#if>
                                </div>

                                <div class="reply-body markdown-reply content-body">
                                    <p><i class="icon-bubble"></i> ${row.content}</p>
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
            </@user_comments>
        </div>
    </div>
</div>
<!-- /end -->

<script type="text/javascript">
$(function() {
	$('a[data-evt=trash]').click(function () {
		var id = $(this).attr('data-id');

		layer.confirm('Bạn có chắc chắn muốn xóa mục này?', {
            icon: 3,
            title: 'Thông báo',
            btn: ['Đồng ý','Hủy'],
            shade: false
        }, function(){
			jQuery.getJSON('${base}/comment/delete', {'id':id }, function (ret) {
				layer.msg(ret.message, {icon: 1});
				if (ret.code >=0) {
					var el = $('li[el=loop-' + id + ']');
                    el.remove();
				}
			});
            
        }, function(){
			
        });
	});
})
</script>
</@layout>
