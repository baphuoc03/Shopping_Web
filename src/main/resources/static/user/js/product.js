var app = angular.module("index-app", [])
app.controller("index-ctrl", function ($scope, $http) {
    $scope.products = [];
    $scope.filterDto = {};
    $scope.totalPage = 0;
    $scope.pageNumbers = [];
    $scope.pageNumber = 0;
    $scope.maSpInDSYT = []
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
        for (let i = 0; i < totalPages; i++) {
            $scope.pageNumbers.push(i);
        }
    }

    $scope.addDSYT = function (id) {
        let heartButton = document.getElementById(""+ id)
        console.log(heartButton.className)
        // let className = heartButton.className;
        let data ={
            "sanPham": id
        }
        if (heartButton.className == "far fa-heart" || heartButton.className == "far fa-heart ng-scope") {// thêm vào danh sách yêu thích
            $http.post("/danh-sach-yeu-thich/add",data).then(r => {
                heartButton.className = "fas fa-heart"
                alert("Đã thêm vào danh sách yêu thích!")
            })
        } else { // xóa khỏi danh sách yêu thích
            $http.delete("/danh-sach-yeu-thich/delete/"+id).then(r => {
                heartButton.className = "far fa-heart"
                alert("Đã xóa danh sách yêu thích!")
            })

        }
        $scope.getMaSanPhamInDSTY()

    }

    $scope.checkSanPhamInDSYT=function (maSP){
        let reult = false;
        $http.get("/danh-sach-yeu-thich/check/"+maSP).then(r => {
            reult = r.data
        }).catch(e => console.log(e))
        console.log(reult)
        return reult
    }

    $scope.getMaSanPhamInDSTY = function (){
        $http.get("/danh-sach-yeu-thich/get-ma-san-pham-in-dsyt").then(r => {
            $scope.maSpInDSYT = r.data
            console.log($scope.maSpInDSYT.length)
            document.getElementById("buttonHeart").setAttribute("data-notify", ""+$scope.maSpInDSYT.length)
        }).catch(e => console.log(e))
    }
    $scope.getMaSanPhamInDSTY()

    $scope.addDSYT1 = function (id) {
        console.log(id)

        let data ={
            "sanPham": id
        }
        $http.post("/danh-sach-yeu-thich/add",data).then(r => {
            alert("Đã thêm vào danh sách yêu thích!")
        })
    }
})