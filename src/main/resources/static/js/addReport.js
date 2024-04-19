$('#btnSubmitReportRe').click(function () {
    const emailAuthor = document.getElementById('emailReportRe').value;
    const phoneAuthor = document.getElementById('phoneReportRe').value;
    const type = document.getElementById('typeReportRe').value;
    const content = document.getElementById('descReTextarea').value;
    const realEstateId = parseInt($('#idReHolder').text());
    let isValid = true;
    let emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    let phoneRegex = /^\d{10}$/;
    $('.border-danger').removeClass('border-danger');
    if (!emailRegex.test(emailAuthor)) {
        isValid = false;
        $('#emailReportRe').addClass('border-danger');
    }
    if (!phoneRegex.test(phoneAuthor)) {
        isValid = false;
        $('#phoneReportRe').addClass('border-danger');}
    if (type.trim() === '') {
        isValid = false;
        $('#typeReportRe').addClass('border-danger');
    }
    if (content.trim() === '') {
        isValid = false;
        $('#descReTextarea').addClass('border-danger');
    }
    if(isValid) {
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
    }else{
        saberToast.error({
            title: "Report Send fail",
            text: "Report fail!",
            delay: 200,
            duration: 2600,
            rtl: true,
            position: "top-right"
        });
    }
});
$('#btnSubmitReportUser').click(function () {
    const emailAuthor = document.getElementById('emailReportUser').value;
    const phoneAuthor = document.getElementById('phoneReportUser').value;
    const type = document.getElementById('typeReportUser').value;
    const content = document.getElementById('descUserTextarea').value;
    const userId = $('#userIdHolderToReport').text();
    let isValid = true;
    let emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    let phoneRegex = /^\d{10}$/;
    $('.border-danger').removeClass('border-danger');
    if (!emailRegex.test(emailAuthor)) {
        isValid = false;
        $('#emailReportUser').addClass('border-danger');
    }
    if (!phoneRegex.test(phoneAuthor)) {
        isValid = false;
        $('#phoneReportUser').addClass('border-danger');}
    if (type.trim() === '') {
        isValid = false;
        $('#typeReportUser').addClass('border-danger');
    }
    if (content.trim() === '') {
        isValid = false;
        $('#descUserTextarea').addClass('border-danger');
    }
    if (isValid) {
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
    }else{
        saberToast.error({
            title: "Report Send fail",
            text: "Report fail!",
            delay: 200,
            duration: 2600,
            rtl: true,
            position: "top-right"
        });
    }
});

