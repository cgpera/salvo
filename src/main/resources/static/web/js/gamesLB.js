let playersArray;
let gamesData;

$(function() {
    loadData()
});

function updateViewGames(data) {
    var htmlList = data.map(function(game) {
    return '<li class="list-group-item">' + new Date(game.created).toLocaleString() + ' '
     + game.gamePlayers.map(function(element) { return element.player.userName}).join(', ') + '</li>';
     }).join('')
  document.getElementById("game-list").innerHTML = htmlList;
}


function loadData() {
  $.get("/api/games")
    .done(function(data) {
      updateViewGames(data);
    })
    .fail(function( jqXHR, textStatus ) {
      alert( "Failed: " + textStatus );
    });

  $.get("/api/leaderBoard")
    .done(function(respuesta) {
//      let row = $('<th></th>').appendTo("#leader-list");
      let row = $("<thead><th>Name</th><th>Total</th><th>Won</th><th>Lost</th><th>Tied</th></thead>").appendTo("#leader-list")
      $.each(respuesta, function(key, data2) {
        $("<tr>").appendTo(row)
        $("<td>" + data2.email + "</td>").appendTo(row);
        $("<td class='textCenter'>" + data2.scores.total.toFixed(1) + "</td>").appendTo(row);
        $("<td class='textCenter'>" + data2.scores.won + "</td>").appendTo(row);
        $("<td class='textCenter'>" + data2.scores.lost + "</td>").appendTo(row);
        $("<td class='textCenter'>" + data2.scores.tied + "</td>").appendTo(row);
        $("</tr>").appendTo(row)
      })
      })
    .fail(function( jqXHR, textStatus ) {
      alert( "Failed: " + textStatus );
    });
}

function login(evt) {
    evt.preventDefault();
    var form = evt.target.form;
    $.post("/api/login",
        {   name: form["username"].value,
            pwd: form["password"].value })
        .done(function() {
            console.log("exitoss", name);
        })
        .fail(function( jqXHR, textStatus ) {
            alert( "Failed: " + textStatus );
        });

}

/*function login() {
    $(boton).Click(function() {
    $.post("/api/login",
        {
        name: form["username"].value,
        pwd: form["password"].value
        })
        .done(function() {
        console.log("exito", name)
        })
        .fail(function(jqXHR, textStatus) {
            alert("Failed" + textStatus)
        })
     })
}
*/
function logout(evt) {
    evt.preventDefault();
    $.post("/api/logout")
        .done(function(data) {
            console.log(data.status)
        })
        .fail(function( jqXHR, textStatus ) {
            alert( "Failed: " + textStatus );
        });
}