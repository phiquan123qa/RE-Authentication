$(document).ready(function() {
    var $advanceSearch = $('#advanceSearch');
    $('#titleID').on('click', function() {
        // Check if the element is currently visible
        if ($advanceSearch.is(":hidden")) {
            // If it is visible, slide it up
            $advanceSearch.slideDown();
        }
    });
});