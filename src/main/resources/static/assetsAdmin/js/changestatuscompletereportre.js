function changestatuscompletereportre(id, status) {
    $.ajax({
        url: '/report/admin/statusre',
        data: JSON.stringify({
            id: id,
            status: status
        }),
        type: 'POST',
        contentType: 'application/json',
        success: function (response) {
            var itemRow = $(`#table-content tr:has(th:contains(${id}))`);
            var statusSpan = itemRow.find('.badgeStatus');
            statusSpan.text(status);
            if (statusSpan.text() === 'Complete') {
                itemRow.find('.btn.btn-danger').prop('disabled', true);
            }
            saberToast.success({
                title: "Complete successfully",
                text: "You have accepted",
                delay: 200,
                duration: 2600,
                rtl: true,
                position: "top-right"
            });
            changeenabletext();
            console.log(response);
        },
        error: function (error) {
            console.log('Error fetching data: ', error);
        }
    });
}