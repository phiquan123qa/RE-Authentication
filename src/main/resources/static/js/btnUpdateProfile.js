document.addEventListener('DOMContentLoaded', function() {
    const modifyButton = document.getElementById('modifyButton');
    const updateButton = document.getElementById('updateButton');
    const formInputs =
        document.querySelectorAll('.row input:not([type=button]):not([type=submit]), .row textarea, .row select');
    const fileInput = document.querySelector('.file-input');
    const $imagePreview = $('#imagePreview');
    const originalValues = {};

    // Save original form data
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
            $imagePreview.hide();
        }
    });
});