var app = angular.module("xuat-xu",[])
app.controller("xuat-xu-ctrl",function ($scope,$http){
    $scope.form = {}
    $scope.items = []

    $scope.findAll = function (){
        $http.get("/admin/xuat-xu/find-all").then(resp => {
            console.log(resp.data)
            $scope.items = resp.data;
        }).catch(error =>{
            console.log(error)
        })
    }
    $scope.findAll();

    $scope.getXuatXu = function (id){
        var url = "/admin/xuat-xu/chiTiet" +"/"+ id;
        $http.get(url).then(function (r){
            console.log(r.data)
            let mauSac = r.data;
            $scope.id = mauSac.id;
            $scope.ten = mauSac.ten;
            $scope.ngayTao = mauSac.ngayTao;
            $scope.ngayCapNhat = mauSac.ngayCapNhat;
        })
    }

    $scope.delete = function (id){
        var url = "/admin/xuat-xu/delete" +"/"+ id;
        $http.delete(url).then(function (r){
            alert("Dlete thành công!!!")
            location.reload();
        })
    }

    $scope.create = function (){
        var xuatXu = {
            id: $scope.id,
            ten: $scope.ten,
            ngayTao: $scope.ngayTao,
            ngayCapNhat: $scope.ngayCapNhat
        }
        $http.post("/admin/xuat-xu/add",xuatXu).then(r => {
            location.reload();
            alert("Thêm thành công")
        })
    }

    $scope.update = function (ma) {
        var url = "/admin/xuat-xu/update" +"/"+ ma;
        var updateMau = {
            id: $scope.id,
            ten: $scope.ten,
            ngayTao: $scope.ngayTao,
            ngayCapNhat: $scope.ngayCapNhat
        }
        $http.put(url,updateMau).then(function (r){
            location.reload();
            alert("Update thành công")
        })
    }

})