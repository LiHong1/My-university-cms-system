<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-1.7.2.min.js"></script>
</head>
<style type="text/css">
  #t{
    width: 80px;
    height: 80px;
    border:10px solid #d9331a;
    border-radius: 50%;
    background: linear-gradient(to right bottom,transparent 46%,#d9331a 46%,#d9331a 54%,transparent 54%);

  }
  .test1 {
    background: url(resources/small/1336997584046_small.jpg) no-repeat;/**200px **/
  }
  @media screen and (-webkit-min-device-pixel-ratio: 1.5){
    .test1 {
      background: url(resources/small/1336997584046_small.jpg) no-repeat;/**两倍图 400px**/
      background-size: 200px;
    }
  }
  .top,.bottom{
    width: 300px;
    height: 300px;
    border: 1px solid #000;
  }
  /*.box .top{transform: rotateX(90deg) translateZ(150px);}*/
  .box .bottom{transform: rotateX(-40deg) translateZ(150px);}
  .box .left{transform: rotateY(-90deg) translateZ(150px);}
  .box .right{transform: rotateY(90deg) translateZ(150px);}
  .box .back{transform: rotateZ(180deg) translateZ(150px);}
  .box .front{transform: translateZ(150px);}
  #parent{
    width: 700px;
    height: auto;
    position: relative;
    border: 1px solid black;
  }
  /** 左边左浮动，右边加个margin-left;**/
  /*#left{  float: left;  width: 100px;  background: #7EC4CC;  }
  #right{  margin-left: 120px;  background: #ee4433;  }*/
  /**左边左浮动，右边加个overflow:hidden;**/
 /* #left{ float: left;width:100px; background: #7EC4CC;margin-right: 20px}
  #right{ overflow: hidden; background: #ee4433;}*/
  /**左边绝对定位，右边加个margin-left;**/
  /*#left{ position: absolute; top:0; left:0; width:100px; background: #7EC4CC;}
  #right{ margin-left: 120px; background: #ee4433;}*/
  /**左右两边都采用绝对定位**/
  /*#left{ position: absolute; top:0 ; left:0 ;width:100px; background: #7EC4CC;}
  #right{ position: absolute; top:0 ; left:120px;  right:0px;background: #ee4433;}
  #parent2{
      width: 600px;
  }*/
  #left{ display: table-cell;width: 100px;padding-right: 20px; background: #7EC4CC;}
  #right{ display: table-cell;background: #ee4433;}
  #parent2{
      width: 600px;display: table;table-layout: fixed;
  }
  #left2{
      float: left; width: 100px;background: #7EC4CC;position: relative;
  }
  .right-fix{
      float: right;width: 100%;margin-left: -100px;background: #3C6E31;
  }
  #right2{
      margin-left: 120px;background: #ee4433;
  }

  /**css2能解决的方式**/
  /*#parent1{  width: 600px;  height: auto; }
  #left1{  float: left;  width: auto;background: #7EC4CC;margin-right: 20px }
  #right1{  overflow: hidden;background: #ee4433;  }*/

  #parent1{
    display: flex;/*弹性布局 默认有align-items:strentch 能够拉伸每行等高*/
    width: 600px;
    height: auto;
  }
  #left1{
    margin-left: 20px;background: #7EC4CC;margin-right: 20px;
  }
  #right1{
    flex: 1;background: #ee4433;
  }
</style>

<body>
<div id="t"></div>
<%--<div class="box">
  <div class="top"></div>
  <div class="bottom"></div>
  <div class="left"></div>
  <div class="right"></div>
  <div class="front"></div>
  <div class="back"></div>
</div>--%>
<%--<div id="parent">
  <div id="left">固定大小</div>
  <div id="right">自适应大小</div>
</div>--%>

<%--<div id="parent1">
    <div id="left1">随文字不定宽<br>随文字不定宽<br>随文字不定宽</div>
    <div id="right1">自适应大小</div>
</div>--%>

<div id="parent2">
  <div id="left2">left</div>
    <div class="right-fix">
        <div id="right2">right</div>
    </div>
</div>
<form>
<input id="telephone" name="telephone" class="u-text"
        type="tel" maxlength="11" required pattern="^0?(13[0-9]|15[0123456789|18[0236789]|14[57][0-9]{8}$)">
</form>
<script type="text/javascript">
    var flightHandler = function(data){
        alert(data.name);
    };
    var callback = function(data){
        alert(data);
    };
    $(function(){
        $.ajax({type:"get",
                async: false,
                url:"http://localhost:8080/cm-springMVC/getJsonp",
                dataType:"jsonp",
                jsonp: "callBack",
                jsonpCallback:"flightHandler",
                success: function(json){alert(json+"sucess")}});
    });

 /*   var form = document.getElementsByTagName("form")[0];
    alert(form);
    form.telephone.addEventListener('invalid',function(event){
        alert("df");
        event.preventDefault();
        invalidInput(form.telephone,'sdf');
    })*/
</script>
</body>
</html>
