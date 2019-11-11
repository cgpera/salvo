$(document).ready(function () {

//  var url = 'http://localhost:8080/api/games';
function getParameterByName(name) {
    var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.search);
    return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
}

function makeUrl() {
    var gamePlayerID =  getParameterByName("gp");
    console.log(gamePlayerID)
    return '/api/game_view/' + gamePlayerID;
}

var url = makeUrl();
console.log(url)
  $.get(url, function (respuesta) {
    var items = [];

      items.push("<div class='divider'></div><div class='section'>");
      items.push("<h5 class='blue-text text-darken-2'>Game " + respuesta.id + "</h5>")
      items.push(`<p class="card-panel teal lighten-2"> Game ID: ${respuesta.id} Game Date Creation: ${respuesta.created}</p>`)
    console.log("game id", respuesta.id)
    console.log("respuesta ", respuesta)
    console.log("game date creat", respuesta.created)

      valid0 = respuesta.gamePlayers[0]
      items.push("<div class='divider'></div><div class='section'>");
      items.push(`<p class="card-panel teal lighten-2"> Game ID: ${respuesta.id} Game Date Creation: ${respuesta.created}</p>`)

      console.log("user0", valid0)
      console.log("name", valid0.player.userName, )
//      if(valid0 !== null) {
        items.push(`<p> User ID: ${valid0.player.id} User Name: ${valid0.player.userName}</p>`)
//      }
      items.push("</div>")
//    });
    $("</div>", {
      html: items.join("")
    }).appendTo("body");
  });

})