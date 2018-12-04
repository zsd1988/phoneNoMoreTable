	//******创建一个显示图片作为背景的插件******
	function ImageBackground(graph, options) {
	    this.graph = graph;
	    graph.delayedRendering = false;
	    graph.background = this;
	    graph.onPropertyChange('transform', this.update.bind(this));
	
	    this.canvas = Q.createCanvas(graph.width, graph.height);
	    this.canvas.style.position = 'absolute';
	    this.canvas.style.top = '0px';
	    this.canvas.style['-webkit-user-select'] = 'none';
	    this.canvas.style['-webkit-tap-highlight-color'] = 'rgba(0, 0, 0, 0)';
	
	    graph.canvasPanel.insertBefore(this.canvas, graph.canvasPanel.firstChild);
	
	    this._options = options;
	
	    this.graph._getOverviewScaleInfo = function () {
	        var elementBounds = this.bounds;
	        if (this.background && this.background.bounds) {
	            elementBounds = elementBounds.union(this.background.bounds);
	        }
	        if (!elementBounds.isEmpty()) {
	            var xScale = this.width / elementBounds.width;
	            var yScale = this.height / elementBounds.height;
	            var scale = Math.max(xScale, yScale);
	            scale = Math.max(this.minScale, Math.min(this.maxScale, scale));
	            return {scale: scale, cx: elementBounds.cx, cy: elementBounds.cy};
	        }
	    }
	    this.graph.zoomToOverview = function (byAnimate, maxScale) {
	        return this.callLater(function () {
	            var scaleInfo = this._getOverviewScaleInfo();
	            if (scaleInfo) {
	                if (maxScale) {
	                    scaleInfo.scale = Math.min(scaleInfo.scale, maxScale);
	                }
	                this.centerTo(scaleInfo.cx, scaleInfo.cy, scaleInfo.scale, false);
	            }
	        }, this);
	    }
	}
	
	ImageBackground.prototype = {
	    update: function () {
	        if (!this._imageTarget) {
	            return;
	        }
	        //Q.callLater(function () {
	            var image = this._imageTarget;
	            var graph = this.graph;
	            var canvas = this.canvas;
	
	            canvas.width = graph.width;//clear canvas
	            canvas.height = graph.height;//clear canvas
	
	            var g = canvas.g;
	
	            g.save();
	            g.translate(graph.tx, graph.ty);
	            g.scale(graph.scale, graph.scale);
	
	            g.drawImage(image, 0, 0, image.width, image.height);
	
	            g.restore();
	        //}.bind(this));
	    },
	    _doTransform: function (g, scale, bounds) {
	        g.translate(-scale * bounds.x, -scale * bounds.y);
	        g.scale(scale, scale);
	    },
	    _onImageLoad: function (evt) {
	        if (evt.target != this._imageTarget) {
	            return;
	        }
	        this.bounds = new Q.Rect(0, 0, this._imageTarget.width, this._imageTarget.height);
	        this.update();
	        if (this._options && this._options.onload instanceof Function) {
	            this._options.onload(this);
	        }
	    }
	}
	
	Object.defineProperties(ImageBackground.prototype, {
	    image: {
	        get: function () {
	            return this._image;
	        },
	        set: function (v) {
	            this._image = v;
	            if (!v) {
	                this._imageTarget = null;
	                return;
	            }
	            var image = this._imageTarget = document.createElement('img');
	            image.src = this._image;
	            image.onload = this._onImageLoad.bind(this);
	        }
	    }
	});