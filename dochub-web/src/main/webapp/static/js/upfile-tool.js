
function upfile(md5, id, userid, noticeid, final){
    // console.log(id);
    var file = document.getElementById(id).files[0];
    if(md5 == null || md5 == undefined || md5 == ""){
        md5 = computeMD5(file, upfile, id, userid, noticeid, final);
        return ;
    }
    var size = file.size;
    var name = file.name;
    var index = 0;
    var step = 10485670;
    var split = Math.ceil(size / step);
    if(preUpload(md5, split, name, size, userid, noticeid)){
        var splitNo = 0;
        document.getElementById("waitmodalbutton").click();
        upload(file, step, index, splitNo, split, md5, final, noticeid);
    }
    else{
        document.getElementById("waitmodalcancel").click();
        alert("上传失败");
    }

}

function upload(file, step, index, splitNo, totalSplit, md5, final, noticeid) {
    // console.log("index:" + index + " splitNo:" + splitNo)
    if(splitNo >= totalSplit){
        combine(md5);
        final(file.name, md5, noticeid);
        document.getElementById("waitmodalcancel").click();
        // console.log(splitNo + ' : ' + totalSplit);
        return;
    }
    var fileslice = file.slice(index, index + step);
    fileslice = new File([fileslice], splitNo + name, {type: file.type});
    var f;
    index += step;
    $.ajax({
        url:fileServer + "/isexists",
        type:"post",
        data:{"fatherMD5":md5, "splitNo":splitNo},
        async:false,
        success:function(data){
            // console.log(splitNo + " over ")
            if(data.isexists){
                upload(file, step, index, splitNo + 1, totalSplit, md5, final, noticeid);
                return;
            }
            else{
                send(fileslice, file, step, index, splitNo, totalSplit, md5, upload, final, noticeid);
                return;
            }
        },
        error:function(data){
            console.log(data);
            return false;
        }
    });
}

function preUpload(fatherMD5, split, fileName, size, userid, noticeid){
    console.log("preupload");
    $.ajax({
        url:fileServer + "/preupload",
        type:"post",
        data:{"fatherMD5":fatherMD5, "split":split, "fileName":fileName, "size":size, "noticeid":noticeid, "userid":userid, "splitsize":10485670},
        async:false,
        success:function(data){
            if(data.status == 1){
                return true;
            }
            else{
                return false;
            }
        },
        error:function(data){
            console.log(data);
        }
    });
}

function isExists(fatherMD5, splitNo, f){
    $.ajax({
        url:fileServer + "/isexists",
        type:"post",
        data:{"fatherMD5":fatherMD5, "splitNo":splitNo},
        async:false,
        success:function(data){
            if(data.isexists){
                f = true;
                return true;
            }
            else{
                f = false;
                return false;
            }
        },
        error:function(data){
            console.log(data);
            return false;
        }
    });
}

function send(data, file, step, index, splitNo, totalSplit, md5, upload, final, noticeid){
    // console.log(data);
    var fd = new FormData();
    fd.append("filedata", data);
    fd.append("fatherMD5", md5);
    fd.append("splitNo", splitNo + "");
    // console.log(data);
    $.ajax({
        url:fileServer + "/upload",
        data:fd,
        type:"post",
        async:true,
        contentType: false,
        processData: false,
        xhrFields: {  withCredentials: true  },
        xhr: function(){ //获取ajaxSettings中的xhr对象，为它的upload属性绑定progress事件的处理函数
            myXhr = $.ajaxSettings.xhr();
            if(myXhr.upload){ // check if upload property exists
                myXhr.upload.addEventListener('progress',function(e){
                    var loaded = e.loaded;                  //已经上传大小情况
                    var total = e.total;                      //附件总大小
                    // console.log(loaded + "  " + total);
                    var percent = Math.floor(100 * (loaded + step * splitNo) / file.size)+"%";     //已经上传的百分比
                    document.getElementById("myModalLabel").innerHTML = "已上传 " + percent;
                    // console.log("已经上传了："+"width: " + percent + ";");
                    document.getElementById("processbar").setAttribute("style", "width: " + percent + ";");
                    // $("#processbar").css("width",percent);
                }, false); // for handling the progress of the upload
            }
            return myXhr;
        },
        success:function(data){
            console.log(data);
            if(data.status == 1){
                upload(file, step, index, splitNo + 1, totalSplit, md5, final, noticeid);
            }
            else{
                alert("上传失败");

            }
            return;
        },
        error:function(data){
            console.log(data)
        }
    });
}

function computeMD5(file, fun, id, userid, noticeid, final) {
    console.log("start");
    var blobSlice = File.prototype.slice || File.prototype.mozSlice || File.prototype.webkitSlice,
        file,
        chunkSize = 2097152, // read in chunks of 2MB
        chunks = Math.ceil(file.size / chunkSize),
        currentChunk = 0,
        spark = new SparkMD5.ArrayBuffer(),
        frOnload = function(e){
            //  log.innerHTML+="\nread chunk number "+parseInt(currentChunk+1)+" of "+chunks;
            spark.append(e.target.result); // append array buffer
            currentChunk++;
            if (currentChunk < chunks)
                loadNext();
            else{
                var end = spark.end();
                fun(end, id, userid, noticeid, final);
                return ;
            }
        },
        frOnerror = function () {
            return null;
        };
    function loadNext() {
        var fileReader = new FileReader();
        fileReader.onload = frOnload;
        fileReader.onerror = frOnerror;
        var start = currentChunk * chunkSize,
            end = ((start + chunkSize) >= file.size) ? file.size : start + chunkSize;
        fileReader.readAsArrayBuffer(blobSlice.call(file, start, end));
    };
    loadNext();
}
function combine(md5){
    console.log("@combine " + md5);
    $.ajax({
        url:"http://localhost:8081/combine",
        type:"post",
        async:"false",
        data:{"md5" : md5},
        success:function (data) {
            // console.log(data);
        },
        error:function (data) {
            alert("上传失败，请重试");
            // console.log(data);
        }
    })
}