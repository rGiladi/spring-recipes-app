var transSupport = ('transition' in document.documentElement.style) || ('WebkitTransition' in document.documentElement.style);

/* menus main_page */

var edit_href = $('#edit_href').attr('href');

$('.menu_li, .recipe_row').on('click', function() {
	$('.mm_tabs').css('display', 'none');
	
	// Both
	
	var recmen_id = $(this).attr('id');
	
	$('.edit_p').attr('ref_id', recmen_id);
	$('#edit_href').attr('href', edit_href + recmen_id);
	
	$('.showmm_like').attr("ref_id", recmen_id);
	if ( $(this).find('.liked_check').hasClass('user_liked') ) {
		$('.showmm_like').addClass('user_liked');
		
	}
	else {
		$('.showmm_like').removeClass('user_liked');
	}
	
	$('.showmm_report').attr("ref_id", recmen_id);
	
	if ( $(this).find('.reported_check').hasClass('user_reported') ) {
		$('.showmm_report').addClass('user_reported')
	}
	else {
		$('.showmm_report').removeClass('user_reported');
	}
	
	
	var freetext = $(this).find('.mm_freetext').clone().removeClass("mm_freetext");
	$('#show_mm_freetext').empty().append(freetext);
	
	$('#show_mm_title').text($(this).find('.mm_title .orig').text());
	$('#show_mm_price').text($(this).find('.mm_price').text());
	
	// If recipes
	
	$('#showrecipe_recipe_time').text($(this).find('.recipe_time').text());
	$('#showrecipe_img').attr("src", $(this).find('.recipe_img').attr("src"));
	
	var ingridients = $(this).find('.toshow_ingridients_list').clone().removeClass("toshow_ingridients_list");
	$('#ing_list').empty().append(ingridients);
	
	// If Menus
	
	var toshow_menu_recipes = $(this).find('.toshow_menu_recipes').clone().removeClass("toshow_menu_recipes").addClass('fix_titles');
	$('#showmenu_recipes').empty().append(toshow_menu_recipes);
	$('.fix_titles').find('div').each(function(indx) {
		$(this).find('.menu_recipes_title').text('ארוחה ' + (indx + 1) )
	});
	
	// If menus_user 
	
	$('.fa-users').attr("title", $(this).find('.toshow_publish_title').text());
	$('.fa-users').attr("ref_id", recmen_id);
	
	if ($(this).find('.toshow_publish_title').hasClass('mm_published')) {
		$('.fa-users').addClass('mm_published');
	}
	
	$('.btn_delete_menu').attr("ref_id", recmen_id);
	
	//
	
	if (transSupport) {
		$('.show_mm_wrapper').removeClass('fadeOut').css({'visibility':'visible', 'display':'block'}).addClass('animated fadeIn mm_active_page');
	}else {
		$('.show_mm_wrapper').fadeIn(300).addClass('mm_active_page');
	}
});


$('.showmm_close').on('click', function() {
	
	if (transSupport) {
		$('.show_mm_wrapper').removeClass('fadeIn mm_active_page').css({'visibility':'hidden', 'display':'none'}).addClass('animated fadeOut');
		$('.mm_tabs').css('display', 'block').addClass('mm_active_page fadeIn animated');
	}else {
		$('.mm_active_page').fadeOut(100, function() {
			$('.mm_tabs').fadeIn(200).addClass('mm_active_page');
			
			// Users Specifics
			$('.fa-users').removeClass('menu_published');
			
		}).removeClass('mm_active_page');
	}
});


// Toolbar 

var token = $('input[name="_csrf"]').val();
$('.showmm_like').on('click', function() {
	var menu_or_recipe = $('.container-fluid').hasClass('recipes_page') ? '/user/likeRecipe?recipe=' : "/user/likeMenu?menu=";
	var ref_id = $(this).attr("ref_id");
	
	$.ajax({
		url: menu_or_recipe + ref_id,
		type: 'post',
		beforeSend: function(req) {
			req.setRequestHeader('X-CSRF-TOKEN', token);
		},
		success: function(res) {
			var parent = $('#' + ref_id);
			var mm_lkn = parent.find(".mm_lkn");
			switch(res) {
			case "1":
				$('.showmm_like').addClass('user_liked');
				mm_lkn.text(parseInt(mm_lkn.text()) + 1);
				parent.find('.liked_check').addClass('user_liked');
				break;
			case "0":
				$('.showmm_like').removeClass('user_liked');
				mm_lkn.text(parseInt(mm_lkn.text()) - 1);
				break;
			case "user":
				toast("רק משתמשים רשומים יכולים לתת חוות דעת..");
			}
			
		}
	})
});
$('.showmm_report').on('click', function() {
	var menu_or_recipe = $('.container-fluid').hasClass('recipes_page') ? '2' : '1';
	var ref_id = $(this).attr("ref_id");
	
	$.ajax({
		url: '/user/report?m=' + menu_or_recipe + '&id=' + ref_id,
		type: 'post',
		beforeSend: function(req) {
			req.setRequestHeader('X-CSRF-TOKEN', token);
		},
		success: function(res) {
			switch(res) {
			case "1":
				$('.showmm_report').addClass('user_reported');
				break;
			case "2":
				$('.showmm_report').removeClass('user_reported');
				break;
			case "user":
				toast("רק משתמשים רשומים יכולים לתת חוות דעת..");
			}
			
		}
	})
});




