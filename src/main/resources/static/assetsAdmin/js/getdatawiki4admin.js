const currentUrl = window.location.href;
const url = new URL(currentUrl);
const searchParams = new URLSearchParams(url.search);
const titleValue = searchParams.get('title');
const statusValue = searchParams.get('status');
const sortValue = searchParams.get('sort');

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
            changestatustext();
            console.log(response);
        },
        error: function (error) {
            console.log('Error fetching data: ', error);
        }
    });
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
            var typeBg = item.isPublished === 'True' ? 'bg-success' : 'bg-warning';
            $('#table-content').append(
                `<tr>
              <th scope="row">${item.id}</th>
              <td>${item.title}</td>
              <td>
                  <div class="d-flex align-items-center gap-2">
                    <span class="badge ${typeBg} rounded-3 fw-semibold text-capitalize badgeType">${item.isPublished}</span>
                  </div>
              </td>
              <td>
                  <div class="d-flex align-items-center gap-2">
                    <span class="badge rounded-3 fw-semibold text-capitalize badgeStatus">${item.tag}</span>
                  </div>
              </td>
              <td>${item.date}</td>
              <td>${item.author.email}</td>
              <td>
                  <button class="btn btn-danger mt-3" data-id="${item.id}" data-bs-toggle="modal" data-bs-target="#disableModal">
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
    function changestatustext(){
        $('.badgeStatus').each(function () {
            var currentStatus = $(this).text();
            if(currentStatus === 'Tips'){
                $(this).removeClass('bg-success bg-warning bg-danger').addClass('bg-success');
            } else if(currentStatus === 'News'){
                $(this).removeClass('bg-success bg-warning bg-danger').addClass('bg-warning');
            } else {
                $(this).removeClass('bg-success bg-warning bg-danger').addClass('bg-danger');
            }
            var row = $(this).closest('tr');
            var disableButton = row.find('.btn.btn-danger');
            if (currentStatus === 'DISABLED') {
                disableButton.prop('disabled', true);
            } else {
                disableButton.prop('disabled', false);
            }
        });
    }
}
fetchPage(0);