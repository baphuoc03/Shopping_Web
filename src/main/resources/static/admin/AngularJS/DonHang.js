var app = angular.module("donhang-app", [])
app.controller("donhang-ctrl", function ($scope, $http){
    $scope.chuaXacNhan = [];
    $scope.daXacNhan = [];
    $scope.donHangChuaXacNhan = {}

    $http.get("/admin/don-hang/chua-xac-nhan").then(r => {
        $scope.chuaXacNhan = r.data;
    })
    $http.get("/admin/don-hang/xac-nhan").then(r => {
        $scope.daXacNhan = r.data;
    })

    $scope.detailDonHangChuaXacNhan = function (ma){
        $http.get("/admin/don-hang/"+ma).then(r => {
            $scope.donHangChuaXacNhan = r.data;
            $('#chuaXacNhanDetail').modal('show')
        }).catch(e => console.log(e))
    }

    $scope.closeModal = function (id){
        $(id).modal('hide')


    }
})