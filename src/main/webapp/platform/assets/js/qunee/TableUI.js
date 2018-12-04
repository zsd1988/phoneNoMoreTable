var TableUI = function (data) {
    Q.doSuperConstructor(this, TableUI, arguments);
}
TableUI.prototype = {
    cellWidth1: 80,
    cellWidth2: 120,
    cellHeight: 25,
    measure: function () {
        if (!this.data) {
            this.setMeasuredBounds(0, 0);
            return;
        }
        var width = 0, height = 0;
        if (this.data.header) {
            height += this.cellHeight;
            width = this.cellWidth1 + this.cellWidth2;
        }
        if (this.data.data && this.data.data.length) {
            var rows = this.data.data.length;
            height += rows * this.cellHeight;
            if (!width) {
                width = this.data.data[0].length * this.cellWidth;
            }
        }
        //计算画布的高和宽
        this.setMeasuredBounds(width, height);
    },
    drawCell: function (g, x, y, background, align, color, content, cellWidth, cellHeight) {    	
        var text;
        if (content instanceof Object && !Q.isString(content)) {
        	//如果传递进来的显示内容不是文本，则转换成文本绘制
            text = '' + content.text;
            color = content.color || color;
            align = content.align || align;
        } else {
            text = '' + content;
        }
        if (background) {
            g.fillStyle = background;
            g.fillRect(x, y, cellWidth, cellHeight);
            //绘制一个矩形，充当表格的边框
            g.strokeStyle = '#FFF';
            g.strokeRect(x, y, cellWidth, cellHeight);
        }
        if (align) {
            if(align == 'center'){
                x += cellWidth / 2;
            }else if(align == 'right'){
                x += cellWidth;
            }
            g.textAlign = align;
        }
        g.textBaseline = 'middle';
        g.fillStyle = color;
        g.fillText(text, x, y + cellHeight / 2);
    },
    draw: function (g, scale, selected) {
    	//draw供qunee插件调用，参数g是canvascontext2d对象
        if (!this.data) {
            return;
        }
        //绘制一个矩形
        g.fillStyle = '#EEE';
        g.fillRect(0, 0, this.originalBounds.width, this.originalBounds.height);

        var header = this.data.header;
        var data = this.data.data;

        var x = 0, y = 0;
        var colindex = 1;
        var cellWidth1 = this.cellWidth1,cellWidth2 = this.cellWidth2, cellHeight = this.cellHeight;
        if (header) {
            header.forEach(function (name) {
            	if(colindex == 1){//如果是第一列，则宽度小一些
            		this.drawCell(g, x, y, '#EAEDF4', 'center', '#000', name, cellWidth1, cellHeight);
            		x += cellWidth1;
            	}else{
            		this.drawCell(g, x, y, '#FFFAE8', 'center', '#000', name, cellWidth2, cellHeight);
            		x += cellWidth2;
            	}
            	colindex+=1;
            }.bind(this))
            y+= cellHeight;
        }
        if(data){
            data.forEach(function(row, index){
                x = 0;
                colindex = 1;
                row.forEach(function (name) {
                	if(colindex == 1){
	            		this.drawCell(g, x, y, '#EAEDF4', 'center', '#000', name, cellWidth1, cellHeight);
	            		x += cellWidth1;
	            	}else{
	            		this.drawCell(g, x, y, '#FFFAE8', 'center', '#000', name, cellWidth2, cellHeight);
	            		x += cellWidth2;
	            	}
	            	colindex+=1;                	
                }.bind(this))
                y+= cellHeight;
            }.bind(this));
        }
    }
}
Q.extend(TableUI, Q.BaseUI);