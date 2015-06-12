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
        var todoId = $(this).attr("value");
        $.ajax({
            url: "/todo/" + todoId + "/delete",
            type: 'DELETE',
            headers: { "X-CSRF-Token": $("#__anti-forgery-token").val() },
            success: function(result, textStatus, jqXHR) {
                parentElement.remove();
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.log("Failed to delete: " + errorThrown);
            }
        });
    });

    $(".todo-form-submit").click(function() {
        var parentElement = $(this).parent();
        $.ajax({
            url: "/todo",
            type: "POST",
            headers: {"X-CSRF-Token": $("#__anti-forgery-token").val()},
            data: { title: $("#title").val(), description : $("#description").val() } ,
            dataType: "json",
            success: function(result, textStatus, jqXHR) {
                console.log(result);
                parentElement.append(result);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.log("Failed to create: " + textStatus);
            }
        });
    });
});

