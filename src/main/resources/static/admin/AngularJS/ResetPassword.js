var app = angular.module("resetPassword-app",[])
app.controller("resetPassword-ctrl", function($scope, $http){
    $scope.showPassword = function (idInput,idButton){
        let input = document.getElementById(idInput);
        let button = document.getElementById(idButton)
        if(input.type == "password") {
            button.className = " bx bxs-hide";
            input.type="text"
        }else {
            input.type="password"
            button.className = " bx bxs-show"
        }
    }
})