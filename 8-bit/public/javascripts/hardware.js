$(function() {
    var processorDialog, processorForm,
        videoCardDialog, videoCardForm,

        speedRegex = /^[0-9]*[.][0-9]+$/,
        coresRegex = /^[1-8]$/,
        speedRegexI = /^[0-9]+$/,

        /* Processor fields */
        processorName = $( "#processor-name" ),
        processorManufacturer = $( "#processor-manufacturer" ),
        processorSpeed = $( "#processor-speed" ),
        cores = $( "#cores" ),
        processorAllFields = $( [] ).add( processorName ).add( processorManufacturer ).add( processorSpeed ).add( cores ),

        /* Video card fields */
        videoCardName = $( "#video-card-name" ),
        videoCardManufacturer = $( "#video-card-manufacturer" ),
        videoCardSpeed = $( "#video-card-speed" ),
        directX = $( "#directX" ),
        videoCardAllFields = $( [] ).add( videoCardName ).add( videoCardManufacturer ).add( videoCardSpeed ).add( directX );

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

    /* Processor dialog form */
    function addProcessor() {
        var valid = true;
        processorAllFields.removeClass("valid-input");
        processorAllFields.removeClass( "invalid-input");

        valid = valid && checkLength( processorName, 1, 255 );
        valid = valid && checkLength( processorManufacturer, 1, 255 );
        valid = valid && checkRegexp( processorSpeed, speedRegex );
        valid = valid && checkRegexp( cores, coresRegex );

        if ( valid ) {
            /* Ajax request */
            $.post("/addProcessor",
                {
                    name: processorName.val(),
                    manufacturer: processorManufacturer.val(),
                    speed: processorSpeed.val(),
                    cores: cores.val()
                }
            ).done(function() {
                        $( "#processors tbody" ).append( "<tr>" +
                                "<td>" + processorName.val() + "</td>" +
                                "<td>" + processorManufacturer.val() + "</td>" +
                                "<td>" + processorSpeed.val() + "</td>" +
                                "<td>" + cores.val() + "</td>" +
                                "</tr>" );
                        processorDialog.dialog( "close" );
                        swal({
                            title: ":)",
                            text: "Le processeur a été ajouté.",
                            type: "success",
                            timer: 2000,
                            showConfirmButton: false
                        });
                    }
            ).fail(function () {
                    processorDialog.dialog( "close" );
                        swal({
                            title: "Oops...",
                            text: "Le processeur existe déjà !",
                            type: "error",
                            timer: 2000,
                            showConfirmButton: false
                        });
            });
        }
        return valid;
    }

    processorDialog = $( "#processors-form" ).dialog({
        autoOpen: false,
        modal: true,
        closeText: "Fermer",
        close: function() {
            processorForm[ 0 ].reset();
            processorAllFields.removeClass( "valid-input ");
            processorAllFields.removeClass( "invalid-input" );
        }
    });

    processorForm = processorDialog.find( "form" ).on( "submit", function( event ) {
        event.preventDefault();
        addProcessor();
    });

    $( "#add-processor" ).on( "click", function() {
        processorDialog.dialog( "open" );
    });

    /* Video card dialog form */
    function addVideoCard() {
        var valid = true;
        videoCardAllFields.removeClass("valid-input");
        videoCardAllFields.removeClass( "invalid-input");

        valid = valid && checkLength( videoCardName, 1, 255 );
        valid = valid && checkLength( videoCardManufacturer, 1, 255 );
        valid = valid && checkRegexp( videoCardSpeed, speedRegexI );

        if ( valid ) {
            /* Ajax request */
            $.post("/addVideoCard",
                {
                    name: videoCardName.val(),
                    manufacturer: videoCardManufacturer.val(),
                    speedMemory: videoCardSpeed.val(),
                    versionDirectX: directX.val()
                }
            ).done(function() {
                    $( "#video-cards tbody" ).append( "<tr>" +
                        "<td>" + videoCardName.val() + "</td>" +
                        "<td>" + videoCardManufacturer.val() + "</td>" +
                        "<td>" + videoCardSpeed.val() + "</td>" +
                        "<td>" + directX.val() + "</td>" +
                        "</tr>" );
                    videoCardDialog.dialog( "close" );
                    swal({
                        title: ":)",
                        text: "La carte graphique a été ajoutée.",
                        type: "success",
                        timer: 2000,
                        showConfirmButton: false
                    });
                }
            ).fail(function () {
                    videoCardDialog.dialog( "close" );
                    swal({
                        title: "Oops...",
                        text: "La carte graphique existe déjà !",
                        type: "error",
                        timer: 2000,
                        showConfirmButton: false
                    });
                });
        }
        return valid;
    }

    videoCardDialog = $( "#video-cards-form" ).dialog({
        autoOpen: false,
        modal: true,
        closeText: "Fermer",
        close: function() {
            videoCardForm[ 0 ].reset();
            videoCardAllFields.removeClass( "valid-input ");
            videoCardAllFields.removeClass( "invalid-input" );
        }
    });

    videoCardForm = videoCardDialog.find( "form" ).on( "submit", function( event ) {
        event.preventDefault();
        addVideoCard();
    });

    $( "#add-video-card" ).on( "click", function() {
        videoCardDialog.dialog( "open" );
    });
});