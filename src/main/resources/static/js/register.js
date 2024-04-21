$(function () {
    'use strict';
    // Add class for lable fly up
    $('.form-control').on('input', function () {
        var $field = $(this).closest('.form-group');
        if (this.value) {
            $field.addClass('field--not-empty');
        } else {
            $field.removeClass('field--not-empty');
        }
    });
    // Validate before submit
    $('#registerForm').on('submit', function (event) {
        event.preventDefault();
        const name = $('#name').val();
        const email = $('#email').val();
        const password = $('#password').val();
        const confirmPassword = $('#confirmPassword').val();
        const dob = $('#dob').val();
        const phoneNumber = $('#phoneNumber').val();
        const city = $('#cityID').val();
        const district = $('#districtID').val();
        const ward = $('#wardID').val();
        let valid = true;

        // Check if email and password are not empty
        if (!name || !email || !password || !confirmPassword || !dob || !phoneNumber || !city || !district || !ward) {
            saberToast.error({
                title: "Register Failed",
                text: "All fields cannot be empty",
                delay: 200,
                duration: 2600,
                rtl: true,
                position: "top-right"
            })
            valid = false; // Mark form as invalid
        }
        // Validate email with regex
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            saberToast.warn({
                title: "Register Failed",
                text: "Email invalidate",
                delay: 200,
                duration: 2600,
                rtl: true,
                position: "top-right"
            })
            valid = false; // Mark form as invalid
        }

        // Check if the password is not too short
        const minPasswordLength = 6; // You can change this to whatever minimum length you want
        if (password.length < minPasswordLength) {
            saberToast.warn({
                title: "Login Failed",
                text: "Password must be at least " + minPasswordLength + " characters long",
                delay: 200,
                duration: 2600,
                rtl: true,
                position: "top-right"
            })
            valid = false; // Mark form as invalid
        }

        if (password !== confirmPassword) {
            saberToast.warn({
                title: "Register Failed",
                text: "Password and confirm password not match",
                delay: 200,
                duration: 2600,
                rtl: true,
                position: "top-right"
            })
            valid = false; // Mark form as invalid
        }
        const regexPhoneNumber = /(84|0[3|5|7|8|9])+([0-9]{8})\b/g;
        if (!regexPhoneNumber.test(phoneNumber)) {
            saberToast.warn({
                title: "Register Failed",
                text: "Phone number invalidate",
                delay: 200,
                duration: 2600,
                rtl: true,
                position: "top-right"
            })
            valid = false; // Mark form as invalid
        }

        // If all checks pass, submit the form
        if (valid) {
            saberToast.success({
                title: "We are checking your email",
                text: "Please wait for a while for verification",
                delay: 200,
                duration: 2600,
                rtl: true,
                position: "top-right"
            })
            setTimeout(() => {
                this.submit();
            }, 3000);

        }
    });



});