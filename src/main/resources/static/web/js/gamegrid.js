$(document).ready(function () {

  var url = '/api/games';

  $.get(url, function (respuesta) {
    var items = [];
    /*      $.each( respuesta, function( key, val ) {
            items.push( "<li id='" + key + "'>" + JSON.stringify(val) + "</li>" );
          });

          $( "<ol/>", {
            html: items.join( "" )
          }).appendTo( "body" );
    */
    console.log(respuesta)
    $.each(respuesta, function (key, val) {
      var valor = JSON.stringify(val)
//      console.log(valor)
      valid0 = val.gamePlayers[0]
      valid1 = val.gamePlayers[1]
      items.push("<div class='divider'></div><div class='section'>");
      items.push("<h5 class='blue-text text-darken-2'>Game " + key + "</h5>")
      items.push(`<p class="card-panel teal lighten-2"> Game ID: ${val.id} Game Date Creation: ${val.created}</p>`)

      if(valid0.player.id !== null) {
        items.push(`<p> User ID: ${valid0.player.id} User Name: ${valid0.player.userName}</p>`)
      }
      if(valid1.player.id !== null) {
        items.push(`<p> User ID: ${valid1.player.id} User Name: ${valid1.player.userName}</p>`)
      }
      items.push("</div>")
    });
    $("<div/>", {
      html: items.join("")
    }).appendTo("body");
  });

})