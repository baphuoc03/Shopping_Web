var app = angular.module("cart-app", [])
app.controller("cart-ctrl", function ($scope, $http){
    $scope.cart = [];

    $http.get("/cart/find-all").then(r => {
        $scope.cart = r.data;
    }).catch(e => console.log(e))

})
