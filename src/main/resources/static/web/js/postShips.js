

dataShips = JSON.stringify(
        [
        {type: "Destroyer", locations: ["A1", "B1", "C1"]},
        {type: "Submarine", locations: ["A2", "B2", "C2"]},
        {type: "Patrol Boat", locations: ["B4", "B5"]},
        {type: "Carrier", locations: ["A6", "A7", "A8", "A9"]},
        {type: "BattleShip", locations: ["D6", "E6", "F6", "G6", "H6"]}
        ])

function postShips(gameId) {
    $.post("/api/games/players/" + gameId + "/ships", dataShips)
    .done(function (response, status, jqXHR) {
      alert( "Ship added: " + response );
      // app.ships = response.
    })
    .fail(function (jqXHR, status, httpError) {
      alert("Failed to add ship: " + textStatus + " " + httpError);
    })
}

$(function() {
    postShips(game)
});
