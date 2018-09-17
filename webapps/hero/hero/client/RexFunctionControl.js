
function createLabel(x,y,width,height)
{	
	this.family = enumFamily[9];
	this.name = "default";

	this.nX = x;
	this.nY = y;
	this.nWidth = width;
	this.nHeight = height;
	
	this.setX = function(nX)
	{
		this.nX = nX;
	}
	this.setY = function(nY)
	{
		this.nY = nY;
	}
	this.getX = function()
	{
		return this.nX;
	}
	this.getY = function()
	{
		return this.nY;
	}	
	this.setWidth = function(nWidth)
	{
		this.nWidth = nWidth;
	}
	this.setHeight = function(nHeight)
	{
		this.nHeight = nHeight;
	}
	this.getWidth = function()
	{
		return this.nWidth;
	}
	this.getHeight = function()
	{
		return this.nHeight;
	}
	

	this.fontStyle = new styleFont();
	this.text = "";
	this.setText = function(strText)
	{
		this.text = strText;
	}
	this.getText = function()
	{
		return this.text;
	}
	
	this.setFontStyle = function(pStyle)
	{
		this.fontStyle = pStyle;
	}
	this.getFontStyle = function()
	{
		return this.fontStyle;
	}
	

	this.paddingStyle = new stylePadding();
	this.setPaddingStyle = function(pStyle)
	{
		this.paddingStyle = pStyle;
	}
	this.getPaddingStyle = function()
	{
		return this.paddingStyle;
	}
	

	this.borderStyle = new styleBorder();
	this.setBorderStyle = function(pStyle)
	{
		this.borderStyle = pStyle;
	}
	this.getBorderStyle = function()
	{
		return this.borderStyle;
	}
	

	this.backgroundStyle = new styleBackground();
	this.setBackgroundStyle = function(pStyle)
	{
		this.backgroundStyle = pStyle;
	}
	this.getBackgroundStyle = function()
	{
		return this.backgroundStyle;
	}
	

	this.imageStyle = new styleImage();
	this.setImageStyle = function(pStyle)
	{
		this.imageStyle = pStyle;
	}
	this.getImageStyle = function()
	{
		return this.imageStyle;
	}	
	

	this.nMergeRow = 0;		
	this.setMergeRow = function(nRow)
	{
		this.nMergeRow = pStyle;
	}
	this.getMergeRow = function()
	{
		return this.nMergeRow;
	}	
	this.nMergeColumn = 0;
	this.setMergeColumn= function(nColumn)
	{
		this.nMergeColumn = pStyle;
	}
	this.getMergeColumn = function()
	{
		return this.nMergeColumn;
	}	
	
	this.dummyCell = false;
	this.getDummyCell = function()
	{
		return this.dummyCell;
	}
	this.setDummyCell = function(bDummyCell)
	{
		this.dummyCell = bDummyCell;
	}
	
	this.bCellLink = false;
}


function createTable(row,column)
{	
	this.family = enumFamily[10];
	this.name = "default";
	
	this.nRow = row;
	this.nColumn = column;
	this.nX = 0;
	this.nY = 0;

	this.pCell = new Array(this.nRow);	

	for(var nCell = 0;nCell < this.nRow;nCell++)
	{
		this.pCell[nCell] = new Array(this.nColumn);
	}		

	for(var i = 0;i < this.nRow;i++)
	{
		for(var j = 0;j < this.nColumn;j++)
		{
			this.pCell[i][j] = new createLabel(0,0,0,0);
		}
	}		

	this.setX = function(nX1)
	{
		this.nX = nX1;
	}
	this.setY = function(nY1)
	{
		this.nY = nY1;
	}
	this.getX = function()
	{
		return this.nX;
	}
	this.getY = function()
	{
		return this.nY;
	}
		

	this.getRowCount = function()
	{
		return this.pCell.length;
	}
	

	this.getColumnCount = function(nRow)
	{
		return this.pCell[nRow].length;		
	}
	

	this.getCell = function(nRow,nColumn)
	{	
		if(nRow < this.nRow && nColumn < this.nColumn){
			return this.pCell[nRow][nColumn];
		}else{
			return null;
		}
			
	}
	this.deleteCell = function(nRow,nColumn)
	{		
		this.pCell[nRow].splice(nColumn,1);
	}
	this.insertCell = function(nRow,nColumn,pCell)
	{		
		this.pCell[nRow].splice(nColumn,0,pCell);
	}
}

function createLine(x1,y1,x2,y2)
{
	this.family = enumFamily[1];
	this.name = "default";
	
	this.x1 = x1;
	this.y1 = y1;
	this.x2 = x2;
	this.y2 = y2;
	
	this.setX1 = function(x1)
	{
		this.x1 = x1;
	}
	this.getX1 = function()
	{
		return this.x1;
	}	
	this.setY1 = function(y1)
	{
		this.y1 = y1;
	}
	this.getY1 = function()
	{
		return this.y1;
	}
	
	this.setX2 = function(x2)
	{
		this.x2 = x2;
	}
	this.getX2 = function()
	{
		return this.x2;
	}	
	this.setY2 = function(y2)
	{
		this.y2 = y2;
	}
	this.getY2 = function()
	{
		return this.y2;
	}
	
	this.visible = true;
	this.setVisible = function(bVisible)
	{
		this.visible = bVisible;
	}
	this.getVisible = function()
	{
		return this.visible;
	}	
	
	this.condition = "";
	this.setCondition = function(condition)
	{
		this.condition = condition;
	}
	this.getCondition = function()
	{
		return this.condition;
	}	

	this.lineStyle = new styleLine();
	this.setLineStyle = function(plineStyle)
	{
		this.lineStyle = plineStyle;
	}
	this.getLineStyle = function()
	{
		return this.lineStyle;
	}	
}

function createShape(x1,y1,width,height)
{
	this.family = enumFamily[11];
	this.name = "default";
	
	this.x1 = x1;
	this.y1 = y1;	
	this.setX1 = function(x1)
	{
		this.x1 = x1;
	}
	this.getX1 = function()
	{
		return this.x1;
	}	
	this.setY1 = function(y1)
	{
		this.y1 = y1;
	}
	this.getY1 = function()
	{
		return this.y1;
	}
	
	this.nWidth = width;
	this.nHeight = height;
	this.setWidth = function(nWidth)
	{
		this.nWidth = nWidth;
	}
	this.setHeight = function(nHeight)
	{
		this.nHeight = nHeight;
	}
	this.getWidth = function()
	{
		return this.nWidth;
	}
	this.getHeight = function()
	{
		return this.nHeight;
	}

	this.lineStyle = new styleLine();
	this.setLineStyle = function(plineStyle)
	{
		this.lineStyle = plineStyle;
	}
	this.getLineStyle = function()
	{
		return this.lineStyle;
	}	

	this.backgroundStyle = new styleBackground();
	this.setBackgroundStyle = function(pStyle)
	{
		this.backgroundStyle = pStyle;
	}
	this.getBackgroundStyle = function()
	{
		return this.backgroundStyle;
	}
}

