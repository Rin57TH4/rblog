<form id="qForm" class="form-horizontal" method="post" action="update">
    <div class="form-group">
        <label class="col-sm-2 control-label">Địa chỉ SMTP</label>
        <div class="col-sm-6">
            <input type="text" name="mail_smtp_host" class="form-control" value="${options['mail_smtp_host']}" placeholder="smtp.rin-blog.com">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">Tài khoản email</label>
        <div class="col-sm-6">
            <input type="text" name="mail_smtp_username" class="form-control" value="${options['mail_smtp_username']}" placeholder="Vui lòng nhập email">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">Mật khẩu email</label>
        <div class="col-sm-6">
            <input type="text" name="mail_smtp_password" class="form-control" value="${options['mail_smtp_password']}" placeholder="Vui lòng nhập mật khẩu email">
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary">Áp dụng</button>
        </div>
    </div>
</form>