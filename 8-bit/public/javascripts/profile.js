/* Validation constraints */
var EMAIL_PATTERN = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
var PASSWORD_MINLENGTH = 8;

/* Tooltips Content */
var INVALID_EMAIL = "Email non valide !";
var PASSWORD_TOOLTIP = "8 caractères minimum";
var INVALID_PASSWORD2 = "Hmm...";

/* Class names */
var INFO_TOOLTIP = "info-tooltip";
var ERROR_TOOLTIP = "error-tooltip";
var VALID_INPUT = "valid-input";
var INVALID_INPUT = "invalid-input";

/*
 * Validate input value in password/mail form
 */
$(function validate() {

    var emailInput = $("#email");
    var passwordInput = $("#password");
    var password2Input = $("#password2");

    /* Initialize tooltips */
    emailInput.tooltip();
    passwordInput.tooltip();
    password2Input.tooltip();

    /* Apply tooltips only on focus */
    emailInput.tooltip().off("mouseover mouseout");
    passwordInput.tooltip().off("mouseover mouseout");
    password2Input.tooltip().off("mouseover mouseout");

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
    emailInput.tooltip("option", "position", {my: "right-2 center", at: "right center"});
    passwordInput.tooltip("option", "position", {my: "right-2 center", at: "right center"});
    password2Input.tooltip("option", "position", {my: "right-2 center", at: "right center"});

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

/*
 * Show form ('change Password' or 'change Email') when click on it
 */
$(function () {
    $(".show-form").click(function () {
        $(this).next().toggle();
    });
});

/*
 * Display selected configuration
 */
$(function selectConfiguration () {
    $("#select-config-gamer").bind("change", function() {
        var configIndex = $("#select-config-gamer option:selected").val();

        var ul = $("#config-"+configIndex);
        $("#config-displayed").empty();
        $("#config-displayed").append(ul.children().clone());
    });
});
