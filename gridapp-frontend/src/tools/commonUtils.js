var CommonUtil = {

};
CommonUtil.install = function (Vue) {
  Object.defineProperties(Vue.prototype, {
    $CU: {
      get() {
        return CommonUtil;
      }
    }
  });
};
// 全角转半角
CommonUtil.toCDB = function(textString) {
  var temp = '';
  for (let i = 0; i < textString.length; i++) {
    if(textString.charCodeAt(i) > 65248 && textString.charCodeAt(i) < 65375) {
      temp += String.fromCharCode(textString.charCodeAt(i) - 65248);
    }else if(textString.charCodeAt(i) === 12288) { //全角空格的情况
      temp += String.fromCharCode(32);
    }else{
      temp += String.fromCharCode(textString.charCodeAt(i));
    }
  }
  return temp;
};
// 半角转全角
CommonUtil.toDBC = function(textString) {
  var temp = '';
  for (let i = 0; i < textString.length; i++) {
    if(textString.charCodeAt(i) == 32) {
      temp += String.fromCharCode(12288);
    }
    if(textString.charCodeAt(i) < 127) {
      temp += String.fromCharCode(textString.charCodeAt(i) + 65248);
    }
  }
  return temp;
};
// 时间戳转时间
CommonUtil.toTime = function(textString) {
	var temp = '';
	var date = new Date(textString);
	var Y = date.getFullYear() + "-";
	var M = (date.getMonth()+1 < 10 ? "0"+(date.getMonth()+1) : date.getMonth()+1)+"-";
	var D = date.getDate()+" ";
	var h = date.getHours()+":";
	var m = date.getMinutes() >= 10 ? date.getMinutes()+":" : '0'+(date.getMinutes())+":";
	var s = date.getSeconds() >= 10 ? date.getSeconds() : '0'+(date.getSeconds());
	temp = Y+M+D+h+m+s
	return temp
};
export default CommonUtil
