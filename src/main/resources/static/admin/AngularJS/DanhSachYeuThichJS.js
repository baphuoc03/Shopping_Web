var app = angular.module("danh-sach-yeu-thich",[])
app.controller("danh-sach-yeu-thich-ctrl", function($scope, $http){
    $scope.form = {}
    $scope.items = []
    $scope.productDetails = [];

    $scope.findAll = function () {
        $http.get("/danh-sach-yeu-thich/find-all").then(resp => {
            console.log(resp.data)
            $scope.items = resp.data;
        }).catch(error =>{
            console.log(error)
        })
    }
    $scope.findAll();

    $scope.delete = function (ma){
        var url = "/danh-sach-yeu-thich/delete" +"/"+ ma;
        $http.delete(url).then(function (r){
            alert("Dlete thành công!!!")
            location.reload();
        })
    }

    $scope.getDetail =function (maSP){
        var productDetails = []
        $http.get("/chi-tiet-san-pham/" + maSP + "/get-all").then(r => {
            productDetails = r.data;
        }).catch(e => console.log(e))
        console.log(productDetails)
        return productDetails
    }

})