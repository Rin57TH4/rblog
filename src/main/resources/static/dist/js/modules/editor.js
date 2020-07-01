define(function(require, exports, module) {

    var initEditor = function (callback) {
        require.async(['form', 'tinymce'], function () {
            var options = {
                selector: "#content",
                theme: 'modern',
                language: "vi",
                upload_image_url: RIN_BLOG.BASE_PATH + "/post/upload",
                height: 400,
                plugins: [
                    'fullpage advlist autolink autosave charmap lists link image print anchor codesample',
                    'searchreplace visualblocks code fullscreen textcolor colorpicker textpattern uploadimage',
                    'contextmenu paste'
                ],
                toolbar: "undo redo | formatselect | bold underline blockquote | alignleft aligncenter alignright | " +
                "forecolor bullist numlist | link unlink | uploadimage codesample removeformat | fullscreen ",
                menubar: false,
                statusbar : false,
                paste_data_images: true,
                convert_urls: false,
                body_class: 'markdown-body',
                codesample_dialog_width: '600',
                codesample_dialog_height: '400',
                block_formats: 'Paragraph=p;Heading 1=h4;Heading 2=h5;Heading 3=h6;',
                codesample_languages: [
                    {text: 'HTML/XML', value: 'html'},
                    {text: 'JavaScript', value: 'javascript'},
                    {text: 'CSS', value: 'css'},
                    {text: 'PHP', value: 'php'},
                    {text: 'Ruby', value: 'ruby'},
                    {text: 'Python', value: 'python'},
                    {text: 'Java', value: 'java'},
                    {text: 'C', value: 'c'},
                    {text: 'C#', value: 'csharp'},
                    {text: 'C++', value: 'cpp'}
                ],
                // entity_encoding: 'raw',
                content_css: [
                    RIN_BLOG.BASE_PATH + '/dist/vendors/bootstrap/css/bootstrap.min.css',
                    RIN_BLOG.BASE_PATH + '/dist/css/editor.css',
                ],
                setup: function(editor) {
                    editor.on('change', function(e) {
                        tinymce.triggerSave();
                        $("#" + editor.id).valid();
                    });
                }
            };

            if ($(window).width() < 900) {
                options.theme = 'mobile';
                options.toolbar = "undo redo | bold underline blockquote | alignleft aligncenter alignright | uploadimage"
            }
            tinymce.init(options);

            callback.call(this);
        });

    };

	exports.init = function (callback) {
        initEditor(callback);
    };
});