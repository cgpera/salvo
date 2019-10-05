var playersArray;
var gamesData;
var jugador = "GUEST";

$(function() {
    loadData()
});

function updateViewGames(data) {
/*
    console.log(data)
    if(data) {
        jugador = data.player
        console.log(player)
    }
    else {
        jugador = "GUEST"
    }
*/
    var htmlList = data.games.map(function(game) {
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

$(signBut).click(function () {
    register()
})

$(logoutBut).click(function () {
    logout()
})

function login() {
    var nombre = $("#username").val()
    var pass = $("#password").val()
            $.post("/api/login",
        {   name: $("#username").val(),
            pwd: $("#password").val() })
        .done(function(data) {
            $("#login-form").hide()
            jugador = nombre
            console.log(jugador)
            $("#login-name").text(nombre)
            $("#login-name").show()
            $("#logout-form").show()
            $("#register-form").hide()
        })
        .fail(function( jqXHR, textStatus ) {
            alert( "Failed: " + textStatus );
            $("#username").val('')
            $("#password").val('')
            $("#login-form").show()
            $("#logout-form").hide()
//            $("#register-form").show()
        });
}

function logout(evt) {
    $("#username").val('')
    $("#password").val('')
    $.post("/api/logout")
        .done(function(data) {
            $("#login-form").show()
            $("#login-name").show()
            $("#logout-form").hide()
            $("#register-form").show()
            jugador = "GUEST"
            console.log(jugador)
            location.reload()

        })
        .fail(function( jqXHR, textStatus ) {
            alert( "Failed: " + textStatus );
        });
}

function register() {
//    var nombre = $("#username").val()
//    var pass = $("#password").val()
//    console.log((nombre, pass))
    $.post("/api/players",
        {   name: $("#username").val(),
            pwd: $("#password").val() })
        .done(function(data) {
            $("#login-form").hide()
            jugador = $("#username").val()
            $("#login-name").text(jugador)
            $("#logout-form").show()
            $.post("/api/login",
                {   name: $("#username").val(),
                    pwd: $("#password").val() })
                .done(function (data) {
                    console.log("logged after signup")
                    return data
                })
        })
        .fail(function( jqXHR, textStatus ) {
            alert( "Fallo: " + textStatus );
            $("#username").val('')
            $("#password").val('')
            $("#login-form").show()
            $("#logout-form").hide()
        });
}
