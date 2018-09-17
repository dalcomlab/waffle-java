
function createPageList()
{
	this.pPageList = [];
	
	
	this.addPage = function(pPage)
	{
		this.pPageList.push(pPage);
	}
	
	this.getPage = function(nPage)
	{
		
		return this.pPageList[nPage];
	}	
	
	this.getPageCount = function()
	{
		return this.pPageList.length;
	}
}

function createPage()
{
	this.pPage = new stylePage();


	this.pControlList = [];
	
	this.setPageType = function(type)
	{
		this.pPage.pageType = type;	
	}
	this.getPageType = function()
	{		
		return this.pPage.pageType;	
	}
	
	this.setPageWidth = function(nWidth)
	{
		this.pPage.pageWidth = nWidth;	
	}
	this.getPageWidth = function()
	{
		return this.pPage.pageWidth;
	}
	this.setPageHeight = function(nHeight)
	{
		this.pPage.pageHeight = nHeight;
	}
	this.getPageHeight = function()
	{
		return this.pPage.pageHeight;
	}
	this.setpageDirection = function(direction)
	{
		this.pPage.pageDirection = direction;
	}
	this.getpageDirection = function()
	{
		return this.pPage.pageDirection; 	
	}

	this.setPagePaddingRight = function(nPaddingRight)
	{
		this.pPage.pagePaddingRight = nPaddingRight;
	}
	this.getPagePaddingRight = function()
	{
		return this.pPage.pagePaddingRight;	
	}
	this.setPagePaddingLeft = function(nPaddingLeft)
	{
		this.pPage.pagePaddingLeft = nPaddingLeft;
	}
	this.getPagePaddingLeft = function()
	{
		return this.pPage.pagePaddingLeft;
	}
	this.setPagePaddingTop = function(nPaddingTop)
	{
		this.pPage.pagePaddingTop = nPaddingTop;
	}
	this.getPagePaddingTop = function()
	{
		return this.pPage.pagePaddingTop;
	}
	this.setPagePaddingBottom = function(nPaddingBottom)
	{
		this.pPage.pagePaddingBottom = nPaddingBottom;
	}
	this.getPagePaddingBottom = function()
	{
		return this.pPage.pagePaddingBottom;
	}
	
	this.setPageStyle = function(pStyle)
	{
		this.pPage = pStyle;
	}
	this.getPageStyle = function()
	{
		return this.pPage;
	}	
	

	this.addControl = function(pControl)
	{
		this.pControlList.push(pControl);
	}

	this.getControlCount = function()
	{
		return this.pControlList.length;
	}

	this.getControl = function(nSelected)
	{
		return this.pControlList[nSelected];
	}

	this.deleteControl = function(nSelected)
	{
		delete this.pControlList[nSelected];		
	}

	this.allDeleteControl = function()
	{
		this.nCount = this.pControlList.length;
		
		for(var i = nCount;i >= 0;i--)
		{
			delete this.pControlList[i];
		}
	}
	this.drawControl = function()
	{
		drawControl(this.pControlList);
	}
}












