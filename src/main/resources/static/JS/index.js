const ITEMS = [];
document.getElementById('addCustomer').addEventListener('click', async function (event) {
  event.preventDefault();
  
  
  const customer = getCustomerDetails();
  if (customer == null) {
    return null;
  }
  // Check if customer exists
  const customerDetails = await retrieveCustomerDetails(customer.phone);
  if (customerDetails) {
    if (customerDetails.debtToReturn === null) {
      customerDetails.debtToReturn = 0.00 + ' Ksh';
    }
    if (customerDetails.unpaidDebt === null) {
      customerDetails.unpaidDebt = 0.00 + ' Ksh';
    }
    // Change other fields to read with the new data
    document.getElementById('debt').textContent = `${customerDetails.debtToReturn}`;
    document.getElementById('remainder').textContent = `${customerDetails.unpaidDebt}`;
    alert(`The customer's details already exists as follows:\n
      \t Name: ${customerDetails.name}\n
      \t Debt: ${customerDetails.unpaidDebt}\n
      \t Change: ${customerDetails.debtToReturn}\n`);
    return;
  } else {
    const response = await addCustomer(customer);
    alert(response);
  }
  document.getElementById('heading').textContent = `Selling to ${customer.name}`;
});

document.getElementById('addItem').addEventListener('click', async function (event) {
  event.preventDefault();
  const itemName = document.getElementById('item-name').value;
  const item = await getItem(itemName);
  if (item === null) {
    alert("The provided item doesn't exist");
  }
  const requiredAmount = document.getElementById('item-quantity').value;
  // Check if required amount is empty
  if (requiredAmount.trim().length === 0) {
    alert("Please enter the required item's quantity");
    return;
  }
  // Check if the required number is more than the available stock
  if (item.quantity < requiredAmount) {
    alert(`The entered amount is more than the available stock of ${item.quantity}`);
    return;
  }
  item.quantity = requiredAmount;
  // Check if item was already added
  if (ITEMS.find(purchaseItems => purchaseItems.name === item.name)) {
    alert('The item already exists in the list');
    return;
  }
  addToTable(item);

});

document.getElementById('sendPayment').addEventListener('click', function (event) {
  event.preventDefault();

  const payments = getPayments();

  if (payments.change > 0) {
    document.getElementById('debt').textContent = payments.change.toFixed(2) + ' Ksh';
    document.getElementById('remainder').textContent = '0.00 Ksh';
  } else {
    document.getElementById('debt').textContent = '0.00 Ksh';
    document.getElementById('remainder').textContent = payments.newUnpaidDebt.toFixed(2) + ' Ksh';
  }

});

document.getElementById('sellButton').addEventListener('click', async function (event) {
  event.preventDefault();
  const customer = getCustomerDetails();
  const items = []
  if (ITEMS.length !== 0) {
    ITEMS.forEach(item => {
      items.push(item)
    })
  } else {
    alert("The items list is empty");
    return;
  }
  const payments = getPayments();
  const sell = {
    dateOfSale: new Date().toISOString(),
    customer: {
      name: customer.name,
      phone: customer.phone
    },
    items: items,
    totalAmount: payments.totalItemPrice,
    amountPaid: payments.totalPaid,
    change: payments.change,
    unpaid: payments.newUnpaidDebt
  };
  const url = 'http://localhost:8080/sell/new';
  const response = await fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(sell)
  });
  const data = await response.json();
  if (data.status === 'SUCCESS') {
    await sellItems();
  }
  alert(data.message);
})


/**
 * Helper function to send customer's details to the server
 * @param {customer} customer te customer's details
 * @returns a string containing the server's response
 */
const addCustomer = async function (customer) {
  // Check if customer is empty
  if (!customer) {
    return "Please enter the customer's details";
  }
  const url = 'http://localhost:8080/customer/new';
  let response = await fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(customer)
  });
  const data = await response.json();
  return data.message;
};

/**
 * Checks if the customer already exists
 * @param {string} customerPhone the customer's phone number
 * @returns the customer's details or null if it wasn't found
 */
const retrieveCustomerDetails = async function (customerPhone) {
  // Check if the phone number was provided
  const params = new URLSearchParams();
  params.append('phone', customerPhone);
  const url = `http://localhost:8080/customer/get?${params.toString()}`;
  try {
    const response = await fetch(url);
    const data = await response.json();
    if (data.customer == null) {
      return null;
    } else {
      return data.customer;
    }
  } catch (error) {
    alert(error);
  }
};

/**
 * Retrieves an item from the server
 * @param {string} itemName the name of the item to search for
 * @returns the item's details or null
 */
const getItem = async function (itemName) {
  // Check if item's name was provided
  if (itemName.trim().length === 0) {
    alert("Please provide the item's name.");
    return null;
  }
  try {
    const params = new URLSearchParams();
    params.append('itemName', itemName);
    const url = `http://localhost:8080/item/get?${params.toString()}`;
    const response = await fetch(url);
    const data = await response.json();
    return data.item;
  } catch (error) {
    alert(error);
  }
};

/**
 * Adds a new item to the html table
 * @param {item} item the item's data
 */
const addToTable = function (item) {
  const tableBody = document.getElementById('items-table').getElementsByTagName('tbody')[0];
  const row = tableBody.insertRow();
  const nameCell = row.insertCell().textContent = item.name;
  const descriptionCell = row.insertCell().textContent = item.description;
  const priceCell = row.insertCell().textContent = item.sellingPrice + ' Ksh';
  const quantityCell = row.insertCell().textContent = item.quantity;
  const totalPriceCell = row.insertCell().textContent = item.quantity * item.sellingPrice + ' Ksh';
  ITEMS.push(item);
  calculateTotalPrice();
};

/**
 * Calculates the total price
 */
const calculateTotalPrice = function () {
  const totalPrice = document.getElementById('total-price');
  let sum = 0;
  ITEMS.forEach(item => {
    sum += item.quantity * item.sellingPrice;
  });
  totalPrice.textContent = sum.toFixed(2) + ' Ksh';
};

/**
 * Reduces the items stock in the database
 * @returns a success message on selling items, else an error response
 */
const sellItems = async function () {
  if (ITEMS.length === 0) {
    alert('The item\'s list is empty');
    return;
  }

  for (const item of ITEMS) {
    const response = await fetchItemData(item.name); // Await the promise

    if (response && response.item) {
      response.item.quantity -= item.quantity;
      const updateResponse = await updateItem(response.item);
      if (updateResponse.status !== 'SUCCESS') {
        alert(updateResponse.message);
        return null;
      }
    } else {
      alert(response.message);
      return null;
    }
  }

  ITEMS.length = 0; // Clear items after sale
  alert("All items were successfully sold");
};




/**
 * Retrieves an item's data from the database
 * @param {string} name the name of the item to get
 * @returns the item's data or null
 */
const fetchItemData = async function (name) {
  const params = new URLSearchParams();
  params.append('itemName', name);
  const url = `http://localhost:8080/item/get?${params.toString()}`;
  try {
    const response = await fetch(url);
    return await response.json();
  } catch (error) {
    alert(error);
    return null;
  }
};

/**
 * Updates an item in the server
 * @param {item} item the item's data
 * @returns a custom response from the server
 */
const updateItem = async function (item) {
  const params = new URLSearchParams();
  params.append('initialName', item.name);
  params.append('newCategoryName', item.category.name);
  const url = `http://localhost:8080/item/update?${params.toString()}`;
  try {
    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item)
    });
    return await response.json();
  } catch (error) {
    alert(error);
  }
};

/**
 * Retreives a customers details from the UI
 * @returns customer's details or null
 */
const getCustomerDetails = function () {
  const customer = {
    name: document.getElementById('customer-name').value,
  phone: document.getElementById('customer-phone').value
  }
  if (customer.name.trim().length === 0) {
    alert("Please enter the customer's name!");
    return null;
  } else if (customer.phone.trim().length === 0) {
    alert("Please enter customer's phone number!");
    return null;
  }
  return customer;
}

/**
 * Retrieves the payment values from the UI
 * @returns An object of all payments
 */
const getPayments = function() {
  const totalItemPrice = Number(document.getElementById("total-price").textContent.split(' ')[0]);
  const totalPaid = Number(document.getElementById("price-paid").value);
  const previousUnpaidDebt = Number(document.getElementById('remainder').textContent.split(' ')[0]);
  const totalDebt = totalItemPrice + previousUnpaidDebt;

  let newUnpaidDebt = totalDebt - totalPaid;

  const change = (newUnpaidDebt < 0) ? Math.abs(newUnpaidDebt) : 0.00;
  newUnpaidDebt = (newUnpaidDebt < 0) ? 0.00 : newUnpaidDebt;

  return {
    totalItemPrice,
    totalPaid,
    previousUnpaidDebt,
    newUnpaidDebt,
    change
  };
};

/**
 * Calculates the change from payments
 * @returns the change
 */
const calculateChange = function () {
  const payments = getPayments();
  return payments.change;
}