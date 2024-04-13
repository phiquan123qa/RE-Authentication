function changestatusre(id, status) {
    $.ajax({
        url: '/re/admin/status',
        data: JSON.stringify({
            id: id,
            status: status
        }),
        type: 'POST',
        contentType: 'application/json',
        success: function (response) {
            $('#closeModal').click();
            var itemRow = $(`#table-content tr:has(th:contains(${id}))`);
            var statusSpan = itemRow.find('.badgeStatus');
            statusSpan.text(status);
            if (statusSpan.text() === 'DISABLED') {
                itemRow.find('.btn.btn-danger').prop('disabled', true);
            }
            changestatustext();
            console.log(response);
        },
        error: function (error) {
            console.log('Error fetching data: ', error);
        }
    });
}