var app = angular.module("donhang-app", [])
app.controller("donhang-ctrl", function ($scope, $http) {
    $scope.donHang = {}
    $scope.chiTietDonHang = []
    $scope.sanPham = [];
    const limit = 10;
    $scope.er = {}
    $scope.dateNow = new Date().getTime();


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
    $scope.getSanPham = function () {
        $http.get("/admin/san-pham/1/get-all-ctsp").then(r => {
            $scope.sanPham = r.data
        }).catch(e => console.log(e))
    }
    $scope.getSanPham()
    $scope.addChiTietDonHang = function (item) {
        $scope.chiTietDonHang.push({
            sanPham: item.sanPham,
            anh: item.sanPhamDTO.anh.length == 0 ? "default.png" : item.sanPhamDTO.anh[0],
            size: item.size,
            soLuong: 1,
            donGia: item.sanPhamDTO.giaBan,
            donGiaSauGiam: item.sanPhamDTO.giaNiemYet,
            idChiTietSanPham: item.id
        })
        $scope.er.soLuongSP = ""
    }
    $scope.searchSanPham = function () {
        $http.get("/admin/san-pham/1/get-all-ctsp?keyWord=" + $scope.inputProduct).then(r => {
            $scope.sanPham = r.data
        }).catch(e => console.log(e))
    }
    $scope.checkSanPhamInDonHang = function (idCTSP) {
        let result = false;
        $scope.chiTietDonHang.forEach(d => {
            if (d.idChiTietSanPham == idCTSP) {
                result = true;
            }
        })
        return result;
    }

    ///////////////////////////////////////
    ///////Hàm dùng chung
    $scope.id = []
    $scope.trangThaiDonHang = 2
    $scope.huyDH = function () {
        console.log($scope.trangThaiDonHang)
        if ($scope.trangThaiDonHang == 1) {
            $scope.daXacNhan.huyDH();
        } else if ($scope.trangThaiDonHang == 2) {
            $scope.chuaXacNhan.huyDH();
        } else if ($scope.trangThaiDonHang == 3) {
            $scope.dangGiao.huyDH();
        }else if ($scope.trangThaiDonHang == 5) {
            $scope.chuaThanhToan.huyDH();
        }

    }
    /////////////////////Check Box
    $scope.setCheckAll = function (id, name) {
        let setCheckbox = document.getElementById(id)

        let checkBox = document.getElementsByName(name)
        if (setCheckbox.checked == true) {
            checkBox.forEach(c => {
                c.checked = true
            })
        } else {
            checkBox.forEach(c => {
                c.checked = false
            })
        }
        if ($scope.trangThaiDonHang == 1) {
            $scope.daXacNhan.checkButton();
        } else if ($scope.trangThaiDonHang == 2) {
            $scope.chuaXacNhan.checkButton()
        } else if ($scope.trangThaiDonHang == 3) {
            $scope.dangGiao.checkButton();
        }else if ($scope.trangThaiDonHang == 5) {
            $scope.chuaThanhToan.checkButton();
        }
    }
    $scope.checkAllChecked = function (name, idCheckBoxSetAll) {
        let checkBox = document.getElementsByName(name)
        let check = true;
        checkBox.forEach(c => {
            if (c.checked == false) {
                check = false
            }
        })
        document.getElementById(idCheckBoxSetAll).checked = check
        if ($scope.trangThaiDonHang == 1) {
            $scope.daXacNhan.checkButton();
        } else if ($scope.trangThaiDonHang == 2) {
            $scope.chuaXacNhan.checkButton()
        } else if ($scope.trangThaiDonHang == 3) {
            $scope.dangGiao.checkButton();
        }else if ($scope.trangThaiDonHang == 5) {
            $scope.chuaThanhToan.checkButton();
        }
    }

    /////////////////////////////////////////
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
                this.wards.length = 0
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
        id: [],
        pages: [],
        sdtSearch : "",
        init() {
            $scope.trangThaiDonHang = 2
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=2").then(r => {
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })

        },
        getList(pageNumber) {
            $scope.trangThaiDonHang = 2
            this.page = pageNumber;
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=2&pageNumber=" + pageNumber+"&sdt="+this.sdtSearch).then(r => {
                this.list = r.data.content;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        xacNhanDH(ma) {
            alertify.confirm("Xác nhận đơn hàng?", function () {

                $http.get("/admin/don-hang/update-trang-thai/" + ma + "?trangThai=1").then(r => {
                    if ($scope.chuaXacNhan.page == $scope.chuaXacNhan.totalPage - 1) {
                        if ($scope.chuaXacNhan.list.length == 1 && $scope.chuaXacNhan.page > 0) {
                            $scope.chuaXacNhan.page--;
                        }
                    }
                    $scope.chuaXacNhan.getList($scope.chuaXacNhan.page)
                    $scope.chuaXacNhan.init()
                    document.getElementById('checkAllChuaXacNhan').checked = false
                    $scope.daXacNhan.totalElement++
                    alertify.success("Xác nhận thành công")
                }).catch(e => {
                    alertify.error("Xác nhận thất bại")
                    console.log(e)
                })

            }, function () {
                alertify.error("Xác nhận thất bại")
            })
        },
        xacNhanDHAll() {
            alertify.confirm("Xác nhận đơn hàng?", function () {
                let checkBox = document.getElementsByName('checkChuaXacNhan')
                checkBox.forEach(c => {
                    if (c.checked == true) {
                        $scope.chuaXacNhan.id.push(c.value)
                        $scope.daXacNhan.totalElement++
                    }
                })

                $http.put("/admin/don-hang/update-trang-thai?trangThai=1", $scope.chuaXacNhan.id).then(r => {
                    if ($scope.chuaXacNhan.page == $scope.chuaXacNhan.totalPage - 1) {
                        if ($scope.chuaXacNhan.list.length == 1 && $scope.chuaXacNhan.page > 0) {
                            $scope.chuaXacNhan.page--;
                        }
                    }
                    $scope.chuaXacNhan.getList($scope.chuaXacNhan.page)
                    $scope.chuaXacNhan.init()
                    $scope.chuaXacNhan.id = []
                    document.getElementById('checkAllChuaXacNhan').checked = false
                    alertify.success("Xác nhận thành công")
                }).catch(e => {
                    console.log(e)
                    alertify.error("Xác nhận thất bại")
                })
            }, function () {
                alertify.error("Xác nhận thất bại")
            })
        },
        setIdDonHang(id) {
            this.id = []
            this.id.push(id)
        },
        setAllIdDonHang() {
            this.id = []
            let checkBox = document.getElementsByName('checkChuaXacNhan')
            checkBox.forEach(c => {
                if (c.checked == true) {
                    this.id.push(c.value)
                }
            })
        },
        huyDH() {

            if ($scope.lyDo == null || $scope.length == 0 || $scope.lyDo == undefined) {
                $scope.messLyDo = "Không để trống lý do hủy"
                alertify.error("Hủy đơn hàng thất bại")
                return
            } else if ($scope.lyDo.length == 200) {
                $scope.messLyDo = "Lý do hủy chỉ tối đa 200 ký tự"
                alertify.error("Hủy đơn hàng thất bại")
                return;
            }

            $http.put("/admin/don-hang/huy-don-hang?lyDo=" + $scope.lyDo, this.id).then(r => {
                if (this.page == this.totalPage - 1) {
                    if (this.list.length == 1 && this.page > 0) {
                        this.page--;
                    }
                }
                this.getList(this.page)
                this.init()
                $scope.lyDo = null;
                $scope.messLyDo = "";
                this.id = []
                $('#closeHuy').click()
                document.getElementById('checkAllChuaXacNhan').checked = false
                alertify.success("Hủy đơn hàng thành công")
            }).catch(e => {
                alertify.error("Hủy đơn hàng thất bại")
                console.log(e)
            })
        },
        getDetail(ma) {
            $http.get("/admin/don-hang/" + ma).then(r => {
                this.detail = r.data;
                this.detail.thanhPhoCode = this.detail.thanhPhoCode + ""

                //Lấy quận huyện
                $scope.giaoHangNhanh.getDistricts(this.detail.thanhPhoCode)//hàm lấy quận huyện truyền vào thành phố
                this.detail.quanHuyenCode = this.detail.quanHuyenCode + "" // set selected quận huyện

                $scope.giaoHangNhanh.getWards(this.detail.quanHuyenCode)//hàm lấy xã truyền vào quận huyện
                this.detail.xaPhuongCode = this.detail.xaPhuongCode + "" //set selected xã

                $('#chuaXacNhanDetail').modal('show')
            }).catch(e => console.log(e))

            $http.get("/admin/chi-tiet-don-hang/" + ma).then(r => {
                $scope.chiTietDonHang = r.data;
            }).catch(e => console.log(e))
        },
        capNhat() {
            alertify.confirm("Cập nhật đơn hàng?", function () {
                let indexCity = $scope.giaoHangNhanh.citys.findIndex(c => c.ProvinceID == $scope.chuaXacNhan.detail.thanhPhoCode)
                let indexDistrict = $scope.giaoHangNhanh.districts.findIndex(d => d.DistrictID == $scope.chuaXacNhan.detail.quanHuyenCode)
                let indexWard = $scope.giaoHangNhanh.wards.findIndex(w => w.WardCode == $scope.chuaXacNhan.detail.xaPhuongCode)

                $scope.chuaXacNhan.detail.thanhPhoName = $scope.giaoHangNhanh.citys[indexCity] == undefined ? "" : $scope.giaoHangNhanh.citys[indexCity].ProvinceName;
                $scope.chuaXacNhan.detail.quanHuyenName = $scope.giaoHangNhanh.districts[indexDistrict] == undefined ? "" : $scope.giaoHangNhanh.districts[indexDistrict].DistrictName;
                $scope.chuaXacNhan.detail.xaPhuongName = $scope.giaoHangNhanh.wards[indexWard] == undefined ? "" : $scope.giaoHangNhanh.wards[indexWard].WardName == undefined
                let data = {
                    ma: $scope.chuaXacNhan.detail.ma,
                    nguoiSoHuu: {username: $scope.chuaXacNhan.detail.nguoiSoHuu},
                    tenNguoiNhan: $scope.chuaXacNhan.detail.tenNguoiNhan,
                    soDienThoai: $scope.chuaXacNhan.detail.soDienThoai,
                    email: $scope.chuaXacNhan.detail.email,
                    thanhPhoName: $scope.chuaXacNhan.detail.thanhPhoName,
                    thanhPhoCode: $scope.chuaXacNhan.detail.thanhPhoCode,
                    quanHuyenName: $scope.chuaXacNhan.detail.quanHuyenName,
                    quanHuyenCode: $scope.chuaXacNhan.detail.quanHuyenCode,
                    xaPhuongName: $scope.chuaXacNhan.detail.xaPhuongName,
                    xaPhuongCode: $scope.chuaXacNhan.detail.xaPhuongCode,
                    diaChiChiTiet: $scope.chuaXacNhan.detail.diaChiChiTiet,
                    ngayDatHang: $scope.chuaXacNhan.detail.ngayDatHang,
                    trangThai: $scope.chuaXacNhan.detail.trangThai,
                    ghiChu: $scope.chuaXacNhan.detail.ghiChu,
                    tienGiam: $scope.chuaXacNhan.detail.tienGiam,
                    phiGiaoHang: $scope.chuaXacNhan.detail.phiGiaoHang,
                    trangThaiDetail: $scope.chuaXacNhan.detail.trangThai,
                    phuongThucThanhToan : $scope.chuaXacNhan.detail.phuongThucThanhToan =='true' ? 0 : 1
                }
                let chiTietDonHang = [];
                $scope.chiTietDonHang.forEach(c => {
                    chiTietDonHang.push({
                        id: c.id,
                        donHangID: $scope.chuaXacNhan.detail.ma,
                        sanPhamCT: c.idChiTietSanPham,
                        soLuong: c.soLuong,
                        donGia: c.donGia,
                        donGiaSauGiam: c.donGiaSauGiam
                    })
                })
                let formData = new FormData();
                formData.append("donHang", new Blob([JSON.stringify(data)], {
                    type: 'application/json'
                }))
                formData.append("chiTietDonHang", new Blob([JSON.stringify(chiTietDonHang)], {
                    type: 'application/json'
                }))
                $http.put("/admin/don-hang", formData, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                }).then(r => {
                    let index = $scope.chuaXacNhan.list.findIndex(d => d.ma == $scope.chuaXacNhan.detail.ma)
                    $scope.chuaXacNhan.list[index] = $scope.chuaXacNhan.detail
                    alertify.success("Cập nhật thành công")
                    $scope.chuaThanhToan.init()
                    $scope.chuaXacNhan.init()
                    $scope.chuaXacNhan.getList($scope.chuaXacNhan.page)
                    $scope.chuaThanhToan.getList($scope.chuaThanhToan.page)
                }).catch(e => {
                    $scope.er = e.data
                    console.log(e)
                    alertify.error("Cập nhật thất bại")
                })
            }, function () {
                alertify.error("Cập nhật thất bại")
            })

        },
        setPageNumbers() {
            let numbers = [];
            let i = this.page
            let lengthLast = this.totalPage <= 3 ? this.totalPage : this.page + 3
            let lengthFirst = this.totalPage >=2 ?  this.page - 2 : 0

            if(lengthLast>this.totalPage){
                lengthLast = this.totalPage
                i = lengthLast - 2
            }
            if(lengthFirst < 0) lengthFirst = 0

            for (lengthFirst; i > lengthFirst; lengthFirst++) {
                numbers.push(lengthFirst)
            }
            for (i; i < lengthLast; i++) {
                numbers.push(i)
            }
            this.pages = numbers;
        },
        updateSoLuong(idCTSP, soLuong) {
            let index = $scope.chiTietDonHang.findIndex(c => c.idChiTietSanPham == idCTSP)
            let chiTietDonHang = $scope.chiTietDonHang[index]
            $http.get("/admin/san-pham/1/kiem-tra-so-luong/" + idCTSP + "?soLuong=" + soLuong + "&idCTDH=" + (chiTietDonHang.id == undefined ? "" : chiTietDonHang.id)).then(r => {
                $scope.chiTietDonHang[index].soLuong = soLuong
                $scope.getTotalPrice()
            }).catch(e => {
                if (chiTietDonHang.id == undefined) {
                    chiTietDonHang.soLuong = 1
                } else {
                    $http.get("/admin/chi-tiet-don-hang/detail/" + chiTietDonHang.id).then(r => {
                        $scope.chiTietDonHang[index].soLuong = r.data.soLuong
                    }).catch(e => console.log(e))
                }
                alertify.error("số lượng đã vượt quá số lượng sản phẩm!")
            })


        },
        subtractSoLuong(idCTSP) {
            let index = $scope.chiTietDonHang.findIndex(c => c.idChiTietSanPham == idCTSP)
            let chiTietDonHang = $scope.chiTietDonHang[index]
            let soLuong = chiTietDonHang.soLuong - 1


            $http.get("/admin/san-pham/1/kiem-tra-so-luong/" + chiTietDonHang.idChiTietSanPham + "?soLuong=" + soLuong + "&idCTDH=" + "").then(r => {
                $scope.chiTietDonHang[index].soLuong = soLuong
            }).catch(e => {
                alertify.error("số lượng sản phẩm đã đạt giá trị tối thiểu")
            })


        },
        addSoLuong(idCTSP) {
            let index = $scope.chiTietDonHang.findIndex(c => c.idChiTietSanPham == idCTSP)
            let chiTietDonHang = $scope.chiTietDonHang[index]
            let soLuong = chiTietDonHang.soLuong + 1

            $http.get("/admin/san-pham/1/kiem-tra-so-luong/" + chiTietDonHang.idChiTietSanPham + "?soLuong=" + soLuong + "&idCTDH=" + (chiTietDonHang.id == undefined ? "" : chiTietDonHang.id)).then(r => {
                $scope.chiTietDonHang[index].soLuong = soLuong
            }).catch(e => {
                alertify.error("số lượng sản phẩm đã đạt giá trị tối đa")
            })

        },
        removeSanPham(idCTDH) {
            let index = $scope.chiTietDonHang.findIndex(c => c.id == idCTDH)
            $scope.chiTietDonHang.splice(index, 1)
        },
        checkButton() {
            let checkboxs = document.getElementsByName('checkChuaXacNhan')
            let check = true;
            checkboxs.forEach(c => {
                if (c.checked == true) {
                    check = false;
                }
            })
            document.getElementById("huyAll1").disabled = check;
            document.getElementById("xacNhanAll1").disabled = check;
        }
    }
    $scope.chuaXacNhan.init()
    $scope.chuaXacNhan.getList(0)

    $scope.chuaThanhToan = {
        list: [],
        detail: {},
        totalElement: 0,
        totalPage: 0,
        page: 0,
        id: [],
        pages: [],
        sdtSearch : "",
        init() {
            $scope.trangThaiDonHang = 5
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=5").then(r => {
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        getList(pageNumber) {
            $scope.trangThaiDonHang = 5
            this.page = pageNumber;
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=5&pageNumber=" + pageNumber+"&sdt="+this.sdtSearch).then(r => {
                this.list = r.data.content;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        xacNhanDH(ma) {
            alertify.confirm("Xác nhận thanh toán đơn hàng?", function () {

                $http.get("/admin/don-hang/update-trang-thai/" + ma + "?trangThai=2").then(r => {
                    if ($scope.chuaThanhToan.page == $scope.chuaThanhToan.totalPage - 1) {
                        if ($scope.chuaThanhToan.list.length == 1 && $scope.chuaThanhToan.page > 0) {
                            $scope.chuaThanhToan.page--;
                        }
                    }
                    $scope.chuaThanhToan.getList($scope.chuaThanhToan.page)
                    $scope.chuaThanhToan.init()
                    document.getElementById('checkAllChuaTT').checked = false
                    $scope.chuaXacNhan.totalElement++
                    alertify.success("Xác nhận thanh toán thành công")
                }).catch(e => {
                    console.log(e)
                    alertify.error("Xác nhận thanh toán thất bại")
                })
            }, function () {
                alertify.error("Xóa thất bại")
            })
        },
        xacNhanDHAll() {
            alertify.confirm("Xác nhận thanh toán đơn hàng?", function () {
                let checkBox = document.getElementsByName('checkChuaTT')
                checkBox.forEach(c => {
                    if (c.checked == true) {
                        $scope.chuaThanhToan.id.push(c.value)
                        $scope.chuaXacNhan.totalElement++
                    }
                })

                $http.put("/admin/don-hang/update-trang-thai?trangThai=2", $scope.chuaThanhToan.id).then(r => {
                    if ($scope.chuaThanhToan.page == $scope.chuaThanhToan.totalPage - 1) {
                        if ($scope.chuaThanhToan.list.length == 1 && $scope.chuaThanhToan.page > 0) {
                            $scope.chuaThanhToan.page--;
                        }
                    }
                    $scope.chuaThanhToan.getList($scope.chuaThanhToan.page)
                    $scope.chuaThanhToan.init()
                    $scope.chuaThanhToan.id = []
                    document.getElementById('checkAllChuaTT').checked = false
                    alertify.success("Xác nhận thanh toán thành công")
                }).catch(e => {
                    console.log(e)
                    alertify.error("Xác nhận thanh toán thất bại")
                })
            }, function () {
                alertify.error("Xóa thất bại")
            })
        },
        setIdDonHang(id) {
            this.id = []
            this.id.push(id)
        },
        setAllIdDonHang() {
            this.id = []
            let checkBox = document.getElementsByName('checkChuaTT')
            checkBox.forEach(c => {
                if (c.checked == true) {
                    this.id.push(c.value)
                }
            })
        },
        huyDH() {

            if ($scope.lyDo == null || $scope.length == 0 || $scope.lyDo == undefined) {
                $scope.messLyDo = "Không để trống lý do hủy"
                alertify.error("Xác nhận thanh toán thất bại")
                return
            } else if ($scope.lyDo.length == 200) {
                $scope.messLyDo = "Lý do hủy chỉ tối đa 200 ký tự"
                alertify.error("Xác nhận thanh toán thất bại")
                return;
            }

            $http.put("/admin/don-hang/huy-don-hang?lyDo=" + $scope.lyDo, this.id).then(r => {
                if (this.page == this.totalPage - 1) {
                    if (this.list.length == 1 && this.page > 0) {
                        this.page--;
                    }
                }
                this.getList(this.page)
                this.init()
                $scope.lyDo = null;
                $scope.messLyDo = "";
                this.id = []
                $('#closeHuy').click()
                document.getElementById('checkAllChuaTT').checked = false
                alertify.success("Xác nhận thanh toán thành công")
            }).catch(e => {
                console.log(e)
                alertify.error("Xác nhận thanh toán thất bại")
            })
        },
        getDetail(ma) {
            $http.get("/admin/don-hang/" + ma).then(r => {
                this.detail = r.data;
                $scope.chuaXacNhan.detail = r.data
                this.detail.thanhPhoCode = this.detail.thanhPhoCode + ""

                //Lấy quận huyện
                $scope.giaoHangNhanh.getDistricts(this.detail.thanhPhoCode)//hàm lấy quận huyện truyền vào thành phố
                this.detail.quanHuyenCode = this.detail.quanHuyenCode + "" // set selected quận huyện

                $scope.giaoHangNhanh.getWards(this.detail.quanHuyenCode)//hàm lấy xã truyền vào quận huyện
                this.detail.xaPhuongCode = this.detail.xaPhuongCode + "" //set selected xã

                $('#chuaXacNhanDetail').modal('show')
            }).catch(e => console.log(e))

            $http.get("/admin/chi-tiet-don-hang/" + ma).then(r => {
                $scope.chiTietDonHang = r.data;
            }).catch(e => console.log(e))
        },
        setPageNumbers() {

            let numbers = [];
            let i = this.page
            let lengthLast = this.totalPage <= 3 ? this.totalPage : this.page + 3
            let lengthFirst = this.totalPage >=2 ?  this.page - 2 : 0

            if(lengthLast>this.totalPage){
                lengthLast = this.totalPage
                i = lengthLast - 2
            }
            if(lengthFirst < 0) lengthFirst = 0

            for (lengthFirst; i > lengthFirst; lengthFirst++) {
                numbers.push(lengthFirst)
            }
            for (i; i < lengthLast; i++) {
                numbers.push(i)
            }
            this.pages = numbers;

        },
        checkButton() {
            let checkboxs = document.getElementsByName('checkChuaTT')
            let check = true;
            checkboxs.forEach(c => {
                if (c.checked == true) {
                    check = false;
                }
            })
            document.getElementById("huyAll5").disabled = check;
            document.getElementById("xacNhanAll5").disabled = check;
        }
    }
    $scope.chuaThanhToan.init()

    $scope.daXacNhan = {
        list: [],
        detail: {},
        totalElement: 0,
        totalPage: 0,
        page: 0,
        pages: [],
        id: [],
        sdtSearch : "",
        init() {
            $scope.trangThaiDonHang = 1
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=1").then(r => {
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        getList(pageNumber) {

            $scope.daXacNhan.page = pageNumber;
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=1&pageNumber=" + pageNumber+"&sdt="+this.sdtSearch).then(r => {
                this.list = r.data.content;
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
            let i = this.page
            let lengthLast = this.totalPage <= 3 ? this.totalPage : this.page + 3
            let lengthFirst = this.totalPage >=2 ?  this.page - 2 : 0

            if(lengthLast>this.totalPage){
                lengthLast = this.totalPage
                i = lengthLast - 2
            }
            if(lengthFirst < 0) lengthFirst = 0

            for (lengthFirst; i > lengthFirst; lengthFirst++) {
                numbers.push(lengthFirst)
            }
            for (i; i < lengthLast; i++) {
                numbers.push(i)
            }
            this.pages = numbers;
        },
        chuyenGiao(ma) {
            alertify.confirm("Chuyển giao đơn hàng?", function () {

                $http.get("/admin/don-hang/update-trang-thai/" + ma + "?trangThai=3").then(r => {
                    if ($scope.daXacNhan.page == $scope.daXacNhan.totalPage - 1) {
                        if ($scope.daXacNhan.list.length == 1 && $scope.daXacNhan.page > 0) {
                            $scope.daXacNhan.page--;
                        }
                    }
                    $scope.daXacNhan.getList($scope.daXacNhan.page)
                    $scope.daXacNhan.init()
                    // document.getElementById('checkAllChuaXacNhan').checked = false
                    $scope.dangGiao.totalElement++
                    alertify.success("Chuyển giao thành công")
                }).catch(e => {
                    console.log(e)
                    alertify.error("Chuyển giao thất bại")
                })
            }, function () {
                alertify.error("Chuyển giao thất bại")
            })
        },
        setIdDonHang(id) {
            this.id = []
            this.id.push(id)
        },
        setAllIdDonHang() {
            this.id = []
            let checkBox = document.getElementsByName('checkDaXacNhan')
            checkBox.forEach(c => {
                if (c.checked == true) {
                    this.id.push(c.value)
                }
            })
        },
        huyDH() {
            if ($scope.lyDo == null || $scope.length == 0 || $scope.lyDo == undefined) {
                $scope.messLyDo = "Không để trống lý do hủy"
                return
            } else if ($scope.lyDo.length == 200) {
                $scope.messLyDo = "Lý do hủy chỉ tối đa 200 ký tự"
                return;
            }

            $http.put("/admin/don-hang/huy-don-hang?lyDo=" + $scope.lyDo, this.id).then(r => {
                if (this.page == this.totalPage - 1) {
                    if (this.list.length == 1 && this.page > 0) {
                        this.page--;
                    }
                }
                this.getList(this.page)
                this.init()
                $scope.lyDo = null;
                $scope.messLyDo = "";
                this.id = []
                $('#closeHuy').click()
                document.getElementById('checkAlldaXacNhan').checked = false
                alertify.success("Hủy đơn hàng thành công")
            }).catch(e => {
                console.log(e)
                alertify.error("Hủy đơn hàng thất bại")
            })
        },
        chuyenGiaoDHAll() {

            alertify.confirm("Chuyển giao đơn hàng?", function () {
                let checkBox = document.getElementsByName('checkDaXacNhan')
                checkBox.forEach(c => {
                    if (c.checked == true) {
                        $scope.daXacNhan.id.push(c.value)
                        $scope.dangGiao.totalElement++
                    }
                })

                $http.put("/admin/don-hang/update-trang-thai?trangThai=3", $scope.daXacNhan.id).then(r => {
                    if ($scope.daXacNhan.page == $scope.daXacNhan.totalPage - 1) {
                        if ($scope.daXacNhan.list.length == 1 && $scope.daXacNhan.page > 0) {
                            $scope.daXacNhan.page--;
                        }
                    }
                    $scope.daXacNhan.getList($scope.daXacNhan.page)
                    $scope.daXacNhan.init()
                    $scope.daXacNhan.id = []
                    document.getElementById('checkAlldaXacNhan').checked = false

                    alertify.success("Chuyển giao thành công")
                }).catch(e => {
                    console.log(e)
                    alertify.error("Chuyển giao thất bại")
                })
            }, function () {
                alertify.error("Chuyển giao thất bại")
            })
        },
        checkButton() {
            let checkboxs = document.getElementsByName('checkDaXacNhan')
            let check = true;
            checkboxs.forEach(c => {
                if (c.checked == true) {
                    check = false;
                }
            })
            document.getElementById("huyAll2").disabled = check;
            document.getElementById("xacNhanAll2").disabled = check;
        }
    }
    $scope.daXacNhan.init()

    $scope.dangGiao = {
        list: [],
        detail: {},
        totalElement: 0,
        totalPage: 0,
        page: 0,
        pages: [],
        id: [],
        sdtSearch : "",
        init() {
            $scope.trangThaiDonHang = 3
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=3").then(r => {
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        getList(pageNumber) {
            $scope.trangThaiDonHang = 3
            this.page = pageNumber;
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=3&pageNumber=" + pageNumber+"&sdt="+this.sdtSearch).then(r => {
                this.list = r.data.content;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        setPageNumbers() {

            let numbers = [];
            let i = this.page
            let lengthLast = this.totalPage <= 3 ? this.totalPage : this.page + 3
            let lengthFirst = this.totalPage >=2 ?  this.page - 2 : 0

            if(lengthLast>this.totalPage){
                lengthLast = this.totalPage
                i = lengthLast - 2
            }
            if(lengthFirst < 0) lengthFirst = 0

            for (lengthFirst; i > lengthFirst; lengthFirst++) {
                numbers.push(lengthFirst)
            }
            for (i; i < lengthLast; i++) {
                numbers.push(i)
            }
            this.pages = numbers;
        },
        hoanThanh(ma) {
            alertify.confirm("Xác nhận đơn hàng hoàn thành?", function () {

                $http.get("/admin/don-hang/update-trang-thai/" + ma + "?trangThai=4").then(r => {
                    if ($scope.dangGiao.page == $scope.dangGiao.totalPage - 1) {
                        if ($scope.dangGiao.list.length == 1 && $scope.dangGiao.page > 0) {
                            $scope.dangGiao.page--;
                        }
                    }
                    $scope.dangGiao.getList($scope.dangGiao.page)
                    $scope.dangGiao.init()
                    // document.getElementById('checkAllChuaXacNhan').checked = false
                    alertify.success("Xác nhân đơn hàng hoàn thành thành công")
                }).catch(e => {
                    console.log(e)
                    alertify.error("Xác nhận đơn hàng hoàn thành thất bại")
                })
            }, function () {
                alertify.error("Xác nhận đơn hàng hoàn thành thất bại")
            })
        },
        setIdDonHang(id) {
            this.id = []
            this.id.push(id)
        },
        setAllIdDonHang() {
            let checkBox = document.getElementsByName('checkDangGiao')
            this.id = []
            checkBox.forEach(c => {
                if (c.checked == true) {
                    this.id.push(c.value)
                }
            })
        },
        huyDH() {
            if ($scope.lyDo == null || $scope.length == 0 || $scope.lyDo == undefined) {
                $scope.messLyDo = "Không để trống lý do hủy"
                alertify.error("Hủy đơn hàng thất bại")
                return
            } else if ($scope.lyDo.length == 200) {
                $scope.messLyDo = "Lý do hủy chỉ tối đa 200 ký tự"
                alertify.error("Hủy đơn hàng thất bại")
                return;
            }

            $http.put("/admin/don-hang/huy-don-hang?lyDo=" + $scope.lyDo, this.id).then(r => {
                if (this.page == this.totalPage - 1) {
                    if (this.list.length == 1 && this.page > 0) {
                        this.page--;
                    }
                }
                this.getList(this.page)
                this.init()
                $scope.lyDo = null;
                $scope.messLyDo = "";
                this.id = []
                $('#closeHuy').click()
                document.getElementById('checkAllDangGiao').checked = false
                alertify.success("Hủy đơn hàng thành công")
            }).catch(e => {
                console.log(e)
                alertify.error("Hủy đơn hàng thất bại")
            })
        },
        hoanThanhDHAll() {

            alertify.confirm("Xác nhận đơn hàng hoàn thành?", function () {
                let checkBox = document.getElementsByName('checkDangGiao')
                checkBox.forEach(c => {
                    if (c.checked == true) {
                        $scope.dangGiao.id.push(c.value)
                    }
                })

                $http.put("/admin/don-hang/update-trang-thai?trangThai=4", $scope.dangGiao.id).then(r => {
                    if ($scope.dangGiao.page == $scope.dangGiao.totalPage - 1) {
                        if ($scope.dangGiao.list.length == 1 && $scope.dangGiao.page > 0) {
                            $scope.dangGiao.page--;
                        }
                    }
                    $scope.dangGiao.getList($scope.dangGiao.page)
                    $scope.dangGiao.init()
                    $scope.dangGiao.id = []
                    document.getElementById('checkAllDangGiao').checked = false
                    alertify.success("Xác nhận đơn hàng hoàn thành thành công")
                }).catch(e => {
                    console.log(e)
                    alertify.error("Xác nhận đơn hàng hoàn thành thất bại")
                })
            }, function () {
                alertify.error("Xác nhận đơn hàng hoàn thành thất bại")
            })
        },
        checkButton() {
            let checkboxs = document.getElementsByName('checkDangGiao')
            let check = true;
            checkboxs.forEach(c => {
                if (c.checked == true) {
                    check = false;
                }
            })
            document.getElementById("huyAll3").disabled = check;
            document.getElementById("xacNhanAll3").disabled = check;
        }
    }
    $scope.dangGiao.init(0)

    $scope.hoanThanh = {
        list: [],
        detail: {},
        totalElement: 0,
        totalPage: 0,
        page: 0,
        pages: [],
        sdtSearch : "",
        init() {
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=4&pageNumber=" + this.page+"&sdt="+this.sdtSearch).then(r => {
                this.list = r.data.content;
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        getList(pageNumber) {
            $scope.trangThaiDonHang = 0
            this.page = pageNumber;
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=4&pageNumber=" + pageNumber+"&sdt="+this.sdtSearch).then(r => {
                this.list = r.data.content;
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
            let i = this.page
            let lengthLast = this.totalPage <= 3 ? this.totalPage : this.page + 3
            let lengthFirst = this.totalPage >=2 ?  this.page - 2 : 0

            if(lengthLast>this.totalPage){
                lengthLast = this.totalPage
                i = lengthLast - 2
            }
            if(lengthFirst < 0) lengthFirst = 0

            for (lengthFirst; i > lengthFirst; lengthFirst++) {
                numbers.push(lengthFirst)
            }
            for (i; i < lengthLast; i++) {
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
        sdtSearch : "",
        init() {
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=0&pageNumber=" + this.page+"&sdt="+this.sdtSearch).then(r => {
                this.list = r.data.content;
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        getList(pageNumber) {
            $scope.trangThaiDonHang = 0
            this.page = pageNumber;
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=0&pageNumber=" + pageNumber+"&sdt="+this.sdtSearch).then(r => {
                this.list = r.data.content;
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
            let i = this.page
            let lengthLast = this.totalPage <= 3 ? this.totalPage : this.page + 3
            let lengthFirst = this.totalPage >=2 ?  this.page - 2 : 0

            if(lengthLast>this.totalPage){
                lengthLast = this.totalPage
                i = lengthLast - 2
            }
            if(lengthFirst < 0) lengthFirst = 0

            for (lengthFirst; i > lengthFirst; lengthFirst++) {
                numbers.push(lengthFirst)
            }
            for (i; i < lengthLast; i++) {
                numbers.push(i)
            }
            this.pages = numbers;
        }
    }
    //don hang usser
    $scope.donHangChuaXacNhanKh = []
    $scope.donHangUser = function (trangThai) {
        $http.get("/don-hang/get-by-trangThai-khachHang?trangThai=" + trangThai).then(resp => {
            $scope.donHangChuaXacNhanKh = resp.data;
            console.log(resp.data)
        }).catch(err => {
            console.log(err);
        })
    }
    $scope.donHangUser(5)

    $scope.findByMaDonHangUser = function (ma) {
        $http.get("/don-hang/" + ma).then(function (res) {
            const donHang = res.data;
            $scope.maDonHang = donHang.ma
            $scope.nguoiNhan = donHang.tenNguoiNhan
            $scope.soDT = donHang.soDienThoai
            $scope.email = donHang.email
            $scope.ghiChu = donHang.ghiChu
            $scope.diaChiCT = donHang.diaChiChiTiet
            $scope.tienGiam = donHang.tienGiam
            $scope.phiGiaoHang = donHang.phiGiaoHang
            $scope.lyDoHuy = donHang.lyDoHuy
            $scope.chiTietDon = donHang.chiTietDonHang
        })
    }
    $scope.huyDonUser = function (ma) {
        lyDoHuy = $scope.lyDoHuy
        $http.put("/don-hang/huy-don-hang-user?ma=" + ma, lyDoHuy).then(function (res) {
            // location.reload()
            let index = $scope.donHangChuaXacNhanKh.findIndex(d => d.ma == ma)
            $scope.donHangChuaXacNhanKh.splice(index,1);
            alert("Hủy thành công");
        })
    }
    $scope.setChiTietDH = function (maCTDH){
        console.log(maCTDH);
        $scope.danhGia.chiTietDonHang = maCTDH
    }
    $scope.danhGia = {}
    $scope.erDanhGia = {}
    $scope.addDanhGia = function (){
        $http.post("/nhan-xet",$scope.danhGia).then(r => {
            $('#danhGia').modal('hide')
            alert("Thành công")
            $scope.danhGia = {}
        }).catch(e => $scope.erDanhGia = e.data)
    }

})