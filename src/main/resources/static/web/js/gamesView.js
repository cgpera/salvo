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
        
//      updateViewGames(data);
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

/* VUE */

const init_str = {
  method: 'GET',
  headers: {
    'Content-Type': 'application/json',
  },
  mode: 'cors',
};

const url = '/api/games'


const getGamesFromURL = async (urlAddr, options) => {
  try {
    const response = await fetch(urlAddr, options);
    const json = response.json()
    return json
  } catch (e) {
    throw `Error original: ${e}`;
  }
}

getGamesFromURL(url, init_str)
  .then(result => {
    app.games = result.games
    app.player = result.player
    app.gamePlayers = result.games.map(game => game.gamePlayers)

    app.gamePlayers.forEach(function(elemento, index) {
        app.playerGP = []
        app.playerGP = elemento.map((el, index) => el.player.userName)
    })
    app.players = app.gamePlayers.map((gp, index) => gp)
  })
  .catch(e => console.log(`Error Capturado Fuera de la función async: ${e}`));


var app = new Vue({
    el: "#app",
    data: {
        message: '',
        player: '',
        gamePlayers: [],
        players: {},
        playerGP: [],
        games: []
    },
    methods: {
        login() {
            var nombre = $("#username").val()
            var pass = $("#password").val()
            validar(nombre)
            $.post("/api/login",
                {   name: $("#username").val(),
                    pwd: $("#password").val() })
            .done(function(data) {
            $("#login-form").hide()
            location.reload()
            jugador = nombre
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
        },

            logout(evt) {
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
        },

        register() {
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
        },

        joinGame(index) {
        $.post("/api/games/" + index + "/players")
        .done(function(data) {
            console.log(data)
            location.href = "/web/game.html?gp=" + data.gpid
         })
         .fail(function(error) {
            console.log(error)
         })

        },

        createGame() {
            $.post("/api/games/")
                .done(function(data) {
                    console.log(data.gpid)
                    location.href = "/web/game.html?gp=" + data.gpid
                 })
                 .fail(function(error) {
                    console.log(error)
                 })
        },
        enterGame(index) {
            location.href = "/web/game.html?gp=" + index
        }
    }
})

function validar(user) {
    var regex = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
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
