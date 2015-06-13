/*
 * Fait appel au web service proposé par Flickr pour rechercher des fonds d'écran
  * et les afficher sous forme de diaporama en background (avec Vegas).
 *
 * La méthode appelée est flickr.photos.search. Cette méthode retourne une liste de photos
 * correspondant à plusieurs critères. Pour l'utiliser, il faut disposer d'une clé API
 * (gratuite pour une utilisation non commerciale).
 *
 * La méthode est appelée simplement via une requête Ajax JSON. On extrait les éléments
 * "photo" du résultat, on les mappe à des URLs puis on affiche les images référencées.
 */
$(function getBackgrounds() {
    var method,         // Requested method
        api_key,        // API application key
        user_id,        // The NSID of the user who's photo to search
        text,           // A free text search
        per_page,       // Number of photos to return per page
        page;           // The page of results to return

    method = "flickr.photos.search";
    api_key = "e8d8793cbf22d1ed4ccc0caec7a2f20a";
    user_id = "101568492@N02";
    text = "aurora";
    per_page = "2";
    page = "1";

    var url = [];
    $.ajaxSetup({
        async: false
    });

    // Search for photos
    $.getJSON( "https://api.flickr.com/services/rest/?&method=" + method
        + "&api_key=" + api_key
        + "&user_id=" + user_id
        + "&text=" + text
        + "&per_page=" + per_page
        + "&page=" + page
        + "&format=json&nojsoncallback=1",

        function( data ) {
            $.each( data.photos.photo, function( i, item ) {
                // Map the photo element to an URL
                url[i] = "https://farm" + item.farm + ".staticflickr.com/" + item.server + "/" + item.id + "_" + item.secret + "_b.jpg";
            });
        }
    );

    // Apply Vegas
    $('body').vegas({
        shuffle: true,
        timer: false,
        delay: 10000,
        transition: 'fade2',
        animation: 'random',
        slides: [
            { src: '/public/images/backgrounds/space-bg-min.jpg' },
            { src: url[0] },
            { src: url[1] }
        ]
    });
});