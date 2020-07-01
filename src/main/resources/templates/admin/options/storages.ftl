<form id="qForm" class="form-horizontal" method="post" action="update">
    <div class="form-group">
        <label class="col-sm-2 control-label">Phương pháp lưu trữ</label>
        <div class="col-sm-3">
            <select class="form-control" name="storage_scheme" data-select="${options['storage_scheme']}">
                <option value="native">Lưu trữ cục bộ</option>
            </select>
        </div>
    </div>
<#--    <div class="scheme" data-scheme="upyun">-->
<#--        <div class="form-group">-->
<#--            <label class="col-sm-2 control-label">Tên không gian</label>-->
<#--            <div class="col-sm-6">-->
<#--                <input type="text" name="upyun_oss_bucket" class="form-control" value="${options['upyun_oss_bucket']}" placeholder="Bắn tên đám mây lần nữa">-->
<#--            </div>-->
<#--        </div>-->
<#--        <div class="form-group">-->
<#--            <label class="col-sm-2 control-label">Tên nhà khai thác</label>-->
<#--            <div class="col-sm-6">-->
<#--                <input type="text" name="upyun_oss_operator" class="form-control" value="${options['upyun_oss_operator']}" placeholder="Toán tử đám mây một lần nữa">-->
<#--            </div>-->
<#--        </div>-->
<#--        <div class="form-group">-->
<#--            <label class="col-sm-2 control-label">Mật khẩu khai thác</label>-->
<#--            <div class="col-sm-6">-->
<#--                <input type="text" name="upyun_oss_password" class="form-control" value="${options['upyun_oss_password']}" placeholder="Toán tử đám mây một lần nữa password">-->
<#--            </div>-->
<#--        </div>-->
<#--        <div class="form-group">-->
<#--            <label class="col-sm-2 control-label">Liên kết tên miền</label>-->
<#--            <div class="col-sm-6">-->
<#--                <input type="text" name="upyun_oss_domain" class="form-control" value="${options['upyun_oss_domain']}" placeholder="Ví dụ: http://b0.upaiyun.com">-->
<#--            </div>-->
<#--        </div>-->
<#--        <div class="form-group">-->
<#--            <label class="col-sm-2 control-label">Thư mục tập tin</label>-->
<#--            <div class="col-sm-6">-->
<#--                <input type="text" name="upyun_oss_src" class="form-control" value="${options['upyun_oss_src']}" placeholder="Ví dụ: /static/">-->
<#--            </div>-->
<#--        </div>-->
<#--    </div>-->
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary">Áp dụng</button>
        </div>
    </div>
</form>
<script>
    $(function () {
        $('select[name=storage_scheme]').change(function () {
            var value = $(this).val();
            $('.scheme').each(function () {
                if ($(this).data('scheme') === value) {
                    $(this).show();
                } else {
                    $(this).hide();
                }
            });
        }).trigger('change');
    });
</script>