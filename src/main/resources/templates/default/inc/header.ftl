<!-- Login dialog BEGIN -->
<div id="login_alert" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document" style="width: 450px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Vui lòng đăng nhập</h4>
            </div>
            <div class="modal-body">
                <form method="POST" action="${base}/login" accept-charset="UTF-8">
                    <div class="form-group">
                        <label class="control-label" for="username">Tài khoản</label>
                        <input class="form-control" id="ajax_login_username" name="username" type="text" required>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="password">Mật khẩu</label>
                        <input class="form-control" id="ajax_login_password" name="password" type="password" required>
                    </div>
                    <div class="form-group">
                        <button id="ajax_login_submit" class="btn btn-success btn-block btn-sm" type="button">
                            Đăng nhập
                        </button>
                    </div>
                    <div class="form-group">
                        <div id="ajax_login_message" class="text-danger"></div>
                    </div>
                    <@controls name="register">
                        <fieldset class="form-group">
			    <#if site.hasValue("weibo_client_id")>
                            <a class="btn btn-default btn-block" href="${base}/oauth/callback/call_weibo">
                                <i class="fa fa-weibo"></i> Đăng nhập tài khoản Weibo
                            </a>
                            </#if>
                            <#if site.hasValue("qq_app_id")>
                            <a class="btn btn-default btn-block" href="${base}/oauth/callback/call_qq">
                                <i class="fa fa-qq"></i> Đăng nhập tài khoản QQ
                            </a>
                            </#if>
                            <#if site.hasValue("github_client_id")>
                            <a class="btn btn-default btn-block" href="${base}/oauth/callback/call_github">
                                <i class="fa fa-github"></i> Đăng nhập tài khoản Github
                            </a>
                            </#if>
                        </fieldset>
                    </@controls>
                </form>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- Login dialog END -->

<!--[if lt IE 9]>
<div class="alert alert-danger alert-dismissible fade in" role="alert" style="margin-bottom:0">
	<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
	<strong>Bạn đang sử dụng một trình duyệt cũ hơn，</strong> Hiệu ứng hiển thị trên trang này có thể khác nhau. Chúng tôi khuyên bạn nên nâng cấp lên
	<a href="http://www.google.cn/intl/zh-CN/chrome/" target="_blank">Chrome</a>
    Hoặc các trình duyệt sau:
	<a href="www.mozilla.org/en-US/firefox/‎" target="_blank">Firefox</a> /
	<a href="http://www.apple.com.cn/safari/" target="_blank">Safari</a> /
	<a href="http://www.opera.com/" target="_blank">Opera</a> /
	<a href="http://windows.microsoft.com/en-us/internet-explorer/download-ie" target="_blank">Internet Explorer 9+</a>
</div>
<![endif]-->

<!-- Fixed navbar -->
<header class="site-header headroom">
    <div class="container" style="width:1200px">
        <nav class="navbar" role="navigation">
            <div class="navbar-header">
                <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${base}/">
                    <img src="<@resource src=options['site_logo']/>"/>
                </a>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
					<#if profile??>
						<li data="user">
							<a href="${base}/users/${profile.id}" nav="user">Trang chủ của tôi</a>
						</li>
					</#if>
					<#list channels as row>
						<li>
							<a href="${base}/channel/${row.id}" nav="${row.name}">${row.name}</a>
						</li>
					</#list>
                        <li>
                            <a href="${base}/tags" nav="tags">Tags</a>
                        </li>
                </ul>
                <ul class="navbar-button list-inline" id="header_user">
                    <li view="search" class="hidden-xs hidden-sm">
                        <form method="GET" action="${base}/search" accept-charset="UTF-8" class="navbar-form navbar-left">
                            <div class="form-group">
                                <input class="form-control search-input mac-style" placeholder="Tìm kiếm" name="kw" type="text" value="${kw}">
                                <button class="search-btn" type="submit"><i class="fa fa-search"></i></button>
                            </div>
                        </form>
                    </li>

				<#if profile??>
                    <@controls name="post">
                        <li>
                            <a href="${base}/post/editing" class="plus"><i class="icon icon-note"></i>Đăng bài</a>
                        </li>
                    </@controls>
                    <li class="dropdown">
                        <a href="#" class="user dropdown-toggle" data-toggle="dropdown">
                            <img class="img-circle" src="<@resource src=profile.avatar + '?t=' + .now?time />">
                            <span>${profile.name}</span>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li>
                                <a href="${base}/users/${profile.id}">Trang chủ của tôi</a>
                            </li>
                            <li>
                                <a href="${base}/settings/profile">Chỉnh sửa thông tin</a>
                            </li>
                            <@shiro.hasPermission name="admin">
                                <li><a href="${base}/admin">Quản lý nền</a></li>
                            </@shiro.hasPermission>
                            <li><a href="${base}/logout">Đăng xuất</a></li>
                        </ul>
                    </li>
				<#else>
                    <li><a href="${base}/login" class="btn btn-default btn-sm signup">Đăng nhập</a></li>
                    <@controls name="register">
                        <li><a href="${base}/register" class="btn btn-primary btn-sm signup">Đăng ký</a></li>
                    </@controls>
				</#if>

                </ul>
            </div>
        </nav>
    </div>
</header>

<script type="text/javascript">
$(function () {
	$('a[nav]').each(function(){  
        $this = $(this);
        if($this[0].href == String(window.location)){  
            $this.closest('li').addClass("active");  
        }  
    });
});
</script>
<!-- Header END -->
