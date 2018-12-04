	//*************创建一个放大镜插件*************
	function MagnifyingInteraction(graph){
	    this.graph = graph;
	    this.topCanvas = graph.topCanvas;
	}
	MagnifyingInteraction.prototype = {
	    onstart: function(evt, graph){
	        this._addDrawable();
	        this.ondrag(evt, graph);
	    },
	    onrelease: function(){
	        this._removeDrawable();
	        this.currentPoint = null;
	    },
	    ondrag: function(evt, graph){
	        this.currentPoint = graph.toLogical(evt);
	        this.topCanvas.invalidate();
	    },
	    _removeDrawable: function () {
	        if (this._id) {
	            this.topCanvas.removeDrawable(this._id);
	            delete this._id;
	            this.topCanvas.invalidate();
	        }
	    },
	    _addDrawable: function () {
	        if (this._id && this.topCanvas.contains(this._id)) {
	            return;
	        }
	        this._id = this.topCanvas.addDrawable(this.draw, this).id;
	        this.topCanvas.invalidate();
	    },
	    scale: 2,
	    draw: function (g, scale) {
	        if(!this.currentPoint){
	            return;
	        }
	        var xy = this.currentPoint;
	        var graph = this.graph;
	        var size = 200;
	        var ratio =  graph.ratio || 1;
	        var scale = Math.min(graph.maxScale, graph.scale * this.scale);
	        size /= scale;
	        var renderScale = scale * ratio;
	        var clipBounds = new Q.Rect(xy.x - size * 0.5, xy.y - size * 0.5, size, size)
	        var imageInfo = graph.exportImage(renderScale, clipBounds);
	        g.save();
	        g.translate(xy.x, xy.y);
	        g.scale(1 / graph.scale / ratio, 1 / graph.scale / ratio);
	        g.fillStyle = '#FFF';
	        g.strokeStyle = '#fcfb9b';
	        g.lineWidth = 10;
	        g.beginPath();
	        // var x = -imageInfo.width / 2, y = -imageInfo.height / 2, width = imageInfo.width, height = imageInfo.height;
	        // g.rect(x, y, width, height);
	        g.arc(0, 0, imageInfo.width / 2, 0, 2 * Math.PI, false);
	        g.stroke();
	        g.clip();
	        g.fill();
	        g.drawImage(imageInfo.canvas, -imageInfo.width / 2, -imageInfo.height / 2)
	        g.restore()
	    }
	}
	Q.Defaults.registerInteractions('magnifying', [MagnifyingInteraction, Q.WheelZoomInteraction, Q.DoubleClickInteraction, Q.TooltipInteraction]);