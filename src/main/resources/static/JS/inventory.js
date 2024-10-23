
/**
 * Retrieves a list of all categories with their items
 * @returns A list of categories with their items or null
 */
const data = async function () {
  try {
    const categoryResponse = await fetch('http://localhost:8080/category');
    const categoryData = await categoryResponse.json();

    if (categoryData.status !== 'SUCCESS') {
      window.alert(categoryData.message);
      return null;
    }
    for (const category of categoryData.categories) {
      category.items = [];
      const itemParams = new URLSearchParams();
      itemParams.append('categoryId', category.id);
      const itemResponse = await fetch(`http://localhost:8080/item/all?${itemParams.toString()}`);
      const itemData = await itemResponse.json();
      if (itemData.status !== 'SUCCESS') {
        window.alert(itemData.message);
        continue;
      }
      if (itemData.items && itemData.items.length > 0) {
        itemData.items.forEach((item) => {
          delete item.category;
          category.items.push(item);
        });
      }
    }
    return categoryData.categories;
  } catch (error) {
    window.alert(error);
    return null;
  }
};

document.addEventListener('DOMContentLoaded', async function () {
  const outputSection = document.getElementById('outputSection');
  const categoriesList = await data();
  if (categoriesList === null) {
    window.alert('An error occurred');
    return;
  }
  categoriesList.forEach((category) => {
    const categoryDiv = document.createElement('div');
    categoryDiv.setAttribute('class', 'category-div');
    const categoryHeading = document.createElement('h2');
    categoryHeading.setAttribute('class', 'category-heading');
    categoryHeading.textContent = `Category: ${category.name}`;
    const categoryDescription = document.createElement('h3');
    categoryDescription.setAttribute('class', 'category-description');
    categoryDescription.textContent = `Decsription: ${category.description}`;
    const itemsTable = document.createElement('table');
    itemsTable.setAttribute('class', 'items-table');
    const itemsTableHeading = document.createElement('thead');
    const itemsTableRow = document.createElement('tr');
    const thName = document.createElement('th');
    thName.textContent = 'Item Name';
    const thPrice = document.createElement('th');
    thPrice.textContent = 'Selling Price';
    const thQuantity = document.createElement('th');
    thQuantity.textContent = 'Stock';
    const thBuyingPrice = document.createElement('th');
    thBuyingPrice.textContent = 'Buying Price';

    itemsTableRow.appendChild(thName);
    itemsTableRow.appendChild(thQuantity);
    itemsTableRow.appendChild(thPrice);
    itemsTableRow.appendChild(thBuyingPrice);
    itemsTableHeading.appendChild(itemsTableRow);
    itemsTable.appendChild(itemsTableHeading);

    // Create table body
    const itemsTableBody = document.createElement('tbody');
    category.items.forEach((item) => {
      const itemRow = document.createElement('tr');

      const itemNameCell = document.createElement('td');
      itemNameCell.textContent = item.name;
      itemNameCell.setAttribute('class', 'itemName');

      const itemPriceCell = document.createElement('td');
      itemPriceCell.textContent = item.sellingPrice;

      const itemBuyingPriceCell = document.createElement('td');
      itemBuyingPriceCell.textContent = item.buyingPrice;

      const itemQuantityCell = document.createElement('td');
      itemQuantityCell.textContent = item.quantity;

      itemRow.appendChild(itemNameCell);
      itemRow.appendChild(itemQuantityCell);
      itemRow.appendChild(itemPriceCell);
      itemRow.appendChild(itemBuyingPriceCell);

      itemsTableBody.appendChild(itemRow);
    });
    itemsTable.appendChild(itemsTableBody);
    categoryDiv.appendChild(categoryHeading);
    categoryDiv.appendChild(categoryDescription);
    categoryDiv.appendChild(itemsTable);
    outputSection.appendChild(categoryDiv);
  });
  removeItem('itemName');
  removeCategory('category-heading');
});

/**
 * Deletes an item for the database
 * @param {string} className The class identifier
 */
const removeItem = function (className) {
  const itemNames = document.getElementsByClassName(className);
  for (let i = 0; i < itemNames.length; i++) {
    itemNames[i].addEventListener('click', async function () {
      const deleteItem = window.confirm('Do you want to delete this item?');
      if (deleteItem) {
        try {
          const itemParam = new URLSearchParams();
          itemParam.append('itemName', itemNames[i].textContent);
          const response = await fetch(`http://localhost:8080/item/delete?${itemParam.toString()}`, {
            method: 'DELETE',
            headers: {
              'Content-Type': 'application/json'
            }
          });
          const data = await response.json();
          if (data.status !== 'SUCCESS') {
            window.alert(data.message);
            return;
          }
          window.alert(data.message);
          location.reload();
        } catch (error) {
          window.alert(error);
        }
      }
    });
  }
};

const removeCategory = function (className) {
  const categoryNames = document.getElementsByClassName(className);
  for (let i = 0; i < categoryNames.length; i++) {
    categoryNames[i].addEventListener('click', async function () {
      const deleteCategory = window.confirm(`Do you want to delete ${categoryNames[i].textContent}?`);
      if (deleteCategory) {
        const confirmDelete = prompt("Deleting this category will delete the items too!\nEnter the category's name if you're sure");
        if (confirmDelete && confirmDelete.toLowerCase().trim() === categoryNames[i].textContent.split(':')[1].toLowerCase().trim()) {
          try {
            const categoryParam = new URLSearchParams();
            categoryParam.append('name', confirmDelete);
            const url = `http://localhost:8080/category/delete?${categoryParam.toString()}`;
            const response = await fetch(url, {
              method: 'DELETE',
              headers: {
                'Content-Type': 'application/json'
              }
            });
            const data = await response.json();
            window.alert(data.message);

            if (data.status === 'SUCCESS') {
              const categorySection = categoryNames[i].closest('.category-div');
              categorySection.remove();
            }
          } catch (error) {
            window.alert(error);
          }
        } else {
          window.alert('Category name mismatch. Please enter the correct name.');
        }
      }
    });
  }
};
