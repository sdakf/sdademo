var $sortable = $("#sortable");
$sortable.sortable();
$sortable.disableSelection();

countTodos();

$("#checkAll").click(function () {
    markAllAsDone();
});

$('#addTodo').on('click', function () {
    var taskValue = $('#task').val();
    var calendarValue = $('#calendar').val();
    if (taskValue != '' && calendarValue != '') {
        createTodo(taskValue, calendarValue);
    }else {
        alert("Wypełnij pola dotyczące zadania.")
    }
});

$('.todolist').on('change', '#sortable li input[type="checkbox"]', function () {
    var checkBox = $(this);
    if (checkBox.prop('checked')) {
        var predicate = $('p.tvalue');
        var doneItem = checkBox.parent().parent().find(predicate).text();
        $.ajax({
            url: '/todos/' + doneItem,
            type: 'PUT',
            async: false,
            success: function () {
                checkBox.parent().parent().parent().addClass('remove');
                done(doneItem);
                countTodos();
            },
            error: function () {
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
    $('#count-todos').html(count);
}

function createTodo(text, calendarValue) {
    $.post("/todos/" + text + "/" + calendarValue)
        .done(function (result) {
            var markup = '<li class="ui-state-default"><div class="checkbox"><label  style="width: 100%"><input type="checkbox" value="" />' +

                ' <table style="width: 100%">\n' +
                '                                    <tr>\n' +
                '                                        <td width="50%"><p class="tvalue">'+text+'</p></td>\n' +
                '                                        <td width="50%" style="text-align: right"><p>'+toDate(new Date(calendarValue).toISOString().replace(/[T]/g, " ").substring(0,11))+'</p></td>\n' +
                '                                    </tr>\n' +
                '                                </table>'
                '</label></div></li>';

            $('#sortable').append(markup);
            $('.add-todo').val('');
            countTodos();

        })
        .fail(function (jqXHR, textStatus, errorThrown) {
            alert('Nie można dodać, zadanie o takiej nazwie istnieje!');
        })
}
function toDate(dateStr) {
    var parts = dateStr.split("-")
    return parts[2].trim() + "-" + (parts[1]).trim() + "-" + parts[0].trim();
}
function done(done) {
    var markup = '<li><p>' + done + '</p><button class="btn btn-default btn-xs pull-right  remove-item" style="margin-top: -25px;"><span class="glyphicon glyphicon-remove"></span></button></li>';
    $('#done-items').append(markup);
    $('.remove').remove();
}

function markAllAsDone() {
    var myArray = [];
    var predicate = $('p.tvalue');
    $('#sortable li').find(predicate).each(function () {
        console.log($(this))
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
            success: function () {
                $('#done-items').append('<li><p>' + text + '</p><button class="btn btn-default btn-xs pull-right  remove-item" style="margin-top: -25px;"><span class="glyphicon glyphicon-remove"></span></button></li>');
            },
            error: function () {
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
        success: function () {
            $(element).parent().remove();
        },
        error: function () {
            alert('Nie można usunąć!');
        }
    });

}

// $(function() {
//     $( "#calendar" ).datepicker({ dateFormat: 'yy-mm-dd'});
// });