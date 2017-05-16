var transSupport = ('transition' in document.documentElement.style) || ('WebkitTransition' in document.documentElement.style);
$(document).ready(function() {
	
	if (transSupport) {
		$('body').addClass('animated fadeIn');
	}
	else {
		$('body').fadeIn(100);
	}
	
	$('.getby_price, .getby_time, .getby_likes').val('');
});

$('.open_menu').on('click', function(e) {
	e.stopPropagation();
	if (transSupport) {
		$('.user_menu').removeClass('slideOutRight').addClass('animated slideInRight').css('visibility', 'visible');
	}else {
		$('.user_menu').css('right', '-240px').animate({right: '0'}).css('visibility', 'visible');
	}
	$('.page_overlay').css('display', 'block').addClass("activated");
});

$('.user_menu_ul li').on('click', function(e) {
	var href = $(this).find("a").attr("href");
	
	if (href != null) 
		window.location = $(this).find("a").attr("href");
	e.stopPropagation();
});

// Search mechanism handling

$('.search_input').on('mousedown', function(e) {
	// $(this).attr('placeholder', ' ');
	openSearchBox();
});

$('.search_input').on('focusout', function(e) {
	$(this).attr('placeholder', 'חפש ארוחה');
});

$('.getby_price, .getby_time').on('blur', function() {
	var $this = $(this);
	if ( $this.val() < 0 ) {
		$this.val("");
	}
	
	else if ( $this.val() > 999 ) {
		$this.val(999);
	}
})

$('.btn_search_recipe').on('click', function() {
	
	$('.search_box').hide();
	
	if ($('.getby_price').val() == '') {
		$('.getby_price').val(0);
	}
	
	if($('.getby_time').val() == '') {
		$('.getby_time').val(0);
	}
	
	if($('.getby_likes').val() == '') {
		$('.getby_likes').val(-1);
	}
	
	$('.search_form').submit();
});

$('.close_search_box').click(function() {
	closeSearchBox();
})

//Handling User's menu and Search box + Escape key 

$(document).on('click', function(e) {
	if ($('.page_overlay').hasClass('activated') && !$(e.target).hasClass('user_menu_opening') && !$(e.target).hasClass('user_menu') ) {
		closeUserMenu();
	}
	
	if ( $('.search_box').hasClass('shown') ) {
		if ( !$.contains($('.search_wrapper')[0], $(e.target)[0]) ) {
			closeSearchBox();
		}
	}
});

$(document).keyup(function(e) {
	
	if ( e.keyCode == 27 && $('.page_overlay').hasClass('activated')) {
		closeUserMenu();
		return true;
	}
	
	if ( e.keyCode == 27 && $('.search_box').hasClass('shown')) {
		closeSearchBox();
		return true;
	}
	
	if ( e.keyCode == 27 && $('.show_mm_wrapper').hasClass('mm_active_page') ) {
		
		if (transSupport) {
			$('.show_mm_wrapper').removeClass('fadeIn mm_active_page').css({'visibility':'hidden', 'display':'none'}).addClass('animated fadeOut');
			$('.mm_tabs').css('display', 'block').addClass('mm_active_page fadeIn animated');
		}else {
			$('.mm_active_page').fadeOut(100, function() {
				$('.mm_tabs').fadeIn(200).addClass('mm_active_page');
			}).removeClass('mm_active_page');
		}
		return true;
	}
});


// Reusable Functions

function openSearchBox() {
	if (transSupport) {
		$('.search_box').css({'visibility':'visible', 'display':'block'}).removeClass('fadeOut').addClass('animated fadeIn shown');
	}else {
		$('.search_box').fadeIn(500).addClass('shown').css('visibility', 'visible');
	}
}

function closeSearchBox() {
	if (transSupport) {
		$('.search_box').css({'visibility':'hidden', 'display':'none'}).addClass('fadeOut').removeClass('fadeIn shown');
	}else {
		$('.search_box').fadeOut(100).removeClass('shown');
	}
}

function closeUserMenu() {
	if (transSupport) {
		$('.user_menu').removeClass('slideInRight').addClass('slideOutRight').css('visiblity', 'hidden');
	} else {
		$('.user_menu').animate({right: '-240px'}).css('visibility', 'hidden');
	}
	$('.page_overlay').css('display', 'none');
	$('.page_overlay').removeClass('activated');
}