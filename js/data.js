/**
* @author amos57
*/

function slowScroll(id){
	
	$('html,body').animate({
		scrollTop: $(id).offset().top
	},500);
}

$(document).on("scroll",function(){
	if($(window).scrollTop()===0)
		$("header").removeClass("fixed");
	else
		$("header").attr("class","fixed");
	
});

$(document).ready(function(){
	$("#mess_send").on("click",function(){
		var name=$("#name").val();
		var email=$("#email").val();
		var message=$("#message").val();
		$.ajax({
			url:"message",
			type: "POST",
			data :{"name":name,"email":email,"message":message},
			dataType: "json",
			success: function(dat){
				console.log(dat);
				window.location.href="http://192.168.1.224:8080/message";

			},
			error: function(e){
				console.log(e);


			}
		});
		
	});
	
}); 