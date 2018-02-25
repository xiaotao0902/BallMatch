var userData=[{id:"1001",name:"Jack",summary:"新手请多多指教",scoreLevel:"img/scoreLevel/D.png",
	img:"img/headImg.png",driver:'img/driver1.png'},
 {id:"1002",name:"Pony",summary:"三十年未逢敌手",scoreLevel:"img/scoreLevel/A.png",
		img:"img/headImg_dingjunhui.png",driver:'img/driver2.png'}];

var contextRoot="/ball/bs/game";
var websocketPath="ws://localhost:8087/ball/matchSocket";

// whoStart score 保存在sessionStorage

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

function MoveMenu(divId,menuId){
	this.oMenuDiv=document.getElementById(divId);
	this.oMenu=document.getElementById(menuId);
	this.oMenuDiv.style.top=-1*this.oMenuDiv.offsetHeight+"px";
	var _this=this;
	this.oMenu.onclick=function(ev){
		_this.menuClick(ev);
	}
	document.onclick=function(){
		_this.docClick();
	}
}

MoveMenu.prototype.menuClick=function(ev){
	var ev=ev||event;
	startMoveNew(this.oMenuDiv,{'top':50});
	ev.stopPropagation();
}

MoveMenu.prototype.docClick=function(){
	startMoveNew(this.oMenuDiv,{'top':-1*this.oMenuDiv.offsetHeight});
}

function getStyle(obj,attr){
	return obj.currentStyle?obj.currentStyle[attr]:getComputedStyle(obj,0)[attr];
}

function startMove(obj,attr,iTarget,fn){
	clearInterval(obj.timer);
	obj.timer=setInterval(function(){
		//取当前值
		var iCur=0;
		if(attr=='opacity'){
			iCur=parseInt(parseFloat(getStyle(obj,attr))*100);
		}else{
			iCur=parseInt(getStyle(obj,attr));
		}
		
		var iSpeed=(iTarget-iCur)/8;
		iSpeed=iSpeed>0?Math.ceil(iSpeed):Math.floor(iSpeed);
		if(iCur==iTarget){
			clearInterval(obj.timer);
			if(fn){
				fn();
			}
		}else{
			if(attr=='opacity'){
				obj.style.filter='alpha(opacity:'+(iCur+iSpeed)+')';
				obj.style.opacity=(iCur+iSpeed)/100;
			}
			else{
				obj.style[attr]=iCur+iSpeed+"px";
			}
		}
	},30);
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