$(document).ready(function() {

  var url = 'http://localhost:8080/api/games';

  $.get(url, function(respuesta) {
    var items = [];
/*      $.each( respuesta, function( key, val ) {
        items.push( "<li id='" + key + "'>" + JSON.stringify(val) + "</li>" );
      });

      $( "<ol/>", {
        html: items.join( "" )
      }).appendTo( "body" );
*/

        $.each(respuesta, function(key, val) {
//        items.push("<div class="divider"></div><div class="section">);
//        items.push("<h5>Section" + key + "</h5>")
        items.push("<p>" + JSON.stringify(val) + "</p>")
//        items.push("</div>")
        });
        $( "<div/>", {
          html: items.join( "" )
        }).appendTo( "body" );

//var lin = respuesta.map(elem => `${elem.id} ${elem.created} ${elem.gamePlayers}`)
//var lin2 = respuesta.map(elem => elem.gamePlayers)
//var lin3 = lin2.map((elem, index) => elem[index])
//    console.log(respuesta);
//    console.log(lin, lin2, lin3);
  });

})