<#include "/default/inc/layout.ftl"/>

<@layout "Đăng ký">
<div class="row">
    <div class="col-md-4 col-md-offset-4 floating-box">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Đăng ký</h3>
            </div>
            <div class="panel-body">
                <#include "/default/inc/action_message.ftl"/>
                <div id="message">
                </div>
                <form id="submitForm" method="POST" action="register" accept-charset="UTF-8">
                    <div class="form-group ">
                        <label class="control-label" for="username">Tên đăng nhập</label>
                        <input class="form-control" id="username" name="username" type="text" placeholder="Chữ cái và số, không ít hơn 5 chữ số" required>
                    </div>
                    <@controls name="register_email_validate">
                        <div class="form-group">
                            <label class="control-label" for="username">Username</label>
                            <div class="input-group">
                                <input type="text" class="form-control" name="email" maxlength="64" placeholder="Vui lòng nhập địa chỉ email của bạn" required>
                                <span class="input-group-btn">
                                    <button class="btn btn-success" type="button" id="sendCode">Nhận mã xác minh</button>
                                </span>
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="control-label" for="code">Mã xác minh</label>
                            <input class="form-control" id="code" name="code" type="text" placeholder="Vui lòng nhập mã xác minh email của bạn" maxlength="6" required>
                        </div>
                    </@controls>
                    <div class="form-group ">
                        <label class="control-label" for="username">Mật khẩu</label>
                        <input class="form-control" id="password" name="password" type="password" maxlength="18" placeholder="Vui lòng nhập mật khẩu" required>
                    </div>
                    <div class="form-group ">
                        <label class="control-label" for="username">Xác nhận mật khẩu</label>
                        <input class="form-control" id="password2" name="password2" type="password" placeholder="Vui lòng nhập lại mật khẩu" maxlength="18">
                    </div>
                    <button type="submit" class="btn btn-success btn-block">
                        Gửi
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    seajs.use('validate', function (validate) {
        validate.register('#submitForm', '#sendCode');
    });
</script>

</@layout>