//json input location
var cities = document.getElementById("cityID");
var districts = document.getElementById("districtID");
var wards = document.getElementById("wardID");
const $modifyButton = $('#modifyButton');
var cityData = null;
var Parameter = {
    url: "https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json",
    method: "GET",
    responseType: "application/json",
};
var promise = axios(Parameter);
promise.then(function (result) {
    cityData = result.data;
    renderCity(result.data);
    fetchPage(0);
});

    $modifyButton.on('click', function() {
        if (cityData) {
            renderCity(cityData);
        }
    });

function renderCity(data) {
    for (const c of data) {
        let option1 = new Option(c.Name, c.Name);
        cities.options[cities.options.length] = option1;
        if (c.Name === $("#my_city").text()) {
            option1.selected = true;
        }
    }
    renderDistrict(data, $("#my_city").text(), 1);
    renderWard(data, $("#my_city").text(), $("#my_district").text(), 1)
    cities.onchange = function () {
        renderDistrict(data, this.value, 2)
    };
    districts.onchange = function () {
        renderWard(data, cities.value, this.value, 2);
    }
}

function renderDistrict(data, cityName, type) {
    districts.length = 1;
    wards.length = 1;
    if (cityName !== "") {
        const result = data.filter(n => n.Name === cityName);
        if (type === 2) {
            districts.options[0].selected = true;
        }
        for (const k of result[0].Districts) {
            let option2 = new Option(k.Name, k.Name);
            districts.options[districts.options.length] = option2;
            if (type === 1 && k.Name === $("#my_district").text()) {
                option2.selected = true;
            }
        }
    }
}

function renderWard(data, cityName, districtName, type) {
    wards.length = 1;
    const dataCity = data.filter(n => n.Name === cityName);
    if (districtName !== "") {
        const dataWards = dataCity[0].Districts.filter(n => n.Name === districtName)[0].Wards;
        if (type === 2) {
            wards.options[0].selected = true;
        }
        for (const w of dataWards) {
            let option3 = new Option(w.Name, w.Name);
            wards.options[wards.options.length] = option3;
            if (type === 1 && w.Name === $("#my_ward").text()) {
                option3.selected = true;
            }
        }
    }
}