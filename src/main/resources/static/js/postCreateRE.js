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
function fetchCreate() {

    let filesToUpload = $('.file-input').prop('files');
    let formData = new FormData();
    $.each(filesToUpload, function(i, file) {
        formData.append('imagesList', file);
    });
    $.ajax(
        {
            url: '/re/upload-images',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function (response) {
                console.log(response);
                let imagesListValue = response.imageUrls;
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
            },
            error: function (error) {
                console.log('Error fetching data: ', error);
            }
        }
    )
}
function validateForm() {
    let isFormValid = true;
    $('.border-danger').removeClass('border-danger');

    if(titleValue.trim() === ''){
        isFormValid = false;
        $('#titleID').addClass('border-danger');
    }
    if (cityValue.trim() === '') {
        isFormValid = false;
        $('#cityID').addClass('border-danger');
    }
    if (districtValue.trim() === '') {
        isFormValid = false;
        $('#districtID').addClass('border-danger');
    }
    if (wardValue.trim() === '') {
        isFormValid = false;
        $('#wardID').addClass('border-danger');
    }
    if (addressValue.trim() === '') {
        isFormValid = false;
        $('#addressID').addClass('border-danger');
    }
    if (landAreaValue.trim() === '' || isNaN(parseFloat(landAreaValue)) || parseFloat(landAreaValue) <= 0) {
        isFormValid = false;
        $('#landAreaID').addClass('border-danger');
    }
    if (priceValue.trim() === '' || isNaN(parseFloat(priceValue)) || parseFloat(priceValue) <= 0) {
        isFormValid = false;
        $('#priceID').addClass('border-danger');
    }
    if (typeof legalDocumentValue === 'undefined' ||legalDocumentValue.trim() === '') {
        isFormValid = false;
        $('#legalDocumentID').addClass('border-danger');
    }
    if (typeof interiorValue === 'undefined' ||interiorValue.trim() === '') {
        isFormValid = false;
        $('#interiorID').addClass('border-danger');
    }
    if (descriptionValue.trim() === '') {
        isFormValid = false;
        $('#descriptionID').addClass('border-danger');
    }
    if (!isFormValid){
        alert('Form not valid. Please check the highlighted fields !');
    }
    return isFormValid;
}

$('#btnSubmit').click(function () {
    let isFormValid = validateForm();
    if (isFormValid) {
        fetchCreate();
    }
});