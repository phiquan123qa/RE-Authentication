function getdatafavoritelist() {
    showSkeletonLoader();
    $.ajax({
        url: "/re/favorite/list",
        type: "GET",
        success: function (response) {
            console.log(response);
            if( response.recordCount === 0){
                nonDataFound();
            }else{
                updatePage(response);
            }
        },
        error: function () {
            alert("error");
        }
    });
}
function nonDataFound(){
    const content = $('#list-card-content');
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
    data.response.forEach(function(item) {
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
                                        <a class="update-re px-3 py-2 border border-1" href="/property/${item.id}">See Details</a>
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
getdatafavoritelist();