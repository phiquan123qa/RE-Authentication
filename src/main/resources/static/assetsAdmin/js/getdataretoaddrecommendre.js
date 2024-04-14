function showAllRe() {
    $.ajax({
        url: '/re/admin/findallredata',
        type: 'GET',
        success: function (response) {
            updateData(response)
            enableDrag();
            console.log(response);
        },
        error: function (error) {
            console.log('Error fetching data: ', error);
        }
    });
}

function updateData(data) {
    const content = $('#columnRe');
    content.empty();
    data.response.forEach(function (item) {
        content.append('<li class="real-estate-item list-group-item" ' +
            'data-id="' + item.id + '">' + item.title + '</li>');
    });
}

function enableDrag() {
    // Make real estate items draggable
    $('.real-estate-item').draggable({
        revert: 'invalid',
        containment: 'document',
        helper: 'clone'
    });

    // Make column 2 droppable
    $('#columnReRecommend').droppable({
        accept: '.real-estate-item',
        drop: function (event, ui) {
            $(this).append(ui.draggable);
        }
    });

    // Make column 1 droppable
    $('#columnRe').droppable({
        accept: '.real-estate-item',
        drop: function (event, ui) {
            $(this).append(ui.draggable);
        }
    });
}
$('#saveButton').click(function () {
    const realEstateIds = [];
    $('#columnReRecommend .real-estate-item').each(function () {
        realEstateIds.push($(this).data('id'));
    });

    $.ajax({
        url: '/re/admin/addrecomre',
        type: 'POST',
        data: JSON.stringify({
            realEstateIds: realEstateIds
        }),
        contentType: 'application/json',
        success: function (response) {
            console.log(response);
        },
        error: function (error) {
            console.log('Error saving data: ', error);
        }
    });
});
showAllRe();