const currentUrl = window.location.href;
const url = new URL(currentUrl);
const searchParams = new URLSearchParams(url.search);
const titleValue = searchParams.get('title');
const cityValue = searchParams.get('city');
const districtValue = searchParams.get('district');
const wardValue = searchParams.get('ward');
const sortValue = searchParams.get('sort');
function fetchReOfUser() {
    $.ajax({
        url: '/re/listRe',
        type: 'GET',
        data: {
            title: titleValue,
            city: cityValue,
            district: districtValue,
            ward: wardValue,
            sortByDate: sortValue
        },
        success: function (response) {
            console.log(response);
        },
        error: function (error) {
            console.log('Error fetching data: ', error);
        }
    })

}
fetchReOfUser();