function fetchCreate() {
    const imagesListValue = [];

    const typeValue = $('input[name="type"]:checked').val();
    const titleValue = $('#titleID').val();
    const cityValue = $('#cityID').find(":selected").val();
    const districtValue = $('#districtID').find(":selected").val();
    const wardValue = $('#wardID').find(":selected").val();
    const addressValue = $('#addressID').val();
    const landAreaValue = $('#landAreaID').val();
    const priceValue = $('#priceID').val();
    const legalDocumentValue = $('input[name="legalDocument"]:checked').val();
    const interiorValue = $('input[name="interior"]:checked').val();
    const descriptionValue = $('#descriptionID').val();
    const roomValue = $('#room').val();
    const bedValue = $('#bedRoom').val();
    const bathValue = $('#bathRoom').val();

    const files = $('.file-input').get(0).files;
    for (let i = 0; i < files.length; i++) {
        imagesListValue.push(files[i].name);
    }
    $.ajax({
        url: '/re/create',
        contentType: 'application/json',
        type: 'POST',
        data: JSON.stringify({
            type: typeValue,
            title: titleValue,
            cityRe: cityValue,
            districtRe: districtValue,
            wardRe: wardValue,
            address: addressValue,
            landArea: landAreaValue,
            price: priceValue,
            legalDocument: legalDocumentValue,
            interior: interiorValue,
            imagesList: imagesListValue,
            room: roomValue,
            bedRoom: bedValue,
            bathRoom: bathValue,
            description: descriptionValue
        }),
        success: function (response) {
            console.log(response);
        },
        error: function (error) {
            console.log('Error fetching data: ', error);
        }
    });
}

$('#btnSubmit').click(function () {
    fetchCreate();
});