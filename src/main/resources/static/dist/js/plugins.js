(function($){
	var uuid = 0;

	function pageLink(curNo, pageNo){
		if(curNo === -1){
			return '<li class="pass"><span>...</span></li>';
		}
		if(pageNo === curNo){
			return $.format('<li class="active"><a href="javascript:void(0)" pn="{0}">{0}</a></li>', pageNo);
		}
		return $.format('<li><a href="javascript:void(0);" pn="{0}">{0}</a></li>', pageNo);
	}


	jQuery.fn.extend({
		upload: function(url, callback){
			var self = this;
			var frameId = "ajax-pload-" + (++uuid);
			var $frame = $("<iframe id='" + frameId + "' name='" + frameId + "' style='position:absolute; top:-9999px;display:none;'/>").appendTo("body");
			var $form = $("<form action='" + url + "' target='" + frameId + "' method='post' enctype='multipart/form-data' style='display:none;'/>");

			var nfile = self.clone(true);
			self.attr("id", "uf-" + uuid);
			self.before(nfile);
			self.detach().appendTo($form);

			$form.appendTo("body");

			$form.submit(function() {
				$frame.load(function() {
					var contents = $frame.contents().get(0);
					var data = $(contents).find('body').text();
					data = (data ? $.parseJSON(data) : {});
					$form.remove();
					setTimeout( function() {
						$frame.remove();
						if (callback) {
							callback.call(self, data);
						}
					}, 1000);
				});
				return true;
			}).submit();
			return this;
		},


		page: function(p, callback, mini){
			var pc = parseInt(p.totalPages);
			if(pc === 0){
				this.html('');
				return this;
			}

			var	c = 3,
				tc = c * 2 + 3,
				cp = parseInt(p.number) + 1,
				h = [];
			h.push('<ul class="pagination pagination-sm">');

			if(cp > 1){
				h.push($.format('<li class="previous"><a href="javascript:void(0);" pn="{0}"><span class="arrow" title="Trước đó">&lt;</span></a></li>', cp - 1));
			}


			if(!mini){
				if(pc > tc){
					if(cp <= c + 2){
						for(var i = 1; i < tc - 1; i++){
							h.push(pageLink(cp, i));
						}
						h.push(pageLink(-1, 0));
						h.push(pageLink(cp, pc));
					}else{
						h.push(pageLink(cp, 1));
						h.push(pageLink(-1, 0));

						if(cp > (pc - (c + 2))){
							for(var i = pc - tc + 2; i <= pc; i++){
								h.push(pageLink(cp, i));
							}
						}else{
							for(var i = cp - c; i < cp + c; i++){
								h.push(pageLink(cp, i));
							}
							h.push(pageLink(-1, 0));
							h.push(pageLink(cp, pc));
						}
					}
				}else{
					for(var i = 1; i <= pc; i++){
						h.push(pageLink(cp, i));
					}
				}
			}


			if(cp < pc){
				h.push($.format('<li class="next"><a href="javascript:void(0);" pn="{0}"><span class="arrow" title="Trang tiếp theo">&gt;</span></a></li>', cp + 1));
			}

			h.push('</ul>');

			this.html(h.join(''));
			this.find('a').bind('click', function(){
				var pn = $(this).attr('pn');
				if(0 < pn && pn <= pc){
					callback(pn);
				}
				return false;
			});

			return this;
		}
	});

	jQuery.extend({

        format: function(source, params) {
	    	if ( arguments.length == 1 )
	    		return function() {
	    			var args = $.makeArray(arguments);
	    			args.unshift(source);
	    			return $.format.apply( this, args );
	    		};
	    	if ( arguments.length > 2 && params.constructor != Array  ) {
	    		params = $.makeArray(arguments).slice(1);
	    	}
	    	if ( params.constructor != Array ) {
	    		params = [ params ];
	    	}
	    	$.each(params, function(i, n) {
	    		source = source.replace(new RegExp('\\{' + i + '\\}', 'g'), n);
	    	});
	    	return source;
	    }

	});

})(window.jQuery);