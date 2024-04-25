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
                saberToast.success({
                    title: "Published successfully",
                    text: "You have published a new wiki",
                    delay: 200,
                    duration: 2600,
                    rtl: true,
                    position: "top-right"
                });
            }else if (isPublished === false) {
                $('#closeDisableModal').click();
                let itemRow = $(`#table-content tr:has(th:contains(${id}))`);
                let statusSpan = itemRow.find('.badgeType');
                statusSpan.text('Unpublished');
                saberToast.success({
                    title: "Unpublished successfully",
                    text: "You have unpublished a wiki",
                    delay: 200,
                    duration: 2600,
                    rtl: true,
                    position: "top-right"
                });
            }
            changestatustext();
            console.log(response);
        },
        error: function (error) {
            console.log('Error fetching data: ', error);
            saberToast.error({
                title: "Unpublished failed",
                text: "Have some error, please try again",
                delay: 200,
                duration: 2600,
                rtl: true,
                position: "top-right"
            });
        }
    });
}