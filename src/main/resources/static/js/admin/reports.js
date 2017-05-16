var token = $('input[name="_csrf"]').val();

$('.recipe_row, .menu_li').on('click', function() {
	var id = $(this).attr('id');
	$('.fa-trash, .showmm_unreport').attr({'ref_id':id, 'mm': $(this).attr('mm')});
})


// Delete Menu / Recipe because report was justified

$('.fa-trash').on('click', function(){
	var id = $(this).attr('ref_id');
	var m = $(this).attr('mm');
	
	$.ajax({
		url: '/admin/reports/delete?m=' + m + '&id=' + id,
		type: 'post',
		beforeSend: function(req) {
			req.setRequestHeader('X-CSRF-TOKEN', token)
		},
		success: function(res) {
			window.location.reload();
		}
	})
});



// Unreport Menu / Recipe
$('.showmm_unreport').on('click', function(){
	var id = $(this).attr('ref_id');
	var m = $(this).attr('mm');
	
	$.ajax({
		url: '/user/report?m=' + m + '&id=' + id,
		type: 'post',
		beforeSend: function(req) {
			req.setRequestHeader('X-CSRF-TOKEN', token)
		},
		success: function(res) {
			window.location.reload();
		}
	})
});