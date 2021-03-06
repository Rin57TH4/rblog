<#include "/default/inc/layout.ftl"/>
<@layout "Danh sách các thẻ">
    <div class="row">
        <div class="col-xs-12 col-md-9 side-left">
            <div class="panel panel-default">
                <div class="panel-body streams-tags">
                    <#list results.content as row>
                        <#assign post = row.post />
                        <div class="col-sm-6 row-item">
                            <h2 class="title">
                                <a href="${base}/tag/${row.name}/"><i class="fa fa-quote-left"></i> ${row.name}</a>
                                <span class="label label-default">${row.posts}</span>
                            </h2>
                            <#if post??>
                                <div class="media">
                                    <div class="media-left">
                                        <@utils.showAva post.author "media-object"/>
                                    </div>
                                    <div class="media-body">
                                        <h4 class="media-heading">
                                            <a href="${base}/post/${post.id}">${post.title?html}</a>
                                        </h4>
                                    </div>
                                </div>
                            </#if>
                        </div>
                    </#list>
                    <#if  results.content?size == 0>
                    <li class="ajax-load-con content">
                        <div class="content-box posts-aside">
                            <div class="posts-default-content">Không có nội dung trong thư mục này!</div>
                        </div>
                    </li>
                    </#if>
                </div>
            </div>

            <!-- Pager -->
            <div class="text-center">
                <@utils.pager request.requestURI!"", results, 5/>
            </div>
        </div>

        <div class="col-xs-12 col-md-3 side-right">
            <#include "/default/inc/right.ftl" />
        </div>

    </div>

</@layout>

