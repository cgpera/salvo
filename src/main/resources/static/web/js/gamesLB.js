var playersArray;
var gamesData;

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
      var row = $("<thead><th>Name</th><th>Total</th><th>Won</th><th>Lost</th><th>Tied</th></thead>").appendTo("#leader-list")
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


$(loginBut).click(function () {
    login()
})

function login() {
    var nombre = $("#username").val()
    var pass = $("#password").val()
    console.log(nombre, pass)
    $.post("/api/login",
        {   name: $("#username").val(),
            pwd: $("#password").val() })
        .done(function(data) {
//            console.log(data)
            $("#login-form").hide()
            $("#login-name").text(nombre)
            $("#login-name").show()
            $("#logout-form").show()
            $("#register-form").hide()
        })
/*
        .done(function(data) {
            console.log("done")
            $('#login-name').text(this.data)})
*/
        .fail(function( jqXHR, textStatus ) {
            alert( "Failed: " + textStatus );
            $("#username").val('')
            $("#password").val('')
            $("#login-form").hide()
            $("#register-form").show()
        });

}

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