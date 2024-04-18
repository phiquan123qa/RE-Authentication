const currentUrl = window.location.href;
const url = new URL(currentUrl);
const searchParams = new URLSearchParams(url.search);
const titleValue = searchParams.get('title');
const tagValue = searchParams.get('tag');
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
            tag: tagValue,
            sort: sortValue,
            isPublished: true
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
    function pageNotFoundData() {
        var content = $('#content');
        content.empty();
        content.append('<div class="d-flex align-items-start justify-content-center">' +
            '<img loading="lazy" src="/static/images/not_found_data.png" alt="Image" class="img-fluid"/>' +
            '</div>');
    }
    function updatePage(data) {
        var content = $('#content');
        content.empty();
        data.response.content.forEach(function (item) {
            var typeBg = item.isPublished === 'True' ? 'bg-success' : 'bg-warning';
            $('#content').append(
                `<div class="card mb-3 p-3" style=" border: 1px solid #ced4da; max-height: 15rem;">
                  <div class="card-body">
                    <h5 class="card-title">${item.title}</h5>
                    <h6 class="card-subtitle mb-2 text-muted">${item.tag}</h6>
                    <p class="card-text mb-4">Author: ${item.author.email}</p>
                    <a href="/wiki/${item.id}" class="card-link border px-2 py-3">View Details</a>
                  </div>
                </div>`
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
            loaderHTML += '<div class="row px-4 py-4" style="box-shadow: rgba(0, 0, 0, 0.15) 0 1px 4px;">\n' +
                '                    <div class="card mb-3" aria-hidden="true">\n' +
                '                        <div class="row no-gutters">\n' +
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
        $('#content').html(loaderHTML);
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
}
fetchPage(0);