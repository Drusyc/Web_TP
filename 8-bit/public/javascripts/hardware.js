/*
 * Sets dialog forms
 */
$(function setDialogForms() {
    var processorDialog, processorForm,
        videoCardDialog, videoCardForm,
        gameDialog, gameForm,

        dateRegex = /^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/,
        doubleRegex = /^[0-9]*[.][0-9]+$/,
        integerRegex = /^[0-9]+$/,
        coresRegex = /^[1-8]$/,

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
        videoCardAllFields = $( [] ).add( videoCardName ).add( videoCardManufacturer ).add( videoCardSpeed ).add( directX ),

        /* Game fields */
        gameName = $( "#game-name" ),
        gameDevelopers = $( "#game-developers" ),
        gameModes = $( "#game-modes" ),
        gameEUDate = $( "#game-EU-date" ),
        gameUSDate = $( "#game-US-date" ),
        gameJPNDate = $( "#game-JPN-date" ),
        gameGenre1 = $( "#game-genre-1" ),
        gameGenre2 = $( "#game-genre-2" ),
        configurationSpace = $( "#configuration-space" ),
        configurationRam = $( "#configuration-ram" ),
        configurationOs1 = $( "#configuration-os-1" ),
        configurationOs2 = $( "#configuration-os-2" ),
        configurationProcessor1 = $( "#configuration-processor-1" ),
        configurationProcessor2 = $( "#configuration-processor-2" ),
        configurationVideoCard1 = $( "#configuration-video-card-1" ),
        configurationVideoCard2 = $( "#configuration-video-card-2" ),
        gameAllFields = $( [] ).add( gameName ).add( gameDevelopers ).add( gameModes )
        .add( gameEUDate ).add( gameUSDate ).add( gameJPNDate ).add( gameGenre1 ).add( gameGenre2 )
        .add( configurationSpace ).add( configurationRam )
        .add( configurationOs1 ).add( configurationOs2 )
        .add( configurationProcessor1 ).add( configurationProcessor2 )
        .add( configurationVideoCard1 ).add( configurationVideoCard2 );


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

        /* We need to check ALL fields, so we write valid &= instead of valid = valid && */
        valid &= checkLength( processorName, 1, 255 );
        valid &= checkLength( processorManufacturer, 1, 255 );
        valid &= checkRegexp( processorSpeed, doubleRegex );
        valid &= checkRegexp( cores, coresRegex );

        if ( valid ) {
            /* Ajax request to add the processor */
            $.post("/addProcessor",
                {
                    name: processorName.val(),
                    manufacturer: processorManufacturer.val(),
                    speed: processorSpeed.val(),
                    cores: cores.val()
                }
            ).done(function() {
                        $( "#processors" ).find( "tbody" ).append( "<tr>" +
                                "<td>" + processorName.val() + "</td>" +
                                "<td>" + processorManufacturer.val() + "</td>" +
                                "<td>" + processorSpeed.val() + "</td>" +
                                "<td>" + cores.val() + "</td>" +
                                "</tr>" );
                        processorDialog.dialog( "close" );
                        /* Displays a success pop-up */
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
                        /* Displays an error pop-up */
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

        valid &= checkLength( videoCardName, 1, 255 );
        valid &= checkLength( videoCardManufacturer, 1, 255 );
        valid &= checkRegexp( videoCardSpeed, integerRegex );
        valid &= checkLength( directX, 1, 255 );

        if ( valid ) {
            /* Ajax request */
            $.post("/addVideoCard",
                {
                    name: videoCardName.val(),
                    manufacturer: videoCardManufacturer.val(),
                    speed: videoCardSpeed.val(),
                    versionDirectX: directX.val()
                }
            ).done(function() {
                    $( "#video-cards").find( "tbody" ).append( "<tr>" +
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

    /* Game dialog form */
    function addGame() {
        var valid = true;

        gameAllFields.removeClass("valid-input");
        gameAllFields.removeClass("invalid-input");

        valid &= checkLength( gameName, 1, 255 );
        valid &= checkRegexp( configurationSpace, doubleRegex );
        valid &= checkRegexp( configurationRam, doubleRegex );

        if ( gameEUDate.val().length > 0 )
            valid &= checkRegexp( gameEUDate, dateRegex );
        if ( gameUSDate.val().length > 0 )
            valid &= checkRegexp( gameUSDate, dateRegex );
        if ( gameJPNDate.val().length > 0 )
            valid &= checkRegexp( gameJPNDate, dateRegex );

        /* At least one genre, OS, processor and video card is required */
        valid &= checkLength( gameGenre1, 1, 255 );
        valid &= checkLength( configurationOs1, 1, 255 );
        valid &= checkLength( configurationProcessor1, 1, 255 );
        valid &= checkLength( configurationVideoCard1, 1, 255 );

        return valid;
    }

    gameDialog = $( "#games-form" ).dialog({
        autoOpen: false,
        closeText: "Fermer",
        modal: true,
        position: { my: "right bottom", at: "right bottom" },
        close: function() {
            gameForm[ 0 ].reset();
            gameAllFields.removeClass( "valid-input ");
            gameAllFields.removeClass( "invalid-input" );
        }
    });

    gameForm = gameDialog.find( "form" ).on( "submit", function( event ) {
        if (!addGame())
            event.preventDefault();
    });

    $( "#add-game" ).on( "click", function() {
        gameDialog.dialog( "open" );
    });
});