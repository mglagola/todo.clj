$(document).ready(function(){

    $(".delete-todo-item").hide();

    // Deleting an Individual Service
    $(".todo-item").hover( function() {
        $(this).find(".delete-todo-item").show();
    }, function() {
        $(this).find('.delete-todo-item').hide();
    });

    $(".delete-todo-item").click(function() {
        var todoId = $(this).attr("value");
        $.ajax({
            url: "todo/" + todoId + "/delete",
            type: 'DELETE',
            success: function(result) {
                console.log(result);
            }
        });
    });
});

