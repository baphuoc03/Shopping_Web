var app = angular.module("donhang-app", [])
app.controller("donhang-ctrl", function ($scope, $http){
    $scope.chuaXacNhan = [];
    $scope.daXacNhan = [];
    $scope.donHangChuaXacNhan = {}
    $scope.donHang = {}
    $scope.chiTietDonHang = []

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

        $http.get("/admin/chi-tiet-don-hang/"+ma).then(r => {
            $scope.chiTietDonHang = r.data;
        }).catch(e => console.log(e))
    }

    $scope.donHangDetail = function (ma){
        $http.get("/admin/don-hang/"+ma).then(r => {
            $scope.donHang = r.data;
            $('#donHangDetail').modal('show')
        }).catch(e => console.log(e))

        $http.get("/admin/chi-tiet-don-hang/"+ma).then(r => {
            $scope.chiTietDonHang = r.data;
        }).catch(e => console.log(e))
    }

    $scope.closeModal = function (id){
        $(id).modal('hide')
        $scope.chiTietDonHang.length = 0
        $scope.donHang = {}
        $scope.donHangChuaXacNhan = {}
    }

    $scope.getTotalPrice = function (){
        let total = 0;
        $scope.chiTietDonHang.forEach(c => total+= c.donGiaSauGiam)
        return total
    }
})