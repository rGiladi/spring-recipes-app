$('.btn_delete_menu').on('click', function() {
	var token = $('input[name="_csrf"]').val();
	$.ajax({
		url: '/user/deleteRecipe?recipe=' + $(this).attr('ref_id'),
		type: 'post',
		beforeSend: function(request) {
			request.setRequestHeader('X-CSRF-TOKEN', token);
		},
		success: function(res) {
			if ( res == "1" ) {
				window.location.reload();
			}
			else {
				console.log(res);
			}
		}
	});
});