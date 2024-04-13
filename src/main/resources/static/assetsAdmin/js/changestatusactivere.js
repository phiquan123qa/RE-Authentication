function changestatusdisablere(id, status) {
    $.ajax({
        url: '/re/admin/status',
        data: JSON.stringify({
            id: id,
            status: status
        }),
        type: 'POST',
        contentType: 'application/json',
        success: function (response) {
            $(`#table-content tr:has(th:contains(${id}))`).remove();
            console.log(response);
        },
        error: function (error) {
            console.log('Error fetching data: ', error);
        }
    });
}