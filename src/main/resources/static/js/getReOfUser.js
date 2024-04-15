const currentUrl = window.location.href;
const url = new URL(currentUrl);
const searchParams = new URLSearchParams(url.search);
const titleValue = searchParams.get('title');
const typeValue = searchParams.get('type');
const cityValue = searchParams.get('city');
const districtValue = searchParams.get('district');
const wardValue = searchParams.get('ward');
const sortValue = searchParams.get('sort');
let contentHolder;
function allParamsAreEmpty() {
    return !titleValue && !typeValue && !cityValue && !districtValue && !wardValue && !sortValue;
}
function fetchReOfUser(pageNumber) {
    scrollToElement('content');
    showSkeletonLoader();
    $.ajax({
        url: '/re/listRe',
        type: 'GET',
        data: {
            offset: pageNumber,
            pageSize: 9,
            title: titleValue,
            type: typeValue,
            city: cityValue,
            district: districtValue,
            ward: wardValue,
            sort: sortValue
        },
        success: function (response) {
            if(allParamsAreEmpty() && response.totalElements === 0){
                nonDataFound();
            }else{
                contentHolder = response.content;
                console.log(contentHolder);
                updatePage(response);
                updatePaginationText(pageNumber, response.totalPages);
                createPagination(response.totalPages, pageNumber);
            }

        },
        error: function (error) {
            console.log('Error fetching data: ', error);
        }
    })
}
function nonDataFound(){
    const content = $('#content-container');
    content.empty();
    content.append('<div class="row px-4 pt-4">\n' +
        '                <div class="col-12 position-relative text-center">\n' +
        '                    <img class="" src="/static/images/welcome_new_user.dcf416a4.png" alt="well come new user">\n' +
        '                    <div class="position-absolute top-50 left-0 w-100 h-100">\n' +
        '                        <h5 class="mb-3">Non have real estate to show</h5>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '                <div class="col-12 text-center">\n' +
        '                    <p>Post your first Real Estate here to sell or rent</p>\n' +
        '                </div>\n' +
        '                <div class="col-12 text-center mb-3">\n' +
        '                    <a class="btn btn-secondary">Post New Real Estate</a>\n' +
        '                </div>\n' +
        '            </div>');
}

function showSkeletonLoader(){
    var loaderHTML = '';
    for(let i = 0; i < 5; i++) {
        loaderHTML += '<div class="row px-4 py-4" style="box-shadow: rgba(0, 0, 0, 0.15) 0 1px 4px;">\n' +
            '                    <div class="card mb-3" aria-hidden="true">\n' +
            '                        <div class="row no-gutters">\n' +
            '                            <div class="col-3">\n' +
            '                                <svg class="bd-placeholder-img card-img-top"\n' +
            '                                     width="100%" height="100%"\n' +
            '                                     xmlns="http://www.w3.org/2000/svg"\n' +
            '                                     role="img" aria-label="Placeholder"\n' +
            '                                     preserveAspectRatio="xMidYMid slice"\n' +
            '                                     focusable="false">\n' +
            '                                    <title>Placeholder</title>\n' +
            '                                    <rect width="100%" height="100%" fill="#868e96"></rect>\n' +
            '                                </svg>\n' +
            '                            </div>\n' +
            '                            <div class="col-7">\n' +
            '                                <div class="card-body">\n' +
            '                                    <div class="h5 card-title placeholder-glow">\n' +
            '                                        <span class="placeholder col-12"></span>\n' +
            '                                    </div>\n' +
            '                                    <div class="card-text placeholder-glow">\n' +
            '                                        <span class="placeholder col-6"></span>\n' +
            '                                    </div>\n' +
            '                                    <div class="card-text placeholder-glow">\n' +
            '                                        <span class="placeholder col-4"></span>\n' +
            '                                    </div>\n' +
            '                                    <div class="card-text placeholder-glow">\n' +
            '                                        <span class="placeholder col-4"></span>\n' +
            '                                        <span class="placeholder col-4"></span>\n' +
            '                                        <span class="placeholder col-4"></span>\n' +
            '                                    </div>\n' +
            '                                    <div class="card-text placeholder-glow">\n' +
            '                                        <span class="placeholder col-12"></span>\n' +
            '                                    </div>\n' +
            '                                    <div class="card-text placeholder-glow">\n' +
            '                                        <span class="placeholder col-4"></span>\n' +
            '                                        <span class="placeholder col-4"></span>\n' +
            '                                    </div>\n' +
            '                                    <div class="card-text placeholder-glow">\n' +
            '                                        <span class="placeholder col-4"></span>\n' +
            '                                        <span class="placeholder col-4"></span>\n' +
            '                                    </div>\n' +
            '                                </div>\n' +
            '                            </div>\n' +
            '                            <div class="col-2">\n' +
            '                                <div class="h3 card-title placeholder-glow">\n' +
            '                                    <span class="placeholder col-12"></span>\n' +
            '                                </div>\n' +
            '                                <div class="h3 card-title placeholder-glow">\n' +
            '                                    <span class="placeholder col-12"></span>\n' +
            '                                </div>\n' +
            '                                <div class="h3 card-title placeholder-glow">\n' +
            '                                    <span class="placeholder col-12"></span>\n' +
            '                                </div>\n' +
            '                            </div>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </div>';
    }
    $('#list-card-content').html(loaderHTML);
}

function updatePage(data) {
    const content = $('#list-card-content');
    content.empty();
    data.content.forEach(function(item) {
        let buttonText = item.statusRe === "DISABLED" ? "Disable" : "Modify";
        let buttonDisabled = item.statusRe === "DISABLED" ? "disabled" : "";
        let buttonClass = item.statusRe === "DISABLED" ? "btn-danger" : "btn-secondary";
        $('#list-card-content').append(
            `<div class="row px-4 py-4 mb-3" style="box-shadow: rgba(0, 0, 0, 0.15) 0 1px 4px;">
                    <div class="card mb-3" aria-hidden="true">
                        <div class="row no-gutters">
                            <div class="col-3">
                                <img src="/static/images/real_estate_images/${item.mainImage}" class="card-img" alt="...">
                            </div>
                            <div class="col-7">
                                <div class="card-body row">
                                    <h5 class="card-title col-12 mb-2">
                                        Title: ${item.title}
                                    </h5>
                                    <div class="card-text price col-6 d-flex flex-wrap mb-2">
                                        <div class="fw-bolder me-1">Price: </div>${item.price != null ? `$${item.price}` : 'Negotiated price'}
                                    </div>
                                    <div class="card-text col-6 d-flex flex-wrap mb-2">
                                        <div class="fw-bolder me-1">Land Area: </div>${item.landArea != null ? item.landArea : '?'} m&sup2
                                    </div>
                                    <div class="card-text col-12 d-flex flex-wrap mb-2">
                                        <div class="fw-bolder me-1">Location: </div>${item.wardRe}, ${item.districtRe}, ${item.cityRe}
                                    </div>
                                    <div class="card-text col-12 d-flex flex-wrap mb-2">
                                        <div class="fw-bolder me-1">Address: </div>${item.address}
                                    </div>
                                    <div class="card-text col-6 d-flex flex-wrap mb-2">
                                        <div class="fw-bolder me-1">Date Started: </div>${item.dateStart}
                                    </div>
                                    <div class="card-text col-6 d-flex flex-wrap mb-2">
                                        <div class="fw-bolder me-1">Date End: </div>${item.dateEnd}
                                    </div>
                                    <div class="card-text col-6 d-flex flex-wrap mb-2">
                                        <div class="fw-bolder me-1">Legal Document:</div> ${item.legalDocument}
                                    </div>
                                    <div class="card-text col-6 d-flex flex-wrap mb-2">
                                        <div class="fw-bolder me-1">Interior: </div>${item.interior}
                                    </div>
                                    <div class="card-text col-12 d-flex flex-wrap mb-2">
                                        <button class="btn ${buttonClass} update-re" value="${item.id}" ${buttonDisabled}
                                         data-bs-toggle="modal" data-bs-target="#staticBackdrop">${buttonText}</button>
                                    </div>
                                </div>
                            </div>
                            <div class="col-2 d-flex flex-wrap flex-column justify-content-evenly">
                                <div class="card-title d-flex flex-wrap mb-2">
                                    <div class="fw-bolder me-1">Rooms:</div> ${item.room}
                                </div>
                                <div class="card-title d-flex flex-wrap mb-2">
                                    <div class="fw-bolder me-1">Bathrooms:</div> ${item.bathRoom}
                                </div>
                                <div class="card-title d-flex flex-wrap mb-2">
                                    <div class="fw-bolder me-1">Bedrooms:</div> ${item.bedRoom}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>`
        );
        console.log(item.title);
    });
}
function createPagination(totalPages, currentPage) {
    const range = 2;
    $('#pagination').empty();
    $('#pagination').append(createPageItem(0, currentPage));
    if (currentPage - range > 1) {
        $('#pagination').append('<a class="pagination-ellipsis">&hellip;</a>');
    }
    for (let i = Math.max(1, currentPage - range); i < Math.min(totalPages - 1, currentPage + range + 1); i++) {
        $('#pagination').append(createPageItem(i, currentPage));
    }
    if (currentPage + range < totalPages - 2) {
        $('#pagination').append('<a class="pagination-ellipsis">&hellip;</a>');
    }
    if (totalPages > 1) {
        $('#pagination').append(createPageItem(totalPages - 1, currentPage));
    }
}
function createPageItem(pageNumber, currentPage) {
    let activeClass = pageNumber === currentPage ? 'active' : '';
    return `<a class="page ${activeClass}" onclick="fetchReOfUser(${pageNumber})">${pageNumber + 1}</a>`;
}
function updatePaginationText(currentPage, totalPages) {
    $('.pagination-text').text(`Pagination (${currentPage+1} of ${totalPages})`);
}
function scrollToElement(id) {
    var element = $('#' + id);
    if (element.length) {
        $('html, body').animate({
            scrollTop: element.offset().top
        }, 700);
    } else {
        console.error("Element with id '" + id + "' not found.");
    }
}

$('#list-card-content').on('click', '.update-re', function () {
    const itemId = $(this).attr('value');
    console.log(itemId);
    let selectedItem = contentHolder.find(item => item.id === parseInt(itemId));
    console.log(selectedItem ?? "Not present");
    $('[name="idUpdate"]').val(selectedItem.id);
    $('input[name="typeUpdate"][value="' + selectedItem.type + '"]').prop('checked', true);
    $('[name="titleUpdate"]').val(selectedItem.title);
    $('[name="cityUpdate"]').val(selectedItem.cityRe);
    $('[name="districtUpdate"]').val(selectedItem.districtRe);
    $('[name="wardUpdate"]').val(selectedItem.wardRe);
    $('[name="addressUpdate"]').val(selectedItem.address);
    $('[name="landAreaUpdate"]').val(selectedItem.landArea);
    $('[name="priceUpdate"]').val(selectedItem.price);
    $('input[name="legalDocumentUpdate"][value="' + selectedItem.legalDocument + '"]').prop('checked', true);
    $('input[name="interiorUpdate"][value="' + selectedItem.interior + '"]').prop('checked', true);
    $('[name="descriptionUpdate"]').val(selectedItem.description.replace(/<br\/>/g, "\n"));
    $('[name="roomUpdate"]').val(selectedItem.room);
    $('[name="bathRoomUpdate"]').val(selectedItem.bathRoom);
    $('[name="bedRoomUpdate"]').val(selectedItem.bedRoom);
});
$('#btnUpdate').click(function () {
    let typeUpdate = $('input[name="typeUpdate"]:checked').val();
    let titleUpdate = $('[name="titleUpdate"]').val();
    let addressUpdate = $('[name="addressUpdate"]').val();
    let priceUpdate = $('[name="priceUpdate"]').val();
    let legalDocumentUpdate = $('input[name="legalDocumentUpdate"]:checked').val();
    let interiorUpdate = $('input[name="interiorUpdate"]:checked').val();
    let descriptionUpdate = $('[name="descriptionUpdate"]').val().replace(/\n/g, "<br/>");
    let roomUpdate = $('[name="roomUpdate"]').val();
    let bathRoomUpdate = $('[name="bathRoomUpdate"]').val();
    let bedRoomUpdate = $('[name="bedRoomUpdate"]').val();
    let idUpdate = $('[name="idUpdate"]').val();
    $.ajax({
        type: 'POST',
        url: '/re/update',
        contentType: 'application/json',
        data: JSON.stringify({
            id: idUpdate,
            type: typeUpdate,
            title: titleUpdate,
            address: addressUpdate,
            price: priceUpdate,
            legalDocument: legalDocumentUpdate,
            interior: interiorUpdate,
            description: descriptionUpdate,
            room: roomUpdate,
            bathRoom: bathRoomUpdate,
            bedRoom: bedRoomUpdate
        }),
        success: function (data) {
            alert('Re updated successfully');
            console.log(data);
            $('#staticBackdrop').modal('hide');
        },
        error: function (error) {
            console.log(error);
        }
    })
});


fetchReOfUser(0);