var app = angular.module('chiTietSP-app', []);
app.controller('chiTietSP-ctrl', function ($scope, $http) {

    const pathName = window.location.pathname.split('/');
    const idSP = pathName[pathName.length - 1]

    $scope.items =[];
    $scope.form ={};

    $scope.getAll = function (){
        $http.get("/admin/san-pham/"+idSP+"/get-all").then(r => {
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





