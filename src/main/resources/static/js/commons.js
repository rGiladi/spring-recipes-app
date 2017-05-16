
$(document).ready(function() {

if (window.innerWidth > 666) {
	$('.mm_title .orig').each(function() {
		var text = $(this).text();
		if ( text.length > 55 ) {
			$(this).hide();
			$(this).parent().find('.substr').text(text.substr(0, 55) + '...' );
		}
	});
}

});

var checkWidth1 = false;
var checkWidth2 = false;
var checkWidth3 = false;
var checkWidth4 = false;
var checkWidth5 = false;
var checkWidth6 = false;
var checkWidth7 = false;

$(window).on('resize', function() {
	if ( window.innerWidth < 1283 && window.innerWidth > 1102 &&  !checkWidth1 ) {
		checkWidth1 = true;
		checkWidth2 = false;
		checkWidth3 = false;
		substrTitle(45);
	}
	
	else if (window.innerWidth < 1102 && window.innerWidth > 1001 && !checkWidth2) {
		checkWidth1 = false;
		checkWidth2 = true;
		checkWidth3 = false;
		substrTitle(35);
	}
	
	else if (window.innerWidth > 1283 && !checkWidth3 && checkWidth1) {
		checkWidth2 = false;
		checkWidth3 = true;
		checkWidth4 = false;
		substrTitle(55);
	}
	
	else if (window.innerWidth < 1001 && window.innerWidth > 889 && !checkWidth4 ) {
		checkWidth4 = true;
		checkWidth5 = false;
		substrTitle(55);
	}
	
	else if (window.innerWidth < 889 && window.innerWidth > 760 && !checkWidth5 ) {
		checkWidth4 = false;
		checkWidth5 = true;
		checkWidth6 = false;
		substrTitle(45);
	}
	
	else if (window.innerWidth < 760 && window.innerWidth > 666 && !checkWidth6) {
		checkWidth5 = false;
		checkWidth6 = true;
		checkWidth7 = false;
		substrTitle(55);
	}
	
	else if (window.innerWidth < 666 && !checkWidth7) {
		checkWidth6 = false;
		checkWidth7 = true;
		
		$('.mm_title .orig').each(function(indx, ele) {
			var text = $(ele).text();
			if ( text.length > 55 ) {
				$(ele).hide();
				$(ele).parent().find('.substr').text(text);
			}
		});
	}
})

function substrTitle(n) {
	$('.mm_title .orig').each(function(indx, ele) {
		var text = $(ele).text();
		if ( text.length > 55 ) {
			$(ele).parent().find('.substr').text(text.substr(0, n) + '...')
		}
	});
}