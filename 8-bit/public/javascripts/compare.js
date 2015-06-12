/*
 * Color the comparison table
 */
$(function colorComparison() {
    $(".eval").each(function() {
        var eval = $(this).html();
        if (eval == 'OK') {
            $(this).addClass("ok");
        } else if (eval == 'KO') {
            $(this).addClass("ko");
        }
    })
});