
export function loadNavbar(id) {
    $("#nav-placeholder").load("navbar.html", function() {
        document.getElementById(id).classList.add('active');
        
        if (localStorage.getItem("token") != null) {            
            document.getElementById("profileHomepageLink").innerHTML = localStorage.getItem("firstName");
        } else {
            document.getElementById("profileHomepageNavItem").display = "none";
        }
         
    });
}