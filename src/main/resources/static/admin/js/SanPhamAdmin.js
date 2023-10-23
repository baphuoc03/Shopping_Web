var app = angular.module('product-admin', []);
app.controller('ctrl', function ($scope, $http) {


    $scope.items =[];
    $scope.form ={};
    $scope.filterDto = {};
    $scope.totalPage = 0;
    $scope.pageNumbers = [];
    $scope.pageNumber = 0;
    var isfilter = false;

    $http.get("/admin/san-pham/get-all").then(r => {
        $scope.items = r.data.content;
        $scope.getPageNumbers(r.data.totalPages)
        $scope.filterData = {}
    }).catch(e => console.log(e))

    $scope.getAll = function (pageNumber){
        $scope.pageNumber = pageNumber;
        //
        // let pagination = document.getElementsByClassName("pagination")[0]
        // let pageItem = pagination.getElementsByClassName("pageNumber")

        // pageItem.forEach(p => {
        //     p.getElementsByTagName("a")[0].className = "page-link"
        // })
        //
        // document.getElementById(pageNumber+"").getElementsByTagName("a")[0].className = "page-link active"

        if(!isfilter){
            $http.get("/admin/san-pham/get-all?pageNumber="+pageNumber).then(r => {
                $scope.items = r.data.content;
                // $scope.filterData = {}
            }).catch(e => console.log(e))
        }else{
            $http.post("/admin/san-pham/filter?pageNumber="+pageNumber,$scope.filterDto).then(r => {
                $scope.items = r.data.content;
            }).catch(e => console.log(e))
        }
    }

    $scope.getPageNumbers = function (totalPages){
        for (let i = 0; i< totalPages;i++){
            $scope.pageNumbers.push(i);
        }
    }

    $scope.delete = function (ma){
        if(confirm("Xóa sản phẩm? ")){
            $http.delete("/admin/san-pham/delete/"+ma).then(r => {
                $scope.getAll($scope.pageNumber)
                alert("Xóa thành công")
            }).catch(e => {
                alert("Lỗi!!!")
                console.log(e)
            });
        }
    }

    $scope.getChiTietSP = function (ma){
        location.href = "/admin/san-pham/"+ma;
    }

    $scope.updateTrangThaiHienThi = function (switchId,maSP){
        let trangThai = document.getElementById(switchId).checked
        $http.put("/admin/san-pham/update-TrangThai-HienThi/"+maSP,trangThai).then(r => {
            console.log("update hiển thị",r.data)
        }).catch(e => {
            alert("Lỗi!!!")
            document.getElementById(switchId).checked = trangThai == true ? false : true
        });
    }

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
            $scope.getPageNumbers(r.data.totalPages)
            isfilter = true;
        }).catch(e => console.log(e))
    }
    $scope.clearFilter = function (){
        $scope.pageNumber = 0
        $scope.pageNumbers = []
        $http.get("/admin/san-pham/get-all").then(r => {
            console.log(r.data)
            $scope.items = r.data.content;
            $scope.getPageNumbers(r.data.totalPages)
            $scope.filterData = {}
            $scope.filterDto = {}
            isfilter = false;
        }).catch(e => console.log(e))
    }
});
//
// var appTB = angular.module('app', []);
// appTB.controller('thongBao-ctrl', function ($scope, $http) {
//
//     $scope.thongBao = [];
//
//     $http.get("/admin/thong-bao/get-all/afc0b6cc-4c66-11ee-b10b-d69e940a783b").then(r => $scope.thongBao = r.data)
//         .catch(e => console.log(e));
//
//     //////////////////
//     var socket = new SockJS('/connectNotification');
//     stompClient = Stomp.over(socket);
//     stompClient.connect({}, function(frame) {
//         // console.log('Connected: ' + frame);
//         stompClient.subscribe('/afc0b6cc-4c66-11ee-b10b-d69e940a783b', function(r) {
//             // $scope.thongBao.unshift()
//             console.log(r)
//         });
//     });
//     /////////////////
// })





