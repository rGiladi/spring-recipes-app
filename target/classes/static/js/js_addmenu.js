function calc_ingrident_price(e){curr_indx=$(".product_price").index(e);var t=+e.val();known_calc_prices[curr_indx]?(add_recipe_total_price+=t-known_calc_prices[curr_indx],known_calc_prices[curr_indx]=t):(known_calc_prices[curr_indx]=t,add_recipe_total_price+=t),$("#sum").text(add_recipe_total_price)}function getDataForAddMenuAjax(){var e={};e.title=$(".add_menu_title").val(),e.freeText=$(".add_menu_freetext").val(),e.price=$("#sum").text();for(var t=1;9>t;t++){var i=[],s=[];$(".mm"+t).find(".products_col").each(function(){$(this).hasClass("menu_recipe_hide")||$(this).find(".products_ajax_wrapper").each(function(e,t){$(t).hasClass("ing_hide")||(0!=$(t).find(".product_ing").val().trim().length&&i.push($(t).find(".product_ing").val()),0!=$(t).find(".product_price").val().trim().length&&s.push($(t).find(".product_price").val()))})}),e["productsRecipe"+t]=i,e["pricesRecipe"+t]=s}return e}function endValidation(){var e=$(".add_menu_title");if(alert(e),0==e.val().trim().length)return e.addClass("alert-danger"),toast("חובה למלא כותרת"),!1;if(recipe_title.val().length>120)return recipe_title.addClass("alert-danger"),toast("הכותרת צריכה להיות בין 1 ל 120 תווים"),!1;recipe_title.removeClass("alert-danger");var t=$(".add_menu_freetext");return t.val()>550?(t.addClass("alert-danger"),toast("הטקסט יכול להיות בין 0 ל 550 תווים"),!1):!0}function ingTop(){var e=$(this).scrollTop();30>e?$(".ingridients_table").css("margin-top",30):$(".ingridients_table").css("margin-top",e)}$(".add_menu_product").on("click",function(){var e=$(this).closest(".products_col").find(".addmenu_products_wrapper").find(".ing_hide");if(0!=e.length){var t=$(e).first();t.removeClass("ing_hide").get(0).scrollIntoView(),t.find(".product_ing").focus()}else toast("עברת את כמות המוצרים המותרת")}),$(".add_menu_meal").on("click",function(){0!=$(".menu_recipe_hide").length?$(".menu_recipe_hide").first().removeClass("menu_recipe_hide").get(0).scrollIntoView():toast("עברת את כמות הארוחות המותרת")});var add_recipe_total_price=0,known_calc_prices=[],curr_indx;$(document).on("focusout",".product_price",function(){calc_ingrident_price($(this))}),$(".add_menu_freetext").on("keyup",function(){var e=$(".re_cha span"),t=$(this).val().length;550>t?e.text(550-$(this).val().length):(e.text(0),$(this).val($(this).val().substr(0,550)))}),$(".add_menu_title").on("keyup",function(){var e=$(".re_cha_t span"),t=$(this).val().length;120>t?e.text(120-$(this).val().length):(e.text(0),$(this).val($(this).val().substr(0,120)))}),$(".add_menu_submit").on("click",function(){if(endValidation()){var e=$('input[name="_csrf"]').val(),t=getDataForAddMenuAjax(),s=$(this).hasClass("edit_menu_submit")?"/updatemenu":"/addmenu";"/updatemenu"==s&&(t.id=$(this).attr("menu_id")),$.ajax({url:s,type:"post",contentType:"application/json",data:JSON.stringify(t),beforeSend:function(t){t.setRequestHeader("X-CSRF-TOKEN",e)},success:function(e){switch(e){case"":window.location="/menus";break;case"err_edit_user":toast("הייתה בעיה, יכול להיות שאין לך את ההרשאות המתאימות בשביל לבצע פעולה זו");break;case"err_edit":toast("הייתה בעיה, נסה שוב מאוחר יותר");break;default:$(".alert-danger").each(function(){$(this).removeClass("alert-danger")});var t=!1,s=2500,a="",r=e.split(", ");for(i=0;i<r.length;i++)".add_menu_title"==r[i]?($(".add_menu_title").addClass("alert-danger").get(0).scrollIntoView(),a+="<div>חובה לתת כותרת לתפריט</div>"):($(r[i]).find(".products_col").addClass("alert-danger"),t||(e.match(/equality/)?e.match(/first3/)&&(a+="<div>לכל מוצר צריך למלא שם ומחיר וחובה למלא לפחות שלוש ארוחות</div>",s+=3e3,t=!0):(a+="<div>חובה למלא לפחות שלוש ארוחות </div>",s+=3e3,t=!0)));toast(a,s)}},xhr:function(){var e=$.ajaxSettings.xhr();return e.upload.onprogress=function(e){$(".progress").css("display","block"),$(".progress-bar").css("width",e.loaded/e.total*100+"%"),$(".progress-bar").attr("aria-valuenow",e.loaded/e.total*100+"%")},e}})}}),$(".by_radio").on("click",function(){$(this).hasClass("by_amount")?($(".mmpil_price").css("display","block"),$(".mmpil_price2").css("display","none"),$(".select_by_oz").css("visibility","hidden"),$(".select_by_amount").css("visibility","visible"),$(".by_oz")[0].checked=!1,$(".mmpil_ing_by").each(function(){$(this).text($(".select_by_amount :selected").text())})):($(".mmpil_price2").css("display","block"),$(".mmpil_price").css("display","none"),$(".mmpil_ing_by").each(function(){$(this).text($(".select_by_oz :selected").text())}),$(".select_by_amount").css("visibility","hidden"),$(".select_by_oz").css("visibility","visible"),$(".by_amount")[0].checked=!1)});var last_value_by_amount=1,last_value_by_oz=1;$(".select_by").on("change",function(){var e=$(this);$(".mmpil_ing_by").each(function(){$(this).text(e.hasClass("select_by_amount")?e[0].value:e.find("option:selected").text())}),$(this).hasClass("select_by_amount")?$(".mmpil_price").each(function(){var t=$(this).text();$(this).text((t/last_value_by_amount*e[0].value).toFixed(2)),last_value_by_amount=e[0].value}):$(".mmpil_price2").each(function(){var t=$(this).text();console.log(t),$(this).text((t/last_value_by_oz*e[0].value).toFixed(2)),last_value_by_oz=e[0].value})});var ing_table_on;window.innerWidth>760?($(document).scroll(function(){ingTop()}),ing_table_on=!0):ing_table_on=!1,$(window).resize(function(){!ing_table_on&&window.innerWidth>760&&$(document).on("scroll",function(){ingTop(),ing_table_on=!0}),window.innerWidth<=760&&ing_table_on&&($(document).off("scroll"),ing_table_on=!1)});
