var app = angular.module("ctsp-app", [])
app.controller("ctsp-ctrl", function ($scope, $http) {
    $scope.product = {};
    $scope.productDetails = [];
    $scope.images = [];
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
            if(s.soLuong <= 0) sizeZone.append('<input type="radio" class="btn-check" name="btnradio" id="' + s.size + '" autocomplete="off" disabled>\n' +
                                '<label class="btn btn-outline-secondary" for="' + s.size + '" style="width: 60px;">' + s.size + '</label>')
            else sizeZone.append('<input type="radio" class="btn-check" name="btnradio" id="' + s.size + '" autocomplete="off" >\n' +
                                    '<label class="btn btn-outline-secondary" for="' + s.size + '" style="width: 60px;">' + s.size + '</label>')
        })
    }).catch(e => console.log(e))

    $scope.addDSYT = function (){
        let className = heartButton.className;

        if(className == "mdi mdi-heart-outline text-dark"){
            heartButton.className = "mdi mdi-heart text-danger"
        }else{
            heartButton.className = "mdi mdi-heart-outline text-dark"
        }
    }

})