$(function() {
	'use strict';
	
  $('.form-control').on('input', function() {
	  var $field = $(this).closest('.form-group');
	  if (this.value) {
	    $field.addClass('field--not-empty');
	  } else {
	    $field.removeClass('field--not-empty');
	  }
	});
	$('#loginForm').on('submit', function(event) {
		event.preventDefault(); // Prevent the default form submit action

		// Fetch values from the form
		var email = $('#username').val();
		var password = $('#password').val();
		var valid = true; // Flag to track overall form validity

		// Check if email and password are not empty
		if (!email || !password) {
			saberToast.warn({
				title: "Login Failed",
				text: "Email and password are required!",
				delay: 200,
				duration: 2600,
				rtl: true,
				position: "top-right"
			})
			valid = false; // Mark form as invalid
		}

		// Validate email with regex
		var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		if (!emailRegex.test(email)) {
			saberToast.warn({
				title: "Login Failed",
				text: "Email invalidate",
				delay: 200,
				duration: 2600,
				rtl: true,
				position: "top-right"
			})
			valid = false; // Mark form as invalid
		}

		// Check if the password is not too short
		var minPasswordLength = 6; // You can change this to whatever minimum length you want
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

		// If all checks pass, submit the form
		if (valid) {
			saberToast.success({
				title: "Login Checking...",
				text: "We are checking your account...",
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