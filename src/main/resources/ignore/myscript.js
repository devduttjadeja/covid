
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
		<th>patientID</th>
		<th>patientName</th>
		<th>phone</th>
		<th>address</th>
		<th>dateOfBirth</th>
		<th>email</th>
		</tr>`;

    // Loop to access all rows
    for (let r of data) {
        tab += `<tr>
	<td>${r.patientID} </td>
	<td>${r.patientName}</td>
	<td>${r.phone}</td>
	<td>${r.address}</td>
	<td>${r.dateOfBirth}</td>
	<td>${r.email}</td>
            </tr>`;
    }
    // Setting innerHTML as tab variable
    document.getElementById("patients").innerHTML = tab;
}