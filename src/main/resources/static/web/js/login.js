jQuery(document).ready(function ($) {
  $('#loginform').submit(function (event) {
    event.preventDefault();
    var data = '{username:' + $('#username').val() + ',password:' + $('#password').val() + '}';
    $.ajax({
      data: data,
      timeout: 1000,
      type: 'POST',
      url: '/api/login'

    }).done(function(data, textStatus, jqXHR) {
        console.log(data)
      var preLoginInfo = JSON.parse($.cookie('dashboard.pre.login.request'));
      window.location = preLoginInfo.url;

    }).fail(function(jqXHR, textStatus, errorThrown) {
      console.log(data)
      alert('Booh! Wrong credentials, try again!');
    });
  });
/*
    $('#registerform').submit(function (event) {
      event.preventDefault();
      var data = '{username:' + $('#regusername').val() + ',password:' + $('#regpassword').val() + '}';
      $.ajax({
        data: data,
        timeout: 1000,
        type: 'POST',
        url: '/api/players'

      }).done(function(data, textStatus, jqXHR) {
        var preLoginInfo = JSON.parse($.cookie('dashboard.pre.login.request'));
        window.location = preLoginInfo.url;

      }).fail(function(jqXHR, textStatus, errorThrown) {
        alert('Booh! Wrong credentials, try again!');
      });
    });
*/

});