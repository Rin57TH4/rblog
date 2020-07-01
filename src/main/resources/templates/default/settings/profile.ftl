<#include "/default/inc/layout.ftl"/>
<@layout "Sửa đổi thông tin người dùng">

<div class="panel panel-default stacked">
	<div class="panel-heading">
		<ul class="nav nav-pills account-tab">
			<li class="active"><a href="profile">Thông tin cơ bản</a></li>
            <li><a href="email">Sửa đổi hộp thư</a></li>
			<li><a href="avatar">Sửa đổi hình đại diện</a></li>
			<li><a href="password">Thay đổi mật khẩu</a></li>
		</ul>
	</div>
	<div class="panel-body">
		<div id="message">
		<#include "/default/inc/action_message.ftl"/>
		</div>
		<div class="tab-pane active" id="profile">
			<form id="submitForm" action="profile" method="post" class="form-horizontal">
				<div class="form-group">
					<label class="control-label col-lg-3" for="nickname">Biệt danh</label>
					<div class="col-lg-4">
						<input type="text" class="form-control" name="name" value="${view.name}" maxlength="7" required>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-lg-3" for="nickname">Chữ ký cá nhân</label>
					<div class="col-lg-6">
						<textarea name="signature" class="form-control" rows="3" maxlength="128">${view.signature}</textarea>
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
        validate.updateProfile('#submitForm');
    });
</script>
</@layout>