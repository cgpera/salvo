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

      valid0 = respuesta.gamePlayers[0]
      valid1 = respuesta.gamePlayers[1]
      items.push("<div class='divider'></div><div class='section'>");

        items.push(`<p>User Name: ${valid0.player.userName}</p>`)
        items.push(`<p>User Name: ${valid1.player.userName}</p>`)
      items.push("</div>")
      $("#playerInfo").text(`${valid0.player.userName}  vs  ${valid1.player.userName}`);
    $("<div></div>", {
      html: items.join("")
    }).appendTo("body");
  });

})