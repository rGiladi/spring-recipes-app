$(document).ready(function() {
	$('.collapse').removeClass('show');
});

var active_delete = false;

$('.fa-trash').on('click', function() {
	var cards = $('.card-header');
	if (active_delete) {
		active_delete = false;
		cards.each(function() {
			$(this).css('backgroundColor', '#f7f7f9');
		});
	}
	else {
		active_delete = true;
		cards.each(function() {
			$(this).css('backgroundColor', '#DE593A');
		});
	}
});

var token = $('input[name="_csrf"]').val();

$('.add_category').on('click', function() {
	
	var category = {};
	category['name'] = $('.category_name').val();
	
	$.ajax({
		url: '/admin/add_category',
		type: 'post',
		contentType: 'application/json',
		data: JSON.stringify(category),
		beforeSend: function(req) {
			req.setRequestHeader('X-CSRF-TOKEN', token)
		},
		success: function(res) {
			if (res.id && res.name) {
				window.location.reload();
			}
			else {
				console.log('already exists')
			}
		}
	});
});

$('.add_ingridient').click(function() {
	var ingridient = {};
	ingridient['name'] = $('.ing_name').val();
	ingridient['priceoz'] = parseFloat($('.ing_price_oz').val());
	ingridient['priceamount'] = parseFloat($('.ing_price_amount').val());
	if (ingridient['name'].length <= 0 || ingridient['priceoz'] <= 0 || ingridient['priceamount'] <= 0 ) {
		alert('שחכת למלא?');
		return false;
	}
	else {
		var category = $('.ing_category option:selected').attr('id');
		$.ajax({
			url: '/admin/add_ingridient?category=' + category,
			type: 'post',
			contentType: 'application/json',
			data: JSON.stringify(ingridient),
			beforeSend: function(req) {
				req.setRequestHeader('X-CSRF-TOKEN', token)
			},
			success: function(res) {
				if (res.name || res.price) {
					window.location.reload();
				}
				else {
					alert('?')
				}
			}
		});
	}
})

$('.card-header').on('mousedown', function(ev) {
	if (ev.which == 1 && active_delete) {
		ev.preventDefault();
		var title = $(this).text();
		if (confirm("למחוק קטגוריה?")) {
			$.ajax({
				url: '/admin/handleRemoveCategory?name=' + title,
				type: 'post',
				contentType: 'application/json',
				beforeSend: function(req){
					req.setRequestHeader('X-CSRF-TOKEN', token)
				},
				success: function(res) {
					if (res) {
						window.location.reload();
					}
					else {
						
					}
				}
			});
		}
	}
});

$('.mm_prices_ing_list li').on('mousedown', function(ev) {
	if (ev.which == 1 && active_delete) {
		ev.preventDefault();
		if ( confirm("למחוק מוצר?")) {
			var id = $(this).attr("ing_id");
			
			$.ajax({
				url: '/admin/handleRemoveIngridient?id=' + id,
				type: 'post',
				contentType: 'application/json',
				beforeSend: function(req) {
					req.setRequestHeader('X-CSRF-TOKEN', token)
				},
				success: function(res) {
					if (res) {
						window.location.reload();
					}
					else {
						
					}
				}
			});
		}
	}
});

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
			var price = Number($(this).text());
			$(this).text( addZeroIfNeed(parseFloat(( (price / last_value_by_amount) * selected[0].value).toFixed(3) )) 	);
			last_value_by_amount = selected[0].value;
		});
	}
	else {
		$('.mmpil_price2').each(function() {
			var price = Number($(this).text());
			$(this).text( addZeroIfNeed(parseFloat( ((price / last_value_by_oz) * selected[0].value).toFixed(3) ) ) );
			last_value_by_oz = selected[0].value;
		});
	}
});

function addZeroIfNeed(n) {
	var s = n.toString();
	
	return s.split('.')[1].length == 1 ? s + '0' : n;
}