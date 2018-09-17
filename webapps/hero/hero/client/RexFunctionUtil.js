
var enumMMToPixel = [2,1.5,1.4,1.3,1.2,1.1,1,0.8,0.7,0.6,0.5,0.4,0.3];
var nNowMMToPixel = 6;

function castRexToPixel(nMM)
{
	var nPixel;
	nPixel = (nMM / (254/96)) * enumMMToPixel[nNowMMToPixel];
	  
	return Math.round(nPixel);
}
function castXToPage(nX)
{
	var nPageX = 0;
	nPageX = parseInt(nX) * enumMMToPixel[nNowMMToPixel];
	
	return Math.round(nPageX);
}

function castPtToPixel(nPt)
{

	var nPixel;
	nPixel = ((parseInt(nPt) * 96)/72) * enumMMToPixel[nNowMMToPixel];
	
	return Math.round(nPixel);
}

function castPtHeight(nPt)
{
	var nOnePixel;
	var nOnePt;
	var nHeight;
	
	nOnePixel = (2.54/96);
	nOnePt = ((2.54/72) * nPt);
	nHeight = (nOnePt / nOnePixel); 
	
	return Math.round(nHeight);
}

function castLineWidth(nWidth)
{
	if(nWidth != 0)
	{
		nWidth = nWidth * 0.01;
		return nWidth;
	}
	return 0;
}

function castFontAutoWordwrap(canvas,strText,nWidth)
{
	var len = strText.length;
	var aText = [];
	var tmpText = "";
	var nNext = 0;

	
	for(var i = 0;i <= len;i++)
	{		
		tmpText = strText.slice(nNext,i);
		measure = canvas.measureText(tmpText);
		var nFontWidth = measure.width;
		
		if(nFontWidth >= nWidth)
		{
			tmpText = strText.slice(nNext,(--i));
			aText.push(tmpText);
			nNext = i;
		}
	}
	aText.push(tmpText);
	return aText;
}

function castHAlign(horizontalAlign,width,totalWidth)
{
	width = castXToPage(width);
	var nHAlign = 0;

	if(horizontalAlign == enumHorizontal[1]){
		nHAlign = (castRexToPixel(totalWidth)/2) - (width / 2);
	}else if(horizontalAlign == enumHorizontal[2]){

		nHAlign = (castRexToPixel(totalWidth-6) - (width));
	}
	return nHAlign;
}
function castVAlign(verticalAlign,textLen,height,totalHeight)
{
	var nVAlign = 0;
	
	if(verticalAlign == enumVertical[1]){
		nVAlign = (castRexToPixel(totalHeight)/2) - castXToPage(((textLen * height)/2));
	}else if(verticalAlign == enumVertical[2]){
		nVAlign = (castRexToPixel(totalHeight)) - castXToPage((textLen * height));
	}
	return nVAlign;
}


var _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

function encode64(input) {
    var output = "";
    var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
    var i = 0;

    input = utf8_encode(input);

    while (i < input.length) {

        chr1 = input.charCodeAt(i++);
        chr2 = input.charCodeAt(i++);
        chr3 = input.charCodeAt(i++);

        enc1 = chr1 >> 2;
        enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
        enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
        enc4 = chr3 & 63;

        if (isNaN(chr2)) {
            enc3 = enc4 = 64;
        } else if (isNaN(chr3)) {
            enc4 = 64;
        }

        output = output +
this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) +
this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4);

    }

    return output;

}

function decode64(input) {
    var output = "";
    var chr1, chr2, chr3;
    var enc1, enc2, enc3, enc4;
    var i = 0;

    input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

    while (i < input.length) {

        enc1 = this._keyStr.indexOf(input.charAt(i++));
        enc2 = this._keyStr.indexOf(input.charAt(i++));
        enc3 = this._keyStr.indexOf(input.charAt(i++));
        enc4 = this._keyStr.indexOf(input.charAt(i++));

        chr1 = (enc1 << 2) | (enc2 >> 4);
        chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
        chr3 = ((enc3 & 3) << 6) | enc4;

        output = output + String.fromCharCode(chr1);

        if (enc3 != 64) {
            output = output + String.fromCharCode(chr2);
        }
        if (enc4 != 64) {
            output = output + String.fromCharCode(chr3);
        }

    }

    output = utf8_decode(output);

    return output;
}

function utf8_decode(utftext) {
    var string = "";
    var i = 0;
    var c = c1 = c2 = 0;

    while (i < utftext.length) {

        c = utftext.charCodeAt(i);

        if (c < 128) {
            string += String.fromCharCode(c);
            i++;
        }
        else if ((c > 191) && (c < 224)) {
            c2 = utftext.charCodeAt(i + 1);
            string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
            i += 2;
        }
        else {
            c2 = utftext.charCodeAt(i + 1);
            c3 = utftext.charCodeAt(i + 2);
            string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
            i += 3;
        }

    }

    return string;
}


function utf8_encode(string) {
    string = string.replace(/\r\n/g, "\n");
    var utftext = "";

    for (var n = 0; n < string.length; n++) {

        var c = string.charCodeAt(n);

        if (c < 128) {
            utftext += String.fromCharCode(c);
        }
        else if ((c > 127) && (c < 2048)) {
            utftext += String.fromCharCode((c >> 6) | 192);
            utftext += String.fromCharCode((c & 63) | 128);
        }
        else {
            utftext += String.fromCharCode((c >> 12) | 224);
            utftext += String.fromCharCode(((c >> 6) & 63) | 128);
            utftext += String.fromCharCode((c & 63) | 128);
        }

    }

    return utftext;
}

