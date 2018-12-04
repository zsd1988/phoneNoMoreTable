function BindClick(id){
    $("#"+id).click(function(){
        saveDeparement();
    });
}
$("#btn1").click(function(){
    $("#btn1").unbind();
    $("#im2").attr('src','assets/images/customer.png');
    BindClick("btn2")
});
$("#btn2").click(function(){
    $("#btn2").unbind();
    $("#im1").attr('src','assets/images/rest.png');
    BindClick("btn1")
});
