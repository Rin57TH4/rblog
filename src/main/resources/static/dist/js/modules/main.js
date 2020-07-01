define(function(require, exports, module) {
    var plugins = require('plugins');
	var Authc = require('authc');

    var wpexLocalize = {
    		"mobileMenuOpen" : "Click here to navigate",
    		"mobileMenuClosed" : "Close navigation",
    		"isOriginLeft" : "1"
    	};

    var backToTop = function () {
    	var $window = $(window);
    	var $scrollTopLink = $( 'a.site-scroll-top' );
		$window.scroll(function () {
			if ($(this).scrollTop() > 100) {
				$scrollTopLink.fadeIn();
			} else {
				$scrollTopLink.fadeOut();
			}
		});		
		$scrollTopLink.on('click', function() {
			$( 'html, body' ).animate({scrollTop:0}, 400);
			return false;
		} );
    }

	var bindClickEvent = function () {
		// Favor
		$('a[rel=favor]').click(function () {
			var id = $(this).attr('data-id');

			if (!Authc.isAuthced()) {
				Authc.showLogin();
				return false;
			}

			if (parseInt(id) > 0) {
				jQuery.getJSON(RIN_BLOG.BASE_PATH +'/user/favor', {'id': id}, function (ret) {
					if (ret.code >=0) {
						var favors = $('#favors').text();
						$('#favors').text(parseInt(favors) + 1);
					} else {
						layer.msg(ret.message, {icon: 5});
					}
				});
			}
		});

	}

    exports.init = function () {
    	// imagesLazyload();
    	backToTop();
		bindClickEvent();
        $('[data-toggle="tooltip"]').tooltip();
    };
    
});