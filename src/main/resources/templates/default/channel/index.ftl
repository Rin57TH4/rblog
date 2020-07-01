<#include "/default/inc/layout.ftl"/>
<@layout channel.name>
    <div class="row streams">
        <div class="col-xs-12 col-md-9 side-left">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <ul class="list-inline topic-filter">
                        <li data-toggle="tooltip" title="Sắp xếp theo thời gian phát hành">
                            <a href="?order=newest" <#if order == 'newest'> class="active" </#if>>Gần đây</a>
                        </li>
                        <li data-toggle="tooltip" title="Sắp xếp Thích">
                            <a href="?order=favors" <#if order == 'favors'> class="active" </#if>>Bỏ phiếu</a>
                        </li>
                        <li data-toggle="tooltip" title="Sắp xếp đánh giá">
                            <a href="?order=hottest" <#if order == 'hottest'> class="active" </#if>>Nóng</a>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                </div>
                <@contents channelId=channel.id pageNo=pageNo order=order>

                    <div class="panel-body remove-padding-horizontal">

                        <ul class="list-group row topic-list">
                            <#list results.content as row>
                                <li class="list-group-item ">
                                    <a class="reply_count_area hidden-xs pull-right" href="#">
                                        <div class="count_set">
                                            <span class="count_of_votes" data-toggle="tooltip" title="Đọc">${row.views}</span>
                                            <span class="count_seperator">/</span>
                                            <span class="count_of_replies" data-toggle="tooltip" title="Bình luận">${row.comments}</span>
                                            <span class="count_seperator">/</span>
                                            <span class="count_of_visits" data-toggle="tooltip" title="Thích">${row.favors}</span>
                                            <span class="count_seperator">|</span>
                                            <abbr class="timeago">${timeAgo(row.created)}</abbr>
                                        </div>
                                    </a>
                                    <div class="avatar pull-left">
                                        <@utils.showAva row.author "media-object img-thumbnail avatar avatar-middle"/>
                                    </div>
                                    <div class="infos">
                                        <div class="media-heading">
                                            <@utils.showChannel row/><a href="${base}/post/${row.id}">${row.title}</a>
                                        </div>
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

                    <div class="panel-footer text-right remove-padding-horizontal pager-footer">
                        <!-- Pager -->
                        <@utils.pager request.requestURI!"", results, 5/>
                    </div>
                </@contents>
            </div>

        </div>

        <div class="col-xs-12 col-md-3 side-right">
            <#include "/default/inc/right.ftl" />
        </div>

    </div>

</@layout>

