
// set caret ( up or down ) according to user click on button.

var caret = false; 
$('.addrecipe_moreoptions').on('click', function() { 
	if (!caret) {
		$(this).children('.fa-caret-down').removeClass('fa-caret-down').addClass('fa-caret-up');
		$('.addrecipe_options').show();
		caret = true;
	}else {
		$(this).children('.fa-caret-up').removeClass('fa-caret-up').addClass('fa-caret-down');
		$('.addrecipe_options').hide();
		caret = false;
	}
})

// Calculate total cost of recipe

var add_recipe_total_price = 0;
var known_calc_prices = [];
var curr_indx;
$(document).on('focusout', '.product_price', function() {
	if ($(this).val() < 1) {
		$(this).val("");
	}
	else {
		calc_ingrident_price($(this));
	}
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

$('.add_recipe_freetext').on('keyup', function() {
	var cha_span = $('.re_cha span');
	var ft_length = $(this).val().length;
	if ( ft_length < 550 ) {
		cha_span.text(550 - $(this).val().length);
	} else {
		cha_span.text(0);
		$(this).val($(this).val().substr(0, 550));
	}
});

$('.add_recipe_title').on('keyup', function() {
	var cha_span = $('.re_cha_t span');
	var ti_length = $(this).val().length;
	if ( ti_length < 120 ) {
		cha_span.text( 120 - $(this).val().length)
	} else {
		cha_span.text(0);
		$(this).val($(this).val().substr(0,120));
	}
});

// Correct input on focusout

$('.add_recipe_preparetime').on('focusout', function() {
	if ( $(this).val() > 2880 ) {
		$(this).val(2880);
		toast('זמן ההכנה צריך להיות בין 1 ל 2880 דקות (2 ימים)');
	}
});

$('.add_recipe_amount').on('focusout', function() {
	if ( $(this).val() > 100 ) {
		$(this).val(100);
		toast('הכמות צריכה להיות בין 1 ל 100 מנות');
	}
});

/* Handling file upload */

$('.btn_upload_file').on('click', function() {
	$("input[name='file']").click();
});

$("input[name='file']").on('change', function() {
	readURL(this);
})

// Set preview image 

function readURL(input) { 
    if (input.files && input.files[0] && input.files[0].size < 4800000) {
    	if ( input.files[0].type.match(/.(jpg|jpeg|png|gif)$/i) ) {
    		$('#prev_img').attr('real_src', null); // When updating existing recipe
	        var reader = new FileReader();
	        reader.onload = function (e) {
	        	$('.btn_upload_file').hide();
	            $('#prev_img').attr('src', e.target.result).css("display", "block");
	        }
	        
	        reader.readAsDataURL(input.files[0]);
    	}
    	else {
    		resetFile();
    	}
    } 
    else {
    	resetFile();
    }
}

//Delete uploaded file if necesseary.

function resetFile() { 
	var input = $('input[name="file"]');
	toast('אפשר להעלות קבצים רק מסוג GIF, PNG, JPEG, JPG ועד 5 MB');
	input.val('');
	input.type = '';
	input.type = 'file';
	
	if (input[0].files.length != 0) {
		file_input.wrap('<form>').closest('form').get(0).reset();
		file_input.unwrap();
	}
	
	if (input[0].files.length != 0 ) { // If file reset didn't work ( IE 11 ? )
		input.replaceWith(input.clone(true, true));
	}
}


// Upload File 

function sendFile(file, token) {
	var upload_success = false;
	var arr = [];
	var data = new FormData();
	data.append("file", file);
	
	return $.ajax({
		type: 'POST',
		url: '/fileUpload',
		data: data,
		beforeSend: function (request) {
			request.setRequestHeader('X-CSRF-TOKEN', token)
		},
		processData: false,
		contentType: false,
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
};

/* Ajax add recipe + Validations */

$('.add_recipe_submit').on('click', function(e) {
	var id = $(this).attr('href_id');
	if (endValidation()) {
		
		var image= $("input[name='file']")[0].files[0];
		var token = $('input[name="_csrf"]').val();
		
		$.when(sendFile(image, token)).then(function(img_name, status, c) {
			if ( img_name != "type" && img_name != "server" && img_name != "empty" && status == "success" ) {
				var data = getFormDataObject(img_name);
				updateRecipeAjaxCall(id, token, img_name, '/addrecipe');
			}
			
			switch (img_name) {
			case "type":
				toast('אפשר להעלות קבצים רק מסוג GIF, PNG, JPEG, JPG ועד 5 MB')
				break;
			case "server":
				toast('הייתה שגיאה, בבקשה נסה שוב (רענון יכול לפתור את הבעיה)')
				break;
			case "empty":
				toast('הייתה בעיה עם העלאת התמונה')
			}
		});
	}
});


// Update Recipe

$('.edit_recipe_submit').on('click', function() {
	var id = $(this).attr('href_id');
	var token = $('input[name="_csrf"]').val();
	
	if (endValidation()) { // Frontend Validation for input fields
		
		if ( $('#prev_img').attr('real_src') == null ) { // checking if image has changed from original
			var image= $("input[name='file']")[0].files[0];
			$.when(sendFile(image, token)).then(function(img_name, status, c) { // Uploading image first
				if ( img_name != "type" && img_name != "server" && img_name != "empty" && status == "success" ) {
					updateRecipeAjaxCall(id, token, img_name, '/updaterecipe');
				} 
				else{ // if couldn't update recipe
					i_name = "error";
					return false;
					switch (img_name) {
					case "type":
						toast('אפשר להעלות קבצים רק מסוג GIF, PNG, JPEG, JPG ועד 5 MB')
						return false;
						break;
					case "server":
						toast('הייתה שגיאה, בבקשה נסה שוב (רענון יכול לפתור את הבעיה)')
						return false;
						break;
					case "empty":
						toast('הייתה בעיה עם העלאת התמונה')
						return false;
					}
				}
			});
		}
		else { // If original image havn't been changed, simple ajax call
			updateRecipeAjaxCall(id, token, '', '/updaterecipe');
		}
	}
});

// Ajax Call

function updateRecipeAjaxCall(id, token, img_name, update_or_add) {
	var data = getFormDataObject(img_name, id);
	$.ajax( {
		url: update_or_add,
		type: 'post',
		contentType: 'application/json',
		data: JSON.stringify(data),
		beforeSend: function(request) {
			request.setRequestHeader('X-CSRF-TOKEN', token);
		},
		success: function(res) {
			switch (res) {
			case '':
				$('.add_recipe_form')[0].reset();
				window.location = '/';
				break;
			case 'error':
				toast('הייתה בעיה, נסה שוב או רענן')
				break;
			default:
				toast('שכחת למלא סעיפים מסויימים?')
				var res_arr = res.split(', ');
				for (i = 0; i < res_arr.length; i++) {
					$(res_arr[i]).addClass('alert-danger');
				}
				if ( res_arr.indexOf('.products_col') == -1 ) {
					$('.products_col').removeClass('alert-danger');
				}
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

// Get data for ajax call

function getFormDataObject(img_name, id) {
	
	var data = {};
	data['id'] = id;
	data['title'] = $('.add_recipe_title').val();
	data['time'] = $('.add_recipe_preparetime').val();
	data['freetext'] = $('.add_recipe_freetext').val();
	data['amount'] = $('.add_recipe_amount').val();
	data['price'] = parseFloat($('#sum').text());
	data['prices'] = getProductsPrices();
	data['products'] = getProducts();
	data['image'] =  $('#prev_img').attr('real_src') != null ? $('#prev_img').attr('real_src') : img_name;
	data['recipetime'] = $('#addrecipe_select_recipetime :selected').val();
	data['recipetype'] = $('#addrecipe_select_recipetype :selected').val();
	data['gluten'] = $('input[name="gluten"]').is(':checked');
	data['vegetarian'] = $('input[name="vegetarian"]').is(':checked');
	data['vegan'] = $('input[name="vegan"]').is(':checked');
	data['kosher'] = $('input[name="kosher"]').is(':checked');
	data['spicy'] = $('input[name="spicy"]').is(':checked');
	
	return data;
}

// Form Validation 

function endValidation() {
	
	
	// Title 
	
	var recipe_title = $('.add_recipe_title');
	if ( recipe_title.val().trim().length == 0 ) {
		recipe_title.addClass('alert-danger');
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
	var recipe_freetext = $('.add_recipe_freetext');
	if (recipe_freetext.val() > 550) {
		recipe_freetext.addClass('alert-danger');
		toast('הטקסט יכול להיות בין 0 ל 550 תווים');
		return false;
	}
	
	// Preperation time
	var recipe_preparetime = $('.add_recipe_preparetime');
	if ( !recipe_preparetime.val() || recipe_preparetime.val() <= 0 ) {
		recipe_preparetime.addClass('alert-danger');
		toast('חובה למלא זמן הכנה')
		return false;
	} 
	else if (recipe_preparetime.val() > 2880) {
		recipe_preparetime.addClass('alert-danger');
		toast('זמן ההכנה צריך להיות בין 1 ל 2880 דקות (2 ימים)');
		return false;
	}
	else { 
			recipe_preparetime.removeClass('alert-danger'); 
		}
	
	// Amount
	var recipe_amount = $('.add_recipe_amount');
	if ( !recipe_amount.val() || recipe_amount.val() <= 0 ) {
		recipe_amount.addClass('alert-danger');
		toast('חובה למלא כמות מנות');
		return false;
	}
	else if (recipe_amount.val() > 99) {
		recipe_amount.addClass('alert-danger');
		toast('הכמות צריכה להיות בין 1 ל 100 מנות');
		return false;
	}
	else {
		recipe_amount.removeClass('alert-danger');
	}
	
	var arr_prod = getProducts();
	var arr_prod_price = getProductsPrices();
	
	if ( arr_prod.length == 0 || arr_prod_price.length == 0 ) {
		toast('חובה להכניס לפחות 3 מוצרים')
		$('.products_col').addClass('alert-danger');
		
		return false;
	} 
	
	else {
			if ( arr_prod.length < 3 || arr_prod_price < 3 ) {
				toast('חובה להכניס לפחות 3 מוצרים');
				$('.products_col').addClass('alert-danger');
				return false;
			}
			else {
				$('.products_col').removeClass('alert-danger');
			}
		}
	
	if ( arr_prod.length !== arr_prod_price.length) {
		$('.products_col').addClass('alert-danger');
		toast('לכל מוצר צריך למלא שם ומחיר')
		return false;
	}
	
	else {
		if (arr_prod.length !== 0 || arr_prod_price.length !== 0) {
			$('.products_col').removeClass('alert-danger');
		}
	}
	
	var file = $("input[name='file']");
	if (!file[0].files[0] && $('#prev_img').hasClass('adding_rec') || $('#prev_img').attr('src').trim().length == 0) {
		$('.box_upload_file').addClass("alert-danger");
		toast('לא העלית תמונה עדיין...')
		return false;
	}
	else { $('.box_upload_file').removeClass("alert-danger"); }
	
	return true;
}

//Functions to get values from form

function getProducts() {
	var arr = [];
	$('.product_ing').each(function() {
		if (!$(this).hasClass("ing_hide")) {
			if ($(this).val().trim().length > 0) {
				arr.push($(this).val())	
			}
		}
	});
	return arr;
}

function getProductsPrices() {
	var arr = [];
	$('.product_price').each(function() {
		if ($(this).val() > 0) {
			arr.push($(this).val())	
		}
	});
	
	return arr;
}

/* User actions validation */

$('.add_recipe_product').on('click', function() {
	if ( $('.ing_hide').length != 0) {
		var f_ih = $('.ing_hide').first();
		f_ih.removeClass('ing_hide').get(0).scrollIntoView();
		f_ih.find('.product_ing').focus();
	}else {
		toast("עברת את כמות המוצרים המותרת")
	}
});

$('input, div').on('click', function() {
	if ($(this).hasClass('alert-danger')) {
		$(this).removeClass('alert-danger');
	}
});

//Ingridients

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

//Change by_select_change for ingridients table 

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