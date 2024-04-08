function bindValues(slider, inputNumberMin, inputNumberMax) {
    slider.noUiSlider.on('update', function (values, handle) {
        var value = values[handle];
        if (handle) {
            inputNumberMax.value = Math.round(value);
        } else {
            inputNumberMin.value = Math.round(value);
        }
    });
    inputNumberMin.addEventListener('change', function () {
        slider.noUiSlider.set(this.value);
    });
    inputNumberMax.addEventListener('change', function () {
        slider.noUiSlider.set([null, this.value]);
    });

}

var priceSlider = $('.price-filter');
var priceMinInput = $('.price-filter-min-price');
var priceMaxInput = $('.price-filter-max-price');
for (var i = 0; i < priceSlider.length; i++) {
    noUiSlider.create(priceSlider[i], {
        start: [0, 1000000],
        connect: true,
        range: {
            'min': 0,
            'max': 1000000
        }
    });
    bindValues(priceSlider[i], priceMinInput[i], priceMaxInput[i]);
}

var landAreaSlider = $('.land-area-filter');
var landAreaMinInput = $('.land-area-filter-min');
var landAreaMaxInput = $('.land-area-filter-max');
for (var i = 0; i < landAreaSlider.length; i++) {
    noUiSlider.create(landAreaSlider[i], {
        start: [0, 500],
        connect: true,
        range: {
            'min': 0,
            'max': 500
        }
    });
    bindValues(landAreaSlider[i], landAreaMinInput[i], landAreaMaxInput[i]);
}
const currentUrll = window.location.href;
const urll = new URL(currentUrll);
const searchParamss = new URLSearchParams(urll.search);
const minAreaValuee = searchParamss.get('minArea');
const maxAreaValuee = searchParamss.get('maxArea');
const minPriceValuee = searchParamss.get('minPrice');
const maxPriceValuee = searchParamss.get('maxPrice');
if(minAreaValuee && maxAreaValuee){
    landAreaSlider[0].noUiSlider.set([minAreaValuee, maxAreaValuee]);
}
if (minPriceValuee && maxPriceValuee) {
    priceSlider[0].noUiSlider.set([minPriceValuee, maxPriceValuee]);
}

$('#qwe').click(function () {
    priceSlider[0].noUiSlider.set([0, 200]);
});