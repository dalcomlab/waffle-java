var aErrorList = [];

function loadXML(xml)
{
	var pTotalStyle = new loadStyle(xml);

	this.getStyle = function()
	{
		return pTotalStyle;
	}	

	var pPageList = new loadPageList(xml,pTotalStyle);		
	this.getPageCount = function()
	{		
		return pPageList.getPageCount();
	}
	this.getPage = function(nPageCount)
	{		
		return pPageList.getPage(nPageCount);
	}
}


function loadStyle(xml)
{
  	var pDefaultStyle = createDefaultStyle();
	var pUserStyle = [];

	var tmpStyle;

    $("default-style",xml).each(function(i) {      
     	processStyleData($(this),pDefaultStyle,false);      	      
    });

    $("style",xml).each(function(i) { 
      tmpStyle = processStyleData($(this),pDefaultStyle,true);	
      if(tmpStyle == null){
    	  return null; 
      }else{
    	  pUserStyle.push(tmpStyle);
      }
      
    });    

    this.getDefaultStyleCount = function()
	{
		return pDefaultStyle.length;
	}
	this.getDefaultStyleData = function(nSel)
	{
		return pDefaultStyle[nSel];
	}
	this.getDefaultStyle = function()
	{
		return pDefaultStyle;
	}

	this.getUserStyleCount = function()
	{
		return pUserStyle.length;
	}
	this.getUserStyleData = function(nSel)
	{
		return pUserStyle[nSel];
	}
	this.getUserStyle = function()
	{
		return pUserStyle;
	}
}

function loadPageList(xml,pTotalStyle)
{
	
	var pPageList = new createPageList();
	
	$("page",xml).each(function(i) {
		var pPage = new createPage();
		
		var pageStyleName = $(this).attr('page-style');
		if(pageStyleName != null && pageStyleName != ""){			
			pPage.setPageStyle(processGetTotalStyle(pageStyleName,pTotalStyle));
		}else{
			pPage.setPageStyle(processStylePage($(this),pTotalStyle));
		}

	  	$("control",$(this)).each(function(i) {
	  		var type = $(this).attr('control-type');	
	  		var pControl = null;  	

	  		if(type == "table"){	 
	  			pControl = loadControlTable($(this),pTotalStyle,0,0,0,0);
	  		}else if(type == "textbox"){	  
	  			pControl = loadControlLabel($(this),pTotalStyle,0,0,0,0);
	  		}else if(type == "line"){
	  			pControl = loadControlLine($(this),pTotalStyle,0,0,0,0);
	  		}else if(type == "shape"){
	  			pControl = loadControlShape($(this),pTotalStyle,0,0,0,0);
	  		}	

	  		if(pControl != null){
	  			pPage.addControl(pControl);	  		
	  		}	  	 	
	  	});

	  	pPageList.addPage(pPage);	  
	});
	
	return pPageList;
}

function processGetStyle(styleName,pStyle,bNeworOldStyle)
{
	var nStyleCount = pStyle.length;	
	function tmpDefaultStyle() {}	
	
	for(var i = 0;i < nStyleCount;i++){
		if(styleName == pStyle[i].family){	
			if(bNeworOldStyle == true){
				tmpDefaultStyle.prototype = pStyle[i];
				return new tmpDefaultStyle();
			}else{
				return pStyle[i];
			}
		}
	}	
	//alert(styleName);
	aErrorList.push(styleName + "error ");
	return null;
}

function processGetTotalStyle(styleName,pTotalStyle)
{
	var nDefaultStyleCount = pTotalStyle.getDefaultStyleCount();	
	var nUserStyleCount = pTotalStyle.getUserStyleCount();	
	function tmpDefaultStyle() {}
	
	
	for(var i = 0;i < nDefaultStyleCount;i++){
		if(styleName == pTotalStyle.getDefaultStyleData(i).family){				
			tmpDefaultStyle.prototype = pTotalStyle.getDefaultStyleData(i);
			return new tmpDefaultStyle(); 
		}
	}
	
	for(var i = 0;i < nUserStyleCount;i++){		
		if(styleName == pTotalStyle.getUserStyleData(i).name){		
			tmpDefaultStyle.prototype = pTotalStyle.getUserStyleData(i);
			return new tmpDefaultStyle(); 
		}
	}
	aErrorList.push(styleName+"");
	return null;
}
function processStylePage(tmpThis){
	
	this.tmpStyle = -1;
	this.tmpStyle = new stylePage();	

  	
  	if(tmpThis.attr('page-type') != null){
		this.tmpStyle.pageType = tmpThis.attr('page-type');
	}
	if(tmpThis.attr('page-width') != null){
		this.tmpStyle.pageWidth = tmpThis.attr('page-width');			
	}
	if(tmpThis.attr('page-height') != null){
		this.tmpStyle.pageHeight = tmpThis.attr('page-height');
	}
	if(tmpThis.attr('page-direction') != null){
		this.tmpStyle.pageDirection = tmpThis.attr('page-direction');
	}
	///////////////////////////이중
	if(tmpThis.attr('page-padding-right') != null){
		this.tmpStyle.pagePaddingRight = tmpThis.attr('page-padding-right');
	}
	if(tmpThis.attr('page-padding-left') != null){
		this.tmpStyle.pagePaddingLeft = tmpThis.attr('page-padding-left');
	}
	if(tmpThis.attr('page-padding-top') != null){
		this.tmpStyle.pagePaddingTop = tmpThis.attr('page-padding-top');
	}
	if(tmpThis.attr('page-padding-bottom') != null){
		this.tmpStyle.pagePaddingBottom = tmpThis.attr('page-padding-bottom');
	}
	
	///////////////
	if(tmpThis.attr('page-margin-right') != null){
		this.tmpStyle.pagePaddingRight = tmpThis.attr('page-margin-right');
	}
	if(tmpThis.attr('page-margin-left') != null){
		this.tmpStyle.pagePaddingLeft = tmpThis.attr('page-margin-left');
	}
	if(tmpThis.attr('page-margin-top') != null){
		this.tmpStyle.pagePaddingTop = tmpThis.attr('page-margin-top');
	}
	if(tmpThis.attr('page-margin-bottom') != null){
		this.tmpStyle.pagePaddingBottom = tmpThis.attr('page-margin-bottom');
	}
	
	return this.tmpStyle;
}

function processStyleData(tmpThis,pDefaultSytleList,bNeworOldStyle)
{
	this.tmpStyle = -1;

	var style = tmpThis.attr('family');
	
	this.tmpStyle = processGetStyle(style,pDefaultSytleList,bNeworOldStyle);
	if(this.tmpStyle == null){
		return this.tmpStyle;
	}
	if(tmpThis.attr('name') != null){
		this.tmpStyle.name = tmpThis.attr('name');
	}
		
	if(style == "line"){  		
		
		if(tmpThis.attr('line-color') != null){
			this.tmpStyle.lineColor = tmpThis.attr('line-color');
		}		
		if(tmpThis.attr('line-type') != null){
			this.tmpStyle.lineStyle = tmpThis.attr('line-type');
		}			
		if(tmpThis.attr('line-width') != null){
			this.tmpStyle.lineWidth = tmpThis.attr('line-width');			
		}

  	}else if(style == "border"){
  			  	
	  	if(tmpThis.attr('border-right-color') != null){
	  		this.tmpStyle.borderRightColor = tmpThis.attr('border-right-color');
	  	}
	  	if(tmpThis.attr('border-right-style') != null){
			this.tmpStyle.borderRightStyle = tmpThis.attr('border-right-style');
		}
		if(tmpThis.attr('border-right-width') != null){
			this.tmpStyle.borderRightWidth = tmpThis.attr('border-right-width');
		}		
		
		if(tmpThis.attr('border-left-color') != null){
			this.tmpStyle.borderLeftColor = tmpThis.attr('border-left-color');
		}
		if(tmpThis.attr('border-left-style') != null){
			this.tmpStyle.borderLeftStyle = tmpThis.attr('border-left-style');
		}
		if(tmpThis.attr('border-left-width') != null){
			this.tmpStyle.borderLeftWidth = tmpThis.attr('border-left-width');
		}
		
		if(tmpThis.attr('border-top-color') != null){
			this.tmpStyle.borderTopColor = tmpThis.attr('border-top-color');
		}
		if(tmpThis.attr('border-top-style') != null){
			this.tmpStyle.borderTopStyle = tmpThis.attr('border-top-style');
		}
		if(tmpThis.attr('border-top-width') != null){
			this.tmpStyle.borderTopWidth = tmpThis.attr('border-top-width');
		}		
		
		if(tmpThis.attr('border-bottom-color') != null){
			this.tmpStyle.borderBottomColor = tmpThis.attr('border-bottom-color');
		}
		if(tmpThis.attr('border-bottom-style') != null){
			this.tmpStyle.borderBottomStyle = tmpThis.attr('border-bottom-style');
		}
		if(tmpThis.attr('border-bottom-width') != null){			
			this.tmpStyle.borderBottomWidth = tmpThis.attr('border-bottom-width');
		}						
	}	
	else if(style == "background")   
	{
	  	
	  	if(tmpThis.attr('background-color') != null){
	  		this.tmpStyle.backgroundColor = tmpThis.attr('background-color');
	  	}
	  	if(tmpThis.attr('background-style') != null){
			this.tmpStyle.backgroundStyle = tmpThis.attr('background-style');
		}
		
		if(tmpThis.attr('background-fill-color') != null){
			this.tmpStyle.backgroundFillColor = tmpThis.attr('background-fill-color');
		}
		if(tmpThis.attr('background-fill-style') != null){
			this.tmpStyle.backgroundFillStyle = tmpThis.attr('background-fill-style');
		}
	}   
	else if(style == "font")   
	{			
		
		if(tmpThis.attr('font-name') != null){
			this.tmpStyle.fontName = tmpThis.attr('font-name');
		}
		if(tmpThis.attr('font-color') != null){
			this.tmpStyle.fontColor = tmpThis.attr('font-color');
		}
		if(tmpThis.attr('font-size') != null){
			this.tmpStyle.fontSize = tmpThis.attr('font-size');
		}
		if(tmpThis.attr('font-strike') != null){
			if(tmpThis.attr('font-strike') == "1")	{
				this.tmpStyle.fontStrike = "strike";
			}			
		}
		if(tmpThis.attr('font-underline') != null){
			if(tmpThis.attr('font-underline') == "1"){
				this.tmpStyle.fontUnderline = "underline";
			}			 
		}
		if(tmpThis.attr('font-italic') != null){
			if(tmpThis.attr('font-italic') == "1"){
				this.tmpStyle.fontItalic = "italic";
			}			 
		}
		if(tmpThis.attr('font-bold') != null){
			if(tmpThis.attr('font-bold') == "1"){
				this.tmpStyle.fontBold = "bold";
			}			 
		}
		if(tmpThis.attr('font-wordwrap') != null){
			this.tmpStyle.fontWordwrap = tmpThis.attr('font-wordwrap');
		}

		if(tmpThis.attr('font-horizontal-align') != null){			
			this.tmpStyle.fontHorizontalAlign = tmpThis.attr('font-horizontal-align');			
		}
		if(tmpThis.attr('font-vertical-align') != null){			
			this.tmpStyle.fontVerticalAlign = tmpThis.attr('font-vertical-align');			
		}
		if(tmpThis.attr('font-horizontal') != null){			
			this.tmpStyle.fontHorizontalAlign = tmpThis.attr('font-horizontal');			
		}
		if(tmpThis.attr('font-vertical') != null){			
			this.tmpStyle.fontVerticalAlign = tmpThis.attr('font-vertical');			
		}
	}
	else if(style == "image")   
	{
		
		if(tmpThis.attr('image-type') != null){
			this.tmpStyle.imageType = tmpThis.attr('image-type');
		}
		if(tmpThis.attr('image-source') != null){
			this.tmpStyle.imagesource = tmpThis.attr('image-source');
		}
		if(tmpThis.attr('image-path') != null){
			this.tmpStyle.imagePath = tmpThis.attr('image-path');
		}
		if(tmpThis.attr('image-data') != null){
			this.tmpStyle.imageData = tmpThis.attr('image-data');
		}
		if(tmpThis.attr('image-fill') != null){
			this.tmpStyle.imageFill = tmpThis.attr('image-fill');
		}
		if(tmpThis.attr('image-align-horizontal') != null){
			this.tmpStyle.imageHorizontalAlign = tmpThis.attr('image-align-horizontal');
		}
		if(tmpThis.attr('image-align-vertical') != null){
			this.tmpStyle.imageVerticalAlign = tmpThis.attr('image-align-vertical');
		}
	}
	else if(style == "padding")   
	{
		
	  	if(tmpThis.attr('padding-right') != null){
			this.tmpStyle.paddingRight = tmpThis.attr('padding-right');
		}
		if(tmpThis.attr('padding-left') != null){
			this.tmpStyle.paddingLeft = tmpThis.attr('padding-left');
		}
		if(tmpThis.attr('padding-top') != null){
			this.tmpStyle.paddingTop = tmpThis.attr('padding-top');
		}
		if(tmpThis.attr('padding-bottom') != null){
			this.tmpStyle.paddingBottom = tmpThis.attr('padding-bottom');
		}
	}else if(style == "section")   
	{
		
	  	if(tmpThis.attr('section-height') != null){
			this.tmpStyle.sectionHeight = tmpThis.attr('section-height');
		}
		if(tmpThis.attr('section-new-page') != null){
			this.tmpStyle.sectionNewPage = tmpThis.attr('section-new-page');
		}
	}		

	return this.tmpStyle; 
}

function processLabel(pLabel,nowThis,pTotalStyle)
{
			
	var text = nowThis.text();
	
	var tmpText = "";
	for(var len = 0;len < text.length;len++){		 			
		if(text.charCodeAt(len) != 10 && text.charCodeAt(len) != 9){	 			
			tmpText = tmpText + text.charAt(len);
		}	 			
	}
	text = tmpText;

	pLabel.setText(text);

	var borderStyleName = nowThis.attr('border-style');		
	if(borderStyleName != null && borderStyleName != ""){						
		pLabel.setBorderStyle(processGetTotalStyle(borderStyleName,pTotalStyle));
	}else{				
		pLabel.setBorderStyle(processGetTotalStyle("border",pTotalStyle));
	}

	var backgroundStyleName = nowThis.attr('background-style');
	if(backgroundStyleName != null && backgroundStyleName != ""){			
		pLabel.setBackgroundStyle(processGetTotalStyle(backgroundStyleName,pTotalStyle));
	}else{
		pLabel.setBackgroundStyle(processGetTotalStyle("background",pTotalStyle));
	}

	var paddingStyleName = nowThis.attr('padding-style');
	if(paddingStyleName != null && paddingStyleName != ""){			
		pLabel.setPaddingStyle(processGetTotalStyle(paddingStyleName,pTotalStyle));
	}else{
		pLabel.setPaddingStyle(processGetTotalStyle("padding",pTotalStyle));
	}

	var fontStyleName = nowThis.attr('font-style');
	if(fontStyleName != null && fontStyleName != ""){			
		pLabel.setFontStyle(processGetTotalStyle(fontStyleName,pTotalStyle));
	}else{
		pLabel.setFontStyle(processGetTotalStyle("font",pTotalStyle));
	}

	var imageStyleName = nowThis.attr('image-style');
	if(imageStyleName != null && imageStyleName != ""){			
		pLabel.setImageStyle(processGetTotalStyle(imageStyleName,pTotalStyle));
	}else{
		pLabel.setImageStyle(processGetTotalStyle("image",pTotalStyle));
	}	 		
		
	return pLabel;
}

function loadControlTable(tmpThis,pTotalStyle,nPaddingLeft,nPaddingTop,nPaddingRight,nPaddingBottom)
{	

	var nRow = 0;
	var nColumn = 0;

	$("column",tmpThis).each(function(i){
		nColumn++;
  	});
  	$("row",tmpThis).each(function(i){
		nRow++;
  	});

  	var pTable = new createTable(nRow,nColumn);

  	var nX = parseInt(tmpThis.attr('x1'),10) + parseInt(nPaddingLeft,10);
  	var nY = parseInt(tmpThis.attr('y1'),10) + parseInt(nPaddingTop,10); 	
 	pTable.setX(nX); 
	pTable.setY(nY);		

	var nTotalWidth = new Number();
	$("column",tmpThis).each(function(i){
		var nWidth = $(this).attr('width');
		for(var j = 0;j < nRow;j++){
			var pCell = pTable.getCell(j,i);											
			pCell.setWidth(nWidth);
			pCell.setX(nX + nTotalWidth);
		}	
		nTotalWidth = parseInt(nTotalWidth,10) + parseInt(nWidth,10);		
 	});

	var nTotalHeight = new Number();
 	$("row",tmpThis).each(function(i){ 		
 		var nHeight = $(this).attr('height');	
		for(var j = 0;j < nColumn;j++){
			var pCell = pTable.getCell(i,j);			
			pCell.setHeight(nHeight);	
			pCell.setY(nY + nTotalHeight);			
		}	
		nTotalHeight = parseInt(nTotalHeight,10) + parseInt(nHeight,10);	
 	});
 	

	nRow = 0;
	nColumn = 0;
	
 	$("row",tmpThis).each(function(i){
		$("cell",$(this)).each(function(j){
			var nColspan = $(this).attr('colspan');	
			if(nColspan == 0){
				nColspan = null;
			}
			var nRowspan = $(this).attr('rowspan');
			if(nRowspan == 0){
				nRowspan = null;
			}
			
			var tmpCellWidth = 0;
			var tmpCellHeight = 0;
			var pCell = pTable.getCell(i,nColumn);
			while(pCell.getDummyCell() != false){
				nColumn++;
				pCell = pTable.getCell(i,nColumn);
			}	
			
			if((nColspan != null) && (nRowspan == null)){
				for(var tmpCol = 0;tmpCol <= nColspan;tmpCol++){
					var tmpCell = pTable.getCell(i, nColumn + (tmpCol));
					tmpCellWidth = parseInt(tmpCellWidth) + parseInt(tmpCell.getWidth());
					tmpCell.setDummyCell(true);
				}		
				pCell.setDummyCell(false);
				pCell.setWidth(tmpCellWidth);
			}else if((nRowspan != null) && (nColspan == null)){
				for(var tmpRow = 0;tmpRow <= nRowspan;tmpRow++){
					var tmpCell = pTable.getCell(i + (tmpRow), nColumn);
					tmpCellHeight = parseInt(tmpCellHeight) + parseInt(tmpCell.getHeight());						
					tmpCell.setDummyCell(true);
				}	
				pCell.setDummyCell(false);
				pCell.setHeight(tmpCellHeight);
			}else if((nColspan != null) && (nRowspan != null)){
				
				for(var tmpRow = 0;tmpRow <= nRowspan;tmpRow++){
					tmpCellWidth = 0;
					for(var tmpCol = 0;tmpCol <= nColspan;tmpCol++){
						var tmpCell = pTable.getCell(i + (tmpRow), nColumn + (tmpCol));
						tmpCellWidth = parseInt(tmpCellWidth) + parseInt(tmpCell.getWidth());
						tmpCell.setDummyCell(true);
					}	
					tmpCellHeight = parseInt(tmpCellHeight) + parseInt(tmpCell.getHeight());
				}
				pCell.setDummyCell(false);
				pCell.setHeight(tmpCellHeight);
				pCell.setWidth(tmpCellWidth);				
			}			

			if(pCell.getDummyCell() == false){
				
		 		pCell = processLabel(pCell,$(this),pTotalStyle);				
		 		
	 		}
			nColumn++;
	 	});
		nColumn = 0;
	});

 	for(var i = 0;i < (parseInt(pTable.getRowCount()));i++){
 		for(var j = 0;j < pTable.getColumnCount(i);j++){
 			var pCell = pTable.getCell(i,j); 	
 			var pLeftCell = pTable.getCell(i,j + 1);
 			var pBottomCell = pTable.getCell(i + 1,j);
 			if(pCell.getDummyCell() == false){

	 			if(pLeftCell != null){	 				
	 				
	 				if(pCell.getBorderStyle().borderRightStyle == pLeftCell.getBorderStyle().borderLeftStyle){
	 					if(pCell.getBorderStyle().borderRightStyle != enumLineStyle[0] && pCell.getBorderStyle().borderRightColor != "rgb(0,0,0)"){
	 						pLeftCell.getBorderStyle().borderLeftStyle = enumLineStyle[0];
		 					pLeftCell.getBorderStyle().borderLeftColor = "rgb(255,255,255)";
	 					}else{
	 						pCell.getBorderStyle().borderRightStyle = enumLineStyle[0];	 					
		 					pCell.getBorderStyle().borderRightColor = "rgb(255,255,255)";
	 					}	 						 					
	 				}	 				
	 			}

	 			if(pBottomCell != null){
	 				
	 				if(pCell.getBorderStyle().borderBottomStyle == pBottomCell.getBorderStyle().borderTopStyle){
	 					if(pCell.getBorderStyle().borderBottomStyle != enumLineStyle[0] && pCell.getBorderStyle().borderBottomColor != "rgb(0,0,0)"){
	 						pBottomCell.getBorderStyle().borderTopStyle = enumLineStyle[0];
		 					pBottomCell.getBorderStyle().borderTopColor = "rgb(255,255,255)";	
	 					}else{
	 						pCell.getBorderStyle().borderBottomStyle = enumLineStyle[0];
		 					pCell.getBorderStyle().borderBottomColor = "rgb(255,255,255)";	 							
	 					} 					
	 				}	 				
	 			}
 			}
 		}
 	}
 
 	return pTable; 	
}

function loadControlLabel(tmpThis,pTotalStyle,nPaddingLeft,nPaddingTop,nPaddingRight,nPaddingBottom)
{
  	var nX = parseInt(tmpThis.attr('x1'),10) + parseInt(nPaddingLeft,10);
  	var nY = parseInt(tmpThis.attr('y1'),10) + parseInt(nPaddingTop,10);

    var nWidth = tmpThis.attr('width');
    var nHeight = tmpThis.attr('height');	
  	
	var pLabel = new createLabel(nX,nY,nWidth,nHeight);
	
	return processLabel(pLabel,tmpThis,pTotalStyle);
}

function loadControlLine(tmpThis,pTotalStyle,nPaddingLeft,nPaddingTop,nPaddingRight,nPaddingBottom)
{
	  var nX1 = tmpThis.attr('x1');
	  var nY1 = tmpThis.attr('y1');
	  var nX2 = tmpThis.attr('x2');
	  var nY2 = tmpThis.attr('y2');	
		
	  var pLine= new createLine(nX1,nY1,nX2,nY2);	

	  var lineStyleName = tmpThis.attr('line-style');
	  if(lineStyleName != null && lineStyleName != ""){			
		  pLine.setLineStyle(processGetTotalStyle(lineStyleName,pTotalStyle));
	  }else{
		  pLine.setLineStyle(processGetTotalStyle("line",pTotalStyle));
	  }	 
	  
	  return pLine;		
}

function loadControlShape(tmpThis,pTotalStyle,nPaddingLeft,nPaddingTop,nPaddingRight,nPaddingBottom)
{	
	var nX1 = tmpThis.attr('x1');
	var nY1 = tmpThis.attr('y1');
	var nWidth = tmpThis.attr('width');
	var nHeight = tmpThis.attr('height');	
  	
	var pShape = new createShape(nX1,nY1,nWidth,nHeight);	

	 var lineStyleName = tmpThis.attr('line-style');
	  if(lineStyleName != null && lineStyleName != ""){			
		  pShape.setLineStyle(processGetTotalStyle(lineStyleName,pTotalStyle));
	  }else{
		  pShape.setLineStyle(processGetTotalStyle("line",pTotalStyle));
	  }	 

	var backgroundStyleName = tmpThis.attr('background-style');
	if(backgroundStyleName != null && backgroundStyleName != ""){			
		pShape.setBackgroundStyle(processGetTotalStyle(backgroundStyleName,pTotalStyle));
	}else{
		pShape.setBackgroundStyle(processGetTotalStyle("background",pTotalStyle));
	}
	
	return pShape;
}
