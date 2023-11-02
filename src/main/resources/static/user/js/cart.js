var app = angular.module("cart-app", [])
app.controller("cart-ctrl", function ($scope, $http){
    $scope.cart = [];

    $http.get("/cart/find-all").then(r => {
        console.log(r.data)
        $scope.cart = r.data;
        console.log("soLuong:")
    }).catch(e => console.log(e))

    $scope.getTotal = function () {
        var totalPrice = 0;
        for (let i = 0; i < $scope.cart.length; i++) {
            totalPrice += $scope.cart[i].soLuong * $scope.cart[i].donGia
        }
        return totalPrice;
    }
    $scope.getUpdateSL = function (id,soLuong){
        let index = $scope.cart.findIndex(c => c.id == id)
        $scope.cart[index]=
        console.log($scope.cart[index])

    }
    $scope.setUpdateSL = function (){
        console.log(document.getElementById("slUpdate").getAttribute("name"))
        var id = document.getElementById("slUpdate").getAttribute("name")
        var soLuong = document.getElementById("slUpdate").value
        console.log(soLuong)
        if(soLuong <=0 || soLuong.split('.').length>1 || soLuong.split(',').length>1 ){
            alert("Số lượng phải là số nguyên > 0!!!")
            return
        }
        if(!parseInt(soLuong)){
            alert("Số lượng phải là số nguyên > 0!!!")
            return
        }

        if (confirm("Cập nhật số lượng sản phẩm trong giỏ?")) {


            $http.get("/cart/update-soLuong?idCTSP=" + id + "&soLuong=" + soLuong).then(function (response) {
                console.log(response.data)
                if(response.data == null || response.data.length == 0){
                    alert("Phân loại của sản phẩm không đủ số lượng!!!")
                }else {
                    alert("Success")
                    location.reload()
                }
            })
        }

    }
    $scope.removeProductIncart = function (idCTSP){
        if(confirm("Xóa sản phẩm khỏi giỏ hàng?")) {
            $http.delete("/cart/remove/" + idCTSP).then(function (response) {
                // alert("Success")
                $scope.cart = response.data;
                $scope.getTotal();
                // let index = $scope.cart.findIndex(c => c.id == idCTSP);
                // $scope.cart.slice(index,1)


            })
        }
    }
})


