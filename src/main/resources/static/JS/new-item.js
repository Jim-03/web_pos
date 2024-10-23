
document.addEventListener('DOMContentLoaded', function () {
  const categorySelect = document.getElementById('item-category');
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
    name: document.getElementById('item-name').value,
    quantity: document.getElementById('quantity').value,
    buyingPrice: Number(document.getElementById('buying-price').value),
    sellingPrice: Number(document.getElementById('selling-price').value)
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
  const categoryName = document.getElementById('item-category').value;
  const param = new URLSearchParams();
  param.append('categoryName', categoryName);
  const url = 'http://localhost:8080/item/submit';
  try {
    const response = await fetch(`${url}?${param.toString()}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item)
    });
    const result = await response.json();
    alert(result.message);
  } catch (error) {
    alert(error);
    document.getElementById('item-name').textContent = '';
    document.getElementById('quantity').textContent = '';
    document.getElementById('buying-price').textContent = '';
    document.getElementById('selling-price').textContent = '';
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
