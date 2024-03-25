const $fileInput = $('.file-input');
const $droparea = $('.file-drop-area');
const $delete = $('.item-delete');
const $imagePreview = $('#imagePreview');

$fileInput.on('dragenter focus click', function () {
    $droparea.addClass('is-active');
});

$fileInput.on('dragleave blur drop', function () {
    $droparea.removeClass('is-active');
});
$fileInput.on('change', function () {
    let filesCount = $(this)[0].files.length;
    let $textContainer = $(this).prev('.file-msg');
    if (filesCount === 1) {
        let file = $(this)[0].files[0];
        let fileName = $(this).val().split('\\').pop();
        $textContainer.text(fileName);
        $delete.css('display', 'inline-block');
        let reader = new FileReader();
        reader.onload = function(e) {
            $imagePreview.attr('src', e.target.result).show();
        };
        reader.readAsDataURL(file);
    } else if (filesCount === 0) {
        $textContainer.text('or drop files here');
        $('.item-delete').css('display', 'none');
        $imagePreview.hide();
    } else {
        $textContainer.text(filesCount + ' files selected');
        $('.item-delete').css('display', 'inline-block');
    }
});
$delete.on('click', function () {
    $fileInput.val(null);
    $('.file-msg').text('or drop files here');
    $(this).hide();
    $imagePreview.hide();
});