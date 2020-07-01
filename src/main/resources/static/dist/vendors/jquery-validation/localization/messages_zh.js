(function( factory ) {
	if ( typeof define === "function" && define.amd ) {
		define( ["jquery", "../jquery.validate"], factory );
	} else if (typeof module === "object" && module.exports) {
		module.exports = factory( require( "jquery" ) );
	} else {
		factory( jQuery );
	}
}(function( $ ) {


	$.extend( $.validator.messages, {
		required: "Đây là một trường bắt buộc",
		remote: "Vui lòng sửa trường này",
		email: "Vui lòng nhập một địa chỉ email hợp lệ",
		url: "Vui lòng nhập một URL hợp lệ",
		date: "Vui lòng nhập một ngày hợp lệ",
		dateISO: "Vui lòng nhập một ngày hợp lệ (YYYY-MM-DD)",
		number: "Vui lòng nhập một số hợp lệ",
		digits: "Chỉ có thể nhập số",
		creditcard: "Vui lòng nhập số thẻ tín dụng hợp lệ",
		equalTo: "Đầu vào của bạn không giống nhau",
		extension: "Vui lòng nhập một hậu tố hợp lệ",
		maxlength: $.validator.format( "Bạn có thể nhập tối đa {0} ký tự" ),
		minlength: $.validator.format( "Nhập ít nhất {0} ký tự" ),
		rangelength: $.validator.format( "Vui lòng nhập một chuỗi giữa {0} và {1}" ),
		range: $.validator.format( "Vui lòng nhập một giá trị trong khoảng từ {0} đến {1}" ),
		step: $.validator.format( "Vui lòng nhập bội số của {0}" ),
		max: $.validator.format( "Vui lòng nhập một giá trị không lớn hơn {0}" ),
		min: $.validator.format( "Vui lòng nhập giá trị ít nhất {0}" )
	} );
	return $;
}));