<#include "/default/inc/layout.ftl"/>
<@layout "Sửa đổi thông tin người dùng">

<div class="panel panel-default stacked">
	<div class="panel-heading">
		<ul class="nav nav-pills account-tab">
			<li><a href="profile">Thông tin cơ bản</a></li>
            <li><a href="email">Sửa đổi hộp thư</a></li>
			<li class="active"><a href="avatar">Sửa đổi hình đại diện</a></li>
			<li><a href="password">Thay đổi mật khẩu</a></li>
		</ul>
	</div>
	<div class="panel-body">
		<div id="message">
			<#include "/default/inc/action_message.ftl"/>
		</div>
			<div class="upload-btn">
				<label>
					<span>Nhấn vào đây để chọn một hình ảnh</span>
					<input id="upload_btn" type="file" name="file" accept="image/*" title="Nhấn vào đây để thêm hình ảnh">
				</label>
			</div>
			<div class="update_ava">
				<img src="<@resource src=profile.avatar/>" id="target" alt="[Example]" />
			</div>
	</div><!-- /panel-content -->
</div><!-- /panel -->

<script type="text/javascript">
    seajs.use('avatar');
</script>
</@layout>