/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var container = document.getElementById('container');
var bcifrar = document.getElementById('bcifrar');
var bdescifrar = document.getElementById('bdescifrar');

container.addEventListener('dragenter',dragenterf,false);
container.addEventListener('dragover',dragoverf,false);
container.addEventListener('dragleave',dragleavef,false);
container.addEventListener('drop',dropf,false);

function dragenterf(e) {
    bcifrar.className = bdescifrar.className = "grande";
    e.preventDefault();
    e.stopPropagation();
}

function dragoverf(e) {
    bcifrar.className = bdescifrar.className = "grande";
    e.preventDefault();
    e.stopPropagation();
}

function dragleavef(e) {
    bcifrar.className = bdescifrar.className = "";
    e.preventDefault();
    e.stopPropagation();
}

function dropf(e) {
    bcifrar.className = bdescifrar.className = "";
    var dt = e.dataTransfer;
    var files = dt.files;
    if (files.length > 0) {
        var file = files[0];
        var url = "/cliente_glassfish/servletCifradoContenido";
        var fd = new FormData();
        fd.append("accioncifrado","cifrar");
        fd.append("username",document.getElementById('username').value);
        fd.append("fichero",file);
        fetch(url, {method:"POST",body:fd}).then( response => {
            var ct = response.headers.get("Content-Type");
            var cd = response.headers.get("Content-Disposition");
            var mcd = /"([^"]*)"/.exec(cd);
            var fn = (mcd !== null && mcd[1])?mcd[1]:null;
            document.body.innerHTML = document.body.innerHTML + "<a href='"+
            URL.createObjectURL(response.blob())+"'"+
            (fn!==null?(" download='"+fn+"'"):"")+
            " id='enlace'></a>";
            document.getElementById('enlace').click();
            document.getElementById('enlace').parent.
                    removeChild(document.getElementById('enlace'));
        }
           );
    }
    
    e.preventDefault();
    e.stopPropagation();
}