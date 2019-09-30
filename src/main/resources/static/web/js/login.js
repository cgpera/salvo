jQuery(document).ready(function ($) {
  $('#loginform').submit(function (event) {
    event.preventDefault();
    var datastr = '{name:' + $('#username').val() + ',pwd:' + $('#password').val() + '}';
    var name = $('#username').val()
    var pwd = $('#password').val()
//    console.log(datastr, data)
    $.ajax({
//      data: data,
        name: name,
        pwd: pwd,
        data: {
        'name': name,
        'pwd': pwd
        },
      timeout: 1000,
      type: 'POST',
      url: '/api/login'
    }).done(function(data, textStatus, jqXHR) {
      var preLoginInfo = JSON.parse($.cookie('dashboard.pre.login.request'));
      window.location = preLoginInfo.url;

    }).fail(function(jqXHR, textStatus, errorThrown) {
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