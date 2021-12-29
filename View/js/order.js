$(document).ready(function () {

    $.ajax({
        dataType: 'json',
        url: 'http://localhost:8120/api/address/TTP',
        success: function (data1) {
            var htmls = data1.map(function (item) {
                return `<option value='${item.matp}'>${item.name}</option>`;
            })
            $('#province').html(`<option value="none">Vui lòng chọn Tỉnh/Thành</option>` + htmls.join(''));
        }
    });

    $('#province').on('change', function () {
        var selectedProvince = $(this).val();
        if (selectedProvince != 'none') {
            $.ajax({
                dataType: 'json',
                url: 'http://localhost:8120/api/address/QH?matp=' + selectedProvince,
                success: function (data2) {
                    var htmls = data2.map(function (item) {
                        return `<option value='${item.maqh}'>${item.name}</option>`;
                    })
                    $('#district').prop("disabled", false);
                    $('#district').html(`<option value="none">Vui lòng chọn Quận/Huyện</option>` +htmls.join(''));
                    $('#town').empty();
                    $('#town').prop("disabled", true);
                }
            });
        } else {
            $('#district').empty();
            $('#district').prop("disabled", true);
            $('#town').empty();
            $('#town').prop("disabled", true);
        }
    });

    $('#district').on('change', function () {
        var selectedDistrict = $(this).val();
        if (selectedDistrict!='none') {
            $.ajax({
                dataType: 'json',
                url: 'http://localhost:8120/api/address/XPTT?maqh=' + selectedDistrict,
                success: function (data3) {
                    var htmls = data3.map(function (item) {
                        return `<option value='${item.maqh}'>${item.name}</option>`;
                    })
                    $('#town').prop("disabled", false);
                    $('#town').html(`<option value="none">Vui lòng chọn Phường/Xã</option>`+htmls.join(''));
                }
            });
        } else {
            $('#town').empty();
            $('#town').prop("disabled", true);
        }
    });
});


function loadcart(){
    var cartstring = localStorage.getItem("cart");
    var cart = JSON.parse(cartstring);
    if (cart == null || cart.length == 0) {
        window.alert("Bạn chưa chọn sản phẩm!");
        window.location.href="shop.html";
    } else {
        var total = 0;
        var cartitems = cart.map(function (item) {
            total += item.pprice*item.quantity;
            return `
            <tr class="cart_item">
                <td class="product-name">
                    ${item.productname} <strong class="product-quantity"> × ${item.quantity}</strong>
                </td>
                <td class="product-total">
                    <span class="amount">`+formatCash((item.quantity*item.pprice)+'')+`đ</span>
                </td>
            </tr>
            `;
        })
        $('#checkout-list').html(cartitems.join(''));
        $('#mustPay').html(formatCash(total + '')+'đ');
    }
}

$('#checkout-form').on('submit', function(e) {
    e.preventDefault();
    let fullname = $('#fullname').val();
    let email = $('#email').val();
    let phonenumber = $('#phonenumber').val();
    let province = $('#province :selected').text();
    let district = $('#district :selected').text();
    let town = $('#town :selected').text();
    let addressdt = $('#dt').val();
    let address = addressdt + ', ' + town + ', ' + district + ', ' + province;
    let note = $('#checkout-mess').val();

    var cart = localStorage.getItem("cart");
    var pcart = JSON.parse(cart) != null ? JSON.parse(cart) : [];
    if (pcart.length == 0) {
        window.alert("Bạn chưa chọn sản phẩm");
        window.location.href = "shop.html";
    } else {
        var total = 0;

        var listItem = [];
        pcart.forEach(element => {
            total += element.pprice * element.quantity;
            let item = {
                productId: element.productid,
                quantity: element.quantity,
                price: element.pprice
            }
            listItem.push(item);
        });

        var order = {
            fullname: fullname,
            phoneNumber: phonenumber,
            email: email,
            address: address,
            total: total,
            note: note,
            items: listItem
        }

        data = JSON.stringify(order);
        console.log(data);
        $.ajax({
            type: 'POST',
            contentType: "application/json",
            url: 'http://localhost:8120/api/orders',
            data: data,
            success: function (res) {
                $.ajax({
                    type: 'GET',
                    url: 'http://localhost:8120/api/order/'+ res.orderId,
                    success: function (data) {
                        
                    },
                    error: function (res) {
                    }
                })
                cleanCart();
                window.location.href = "review-order.html?id="+res.orderId;
            },
            error: function (res) {
                console.log(res);
            }
        });
    }
})

function cleanCart(){
    localStorage.removeItem("cart");
}

loadcart();