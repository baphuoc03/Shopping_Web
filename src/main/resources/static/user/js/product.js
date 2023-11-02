var app = angular.module("index-app", [])
app.controller("index-ctrl", function ($scope, $http) {
    $scope.products = [];
    $scope.filterDto = {};
    $scope.totalPage = 0;
    $scope.pageNumbers = [];
    $scope.pageNumber = 0;
    var isfilter = false;

    $http.get("/san-pham/get-all").then(r => {
        $scope.products = r.data.content;
        $scope.getPageNumbers(r.data.totalPages)
        $scope.filterData = {}
    }).catch(e => console.log(e))

    $scope.getAll = function (pageNumber) {
        $scope.pageNumber = pageNumber;

        if (!isfilter) {
            $http.get("/san-pham/get-all?pageNumber=" + pageNumber).then(r => {
                $scope.products = r.data.content;
                // $scope.filterData = {}
            }).catch(e => console.log(e))
        } else {
            $http.post("/san-pham/filter?pageNumber=" + pageNumber, $scope.filterDto).then(r => {
                $scope.products = r.data.content;
            }).catch(e => console.log(e))
        }
    }

    $scope.getPageNumbers = function (totalPages) {
        $scope.pageNumbers = []
        for (let i = 0; i < totalPages; i++) {
            $scope.pageNumbers.push(i);
        }
    }

    $scope.addDSYT = function (id) {
        let heartButton = document.getElementById("heart" + id)
        let className = heartButton.className;
        let data ={
            "nguoiSoHuu": "khach2",
            "sanPham": id
        }
        if (className == "mdi mdi-heart-outline text-dark") {// thêm vào danh sách yêu thích
            $http.post("/danh-sach-yeu-thich/add",data).then(r => {
                heartButton.className = "mdi mdi-heart text-danger"
                alert("Đã thêm vào danh sách yêu thích!")
            })
        } else { // xóa khỏi danh sách yêu thích
                heartButton.className = "mdi mdi-heart-outline text-dark"
        }

    }


    ///////////////////////////////////////
    $scope.getPropertiesInFilter = function (){
        $http.get("/admin/mau-sac/find-all").then(r =>{
            $scope.mauSac = r.data;
        }).catch( e => console.log(e))

        $http.get("/admin/chat-lieu/find-all").then(r =>{
            $scope.chatLieu = r.data;
        }).catch( e => console.log(e))

        $http.get("/admin/thuong-hieu/find-all").then(r =>{
            $scope.thuongHieu = r.data;
        }).catch( e => console.log(e))

        $http.get("/admin/xuat-xu/find-all").then(r =>{
            $scope.xuatXu = r.data;
        }).catch( e => console.log(e))

        $http.get("/admin/kieu-dang/find-all").then(r =>{
            $scope.kieuDang = r.data;
        }).catch( e => console.log(e))
    }
    $scope.getPropertiesInFilter();
    $scope.filter = function (filterData){
        $scope.pageNumber = 0
        $scope.filterDto = filterData
        $scope.pageNumbers = []
        $http.post("/admin/san-pham/filter",$scope.filterDto).then(r => {
            $scope.items = r.data.content;
            $scope.totalPage = r.data.totalPages;
            $scope.getPageNumbers(r.data.totalPages)
            isfilter = true;
        }).catch(e => console.log(e))
    }
    $scope.search = function (keyWord){
        $scope.pageNumber = 0
        $scope.filterDto.ten = keyWord
        $scope.pageNumbers = []
        $http.post("/admin/san-pham/filter",$scope.filterDto).then(r => {
            $scope.products = r.data.content;
            $scope.totalPage = r.data.totalPages;
            $scope.getPageNumbers(r.data.totalPages)
            isfilter = true;
        }).catch(e => console.log(e))
    }
    $scope.clearFilter = function (){
        $scope.pageNumber = 0
        $scope.pageNumbers = []
        $http.get("/admin/san-pham/get-all").then(r => {
            $scope.products = r.data.content;
            $scope.getPageNumbers(r.data.totalPages)
            $scope.filterData = {}
            $scope.filterDto = {}
            isfilter = false;
        }).catch(e => console.log(e))
    }
    ///////////////////////////////////////
})