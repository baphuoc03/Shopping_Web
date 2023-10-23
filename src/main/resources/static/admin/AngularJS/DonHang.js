var app = angular.module("donhang-app", [])
app.controller("donhang-ctrl", function ($scope, $http){
    $scope.chuaXacNhan = [];
    $scope.daXacNhan = [];

    $http.get("/admin/don-hang/chua-xac-nhan").then(r => {
        $scope.chuaXacNhan = r.data;
    })
    $http.get("/admin/don-hang/xac-nhan").then(r => {
        $scope.daXacNhan = r.data;
    })
})