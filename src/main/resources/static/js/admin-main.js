$(function() {
    $(window).bind("load resize", function() {
        topOffset = 50;
        width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse')
            topOffset = 100; // 2-row-menu
        } else {
            $('div.navbar-collapse').removeClass('collapse')
        }
        height = (this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height;
        height = height - topOffset -1;
        if (height < 1) height = 1;
        if (height > topOffset) {
            $("#page-wrapper").css("min-height", (height) + "px");
			$("#mainframe").height(height - 8 + "px");
        }
    });
	$("#side-menu ul.nav").hide();
	$("#side-menu li").find("i").parent().click(function(){
		if($(this).next().is('ul:visible')){
			$(this).next().slideUp();
			$(this).parent().addClass("active");
		}
		else{
			$(this).next().slideDown();
			if($(this).parent().is(".active")){
				$(this).parent().removeClass("active");
			}
		}
		
	});
	$("#side-menu li li a").click(function(){
		$("#side-menu li li a").css("background-color","none");
		$(this).css("background-color","#eeeeee");
	});
	$('.timeShowDv').calendar({    
		current:new Date(),
		onSelect: function(date){
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			var time =  y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
			$(this).prev().find(".time-input").val(time);
			$(this).hide();
		}
	});
	$('.time-btn').click(function(event){
		event.stopPropagation();
		var obj = $(this).parent().parent().next();
		if(obj.is(":hidden")){
			obj.show();
		}
		else{
			obj.hide();
		}
	});
	$(document).click(function(){
		var obj = $(".timeShowDv");
		if(obj.is(":visible")){
			obj.hide();
		}
	});
	$('.timeShowDv').click(function(event){
		event.stopPropagation();
	});
})