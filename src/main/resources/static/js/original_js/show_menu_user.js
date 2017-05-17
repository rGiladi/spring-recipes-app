$('.fa-users').on('click', function() {
	var token = $('input[name="_csrf"]').val();
	
	$.ajax({
		url: '/user/updatePublishedMenu?menu=' + $(this).attr('ref_id'),
		type: 'post',
		beforeSend: function(request) {
			request.setRequestHeader('X-CSRF-TOKEN', token);
		},
		success: function(res) {
			switch(res) {
			case "0":
				$('.fa-users').attr("title", "פרסם לכולם").removeClass("menu_published");
				break;
			case "1":
				$('.fa-users').attr("title", "הורד מפרסום").addClass("menu_published")
				break;
			case "permission":
				alert("שגיאה");
				break;
			case "error":
				alert("שגיאה");
			}
		},
	})
});

$('.btn_delete_menu').on('click', function() {
	var token = $('input[name="_csrf"]').val();
	
	$.ajax({
		url: '/user/deleteMenu?menu=' + $(this).attr('ref_id'),
		type: 'post',
		beforeSend: function(request) {
			request.setRequestHeader('X-CSRF-TOKEN', token);
		},
		success: function(res) {
			if ( res == "1" ) {
				window.location.reload();
			}
			else {
				toast("יש בעיה, אנא נסה שוב")
			}
		}
	});
});


$('.fa-users').on('click', function() {
	if ( $(this).hasClass('mm_published') ) {
		$(this).removeClass('mm_published');
	}
	else {
		$(this).addClass('mm_published');
	}
});