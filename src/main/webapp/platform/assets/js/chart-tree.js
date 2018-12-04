'use strict';

(function($){
	$(function(){
		var datasource = {
			'id': "rootNode",
			'collapsed': false,
			'name': '商场',
			'title': '擎谱集团',
			'children': [
				{'name': '第一层', 'title': '闪耀星城',
					'children': [ // The property stands for nested nodes. "children" is just default name you can override.
					    { 'name': 'Bo Miao', 'title': 'department manager'},
					    { 'name': 'Su Miao', 'title': 'department manager',
					      'children': [
					        { 'name': 'Tie Hua', 'title': 'senior engineer'},
					        { 'name': 'Hei Hei', 'title': 'senior engineer'}
					      ]
					    },
					    { 'name': 'Yu Jie', 'title': 'department manager'}
					]					
				},
				{'name': '第二层', 'title': '全民总动员',
					'children': [ // The property stands for nested nodes. "children" is just default name you can override.
					    { 'name': 'Bo Miao', 'title': 'department manager'},
					    { 'name': 'Su Miao', 'title': 'department manager',
					      'children': [
					        { 'name': 'Tie Hua', 'title': 'senior engineer'},
					        { 'name': 'Hei Hei', 'title': 'senior engineer'}
					      ]
					    },
					    { 'name': 'Yu Jie', 'title': 'department manager'}
					]	
				},
				{'name': '第三层', 'title': '云之彼端',
					'children': [ // The property stands for nested nodes. "children" is just default name you can override.
					    { 'name': 'Bo Miao', 'title': 'department manager'},
					    { 'name': 'Su Miao', 'title': 'department manager',
					      'children': [
					        { 'name': 'Tie Hua', 'title': 'senior engineer'},
					        { 'name': 'Hei Hei', 'title': 'senior engineer'}
					      ]
					    },
					    { 'name': 'Yu Jie', 'title': 'department manager'}
					]
				},
				{'name': '第四层', 'title': '翱翔蓝天'}
			]
		};
		
		$('#chart-container').orgchart({
			'data': datasource,
			'depth': 2,
			'nodeContent': 'title',
			'nodeID': 'id',
			'zoom': true,
			'zoominLimit': 1,
			'zoomoutLimit': 0.5,
			'pan': true,
			'exportButton': true,
			'createNode': function($node, data){//创建每一个节点的时候都会执行的函数				
				var checkbox = $("<input>", {
					type: 'checkbox',
					style: 'float: right; width: 18px; height: 18px;',
					click: function(){
					}
				});
				$node.find(".title").append(checkbox);
			}
		});
	});
})(jQuery);
