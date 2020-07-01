<#include "/default/inc/layout.ftl"/>

<@layout "Thông báo nhắc">

<div class="panel panel-default" style="min-height: 300px; max-width: 460px; margin: 30px auto;">
	<div class="panel-heading">Thông báo</div>
	<div class="panel-body">
		<fieldset>
			<#if error??>
				${error}
			</#if>
		</fieldset>
	</div><!-- /panel-content -->
</div><!-- /panel -->
</@layout>