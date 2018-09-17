function createView()
{

	var pXmlData;
	

	var currentPage = 1;	
	this.getCurrentPage = function(){
		return currentPage;
	}	

	var totalPage = 1;	
	this.getTotalPage = function(){
		return totalPage;
	}	


	this.path = "";	
	this.setPath = function(path){		 
		this.path = path;		
	}
	

	var parameter = "";
	this.addParameter = function(name,value){
		this.tmpParameter = "";
		this.tmpParameter = name + ":" + value+",";
		parameter = parameter + this.tmpParameter;		
	}

	this.moveNextPage = function(){	
		onPageProcess("onNextPage");		
	}


	this.moveLastPage = function(){
		onPageProcess("onEndPage");
	}	

	this.movePrevPage = function(){
		onPageProcess("onPrevPage");
	}

	this.moveFirstPage = function(){
		onPageProcess("onFirstPage");
	}
	

	var pageRatio = Math.floor(enumMMToPixel[nNowMMToPixel] * 100) + '%';
	this.getPageRatio = function(){
		return pageRatio;
	}
	this.zoomIn = function(){				
		onPageProcess("onZoomIn");
	}

	this.zoomOut = function(){				
		onPageProcess("onZoomOut");
	}

	this.zoomOriginal = function(){
		onPageProcess("onZoomKeep");
	}	
	
	this.print = function(){
		
		this.element = document.getElementById('idCanvas');		 
		this.imageData = saveReportImageData('idCanvas',pXmlData,currentPage);
		this.widht = parseInt(this.element.width) + 10;
		this.height = parseInt(this.element.height) + 10;
		
		//html문서로 보이는 그림
		this.winHandle = window.open("","print","toolbar=no,scrollbars=yes,resizable=yes,width="+ (parseInt(this.widht) + 15)+ ",height="+this.height);
		if(this.winHandle != null)
		{
			this.htmlString = "<html><head><title>print</title></head>";
			this.htmlString += "<body>";
			for(var i = 0;i < this.imageData.length;i++){
				this.htmlString += "<a href=javascript:window.close()><image src=" + this.imageData[i] + " border=0 alt=close></a>";
			}
			this.htmlString += "</body></html>";
			this.winHandle.document.open();
			this.winHandle.document.write(this.htmlString);
			this.winHandle.document.close();
			this.winHandle.print().focus();
		}
		
	}
	var delayTime = false;
	this.isLoaded = function(){
		return delayTime;
	}
	

	this.run = function(){
		delayTime = false;
		currentPage = 1;		
		totalPage = 1;
		nNowMMToPixel = 6;
		pageRatio = Math.floor(enumMMToPixel[nNowMMToPixel] * 100) + '%';
	
		if(parameter.length > 0){
			parameter = parameter.slice(0, (parseInt(parameter.length) - 1));
		}
		document.getElementById("loadingDiv").style.display = "";
	
		
		$.ajax({
		    type: "GET",
		    url: this.path,
		    data: {	            	
            	path : this.path,
            	parameter : parameter
            },
            success:function(data){	            	
            	
            	pXmlData = new loadXML(data);	
            
            	
            	var error = drawReport('idCanvas',pXmlData,currentPage);            
            	totalPage = pXmlData.getPageCount();         
            	document.getElementById("loadingDiv").style.display = "none";    
            	
            	delayTime = true;	
            	parameter = "";            	
            	if(error == null){
            		alert("Error : Report Draw fail!");
            	}
            },
            error:function(request){
            	document.getElementById("loadingDiv").style.display = "none";
            	var errorMessage = new Array;
            	errorMessage["errorName"] = request.status;
            	errorMessage["errorString"] = request.responseText;
            	
            	var modal = window.showModalDialog("Error.html",errorMessage,"dialogWidth:340px;dialogHeight:190px;center:yes"); 
            	
            	//alert("code : " + request.status + "\r\nMessage : " + request.responseText);
           	}
            });

		delayTime = false;
	}	

	function onPageProcess(processID){		
		if(processID == "onNextPage"){		
			if(currentPage < totalPage){			
				currentPage++;			
			}
		}else if(processID == "onEndPage"){
			currentPage = totalPage;
		}else if(processID == "onPrevPage"){
			if(currentPage > 1){
				currentPage--;				
			}		
		}else if(processID == "onFirstPage"){
			currentPage = 1;
		}else if(processID == "onZoomIn"){			
			if(nNowMMToPixel > 0){
				nNowMMToPixel--;								
			}						
		}else if(processID == "onZoomOut"){
			this.nCount = enumMMToPixel.length - 1;
			if(nNowMMToPixel < nCount){
				nNowMMToPixel++;							
			}
		}else if(processID == "onZoomKeep"){
			nNowMMToPixel = 6;
		}
		pageRatio = Math.floor(enumMMToPixel[nNowMMToPixel] * 100) + '%';
		drawReport('idCanvas',pXmlData,currentPage);
	}
}