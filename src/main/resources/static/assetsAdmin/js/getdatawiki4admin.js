const currentUrl = window.location.href;
const url = new URL(currentUrl);
const searchParams = new URLSearchParams(url.search);
const titleValue = searchParams.get('title');
const tagValue = searchParams.get('tag');
const sortValue = searchParams.get('sort');
const isPublishedValue = searchParams.get('isPublished');

function fetchPage(pageNumber) {
    scrollToElement('content');
    showSkeletonLoader();
    $.ajax({
        url: '/wiki/findall',
        type: 'GET',
        data: {
            offset: pageNumber,
            pageSize: 6,
            title: titleValue,
            tag: tagValue,
            sort: sortValue,
            isPublished: isPublishedValue
        },
        success: function (response) {
            if (response.response.totalElements === 0) {
                pageNotFoundData();
            } else {
                updatePage(response);
            }
            updatePaginationText(pageNumber, response.response.totalPages);
            createPagination(response.response.totalPages, pageNumber);
            changestatustext();
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
        $('#table-content').append(
            `<tr>
              <th scope="row">${item.id}</th>
              <td>${item.title}</td>
              <td>
                  <div class="d-flex align-items-center gap-2">
                    <span class="badge rounded-3 fw-semibold text-capitalize badgeType">${item.isPublished === true ? 'Published' : 'Unpublished'}</span>
                  </div>
              </td>
              <td>
                  <div class="d-flex align-items-center gap-2">
                    <span class="badge rounded-3 fw-semibold text-capitalize badgeStatus">${item.tag}</span>
                  </div>
              </td>
              <td>${item.date}</td>
              <td>${item.author.email}</td>
              <td class="d-flex align-items-center align-content-center">
                  <a href="/wiki/${item.id}" class="btn btn-secondary mt-3 me-2" target="_blank" >
                      Preview
                  </a>
                  <button class="btn mt-3" data-id="${item.id}" data-bs-toggle="modal" data-bs-target="#disableModal">
                      Disable
                  </button>
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
    if (currentPage < totalPages - 1) {
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
    for (let i = 0; i < 6; i++) {
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

function changestatustext() {
    $('.badgeStatus').each(function () {
        var currentStatus = $(this).text();
        if (currentStatus === 'Tips') {
            $(this).removeClass('bg-success bg-warning bg-danger').addClass('bg-primary');
        } else if (currentStatus === 'News') {
            $(this).removeClass('bg-success bg-warning bg-danger').addClass('bg-secondary');
        } else {
            $(this).removeClass('bg-success bg-warning bg-danger').addClass('bg-dark');
        }
        var row = $(this).closest('tr');
        var isPublishedTag = row.find('.badgeType');
        if (isPublishedTag.text() === 'Published') {
            isPublishedTag.removeClass('bg-success bg-danger').addClass('bg-success');
        } else {
            isPublishedTag.removeClass('bg-success bg-danger').addClass('bg-danger');
        }
        var changStatusBtn = row.find('button.btn');
        if (isPublishedTag.text() === 'Unpublished') {
            changStatusBtn.removeClass('btn-danger btn-success').addClass('btn-success').text('Enabled').attr('data-bs-target', '#enableModal');
        } else {
            changStatusBtn.removeClass('btn-danger btn-success').addClass('btn-danger').text('Disabled').attr('data-bs-target', '#disableModal');
        }
    });
}

$(document).on('click', '.btn-success', function () {
    var itemId = $(this).data('id');
    $('#confirmEnableBtn').data('id', itemId);
});
$('#confirmEnableBtn').click(function () {
    var itemId = $(this).data('id');
    console.log('Confirm Disable for item ID: ' + itemId);
    changespublishedwiki(itemId, true);
});
$(document).on('click', '.btn-danger', function () {
    var itemId = $(this).data('id');
    $('#confirmDisableBtn').data('id', itemId);
});
$('#confirmDisableBtn').click(function () {
    var itemId = $(this).data('id');
    console.log('Confirm Disable for item ID: ' + itemId);
    changespublishedwiki(itemId, false);
});
fetchPage(0);