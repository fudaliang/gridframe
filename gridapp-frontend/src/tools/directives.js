import Vue from 'vue'
Vue.directive('dialogDrag', {
	bind(el, binding, vnode, oldVnode) {
		const dialogHeaderEl = el.querySelector('.el-dialog__header');
		const dragDom = el.querySelector('.el-dialog');
		dialogHeaderEl.style.cursor = 'move';
		//获取dom元素
		const sty = dragDom.currentStyle || window.getComputedStyle(dragDom, null)
		dialogHeaderEl.onmousedown = (e) => {
			//记录鼠标按下时的位置
			const disX = e.clientX - dialogHeaderEl.offsetLeft;
			const disY = e.clientY - dialogHeaderEl.offsetTop;
			let styL, styT
			//判断是否为%
			if(sty.left.includes('%')) {
				styL = +document.body.clientWidth * (+sty.left.replace(/\%/g, '') / 100);
				styT = +document.body.clientHeight * (+sty.top.replace(/\%/g, '') / 100);
			} else {
				styL = +sty.left.replace(/\px/g, '');
				styT = +sty.top.replace(/\px/g, '');
			}
			//鼠标按下移动 计算位置
			document.onmousemove = function(e) {
				const l = e.clientX - disX;
				const t = e.clientY - disY;
				dragDom.style.left = `${l + styL}px`;
				dragDom.style.top = `${t + styT}px`;
			}
			//清空事件
			document.onmouseup = function(e) {
				document.onmousemove = null;
				document.onmouseup = null;
			}
		}
	}
})
Vue.directive('dialogDragWidth', {
	bind(el, binding, vnode, oldVonde) {
		const dragDom = binding.value.$el.querySelector('.el-dialog');
		el.onmousedown = (e) => {
			//记录初始位置
			const disX = e.clientX - el.offsetLeft
			document.onmousemove = function(e) {
				e.preventDefault(); //组织默认事件
				const l = e.clientX - disX;
				dragDom.style.width = `${l}px`
			}
			document.onmouseup = function(e) {
				document.onmousemove = null;
				document.onmouseup = null;
			}
		}
	}
})