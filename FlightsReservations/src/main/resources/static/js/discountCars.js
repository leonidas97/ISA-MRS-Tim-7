import {loadNavbar} from "./navbar.js"; 
import { checkRoleFromToken } from "./securityStuff.js";
var getAllDiscountCarsForPeriod = "/cars/getAllDiscountCarsForPeriod";
var carReservationLink = "/carReservations";

// for testing purposes
localStorage.setItem("reservationPeriodStart", "01-06-2019 20:00");
localStorage.setItem("reservationPeriodEnd", "01-12-2019 20:00");
localStorage.setItem("reservationCity", "Amsterdam");
localStorage.setItem("previousReservationDetails", "blabla");

var reservationCity = localStorage.getItem("reservationCity");
var previousReservationDetails = localStorage.getItem("previousReservationDetails");
// car reservation cannot be made on its own
if (previousReservationDetails == null) history.go(-1);

var rps = localStorage.getItem("reservationPeriodStart");
var rpe = localStorage.getItem("reservationPeriodEnd");

if (rps != null && rpe != null) {
    var reservationPeriodStart = dateFormatter(rps);
    var stringReservationPeriodStart = dateToString(rps);
    var reservationPeriodEnd = dateFormatter(rpe);
    var stringReservationPeriodEnd = dateToString(rpe);
} else if (rps == null) {
    $(document.documentElement).append("<h3 id=\"discountCars\">reservationPeriodStart not set</h3>");    
} else if (rpe == null) {
    $(document.documentElement).append("<h3 id=\"discountCars\">reservationPeriodEnd not set</h3>");    
}

// used for converting reservationPeriodStart and reservationPeriodEnd to Date of desired format
function dateFormatter(obj) {
    var objDate = obj.split(" ")[0].split("-")[0];
    if (objDate.length == 1) objDate = "0" + objDate;
    var objMonth = obj.split(" ")[0].split("-")[1];
    if (objMonth.length == 1) objMonth = "0" + objMonth;
    var objYear = obj.split(" ")[0].split("-")[2];
    var objHours = obj.split(" ")[1].split(":")[0];
    var objMinutes = obj.split(" ")[1].split(":")[1];
    return new Date(objYear, objMonth, objDate, objHours, objMinutes);
}

// used for converting reservationPeriodStart and reservationPeriodEnd to string
function dateToString(obj) {
    var objDate = obj.split(" ")[0].split("-")[0];
    if (objDate.length == 1) objDate = "0" + objDate;
    var objMonth = obj.split(" ")[0].split("-")[1];
    if (objMonth.length == 1) objMonth = "0" + objMonth;
    var objYear = obj.split(" ")[0].split("-")[2];
    var objHours = obj.split(" ")[1].split(":")[0];
    var objMinutes = obj.split(" ")[1].split(":")[1];

    return objDate + "-" + objMonth + "-" + objYear + " " + objHours + ":" + objMinutes;
}

var token = localStorage.getItem("token");
if (!checkRoleFromToken(token, "ROLE_USER")) history.go(-1);



window.fastBook = fastBook;

$(document).ready(function() {
    
    if (rps != null && rpe != null) {
        $("#discountCars").remove();
        $("#error").remove();
        $.ajax({
            url: getAllDiscountCarsForPeriod + "/" + stringReservationPeriodStart + "/" + stringReservationPeriodEnd + "/" + reservationCity,
            method: "GET",
            dataType: "json",
            contentType: "application/json",
            data: {},
            success: function(cars) {
                if (cars != null && cars.length > 0) {
                    displayDiscountCars(cars);
                } else {
                    $(document.documentElement).append("<h3 id=\"discountCars\">No cars to display</h3>");    
                }
            }, error: function(error) {
                $(document.documentElement).append("<h3 id=\"error\">Error</h3>");
                console.log(error);
            }
        });
    
    }
    
    loadNavbar('RACSHomepageNavItem');
});

function displayDiscountCars(cars) {
    var text = "<table id=\"discountCars\" style= \"margin:20px; width: 90%; float: center; text-align: center;\" class=\"table table-striped\">";
    text += "<thead>";
    text += "<tr>";
    text += "<th>Manufacturer</th>";
    text += "<th>Name</th>";
    text += "<th>Year of manufacture</th>";
    text += "<th>Color</th>";
    text += "<th>Total reservation price</th>";
    text += "<th>Start time</th>";
    text += "<th>End time</th>";
    text += "<th>Fast book</th>";
    
    text += "</tr>";
    text += "</thead><tbody>";

    for (var car of cars) {
        text += "<tr>";
        text += "<td>"+car.manufacturer+"</td>";
        text += "<td>"+car.name+"</td>";
        text += "<td>"+car.yearOfManufacture+"</td>";
        text += "<td>"+car.color+"</td>";
        text += "<td>"+car.initialPrice.toString().strike() + "&nbsp&nbsp -" + car.discountValue + "%" + " = " + car.totalPrice+"</td>";
        var start = car.startTime.split("+")[0];
       
        var end = car.endTime.split("+")[0];
        var startTimeString = discountDateToString(start);
        var endTimeString = discountDateToString(end);
        text += "<td>"+startTimeString+"</td>";
        text += "<td>"+endTimeString+"</td>";
        var obj = {};
        obj.carId = car.id;
        obj.startTime = startTimeString;
        obj.endTime = endTimeString;
        localStorage.setItem("fastBookCarStartTime", startTimeString);
        localStorage.setItem("fastBookCarEndTime", endTimeString);
        text += "<td><button class=\"btn btn-primary\" onclick=\"fastBook("+car.id+")\" >Fast book this car</button></td>";
        
        text += "</tr>";
    }
    text += "</tbody></table>";
    $(document.documentElement).append(text);
}



// used to convert date to string in displayDiscountCars
function discountDateToString(obj) {
    var time = obj.split("T")[1].substring(0, 5);
    var date = obj.split("T")[0];
    return date.substring(8)+"-"+date.substring(5,8)+date.substring(0,4)+" "+time;
}

function fastBook(carId) {
    var carReservationRequestDTO = {};
    carReservationRequestDTO.ownerEmail = localStorage.getItem("email");
    carReservationRequestDTO.carId = carId;
    
    carReservationRequestDTO.startTime = localStorage.getItem("fastBookCarStartTime");
    carReservationRequestDTO.endTime = localStorage.getItem("fastBookCarEndTime");
    
    
    
    $.ajax({
        url: carReservationLink + "/" + previousReservationDetails,
        method: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(carReservationRequestDTO),
        headers: {
            "Authorization": "Bearer " + token
        },
        success: function(carReservationDTO) {
            location.replace("/html/userHomepage.html");
        }, error: function(error) {
            $(document.documentElement).append("<h3 id=\"error\">Error</h3>");
            console.log(error);
        }
    });
    
    
    
}