define(function(require, exports, module) {
	J = jQuery;
	require('tagsinput');

	var PostView = function () {};
	
	PostView.prototype = {
        name : 'PostView',
        init : function () {
        	this.bindEvents();
        },
        defaults: {
        	type : 'text',
        	defaultEditor: 'ueditor',
        	maxFiles : 6,
        },
        bindEvents : function () {
        	var that = this;

        	that.bindTagit();
        	that.bindValidate();
        	that.bindUpload();

            $('button[event="post_submit"]').click(function () {
                var status = $(this).data('status');
                $("input[name='status']").val(status);
                $("#submitForm").submit();
            });
        },
        
        bindTagit : function () {
            $('#tags').tagsinput({
                maxTags: 4,
                trimValue: true
            });
        },
        
        bindUpload : function () {
            $('#upload_btn').change(function(){
                $(this).upload(RIN_BLOG.BASE_PATH + '/post/upload?crop=thumbnail_post_size', function(data){
                    if (data.status == 200) {
                        var path = data.path;
                        $("#thumbnail_image").css("background", "url(" + path + ") no-repeat scroll center 0 rgba(0, 0, 0, 0)");
                        $("#thumbnail").val(path);
                    }
                });
            });
        },

        bindValidate: function () {
            require.async(['validation'], function () {
                require.async(['validation-additional'], function () {
                    $("#submitForm").submit(function () {
                        if (typeof tinyMCE == "function") {
                            tinyMCE.triggerSave();
                        }
                    }).validate({
                        ignore: "",
                        rules: {
                            title: 'required',
                            channelId: 'required',
                            content: {
                                required: true,
                                check_editor: true
                            }
                        },
                        messages: {
                            title: 'Vui lòng nhập một tiêu đề',
                            channelId: 'Vui lòng chọn một kênh',
                            content: {
                                required: 'Nội dung không thể để trống',
                                check_editor: 'Nội dung không thể để trống'
                            }
                        },
                        errorElement: "p",
                        errorPlacement: function (error, element) {
                            error.addClass("help-block");
                            if (element.prop("type") === "checkbox") {
                                error.insertAfter(element.parent("label"));
                            } else if (element.is("textarea")) {
                                error.insertAfter(element.closest(".form-group"));
                            } else {
                                error.insertAfter(element);
                            }
                        },
                        highlight: function (element, errorClass, validClass) {
                            $(element).closest("div").addClass("has-error").removeClass("has-success");
                        },
                        unhighlight: function (element, errorClass, validClass) {
                            $(element).closest("div").addClass("has-success").removeClass("has-error");
                        }
                    });
                });
            });
        }
    };
	
	exports.init = function () {
	    new PostView().init();
	}
});