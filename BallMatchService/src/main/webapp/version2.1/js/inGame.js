$(document).ready(function(){
	
	var timer=null;
	$(".left").css("height",$(document).height()); 
	
	//init user infor
	var whoStart=window.sessionStorage.getItem("whoStart");
	if(whoStart==null){
		whoStart=0;
	}
	whoStart=parseInt(whoStart);
	
	var score=window.sessionStorage.getItem("score");
	var scoreArr=[];
	if(!!score){
		scoreArr=score.split(",");
	}else{
		scoreArr=[0,0];
	}
	
	initUserInfor();
	function initUserInfor(){
		$("#btn1").attr("src",userData[whoStart].img);
		$(".infor li").eq(0).html(userData[whoStart].name);
		$(".infor li").eq(1).html(userData[whoStart].summary1);
		$(".infor li").eq(2).html(userData[whoStart].level);
		$(".infor li").eq(3).html(userData[whoStart].experience);
		$(".scoreLevel").attr("src",userData[whoStart].scoreLevel);
		$(".body1").css("background","url('../img/scorepanel/"+scoreArr[whoStart]+".png') no-repeat,"+
						"url('../img/ingame/bodyBg.png') no-repeat");
		
		var startTime=50;
		timer=setInterval(function(){
			startTime--;
			if(startTime==0){
				clearInterval(timer);
				timeDiv.style.color="red";
			}
			$("#time").html(formatTime(startTime));
		},1000);
		
		var html='<li class="li1">R8<img class="img1" src="img/score/1.png" /><img src="img/score/8.png" /></li>'+
			'<li>R7<img src="img/score/2.png" /><img src="img/score/3.png" /></li>'+
			'<li>R6<img src="img/score/4.png" /><img src="img/score/5.png" />'+
			'<img src="img/score/6.png" /></li>'+
			'<li>R5<img src="img/score/7.png" /></li>'+
			'<li>R4<img src="img/score/10.png" /><img src="img/score/12.png" />'+
			'<img src="img/score/13.png" /><img src="img/score/14.png" />'+
			'<img src="img/score/15.png" /></li>'+
			'<li>R3<img src="img/score/9.png" /></li>'+
			'<li>R2<img src="img/score/11.png" /></li>';
		$(".steps").append(html);
	}
	
	var li='';
	for(var i=1;i<16;i++){
		li+='<li><img src="img/ball/'+i+'.png" /></li>';
	}
	$(".ball").append(li);
	
	var flag=true;	
	$(".ball img").click(function(){
		if(flag){
			flag=false;
			$(this).animate({"width":"100px","left":"-25px","top":"-25px","opacity":"0.2"},"slow").
			animate({"width":"50px","left":"0","top":"0","opacity":"1"},"slow",function(){
				flag=true;
			});
		}
	});
	
	$("#extend").hover(function(){
		$(this).attr("src","img/ingame/extension1.png");
	},function(){
		$(this).attr("src","img/ingame/extension.png");
	});
	
	$("#change").hover(function(){
		$(this).attr("src","img/ingame/changePlayer1.png");
	},function(){
		$(this).attr("src","img/ingame/changePlayer.png");
	});
	
	
	
	$("#btn1").click(function(){
		$(".li1").removeClass("li1");
		var html='<li class="li1">R8<img class="img1" src="img/score/1.png" /><img src="img/score/8.png" /></li>';
		$(".steps").prepend(html);
	});
	
	//lianjiLi
	var oTab=document.getElementById("tab");
	var oLiOfTab=oTab.getElementsByTagName("li");
	
	
	
	var zIndex=10;
	
	for(var i=0;i<2;i++){
		oLiOfTab[i].index=i;
		oLiOfTab[i].innerHTML=userData[i].name+" Win";
		oLiOfTab[i].onclick=function(){
			var player=this.index==0?"A":"B";
			$.ajax({
				type: "GET",
	        	url: contextRoot+"/setPlayer?player="+player,
	        	dataType:"json",
	        	success: function(data){
	            	console.log(data);
	        	}
			});
			scoreArr[this.index]++;
			window.sessionStorage.setItem("score",scoreArr.join(","));
			window.sessionStorage.setItem("whoStart",this.index);
			window.location.href="vsPlayerProflieScore.html";
		}
	}
	
	oLiOfTab[2].onclick=function(){
		$("#tab").animate({"right":"-300px"},"slow",function(){
			$(".ball").find("li").eq(7).find("img").attr("img/ball/8.png");
		});
	}
	
	$("#extend").click(function(){
		startTime=100;
	});
	
	$("#change").click(function(){
		var player=0;
		whoStart=whoStart==0?1:0;
		window.sessionStorage.setItem("whoStart",whoStart);
		
		//判断是不是vip 如果是 则跳转页面，如果不是，则不跳转
		if(userData[whoStart].isVip){
			window.location.href="inGame1.html";
		}else{
			$("#time").html("00:50");
			player=whoStart==0?"A":"B";
			$.ajax({
				type: "GET",
	        	url: contextRoot+"/changePlayer",
	        	data: {
	        		"player":player
	        	},
	        	dataType:"json",
	        	success: function(data){
	            	console.log(data);
	            	
	            	initUserInfor();
	        	}
			}); 
		}
	});
	
	var websocket = null;
	//判断当前浏览器是否支持WebSocket
	if ('WebSocket' in window) {
	    websocket = new WebSocket(websocketPath);
	}
	else {
	    alert('当前浏览器 Not support websocket');
	}

	//连接发生错误的回调方法
	websocket.onerror = function () {
		console.log("WebSocket连接发生错误");
	};

	//连接成功建立的回调方法
	websocket.onopen = function () {
		console.log("WebSocket连接成功");
	}

	//接收到消息的回调方法
	websocket.onmessage = function (event) {
		console.log(event);
		//1 表示不进 0 表示进球
		//{"player":"A","balls":[0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1]} 
	    //setMessageInnerHTML(event.data);
		var data=event.data;
		console.log("data "+data);
		data=JSON.parse(data);
		var balls=data.balls;
		console.log("balls "+balls);
		var oldBalls=window.sessionStorage.getItem("balls");
		console.log("oldBalls",oldBalls);
		//判断是不是第一次打
		if(!!oldBalls){
			var oldBallArr=oldBalls.split(",");
			for(var i=1;i<16;i++){
				if(balls[i]==0&&oldBallArr[i]==1){
					getAGoal(ballArr[i-1],true);
				}
			}
		}else{
			for(var i=1;i<16;i++){
				if(balls[i]==0){
					getAGoal(ballArr[i-1],true);
				}
			}
		}
		console.log("data.player",data.player);
		
		
		//切换用户
		var player=data.player=="A"?0:1;
		window.sessionStorage.setItem("whoStart",player);
		playerName.innerHTML=userData[player].name+"<span>"+scoreArr[player]+"</span>";
		
		playerName.style.background="url('../newversion/img/playerName"+(player+1)+".png') no-repeat -180px";
		timeDiv.innerHTML="00:50"; 
		
		window.sessionStorage.setItem("balls",balls.join(","));
	}

	//连接关闭的回调方法
	websocket.onclose = function () {
	    console.log("WebSocket连接关闭");
	}

	//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
	window.onbeforeunload = function () {
	    closeWebSocket();
	}

	//将消息显示在网页上
	function setMessageInnerHTML(innerHTML) {
	    document.getElementById('message').innerHTML += innerHTML + '<br/>';
	}

	//关闭WebSocket连接
	function closeWebSocket() {
	    websocket.close();
	}

	//发送消息
	function send() {
	    var message = document.getElementById('text').value;
	    websocket.send(message);
	}
});