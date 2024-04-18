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
            if(isEnable === true) {
                $('#closeEnableModal').click();
                let itemRow = $(`#table-content tr:has(th:contains(${id}))`);
                let statusSpan = itemRow.find('.badgeStatus');
                statusSpan.text('Enabled');
            }else if (isEnable === false) {
                $('#closeDisableModal').click();
                let itemRow = $(`#table-content tr:has(th:contains(${id}))`);
                let statusSpan = itemRow.find('.badgeStatus');
                statusSpan.text('Disabled');
            }
            changeenabletext();
            console.log(response);
        },
        error: function (error) {
            console.log('Error fetching data: ', error);
        }
    });
}