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
        for (let i = 0; i < totalPages; i++) {
            $scope.pageNumbers.push(i);
        }
    }

    $scope.addDSYT = function (id) {
        let heartButton = document.getElementById("heart" + id)
        let className = heartButton.className;

        if (className == "mdi mdi-heart-outline text-dark") {// thêm vào danh sách yêu thích
            heartButton.className = "mdi mdi-heart text-danger"
        } else { // xóa khỏi danh sách yêu thích
            heartButton.className = "mdi mdi-heart-outline text-dark"
        }

    }
})