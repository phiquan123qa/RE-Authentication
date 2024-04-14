function changedisableuser(id, isEnable) {
    $.ajax({
        url: '/user/admin/disableuser',
        data: JSON.stringify({
            id: id,
            isEnable: isEnable
        }),
        type: 'POST',
        contentType: 'application/json',
        success: function (response) {
            $('#closeModal').click();
            var itemRow = $(`#table-content tr:has(th:contains(${id}))`);
            var statusSpan = itemRow.find('.badgeStatus');
            statusSpan.text('Disabled');
            if (statusSpan.text() === 'Disabled') {
                itemRow.find('.btn.btn-danger').prop('disabled', true);
            }
            changeenabletext();
            console.log(response);
        },
        error: function (error) {
            console.log('Error fetching data: ', error);
        }
    });
}