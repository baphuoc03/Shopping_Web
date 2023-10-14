var app = angular.module("formSP-app", [])
app.controller("ctrl", function ($scope, $http) {

    var httpThuocTinh = "";
    var thuocTinhSL = undefined;
    var filesTransfer = new DataTransfer();
    $("#viewAdd").on('hide.bs.modal', function () {
        thuocTinhSL.selectedIndex = 0;
    });

    $scope.viewAddMauSac = function () {
        httpThuocTinh = "/admin/mau-sac/add"
        thuocTinhSL = document.getElementById("mauSac");

        if (thuocTinhSL.selectedIndex == thuocTinhSL.length - 1) {
            $('#viewAdd').modal('show');
        }
    }

    $scope.viewAddDongSP = function () {
        httpThuocTinh = "/admin/dong-san-pham/add"
        thuocTinhSL = document.getElementById("dongSP");

        if (thuocTinhSL.selectedIndex == thuocTinhSL.length - 1) {
            $('#viewAdd').modal('show');
        }
    }

    $scope.viewAddKieuDang = function () {
        httpThuocTinh = "/kieu-dang/add"
        thuocTinhSL = document.getElementById("kieuDang");

        if (thuocTinhSL.selectedIndex == thuocTinhSL.length - 1) {
            $('#viewAdd').modal('show');
        }
    }

    $scope.viewAddChatLieu = function () {
        httpThuocTinh = "/admin/chat-lieu"
        thuocTinhSL = document.getElementById("chatLieu");

        if (thuocTinhSL.selectedIndex == thuocTinhSL.length - 1) {
            $('#viewAdd').modal('show');
        }
    }
    $scope.addThuocTinh = function () {
        $http.post(httpThuocTinh, {ten: $scope.tenThuocTinh}).then(r => {
            var option = document.createElement("option");
            option.text = r.data.ten;
            option.value = r.data.id == undefined ? r.data.ma : r.data.id
            thuocTinhSL.add(option, thuocTinhSL[thuocTinhSL.length - 1]);
            thuocTinhSL.selectedIndex = thuocTinhSL.length - 2;
            $scope.tenThuocTinh = "";
            $('#viewAdd').modal('hide');
        }).catch(e => console.log(e))
    }

    $scope.removeER = function (id) {
        document.getElementById(id).innerText = "";
    }
    $scope.closeModal = function () {
        $('#viewAdd').modal('hide');
    }


    $scope.appendFile = function () {
        let files = document.getElementById("pro-image").files
        files.forEach(f => filesTransfer.items.add(f))
        document.getElementById("pro-image").files = filesTransfer.files
    }
    $scope.removeFile = function (key){
        let index;
        let files1 = new DataTransfer();
        filesTransfer.files.forEach(f => {
            if(f.lastModified!=key){
                files1.items.add(f);
            }
        })

        filesTransfer = files1
        document.getElementById("pro-image").files = filesTransfer.files
    }
    $scope.loadImgProduct = function (fileName){
        const image = new File([fileName], fileName, {
            lastModified: new Date(),
        });
        let buttonCancel = document.getElementById(fileName).getElementsByClassName('image-cancel')
        buttonCancel[0].setAttribute("id", image.lastModified);
        console.log(buttonCancel)

        filesTransfer.items.add(image);
        document.getElementById("pro-image").files = filesTransfer.files
    }
    $scope.sortFiles = function () {
        console.log(document.getElementsByClassName("image-cancel"))
        let files1 = new DataTransfer();
        document.getElementsByClassName("image-cancel").forEach(i => {
            console.log(i)
            filesTransfer.files.forEach(f => {
                if(f.lastModified==i.id){
                    files1.items.add(f);
                }
            })
        })
        filesTransfer = files1
        document.getElementById("pro-image").files = filesTransfer.files

        console.log(filesTransfer.files, document.getElementById("pro-image").files)
    }

})

