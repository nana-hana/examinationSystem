
function queryTestClick(eiid) {
	$.ajax({
		url : "/administrator/queryTest.do",
		type : "post",
		data : {
			"eiid" : eiid
		},
		success : function(data) {
			//				layer.msg(data);
			var dataJson = JSON.parse(data); //转成json对象
			var testStr = "";//存放所有题目
			var checkStr = "";//存放判断题
			var singleTestStr = "";//存放单选题
			var multupleStr = "";//存放多选题
			
			if(dataJson.single == "empty"){
				testStr += "单选题 ： 没有单选题</br></br>";
			}else{
				testStr += "题型：单选题</br></br>";
				var singleJson = dataJson.single;
				for (var i in singleJson) {
					singleTestStr += "问题：" + singleJson[i].question + "</br>";
					singleTestStr += "选项A：" + singleJson[i].answerA + "</br>";
					singleTestStr += "选项B：" + singleJson[i].answerB + "</br>";
					singleTestStr += "选项C：" + singleJson[i].answerC + "</br>";
					singleTestStr += "选项D：" + singleJson[i].answerD + "</br>";
					singleTestStr += "正确答案选项：" + singleJson[i].trueAnswer + "</br>";
					singleTestStr += "题目难度：" + singleJson[i].level + "</br></br>";
				}
				testStr += singleTestStr;
			}
			if(dataJson.multuple == "empty"){
				testStr += "多选题 ： 没有多选题</br></br>";
			}else{
				testStr += "题型：多选题</br></br>";
				var multupleJson = dataJson.multuple;
				for (var i in multupleJson) {
					multupleStr += "问题：" + multupleJson[i].question + "</br>";
					multupleStr += "选项A：" + multupleJson[i].answerA + "</br>";
					multupleStr += "选项B：" + multupleJson[i].answerB + "</br>";
					multupleStr += "选项C：" + multupleJson[i].answerC + "</br>";
					multupleStr += "选项D：" + multupleJson[i].answerD + "</br>";
					multupleStr += "正确答案选项：" + multupleJson[i].trueAnswer + "</br>";
					multupleStr += "题目难度：" + multupleJson[i].level + "</br></br>";
				}
				testStr += multupleStr;
			}
			if(dataJson.check == "empty"){
				testStr += "判断题 ： 没有判断题</br></br>";
			}else{
				testStr += "题型：判断题</br></br>";
				var checkJson = dataJson.check;
				for (var i in checkJson) {
					checkStr += "问题：" + checkJson[i].question + "</br>";
					checkStr += "选项A：" + checkJson[i].answerA + "</br>";
					checkStr += "选项B：" + checkJson[i].answerB + "</br>";
					checkStr += "正确答案选项：" + checkJson[i].trueAnswer + "</br>";
					checkStr += "题目难度：" + checkJson[i].level + "</br></br>";
				}
				testStr += checkStr;
			}
			layer.open({
				type : 1,
				area : [ '600px', '360px' ],
				shadeClose : true, //点击遮罩关闭
				content : '\<\div style="padding:20px;font-size:large;color: darkgray;font-family: simsun;">'+ testStr +'\<\/div>'
			});
		}
	});
}