<#include "/classic/inc/layout.ftl"/>

<@layout "Thông báo">
<div class="row users-show streams">
    <div class="col-xs-12 col-md-3 side-left">
        <#include "/classic/inc/user_sidebar.ftl"/>
    </div>
    <div class="col-xs-12 col-md-9 side-right">
        <div class="panel panel-default">
            <div class="panel-heading">Danh sách thông báo</div>
            <@user_messages userId=user.id pageNo=pageNo>
                <div class="panel-body remove-padding-horizontal">
                    <ul class="list-group topic-list messages">
                        <#list results.content as row>
                            <li class="list-group-item " style="padding: 0 15px;">
                                <a class="reply_count_area hidden-xs pull-right" href="#">
                                    <div class="count_set">
                                        <abbr class="timeago">${timeAgo(row.created)}</abbr>
                                    </div>
                                </a>
                                <div class="avatar pull-left">
                                    <@utils.showAva row.from "media-object img-thumbnail avatar avatar-middle"/>
                                </div>
                                <div class="infos">
                                    <div class="media-heading">
                                    <#--<span class="hidden-xs label label-warning">${row.channel.name}</span>-->
                                        <a href="${base}/users/${row.from.id}">${row.from.name}</a>
                                        <span>
                                            <#if (row.event == 1)>
                                                Yêu thích - <a href="${base}/post/${row.postId}">${row.post.title}</a>
                                            <#elseif (row.event == 3)>
                                                Nhận xét - <a href="${base}/post/${row.postId}">Chi tiết</a>
                                            <#elseif (row.event == 4)>
                                                Bình luận - <a href="${base}/post/${row.postId}">Chi tiết</a>
                                            </#if>
                                        </span>
                                    </div>
                                </div>
                            </li>
                        </#list>

                        <#if results.content?size == 0>
                            <li class="list-group-item ">
                                <div class="infos">
                                    <div>Không có nội dung trong thư mục này!</div>
                                </div>
                            </li>
                        </#if>
                    </ul>
                </div>
                <div class="panel-footer">
                    <@utils.pager request.requestURI!'', results, 5/>
                </div>
            </@user_messages>
        </div>
    </div>
</div>
<!-- /end -->

</@layout>