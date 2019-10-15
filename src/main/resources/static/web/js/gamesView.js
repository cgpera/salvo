var playersArray;
var gamesData;
var jugador = "GUEST";
var data;

$(function() {
    loadData()
});


function updateViewGames(data) {
//    var htmlHead= '<li class="list-group-item">Created   Username 1      Username 2 </li>'
    var htmlList = data.games.map(function(game) {
    return '<li class="list-group-item">' + new Date(game.created).toLocaleString() + ' '
     + game.gamePlayers.map(function(element) { return element.player.userName}).join(' - ') + '<button id="join">Join</button></li>';
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
    validar(nombre)
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
            $("#register-form").show()
            location.reload()
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
            location.reload()
        })
        .fail(function( jqXHR, textStatus ) {
            alert( "Failed: " + textStatus );
        });
}

function register() {
    validar($("#username").val())
    $.post("/api/players",
        {   name: $("#username").val(),
            pwd: $("#password").val() })
        .done(function(data) {
            jugador = $("#username").val()
            alert("Player created " + jugador)
            $("#login-form").hide()
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
            location.reload()
       });
}

function validar(user) {

    var regex = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
//    return regex.test(user) ? true : false;
    if( regex.test(user) == true) {
        return user
    }
    else {
        alert( "User not valid " + user + " , it must be a valid email address");
        $("#username").val('')
        $("#password").val('')
        $("#login-form").show()
        $("#logout-form").hide()
        $("#register-form").show()
        location.reload()
        return false
    }
}

const app = new Vue({
    el: "#app",
    data: {
        message: '',
        player: '',
        games: []
/*
        { date: 'a', gp1: 'a', gp2: 'a'},
        { date: 'b', gp1: 'b', gp2: 'b'},
        { date: 'c', gp1: 'c', gp2: 'c'}
        ] //  'game1', 'game2', 'game3']
*/
    }
})

app.data = data