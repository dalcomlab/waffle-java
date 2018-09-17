
function drawReport(canvasID,pXmlData,nowPage)
{	
 	var element = document.getElementById(canvasID); 	
 	
	var error = drawPage(element,pXmlData,(nowPage-1),false);
	if(error == "data:,"){
		return null;
	}

	return true;	
}
function drawPage(element,pXmlData,nNowPage,printCheck)
{
	var canvas = element.getContext('2d');
	var pPage = pXmlData.getPage(nNowPage);

	var nControlCount = pPage.getControlCount();	

	element.width = castRexToPixel(pPage.getPageWidth()) + 1; 
	element.height = castRexToPixel(pPage.getPageHeight()) + 1;
	
	
	canvas.translate(0.5, 0.5);
	
	canvas.beginPath();

	for(var i = 0;i < nControlCount;i++){
		var pControl = pPage.getControl(i);
		drawControl(canvas,pControl,printCheck);
	}
	if(printCheck == false){
		drawPadding(canvas,pPage);
	}
	canvas.closePath();	
	
	//return element.toDataURL();
	return element.toDataURL('image/jepg');
	
	
}

function drawControl(canvas,pControl,printCheck)
{
	if(pControl.family == enumFamily[1]){		
		drawLine(canvas,pControl);		
	}else if(pControl.family == enumFamily[9]){
		drawLabel(canvas,pControl,printCheck);	
	}else if(pControl.family == enumFamily[10]){		
		drawTable(canvas,pControl,printCheck);
	}else if(pControl.family == enumFamily[11]){
		drawShape(canvas,pControl);	
	}
}
function drawTable(canvas,pControl,printCheck)
{
	for(var nRow = 0;nRow < pControl.getRowCount();nRow++){
		for(var nCol = 0;nCol < pControl.getColumnCount(nRow);nCol++){
			var pCell = pControl.getCell(nRow,nCol);
			if(pCell.getDummyCell() == false){
				drawLabel(canvas,pCell,printCheck);
			}
		}
	}
}


function drawLine(canvas,pLine)
{
	var nX1 = castRexToPixel(pLine.getX1());
	var nY1 = castRexToPixel(pLine.getY1());
	var nX2 = castRexToPixel(pLine.getX2());
	var nY2 = castRexToPixel(pLine.getY2());
	
	canvas.beginPath();

	canvas.strokeStyle = pLine.getLineStyle().lineColor;
	canvas.lineWidth = castLineWidth(pLine.getLineStyle().lineWidth);

	canvas.moveTo(nX1,nY1);
	canvas.lineTo(nX2,nY2);
	canvas.stroke();
	canvas.closePath();
}

function drawShape(canvas,pShape)
{
	var nX1 = castRexToPixel(pShape.getX1());
	var nY1 = castRexToPixel(pShape.getY1());
	var nWidth = castRexToPixel(pShape.getWidth());
	var nHeight = castRexToPixel(pShape.getHeight());
	
	canvas.beginPath();

	canvas.strokeStyle = pShape.getLineStyle().lineColor;
	canvas.lineWidth = castLineWidth(pShape.getLineStyle().lineWidth);

	if(pShape.getBackgroundStyle().backgroundStyle == enumBackgroundStyle[0])
	{
		canvas.fillStyle = pShape.getBackgroundStyle().backgroundColor;
		canvas.fillRect(nX1,nY1,nWidth,nHeight);
	}
	canvas.strokeRect(nX1,nY1,nWidth,nHeight);
	canvas.closePath();
}

function drawLabel(canvas,pCell,printCheck)
{	
	canvas.beginPath();	
	if(pCell.getImageStyle().family == enumFamily[5]){
		var cellImage = new Image();
		if(pCell.getImageStyle().imagesource == enumImageSource[0]){
			cellImage.src =  pCell.getImageStyle().imagePath;
		}else{
			if(pCell.getImageStyle().imageType.length != 0 && pCell.getImageStyle().imageType != null){
				cellImage.src = "data:image/"+ pCell.getImageStyle().imageType +";base64," + pCell.getImageStyle().imageData;
			}else{
				cellImage.src = "data:image/jpg"+ ";base64," + pCell.getImageStyle().imageData;
			}			
		}

		if(printCheck == false){
			cellImage.onload = function(){	
				drawImage(canvas,pCell,cellImage);
				drawCell(canvas,pCell);
			}		
		}else{
			drawImage(canvas,pCell,cellImage);
		}
				
	}
	canvas.closePath();
	drawCell(canvas,pCell);
}

function drawImage(canvas,pCell,imageData)
{
	var nX = castRexToPixel(pCell.getX());
	var nY = castRexToPixel(pCell.getY());
	var nBoxWidth = castRexToPixel(parseInt(pCell.getWidth()));
	var nBoxHeight = castRexToPixel(parseInt(pCell.getHeight()));

	if(imageData.width != 0 && imageData.height != 0){	
		if(pCell.getImageStyle().imageFill == enumImageFillStyle[1]){
			//오리지널
	
			var nImgHAlign = castHAlign(pCell.getImageStyle().imageHorizontalAlign,imageData.width,pCell.getWidth());
			var nImgVAlign = castVAlign(pCell.getImageStyle().imageVerticalAlign,1,imageData.height,pCell.getHeight());	
			
			var nImageWidth = castXToPage(imageData.width);
			var nImageHeight = castXToPage(imageData.height);
				
			if(nImageWidth > nBoxWidth && nImageHeight > nBoxHeight){					
				canvas.drawImage(imageData,0,0,nBoxWidth,nBoxHeight,nX,nY,nBoxWidth,nBoxHeight);
			}else if(nImageWidth < nBoxWidth && nImageHeight < nBoxHeight){						
				canvas.drawImage(imageData,0,0,imageData.width,imageData.height,nX + nImgHAlign,nY + nImgVAlign,nImageWidth,nImageHeight);
			}else if(nImageWidth > nBoxWidth && nImageHeight < nBoxHeight){						
				canvas.drawImage(imageData,0,0,nBoxWidth,imageData.height,nX,nY + nImgVAlign,nBoxWidth,nImageHeight);
			}else if(nImageWidth < nBoxWidth && nImageHeight > nBoxHeight){						
				canvas.drawImage(imageData,0,0,imageData.width,nBoxHeight,nX + nImgHAlign,nY,nImageWidth,nBoxHeight);
			}else{					
				canvas.drawImage(imageData,0,0,imageData.width,imageData.height,nX,nY,nBoxWidth,nBoxHeight);
			}				
		}else{
			canvas.drawImage(imageData,0,0,imageData.width,imageData.height,nX,nY,nBoxWidth,nBoxHeight);
		}				
	}	
}

function drawCell(canvas,pCell)
{
	
	var nX = castRexToPixel(pCell.getX());
	var nY = castRexToPixel(pCell.getY());
	var nWidth = castRexToPixel(parseInt(pCell.getWidth()) + parseInt(pCell.getX()));
	var nHeight = castRexToPixel(parseInt(pCell.getHeight()) + parseInt(pCell.getY()));

	if(pCell.getBackgroundStyle().backgroundStyle != enumBackgroundStyle[1]){
		canvas.beginPath();	
		canvas.fillStyle = pCell.getBackgroundStyle().backgroundColor;
		canvas.fillRect(nX,nY,castRexToPixel(pCell.getWidth()),castRexToPixel(pCell.getHeight()));
		canvas.closePath();
	}

	if(pCell.getBorderStyle().borderTopStyle != enumLineStyle[0]){
		canvas.beginPath();	
		canvas.strokeStyle = pCell.getBorderStyle().borderTopColor;
		canvas.lineWidth = castLineWidth(pCell.getBorderStyle().borderTopWidth);

		canvas.moveTo(nX,nY);
		canvas.lineTo(nWidth,nY);
		canvas.stroke();
	}
	if(pCell.getBorderStyle().borderBottomStyle != enumLineStyle[0]){
		canvas.beginPath();
		canvas.strokeStyle = pCell.getBorderStyle().borderBottomColor;
		canvas.lineWidth = castLineWidth(pCell.getBorderStyle().borderBottomWidth);

		canvas.moveTo(nX,nHeight);
		canvas.lineTo(nWidth,nHeight);
		canvas.stroke();
	}
	if(pCell.getBorderStyle().borderLeftStyle != enumLineStyle[0]){
		canvas.beginPath();
		canvas.strokeStyle = pCell.getBorderStyle().borderLeftColor;
		canvas.lineWidth = castLineWidth(pCell.getBorderStyle().borderLeftWidth);
	
		canvas.moveTo(nX,nY);
		canvas.lineTo(nX,nHeight);
		canvas.stroke();
	}
	if(pCell.getBorderStyle().borderRightStyle != enumLineStyle[0]){
		canvas.beginPath();
		canvas.strokeStyle = pCell.getBorderStyle().borderRightColor;
		canvas.lineWidth = castLineWidth(pCell.getBorderStyle().borderRightWidth);
		
		canvas.moveTo(nWidth,nY);
		canvas.lineTo(nWidth,nHeight);		
		canvas.stroke();
	}
	
	canvas.closePath();

	
	canvas.beginPath();
	canvas.fillStyle = pCell.getFontStyle().fontColor;
	var fontSize = Math.floor(castPtToPixel(pCell.getFontStyle().fontSize)) + "px ";
	var nFontHeight = castPtHeight(pCell.getFontStyle().fontSize);

	canvas.textBaseline = 'top';	
	canvas.font = pCell.getFontStyle().fontItalic + " " +  pCell.getFontStyle().fontBold + " " + fontSize + pCell.getFontStyle().fontName;

	var aText = castFontAutoWordwrap(canvas,pCell.getText(),castRexToPixel(pCell.getWidth()));

	var nTextLen = aText.length;	

	var nFontLine = 0;
	var fontHorizontalAlign = pCell.getFontStyle().fontHorizontalAlign;
	var fontVerticalAlign = pCell.getFontStyle().fontVerticalAlign;	
	var nHAlign = 0;
	var nVAlign = 0;
	
	for(var i = 0;i < nTextLen;i++){		
		if(nFontLine <= castRexToPixel(pCell.getHeight())){
			measure = canvas.measureText(aText[i]);
			var nFontWidth = measure.width;	
			
			//정렬
			nHAlign = castHAlign(fontHorizontalAlign,nFontWidth,pCell.getWidth());
			nVAlign = castVAlign(fontVerticalAlign,nTextLen,nFontHeight,pCell.getHeight());
			
			canvas.fillText(aText[i],nX + nHAlign,nY + nFontLine + nVAlign);
			nFontLine = nFontLine + castXToPage(nFontHeight);
		}
	}	
	canvas.closePath();	
}

function drawPadding(canvas,pPage)
{
	var pagePaddingTop = castRexToPixel(pPage.getPagePaddingTop());
	var pagePaddingLeft = castRexToPixel(pPage.getPagePaddingLeft());
	var pagePaddingRight = castRexToPixel(pPage.getPagePaddingRight());
	var pagePaddingBottom= castRexToPixel(pPage.getPagePaddingBottom());
	
	var lineWidth = castRexToPixel(30);
	
	var rightLine = 0;
	var bottomLine = 0;
	if(pagePaddingRight > lineWidth){
		rightLine = (pagePaddingRight - lineWidth);
	}
	
	if(pagePaddingBottom > lineWidth){
		bottomLine = (pagePaddingBottom - lineWidth);
	}	 
	
	var paddingHeightBottomLine = castRexToPixel(pPage.getPageHeight()) - bottomLine;
	var paddingHeightBottom = castRexToPixel(pPage.getPageHeight()) - pagePaddingBottom;
	var paddingWidthRightLine = castRexToPixel(pPage.getPageWidth())- rightLine;
	var paddingWidthRight = castRexToPixel(pPage.getPageWidth())- pagePaddingRight;

	canvas.beginPath();
	canvas.strokeStyle = "rgb(100,100,100)";

	canvas.moveTo((pagePaddingLeft-lineWidth),pagePaddingTop);
	canvas.lineTo(pagePaddingLeft,pagePaddingTop);	
	canvas.moveTo(pagePaddingLeft,pagePaddingTop);
	canvas.lineTo(pagePaddingLeft,(pagePaddingTop-lineWidth));

	canvas.moveTo(paddingWidthRightLine,pagePaddingTop);
	canvas.lineTo(paddingWidthRight,pagePaddingTop);	
	canvas.moveTo(paddingWidthRight,pagePaddingTop);
	canvas.lineTo(paddingWidthRight,(pagePaddingTop-lineWidth));

	canvas.moveTo((pagePaddingLeft-lineWidth),paddingHeightBottom);
	canvas.lineTo(pagePaddingLeft,paddingHeightBottom);	
	canvas.moveTo(pagePaddingLeft,paddingHeightBottom);
	canvas.lineTo(pagePaddingLeft,paddingHeightBottomLine);

	canvas.moveTo(paddingWidthRight,paddingHeightBottom);
	canvas.lineTo(paddingWidthRightLine,paddingHeightBottom);	
	canvas.moveTo(paddingWidthRight,paddingHeightBottom);
	canvas.lineTo(paddingWidthRight,paddingHeightBottomLine);

	canvas.stroke();
}


function saveReportImageData(canvasID,pXmlData,nowPage)
{
	var reportImageData = [];
	var element = document.getElementById(canvasID);

	var nPageCount = pXmlData.getPageCount(); 		
	for(var i = 0;i < nPageCount;i++){
		reportImageData.push(drawPage(element,pXmlData,i,true));
	} 	
	drawPage(element,pXmlData,(nowPage-1),true);
	
	return reportImageData;
}

