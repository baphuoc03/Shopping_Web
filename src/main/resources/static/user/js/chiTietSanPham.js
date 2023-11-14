var app = angular.module("ctsp-app", [])
app.controller("ctsp-ctrl", function ($scope, $http) {
    $scope.product = {};
    $scope.productDetails = [];
    $scope.images = [];
    $scope.idCTSP = ""
    $scope.soLuongAdd = 1
    $scope.soLuong = ""
    $scope.size = ""
    $scope.lengthFoot = 26

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
            if (s.soLuong <= 0) sizeZone.append('<input type="radio" class="btn-check" name="ctsp" id="' + s.size + '" autocomplete="off" disabled>\n' +
                '<label class="btn btn-outline-secondary" for="' + s.size + '" style="width: 60px;">' + s.size + '</label>')
            else sizeZone.append('<input type="radio" ng-model="idCTSP" value="' + s.id + '" class="btn-check" name="ctsp" id="' + s.size + '" autocomplete="off" ng-click="getSoLuong(' + s.id + ')">\n' +
                '<label class="btn btn-outline-secondary" for="' + s.size + '" style="width: 60px;" onclick="angular.element(this).scope().getSoLuong(\'' + s.id + '\')">' + s.size + '</label>')
        })
    }).catch(e => console.log(e))

    $http.get("/san-pham/san-pham-tuong-tu" + maSP).then(r => {
        $scope.productsTuongTu = r.data
    }).catch(e => {
        console.log(e)
        alert("Lỗi!")
    })


    $scope.addDSYT = function (id) {
        let heartButton = document.getElementById("" + id)
        console.log(heartButton.className)
        // let className = heartButton.className;
        let data = {
            "sanPham": id
        }
        if (heartButton.className == "far fa-heart" || heartButton.className == "far fa-heart ng-scope") {// thêm vào danh sách yêu thích
            $http.post("/danh-sach-yeu-thich/add", data).then(r => {
                heartButton.className = "fas fa-heart"
                alert("Đã thêm vào danh sách yêu thích!")
            })
        } else { // xóa khỏi danh sách yêu thích

            heartButton.className = "far fa-heart"
        }
    }
    // $scope.getMaSanPhamInDSTY()

    $scope.addDSYT1 = function (id) {
        console.log(id)

        let data = {
            "sanPham": id
        }
        $http.post("/danh-sach-yeu-thich/add", data).then(r => {
            alert("Đã thêm vào danh sách yêu thích!")
        })
    }

    $scope.checkSanPhamInDSYT = function (maSP) {
        let reult = false;
        $http.get("/danh-sach-yeu-thich/check/" + maSP).then(r => {
            reult = r.data
        }).catch(e => console.log(e))
        console.log(reult)
        return reult
    }

    $scope.getMaSanPhamInDSTY = function () {
        $http.get("/danh-sach-yeu-thich/get-ma-san-pham-in-dsyt").then(r => {
            $scope.maSpInDSYT = r.data
            // console.log($scope.maSpInDSYT.length)
            // document.getElementById("buttonHeart").setAttribute("data-notify", ""+$scope.maSpInDSYT.length)
        }).catch(e => console.log(e))
    }
    $scope.getMaSanPhamInDSTY()


    $scope.showImg = function (nameImg) {
        document.getElementById("show-Img").src = "/image/loadImage/product/" + nameImg
    }
    //add to cart
    $scope.CTSP = null
    $scope.addToCart = function () {
        let form = document.getElementById("form")
        let idCtsp = form.elements["ctsp"].value
        if (idCtsp == null) {
            return;
        }
        let sl = parseInt(document.getElementById("soLuong").value)
        console.log("sốluong: " + sl)
        if (confirm("Thêm sản phẩm vào giỏ hàng?")) {
            $http.post("/cart/add-to-cart?idCTSP=" + idCtsp + "&sl=" + sl).then(function (response) {
                console.log(response.data)
                if (response.data == null || response.data.length == 0) {
                    alert("Phân loại của sản phẩm không đủ số lượng!!!")
                } else {
                    alert("Success")
                }
            }).catch(e => {
                document.getElementById("eSize").innerText = e.data.eSize == undefined ? "" : e.data.eSize
            })
        }
    }

    $scope.getSoLuong = function (idCTSP) {
        $http.get("/chi-tiet-san-pham/1/" + idCTSP).then(r => {
            $scope.soLuong = "Còn lại " + r.data + " sản phẩm"
        }).catch(e => console.log(e))
    }
    $scope.getSizePhuHop = function () {
        let sizes = []
        $http.get("/size/get-by-chieu-dai?chieuDai=" + $scope.lengthFoot).then(r => {
            sizes = r.data
            if (sizes.length == 0) {
                $scope.size = "Không có kích thước phù hợp"
            } else {
                for (let i = 0; i < sizes.length; i++) {
                    if (i == 0) {
                        $scope.size = sizes[i].ma + "";
                    } else {
                        $scope.size += ", " + sizes[i].ma;
                    }
                }
            }
        }).catch(e => console.log(e))
    }
    $scope.getSizePhuHop()

//    cart show
    $http.get("/cart/find-all").then(r => {
        console.log(r.data)
        $scope.cart = r.data;
        console.log("soLuong:")
    }).catch(e => console.log(e))

    $scope.getTotal = function () {
        var totalPrice = 0;
        for (let i = 0; i < $scope.cart.length; i++) {
            totalPrice += $scope.cart[i].soLuong * $scope.cart[i].donGiaSauGiam
        }
        return totalPrice;
    }


})