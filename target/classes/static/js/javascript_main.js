var opts = {
  lines: 11 // The number of lines to draw
, length: 9 // The length of each line
, width: 12 // The line thickness
, radius: 40 // The radius of the inner circle
, scale: 0.5 // Scales overall size of the spinner
, corners: 0.8 // Corner roundness (0..1)
, color: '#000' // #rgb or #rrggbb or array of colors
, opacity: 0.3 // Opacity of the lines
, rotate: 64 // The rotation offset
, direction: 1 // 1: clockwise, -1: counterclockwise
, speed: 0.9 // Rounds per second
, trail: 55 // Afterglow percentage
, fps: 20 // Frames per second when using setTimeout() as a fallback for CSS
, zIndex: 2e9 // The z-index (defaults to 2000000000)
, className: 'spinner' // The CSS class to assign to the spinner
, top: '45%' // Top position relative to parent
, left: '50%' // Left position relative to parent
, shadow: true // Whether to render a shadow
, hwaccel: false // Whether to use hardware acceleration
, position: 'absolute' // Element positioning
}

var spinner = new Spinner(opts);

$(document).ready(function() {

	$('.btn_search_recipea').on('click', function() {
		$('.recipes_wrapper').fadeOut(300, function() {
			spinner.spin();
			$('.recipes_col').append(spinner.el);
		});
		setTimeout(function() {
			spinner.stop();
			$('.recipes_wrapper').fadeIn(300);
		}, 1000);
	})
});

