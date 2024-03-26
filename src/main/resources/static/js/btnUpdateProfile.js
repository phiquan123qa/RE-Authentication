document.addEventListener('DOMContentLoaded', function() {
    const $updateForm = $('#form-update-info');
    const modifyButton = document.getElementById('modifyButton');
    const updateButton = document.getElementById('updateButton');
    const formInputs =
        document.querySelectorAll('.row input:not([type=button]):not([type=submit]), .row textarea, .row select');
    const fileInput = document.querySelector('.file-input');
    const $imagePreview = $('#imagePreview');
    var originalImageSrc = $imagePreview.attr('src');
    const originalValues = {};

    formInputs.forEach(function(input) {
        if (input.type !== 'file') {
            originalValues[input.name] = input.value;
        }
    });
    modifyButton.addEventListener('click', function() {
        if (modifyButton.value === 'Modify Profile') {
            modifyButton.value = 'Cancel';
            updateButton.disabled = false;
            formInputs.forEach(function(input) {
                input.disabled = false;
            });
            fileInput.disabled = false;
        } else {
            modifyButton.value = 'Modify Profile';
            updateButton.disabled = true;
            formInputs.forEach(function(input) {
                if (input.tagName === 'SELECT') {
                    input.disabled = true;
                } else {
                    input.disabled = true;
                    input.value = originalValues[input.name] || '';
                }
            });
            fileInput.value = '';
            fileInput.disabled = true;
            $('.file-msg').text('or drop files here');
            $('.item-delete').hide();
            if (originalImageSrc) {
                $imagePreview.attr('src', originalImageSrc).show();
            } else {
                $imagePreview.hide();
            }
        }
    });
    updateButton.addEventListener("click", function(event) {
        if (modifyButton.value === 'Cancel') {
            modifyButton.value = 'Modify Profile';
            updateButton.disabled = true;
            const formData = new FormData($updateForm[0]);
            if (fileInput.files.length > 0) {
                formData.append('avatar', fileInput.files[0]); // Add file to FormData
            }
            $.ajax({
                url: '/user/upload-avatar',
                type: 'POST',
                data: formData,
                processData: false,
                contentType: false,
                success: function(response) {
                    var userInfo = new FormData($updateForm[0]);
                    userInfo.append('avatar', response.avatarUrl);
                    userInfo = JSON.stringify(Object.fromEntries(userInfo));
                    $.ajax({
                        url: '/user/update',
                        type: 'POST',
                        data: userInfo,
                        processData: false,
                        contentType: 'application/json',
                        success: function(updateResponse) {
                            alert('User updated successfully');
                            formInputs.forEach(function(input) {
                                input.disabled = true;
                            });
                            updateButton.disabled = true;
                        },
                        error: function (error) {
                            console.log('Error updating user data:', error);
                            console.log(userInfo)
                        }
                    })
                },
                error: function (error) {
                    console.log('Error fetching data: ', error);
                }
            })
        }
    });
});