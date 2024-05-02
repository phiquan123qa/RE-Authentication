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
            $('#closeModal').click();
            var itemRow = $(`#table-content tr:has(th:contains(${id}))`);
            var statusSpan = itemRow.find('.badgeStatus');
            statusSpan.text(status);
            if (statusSpan.text() === 'DISABLED') {
                itemRow.find('.btn.btn-danger').prop('disabled', true);
            }
            changestatustext();
            saberToast.success({
                title: "Disable successfully",
                text: "You have disabled a property",
                delay: 200,
                duration: 2600,
                rtl: true,
                position: "top-right"
            });
            console.log(response);
        },
        error: function (error) {
            console.log('Error fetching data: ', error);
            saberToast.error({
                title: "Disable failed",
                text: "Have some error, please try again",
                delay: 200,
                duration: 2600,
                rtl: true,
                position: "top-right"
            });
        }
    });
}