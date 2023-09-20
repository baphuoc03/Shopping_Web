var app = angular.module('nguoidung-admin', []);
app.controller('ctrl', function ($scope, $http) {

    $scope.nhanVien = [];
    $scope.nhanVienDetail = {};

    $http.get("/admin/tai-khoan/get-all-khach-hang").then(r =>{
        $scope.nhanVien = r.data;
    }).catch(e => console.log(e))

    $scope.detail = function (id){
        $http.get("/admin/tai-khoan/detail/"+id).then(r => {
            $scope.nhanVienDetail = r.data;
            console.log($scope.nhanVienDetail)
        }).catch(e => console.log(e))
    }
})