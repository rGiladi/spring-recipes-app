function openSearchBox(){transSupport?$(".search_box").css({visibility:"visible",display:"block"}).removeClass("fadeOut").addClass("animated fadeIn shown"):$(".search_box").fadeIn(500).addClass("shown").css("visibility","visible")}function closeSearchBox(){transSupport?$(".search_box").css({visibility:"hidden",display:"none"}).addClass("fadeOut").removeClass("fadeIn shown"):$(".search_box").fadeOut(100).removeClass("shown")}function closeUserMenu(){transSupport?$(".user_menu").removeClass("slideInRight").addClass("slideOutRight").css("visiblity","hidden"):$(".user_menu").animate({right:"-240px"}).css("visibility","hidden"),$(".page_overlay").css("display","none"),$(".page_overlay").removeClass("activated")}var transSupport="transition"in document.documentElement.style||"WebkitTransition"in document.documentElement.style;$(document).ready(function(){transSupport?$("body").addClass("animated fadeIn"):$("body").fadeIn(100),$(".getby_price, .getby_time, .getby_likes").val("")}),$(".open_menu").on("click",function(e){e.stopPropagation(),transSupport?$(".user_menu").removeClass("slideOutRight").addClass("animated slideInRight").css("visibility","visible"):$(".user_menu").css("right","-240px").animate({right:"0"}).css("visibility","visible"),$(".page_overlay").css("display","block").addClass("activated")}),$(".user_menu_ul li").on("click",function(e){var t=$(this).find("a").attr("href");null!=t&&(window.location=$(this).find("a").attr("href")),e.stopPropagation()}),$(".search_input").on("mousedown",function(){openSearchBox()}),$(".search_input").on("focusout",function(){$(this).attr("placeholder","חפש ארוחה")}),$(".getby_price, .getby_time").on("blur",function(){var e=$(this);e.val()<0?e.val(""):e.val()>999&&e.val(999)}),$(".btn_search_recipe").on("click",function(){$(".search_box").hide(),""==$(".getby_price").val()&&$(".getby_price").val(0),""==$(".getby_time").val()&&$(".getby_time").val(0),""==$(".getby_likes").val()&&$(".getby_likes").val(-1),$(".search_form").submit()}),$(".close_search_box").click(function(){closeSearchBox()}),$(document).on("click",function(e){!$(".page_overlay").hasClass("activated")||$(e.target).hasClass("user_menu_opening")||$(e.target).hasClass("user_menu")||closeUserMenu(),$(".search_box").hasClass("shown")&&($.contains($(".search_wrapper")[0],$(e.target)[0])||closeSearchBox())}),$(document).keyup(function(e){return 27==e.keyCode&&$(".page_overlay").hasClass("activated")?(closeUserMenu(),!0):27==e.keyCode&&$(".search_box").hasClass("shown")?(closeSearchBox(),!0):27==e.keyCode&&$(".show_mm_wrapper").hasClass("mm_active_page")?(transSupport?($(".show_mm_wrapper").removeClass("fadeIn mm_active_page").css({visibility:"hidden",display:"none"}).addClass("animated fadeOut"),$(".mm_tabs").css("display","block").addClass("mm_active_page fadeIn animated")):$(".mm_active_page").fadeOut(100,function(){$(".mm_tabs").fadeIn(200).addClass("mm_active_page")}).removeClass("mm_active_page"),!0):void 0});
