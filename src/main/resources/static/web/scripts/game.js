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
      items.push("<div class='divider'></div><div class='section'>");

       user0 = respuesta.gamePlayers[0].player.userName

    if(respuesta.gamePlayers.length == 2) {
       user1 = respuesta.gamePlayers[1].player.userName
      items.push(`<p>User Name: ${user0}</p>`)
      items.push(`<p>User Name: ${user1}</p>`)
      }
    if(respuesta.gamePlayers.length == 1) {
       user1 = ""
      items.push(`<p>User Name: ${user0}</p>`)
    }


      items.push("</div>")
      $("#playerInfo").text(`${user0}    ${user1}`);
    $("<div></div>", {
      html: items.join("")
    }).appendTo("body");
  });

})