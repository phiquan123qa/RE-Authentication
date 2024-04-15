const currentUrl = window.location.href;
const url = new URL(currentUrl);
const searchParams = new URLSearchParams(url.search);
const statusValue = searchParams.get('status');
const sortValue = searchParams.get('sort');

function fetchPage(pageNumber) {
    scrollToElement('content-container');
    showSkeletonLoader();
    $.ajax({
        url: '/report/admin/re',
        type: 'GET',
        data: {
            offset: pageNumber,
            pageSize: 6,
            status: statusValue,
            sort: sortValue
        },
        success: function (response) {
            if (response.response.totalElements === 0) {
                pageNotFoundData();
            } else {
                updatePage(response);
            }
            updatePaginationText(pageNumber, response.response.totalPages);
            createPagination(response.response.totalPages, pageNumber);
            changeenabletext();
            console.log(response);
        },
        error: function (error) {
            console.log('Error fetching data: ', error);
        }
    });
}

function updatePage(data) {
    var content = $('#table-content');
    content.empty();
    data.response.content.forEach(function (item) {
        $('#table-content').append(
            `<tr>
              <th scope="row">${item.id}</th>
              <td>${item.realEstate.id}</td>
              <td>
                  <div class="d-flex align-items-center gap-2">
                    <span class="badge rounded-3 fw-semibold text-capitalize badgeStatus">${item.status === "Pending" ? 'Pending' : 'Complete'}</span>
                  </div>
              </td>
              <td>${item.type}</td>
              <td>${item.content}</td>
              <td>${item.dateCreate}</td>
              <td>${item.emailAuthor}</td>
              <td>${item.phoneAuthor}</td>
              <td>
                  <button class="btn btn-danger mt-3" onclick="changestatuscompletereportre(${item.id}, 'Complete')">
                      Complete
                  </button>
              </td>
            </tr>`
        );
    });
}

function pageNotFoundData() {
    var content = $('#content');
    content.empty();
    content.append('<div class="d-flex align-items-start justify-content-center">' +
        '<img loading="lazy" src="/static/images/not_found_data.png" alt="Image" class="img-fluid"/>' +
        '</div>');
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
            </tr>`;
    }
    $('#table-content').html(loaderHTML);
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
function changeenabletext(){
    $('.badgeStatus').each(function () {
        var currentStatus = $(this).text();
        if(currentStatus === 'Pending'){
            $(this).removeClass('bg-success bg-warning').addClass('bg-success');
        } else {
            $(this).removeClass('bg-success bg-warning').addClass('bg-warning');
        }
        var row = $(this).closest('tr');
        var disableButton = row.find('.btn.btn-danger');
        if (currentStatus === 'Complete') {
            disableButton.prop('disabled', true);
        } else {
            disableButton.prop('disabled', false);
        }
    });
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
fetchPage(0);