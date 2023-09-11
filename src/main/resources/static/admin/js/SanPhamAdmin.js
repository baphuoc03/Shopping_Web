var app = angular.module('product-admin', []);
app.controller('ctrl', function ($scope, $http) {


    $scope.items =[];
    $scope.form ={};

    $scope.getAll = function (){
        $http.get("/admin/san-pham/get-all").then(r => {
            console.log(r.data)
            $scope.items = r.data;
        }).catch(e => console.log(e))
    }
    $scope.getAll()



    $scope.delete = function (ma){
        $http.delete("/admin/san-pham/delete/"+ma).then(r => {
            var index = $scope.items.findIndex(i => i.ma == ma);
            console.log(index)
            $scope.items.splice(index,1);
            // $scope.getAll();
        }).catch(e => console.log(e));
    }

    $scope.getChiTietSP = function (ma){
        location.href = "/admin/san-pham/"+ma;
    }
});




