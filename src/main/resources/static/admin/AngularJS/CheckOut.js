var app = angular.module('checkOut', []);
app.controller('checkOutCtrl', function ($scope, $http) {
    const token = "954c787d-2876-11ee-96dc-de6f804954c9";
    const headers = {headers: {token: token}}
    const headersShop = {headers: {token: token, ShopId: 4379549}}
    $scope.sumTotal = new Big(0);
    $scope.feeShipped = new Big(0)
    $scope.giaGiam = new Big(0)
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
    //show chiTietSanPham

    $http.get("/check-out/cart").then(resp => {
        console.log(resp.data)
        $scope.items = resp.data;

        for (var i = 0; i < $scope.items.length; i++) {
            var item = $scope.items[i];
            item.sanPhamDTO.giaBan = new Big(item.sanPhamDTO.giaBan);  // chuyển đổi số thành kiểu big
            var fixedNumber = 2; // Số cố định
            item.total = item.sanPhamDTO.giaBan.times(fixedNumber)
            $scope.sumTotal = $scope.sumTotal.plus(item.total);
            $scope.totalPayment = $scope.sumTotal.plus($scope.feeShipped)
        }
    }).catch(error => {
        console.log(error)
    })

    //show voucher
    $http.get("/check-out/voucher").then(resp => {
        console.log(resp.data)
        $scope.vouchers = resp.data;
    }).catch(error => {
        console.log(error)
    })

//    check-out
    $scope.create = function () {
        var donHang = {
            ten: $scope.ten,
            tenNguoiNhan: $scope.tenNguoiNhan,
            soDienThoai: $scope.soDienThoai,
            email: $scope.email,
            thanhPhoCode: $scope.thanhPhoCode,
            quanHuyenCode: $scope.quanHuyenCode,
            xaPhuongCode: $scope.xaPhuongCode,
            diaChiChiTiet: $scope.diaChiChiTiet,
            ghiChu: $scope.ghiChu,
            phiGiaoHang: $scope.feeShipped,
            tienGiam: $scope.giaGiam
        }
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
        console.log(data)
        $http.post('/using-voucher', data)
            .then(function (response) {
                $scope.giaGiam = response.data;
            })
            .catch(function (error) {
                console.log("lỗi")
            });
    };

});
