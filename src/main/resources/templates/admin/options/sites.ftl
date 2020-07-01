<form id="qForm" class="form-horizontal" method="post" action="update">
    <div class="form-group">
        <label class="col-sm-2 control-label">Tên trang web</label>
        <div class="col-sm-6">
            <input type="text" name="site_name" class="form-control" value="${options['site_name']}">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">Tên miền</label>
        <div class="col-sm-6">
            <input type="text" name="site_domain" class="form-control" value="${options['site_domain']}" placeholder="Ví dụ: http://blog.hanoidev.net">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">Từ khóa trang web</label>
        <div class="col-sm-6">
            <input type="text" name="site_keywords" class="form-control" value="${options['site_keywords']}">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">Mô tả trang web</label>
        <div class="col-sm-6">
            <input type="text" class="form-control" name="site_description" value="${options['site_description']}" />
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">METAS mở rộng</label>
        <div class="col-sm-6">
            <input type="text" class="form-control" name="site_metas" value="${options['site_metas']}" placeholder="Vui lòng nhập thẻ meta"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">Copyright</label>
        <div class="col-sm-6">
            <input type="text" name="site_copyright" class="form-control" value="${options['site_copyright']}" placeholder="Ví dụ: Copyright © Rin Blog">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">Địa chỉ</label>
        <div class="col-sm-6">
            <input type="text" name="site_icp" class="form-control" value="${options['site_icp']}" placeholder="Ví dụ: Số 7 Hồ tùng mậu">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">Logo</label>
        <div class="col-sm-6">
            <input type="text" name="site_logo" class="form-control" value="${options['site_logo']}" placeholder="Vui lòng nhập logo">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">Favicon</label>
        <div class="col-sm-6">
            <input type="text" name="site_favicon" class="form-control" value="${options['site_favicon']}" placeholder="Vui lòng nhập Favicon">
        </div>
    </div>
    <div class="form-group">
        <label class="col-lg-2 control-label">Tool biên tập</label>
        <div class="col-lg-2">
            <select class="form-control" name="editor" data-select="${options['editor']}">
                <option value="tinymce">tinymce</option>
                <option value="markdown">markdown</option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary">Áp dụng</button>
        </div>
    </div>
</form>