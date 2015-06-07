<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id=PageSelectorBar>
    <div id=PageSelectorMemo>
        页次：${pageBean.currentPage}/${pageBean.pageCount }页 &nbsp;
        每页显示：${pageBean.pageSize }条 &nbsp;
        总记录数：${pageBean.totalCount }条
    </div>
    <div class="manu" >
 
         <span  onClick="javascript: gotoPage(${pageBean.currentPage-1})"> <  Prev</span>
        <c:forEach begin="${pageBean.beginPageIndex}" end="${pageBean.endPageIndex}" var="num">
             <c:if test="${num eq pageBean.currentPage}">
                <span class="current">${num}</span>
             </c:if>
              
             <c:if test="${num != pageBean.currentPage}">
                <span  onClick="gotoPage(${num});">${num}</span>
            </c:if>
        </c:forEach>
     
       <span onClick="javascript: gotoPage(${pageBean.currentPage+1})">Next  >  </span>
    
        
        
        转到：
        <select onchange="gotoPage(this.value)" id="_pn">
            <c:forEach begin="1" end="${pageBean.pageCount}" var="num">
                  <option value="${num}">${num}</option>
            </c:forEach>
            
        </select> 
        <script type="text/javascript">
            $("#_pn").val("${pageBean.currentPage}");
        </script>
        
    </div>
</div>
<script type="text/javascript">
    function gotoPage( pageNum ){
        // window.location.href = "forum_show.action?id=${id}&pageNum=" + pageNum;
        if(pageNum>0&&pageNum<${pageBean.pageCount+1}){
            $(document.getElementById("form")).append("<input type='hidden' name='pageNum' value='" + pageNum +"'>");
            document.getElementById("form").submit();
        }
        
    }
</script>
