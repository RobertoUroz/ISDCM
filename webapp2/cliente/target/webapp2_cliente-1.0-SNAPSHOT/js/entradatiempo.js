/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var p_h = document.getElementById('duracionh').value;
var p_min = document.getElementById('duracionmin').value;
var p_s = document.getElementById('duracions').value;

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

document.getElementById('duracionh').addEventListener("keyup",duracionc,false);
document.getElementById('duracionmin').addEventListener("keyup",duracionc,false);
document.getElementById('duracions').addEventListener("keyup",duracionc,false);