/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var BASE_URL = "http://localhost:8080/server_rest/resources";
var url_string = window.location.href;
var url = new URL(url_string);
var id = url.searchParams.get("id");
var finalUrl = BASE_URL += "/reproductor?id=" + id; 
var video = document.getElementById('my-video');
var source = document.getElementById('sourceVideo');
$.ajax({
    url: finalUrl,
    cache: false,
    success: function (html) {
        console.log(html);
        source.setAttribute('src', html);
        video.load();
    }
});