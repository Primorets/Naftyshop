// Функция для получения списка товаров с сервера
function fetchProducts() {
    fetch('/api/products')
        .then(response => response.json())
        .then(data => {
            const productList = document.getElementById('productList');
            productList.innerHTML = '';
            data.forEach(product => {
                const productElement = document.createElement('div');
                productElement.classList.add('product');
                productElement.innerHTML = `
                    <h3>${product.name}</h3>
                    <p>Категория: ${product.category}</p>
                    <p>Цена: ${product.price} руб.</p>
                `;
                productList.appendChild(productElement);
            });
        })
        .catch(error => console.error('Ошибка при получении товаров:', error));
}

// Функция для добавления нового товара
function addProduct(event) {
    event.preventDefault();
    const name = document.getElementById('name').value;
    const category = document.getElementById('category').value;
    const price = document.getElementById('price').value;

    fetch('/api/products', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name, category, price })
    })
        .then(response => response.json())
        .then(data => {
            console.log('Товар добавлен:', data);
            // Очистка формы
            document.getElementById('name').value = '';
            document.getElementById('category').value = '';
            document.getElementById('price').value = '';
            // Обновление списка товаров
            fetchProducts();
        })
        .catch(error => console.error('Ошибка при добавлении товара:', error));
}

// Привязка обработчика событий к форме добавления товара
document.getElementById('addProductForm').addEventListener('submit', addProduct);

// Инициализация: получение списка товаров при загрузке страницы
window.addEventListener('load', fetchProducts);