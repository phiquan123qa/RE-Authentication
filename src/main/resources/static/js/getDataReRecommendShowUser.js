function showRecommendRe() {
    showSkeleton();
    $.ajax({
        url: '/re/findrerecommenddataforuser',
        type: 'GET',
        success: function (response) {
            console.log(response);
            updatePage(response);
        },
        error: function (error) {
            console.log('Error fetching data: ', error);
        }
    });
}

function showSkeleton() {
    const content = $('#property-slider');
    content.empty();
    for (let i = 0; i < 3; i++) {
        $('#property-slider').append(`
            <div class="property-item tns-item card col-xs-12 col-sm-6 col-md-6 col-lg-4 mb-4" aria-hidden="true">
                <svg class="bd-placeholder-img card-img-top"
                     width="100%" height="20rem"
                     xmlns="http://www.w3.org/2000/svg"
                     role="img" aria-label="Placeholder"
                     preserveAspectRatio="xMidYMid slice"
                     focusable="false" style="position: static !important;">
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
            </div>`);
    }
}
function updatePage(data) {
    const content = $('#property-slider');
    content.empty();
    data.response.forEach(function(item) {
        $('#property-slider').append(
            `<div class="property-item">
                    <a href="/property/${item.id}" class="img d-flex justify-content-center">
                        <img loading="lazy" src="/static/images/${'real_estate_images/' + item.mainImage || 'img_1.jpg'}" alt="Image" class="img-fluid" style="height: 23rem"/>
                    </a>
                    <div class="property-content" style="border: 1px solid #8080803b">
                        <div class="price mb-2">
                            <span>${item.price != null ? formatPrice(item.price) : 'Negotiated price'}</span>
                        </div>
                        <div>
                            <span class="d-block mb-2 text-black-50">
                                ${item.wardRe}, ${item.districtRe}, ${item.cityRe}
                            </span>
                            <a href="/property/${item.id}" class="city d-block mb-3">
                                ${item.title.length > 28 ? item.title.substring(0,28) + '...' : item.title}
                            </a>
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
                            <a href="/property/${item.id}"
                               class="btn btn-primary py-2 px-3">
                               See details
                            </a>
                        </div>
                    </div>
            </div>
        `
        );
    });
    var tinySdlierr = function () {
        var propertySlider = document.querySelectorAll('.property-slider');
        if (propertySlider.length > 0) {
            var tnsSlider = tns({
                container: '.property-slider',
                mode: 'carousel',
                speed: 700,
                gutter: 30,
                items: 3,
                autoplay: true,
                autoplayButtonOutput: false,
                controlsContainer: '#property-nav',
                responsive: {
                    0: {
                        items: 1
                    },
                    700: {
                        items: 2
                    },
                    900: {
                        items: 3
                    }
                }
            });
        }
    }
    tinySdlierr();
    $('#property-nav').show();
}
function formatPrice(price) {
    if (price != null) {
        return '$' + price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    } else {
        return 'Negotiated price';
    }
}
showRecommendRe();