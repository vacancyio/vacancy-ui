function enableEditor() {
    $('textarea').wysihtml5({
        toolbar: {
            "font-styles": false,
            "emphasis":false,
            "lists": true,
            "html": false,
            "link": false,
            "image": false,
            "color": false,
            "blockquote": false
        }
    });
}

$(document).ready(function(){

    return enableEditor();

});
