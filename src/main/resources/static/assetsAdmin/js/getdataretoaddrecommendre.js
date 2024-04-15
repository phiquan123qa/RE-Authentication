let responseSave ;
function showAllRe() {
    showSkeletonLoader();
    $.ajax({
        url: '/re/admin/findrerecommenddata',
        type: 'GET',
        success: function (response) {
            responseSave = response;
            console.log(response);
        },
        error: function (error) {
            console.log('Error fetching data: ', error);
        }
    });
    $.ajax({
        url: '/re/admin/findallredata',
        type: 'GET',
        success: function (response) {
            updateData(response)
            console.log(response);
            enableDrag();
        },
        error: function (error) {
            console.log('Error fetching data: ', error);
        }
    });
}

function updateData(data) {
    const content = $('#columnRe');
    const contentRecommend = $('#columnReRecommend');
    content.empty();
    contentRecommend.empty();

    const savedIds = responseSave ? responseSave.response.map(item => item.id) : [];

    data.response.forEach(function (item) {
        const isSaved = savedIds.includes(item.id);

        const element = '<li class="real-estate-item list-group-item d-flex align-items-center" ' +
            'data-id="' + item.id + '">' +
            '<span class="badge bg-primary rounded-pill me-2">'+item.id+'</span>' + item.title + '' +
            '</li>';

        if(isSaved){
            contentRecommend.append(element);
        } else {
            content.append(element);
        }
    });
}

function showSkeletonLoader() {
    const contentRe = $('#columnRe');
    const contentReRecommend = $('#columnReRecommend');
    contentRe.empty();
    contentReRecommend.empty();
    for (let i = 0; i < 6; i++) {
        contentRe.append('<li class="h5 card-title placeholder-glow p-3">' +
            '<span class="placeholder col-3 me-3"></span>' +
            '<span class="placeholder col-8"></span>' +
            '</li>');
    }
    for (let i = 0; i < 6; i++) {
        contentReRecommend.append('<li class="h5 card-title placeholder-glow p-3">' +
            '<span class="placeholder col-3 me-3"></span>' +
            '<span class="placeholder col-8"></span>' +
            '</li>');
    }
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

function saveLoading(){
    $('#saveButton').empty();
    $('#saveButton').append('Save <span  class="spinner-border text-info spinner-border-sm" role="status" aria-hidden="true">\n' +
        '  <span class="visually-hidden">Loading...</span>\n' +
        '</span >')
}
function replaceLoadingSuccess(){
    $('#saveButton .spinner-border').replaceWith('<i class="ti ti-checks ms-2"></i>');
}
function replaceLoadingError(){
    $('#saveButton .spinner-border').replaceWith('<i class="ti ti-x ms-2"></i>');
}


$('#saveButton').click(function () {
    const realEstateIds = [];
    $('#columnReRecommend .real-estate-item').each(function () {
        realEstateIds.push($(this).data('id'));
    });
    saveLoading();
    $.ajax({
        url: '/re/admin/addrecomre',
        type: 'POST',
        data: JSON.stringify({
            realEstateIds: realEstateIds
        }),
        contentType: 'application/json',
        success: function (response) {
            console.log(response);
            replaceLoadingSuccess();
        },
        error: function (error) {
            console.log('Error saving data: ', error);
        }
    });
});
showAllRe();