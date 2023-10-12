var app = angular.module("thongTinNhanVien-app", [])
app.controller("thongTinNhanVien-ctrl", function ($scope, $http) {
    $scope.user = {};
    const labelAddImg = '<label for="pro-image" id="labelAddImg" class="addImg d-flex align-items-center justify-content-center" >' +
        '<i class="bx bxs-image-add"></i>' +
        '</label>'

    $http.get("/admin/nhan-vien/getUser").then(r => {
        $scope.user = r.data
        $scope.user.ngaySinh = new Date(r.data.ngaySinh)
        if ($scope.user.anhDaiDien == null) $(".preview-images-zone").append(labelAddImg);
        else {
            let imgUser = new DataTransfer();
            imgUser.items.add(new File([$scope.user.username],$scope.user.username,{
                lastModified:new Date()
            }))
            document.getElementById("pro-image").files = imgUser.files;
        };
        document.getElementById("gioiTinh").value = $scope.user.gioiTinh
        console.log(r.data)
    }).catch(e => console.log(e))

    $scope.update = function () {
        let anhDaiDien = document.getElementById("pro-image").files.length == 0 ? null : document.getElementById("pro-image").files[0];
        let formData = new FormData();
        formData.append("nhanVien",new Blob([JSON.stringify($scope.user)], {
            type: 'application/json'
        }))
        formData.append("img",anhDaiDien)
        $http.put("/admin/nhan-vien?img=", formData,{
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).then(r => {
            alert("Cập nhật thành công")
            console.log(r.data)
        }).catch(e => {
            alert("Cập nhật thất bại")
            console.log(e)
        })
    }
    $scope.removeFile = function (id) {
        document.getElementById("pro-image").files = new DataTransfer().files
        $(".preview-images-zone").append(labelAddImg);
    }
    $scope.addFile = function () {
        document.getElementById("labelAddImg").remove()
    }

})