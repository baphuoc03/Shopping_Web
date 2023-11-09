var app = angular.module("trangChu-app", [])
app.controller("ctrl", function ($scope, $http) {
    $scope.products = [];
    $scope.getSanPham = function (number){
        let path = ""
        if(number == 0){
            path="ban-chay"
        }else if(number==1){
            path="khuyen-mai"
        }else{
            path="san-pham-moi"
        }
        $http.get("/san-pham/"+path).then(r => {
            $scope.products = r.data
            console.log($scope.products)
        }).catch(e => console.log(e))
    }
    $scope.getSanPham(0)

})