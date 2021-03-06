
// Defining async function
async function getapi(url) {

    // Storing response
    const response = await fetch(url);

    // Storing data in form of JSON
    var data = await response.json();
    console.log(data);
    show(data);
}

// Function to define innerHTML for HTML table
function show(data) {
    let tab =
        `<tr>
		<th>ID</th>
		<th>Name</th>
		<th>Phone</th>
		<th>Address</th>
		<th>Date Of Birth</th>
		<th>Email</th>
		</tr>`;

    // Loop to access all rows
    for (let patient of data) {
        tab += `<tr>
	<td>${patient.patientID} </td>
	<td>${patient.patientName}</td>
	<td>${patient.phone}</td>
	<td>${patient.address}</td>
	<td>${patient.dateOfBirth}</td>
	<td>${patient.email}</td>
            </tr>`;
    }
    // Setting innerHTML as tab variable
    document.getElementById("patients").innerHTML = tab;
}

function myFunction() {
  location.replace("URL to redirect")
}