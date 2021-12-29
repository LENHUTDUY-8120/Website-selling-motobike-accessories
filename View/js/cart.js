
function renderAllCart() {
    var cartstring = localStorage.getItem("cart");
    var cart = JSON.parse(cartstring);
    if (cart == null || cart.length == 0) {
        var html = `
            <h2>Bạn chưa chọn sản phẩm</h2>
        `;
        $('#cart-container').html(html);
        $('#checkout').hide();
    } else {
        var htmls = cart.map(function (item) {
            return `
            <tr class="cart_row">
                <td class="product-thumbnail">
                    <a href="#"><img src="http://localhost:8120/images/productImages/${item.productImg}" alt="cart-image" /></a>
                </td>
                <td class="product-name"><a href="details.html?id=${item.productid}">${item.productname}</a></td>
                <td class="product-price"><span value="${item.pprice}"class="amount cart-price">${item.pprice}</span></td>
                <td class="product-quantity"><input class="cart-quantity-input" onchange="updatecart(${item.productid},this.value)" type="number" value="${item.quantity}" min="1" max="10"/></td>
                <td class="product-subtotal sub-total">`+formatCash((item.pprice*item.quantity)+'')+`đ</td>
                <td class="product-remove"> <a href="#" onclick="remove(${item.productid})"><i class="fa fa-times" aria-hidden="true"></i></a></td>
            </tr>
            `;
        })
        $('#cart-body').html(htmls.join(''));
    }
    updatecart(null, null);
}

function updatecart(pid, qtt) {
    if (pid !== null && qtt !== null) {
        var cart = localStorage.getItem("cart");
        var pcart = JSON.parse(cart) != null ? JSON.parse(cart) : [];
        var present_or_not = pcart.findIndex(item => item.productid == pid);
        var actual_stored_product = pcart[present_or_not];
        pcart.splice(present_or_not, 1);
        actual_stored_product.quantity = parseInt(qtt);
        // pcart.push(actual_stored_product);
        pcart.splice(present_or_not, 0, actual_stored_product);
        localStorage.setItem("cart", JSON.stringify(pcart));
    }

    var cart_rows = document.getElementsByClassName("cart_row");
    var total = 0;
    for (var i = 0; i < cart_rows.length; i++) {
        var cart_row = cart_rows[i];
        var price_item = cart_row.getElementsByClassName("cart-price")[0].innerHTML;
        var pprice = price_item.replace(/[^\x00-\x7F]/g, "");
        var quantity_item = cart_row.getElementsByClassName("cart-quantity-input")[0];
        var quantity = parseInt(quantity_item.value);
        var price = parseInt(pprice);
        var subPrice = price * quantity;
        document.getElementsByClassName("sub-total")[i].innerText = formatCash(subPrice + '') + ' VND';
        total = total + (price * quantity);
    }
    document.getElementsByClassName("cart-total-price")[0].innerText = formatCash(total + '') + ' VND';
}

renderAllCart();
