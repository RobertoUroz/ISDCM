/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function duracionc(event) {
    console.log(document.getElementById('duracionh').value,
    document.getElementById('duracionh').selectionStart,
    document.getElementById('duracionh').selectionEnd,
    document.getElementById('duracionmin').value,
    document.getElementById('duracions').value);

    if (event.target === document.getElementById('duracionh') &&
        document.getElementById('duracionh').value.length === 2 &&
        document.getElementById('duracionh').selectionStart === 2 &&
        document.getElementById('duracionh').selectionEnd === 2 &&
        document.getElementById('duracionmin').value==="" &&
        document.getElementById('duracions').value==="") {
        //console.log("caso 1");
        document.getElementById('duracionmin').focus();
    } else if (event.target === document.getElementById('duracionmin') &&
        document.getElementById('duracionmin').value.length === 2 &&
        document.getElementById('duracionmin').selectionStart === 2 &&
        document.getElementById('duracionmin').selectionEnd === 2 &&
        document.getElementById('duracions').value==="") {
        //console.log("caso 2");
        document.getElementById('duracions').focus();
    } else if (document.getElementById('duracionmin').value.length === 0 &&
        document.getElementById('duracionmin').selectionStart === 0 &&
        document.getElementById('duracionmin').selectionEnd === 0 &&
        event.key === "Backspace") {
        //console.log("caso 3");
        //document.getElementById('duracionh').value =
        //    document.getElementById('duracionh').value.slice(0,-1);
        document.getElementById('duracionh').focus();
    } else if (document.getElementById('duracions').value.length === 0 &&
        document.getElementById('duracions').selectionStart === 0 &&
        document.getElementById('duracions').selectionEnd === 0 &&
        event.key === "Backspace") {
        //console.log("caso 4");
        //document.getElementById('duracionmin').value =
        //    document.getElementById('duracionmin').value.slice(0,-1);
        document.getElementById('duracionmin').focus();
    } else if (event.target === document.getElementById('duracionh') &&
        document.getElementById('duracionh').value.length === 3 &&
        document.getElementById('duracionh').selectionStart === 3 &&
        document.getElementById('duracionh').selectionEnd === 3 &&
        document.getElementById('duracionmin').value==="" &&
        document.getElementById('duracions').value===""){
        document.getElementById('duracionmin').value =
            document.getElementById('duracionh').value.slice(2);
        document.getElementById('duracionh').value =
            document.getElementById('duracionh').value.slice(0,-1);
        document.getElementById('duracionmin').focus();
    } else if (event.target === document.getElementById('duracionmin') &&
        document.getElementById('duracionmin').value.length === 3 &&
        document.getElementById('duracionmin').selectionStart === 3 &&
        document.getElementById('duracionmin').selectionEnd === 3 &&
        document.getElementById('duracions').value===""){
        document.getElementById('duracions').value =
            document.getElementById('duracionmin').value.slice(2);
        document.getElementById('duracionmin').value =
            document.getElementById('duracionmin').value.slice(0,-1);
        document.getElementById('duracions').focus();
    }
    
    p_h = document.getElementById('duracionh').value;
    p_min = document.getElementById('duracionmin').value;
    p_s = document.getElementById('duracions').value;
}

function fechavalida(y,m,d) {
    y = parseInt(y);
    m = parseInt(m);
    d = parseInt(d);
    
    if (y>2030 || y<1914 || m<1 || m>12 || d<1 || d>31) return false;
    if (m==2) {
        if (d>29) return false;
        else if (d==29) return (y%4==0 && (y%100==0 || y%400==0));
        else return true;
    } else if (m==4 || m==6 || m==9 || m==11) return (d<31);
    else return true;
}

function fechac(event) {
    console.log(document.getElementById('fechay').value,
    document.getElementById('fechay').selectionStart,
    document.getElementById('fechay').selectionEnd,
    document.getElementById('fecham').value,
    document.getElementById('fechad').value);

    if (event.target === document.getElementById('fechay') &&
        document.getElementById('fechay').value.length === 4 &&
        document.getElementById('fechay').selectionStart === 4 &&
        document.getElementById('fechay').selectionEnd === 4 &&
        document.getElementById('fecham').value==="" &&
        document.getElementById('fechad').value==="") {
        //console.log("caso 1");
        document.getElementById('fecham').focus();
    } else if (event.target === document.getElementById('fecham') &&
        document.getElementById('fecham').value.length === 2 &&
        document.getElementById('fecham').selectionStart === 2 &&
        document.getElementById('fecham').selectionEnd === 2 &&
        document.getElementById('fechad').value==="") {
        //console.log("caso 2");
        document.getElementById('fechad').focus();
    } else if (event.target === document.getElementById('fechad') &&
        document.getElementById('fechad').value.length === 2 &&
        document.getElementById('fechad').selectionStart === 2 &&
        document.getElementById('fechad').selectionEnd === 2) {
        if (fechavalida(document.getElementById('fechay').value,
            document.getElementById('fecham').value,
            document.getElementById('fechad').value)) {
                document.getElementById('fechad').setCustomValidity("");
            } else {
                document.getElementById('fechad').setCustomValidity("¡Fecha no válida!");
            }
    } else if (document.getElementById('fecham').value.length === 0 &&
       document.getElementById('fecham').selectionStart === 0 &&
       document.getElementById('fecham').selectionEnd === 0 &&
       event.key === "Backspace") {
       //console.log("caso 3");
       //document.getElementById('fechay').value =
       //    document.getElementById('fechay').value.slice(0,-1);
       document.getElementById('fechay').focus();
    } else if (document.getElementById('fechad').value.length === 0 &&
       document.getElementById('fechad').selectionStart === 0 &&
       document.getElementById('fechad').selectionEnd === 0 &&
       event.key === "Backspace") {
       //console.log("caso 4");
       //document.getElementById('fecham').value =
       //    document.getElementById('fecham').value.slice(0,-1);
       document.getElementById('fecham').focus();
    } else if (event.target === document.getElementById('fechay') &&
       document.getElementById('fechay').value.length === 5 &&
       document.getElementById('fechay').selectionStart === 5 &&
       document.getElementById('fechay').selectionEnd === 5 &&
       document.getElementById('fecham').value==="" &&
       document.getElementById('fechad').value===""){
       document.getElementById('fecham').value =
           document.getElementById('fechay').value.slice(4);
       document.getElementById('fechay').value =
           document.getElementById('fechay').value.slice(0,-1);
       document.getElementById('fecham').focus();
    } else if (event.target === document.getElementById('fecham') &&
       document.getElementById('fecham').value.length === 3 &&
       document.getElementById('fecham').selectionStart === 3 &&
       document.getElementById('fecham').selectionEnd === 3 &&
       document.getElementById('fechad').value===""){
       document.getElementById('fechad').value =
           document.getElementById('fecham').value.slice(2);
       document.getElementById('fecham').value =
           document.getElementById('fecham').value.slice(0,-1);
       document.getElementById('fechad').focus();
    }
    
    f_y = document.getElementById('fechay').value;
    f_m = document.getElementById('fecham').value;
    f_d = document.getElementById('fechad').value;
}

if (document.getElementById('duracionh')) {
    var p_h = document.getElementById('duracionh').value;
    var p_min = document.getElementById('duracionmin').value;
    var p_s = document.getElementById('duracions').value;

    document.getElementById('duracionh').addEventListener("keyup",duracionc,false);
    document.getElementById('duracionmin').addEventListener("keyup",duracionc,false);
    document.getElementById('duracions').addEventListener("keyup",duracionc,false);
} else if (document.getElementById('fechay')) {
    var f_y = document.getElementById('fechay').value;
    var f_m = document.getElementById('fecham').value;
    var f_d = document.getElementById('fechad').value;

    document.getElementById('fechay').addEventListener("keyup",fechac,false);
    document.getElementById('fecham').addEventListener("keyup",fechac,false);
    document.getElementById('fechad').addEventListener("keyup",fechac,false);
}