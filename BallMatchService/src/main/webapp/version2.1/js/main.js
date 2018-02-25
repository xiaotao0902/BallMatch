var userData = [ {
	id : "1001",
	name : "访客",
	summary : "新手请多多指教",
	summary1 : "数学天才 访客",
	level : "3.12",
	scoreLevel : "img/scoreLevel1/C.png",
	experience : 35437,
	img : "img/vistorHead.png",
	driver : "img/driver1.png",
	kda : [ 0.6, 0.5, 0.4, 0.3, 0.2, 0.1 ],
	isVip : false,
	startScreenHeadImg:"img/page1/headPic.png",
	isLogin:false
}, {
	id : "1002",
	name : "董路",
	summary : "我真tm的是个天才",
	summary1 : "数学天才",
	level : "4.82",
	scoreLevel : "img/scoreLevel1/C+.png",
	experience : 35437,
	img : "img/head2.png",
	driver : "img/driver2.png",
	kda : [ 0.6, 0.5, 0.4, 0.3, 0.2, 0.1 ],
	isVip : true,
	startScreenHeadImg:"img/page1/vistorPic.png",
	isLogin:true
} ];

function formatTime(time){
	var minutes=Math.floor(time/60);
	var mm=time%60;
	if(mm<10){
		mm="0"+mm;
	}
	if(minutes<10){
		minutes="0"+minutes;
	}
	return minutes+":"+mm;
}

var contextRoot="/ballMatch/bs/game";
var websocketPath="ws://localhost:8080/ballMatch/matchSocket";

function draw(z,h,data,id){
	var x1=h;
	var y1=z-z*data[0];
	
	var z2=z*data[1];
	var x2=h+z2*Math.sqrt(3)/2;
	var y2=z-z2/2;
	
	var z3=z*data[2];
	var x3=h+z3*Math.sqrt(3)/2;
	var y3=z+z3/2;
	
	var x4=h;
	var y4=z+z*data[3];
	
	var z5=z*data[4];
	var x5=h-z5*Math.sqrt(3)/2;
	var y5=z+z5/2;
	
	var z6=z*data[5];
	var x6=h-z6*Math.sqrt(3)/2;
	var y6=z-z6/2;
	
	//获取Canvas对象(画布)
	var canvas = document.getElementById(id);
	//简单地检测当前浏览器是否支持Canvas对象，以免在一些不支持html5的浏览器中提示语法错误
	if(canvas.getContext){
		//获取对应的CanvasRenderingContext2D对象(画笔)
		var ctx = canvas.getContext("2d");
		//开始一个新的绘制路径
		ctx.beginPath();
		//设置线条颜色为蓝色
		ctx.strokeStyle = "rgba(0,163,161,0.3)";
		ctx.fillStyle='rgba(0,163,161,0.3)';
		//设置路径起点坐标
		/* ctx.moveTo(65.5, 0);
		//绘制直线线段到坐标点(60, 50)
		ctx.lineTo(131, 37.5);
		//绘制直线线段到坐标点(60, 90)
		ctx.lineTo(131, 112.5);
		ctx.lineTo(65.5, 150);
		ctx.lineTo(0, 112.5);
		ctx.lineTo(0, 37.5); */
		ctx.moveTo(x1, y1);
		//绘制直线线段到坐标点(60, 50)
		ctx.lineTo(x2, y2);
		//绘制直线线段到坐标点(60, 90)
		ctx.lineTo(x3, y3);
		ctx.lineTo(x4, y4);
		ctx.lineTo(x5, y5);
		ctx.lineTo(x6, y6);
		
		ctx.fill();
		//先关闭绘制路径。注意，此时将会使用直线连接当前端点和起始端点。
		ctx.closePath();
		//最后，按照绘制路径画出直线
		ctx.stroke();
	}
}

function getStyle(obj,attr){
	return obj.currentStyle?obj.currentStyle[attr]:getComputedStyle(obj,0)[attr];
}

function startMoveNew(obj,json,fn){
	clearInterval(obj.timer);
	obj.timer=setInterval(function(){
		var ifStop=true;
		for(var attr in json){
			//取当前值
			var iCur=0;
			if(attr=='opacity'){
				iCur=parseInt(parseFloat(getStyle(obj,attr))*100);
			}else{
				iCur=parseInt(getStyle(obj,attr));
			}
			
			var iSpeed=(json[attr]-iCur)/8;
			iSpeed=iSpeed>0?Math.ceil(iSpeed):Math.floor(iSpeed);
			if(iCur!=json[attr]){
				ifStop=false;
			}
			if(attr=='opacity'){
				obj.style.filter='alpha(opacity:'+(iCur+iSpeed)+')';
				obj.style.opacity=(iCur+iSpeed)/100;
			}
			else{
				obj.style[attr]=iCur+iSpeed+"px";
			}
		}
		if(ifStop){
			clearInterval(obj.timer);
			if(fn){
				fn();
			}
		}
	},30);
}