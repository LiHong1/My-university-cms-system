<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>图片滚动</title>
</head>
<body>
<style type="text/css">
    #demo {
        background: #FFF;
        overflow:hidden;
        border: 1px dashed #CCC;
        width: 500px;
    }
    #demo img {
        border: 3px solid #F2F2F2;
    }
    #indemo {
        float: left;
        width: 800%;
    }
    #demo1 {
        float: left;
    }
    #demo2 {
        float: left;
    }
</style>
<div id="demo">
    <div id="indemo">
        <div id="demo1">
            <a href="#"><img src="resources/small/1337065203421_small.jpg" border="0" /></a>
            <a href="#"><img src="resources/small/1336998514781_small.jpg" border="0" /></a>
            <a href="#"><img src="resources/small/1336997584046_small.jpg" border="0" /></a>
            <a href="#"><img src="resources/small/1336998620593_small.jpg" border="0" /></a>
            <a href="#"><img src="resources/small/1337065203421_small.jpg" border="0" /></a>
            <a href="#"><img src="resources/small/1336999028781_small.jpg" border="0" /></a>
        </div>
        <div id="demo2"></div>
    </div>
</div>
<script>

    var speed=10;
    var tab=document.getElementById("demo");
    var tab1=document.getElementById("demo1");
    var tab2=document.getElementById("demo2");
    tab2.innerHTML=tab1.innerHTML;
    function Marquee(){
        if(tab2.offsetWidth-tab.scrollLeft<=0)
            tab.scrollLeft-=tab1.offsetWidth
        else{
            tab.scrollLeft++;
        }
    }
    var MyMar=setInterval(Marquee,speed);
    tab.onmouseover=function() {clearInterval(MyMar)};
    tab.onmouseout=function() {MyMar=setInterval(Marquee,speed)};

</script>
</body>
</html> 
