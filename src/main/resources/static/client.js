/**
 * Created by hazim on 4/26/16.
 *
 */
var accessToken = "";

function loginFacebook() {
    console.log("Loging in Facebook, Please wait...");

    var clientId = "1709699072616518";
    var redirectUri = "http://localhost:8080";
    var url = "http://www.facebook.com/dialog/oauth?client_id=" + clientId + "&redirect_uri=" + redirectUri;

    var popupWindow = window.open(url, "Facebook login", "width=800, height=600");

    if (popupWindow) {
        popupWindow.onload = function () {
            var fbAuthCode = getUrlParameter('code', popupWindow);
            popupWindow.close();

            //request our Authorization code from our Authorization server
            $.get('http://localhost:8080/api/auth/facebook?code=' + fbAuthCode)
                .success(function (response) {
                    //request Access Token from our Authorization server
                    requestAccessToken(response);
                })
                .error(function (error) {
                    console.log(error);
                });
        };
    }
}

function requestAccessToken(authCode) {
    var redirectURI = "/api/code";
    var data = "grant_type=authorization_code&redirect_uri=" + redirectURI + "&code=" + authCode;

    var base64Authorization = btoa("clientId:averysecretkey");
    jQuery.ajax(
        {
            url: 'http://localhost:8080/oauth/token',
            type: 'POST',
            dataType: "json",
            headers: {"Authorization": "Basic " + base64Authorization},
            data: data
        }).success(function (response) {
            console.log(response);
            //save access token in global variable
            accessToken = response.access_token;
            alert("Logged in successfully!");
        })
        .error(function (error) {
            console.log(error);
        });
}

function callHello() {
    var url = "http://localhost:8080/api/hello";
    $.get(url)
        .success(function (response) {
            console.log(response);
            alert(response);
        })
        .error(function (error) {
            alert(error.responseText);
        });
}

function callHelloSecure() {
    var url = "http://localhost:8080/api/hello/secure";
    $.ajax({
        url: url,
        headers: accessToken != "" ? {"Authorization": "Bearer " + accessToken} : ""
    })
        .success(function (response) {
            console.log(response);
            alert(response);
        })
        .error(function (error) {
            alert(error.responseText);
        });

}

function getUserData() {
    var url = "http://localhost:8080/api/me";
    $.ajax({
        url: url,
        headers: accessToken != "" ? {"Authorization": "Bearer " + accessToken} : ""
    })
        .success(function (response) {
            console.log(response);
            var data = "ID: " + response.id + "\n" +
                "Name: " + response.name + "\n" +
                "Email Address: " + response.email + "\n" +
                "Facebook ID: " + response.facebookId;
            alert(data);
        })
        .error(function (error) {
            alert(error.responseText);
        });
}

function logout(){
    var url = "http://localhost:8080/logout";
    $.ajax({
        url: url,
        headers: accessToken != "" ? {"Authorization": "Bearer " + accessToken} : ""
    })
        .success(function (response) {
            console.log(response);
            alert("Logged out successfully!")
        })
        .error(function (error) {
            console.log(error);
        });
}

var getUrlParameter = function getUrlParameter(sParam, window) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};