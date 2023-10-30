var app = angular.module('checkOut', []);
app.controller('checkOutCtrl', function ($scope, $http) {
    const token = "954c787d-2876-11ee-96dc-de6f804954c9";
    const headers = {headers: {token: token}}
    const headersShop = {headers: {token: token, ShopId: 4379549}}
    $scope.feeShipped = 0
    $scope.giaGiam = 0
    $scope.sumTotal = 0
    $scope.vouchers = []
    $scope.citys = []
    $scope.wards = []
    $scope.districts = []
    $scope.voucherDH = ""
    $scope.getValue = function () {
        $scope.textInner = "Thành Phố: " + $scope.city + "/ Quận huyện: " + $scope.district + " / Xã: " + $scope.xa
    }


    $http.get("https://online-gateway.ghn.vn/shiip/public-api/master-data/province", headers).then(res => {
        $scope.citys = res.data.data
    }).catch(err => console.log(err))


    $scope.cityChange = function (id) {
        if (id.length == 0) {
            document.getElementById('feeShipped').value = "";
            document.getElementById('district').length = 0;
            document.getElementById('ward').length = 0;
        } else {
            let data = {province_id: parseInt(id)}
            $http.get("https://online-gateway.ghn.vn/shiip/public-api/master-data/district?province_id=" + id, headers).then(res => {
                $scope.districts = res.data.data;
                $scope.districtChange($scope.districts[0].DistrictID)
            }).catch(err => console.log(err))
        }
    }

    $scope.districtChange = function (id) {
        $http.get("https://online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id=" + id, headers).then(res => {
            $scope.wards = res.data.data;
            console.log(res.data)
        }).catch(err => console.log(err))
    }

    $scope.getFeeShipped = function (idDistrict, wardCode) {
        let data = {
            "service_type_id": 2,
            "to_district_id": parseInt(idDistrict),
            "to_ward_code": wardCode,
            "height": 10,
            "length": 10,
            "weight": 200,
            "width": 10,

        }

        $http.post("https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee", data, headersShop).then(res => {
            console.log(res.data.data);
            $scope.feeShipped = res.data.data.total;
        }).catch(err => console.log(err));

    }

    //show voucher
    $http.get("/check-out/voucher").then(resp => {
        console.log(resp.data)
        $scope.vouchers = resp.data;
    }).catch(error => {
        console.log(error)
    })

//    check-out
    $scope.create = function () {
        let indexCity = $scope.citys.findIndex(c => c.ProvinceID == $scope.thanhPhoCode)
        let indexDistrict = $scope.districts.findIndex(d => d.DistrictID == $scope.quanHuyenCode)
        let indexWard = $scope.wards.findIndex(w => w.WardCode == $scope.xaPhuongCode)

        var donHang = {
            ten: $scope.ten,
            tenNguoiNhan: $scope.tenNguoiNhan,
            soDienThoai: $scope.soDienThoai,
            email: $scope.email,
            phuongThucThanhToan: $scope.phuongThucThanhToan,
            voucher: $scope.voucherDH,
            thanhPhoCode: $scope.thanhPhoCode,
            thanhPhoName: $scope.citys[indexCity].ProvinceName,
            quanHuyenCode: $scope.quanHuyenCode,
            quanHuyenName: $scope.districts[indexDistrict].DistrictName,
            xaPhuongCode: $scope.xaPhuongCode,
            xaPhuongName: $scope.wards[indexWard].WardName,
            diaChiChiTiet: $scope.diaChiChiTiet,
            ghiChu: $scope.ghiChu,
            phiGiaoHang: $scope.feeShipped,
            tienGiam: $scope.giaGiam
        }
        console.log('phương thức thanh toán: ' + $scope.voucherDH)
        $http.post("http://localhost:8080/check-out", donHang).then(r => {
            location.reload();
            alert("Thêm Thành Công")
        })
    }

    $scope.getDataAPI = function (maVC) {
        var data = {
            voucher: maVC,
            tongThanhToan: $scope.sumTotal + 0
        }
        $scope.voucherDH = maVC
        $http.post('/using-voucher', data)
            .then(function (response) {
                $scope.giaGiam = response.data;
            })
            .catch(function (error) {
                console.log("lỗi")
            });
    };

    $http.get("/cart/find-all").then(r => {
        console.log(r.data)
        $scope.cart = r.data;
        for (var i = 0; i < $scope.cart.length; i++) {
            $scope.sumTotal += $scope.cart[i].soLuong * $scope.cart[i].donGiaSauGiam
        }
    }).catch(e => console.log(e))

    $scope.totalpayment = function () {
        var tien = 0;
        tien = ($scope.sumTotal + $scope.feeShipped) - $scope.giaGiam
        return tien
    }
//    disabledVoucher
    $scope.disableVouchers = function () {
        var data = {
            tienHang: $scope.sumTotal
        };

        $http.post('/check-out/disable-voucher', data)
            .then(function (response) {
                var disabledVoucherList = response.data; // Danh sách voucher cần tắt

                // Duyệt qua danh sách vouchers và cập nhật trạng thái disabled
                for (var i = 0; i < $scope.vouchers.length; i++) {
                    var voucher = $scope.vouchers[i];
                    voucher.disabled = disabledVoucherList.includes(voucher.ma);
                }

                $scope.listDisabled = response.data;
            })
            .catch(function (error) {
                console.log("Lỗi");
            });
    };

});
