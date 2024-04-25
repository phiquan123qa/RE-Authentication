function fetchCreate() {
    let typeValue = $('input[name="type"]:checked').val();
    let titleValue = $('#titleID').val();
    let cityValue = $('#cityID').find(":selected").val();
    let districtValue = $('#districtID').find(":selected").val();
    let wardValue = $('#wardID').find(":selected").val();
    let addressValue = $('#addressID').val();
    let landAreaValue = $('#landAreaID').val();
    let priceValue = $('#priceID').val();
    let legalDocumentValue = $('input[name="legalDocument"]:checked').val();
    let interiorValue = $('input[name="interior"]:checked').val();
    let descriptionValue = $('#descriptionID').val();
    let roomValue = $('#room').val();
    let bedValue = $('#bedRoom').val();
    let bathValue = $('#bathRoom').val();
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
                let mainImageValue = imagesListValue.length > 0 ? imagesListValue[0] : null;
                $.ajax({
                    url: '/re/create',
                    contentType: 'application/json',
                    type: 'POST',
                    data: JSON.stringify({
                        type: typeValue,
                        title: titleValue,
                        mainImage: mainImageValue,
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
                        saberToast.success({
                            title: "Create RE Success",
                            text: "Create Real Estate Successfully",
                            delay: 200,
                            duration: 2600,
                            rtl: true,
                            position: "top-right"
                        });
                        $('#btnClear').click();
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
    let titleValue = $('#titleID').val();
    let cityValue = $('#cityID').find(":selected").val();
    let districtValue = $('#districtID').find(":selected").val();
    let wardValue = $('#wardID').find(":selected").val();
    let addressValue = $('#addressID').val();
    let landAreaValue = $('#landAreaID').val();
    let priceValue = $('#priceID').val();
    let legalDocumentValue = $('input[name="legalDocument"]:checked').val();
    let interiorValue = $('input[name="interior"]:checked').val();
    let descriptionValue = $('#descriptionID').val();

    let isFormValid = true;
    $('.border-danger').removeClass('border-danger');

    if(titleValue.trim() === ''){
        isFormValid = false;
        saberToast.warn({
            title: "Title cannot be empty",
            text: "Please enter a title",
            delay: 200,
            duration: 2600,
            rtl: true,
            position: "top-right"
        });
        $('#titleID').addClass('border-danger');
    }
    if (cityValue.trim() === '') {
        isFormValid = false;
        saberToast.warn({
            title: "City cannot be empty",
            text: "Please enter a city",
            delay: 200,
            duration: 2600,
            rtl: true,
            position: "top-right"
        });
        $('#cityID').addClass('border-danger');
    }
    if (districtValue.trim() === '') {
        isFormValid = false;
        saberToast.warn({
            title: "District cannot be empty",
            text: "Please enter a district",
            delay: 200,
            duration: 2600,
            rtl: true,
            position: "top-right"
        });
        $('#districtID').addClass('border-danger');
    }
    if (wardValue.trim() === '') {
        isFormValid = false;
        saberToast.warn({
            title: "Ward cannot be empty",
            text: "Please enter a ward",
            delay: 200,
            duration: 2600,
            rtl: true,
            position: "top-right"
        });
        $('#wardID').addClass('border-danger');
    }
    if (addressValue.trim() === '') {
        isFormValid = false;
        saberToast.warn({
            title: "Address cannot be empty",
            text: "Please enter a address",
            delay: 200,
            duration: 2600,
            rtl: true,
            position: "top-right"
        });
        $('#addressID').addClass('border-danger');
    }
    if (landAreaValue.trim() === '' || isNaN(parseFloat(landAreaValue)) || parseFloat(landAreaValue) <= 0) {
        isFormValid = false;
        saberToast.warn({
            title: "Land area cannot be empty",
            text: "Please enter a land area",
            delay: 200,
            duration: 2600,
            rtl: true,
            position: "top-right"
        });
        $('#landAreaID').addClass('border-danger');
    }
    if (typeof legalDocumentValue === 'undefined' ||legalDocumentValue.trim() === '') {
        isFormValid = false;
        saberToast.warn({
            title: "Legal document cannot be empty",
            text: "Please enter a legal document",
            delay: 200,
            duration: 2600,
            rtl: true,
            position: "top-right"
        });
        $('#legalDocumentID').addClass('border-danger');
    }
    if (typeof interiorValue === 'undefined' ||interiorValue.trim() === '') {
        isFormValid = false;
        saberToast.warn({
            title: "Interior cannot be empty",
            text: "Please enter a interior",
            delay: 200,
            duration: 2600,
            rtl: true,
            position: "top-right"
        });
        $('#interiorID').addClass('border-danger');
    }
    if (descriptionValue.trim() === '') {
        isFormValid = false;
        saberToast.warn({
            title: "Description cannot be empty",
            text: "Please enter a description",
            delay: 200,
            duration: 2600,
            rtl: true,
            position: "top-right"
        });
        $('#descriptionID').addClass('border-danger');
    }
    if (!isFormValid){
        alert('Form not valid. Please check the highlighted fields !');
    }
    return isFormValid;
}
$('#btnClear').on('click', function () {
    $('#descriptionID').val('');
    $('#descriptionID').html('');
});
function updatePriceLabel(text) {
    $('#priceLabel').html(text + '<div class="of-star">&nbsp;*</div>');
}

// Check the initial value of the radio buttons on page load
if ($('#type1').is(':checked')) {
    updatePriceLabel("Price of RE (Total price/ real estate)");
} else if ($('#type2').is(':checked')) {
    updatePriceLabel("Price of RE (Price/ month)");
}

// Update the label text when the radio buttons are changed
$('input[name="type"]').on('change', function() {
    if ($('#type1').is(':checked')) {
        updatePriceLabel("Price of RE (Total price/ real estate)");
    } else if ($('#type2').is(':checked')) {
        updatePriceLabel("Price of RE (Price/ month)");
    }
});
$(document).ready(function() {
    $('#btnSubmit').click(function () {
        let isFormValid = validateForm();
        if (isFormValid) {
            fetchCreate();
        }
    });
});