<#include "/classic/inc/layout.ftl"/>

<@layout "Thẻ:" + kw>

<div class="row streams">
    <div class="col-xs-12 col-md-9 side-left">
        <div class="posts ">
            <ul class="posts-list">
                <li class="content">
                    <div class="content-box posts-aside">
                        <div class="posts-item">Thẻ: ${name} tổng cộng ${results.totalElements} kết quả.</div>
                    </div>
                </li>
                <#include "/classic/inc/posts_item.ftl"/>
                <#list results.content as row>
                    <@posts_item row.post/>
                </#list>
                <#if !results?? || results.content?size == 0>
                    <li class="content">
                        <div class="content-box posts-aside">
                            <div class="posts-item">Không có nội dung trong thư mục này!</div>
                        </div>
                    </li>
                </#if>
            </ul>
        </div>
        <div class="text-center">
            <@utils.pager request.requestURI, results, 5/>
        </div>
    </div>
    <div class="col-xs-12 col-md-3 side-right">
		<#include "/classic/inc/right.ftl" />
    </div>
</div>
</@layout>