var ctx;

$(function () {
    // https://stackoverflow.com/a/5064235/548473
    ctx = {
        ajaxUrl: "meals/",
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    };
    makeEditable();
});

function filter() {
    $.ajax({
        type: "GET",
        url: ctx.ajaxUrl + "filter/",
        data: $("#filter").serialize(),
    }).done(function (data) {
        addRows(data);
    });
}

function clean() {
    $('#filter')[0].reset();
    $.get(ctx.ajaxUrl, updateTable());
}
