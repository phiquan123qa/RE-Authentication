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
                saberToast.success({
                    title: "Success",
                    text: "User has been enabled",
                    delay: 200,
                    duration: 2600,
                    rtl: true,
                    position: "top-right"
                });
            }else if (isEnable === false) {
                $('#closeDisableModal').click();
                let itemRow = $(`#table-content tr:has(th:contains(${id}))`);
                let statusSpan = itemRow.find('.badgeStatus');
                statusSpan.text('Disabled');
                saberToast.success({
                    title: "Success",
                    text: "User has been disabled",
                    delay: 200,
                    duration: 2600,
                    rtl: true,
                    position: "top-right"
                });
            }
            changeenabletext();
            console.log(response);
        },
        error: function (error) {
            console.log('Error fetching data: ', error);
        }
    });
}