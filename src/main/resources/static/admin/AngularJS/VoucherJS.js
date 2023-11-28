var app = angular.module("voucher", [])
app.controller("voucher-ctrl", function ($scope, $http) {
    const url = "http://localhost:8080/admin/voucher"
    var getUrlWithId = function (id) {
        return url + id;
    }
    const pathName = location.pathname;
    const id = pathName.substring(pathName.lastIndexOf("/"))

    $http.get("http://localhost:8080/admin/khach-hang/khach-hang-voucher").then(function (res) {
        $scope.findAllKhachHang = res.data
    }).catch(err => console.log(err))

    $scope.voucherAdd = {}
    $scope.voucherAdd.hinhThucThanhToan = 0
    $scope.voucherAdd.loaiMucGiam = "TIEN"

    //    chi tiet
    $scope.findById = function (id) {
        var urlWithId = getUrlWithId(id);
        $http.get(urlWithId).then(function (res) {
            const voucher = res.data;
            $scope.voucherAdd.moTa = voucher.moTa;
            $scope.voucherAdd.loaiMucGiam = voucher.loaiMucGiam;
            $scope.voucherAdd.mucGiam = voucher.mucGiam;
            $scope.voucherAdd.mucGiamToiDa = voucher.mucGiamToiDa;
            $scope.voucherAdd.giaTriDonHang = voucher.giaTriDonHang;
            $scope.voucherAdd.soLuong = voucher.soLuong;
            $scope.voucherAdd.ngayBatDau = voucher.ngayBatDau;
            $scope.voucherAdd.ngayBatKetThuc = voucher.ngayKetThuc;
        });
    }
    $scope.findById(id)
    //delete
    $scope.delete = function (id) {
        var urlWithId = getUrlWithId(id)
        $http.delete(urlWithId).then(function (res) {
            alert("Delete success");
            location.reload();
        })
    }

    $scope.danhSachKhach = []
    $scope.selectKhach = function (id) {
        var index = $scope.danhSachKhach.indexOf(id);
        if (index > -1) {
            $scope.danhSachKhach.splice(index, 1);
        } else {
            $scope.danhSachKhach.push(id);
        }
    }

    //add
    $scope.create = function () {
        console.log($scope.danhSachKhach)
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

        }).catch(error => {
            $scope.erMoTa = error.data.moTa
            $scope.erMucGiam = error.data.mucGiam
            $scope.erMucGiamToiDa = error.data.mucGiamToiDa
            $scope.erGiaTriDonHang = error.data.giaTriDonHang
            $scope.erNgayBatDau = error.data.ngayBatDau
            $scope.erNgayKetThuc = error.data.ngayKetThuc
            $scope.erSoLuong = error.data.soLuong
            $scope.erKhachHang = error.data.khachHang
            $scope.erdoiTuongSuDung = error.data.doiTuongSuDung
        })
    }
//    updateTrangThai
//     $scope.updateTrangThai = function (id){
//         var urlWithId = getUrlWithId(id)
//         var trangThai =

    // }
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

