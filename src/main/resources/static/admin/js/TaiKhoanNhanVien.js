var app = angular.module('nhanvien-admin', []);
app.controller('ctrl', function ($scope, $http) {

    $scope.nhanVien = [];
    $scope.nhanVienDetail = {};
    $scope.nhanVienAdd = {gioiTinh : null};

    $http.get("/admin/nhan-vien/get-all").then(r =>{
        $scope.nhanVien = r.data;
    }).catch(e => console.log(e))

    $scope.detail = function (id){
        $http.get("/admin/nhan-vien/detail/"+id).then(r => {
            r.data.gioiTinh = JSON.stringify(r.data.gioiTinh)
            $scope.nhanVienDetail = r.data;
            $scope.nhanVienDetail.ngaySinh = new Date(r.data.ngaySinh)
            console.log($scope.nhanVienDetail)
        }).catch(e => console.log(e))
    }
    $scope.update = function (){
        $http.put("/admin/nhan-vien/update",$scope.nhanVienDetail).then(r => {
            let index = $scope.nhanVien.findIndex(n => n.username == $scope.nhanVienDetail.username);
            $scope.nhanVien[index] = $scope.nhanVienDetail
            alert("Cập nhật thành công");
        }).catch(e => {
            document.getElementById("hoVaTenPutER").innerText = e.data.hoVaTen == undefined ? "" : e.data.hoVaTen;
            document.getElementById("soDienThoaiPutER").innerText = e.data.soDienThoai == undefined ? "" : e.data.soDienThoai;
            document.getElementById("vaiTroPutER").innerText = e.data.vaiTro == undefined ? "" : e.data.vaiTro;
            document.getElementById("emailPutER").innerText = e.data.email  == undefined ? "" : e.data.email;
        })
    }
    $scope.add = function (){
        console.log($scope.nhanVienAdd)
        $http.post("/admin/nhan-vien",$scope.nhanVienAdd).then(r => {
            console.log(r.data)
            $scope.nhanVien.push(r.data);
            $scope.nhanVienAdd = {gioiTinh : null};
            $('#viewAdd').modal('hide');
        }).catch(e => {
            document.getElementById("usernameAddER").innerText = e.data.username == undefined ? "" : e.data.username;
            document.getElementById("hoVaTenAddER").innerText = e.data.hoVaTen == undefined ? "" : e.data.hoVaTen;
            document.getElementById("soDienThoaiAddER").innerText = e.data.soDienThoai == undefined ? "" : e.data.soDienThoai;
            document.getElementById("vaiTroAddER").innerText = e.data.vaiTro == undefined ? "" : e.data.vaiTro;
            document.getElementById("emailAddER").innerText = e.data.email  == undefined ? "" : e.data.email;
        })
    }

    $scope.removeErrors = function (id){
        document.getElementById(id).innerText = ""
    }
    $scope.deleteByUsername = function (username){
        if(confirm("Xóa nhân viên "+username)){
            $http.delete("/admin/nhan-vien/"+username).then(r =>{
                let index = $scope.nhanVien.findIndex(n => n.username == username);
                $scope.nhanVien.splice(index,1);
                alert("Xóa thành công");
            }).catch(e => {
                alert("Xóa thất bại!")
            })
        }
    }
})