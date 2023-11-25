var app = angular.module("voucher", [])
app.controller("voucher-ctrl", function ($scope, $http) {
    const url = "http://localhost:8080/admin/voucher"
    var getUrlWithId = function (id) {
        return url + "/" + id;
    }
    $http.get("http://localhost:8080/admin/khach-hang/khach-hang-voucher").then(function (res) {
        $scope.findAllKhachHang = res.data
    }).catch(err => console.log(err))

    $scope.voucherAdd = {}

    //    chi tiet
    $scope.findById = function (id) {
        var urlWithId = getUrlWithId(id);
        $http.get(urlWithId).then(function (res) {
            const voucher = res.data;
            $scope.ten = voucher.ten;
            $scope.ma = voucher.ma;
            $scope.maConfirm = voucher.ma;
            $scope.loai = voucher.loai;
            $scope.mucGiam = voucher.mucGiam;
            $scope.mucGiamToiDa = voucher.mucGiamToiDa;
            $scope.giaTriToiThieu = voucher.giaTriToiThieu;
            $scope.soLuong = voucher.soLuong;
            $scope.ngayBatDau = voucher.ngayBatDau;
            $scope.ngayKetThuc = voucher.ngayKetThuc;
        });
    }
    //delete
    $scope.delete = function (id) {
        var urlWithId = getUrlWithId(id)
        $http.delete(urlWithId).then(function (res) {
            alert("Delete success");
            location.reload();
        })
    }

    $scope.danhSachKhach = []
    $scope.selectKhach = function (id){
        var index = $scope.danhSachKhach.indexOf(id);
        if (index > -1) {
            $scope.danhSachKhach.splice(index, 1);
        } else {
            $scope.danhSachKhach.push(id);
        }
    }

    //add
    $scope.create = function () {
        console.log( $scope.danhSachKhach)
        let formData = new FormData();
        formData.append("voucher", new Blob([JSON.stringify($scope.voucherAdd)], {
            type: 'application/json'
        }))

        formData.append("idKhach", new Blob([JSON.stringify($scope.danhSachKhach)], {
            type: 'application/json'
        }))
        $http.post(url, formData, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).then(function (response) {

            // location.reload();
            alert("Create success");

        }).catch(error => {
            console.log(error)
            $scope.erTen = error.data.moTa
            $scope.erMucGiam = error.data.mucGiam
            $scope.erMucGiamToiDa = error.data.mucGiamToiDa
            $scope.erMucGiamToiThieu = error.data.giaTriDonHang
            $scope.erNgayBatDau = error.data.ngayBatDau
            $scope.erNgayKetThuc = error.data.ngayKetThuc
            $scope.erSoLuong = error.data.soLuong
            $scope.erdoiTuongSuDung = error.data.doiTuongSuDung
            $scope.erhinhThucThanhToan = error.data. hinhThucThanhToan

        })
    }
//update
    $scope.update = function (id) {
        var urlWithId = getUrlWithId(id)
        var voucher = {
            ten: $scope.ten,
            loai: $scope.loai,
            mucGiam: $scope.mucGiam,
            mucGiamToiDa: $scope.mucGiamToiDa,
            giaTriToiThieu: $scope.giaTriToiThieu,
            ngayBatDau: $scope.ngayBatDau,
            ngayKetThuc: $scope.ngayKetThuc,
            soLuong: $scope.soLuong
        }
        $http.put(urlWithId, voucher).then(function (resp) {
            location.reload();
            alert("Update success");
        })
    }
})
;

