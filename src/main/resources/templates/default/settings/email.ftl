<#include "/default/inc/layout.ftl"/>
<@layout "Sửa đổi hộp thư">

<div class="panel panel-default stacked">
	<div class="panel-heading">
		<ul class="nav nav-pills account-tab">
			<li><a href="profile">Thông tin cơ bản</a></li>
            <li class="active"><a href="email">Sửa đổi hộp thư</a></li>
			<li><a href="avatar">Sửa đổi hình đại diện</a></li>
			<li><a href="password">Thay đổi mật khẩu</a></li>
		</ul>
	</div>
	<div class="panel-body">
		<div id="message">
			<#include "/default/inc/action_message.ftl"/>
		</div>
		<div class="tab-pane active" id="profile">
			<form id="submitForm" action="email" method="post" class="form-horizontal">
				<div class="form-group">
					<label class="control-label col-lg-3" for="email">Địa chỉ email</label>
					<div class="col-lg-4">
						<div class="input-group">
							<input type="text" class="form-control" name="email" value="${profile.email}" maxlength="64" required>
                            <span class="input-group-btn">
								<button class="btn btn-primary" type="button" id="sendCode">Nhận mã xác minh</button>
						  	</span>
						</div>
					</div>
				</div>
                <div class="form-group">
                    <label class="control-label col-lg-3">Mã xác minh</label>
                    <div class="col-lg-4">
                        <input type="text" class="form-control" name="code" maxlength="6" required>
                    </div>
                </div>
				<div class="form-group">
					<div class="text-center">
						<button type="submit" class="btn btn-primary">Gửi</button>
					</div>
				</div><!-- /form-actions -->
			</form>
		</div>
	</div><!-- /panel-content -->
</div><!-- /panel -->

<script type="text/javascript">
    seajs.use('validate', function (validate) {
        validate.updateEmail('#submitForm', '#sendCode');
    });
</script>
</@layout>