<form id="qForm" class="form-horizontal" method="post" action="update">
    <div class="form-group">
        <div class="col-md-12">
            <div class="help-block">Nền tảng đăng nhập ba bên chưa được định cấu hình không xuất hiện trên trang đăng nhập</div>
        </div>
    </div>

    <div class="form-group">
        <div class="col-md-12">
            <div class="help-block">Github</div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">Callback</label>
        <div class="col-sm-6">
            <input type="text" name="github_callback" class="form-control" value="${options['github_callback']}" placeholder="Ví dụ: http://{domain}/oauth/callback/github">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">CLIENT ID</label>
        <div class="col-sm-6">
            <input type="text" name="github_client_id" class="form-control" value="${options['github_client_id']}">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">CLIENT SERCRET</label>
        <div class="col-sm-6">
            <input type="text" name="github_secret_key" class="form-control" value="${options['github_secret_key']}">
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary">Áp dụng</button>
        </div>
    </div>
</form>