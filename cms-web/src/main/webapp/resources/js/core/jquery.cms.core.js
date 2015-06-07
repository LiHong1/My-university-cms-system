(function($){
	//判读 返回ajexObj的值
	$.ajaxCheck = function(data) {
		if(data.result) return true;
		else {
			alert(data.msg);
			return false;
		}
	}
	//ztree 代码开始----------------------------------------------
	var t;
	$.fn.myTree=function(opts){
		    var setting = $.extend({
		    	 view: {
		                dblClickExpand: false,
		                showLine: true,
		                selectedMulti: false
		            },
		           /* check: {
		                enable: false,
		                //chkStyle: "checkbox"
		            }*/
		            data: {
		                simpleData: {
		                    enable:true,
		                    idKey: "id",
		                    pIdKey: "pid",
		                    rootPId: "-1"
		                }
		            },
		            async:{
		                enable: true,
		                url: opts?opts.url||$("#treePath").val()||"treeAll":"treeAll",
		                autoParam:["id=pid"],//用来指定树的传递参数的key的名称
		                otherParam: { "ids":"1", "name":"test"}//用来传递其它的参数，ids=1,name=test
		            },
		            callback: {
		                beforeClick: function(treeId, treeNode) {
		                },
		                onClick: function(event,treeId, treeNode) {
		                },
		                onAsyncSuccess: function (event, treeId, treeNode, msg) {
		                	if(setting.expendAll==true){
		        		    	t.expandAll(true);
		        		    }
		                }
		                
		            },
		           
		            mine:{
		            	 srcElement:"#cc",
		            	 listChild:1
		            }
		    },opts||{});
		    
		    var _mine=setting.mine;
		    
		    if(_mine.listChild){
		    	setting.callback.onClick=listChild; 
		    }
		    function listChild(event,treeId, treeNode){
		    	$(_mine.srcElement).attr("src","channels/"+treeNode.id);
		    }
		    var t=$.fn.zTree.init($(this), setting); 
		    
		    	
		    return   t;
	 };
	//ztree 代码结束----------------------------------------------
	//表格排序代码开始-----------------------------------------------
	$.fn.sortTable=function(opts){
		var settings = $.extend({
			begin:"#beginOrder",
			save:"#saveOrder"
		},opts||{});
		var _isOrder=false;
		var sortEle=$(this).find("tbody");
		var _that=$(this);
		sortEle.sortable({
			axis:"y",
			helper:function(e,ele){
				//原始的td对象
				var _original=ele.children();
				var _helper=ele.clone();
				$(_helper).children().each(function(index){
					$(this).width(_original.eq(index).width());
				});
				_helper.css("background","#aaf");
				return _helper;
			},
			update:function(e,ui){
				setOrders();
			}
		});
		var setOrders=function(){
			$(_that).find("tbody tr").each(function(index){
				if(_isOrder==false)
		           $(this).append("<td>"+(index+1)+"</td>");
				else $(this).find("td:last").html(index+1);
		     });
		};
		sortEle.sortable("disable");
		$(settings.begin).click(function(){
			    if(!_isOrder){
			    	$("thead tr").append("<td>顺序</td>");
		            setOrders();
		            $("tfoot tr").append("<td>拖动排序</td>");
		            $(".listTable tbody").sortable("enable");
		            _isOrder=true;
			    }else alert("已经处于排序状态");
				
		});
		$(settings.save).click(function(){
			var ids=sortEle.sortable("serialize",{key:"ids"});
	        if(_isOrder){
	        	$(".listTable tr").each(function(index){
	        		$(this).children().last().remove();
	        	});
	           
	            $.post("updateSort?"+ids,function(date){
	            	 sortEle.sortable("disable");
	 	            _isOrder=false;
	            	  parent.refreshTree();
	            });
	           
	        }else alert("不处于排序状态");
	    });
	
	};
	//表格排序代码结束----------------------------------------------------
	
	
	$.fn.myaccordion = function(opts) {
		var settings = $.extend({
			selectedClz:"navSelected",
			titleTagName:"h3"
		},opts||{});
		var titleNode = $(this).find("ul>"+settings.titleTagName);
		var selectedNode = $(this).find("ul."+settings.selectedClz+">"+settings.titleTagName);
		titleNode.css("cursor","pointer");
		titleNode.nextAll().css("display","none");
		selectedNode.nextAll().css("display","block");
		titleNode.click(function(){
			var checked = $(this).parent().hasClass(settings.selectedClz);
			if(checked) {
				$(this).parent().removeClass(settings.selectedClz);
				$(this).nextAll().slideUp();
			} else {
				$(this).parent().addClass(settings.selectedClz);
				$(this).nextAll().slideDown();
			}
		});
	};
	
	$.fn.trColorChange = function(opts) {
		var settings = $.extend({
			overClz:"trMouseover",
			evenClz:"trEvenColor"
		},opts||{});
		$(this).find("tbody tr:even").addClass(settings.evenClz);
		$(this).find("tbody tr").on("mouseenter mouseleave",function(){
			$(this).toggleClass(settings.overClz);
		});
	};
	
	$.fn.confirmOperator = function(opts) {
		var settings = $.extend({
			msg:"该操作不可逆，确定进行该操作吗？",
			eventName:"click"
		},opts||{});
		$(this).on(settings.eventName,function(event){
			if(!confirm(settings.msg)) {
				event.preventDefault();
			}
		});
	}
})(jQuery)