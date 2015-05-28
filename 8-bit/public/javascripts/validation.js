/* Validation constraints */
var PSEUDO_MINLENGTH = 6;
var EMAIL_PATTERN = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
var PASSWORD_MINLENGTH = 8;

/* Tooltips Content */
var PSEUDO_TAKEN = "Pseudo déjà pris";
var PSEUDO_TOOLTIP = "6 caractères minimum";
var PASSWORD_TOOLTIP = "8 caractères minimum";
var INVALID_EMAIL = "Email non valide !";
var INVALID_PASSWORD2 = "Hmm...";

/* Class names */
var INFO_TOOLTIP = "info-tooltip";
var ERROR_TOOLTIP = "error-tooltip";
var VALID_INPUT = "valid-input";
var INVALID_INPUT = "invalid-input";

/**
 * Validates the sign-up form and displays tooltips
 */
$(function validate() {
    var pseudoInput = $("#pseudo");
    var emailInput = $("#email");
    var passwordInput = $("#password");
    var password2Input = $("#password2");

    /* Initialize tooltips */
    pseudoInput.tooltip();
    emailInput.tooltip();
    passwordInput.tooltip();
    password2Input.tooltip();

    /* Apply tooltips only on focus */
    pseudoInput.tooltip().off("mouseover mouseout");
    emailInput.tooltip().off("mouseover mouseout");
    passwordInput.tooltip().off("mouseover mouseout");
    password2Input.tooltip().off("mouseover mouseout");

    /* Initial content and tooltip class */
    // -- Pseudo input
    if (pseudoInput.attr("title").length == 0) {
        // Info tooltip
        pseudoInput.attr("title", PSEUDO_TOOLTIP);
        pseudoInput.tooltip("option", "tooltipClass", INFO_TOOLTIP);
    } else {
        // There's an error tooltip to be shown
        pseudoInput.tooltip("option", "tooltipClass", ERROR_TOOLTIP);
        pseudoInput.addClass(INVALID_INPUT);
    }
    // -- Email input (No info tooltip, only error tooltip)
    if (emailInput.attr("title").length != 0) {
        emailInput.tooltip("option", "tooltipClass", ERROR_TOOLTIP);
        emailInput.addClass(INVALID_INPUT);
    }
    // -- Password input
    if (passwordInput.attr("title").length == 0) {
        passwordInput.attr("title", PASSWORD_TOOLTIP);
        passwordInput.tooltip("option", "tooltipClass", INFO_TOOLTIP);
    } else {
        passwordInput.tooltip("option", "tooltipClass", ERROR_TOOLTIP);
        passwordInput.addClass(INVALID_INPUT);
    }
    // -- Second password input (No info tooltip, only error tooltip)
    if (password2Input.attr("title").length != 0) {
        password2Input.tooltip("option", "tooltipClass", ERROR_TOOLTIP);
        password2Input.addClass(INVALID_INPUT);
    }

    /* Set position */
    pseudoInput.tooltip("option", "position", {my: "right-2 center", at: "right center"});
    emailInput.tooltip("option", "position", {my: "right-2 center", at: "right center"});
    passwordInput.tooltip("option", "position", {my: "right-2 center", at: "right center"});
    password2Input.tooltip("option", "position", {my: "right-2 center", at: "right center"});

    /* Validate form */
    // -- Pseudo input
    // Initial value
    if (pseudoInput.val().length > 0) {
        if (pseudoInput.val().length < PSEUDO_MINLENGTH) {
            pseudoInput.addClass(INVALID_INPUT);
            pseudoInput.attr("title", PSEUDO_TOOLTIP);
            pseudoInput.tooltip("option", "tooltipClass", ERROR_TOOLTIP);
        } else {
            // Pseudo taken?
            $.get("/verifyPseudo/" + pseudoInput.val(),
                function (response) {
                    if (response == "true") {
                        pseudoInput.addClass(VALID_INPUT);
                        pseudoInput.attr("title", PSEUDO_TOOLTIP);
                        pseudoInput.tooltip("option", "tooltipClass", INFO_TOOLTIP);
                    } else {
                        pseudoInput.addClass(INVALID_INPUT);
                        pseudoInput.attr("title", PSEUDO_TAKEN);
                        pseudoInput.tooltip("option", "tooltipClass", ERROR_TOOLTIP);
                    }
                }
            );
        }
    }
    // On blur event
    pseudoInput.blur(function () {
        pseudoInput.removeClass(VALID_INPUT);
        pseudoInput.removeClass(INVALID_INPUT);
        if (pseudoInput.val().length < PSEUDO_MINLENGTH) {
            pseudoInput.attr("title", PSEUDO_TOOLTIP);
            pseudoInput.tooltip("option", "tooltipClass", ERROR_TOOLTIP);
            pseudoInput.addClass(INVALID_INPUT);
        } else {
            // Pseudo taken?
           $.get("/verifyPseudo/" + pseudoInput.val(),
                function (response) {
                    if (response == "true") {
                        pseudoInput.attr("title", PSEUDO_TOOLTIP);
                        pseudoInput.tooltip("option", "tooltipClass", INFO_TOOLTIP);
                        pseudoInput.addClass(VALID_INPUT);
                    } else {
                        pseudoInput.attr("title", PSEUDO_TAKEN);
                        pseudoInput.tooltip("option", "tooltipClass", ERROR_TOOLTIP);
                        pseudoInput.addClass(INVALID_INPUT);
                    }
                }
            );
        }
    });
    // -- Email input
    // Initial value
    if (emailInput.val().length > 0) {
        if (!emailInput.val().match(EMAIL_PATTERN)) {
            emailInput.attr("title", INVALID_EMAIL);
            emailInput.tooltip("option", "tooltipClass", ERROR_TOOLTIP);
            emailInput.addClass(INVALID_INPUT);
        } else {
            emailInput.attr("title", "");
            emailInput.addClass(VALID_INPUT);
        }
    }
    emailInput.blur(function () {
        emailInput.removeClass(VALID_INPUT);
        emailInput.removeClass(INVALID_INPUT);
        if (!emailInput.val().match(EMAIL_PATTERN)) {
            emailInput.attr("title", INVALID_EMAIL);
            emailInput.tooltip("option", "tooltipClass", ERROR_TOOLTIP);
            emailInput.addClass(INVALID_INPUT);
        } else {
            emailInput.attr("title", "");
            emailInput.addClass(VALID_INPUT);
        }
    });
    // -- Password input
    // Initial value
    if (passwordInput.val().length > 0) {
        if (passwordInput.val().length < PASSWORD_MINLENGTH) {
            passwordInput.attr("title", PASSWORD_TOOLTIP);
            passwordInput.tooltip("option", "tooltipClass", ERROR_TOOLTIP);
            passwordInput.addClass(INVALID_INPUT);
        } else {
            passwordInput.attr("title", PASSWORD_TOOLTIP);
            passwordInput.tooltip("option", "tooltipClass", INFO_TOOLTIP);
            passwordInput.addClass(VALID_INPUT);
        }
    }
    passwordInput.blur(function() {
        passwordInput.removeClass(VALID_INPUT);
        passwordInput.removeClass(INVALID_INPUT);
        if (passwordInput.val().length < PASSWORD_MINLENGTH) {
            passwordInput.attr("title", PASSWORD_TOOLTIP);
            passwordInput.tooltip("option", "tooltipClass", ERROR_TOOLTIP);
            passwordInput.addClass(INVALID_INPUT);
        } else {
            passwordInput.attr("title", PASSWORD_TOOLTIP);
            passwordInput.tooltip("option", "tooltipClass", INFO_TOOLTIP);
            passwordInput.addClass(VALID_INPUT);
        }
        password2Input.removeClass(VALID_INPUT);
        password2Input.removeClass(INVALID_INPUT);
        if (password2Input.val().length > 0) {
            if (password2Input.val().length < PASSWORD_MINLENGTH || password2Input.val() != passwordInput.val()) {
                password2Input.attr("title", INVALID_PASSWORD2);
                password2Input.tooltip("option", "tooltipClass", ERROR_TOOLTIP);
                password2Input.addClass(INVALID_INPUT);
            } else {
                password2Input.attr("title", "");
                password2Input.addClass(VALID_INPUT);
            }
        }
    });
    // -- Second password input
    password2Input.blur(function() {
        password2Input.removeClass(VALID_INPUT);
        password2Input.removeClass(INVALID_INPUT);
        if (password2Input.val().length < PASSWORD_MINLENGTH || password2Input.val() != passwordInput.val()) {
            password2Input.attr("title", INVALID_PASSWORD2);
            password2Input.tooltip("option", "tooltipClass", ERROR_TOOLTIP);
            password2Input.addClass(INVALID_INPUT);
        } else {
            password2Input.attr("title", "");
            password2Input.addClass(VALID_INPUT);
        }
    });
});