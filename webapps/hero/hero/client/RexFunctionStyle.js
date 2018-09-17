
var enumLineStyle = ["none","sold","dash","dot","dashdot","dashdotdot","doublesolid"];
var enumLineWidth = [50,75,100,150,225,300,450,600];
var enumBackgroundStyle	= ["normal","transparent"];
var enumBackgroundFillStyle	= ["none","horizontalline","verticalline","crossline","frontdiagonalline","backdiagonalline","crossdiagonalline","dotdensed","dot","dotrare"];
var enumFamily = ["visible","line","border","background","font","image","padding","page","section","label","table","shape"];
var enumHorizontal = ["left","middle","right"];
var enumVertical = ["top","center","bottom"];
var enumImageFillStyle = ["none","original","stretch"];
var enumImageSource = ["path","data"];


var enumPageType = ["a3transverse","a4","a5","b4","b4envelope","b5","b5envelope","b6envelope","c3envelope","c4envelope",
					"c5envelope","c65envelope","c6envelope","csheet","dlenvelope","dsheet","esheet","executive","folio",
					"germanlegalfanfold","germanstandardfanfold","italyenvelope","ledger","legal","lettersmall","monarchenvelope",
					"papernote","papernumber10envelope","number11envelope","number12envelope","number14envelope","number9envelope",
					"personalenvelope","quarto","standard10x14","standard11x17","statement","tabloid","usstandardfanFold","userdefine"];
					

var enumPageDirection = ["portrait","landscape"];
var enumSectionNewPage = ["none","before","after","beforeafter"];

function createDefaultStyle(){
	var pDefaultStyle = [];
	
	pDefaultStyle.push(new styleVisible());
	pDefaultStyle.push(new styleLine());
	pDefaultStyle.push(new styleBorder());
	pDefaultStyle.push(new styleBackground());
	pDefaultStyle.push(new styleFont());
	pDefaultStyle.push(new styleImage());
	pDefaultStyle.push(new stylePadding());
	//pDefaultStyle.push(new stylePage());
	pDefaultStyle.push(new styleSection());	
	
	return pDefaultStyle;	
}

function styleVisible()
{
	this.family = enumFamily[0];
	this.name = "default";
	
	
	this.visible = "0";
}

function styleLine()
{
	this.family = enumFamily[1];
	this.name = "default";
	
	this.lineColor = "rgb(0,0,0)";
	this.lineStyle = enumLineStyle[1];
	this.lineWidth = enumLineWidth[1];	
}

function styleBorder()
{
	this.family = enumFamily[2];
	this.name = "default";
	
	this.borderRightColor = "rgb(0,0,0)";
	this.borderRightStyle = enumLineStyle[1];
	this.borderRightWidth = enumLineWidth[1];
	
	this.borderLeftColor = "rgb(0,0,0)";
	this.borderLeftStyle = enumLineStyle[1];
	this.borderLeftWidth = enumLineWidth[1];
	
	this.borderTopColor = "rgb(0,0,0)";
	this.borderTopStyle = enumLineStyle[1];
	this.borderTopWidth = enumLineWidth[1];
	
	this.borderBottomColor = "rgb(0,0,0)";
	this.borderBottomStyle = enumLineStyle[1];
	this.borderBottomWidth = enumLineWidth[1];
}

function styleBackground()
{
	this.family = enumFamily[3];
	this.name = "default";
	
	
	this.backgroundColor = "rgb(0,0,0)";
	this.backgroundStyle = enumBackgroundStyle[0];
	
	this.backgroundFillColor = "rgb(0,0,0)";
	this.backgroundFillStyle = enumBackgroundFillStyle[0];	
}

function styleFont()
{
	this.family = enumFamily[4];
	this.name = "default";
	
	this.fontName = "굴림";
	this.fontColor = "rgb(0,0,0)";
	this.fontSize = 11;
	this.fontStrike = "";
	this.fontUnderline = "";
	this.fontItalic = "";
	this.fontBold = "";
	this.fontWordwrap = "0";
	this.fontHorizontalAlign = enumHorizontal[0];
	this.fontVerticalAlign = enumVertical[0];
	
}

function styleImage()
{
	this.family = enumFamily[5];
	this.name = "default";
	
	this.imageType = "";
	this.imagesource = enumImageSource[0];
	this.imagePath = "";
	this.imageData = "";
	
	this.imageFill = enumImageFillStyle[1];
	
	this.imageHorizontalAlign = enumHorizontal[0];
	this.imageVerticalAlign = enumVertical[0];	
}

function stylePadding()
{
	this.family = enumFamily[6];
	this.name = "default";
	
	this.paddingRight = 0;
	this.paddingLeft = 0;
	this.paddingTop = 0;
	this.paddingBottom = 0;
}

function stylePage()
{
	this.family = enumFamily[7];
	this.name = "default";
	
	
	this.pageType = enumPageType[1];
	this.pageWidth = 2100;
	this.pageHeight = 2970;
	this.pageDirection = enumPageDirection[0];
	this.pagePaddingRight = 50;
	this.pagePaddingLeft = 50;
	this.pagePaddingTop = 50;
	this.pagePaddingBottom = 50;
	
}

function styleSection()
{
	this.family = enumFamily[8];
	this.name = "default";
	
	this.sectionHeight = 0;
	this.sectionNewPage = enumSectionNewPage[0];
}





