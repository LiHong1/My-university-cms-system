(function($){
	$.fn.keywordinput=function(){
		$(this).wrap("<div id='keyword-container'></div>");
		$(this).before("<div id='keywords-wrap'></div>");
		$(this).click(function(){
		     $(this).val("");
		}).blur(function(){
			$(this).val("请输入关键字，通过逗号或空格");
		}).keydown(function(event){
			if(event.keyCode==13||event.keyCode==32){
				var value=$.trim($(this).val());
				 var keywords=$(".keyword span");
				 var exist=false;
				 for(var i=0;i<keywords.length;i++){
					 var keyword=$(keywords[i]).html();
					 if(keyword==value)
						 exist=true;
				 } 
				 if(value!=''&&exist==false)
				 {
					 $("#keywords-wrap").append("<div class='keyword'><span>"+value+"</span> <a href='#'>×</a><input type='hidden' name='aks' value='"+value+"'/></div> ");
				 }
				 $(this).val("");
			}
			  
		});
		$("#keywords-wrap").on("click",".keyword a",function(){
			$(this).parent(".keyword").remove();
		});
	}
	
})(jQuery);