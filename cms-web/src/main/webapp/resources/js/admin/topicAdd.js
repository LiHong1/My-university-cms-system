function beforeClick(treeId, treeNode) {
    var check = (treeNode && !treeNode.isParent);
    if (!check) alert("只能选择子栏目...");
    return check;
}

function onDbClick(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("myTree");
    nodes = zTree.getSelectedNodes();
    var cname = $("#cname");
    cname.attr("value", nodes[0].name);
    var cId = $("#cid");
    cId.attr("value", nodes[0].id);
}
function openTree(event, treeId, treeNode, msg) {
    $.fn.zTree.getZTreeObj("myTree").expandAll(true);
}
//显示树结构的区域
function showMenu() {
    var cityObj = $("#cname");
    var cityOffset = $("#cname").offset();
    $("#menuContent").css({
        left: cityOffset.left + "px",
        top: cityOffset.top + cityObj.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
        hideMenu();
    }
}
function createAttachNode(attach) {
    /*<tr>
     <Td>文件名缩略图</Td>
     <td width="180">文件名</td>
     <td>文件大小</td>
     <td>主页图片</td>
     <td>栏目图片</td>
     <td>附件信息</td>
     <td width="160">操作</td>
     </tr>*/
    var isIndex = "";
    var isAttach = "";
    var isColumnImg = "";
    var thumbnail = "";
    if (attach.isAttach) {
        isAttach = "<input type='checkbox' name='isAttach' class='.isAttach' checked='checked' value=" + attach.id + ">";
    } else {
        isAttach = "<input type='checkbox' name='isAttach' class='.isAttach' value=" + attach.id + ">";
    }
    if (attach.isImg) {
        if (attach.isIndexPic) {
            isIndex = "<input type='checkbox' name='isIndex' class='isIndex' checked='checked' value=" + attach.id + ">";
        } else {
            isIndex = "<input type='checkbox' name='isIndex' class='isIndex' value=" + attach.id + ">";
        }
        thumbnail = "<img src='" + $("#ctx").val() + "/resources/upload/thumbnails/" + attach.newName + "'>";
        isColumnImg = "<input type='radio' value='" + attach.id + "' name='channelPicId' id='channelPicId'>"
    } else {
        thumbnail = "是普通类型附件";
    }

    var a = "<tr><td><input type='hidden' name='attachIds' value='" + attach.id + "'>" + thumbnail + "</td>"
        + "<td>" + attach.oldName + "</td>"
        + "<td>" + attach.size + "KB</td>"
        + "<td>" + isIndex + "</td>"
        + "<td>" + isColumnImg + "</td>"
        + "<td>" + isAttach + "</td>"
        + "<td><a href='#' class='list_op insertAttach' title='" + attach.id + "' isImg='" + attach.isImg + "' name='" + attach.newName + "' oldName='" + attach.oldName + "'>插入附件</a> <a href='#' title='" + attach.id + "' class='list_op deleteAttach delete'>删除附件</a></td>"

        + "</tr>";
    return a;
}
$(function () {
    var uploadPath = $(ctx).val() + "/resources/upload/";
    $("#cname").focus(function (e, treeId, treeNode) {
        showMenu();
    });
    var editor = $('#content').xheditor({tools: 'full'});

    var validate = $("#addForm").cmsvalidate();
    $("#addBtn").click(function () {
        if (validate.valid()) {
            $("#addForm").submit();
            $(this).attr("disabled");
        }
    });
    $("#ok_attach").on("click", ".deleteAttach", function () {
        var conf = confirm("确定删除附件信息吗？");
        if (conf) {
            var ad = this;
            var id = $(this).attr("title");
            dwrService.deleteAttach(id, function (data) {
                $(ad).parent("td").parent("tr").remove();
                $("#xhe0_iframe").contents().find("#attach_" + id).remove();
            });
        }
    });
    $("#ok_attach").on("click", ".isIndex", function () {
        dwrService.changeIsIndex($(this).val());
    });
    $("#ok_attach").on("click", ".isAttach", function () {
        dwrService.changeIsAttach($(this).val());
    });
    $("#ok_attach").on("click", ".insertAttach", function () {
        var node = "";
        var isImg = $(this).attr("isimg");
        if (isImg == 1) {
            node = "<img src='" + uploadPath + $(this).attr("name") + "' id='attach_" + $(this).attr("title") + "'/>"
        } else {
            node = "<a href='" + uploadPath + $(this).attr("name") + "' id='attach_" + $(this).attr("title") + "'>" + $(this).attr("oldName") + "</a>"
        }
        editor.pasteHTML(node);
    });
    $("#myTree").myTree({
        url: $("#ctx").val() + "/admin/channel/treeAll",
        expendAll: true,
        callback: {
            beforeClick: beforeClick,
            onDblClick: onDbClick,
            onAsyncSuccess: openTree
        }
    });
    $("#keyword").keywordinput({
        autocomplete: {
            enable: true,
            url: $("#ctx").val() + "/admin/topic/searchKeyword",
            minLength: 2
        }
    });
    $("#uploadFile").click(function () {
        $("#attach").uploadify("upload", "*");
    });
    $("#attach").uploadify({
        swf: $("#ctx").val() + '/resources/uploadify/uploadify.swf',
        uploader: $("#ctx").val() + '/admin/topic/upload;jsessionid=' + $("#sid").val(),
        fileObjName: "attach",
        auto: false,
        formData: {"sid": $("#sid").val()},
        fileTypeExts: "*.jpg;*.gif;*.png;*.doc;*.docx;*.txt;*.xls;*.xlsx;*.rar;*.zip;*.pdf;*.flv;*.swf",
        onUploadSuccess: function (file, data, response) {
            var ao = $.parseJSON(data);
            var suc = $.ajaxCheck(ao.ajaxObj);
            if (suc) {
                var node = createAttachNode(ao.ajaxObj.obj);
                $("#ok_attach").find("tbody").append(node);
            }
        }
    });
});