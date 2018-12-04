	//监听鼠标移动事件，实现图元高亮显示效果
	var currentElement;
	var highlightColor = '#E8DFC4';//鼠标滑过背景颜色
	function unhighlight(element){
	    element.setStyle(Q.Styles.BACKGROUND_COLOR, null);
	    currentElement.setStyle(Q.Styles.PADDING, null);
	}
	function highlight(element){
	    if(currentElement == element){
	        return;
	    }
	    if(currentElement){
	        unhighlight(currentElement);
	    }
	    currentElement = element;
	    if(!currentElement){
	        return;
	    }
	    currentElement.setStyle(Q.Styles.BACKGROUND_COLOR, highlightColor);
	    currentElement.setStyle(Q.Styles.PADDING, new Q.Insets(5));
	}