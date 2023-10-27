var app = angular.module("ctsp-app", [])
app.controller("ctsp-ctrl", function ($scope, $http) {
    $scope.product = {};
    $scope.productDetails = [];
    $scope.images = [];
    $scope.idCTSP = ""
    var heartButton = document.getElementById("heart")
    const sizeZone = $("#sizes-zone")
    const pathName = location.pathname;
    const maSP = pathName.substring(pathName.lastIndexOf("/"))

    $http.get("/san-pham" + maSP).then(r => {
        $scope.product = r.data
        if (r.data.anh.length > 0) {
            for (let i = 1; i < r.data.anh.length; i++) {
                $scope.images = $scope.images.concat(r.data.anh[i]);
            }
        }
    }).catch(e => {
        console.log(e)
        alert("Lỗi!")
    })
    $http.get("/chi-tiet-san-pham" + maSP + "/get-all").then(r => {
        $scope.productDetails = r.data;
        $scope.productDetails.forEach(s => {
            if(s.soLuong <= 0) sizeZone.append('<input type="radio" class="btn-check" name="ctsp" id="' + s.size + '" autocomplete="off" disabled>\n' +
                                '<label class="btn btn-outline-secondary" for="' + s.size + '" style="width: 60px;">' + s.size + '</label>')
            else sizeZone.append('<input type="radio" ng-model="idCTSP" value="'+s.id+'" class="btn-check" name="ctsp" id="' + s.size + '" autocomplete="off" >\n' +
                                    '<label class="btn btn-outline-secondary" for="' + s.size + '" style="width: 60px;">' + s.size + '</label>')
        })
    }).catch(e => console.log(e))
    $http.get("/san-pham/san-pham-tuong-tu" + maSP).then(r => {
        $scope.productsTuongTu = r.data
    }).catch(e => {
        console.log(e)
        alert("Lỗi!")
    })


    $scope.addDSYT = function (){
        let className = heartButton.className;

        if(className == "mdi mdi-heart-outline text-dark"){
            heartButton.className = "mdi mdi-heart text-danger"
        }else{
            heartButton.className = "mdi mdi-heart-outline text-dark"
        }
    }
    $scope.addDSYT1 = function (id) {
        let heartButton = document.getElementById("heart" + id)
        let className = heartButton.className;

        if (className == "mdi mdi-heart-outline text-dark") {// thêm vào danh sách yêu thích
            heartButton.className = "mdi mdi-heart text-danger"
        } else { // xóa khỏi danh sách yêu thích
            heartButton.className = "mdi mdi-heart-outline text-dark"
        }

    }

    $scope.showImg = function (nameImg){
        document.getElementById("show-Img").src = "/image/loadImage/product/"+nameImg
    }
    //add to cart
    $scope.CTSP = null
    $scope.addToCart = function () {
        let form = document.getElementById("form")
        let idCtsp = form.elements["ctsp"].value
        console.log(idCtsp)
        if(idCtsp == null) {
            return;
        }
        var sl = 1
        if (confirm("Thêm sản phẩm vào giỏ hàng?")) {
            $http.post("/cart/add-to-cart?idCTSP=" + idCtsp + "&sl=" + sl).then(function (response) {
                console.log(response.data)
                if(response.data == null || response.data.length == 0){
                    alert("Phân loại của sản phẩm không đủ số lượng!!!")
                }else {
                    alert("Success")
                }
            })
        }
    }

})