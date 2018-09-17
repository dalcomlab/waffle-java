var totalImageCount = 12;
var imagesInRowCount = 1;
var viewportSize;

$(document).ready(function(){
    
    $(window).on("resize", function(){
        var height = $(window).height();
        var width = $(window).width();
        setGridClasses(height, width);
        
    });
    for(var i = 1; i <= 12; i++){
        if((totalImageCount % i) == 0){
            $("#imgCount").html($("#imgCount").html() + 
            '<option value="' + i + '">' + i +'</option>');
        }
    }
    $("#imgCount").on("change", function(){
         setGridClasses($(window).height(), $(window).width());
        imagesInRowCount = $("#imgCount").val();
    });   
    setGridClasses($(window).height(), $(window).width());
    
});
function setClass(){
    $(".row div").each(function(index, element) {
        if($(element).hasClass("clearfix") == false)
        {
            $(element).removeClass("col-" + viewportSize + 
            "-" + (totalImageCount/imagesInRowCount));
            $(element).addClass(getThumbnailClass($("#imgCount").val()));
        }  
    });
    viewportSize = $("imgCount").val();
}
function setGridClasses(height, width){
        if(width < 768){
             viewportSize = "xs";
        } else if(width <= 768 && width < 992){
            viewportSize = "sm";
        } else if(width >= 992 && width < 1200){
            viewportSize = "md"
        } else{
            viewportSize = "lg";
        }
        setClass();
}
function getThumbnailClass(desiredCount){
    var cls = "col-" + viewportSize + "-";
    var count = totalImageCount/desiredCount;
    cls += count;
    return cls;
}