const userId = $('#userNameHolder').text();
const realEstateId = parseInt( $('#idReHolder').text());
const checkbox = document.getElementById("isFavorited");
const icon = document.getElementById("isFavoritedIcon");
function toggleFavorite() {
    if (checkbox.checked) {
        icon.classList.remove("icon-heart-o");
        icon.classList.add("icon-heart");
        console.log("Sending AJAX request:");
        console.log({
            type: 'POST',
            url: '/re/favorite/add',
            data: JSON.stringify({
                userName: userId,
                realEstateId: realEstateId
            }),
            contentType: 'application/json'
        });
        $.ajax({
            type: 'POST',
            url: '/re/favorite/add',
            data: JSON.stringify({
                userName: userId,
                realEstateId: realEstateId
            }),
            contentType: 'application/json',
            success: function (response) {
                console.log(response);
            },
            error: function (error) {
                console.log(error);
            }
        });
    } else {
        icon.classList.remove("icon-heart");
        icon.classList.add("icon-heart-o");
        $.ajax({
            type: 'POST',
            url: '/re/favorite/delete',
            data: JSON.stringify({
                userName: userId,
                realEstateId: realEstateId
            }),
            contentType: 'application/json',
            success: function (response) {
                console.log(response);
            },
            error: function (error) {
                console.log(error);
            }
        });
    }
}
document.addEventListener("DOMContentLoaded", function() {
    $.ajax({
        type: 'POST',
        url: '/re/favorite/check',
        data: JSON.stringify({
            userName: userId,
            realEstateId: realEstateId
        }),
        contentType: 'application/json',
        success: function (response) {
            if (response) {
                checkbox.checked = true;
                icon.classList.remove("icon-heart-o");
                icon.classList.add("icon-heart");
            }
        },
        error: function (error) {
            console.log(error);
        }
    })
});
