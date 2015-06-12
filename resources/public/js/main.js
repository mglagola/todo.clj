$(document).ready(function(){

    $(".delete-todo-item").hide();

    // Deleting an Individual Service
    $(".todo-item").hover( function() {
        $(this).find(".delete-todo-item").show();
    }, function() {
        $(this).find('.delete-todo-item').hide();
    });

    $(".delete-todo-item").click(function() {
        var parentElement = $(this).parent();
        console.log(parentElement);
        var todoId = $(this).attr("value");
        console.log("DELETE " + "todo/" + todoId + "/delete");
        $.ajax({
            url: "todo/" + todoId + "/delete",
            type: 'DELETE',
            headers: { "X-CSRF-Token": $("#__anti-forgery-token").val() },
            success: function(result, textStatus, jqXHR) {
                console.log("Complete: " + result);
                parentElement.remove();
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.log("Failed: " + errorThrown);
            }
        });
    });
});

