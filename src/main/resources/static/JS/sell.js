document.addEventListener("DOMContentLoaded", async function() {
    const url = 'http://localhost:8080/sell';  // Make sure this URL is correct
    try {
        const response = await fetch(url);
        const data = await response.json();
        if (data.status === 'SUCCESS') {
            const sells = data.sellList;
            populateTable(sells);  // Call the function to populate the table with data
        } else {
            const paragraph = document.createElement('p');
            paragraph.innerText = data.message;
            document.body.appendChild(paragraph);
        }
    } catch (error) {
        console.error('Error fetching data:', error);
        const paragraph = document.createElement('p');
        paragraph.innerText = 'Failed to load data.' + error;
        document.body.appendChild(paragraph);
    }
});

// Function to populate the table with the data from the sells list
const populateTable = (sells) => {
    const table = document.querySelector('table');  // Select the existing table
    const tableHead = document.createElement('thead');
    const tableBody = document.createElement('tbody');

    // Create the table header row
    const headerRow = document.createElement('tr');

    const headers = ['Customer', 'Phone', 'Date', 'Items', 'Total Price', 'Amount Paid', 'Change', 'Remaining'];

    headers.forEach(heading => {
        const th = document.createElement('th');
        th.textContent = heading;
        headerRow.appendChild(th);
    });

    tableHead.appendChild(headerRow);
    table.appendChild(tableHead);

    sells.forEach(sell => {
        const row = document.createElement('tr');

        const nameCell = document.createElement('td');
        nameCell.textContent = sell.customer.name;
        row.appendChild(nameCell);

        const phoneCell = document.createElement('td');
        phoneCell.textContent = sell.customer.phone;
        row.appendChild(phoneCell);

        const dateCell = document.createElement('td');
        dateCell.textContent = sell.dateOfSale;
        row.appendChild(dateCell);

        const itemsCell = document.createElement('td');
        itemsCell.textContent = sell.items.map(item => item.name).join(', ');
        row.appendChild(itemsCell);

        const totalPriceCell = document.createElement('td');
        totalPriceCell.textContent = sell.totalAmount.toFixed(2);
        row.appendChild(totalPriceCell);

        const amountPaidCell = document.createElement('td');
        amountPaidCell.textContent = sell.amountPaid.toFixed(2);
        row.appendChild(amountPaidCell);

        const changeDueCell = document.createElement('td');
        changeDueCell.textContent = sell.change.toFixed(2);
        row.appendChild(changeDueCell);

        const unpaidAmountCell = document.createElement('td');
        unpaidAmountCell.textContent = sell.unpaid.toFixed(2);
        row.appendChild(unpaidAmountCell);

        tableBody.appendChild(row);
    });

    table.appendChild(tableBody);
};
