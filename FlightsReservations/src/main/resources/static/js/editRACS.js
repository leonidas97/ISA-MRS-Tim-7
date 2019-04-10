var mapa = new Map();
var nameSelect = $("#racs_name_select");
var idSelect = $("#racs_id_select");
var carSelect = $("#car_select");
var carIdSelect = $("#car_id_select");


$(document).ready(function(){
	$.ajax({
		url: "http://localhost:8080/racss/getAll",
		method: "GET",
		dataType: "json",
		crossDomain: true,
		success: function (result) {
			for (var i = 0; i < result.length; i++) {
                mapa[result[i].id] = result[i];
                nameSelect.append("<option>"+result[i].name+"</option>");
                idSelect.append("<option>" + result[i].id + "</option>");
			}
			setInputs();
        },
        error: function(err) {
            console.log(err);
        }
	});	
});

function updateRACS() {
    var ids = ["name", "longitude", "latitude", "description", "averageScore", "numberOfVotes"];
	if (!validateInputs(ids)) {
		alert("Inputs are invalid!");
		return;
	}
	
    var key = idSelect.val();

	mapa[key].name = $("#name").val();
    mapa[key].longitude = $("#longitude").val();
    mapa[key].latitude = $("#latitude").val();
    mapa[key].promoDescription = $("#description").val();
    mapa[key].averageScore = $("#averageScore").val();
    mapa[key].numberOfVotes = $("#numberOfVotes").val();
    var carsOfRacs = [];
    

    $("#car_select > option").each(function() {
        var currentCar = {};
        currentCar.manufacturer = this.text.split(" ")[1];
        currentCar.name = this.text.split(" ")[2];
        currentCar.color = this.text.split(" ")[3];
        currentCar.yearOfManufacture = parseInt(this.text.split(" ")[4]);
        carsOfRacs.push(currentCar);
    });

    mapa[key].cars = carsOfRacs;
    
		
	$.ajax({
		url: "http://localhost:8080/racss/update",
		method: "PUT",
		contentType: "application/json",
		dataType: "json",	
		data: JSON.stringify(mapa[key]),
		success: function(result) {
		}
	});
}

function validateInputs(ids) {
	
	var flag = true;
	
	for (var i = 0; i < ids.length; i++)
		if ($("#"+ids[i]).val().trim() == "") {
			flag = false;
			break;
		}
	return flag;
}

function setInputs(){
    var key = idSelect.val();
	
	$("#name").val(mapa[key].name);
	$("#longitude").val(mapa[key].longitude);
	$("#latitude").val(mapa[key].latitude);
    $("#description").val(mapa[key].promoDescription);
    $("#averageScore").val(mapa[key].averageScore);
    $("#numberOfVotes").val(mapa[key].numberOfVotes);

    carSelect.empty();
    carIdSelect.empty();

    var idx = 0;
    mapa[key].cars.sort();
    for (var car of mapa[key].cars) {
        idx++;
        carIdSelect.append("<option>"+car.id+"</option>");
        carSelect.append("<option>"+ "#" + idx + " " + car.manufacturer + " " + car.name + " " + car.color + " " + car.yearOfManufacture +"</option>");
    }
}

/*  if the 2nd item in one selectbox is selected,
then the 2nd item is selected in all other
selectboxes as well
*/
$(function(){
    $('#racs_name_select').change(function(){ // when one changes
        var idx = $('#racs_name_select').prop('selectedIndex');
        document.getElementById('racs_id_select').selectedIndex = idx;
        
        setInputs();
    })
    

    $("#car_select").change(function() {
        var idx = $("#car_select").prop('selectedIndex');
        document.getElementById('car_id_select').selectedIndex = idx;
    
    })
    
})

function addCarToRACS() {
    var ids = ["carName", "carManufacturer", "carColor", "carYOM"];
    if (!validateInputs(ids)) {
		alert("Inputs are invalid!");
		return;
    }

    var newCar = {};
    newCar.name = $("#carName").val();
    newCar.manufacturer = $("#carManufacturer").val();
    newCar.color = $("#carColor").val();
    newCar.yearOfManufacture = $("#carYOM").val();
    

    newCar.racs_id = idSelect.val();
    console.log(newCar);
    $.ajax({
		url: "http://localhost:8080/racss/addCar",
		method: "PUT",
		contentType: "application/json",
		dataType: "json",	
		data: JSON.stringify(newCar),
		success: function(car) {
            //TODO: ovde sta treba
            console.log("AAA " + car);
            $("#car_select").append("<option>"+"#"+car.id+" "+car.manufacturer+" "+car.name+" "+car.yearOfManufacture+"</option>");
		}, error: function(error) {
            console.log(error);
        }
	});
    
}

function removeCarFromRACS() {
    var carID = carIdSelect.val();
    $.ajax({
        url: "http://localhost:8080/cars/removeCar/" + carID,
        method: "DELETE",
        data: {},
        contentType: "application/json",
        dataType: "json",
        success: function(allCars) {
            carIdSelect.empty();
            carSelect.empty();
            allCars.sort();
            var idx = 0;
            for (var car of allCars) {
                idx++;
                carIdSelect.append("<option>"+car.id+"</option>");
                carSelect.append("<option>"+ "#" + idx + " " + car.manufacturer + " " + car.name + " " + car.color + " " + car.yearOfManufacture +"</option>");
            }
        },
        error: function(err) {
            console.log(err);
        }
    });
}

function updateCar() {

}