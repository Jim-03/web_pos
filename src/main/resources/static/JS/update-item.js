const initialData = async function (itemName) {
  try {
    const itemNameParam = new URLSearchParams();
    itemNameParam.append('itemName', itemName);
    const response = await fetch(`http://localhost:8080/item/get?${itemNameParam.toString()}`);
    const data = await response.json();
    if (data.status !== 'SUCCESS') {
      window.alert(data.message);
      return null;
    }
    return data.item;
  } catch (error) {
    window.alert(`An error has occured: ${error}`);
    return null;
  }
};

document.getElementById('search-button').addEventListener('click', async function () {
  const searchName = document.getElementById('search-text').value;
  const item = await initialData(searchName);
  if (item === null) {
    window.alert('An error occurred while fetching data');
  }
  document.getElementById('initial-name').textContent = item.name;
  document.getElementById('initial-category').textContent = item.category.name;
  document.getElementById('initial-quantity').textContent = item.quantity;
  document.getElementById('initial-buying-price').textContent = item.buyingPrice;
  document.getElementById('initial-selling-price').textContent = item.sellingPrice;
});
document.addEventListener('DOMContentLoaded', function () {
  const categorySelect = document.getElementById('new-category');
  const url = 'http://localhost:8080/category/all';

  fetch(url)
    .then(response => response.json())
    .then(categories => {
      categories.forEach(category => {
        const option = document.createElement('option');
        option.value = category;
        option.textContent = category;
        categorySelect.appendChild(option);
      });
    })
    .catch(error => console.error('Error fetching categories:', error));
});

document.getElementById('submit-button').addEventListener('click', async function (event) {
  event.preventDefault();

  const item = {
    name: document.getElementById('new-name').value,
    quantity: document.getElementById('new-quantity').value,
    buyingPrice: Number(document.getElementById('new-buying-price').value),
    sellingPrice: Number(document.getElementById('new-selling-price').value)
  };
  if (item.name.trim().length === 0) {
    alert("Please provide the item's name");
    return;
  } else if (item.quantity <= 0) {
    alert('Please enter a valid quanity');
    return;
  } else if (item.buyingPrice <= 0) {
    alert('Please enter a valid buying price');
    return;
  } else if (item.sellingPrice <= 0) {
    alert('Please enter a valid selling price');
    return;
  }
  const newCategoryName = document.getElementById('new-category').value;
  const initialName = document.getElementById('initial-name').value;
  const param = new URLSearchParams();
  param.append('initialName', initialName);
  param.append('newCategoryName', newCategoryName);
  const url = 'http://localhost:8080/item/update';
  try {
    const response = await fetch(`${url}?${param.toString()}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item)
    });
    const result = await response.json();
    alert(result.message);
  } catch (error) {
    alert(error);
    document.getElementById('new-name').textContent = '';
    document.getElementById('new-quantity').textContent = '';
    document.getElementById('new-buying-price').textContent = '';
    document.getElementById('new-selling-price').textContent = '';
  }
});
document.getElementById('other').addEventListener('click', function (event) {
  const newCategoryName = prompt("Enter the new category's name");
  const newCategoryDescription = prompt("Enter the new category's description");
  if (newCategoryName.trim().length === 0) {
    alert('The category needs a name');
  }
  const url = 'http://localhost:8080/category/new';
  const category = {
    name: newCategoryName,
    description: newCategoryDescription
  };
  fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(category)
  })
    .then(response => {
      return response.json();
    })
    .then(data => {
      alert(data.message);
    }).catch(error => {
      alert(error);
    });
}
);
