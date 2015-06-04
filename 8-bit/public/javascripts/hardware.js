$(function() {
    var dialog, form,

        speedRegex = /^[0-9]*[.][0-9]+$/,
        coresRegex = /^[1-8]$/,

        name = $( "#name" ),
        manufacturer = $( "#manufacturer" ),
        speed = $( "#speed" ),
        cores = $( "#cores" ),
        allFields = $( [] ).add( name ).add( manufacturer ).add( speed ).add( cores );

    function checkLength( o, min, max ) {
        if ( o.val().length > max || o.val().length < min ) {
            o.addClass( "invalid-input" );
            return false;
        } else {
            o.addClass("valid-input");
            return true;
        }
    }

    function checkRegexp( o, regexp) {
        if ( !( regexp.test( o.val() ) ) ) {
            o.addClass( "invalid-input" );
            return false;
        } else {
            o.addClass("valid-input");
            return true;
        }
    }

    function addProcessor() {
        var valid = true;
        allFields.removeClass("valid-input");
        allFields.removeClass( "invalid-input");

        valid = valid && checkLength( name, 1, 255 );
        valid = valid && checkLength( manufacturer, 1, 255 );
        valid = valid && checkRegexp( speed, speedRegex );
        valid = valid && checkRegexp( cores, coresRegex );

        if ( valid ) {
            /* Ajax request */
            $.post("/addProcessor",
                {
                    name: name.val(),
                    manufacturer: manufacturer.val(),
                    speed: speed.val(),
                    cores: cores.val()
                }
            ).done(function() {
                        $( "#processors tbody" ).append( "<tr>" +
                                "<td>" + name.val() + "</td>" +
                                "<td>" + manufacturer.val() + "</td>" +
                                "<td>" + speed.val() + "</td>" +
                                "<td>" + cores.val() + "</td>" +
                                "</tr>" );
                        dialog.dialog( "close" );
                        swal({
                            title: ":)",
                            text: "Le processeur a été ajouté.",
                            type: "success",
                            allowOutsideClick: true
                        });
                    }
            ).fail(function () {
                        dialog.dialog( "close" );
                        swal({
                            title: "Oops...",
                            text: "Le processeur existe déjà !",
                            type: "error",
                            allowOutsideClick: true
                        });
            });
        }
        return valid;
    }

    dialog = $( "#processors-form" ).dialog({
        autoOpen: false,
        modal: true,
        closeText: "Fermer",
        close: function() {
            form[ 0 ].reset();
            allFields.removeClass( "valid-input ");
            allFields.removeClass( "invalid-input" );
        }
    });

    form = dialog.find( "form" ).on( "submit", function( event ) {
        event.preventDefault();
        addProcessor();
    });

    $( "#add-processor" ).on( "click", function() {
        dialog.dialog( "open" );
    });
});