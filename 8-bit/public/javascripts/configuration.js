/*
 * Set dialog configuration form
 */
$(function setDialogForm() {
    var dialog, form,

        doubleRegex = /^[0-9]+([.][0-9]+)?$/,

        /* Configuration fields */
        name = $( "#name" ),
        freeDiskSpace = $( "#free-disk-space" ),
        ram = $( "#ram" ),
        operatingSystem = $( "#operating-system" ),
        processor = $( "#processor" ),
        videoCard = $( "#video-card" ),
        allFields = $( [] ).add( name ).add( freeDiskSpace ).add( ram ).add( operatingSystem )
            .add( processor ).add( videoCard );


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

    /* Configuration dialog form */
    function addConfiguration() {
        var valid = true;
        allFields.removeClass("valid-input");
        allFields.removeClass( "invalid-input");

        /* Check all fields */
        valid &= checkLength( name, 1, 255 );
        valid &= checkRegexp( freeDiskSpace, doubleRegex );
        valid &= checkRegexp( ram, doubleRegex );
        valid &= checkLength( operatingSystem, 1, 255 );
        valid &= checkLength( processor, 1, 255 );
        valid &= checkLength( videoCard, 1, 255 );

        if ( valid ) {
            /* Ajax request to add the gamer configuration */
            $.post("/configuration/add",
                {
                    name: name.val(),
                    freeDiskSpace: freeDiskSpace.val(),
                    ram: ram.val(),
                    operatingSystem: operatingSystem.val(),
                    processor: processor.val(),
                    videoCard: videoCard.val()
                }
            ).done(function() {
                        dialog.dialog( "close" );
                        /* Displays a success pop-up */
                        swal({
                            title: ":)",
                            text: "La configuration a été ajoutée.",
                            type: "success",
                            timer: 2000,
                            showConfirmButton: false
                        });
                        /* Refresh the page */
                        window.location.reload();
                    }
            ).fail(function () {
                    dialog.dialog( "close" );
                        /* Displays an error pop-up */
                        swal({
                            title: "Oops...",
                            text: "Une erreur est survenue :(",
                            type: "error",
                            timer: 2000,
                            showConfirmButton: false
                        });
            });
        }
        return valid;
    }

    dialog = $( "#configuration-form" ).dialog({
        autoOpen: false,
        modal: true,
        closeText: "Fermer",
        position: { my: "right bottom", at: "right bottom" },
        close: function() {
            form[ 0 ].reset();
            allFields.removeClass( "valid-input ");
            allFields.removeClass( "invalid-input" );
        }
    });

    form = dialog.find( "form" ).on( "submit", function( event ) {
        event.preventDefault();
        addConfiguration();
    });

    $( "#add-configuration" ).on( "click", function() {
        dialog.dialog( "open" );
    });
});