/**
 * Created by Knight on 2015/8/28.
 */
function request(paras){
	//截取浏览器参数函数
	var url = location.href;
	var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");
	var paraObj = {}
	for (i=0; j=paraString[i]; i++){
		paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length);
	}
	var returnValue = paraObj[paras.toLowerCase()];
	if(typeof(returnValue)=="undefined"){
		return "";
	}else{
		return returnValue;
	}
}
var countdown=60;
function goBack(){
	if ((navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0)){ // IE
		if(history.length > 0){
			window.history.go( -1 );
		}else{
			window.opener=null;window.close();
		}
	}else{ //非IE浏览器
		if (navigator.userAgent.indexOf('Firefox') >= 0 ||
			navigator.userAgent.indexOf('Opera') >= 0 ||
			navigator.userAgent.indexOf('Safari') >= 0 ||
			navigator.userAgent.indexOf('Chrome') >= 0 ||
			navigator.userAgent.indexOf('WebKit') >= 0){

			if(window.history.length > 1){
				window.history.go( -1 );
			}else{
				if(navigator.userAgent.indexOf('MicroMessenger') >= 0){
					WeixinJSBridge.call('closeWindow');
				}else{
					window.opener=null;window.close();
				}

			}
		}else{ //未知的浏览器
			window.history.go( -1 );
		}
	}
}
function settime(val) {
	if (countdown == 0) {
		val.removeAttribute("disabled");
		var reg = new RegExp('(\\s|^)' + 'forbidclick' + '(\\s|$)');
		val.className = val.className.replace(reg, ' ');
		val.innerHTML="发送验证码";
		countdown = 60;
		return true;
	} else {
		val.setAttribute("disabled", true);
		val.innerHTML="重新发送(" + countdown + "秒)";
		countdown--;
	}
	setTimeout(function() {
		settime(val)
	},1000)
}
$("#radio").click(function(){
	$(this).toggleClass("checked")
});
$("#back").click(function(){
	//history.go(-1)
	goBack()
});
$(".back").click(function(){
	//history.go(-1)
	goBack()
});
$("#changephonenum").click(function(){
	swal({title:"",text:"<p style='text-align: left!important;'>如需变更注册手机号，请联系长安基金提交“授权委托书信息变更表”（联系电话：021-20329793）</p>",html: true})
});
$(".CheckBoxZoom p.type").click(function(){
	$(this).parent().find("p").removeClass("queren")
	$(this).addClass("queren")
})
$(".CheckBoxZoom p.SOF").click(function(){
	$(this).toggleClass("queren")
})
