<#include "/admin/utils/ui.ftl"/>
<@layout>

<section class="content-header">
    <h1>Quản lý chủ đề</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">Trang chủ</a></li>
        <li class="active">Quản lý chủ đề</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">Cài đặt chủ đề</h3>
                    <div class="box-tools">
                        <div class="upload-btn">
                            <label style="width: 80px; margin: 0;">
                                <span>Tải lên chủ đề</span>
                                <input id="upload_btn" type="file" name="file" accept="application/x-zip-compressed" title="Nhấn vào đây để thêm">
                            </label>
                        </div>
                    </div>
                </div>
                <div class="box-body">
                    <ul class="mailbox-attachments clearfix">
                        <#list themes as row>
                        <li>
                            <span class="mailbox-attachment-icon no-padding">
                                <img src="<@resource src=row.previews[0]/>" style="width: 100%; height: 132px;"/>
                            </span>
                            <div class="mailbox-attachment-info">
                                <a href="${row.website}" target="_blank" class="mailbox-attachment-name">
                                    <i class="fa fa-circle-o"></i> ${row.name}
                                </a>
                                <span class="mailbox-attachment-size">
                                    Tác giả: ${row.author!'-'}
                                    <#if options['theme'] == row.name >
                                        <a href="javascript:;" class="pull-right">
                                            <i class="fa fa-lg fa-toggle-on"></i>
                                        </a>
                                    <#else>
                                        <a href="javascript:;" class="pull-right" data-action="toggle" data-value="${row.name}">
                                            <i class="fa fa-lg fa-toggle-off"></i>
                                        </a>
                                    </#if>
                                </span>
                            </div>
                        </li>
                        </#list>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</section>
<script type="text/javascript">
    $(function () {
        $('a[data-action="toggle"]').click(function () {
            var that = $(this);
            var value = that.data('value');
            $.getJSON('${base}/admin/theme/active', {'theme': value}, function (json) {
                layer.alert(json.message,{icon: 1,title:'Thông báo',btn:["OK"]}, function(){
                    location.reload();
                });
            })
        });

        $('#upload_btn').change(function(){
            $(this).upload('${base}/admin/theme/upload', function(json){
                layer.alert(json.message, {icon: 1,title:'Thông báo',btn:["OK"]}, function(){
                    location.reload();
                });
            });
        });
    })
</script>
</@layout>
