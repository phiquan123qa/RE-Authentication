$(document).ready(function() {
    const $advanceSearch = $('#advanceSearch');
    $('#advanceSearchBtn').on('click', function() {
        if ($advanceSearch.is(":hidden")) {
            $advanceSearch.slideDown();
        }else{
            $advanceSearch.slideUp();
        }
    });
    if(!allParamsAreEmpty()){
        $advanceSearch.slideDown();
    }
    $('#sort').change(function() {
        const icon_rotate = $('.icon-rotate');
        if ($(this).is(':checked')) {
            icon_rotate.addClass('icon-keyboard_arrow_up');
            icon_rotate.addClass('icon-keyboard_arrow_down');
        } else {
            icon_rotate.removeClass('icon-keyboard_arrow_up');
            icon_rotate.addClass('icon-keyboard_arrow_down');
        }
    });
});