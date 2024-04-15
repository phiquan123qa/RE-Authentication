$('#btnSubmitReportRe').click(function () {
    var emailAuthor = document.getElementById('emailReportRe').value;
    var phoneAuthor = document.getElementById('phoneReportRe').value;
    var type = document.getElementById('typeReportRe').value;
    var content = document.getElementById('descReTextarea').value;
    var realEstateId = parseInt($('#idReHolder').text());
    $.ajax({
        type: 'POST',
        url: '/report/re',
        data: JSON.stringify({
            emailAuthor: emailAuthor,
            phoneAuthor: phoneAuthor,
            type: type,
            content: content,
            realEstateId: realEstateId,
            status: "Pending"
        }),
        contentType: 'application/json',
        success: function (response) {
            console.log(response);
            saberToast.success({
                title: "Report success",
                text: "Report success!",
                delay: 200,
                duration: 2600,
                rtl: true,
                position: "top-right"
            });
            $('#reportReBackdrop').modal('hide');
        },
        error: function (error) {
            console.log(error);
            saberToast.error({
                title: "Report fail",
                text: "Report fail!",
                delay: 200,
                duration: 2600,
                rtl: true,
                position: "top-right"
            });
        }
    });
});
$('#btnSubmitReportUser').click(function () {
    var emailAuthor = document.getElementById('emailReportUser').value;
    var phoneAuthor = document.getElementById('phoneReportUser').value;
    var type = document.getElementById('typeReportUser').value;
    var content = document.getElementById('descUserTextarea').value;
    var userId = $('#userIdHolderToReport').text();
    $.ajax({
        type: 'POST',
        url: '/report/user',
        data: JSON.stringify({
            emailAuthor: emailAuthor,
            phoneAuthor: phoneAuthor,
            type: type,
            content: content,
            userId: userId,
            status: "Pending"
        }),
        contentType: 'application/json',
        success: function (response) {
            console.log(response);
            saberToast.success({
                title: "Report success",
                text: "Report success!",
                delay: 200,
                duration: 2600,
                rtl: true,
                position: "top-right"
            });
            $('#reportUserBackdrop').modal('hide');
        },
        error: function (error) {
            console.log(error);
            saberToast.error({
                title: "Report fail",
                text: "Report fail!",
                delay: 200,
                duration: 2600,
                rtl: true,
                position: "top-right"
            });
        }
    });
});

