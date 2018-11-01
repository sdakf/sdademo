$("#sortable").sortable();
$("#sortable").disableSelection();

countTodos();

$("#checkAll").click(function () {
    allDone();
});

$('.add-todo').on('keypress', function (e) {
    e.preventDefault
    if (e.which == 13) {
        if ($(this).val() != '') {
            var todo = $(this).val();
            createTodo(todo);
            countTodos();
        }
    }
});

$('.todolist').on('change', '#sortable li input[type="checkbox"]', function () {
    var checkBox = $(this);
    if (checkBox.prop('checked')) {
        var doneItem = checkBox.parent().parent().find('p').text();

        $.ajax({
            url: '/todos/' + doneItem,
            type: 'PUT',
            async: false,
            success: function (result) {
                checkBox.parent().parent().parent().addClass('remove');
                done(doneItem);
                countTodos();
            },
            error: function (error) {
                checkBox.prop('checked', false);
                alert('Nie można zaktualizować !');
            }
        });

    }
});

$('.todolist').on('click', '.remove-item', function () {
    removeItem(this);
});

// count tasks
function countTodos() {
    var count = $("#sortable li").length;
    $('.count-todos').html(count);
}

function createTodo(text) {
    $.post("/todos/" + text)
        .done(function (result) {
            var markup = '<li class="ui-state-default"><div class="checkbox"><label><input type="checkbox" value="" /><p>' + text + '</p></label></div></li>';

            $('#sortable').append(markup);
            $('.add-todo').val('');
        })
        .fail(function (jqXHR, textStatus, errorThrown) {
            alert('Nie można dodać !');
        })
}

function done(doneItem) {
    var done = doneItem;
    var markup = '<li><p>' + done + '</p><button class="btn btn-default btn-xs pull-right  remove-item"><span class="glyphicon glyphicon-remove"></span></button></li>';
    $('#done-items').append(markup);
    $('.remove').remove();
}

function allDone() {
    var myArray = [];

    $('#sortable li').each(function () {
        myArray.push($(this).text());
    });

    var notAddedTasks = [];

    // add to done
    for (i = 0; i < myArray.length; i++) {
        var text = myArray[i];
        $.ajax({
            url: '/todos/' + text,
            type: 'PUT',
            async: false,
            success: function(result) {
                $('#done-items').append('<li><p>' + text + '</p><button class="btn btn-default btn-xs pull-right  remove-item"><span class="glyphicon glyphicon-remove"></span></button></li>');
            },
            error: function (error) {
                notAddedTasks.push(text);
            }
        });
    }

    $('#sortable li').remove();

    for (i = 0; i < notAddedTasks.length; i++) {
        var text = notAddedTasks[i];

        var markup = '<li class="ui-state-default"><div class="checkbox"><label><input type="checkbox" value="" /><p>' + text + '</p></label></div></li>';
        $('#sortable').append(markup);
    }
    countTodos();
}

function removeItem(element) {
    var text = $(element).parent().find('p').text();

    $.ajax({
        url: '/todos/' + text,
        type: 'DELETE',
        async: false,
        success: function (result) {
            $(element).parent().remove();
        },
        error: function (error) {
            alert('Nie można usunąć!');
        }
    });

}