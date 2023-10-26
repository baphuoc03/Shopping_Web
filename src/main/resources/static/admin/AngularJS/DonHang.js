var app = angular.module("donhang-app", [])
app.controller("donhang-ctrl", function ($scope, $http){
    $scope.donHang = {}
    $scope.chiTietDonHang = []
    const limit = 10;

    $scope.closeModal = function (id){
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

    $scope.getTotalPrice = function (){
        let total = 0;
        $scope.chiTietDonHang.forEach(c => total+= (c.donGiaSauGiam*c.soLuong))
        return total
    }

    ///////////////////////
    $scope.updateTrangThaiDonHang = function (ma,trangThai){
        let success = true;
        $http.get("/admin/don-hang/update-trang-thai/"+ma+"?trangThai="+trangThai).then(r => {
            success =true;
        }).catch(e => {
            console.log(e)
            success =false
        })
        return success
    }
    ////////////////////////////////////////////
    $scope.chuaXacNhan = {
        list : [],
        detail : {},
        totalElement : 0,
        totalPage : 0,
        page : 0,
        pages : [],
        init(pageNumber){
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=2&pageNumber="+pageNumber).then(r => {
                this.list = r.data.content;
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        getList(pageNumber){
            this.page = pageNumber;
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=2&pageNumber="+pageNumber).then(r => {
                this.list = r.data.content;
            })
        },
        xacNhanDH(ma){
            if(!confirm("Xác nhận đơn hàng?")) return;
            
            $http.get("/admin/don-hang/update-trang-thai/"+ma+"?trangThai=1").then(r => {
                if(this.page==this.totalPage-1){
                    if(this.list.length==1 && this.page>0){
                        this.page--;
                    }
                }
                this.init(this.page)
            }).catch(e => {
                console.log(e)
            })
        },
        huyDH(ma){
            if(!confirm("Hủy đơn hàng?")) return;

            $http.get("/admin/don-hang/huy-don-hang/"+ma).then(r => {
                if(this.page==this.totalPage-1){
                    if(this.list.length==1 && this.page>0){
                        this.page--;
                    }
                }
                this.init(this.page)
            }).catch(e => {
                console.log(e)
            })
        },
        getDetail(ma){
            $http.get("/admin/don-hang/"+ma).then(r => {
                this.detail = r.data;
                $('#chuaXacNhanDetail').modal('show')
            }).catch(e => console.log(e))

            $http.get("/admin/chi-tiet-don-hang/"+ma).then(r => {
                $scope.chiTietDonHang = r.data;
            }).catch(e => console.log(e))
        },
        setPageNumbers(){
            let numbers = [];
            for(let i=0;i<this.totalPage;i++){
                numbers.push(i)
            }
            this.pages = numbers;
        }
    }
    $scope.chuaXacNhan.init(0)

    $scope.daXacNhan = {
        list : [],
        detail : {},
        totalElement : 0,
        totalPage : 0,
        page : 0,
        pages : [],
        init(){
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=1").then(r => {
                this.list = r.data.content;
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        getList(pageNumber){
            $scope.daXacNhan.page = pageNumber;
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=1&pageNumber="+pageNumber).then(r => {
                this.list = r.data.content;
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        getDetail(ma){
            $http.get("/admin/don-hang/"+ma).then(r => {
                $scope.donHang = r.data;
                $('#donHangDetail').modal('show')
            }).catch(e => console.log(e))

            $http.get("/admin/chi-tiet-don-hang/"+ma).then(r => {
                $scope.chiTietDonHang = r.data;
            }).catch(e => console.log(e))
        },
        setPageNumbers(){
            let numbers = [];
            for(let i=0;i<this.totalPage;i++){
                numbers.push(i)
            }
            this.pages = numbers;
        }
    }

    $scope.huy = {
        list : [],
        detail : {},
        totalElement : 0,
        totalPage : 0,
        page : 0,
        pages : [],
        init(){
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=0").then(r => {
                this.list = r.data.content;
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        getList(pageNumber){
            $scope.daXacNhan.page = pageNumber;
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=0&pageNumber="+pageNumber).then(r => {
                this.list = r.data.content;
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        getDetail(ma){
            $http.get("/admin/don-hang/"+ma).then(r => {
                $scope.donHang = r.data;
                $('#donHangDetail').modal('show')
            }).catch(e => console.log(e))

            $http.get("/admin/chi-tiet-don-hang/"+ma).then(r => {
                $scope.chiTietDonHang = r.data;
            }).catch(e => console.log(e))
        },
        setPageNumbers(){
            let numbers = [];
            for(let i=0;i<this.totalPage;i++){
                numbers.push(i)
            }
            this.pages = numbers;
        }
    }

})