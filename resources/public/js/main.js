$(document).ready(function(){

    $(".delete-todo-item").hide();

    // Deleting an Individual Service
    $(".todo-item").hover( function() {
        $(this).find(".delete-todo-item").show();
    }, function() {
        $(this).find('.delete-todo-item').hide();
    });

});