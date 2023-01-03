<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>list</title>
</head>
<style>
	#tabs { display: flex; border-bottom: 1px solid #3367d6; }
	#tabs li {
		width: 140px;  line-height: 40px;  color:#3367d6; cursor: pointer;
		border: 1px solid #3367d6; border-bottom: none; margin-right: 0;
	}
	#tabs li:not(:first-child) { border-left: none; }
	#tabs li.active { background-color:#3367d6; color: #fff;}
	#tab-content { width: 1200px; height: 550px; margin: 20px auto; } 
	.c3-chart-arc text, .c3-axis, .c3-chart { font-size: 16px !important }
	/* #legend { display: flex; justify-content: center; } */
	#legend { display: none; justify-content: center; }
	#legend li { display: flex; align-items: center; }
	#legend li:not(:last-child) { margin-right: 30px; }
	.legend { width: 15px; height: 15px; margin-right: 5px; margin-top: 5px; }
</style>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/c3/0.7.20/c3.css"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/c3/0.7.20/c3.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/5.16.0/d3.min.js"></script>
<script>
/* 문서가 준비된 이후에 이벤트 등록 */
$(function(){
	$('#tabs li').on('click', function(){
		if($(this).hasClass('active')) return; // 현재 선택된 탭인 경우
		$('#tabs li').removeClass();
		$(this).addClass('active');
		
		var idx = $(this).index();
		
		// 선택한 탭의 경우별 선택 라디오 버튼 보이게
		$('.tab').css('display', 'none');
		$('.tab').eq(idx).css('display', 'block');
		
		if(idx==0){
			department();
		}else{
			hirement();
		}
	});
	// 클릭 이벤트 강제 발생
	//$('#tabs li').eq(0).trigger('click');
	$('#tabs li').eq(1).trigger('click');
	
	$('.legend').each(function(idx){
		$(this).css('background-color', colors[idx]);
	});
	
	// 년도별/월별 라디오버튼, top3 체크박스 클릭시
	$('[name=unit], #top3').change(function(){
		hirement();
	});
})

// 채용인원 수 시각화
function hirement(){
	init();
	
	// top3 체크박스 클릭시
	if($('#top3').prop('checked')){
		hirement_top3();
	}
	// 년도별/월별 라디오버튼 클릭시
	else{
		hirement_company();
	}
}
function hirement_top3(){
	var unit = $('[name=unit]:checked').val();
	$.ajax({
		url: 'visual/hirement/top3/' + unit,
		success: function( response ){
			console.log( response );
			/* 데이터 편집 */
			var info = [];
			if(unit=='year'){
				info.push(['부서 명','2001','2002','2003','2004','2005',
					'2006','2007','2008','2009','2010'])
				$(response).each(function(){
					info.push( new Array( this.DEPARTMENT_NAME, this.Y2001, this.Y2002
							, this.Y2003, this.Y2004, this.Y2005, this.Y2006
							, this.Y2007, this.Y2008, this.Y2009, this.Y2010) );
				});
			}else{
				
			}
			/* 편집한 데이터로 그래프 그리기 */
			c3.generate({
				bingo: '#graph',
				data: { columns: info, x: '부서 명', type: unit=='year'?'bar' :'line', labels: true},
				axis: { x: 'category' }
			});
		},error: function(req,text){
			alert(text+':'+req.status);
		}
	});
}

function hirement_company(){
	var unit = $('[name=unit]:checked').val();
	$.ajax({
		url: 'visual/hirement/' + unit,
		success: function( response ){
			//console.log( response );
			var name = [ unit ], count=['채용 인원 수'];
			/* 정보 가져옴 */
			$(response).each(function(){
				name.push(this.UNIT);
				count.push(this.COUNT);
			});
			/* 그래프 그림 */
			make_chart_hirement([name, count]);
		},error: function(req,text){
			alert(text+':'+req.status);
		}
	});
}
/* 그래프 그림 */
function make_chart_hirement(info){
	c3.generate({
		bindto: '#graph',
		data: {
			columns: info, type: 'bar', labels: true,
			x: $('[name=unit]:checked').val(),
			/* 데이터에 맞는 색 지정 */
			color: function(c, d){
				return colors[Math.floor(d.value/10)];
			}
			
		},
		axis: {
			x: { type: 'category'},
			y: { label: { text:info[1][0], position: 'outer-top' } }
		},
		bar: { width: 30 },
		size: { height: 450 },
		grid: { y: {show: true} },
		legend: { hide: true }
	});	
	/* 범례 보이게 */
	$('#legend').css('display','flex');
}

// 부서원 수 시각화
function department(){
	init();
	$.ajax({
		url: 'visual/department',
		success: function(response){
			console.log(response);
			var count = ['부서원 수'] , name=['부서 명'], info = [];
			$(response).each(function(){
				count.push(this.COUNT);				// 선 그래프, 막대 그래프
				name.push(this.DEPARTMENT_NAME);	// 선 그래프, 막대 그래프
				info.push([this.DEPARTMENT_NAME,this.COUNT]);	// 파이 그래프, 도넛 그래프
			});
			console.log(count);
			//make_chart(new Array(count));
			//make_chart([name, count]);	// 선 그래프, 막대 그래프
			//make_chart(info);				// 파이 그래프, 도넛 그래프
			if($('[name=graph]:checked').val() == 'bar'){
				bar_chart([name, count]);
			}else if($('[name=graph]:checked').val() == 'donut'){
				donut_chart(info);
			}
		}
	});
}

function make_chart(info){
	// 1. 선 그래프
	//line_chart(info);
	// 2. 파이 그래프
	//pie_chart(info);
	// 3. 도넛 그래프
	//donut_chart(info);
	// 4. 막대 그래프
	bar_chart(info);
	
}

var colors = ['#FB9B58','#47628D','#FF5D82','#000000','#00B96B',
				'#C982EB','#FF6868','#7270E5','#347D82','#889310',
				'#935710','#D8B893','#59004B'];

function bar_chart(info){
	c3.generate({
		bindto: '#graph',
		data :  { 
			columns: info, 
			type: 'bar', 
			x: '부서 명',
			labels: true,
			color: function(color, data){
				//return colors[data.index];	// 부서별 색 지정
				return colors[Math.floor(data.value/5)];		// 특정 인원수별 색 지정
			}
		},
		axis: {
			x: { type:'category', tick: { rotate:-60 } },
			/* y: { label: { text: '부서원수', position: 'outer-bottom' } } */
			y: { label: { text: info[1][0], position: 'outer-middle' } }
		},
		size: { height:450 },
		bar: { width:30 },
		grid: { y: { show: true } },
		legend: { hide: true }
	});
	/* 막대 일 때만, 범례 표시 */
	$('#legend').css('display', 'flex');
}

function donut_chart(info){
	c3.generate({
		bindto: '#graph',
		data: { columns: info, type: 'donut'},
		size: { height: 450 },
		padding: { bottom:50 },
		donut: {
			label: {
				format: function(value,raito,id){
					return (raito*100).toFixed(1)+'%('+value+'명)';
				}
			},
			width: 100,
			title: '부서원 수'
		}
	});
}

function pie_chart(info){
	c3.generate({
		bindto: '#graph',
		data: { columns: info, type: 'pie'},
		size: { height: 450 },
		pie: {
			label: {
				format: function(value,raito,id){
					//return ratio*100+'%';	//31.5% -> 0.315
					return (raito*100).toFixed(1)+'%('+value+'명)';
				}
			}
		},
		padding: { bottom:50 }
	});
}

function line_chart(info){
	var chart = c3.generate({
		bindto: '#graph',	//#chart 아니면 이름 지정해야 함
	    data: {
	        //columns: [['부서원 수', 30, 200, 100, 400, 150, 250]]
	    	columns: info,
	    	x: '부서 명',
	    },
	    axis: {
    		x:{
    			type: 'category'
    		}
    	}
	});
}
</script>
<body>
	<!-- c3 chart 라이브러리 사용 -->
	<!-- https://cdnjs.com/libraries/c3 -->
	<h3>시각화</h3>
	<div class='w-px1200' style="margin: 0 auto">
		<ul id='tabs'>
			<!-- <li class='active'>부서원 수</li> -->
			<li>부서원 수</li>
			<li>채용인원 수</li>	
		</ul>
	</div>
	<div id='tab-content'>
		<!-- 그래프 선택 라디오 버튼 -->
		<div class='tab'>
			<label><input type='radio' name='graph' value='bar' checked>막대그래프</label>
			<label><input type='radio' name='graph' value='donut'>도넛그래프</label>
		</div>
		<!-- 년도별/월별 선택 라디오 버튼 -->
		<div class='tab'>		
			<label><input type='checkbox' id='top3'>TOP3부서</label>
			<label><input type='radio' name='unit' value='year' checked>년도별</label>
			<label><input type='radio' name='unit' value='month'>월별</label>
		</div>
		<!-- 그래프 표현할 부분 -->
		<!-- <div id='chart'></div> -->
		<div id='graph'></div>
		<!-- 범례 표현할 부분 -->
		<ul id='legend'>
			<li><span class='legend'></span><span>0~9명</span></li>
			<li><span class='legend'></span><span>10~19명</span></li>
			<li><span class='legend'></span><span>20~29명</span></li>
			<li><span class='legend'></span><span>30~39명</span></li>
			<li><span class='legend'></span><span>40~49명</span></li>
			<li><span class='legend'></span><span>50~59명</span></li>
			<li><span class='legend'></span><span>60명이상</span></li>
		</ul>
	</div>
	<script>
		//그래프 초기화
		function init(){
			$('#graph').empty();
			$('#legend').css('display','none');
		}
		$('[name=graph]').on('change', function(){
			init();
			department();
		});
	</script>
</body>
</html>