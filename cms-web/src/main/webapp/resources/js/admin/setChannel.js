var t;
$(document).ready(function(){
      t=$("#tree").myTree({
    	mine:{
            listChild:0
        },
        url: $("#treePath").val(),
        check: {
            enable: true,
            chkboxType: { "Y": "p", "N": "ps" }
        },
        callback: {
            beforeCheck:function(treeId, treeNode) {
                    var childrens=new Array();
                    getCheckChildNodes(treeNode,treeNode.checked,childrens);
                    childrens.push(treeNode.id);
                    if(treeNode.checked)
                    {   
                    	deleteGroupChannel(childrens);
                     }
                    else
                    {
                         var ps=getCheckParentNodes(treeNode,false);
                         ps.push(treeNode.id);
                    	 addGroupChannel(ps);
                        
                    }
            
            },
            onCheck:function(event,treeId, treeNode) {
            	
                    if(!treeNode.checked)
                    {
                    	var ps=getCheckParentNodes(treeNode,false);
                    	deleteGroupChannel(ps);
                    }
                       
            },
            onAsyncSuccess: function (event, treeId, treeNode, msg) {
                 initTree();
            }, 
            
        }
    });
   
   
});

function getCheckParentNodes(treeNode,checked){
    var ps = new Array();
    treeNode=treeNode.getParentNode();
    while(treeNode){
        if(treeNode.checked==checked)
        ps.push(treeNode.id);
      
        treeNode=treeNode.getParentNode();
    }
    return ps;
};
function getCheckChildNodes(treeNode,checked,childrens){
    var cs;
    if((cs=treeNode.children)){
    	for(var i=0;i<cs.length;i++) {
    		if(cs[i].checked==checked) {
    		childrens.push(cs[i].id);
    		}
    		getCheckChildNodes(cs[i],checked,childrens);
    	}
    }
    return t;
}
function addGroupChannel(cs) {
    var gid = $("#gid").val();
    for(var i=0;i<cs.length;i++) {
        var c = cs[i];
        if(c>0) {
            dwrService.addGroupChannel(gid,c);
        }
    }
    
}

function deleteGroupChannel(cs) {
    var gid = $("#gid").val();
    for(var i=0;i<cs.length;i++) {
        var c = cs[i];
        if(c>0) {
            dwrService.deleteGroupChannel(gid,c);
        }
    }
    
}
function initTree() {
    t.expandAll(true);
    var cids = $("input[name='cids']");
    for(var i=0;i<cids.length;i++) {
        var cid = cids[i].value;
        var n = t.getNodeByParam("id",cid,null);
        t.checkNode(n,true,true);
    }
}
function setChild(cids) {
	for(var i=0;i<cids.length;i++) {
		var cid = cids[i];
		alert(cid);
		var n = t.getNodeByParam("id",cid,null);
		t.checkNode(n,true,true);
	}
}