const currentUrl = window.location.href;
const url = new URL(currentUrl);
const searchParams = new URLSearchParams(url.search);
const titleValue = searchParams.get('title');
const typeValue = searchParams.get('type');
const cityValue = searchParams.get('city');
const districtValue = searchParams.get('district');
const wardValue = searchParams.get('ward');
const sortValue = searchParams.get('sort');
const statusValue = searchParams.get('status');
const minAreaValue = searchParams.get('minArea');
const maxAreaValue = searchParams.get('maxArea');
const minPriceValue = searchParams.get('minPrice');
const maxPriceValue = searchParams.get('maxPrice');

function allParamsAreEmpty() {
    return !typeValue && !cityValue && !districtValue && !wardValue && !sortValue && !statusValue;
}

function fetchPage(pageNumber) {
    scrollToElement('content-container');
    showSkeletonLoader();
    $.ajax({
        url: '/re/admin/findallre',
        type: 'GET',
        data: {
            offset: pageNumber,
            pageSize: 6,
            title: titleValue,
            typeRe: typeValue,
            city: cityValue,
            district: districtValue,
            ward: wardValue,
            sort: sortValue,
            status: statusValue,
            minArea: minAreaValue,
            maxArea: maxAreaValue,
            minPrice: minPriceValue,
            maxPrice: maxPriceValue,
        },
        success: function (response) {
            if (response.response.totalElements === 0) {
                pageNotFoundData();
            } else {
                updatePage(response);
            }
            updatePaginationText(pageNumber, response.response.totalPages);
            createPagination(response.response.totalPages, pageNumber);
            console.log(response);
        },
        error: function (error) {
            console.log('Error fetching data: ', error);
        }
    });
}

function pageNotFoundData() {
    var content = $('#content');
    content.empty();
    content.append('<div class="d-flex align-items-start justify-content-center">' +
        '<img loading="lazy" src="/static/images/not_found_data.png" alt="Image" class="img-fluid"/>' +
        '</div>');
}

function updatePage(data) {
    var content = $('#table-content');
    content.empty();
    data.response.content.forEach(function (item) {
        var typeBg = item.type === 'sell' ? 'bg-secondary' : 'bg-success';
        var statusBg;
        if(item.statusRe === 'INACTIVE'){
            statusBg = 'bg-warning';
        }else if (item.statusRe === 'ACTIVE'){
            statusBg = 'bg-success';
        }else{
            statusBg = 'bg-danger';
        }
        $('#table-content').append(
            `<tr>
              <th scope="row">${item.id}</th>
              <td>${item.title}</td>
              <td>
                  <div class="d-flex align-items-center gap-2">
                    <span class="badge ${typeBg} rounded-3 fw-semibold text-capitalize">${item.type}</span>
                  </div>
              </td>
              <td>
                  <div class="d-flex align-items-center gap-2">
                    <span class="badge ${statusBg} rounded-3 fw-semibold text-capitalize">${item.statusRe}</span>
                  </div>
              </td>
              <td>$${item.price}</td>
              <td>${item.landArea} m<sup>2</sup></td>
              <td>${item.cityRe}</td>
              <td>${item.districtRe}</td>
              <td>${item.wardRe}</td>
              <td>${item.user.email}</td>
              <td>
                  <a href="/property/${item.id}" class="btn btn-secondary" target="_blank">
                      View Live
                  </a>
              </td>
            </tr>`
        );
    });
}

function createPagination(totalPages, currentPage) {
    const paginationObject = $('#pagination');
    const range = 2;
    paginationObject.empty();
    if (currentPage === 1) {
        paginationObject.append('<li class="page-item">\n' +
            '                            <a class="page-link" onclick="fetchPage(${pageNumber - 1})" aria-label="Previous">\n' +
            '                                <span aria-hidden="true">&laquo;</span>\n' +
            '                            </a>\n' +
            '                        </li>');
    }
    paginationObject.append(createPageItem(0, currentPage));
    if (currentPage - range > 1) {
        paginationObject.append('<a class="pagination-ellipsis">&hellip;</a>');
    }
    for (let i = Math.max(1, currentPage - range); i < Math.min(totalPages - 1, currentPage + range + 1); i++) {
        paginationObject.append(createPageItem(i, currentPage));
    }
    if (currentPage + range < totalPages - 2) {
        paginationObject.append('<a class="pagination-ellipsis">&hellip;</a>');
    }
    if (totalPages > 1) {
        paginationObject.append(createPageItem(totalPages - 1, currentPage));
    }
    if(currentPage < totalPages - 1) {
        paginationObject.append('<li class="page-item">\n' +
            '                            <a class="page-link" onclick="fetchPage(${pageNumber + 1})" aria-label="Previous">\n' +
            '                                <span aria-hidden="true">&raquo;</span>\n' +
            '                            </a>\n' +
            '                        </li>');
    }
}

function createPageItem(pageNumber, currentPage) {
    let activeClass = pageNumber === currentPage ? 'active' : '';
    return `<li class="page-item ${activeClass}"><a class="page-link" onclick="fetchPage(${pageNumber})">${pageNumber + 1}</a></li>`;
}

function updatePaginationText(currentPage, totalPages) {
    $('.pagination-text').text(`Pagination (${currentPage + 1} of ${totalPages})`);
}

function showSkeletonLoader() {
    var loaderHTML = '';
    for (var i = 0; i < 6; i++) {
        loaderHTML += `
            <tr>
              <td class="h5 card-title placeholder-glow">
                <span class="placeholder col-12"></span>
              </td>
              <td class="h5 card-title placeholder-glow">
                <span class="placeholder col-12"></span>
              </td>
              <td class="h5 card-title placeholder-glow">
                <span class="placeholder col-12"></span>
              </td>
              <td class="h5 card-title placeholder-glow">
                <span class="placeholder col-12"></span>
              </td>
              <td class="h5 card-title placeholder-glow">
                <span class="placeholder col-12"></span>
              </td>
              <td class="h5 card-title placeholder-glow">
                <span class="placeholder col-12"></span>
              </td>
              <td class="h5 card-title placeholder-glow">
                <span class="placeholder col-12"></span>
              </td>
              <td class="h5 card-title placeholder-glow">
                <span class="placeholder col-12"></span>
              </td>
              <td class="h5 card-title placeholder-glow">
                <span class="placeholder col-12"></span>
              </td>
              <td class="h5 card-title placeholder-glow">
                <span class="placeholder col-12"></span>
              </td>
              <td class="h5 card-title placeholder-glow">
                <span class="placeholder col-12"></span>
              </td>
            </tr>`;
    }
    $('#table-content').html(loaderHTML);
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

$('#clear-button').on('click', function () {
    $('#titleInput').val('');
    $('#cityID, #districtID, #wardID, #type, #sort').val('');
    priceSlider[0].noUiSlider.set([0, 1000000]);
    landAreaSlider[0].noUiSlider.set([0, 500]);
});

fetchPage(0);