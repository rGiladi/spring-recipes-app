$(".fa-users").on("click",function(){var e=$('input[name="_csrf"]').val();$.ajax({url:"/user/updatePublishedMenu?menu="+$(this).attr("ref_id"),type:"post",beforeSend:function(t){t.setRequestHeader("X-CSRF-TOKEN",e)},success:function(e){switch(e){case"0":$(".fa-users").attr("title","פרסם לכולם").removeClass("menu_published");break;case"1":$(".fa-users").attr("title","הורד מפרסום").addClass("menu_published");break;case"permission":alert("שגיאה");break;case"error":alert("שגיאה")}}})}),$(".btn_delete_menu").on("click",function(){var e=$('input[name="_csrf"]').val();$.ajax({url:"/user/deleteMenu?menu="+$(this).attr("ref_id"),type:"post",beforeSend:function(t){t.setRequestHeader("X-CSRF-TOKEN",e)},success:function(e){"1"==e?window.location.reload():toast("יש בעיה, אנא נסה שוב")}})}),$(".fa-users").on("click",function(){$(this).hasClass("mm_published")?$(this).removeClass("mm_published"):$(this).addClass("mm_published")});
