function changespublishedwiki(id, isPublished) {
    $.ajax({
        url: '/wiki/admin/changepublished',
        data: JSON.stringify({
            id: id,
            isPublished: isPublished
        }),
        type: 'POST',
        contentType: 'application/json',
        success: function (response) {
            if(isPublished === true) {
                $('#closeEnableModal').click();
                let itemRow = $(`#table-content tr:has(th:contains(${id}))`);
                let statusSpan = itemRow.find('.badgeType');
                statusSpan.text('Published');
            }else if (isPublished === false) {
                $('#closeDisableModal').click();
                let itemRow = $(`#table-content tr:has(th:contains(${id}))`);
                let statusSpan = itemRow.find('.badgeType');
                statusSpan.text('Unpublished');
            }
            changestatustext();
            console.log(response);
        },
        error: function (error) {
            console.log('Error fetching data: ', error);
        }
    });
}