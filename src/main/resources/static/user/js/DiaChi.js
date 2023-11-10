var app = angular.module('diaChi', []);
app.controller('diaChiCtrl', function ($scope, $http) {
        const token = "954c787d-2876-11ee-96dc-de6f804954c9";
        const headers = {headers: {token: token}}
        const headersShop = {headers: {token: token, ShopId: 4379549}}

        $scope.citys = []
        $scope.wards = []
        $scope.districts = []
        $scope.voucherDH = ""
        $scope.loginIn = false;
        $scope.isSelectSaveDC = false;

        $scope.getValue = function () {
            $scope.textInner = "Thành Phố: " + $scope.city + "/ Quận huyện: " + $scope.district + " / Xã: " + $scope.xa
        }
        $http.get("https://online-gateway.ghn.vn/shiip/public-api/master-data/province", headers).then(res => {
            $scope.citys = res.data.data
        }).catch(err => console.log(err))
        $scope.districtChange = function (id) {
            $http.get("https://online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id=" + id, headers).then(res => {
                $scope.wards = res.data.data;
                console.log(res.data)
            }).catch(err => console.log(err))
        }
        $scope.cityChange = function (id) {
            if (id.length == 0) {
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
        $scope.create = function () {

            let indexCity = $scope.citys.findIndex(c => c.ProvinceID == $scope.thanhPhoCode)
            let indexDistrict = $scope.districts.findIndex(d => d.DistrictID == $scope.quanHuyenCode)
            let indexWard = $scope.wards.findIndex(w => w.WardCode == $scope.xaPhuongCode)
            document.getElementById("eThanhPho").innerText = "Vui lòng chọn Tỉnh/ Thành Phố"
            document.getElementById("eQuanHuyen").innerText = "Vui lòng chọn Quận/ Huyện"
            document.getElementById("eXaPhuong").innerText = "Vui lòng chọn Xã/ Phường"
            document.getElementById("eDiaChi").innerText = "Vui lòng nhập Địa Chỉ"
            var diaChi = {
                thanhPhoCode: $scope.thanhPhoCode,
                thanhPhoName: $scope.citys[indexCity].ProvinceName,
                quanHuyenCode: $scope.quanHuyenCode,
                quanHuyenName: $scope.districts[indexDistrict].DistrictName,
                xaPhuongCode: $scope.xaPhuongCode,
                xaPhuongName: $scope.wards[indexWard].WardName,
                diaChiChiTiet: $scope.diaChiChiTiet
            }
            $http.post("http://localhost:8080/dia-chi/add", diaChi).then(r => {
                location.reload();
                alert("Thêm Thành Công")
            })
        }
    }
);
