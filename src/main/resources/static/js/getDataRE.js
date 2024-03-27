
function fetchPage(pageNumber) {
    var titleValue = $('#titleID').val();
    var typeValue = $('#typeID').find(":selected").val();
    var cityValue = $('#cityID').find(":selected").val();
    var districtValue = $('#districtID').find(":selected").val();
    var wardValue = $('#wardID').find(":selected").val();
    // var fieldValue = $('#fieldID').val();
    showSkeletonLoader();
    $.ajax({
        url: '/re/findall',
        type: 'GET',
        data: {
            offset: pageNumber,
            pageSize: 9,
            title: titleValue,
            typeRe: typeValue,
            city: cityValue,
            district: districtValue,
            ward: wardValue
            // field: ""
        },
        success: function(response){
            updatePage(response);
            updatePaginationText(pageNumber, response.response.totalPages);
            createPagination(response.response.totalPages, pageNumber);
            console.log(response);
        },
        error: function(error){
            console.log('Error fetching data: ', error);
        }
    });
}
function updatePage(data) {
    var content = $('#content');
    content.empty();
    data.response.content.forEach(function(item) {
        $('#content').append(
            `<div class="col-xs-12 col-sm-6 col-md-6 col-lg-4">
            <div class="property-item mb-30">
              <a href="/property/${item.id}" class="img">
                <img loading="lazy" src="/static/images/img_1.jpg" alt="Image" class="img-fluid"/>
              </a>
              <div class="property-content">
                <div class="price mb-2"><span>${item.price != null ? `$${item.price}` : 'Negotiated price'}</span></div>
                <div>
                  <span class="d-block mb-2 text-black-50">${item.wardRe}, ${item.districtRe}, ${item.cityRe}</span>
                  <span class="city d-block mb-3">${item.title}</span>

                  <div class="specs d-flex flex-wrap mb-4">
                    <span class="d-block d-flex align-items-center me-3 mb-2">
                      <span class="icon-bed me-2"></span>
                      <span class="caption">${item.room != null ? item.room : 1} bed</span>
                    </span>
                    <span class="d-block d-flex align-items-center me-3 mb-2">
                      <span class="icon-bath me-2"></span>
                      <span class="caption">${item.bedRoom != null ? item.bedRoom : 1} bath</span>
                    </span>
                    <span class="d-block d-flex align-items-center me-3 mb-2">
                      <span class="icon-home me-2"></span>
                      <span class="caption" >${item.landArea != null ? item.landArea : '?'} m&sup2</span>
                    </span>
                  </div>
                  <a
                    href="/property/${item.id}"
                    class="btn btn-primary py-2 px-3">
                    See details
                    </a>
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
    return `<a class="page ${activeClass}" onclick="fetchPage(${pageNumber})">${pageNumber + 1}</a>`;
}

function updatePaginationText(currentPage, totalPages) {
    $('.pagination-text').text(`Pagination (${currentPage+1} of ${totalPages})`);
}

function showSkeletonLoader() {
    var loaderHTML = '';
    for (var i = 0; i < 9; i++) {
        loaderHTML += `
            <div class="card col-xs-12 col-sm-6 col-md-6 col-lg-4 mb-4" aria-hidden="true">
                <svg class="bd-placeholder-img card-img-top"
                     width="100%" height="20rem"
                     xmlns="http://www.w3.org/2000/svg"
                     role="img" aria-label="Placeholder"
                     preserveAspectRatio="xMidYMid slice"
                     focusable="false">
                    <title>Placeholder</title>
                    <rect width="100%" height="100%" fill="#868e96"></rect>
                </svg>
                <div class="card-body" style="  box-shadow: 1px 1px 4px 1px rgba(0, 0, 0, 0.05);">
                    <div class="h5 card-title placeholder-glow">
                        <span class="placeholder col-5"></span>
                    </div>
                    <p class="card-text placeholder-glow">
                        <span class="placeholder col-4"></span>
                        <span class="placeholder col-7"></span>
                    </p>
                    <div class="h5 card-title placeholder-glow">
                        <span class="placeholder col-8"></span>
                    </div>
                    <p class="card-text placeholder-glow">
                        <span class="placeholder col-3"></span>
                        <span class="placeholder col-3"></span>
                    </p>
                    <a href="#" tabindex="-1" class="btn btn-primary disabled placeholder py-2 px-3 col-5"></a>
                </div>
            </div>`;
    }
    $('#content').html(loaderHTML);
}


$(document).ready(function() {
    $('form').on('submit', function(e) {
        e.preventDefault();
        fetchPage(0);
    });
});