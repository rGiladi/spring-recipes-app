$('.add_menu_product').on('click', function() {
	var products = $(this).closest('.products_col').find('.addmenu_products_wrapper').find('.ing_hide');
	if ( products.length != 0) {
		var prod_fi = $(products).first();
		prod_fi.removeClass("ing_hide").get(0).scrollIntoView();
		prod_fi.find('.product_ing').focus();
	}
	else {
		toast("עברת את כמות המוצרים המותרת")
	}
});

$('.add_menu_meal').on('click', function() {
	if ($('.menu_recipe_hide').length != 0) {
		$('.menu_recipe_hide').first().removeClass("menu_recipe_hide").get(0).scrollIntoView();
	}
	else {
		toast("עברת את כמות הארוחות המותרת");
	}
	});

// Calculate Total Money For Add_Recipe

var add_recipe_total_price = 0;
var known_calc_prices = [];
var curr_indx;
$(document).on('focusout', '.product_price', function() {
	calc_ingrident_price($(this));
});

function calc_ingrident_price(ele_focused) {
	curr_indx = $('.product_price').index(ele_focused);
	var ele_val = +ele_focused.val();

	if (known_calc_prices[curr_indx]) {
		add_recipe_total_price += ele_val - known_calc_prices[curr_indx];
		known_calc_prices[curr_indx] = ele_val;
	}
	else {
		known_calc_prices[curr_indx] = ele_val;
		add_recipe_total_price += ele_val;
	}
	
	$('#sum').text(add_recipe_total_price); // Remember to parseInteger up to 3 numbers after decimal
};

/* Showing remaining possible characters */

$('.add_menu_freetext').on('keyup', function() {
	var cha_span = $('.re_cha span');
	var ft_length = $(this).val().length;
	if ( ft_length < 550 ) {
		cha_span.text(550 - $(this).val().length);
	} else {
		cha_span.text(0);
		$(this).val($(this).val().substr(0, 550));
	}
});

$('.add_menu_title').on('keyup', function() {
	var cha_span = $('.re_cha_t span');
	var ti_length = $(this).val().length;
	if ( ti_length < 120 ) {
		cha_span.text( 120 - $(this).val().length)
	} else {
		cha_span.text(0);
		$(this).val($(this).val().substr(0,120));
	}
});

 
$('.add_menu_submit').on('click', function(e) {
	
	if (endValidation()) {
		var token = $('input[name="_csrf"]').val();
		var data = getDataForAddMenuAjax();
		var url = $(this).hasClass("edit_menu_submit") ? '/updatemenu' : '/addmenu'
		
		if ( url == '/updatemenu' ) {
			data['id'] = $(this).attr('menu_id');
		}
	
		$.ajax( {
			url: url,
			type: 'post',
			contentType: 'application/json',
			data: JSON.stringify(data),
			beforeSend: function(request) {
				request.setRequestHeader('X-CSRF-TOKEN', token);
			},
			success: function(res) {
				switch ( res ) {
				case '':
					window.location = "/menus";
					break;
				case 'err_edit_user':
					toast('הייתה בעיה, יכול להיות שאין לך את ההרשאות המתאימות בשביל לבצע פעולה זו');
					break;
				case 'err_edit':
					toast('הייתה בעיה, נסה שוב מאוחר יותר');
					break;
				default:
					$('.alert-danger').each(function() {
						$(this).removeClass('alert-danger');
					});
					var eq_fi = false;
					var toast_time = 2500;
					var toast_string = '';
					var res_arr = res.split(', ');
					for (i = 0; i < res_arr.length; i++) {
						if ( res_arr[i] == ".add_menu_title" ) {
							$('.add_menu_title').addClass('alert-danger').get(0).scrollIntoView();
							toast_string += '<div>חובה לתת כותרת לתפריט</div>';
						} else {
							$(res_arr[i]).find('.products_col').addClass('alert-danger');
							if ( !eq_fi ) {
								if ( res.match(/equality/) ) {
									if (res.match(/first3/)) {
										toast_string += '<div>לכל מוצר צריך למלא שם ומחיר וחובה למלא לפחות שלוש ארוחות</div>';
										toast_time += 3000;
										eq_fi = true;
									}
								}
								else {
									toast_string += '<div>חובה למלא לפחות שלוש ארוחות </div>';
									toast_time += 3000;
									eq_fi = true
								}
							}
						}
					}
					toast(toast_string, toast_time);
				}
			},
			xhr: function() {
				var xhr = $.ajaxSettings.xhr();
				xhr.upload.onprogress = function(event) {
					$('.progress').css('display', 'block')
					$('.progress-bar').css('width',event.loaded/event.total*100 + '%');
					$('.progress-bar').attr('aria-valuenow',event.loaded/event.total*100 + '%');
				}
				
				return xhr;
			}
		});
	}
});

function getDataForAddMenuAjax() {
	var arr = {};
	arr['title'] = $('.add_menu_title').val();
	arr['freeText'] = $('.add_menu_freetext').val();
	arr['price'] = $('#sum').text();
	
	for (var i = 1; i < 9; i++ ) {
		var product_ing_arr = [];
		var product_price_arr = [];
		
		$('.mm' + i).find('.products_col').each(function() {
			if (!$(this).hasClass('menu_recipe_hide') ) {
				$(this).find('.products_ajax_wrapper').each(function(event, ele) {
					if (!$(ele).hasClass('ing_hide')) {
						
						if ( $(ele).find('.product_ing').val().trim().length != 0) {
							product_ing_arr.push( $(ele).find('.product_ing').val());
						}
						if ( $(ele).find('.product_price').val().trim().length !=0 ) {
							product_price_arr.push( $(ele).find('.product_price').val());
						}
						
					}
				})
			}
		});
		arr['productsRecipe' + i] = product_ing_arr;
		arr['pricesRecipe' + i] = product_price_arr;
	}
	return arr;
}

//Form Validation 

function endValidation() {
	// Title 
	
	var mm_title = $('.add_menu_title');
	if ( mm_title.val().trim().length == 0 ) {
		mm_title.addClass('alert-danger');
		toast('חובה למלא כותרת')
		return false;
	}
	else if ( recipe_title.val().length > 120 ) {
		recipe_title.addClass('alert-danger');
		toast('הכותרת צריכה להיות בין 1 ל 120 תווים');
		return false;
	}
	else { recipe_title.removeClass('alert-danger'); }
	
	// Freetext
	var mm_freetext = $('.add_menu_freetext');
	if (mm_freetext.val() > 550) {
		mm_freetext.addClass('alert-danger');
		toast('הטקסט יכול להיות בין 0 ל 550 תווים');
		return false;
	}
	
	
	return true;
}


// Ingridients

//Change by_type for ingridients table

$('.by_radio').on('click', function() {
	if ($(this).hasClass('by_amount')) {
		
		$('.mmpil_price').css('display', 'block');
		$('.mmpil_price2').css('display', 'none');
		$('.select_by_oz').css('visibility', 'hidden');
		$('.select_by_amount').css('visibility', 'visible');
		$('.by_oz')[0].checked = false;
		
		$('.mmpil_ing_by').each(function() {
			$(this).text($('.select_by_amount :selected').text())
		});
		
	} 
	else {
		$('.mmpil_price2').css('display', 'block');
		$('.mmpil_price').css('display', 'none');
		$('.mmpil_ing_by').each(function() {
			$(this).text($('.select_by_oz :selected').text())
		});
		
		$('.select_by_amount').css('visibility', 'hidden');
		$('.select_by_oz').css('visibility', 'visible');
		$('.by_amount')[0].checked = false;
		
	}
});

// Change by_select_change for ingridients table 

var last_value_by_amount = 1;
var last_value_by_oz = 1;

$('.select_by').on('change', function() {
	var selected = $(this);
	
	$('.mmpil_ing_by').each(function() {
		if (selected.hasClass('select_by_amount')) {
			$(this).text(selected[0].value);
		}
		else {
			$(this).text(selected.find('option:selected').text());
		}
	});
	
	if ($(this).hasClass('select_by_amount')) {
		$('.mmpil_price').each(function() {
			var price = $(this).text();
			$(this).text( ((price / last_value_by_amount) * selected[0].value).toFixed(2) );
			last_value_by_amount = selected[0].value;
		});
	}
	else {
		$('.mmpil_price2').each(function() {
			var price = $(this).text();
			console.log(price);
			$(this).text( ((price / last_value_by_oz) * selected[0].value).toFixed(2) );
			last_value_by_oz = selected[0].value;
		});
	}
	
});

/* Ingridients Table Scroll */

var ing_table_on;
if ( window.innerWidth > 760 ) {
	$(document).scroll(function() {
		ingTop();
	});
	ing_table_on = true;
} else {
	ing_table_on = false;
}

$(window).resize(function() {
	
	if ( !ing_table_on && window.innerWidth > 760 ) {
		$(document).on('scroll', function() {
			ingTop();
			ing_table_on = true;
		});
	}
	
	if ( window.innerWidth <= 760 && ing_table_on) {
		$(document).off('scroll');
		ing_table_on = false;
	}
	
});

function ingTop () {
	var scrlTop = $(this).scrollTop();
	if (scrlTop < 30) {
		$('.ingridients_table').css('margin-top', 30);
	}
	else {
		$('.ingridients_table').css('margin-top',scrlTop);
	}
}