$(document).ready(function() {
    $('#descriptionID').on('input', function() {
        const textWithBrTags = $(this).val().replace(/\n/g, "<br/>");
        $(this).html(textWithBrTags);
    });
});