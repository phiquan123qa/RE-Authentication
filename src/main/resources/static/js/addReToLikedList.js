const userId = $('#userNameHolder').text();
const realEstateId = parseInt($('#idReHolder').text());
const checkbox = document.getElementById("isFavorited");
const icon = document.getElementById("isFavoritedIcon");

function toggleFavorite() {
    if (userId == null || userId === "") {
        saberToast.warn({
            title: "Add to favorite fail",
            text: " You need to login first!",
            delay: 200,
            duration: 2600,
            rtl: true,
            position: "top-right"
        });
    } else {
        if (checkbox.checked) {
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
                    saberToast.success({
                        title: "Add to favorite success",
                        text: "This property has been added to your favorite",
                        delay: 200,
                        duration: 2600,
                        rtl: true,
                        position: "top-right"
                    });
                    icon.classList.remove("icon-heart-o");
                    icon.classList.add("icon-heart");
                },
                error: function (error) {
                    console.log(error);
                    saberToast.error({
                        title: "Add to favorite fail",
                        text: "Add to favorite fail",
                        delay: 200,
                        duration: 2600,
                        rtl: true,
                        position: "top-right"
                    });
                }
            });
        } else {
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
                    saberToast.success({
                        title: "Remove to favorite success",
                        text: "This property has been removed from your favorite",
                        delay: 200,
                        duration: 2600,
                        rtl: true,
                        position: "top-right"
                    });
                    icon.classList.remove("icon-heart");
                    icon.classList.add("icon-heart-o");
                },
                error: function (error) {
                    console.log(error);
                    saberToast.error({
                        title: "Add to favorite fail",
                        text: "You need to login first",
                        delay: 200,
                        duration: 2600,
                        rtl: true,
                        position: "top-right"
                    });
                }
            });
        }
    }
}

document.addEventListener("DOMContentLoaded", function () {
    if (userId && realEstateId) {
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
    }
});
