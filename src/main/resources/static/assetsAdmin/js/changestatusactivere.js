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
            if (status === 'ACTIVE') {
                saberToast.success({
                    title: "Accept successfully",
                    text: "You have accepted",
                    delay: 200,
                    duration: 2600,
                    rtl: true,
                    position: "top-right"
                });
            } else if (status === 'DISABLED') {
                saberToast.success({
                    title: "Deny successfully",
                    text: "You have denied",
                    delay: 200,
                    duration: 2600,
                    rtl: true,
                    position: "top-right"
                });
            }
            console.log(response);
        },
        error: function (error) {
            console.log('Error fetching data: ', error);
            saberToast.error({
                title: "Error",
                text: "Have some error, please try again",
                delay: 200,
                duration: 2600,
                rtl: true,
                position: "top-right"
            });
        }
    });
}