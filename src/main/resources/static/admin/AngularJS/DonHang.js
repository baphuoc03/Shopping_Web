var app = angular.module("donhang-app", [])
app.controller("donhang-ctrl", function ($scope, $http) {
    $scope.donHang = {}
    $scope.chiTietDonHang = []
    const limit = 10;
    $scope.closeModal = function (id) {
        $(id).modal('hide')
        $scope.donHang = {}
    }
    $("#chuaXacNhanDetail").on('hide.bs.modal', function () {
        $scope.donHangChuaXacNhan = {}
        $scope.chiTietDonHang.length = 0
    });
    $("#donHangDetail").on('hide.bs.modal', function () {
        $scope.donHang = {}
        $scope.chiTietDonHang.length = 0
    });

    $scope.getTotalPrice = function () {
        let total = 0;
        $scope.chiTietDonHang.forEach(c => total += (c.donGiaSauGiam * c.soLuong))
        return total
    }

    ///////////////////////
    $scope.updateTrangThaiDonHang = function (ma, trangThai) {
        let success = true;
        $http.get("/admin/don-hang/update-trang-thai/" + ma + "?trangThai=" + trangThai).then(r => {
            success = true;
        }).catch(e => {
            console.log(e)
            success = false
        })
        return success
    }
    ////////////////////////////////////////////
    $scope.giaoHangNhanh = {
        tokenShop: "954c787d-2876-11ee-96dc-de6f804954c9",
        headers: {headers: {token: "954c787d-2876-11ee-96dc-de6f804954c9"}},
        headersShop: {headers: {token: "954c787d-2876-11ee-96dc-de6f804954c9", ShopId: 4379549}},
        districts: [],
        wards: [],
        getCitys() {
            $http.get("https://online-gateway.ghn.vn/shiip/public-api/master-data/province", this.headers).then(res => {
                this.citys = res.data.data
            }).catch(err => console.log(err))
        },
        getDistricts(idCity) {
            $http.get("https://online-gateway.ghn.vn/shiip/public-api/master-data/district?province_id=" + idCity, this.headers).then(res => {
                this.districts = res.data.data;
            }).catch(err => console.log(err))
        },
        getWards(idDistrict) {
            $http.get("https://online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id=" + idDistrict, this.headers).then(res => {
                this.wards = res.data.data;
            }).catch(err => console.log(err))
        },
        cityChange(idCity) {
            $http.get("https://online-gateway.ghn.vn/shiip/public-api/master-data/district?province_id=" + idCity, this.headers).then(res => {
                this.districts = res.data.data;
            }).catch(err => console.log(err))
            // $scope.chuaXacNhan.detail.quanHuyenCode = this.districts[0].DistrictID +""
        },
        districtChange(idDistrict) {
            $http.get("https://online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id=" + idDistrict, this.headers).then(res => {
                this.wards = res.data.data;
            }).catch(err => console.log(err))
        },
        getFeeShipped() {
            let data = {
                "service_type_id": 2,
                "to_district_id": parseInt($scope.chuaXacNhan.detail.quanHuyenCode),
                "to_ward_code": $scope.chuaXacNhan.detail.xaPhuongCode,
                "height": 10,
                "length": 10,
                "weight": 200,
                "width": 10
            }
            $http.post("https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee", data, this.headersShop).then(res => {
                $scope.chuaXacNhan.detail.phiGiaoHang = res.data.data.total;
            }).catch(err => console.log(err));
        }
    }
    $scope.giaoHangNhanh.getCitys()
    ////////////////////////////////////////////
    $scope.chuaXacNhan = {
        list: [],
        detail: {},
        totalElement: 0,
        totalPage: 0,
        page: 0,
        id : "",
        pages: [],
        init(pageNumber) {
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=2&pageNumber=" + pageNumber).then(r => {
                this.list = r.data.content;
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        getList(pageNumber) {
            this.page = pageNumber;
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=2&pageNumber=" + pageNumber).then(r => {
                this.list = r.data.content;
            })
        },
        xacNhanDH(ma) {
            if (!confirm("Xác nhận đơn hàng?")) return;

            $http.get("/admin/don-hang/update-trang-thai/" + ma + "?trangThai=1").then(r => {
                if (this.page == this.totalPage - 1) {
                    if (this.list.length == 1 && this.page > 0) {
                        this.page--;
                    }
                }
                this.init(this.page)
            }).catch(e => {
                console.log(e)
            })
        },
        setIdDonHang(id){
            this.id = id
        },
        huyDH() {

            if($scope.lyDo==null || $scope.length==0 || $scope.lyDo == undefined){
                $scope.messLyDo = "Không để trống lý do hủy"
                return
            }else if($scope.lyDo.length==200){
                $scope.messLyDo = "Lý do hủy chỉ tối đa 200 ký tự"
                return;
            }

            $http.get("/admin/don-hang/huy-don-hang/" + this.id+"?lyDo="+$scope.lyDo).then(r => {
                if (this.page == this.totalPage - 1) {
                    if (this.list.length == 1 && this.page > 0) {
                        this.page--;
                    }
                }
                this.init(this.page)
                $scope.lyDo = "";
                $scope.messLyDo ="";
                $('#lyDo').modal('hide')
            }).catch(e => {
                console.log(e)
            })
        },
        getDetail(ma) {
            $http.get("/admin/don-hang/" + ma).then(r => {
                this.detail = r.data;
                this.detail.thanhPhoCode = this.detail.thanhPhoCode + ""

                //Lấy quận huyện
                $scope.giaoHangNhanh.getDistricts(this.detail.thanhPhoCode)
                this.detail.quanHuyenCode = this.detail.quanHuyenCode + "" // set selected quận huyện

                $scope.giaoHangNhanh.getWards(this.detail.quanHuyenCode)
                this.detail.xaPhuongCode = this.detail.xaPhuongCode + ""

                $('#chuaXacNhanDetail').modal('show')
            }).catch(e => console.log(e))

            $http.get("/admin/chi-tiet-don-hang/" + ma).then(r => {
                $scope.chiTietDonHang = r.data;
            }).catch(e => console.log(e))
        },
        capNhat() {
            if (!confirm("Cập nhật thông tin đơn hàng " + this.detail.ma + "?")) {
                return
            }
            let indexCity = $scope.giaoHangNhanh.citys.findIndex(c => c.ProvinceID == this.detail.thanhPhoCode)
            let indexDistrict = $scope.giaoHangNhanh.districts.findIndex(d => d.DistrictID == this.detail.quanHuyenCode)
            let indexWard = $scope.giaoHangNhanh.wards.findIndex(w => w.WardCode == this.detail.xaPhuongCode)

            this.detail.thanhPhoName = $scope.giaoHangNhanh.citys[indexCity].ProvinceName;
            this.detail.quanHuyenName = $scope.giaoHangNhanh.districts[indexDistrict].DistrictName;
            this.detail.xaPhuongName = $scope.giaoHangNhanh.wards[indexWard].WardName
            let data = {
                ma: this.detail.ma,
                nguoiSoHuu: {username: this.detail.nguoiSoHuu},
                tenNguoiNhan: this.detail.tenNguoiNhan,
                soDienThoai: this.detail.soDienThoai,
                email: this.detail.email,
                thanhPhoName: this.detail.thanhPhoName,
                thanhPhoCode: this.detail.thanhPhoCode,
                quanHuyenName: this.detail.quanHuyenName,
                quanHuyenCode: this.detail.quanHuyenCode,
                xaPhuongName: this.detail.xaPhuongName,
                xaPhuongCode: this.detail.xaPhuongCode,
                diaChiChiTiet: this.detail.diaChiChiTiet,
                ngayDatHang: this.detail.ngayDatHang,
                trangThai: this.detail.trangThai,
                ghiChu: this.detail.ghiChu,
                tienGiam: this.detail.tienGiam,
                phiGiaoHang: this.detail.phiGiaoHang,
                trangThaiDetail: this.detail.trangThai
            }
            let chiTietDonHang = [];
            $scope.chiTietDonHang.forEach(c => {
                chiTietDonHang.push({
                    id : c.id,
                    donHangID : this.detail.ma,
                    sanPhamCT : c.idChiTietSanPham,
                    soLuong : c.soLuong,
                    donGia : c.donGia,
                    donGiaSauGiam : c.donGiaSauGiam
                })
            })
            let formData = new FormData();
            formData.append("donHang",new Blob([JSON.stringify(data)], {
                type: 'application/json'
            }))
            formData.append("chiTietDonHang",new Blob([JSON.stringify(chiTietDonHang)], {
                type: 'application/json'
            }))
            $http.put("/admin/don-hang", formData,{
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            }).then(r => {
                let index = this.list.findIndex(d => d.ma == this.detail.ma)
                this.list[index] = this.detail
            }).catch(e => console.log(e))

        },
        setPageNumbers() {
            let numbers = [];
            for (let i = 0; i < this.totalPage; i++) {
                numbers.push(i)
            }
            this.pages = numbers;
        },
        updateSoLuong(idCTDH, soLuong) {
            let index = $scope.chiTietDonHang.findIndex(c => c.id == idCTDH)
            let chiTietDonHang = $scope.chiTietDonHang[index]
            $http.get("/admin/san-pham/1/kiem-tra-so-luong/"+chiTietDonHang.idChiTietSanPham+"?soLuong="+soLuong).then(r => {
                $scope.chiTietDonHang[index].soLuong = soLuong
                $scope.getTotalPrice()
            }).catch(e => {
                $http.get("/admin/chi-tiet-don-hang/detail/"+chiTietDonHang.id).then(r => {
                    $scope.chiTietDonHang[index].soLuong = r.data.soLuong
                }).catch(e => console.log(e))
                alert("số lượng đã vượt quá số lượng sản phẩm!")
            })


        },
        subtractSoLuong(idCTDH) {
            let index = $scope.chiTietDonHang.findIndex(c => c.id == idCTDH)
            let chiTietDonHang = $scope.chiTietDonHang[index]
            let soLuong = chiTietDonHang.soLuong - 1


            $http.get("/admin/san-pham/1/kiem-tra-so-luong/"+chiTietDonHang.idChiTietSanPham+"?soLuong="+soLuong).then(r => {
                $scope.chiTietDonHang[index].soLuong = soLuong
            }).catch(e => {
                alert("số lượng sản phẩm đã đạt giá trị tối thiểu")
            })


        },
        addSoLuong(idCTDH) {
            let index = $scope.chiTietDonHang.findIndex(c => c.id == idCTDH)
            let chiTietDonHang = $scope.chiTietDonHang[index]
            let soLuong = chiTietDonHang.soLuong + 1
            $http.get("/admin/san-pham/1/kiem-tra-so-luong/"+chiTietDonHang.idChiTietSanPham+"?soLuong="+soLuong).then(r => {
                $scope.chiTietDonHang[index].soLuong = soLuong
            }).catch(e => {
                alert("số lượng sản phẩm đã đạt giá trị tối ta")
            })

        },
        removeSanPham(idCTDH){
            let index = $scope.chiTietDonHang.findIndex(c => c.id == idCTDH)
            $scope.chiTietDonHang.splice(index,1)
        }
    }
    $scope.chuaXacNhan.init(0)

    $scope.daXacNhan = {
        list: [],
        detail: {},
        totalElement: 0,
        totalPage: 0,
        page: 0,
        pages: [],
        init() {
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=1").then(r => {
                this.list = r.data.content;
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        getList(pageNumber) {
            $scope.daXacNhan.page = pageNumber;
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=1&pageNumber=" + pageNumber).then(r => {
                this.list = r.data.content;
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        getDetail(ma) {
            $http.get("/admin/don-hang/" + ma).then(r => {
                $scope.donHang = r.data;
                $('#donHangDetail').modal('show')
            }).catch(e => console.log(e))

            $http.get("/admin/chi-tiet-don-hang/" + ma).then(r => {
                $scope.chiTietDonHang = r.data;
            }).catch(e => console.log(e))
        },
        setPageNumbers() {
            let numbers = [];
            for (let i = 0; i < this.totalPage; i++) {
                numbers.push(i)
            }
            this.pages = numbers;
        }
    }

    $scope.huy = {
        list: [],
        detail: {},
        totalElement: 0,
        totalPage: 0,
        page: 0,
        pages: [],
        init() {
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=0").then(r => {
                this.list = r.data.content;
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        getList(pageNumber) {
            $scope.daXacNhan.page = pageNumber;
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=0&pageNumber=" + pageNumber).then(r => {
                this.list = r.data.content;
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        getDetail(ma) {
            $http.get("/admin/don-hang/" + ma).then(r => {
                $scope.donHang = r.data;
                $('#donHangDetail').modal('show')
            }).catch(e => console.log(e))

            $http.get("/admin/chi-tiet-don-hang/" + ma).then(r => {
                $scope.chiTietDonHang = r.data;
            }).catch(e => console.log(e))
        },
        setPageNumbers() {
            let numbers = [];
            for (let i = 0; i < this.totalPage; i++) {
                numbers.push(i)
            }
            this.pages = numbers;
        }
    }

    $scope.fillError = function (type, text) {
        let notificationBox = document.querySelector(".notification-box");
        const alerts = {
            info: {
                icon: `<svg class="w-6 h-6 mr-2" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
</svg>`,
                color: "blue-500"
            },
            error: {
                icon: `<svg xmlns="http://www.w3.org/2000/svg" class="w-6 h-6 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
</svg>`,
                color: "red-500"
            },
            warning: {
                icon: `<svg xmlns="http://www.w3.org/2000/svg" class="w-6 h-6 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
</svg>`,
                color: "yellow-500"
            },
            success: {
                icon: `<svg xmlns="http://www.w3.org/2000/svg" class="w-6 h-6 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
</svg>`,
                color: "green-500"
            }
        };
        let component = document.createElement("div");
        component.className = `relative flex items-center bg-${alerts[type].color} text-white text-sm font-bold px-4 py-3 rounded-md opacity-0 transform transition-all duration-500 mb-1`;
        component.innerHTML = `${alerts[type].icon}<p>${text}</p>`;
        notificationBox.appendChild(component);
        setTimeout(() => {
            component.classList.remove("opacity-0");
            component.classList.add("opacity-1");
        }, 1); //1ms For fixing opacity on new element
        setTimeout(() => {
            component.classList.remove("opacity-1");
            component.classList.add("opacity-0");
            //component.classList.add("-translate-y-80"); //it's a little bit buggy when send multiple alerts
            component.style.margin = 0;
            component.style.padding = 0;
        }, 5000);
        setTimeout(() => {
            component.style.setProperty("height", "0", "important");
        }, 5100);
        setTimeout(() => {
            notificationBox.removeChild(component);
        }, 5700);
        //If you can do something more elegant than timeouts, please do, but i can't
    }

})