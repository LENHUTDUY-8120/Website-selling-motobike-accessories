var id = GetParameterValues('id');

function GetParameterValues(param) {
    var url = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for (var i = 0; i < url.length; i++) {
        var urlparam = url[i].split('=');
        if (urlparam[0] == param) {
            return urlparam[1];
        }
    }
    return null;
}

function getProduct() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8120/api/products/' + id,
        success: function (data) {
            renderProduct(data);
        },
        error: function (res) {
        }
    })
}

function renderProduct(data) {
    var count = 0;
    var images = data.images.map(function (image) {
        count++;
        if (count == 1) {
            return `
            <div id="thumb${count}" class="tab-pane active">
                <a data-fancybox="images" href="http://localhost:8120/images/productImages/${image}"><img src="http://localhost:8120/images/productImages/${image}" alt="product-view"></a>
            </div>
            `;
        } else {
            return `
            <div id="thumb${count}" class="tab-pane">
                <a data-fancybox="images" href="http://localhost:8120/images/productImages/${image}"><img src="http://localhost:8120/images/productImages/${image}" alt="product-view"></a>
            </div>
            `;
        }
    });
    count = 0;
    var images1 = data.images.map(function (image) {
        count++;
        if (count == 1) {
            return `
            <a class="active" data-bs-toggle="tab" href="#thumb${count}"> <img src="http://localhost:8120/images/productImages/${image}" alt="product-thumbnail"></a>
            `;
        } else {
            return `
            <a data-bs-toggle="tab" href="#thumb${count}"> <img src="http://localhost:8120/images/productImages/${image}" alt="product-thumbnail"></a>
            `;
        }
    });
    var stars = [];
    for (let i = 1; i <= 5; i++) {
        if (i <= data.rate) {
            stars.push('<i class="fa fa-star checked"></i>');
        } else {
            stars.push('<i class="fa fa-star"></i>');
        }
    }
    var rate = stars.join('');
    var price = `
        <p><span class="price" style="color:red">`+ formatCash(data.price + '') + `đ</span><span class="sku" style="margin-left: 10px;"> .Đơn vị: ${data.donVi}</span></p>
    `;
    var listXe = data.xe.map(function (xe) {
        return `${xe}`;
    })
    var breadcrubm = `
        <a href="#">${data.title}</a>
    `;
    var image1 = data.images[0];
    if (data.quantity == 1) {
        $('#product-status').html('<span style="font-size:20px;" class="in-stock">Còn hàng</span>');
        var box = `
            <input class="number" id="numeric" type="number" min="1" value="1" max="10">
            <a id="add-cart" class="add-cart" href="#" onclick="addtocart(${data.id},'${image1}',${data.price},'${data.title}')">Thêm vào giỏ</a>
        `;
        $('#product-box').html(box);
    } else {
        $('#product-status').html('<span style="font-size:20px;color:red;" class="in-stock">Tạm hết hàng</span>');
    }
    $('#img-content').html(images.join(''));
    $('#thumb-img-menu').html(images1.join(''));
    $('#product-name').text(data.title);
    $('#product-code').text(data.productCode);
    $('#product-rate').html(rate);
    $('#product-price').html(price);
    $('#product-xe').html(listXe.join(','));
    $('#product-breadcrumb').html(breadcrubm);
    $('#dtail').html(data.describes);
}


function addtocart(pid, pimg, price, pname) {
    var pquantity = parseInt($('#numeric').val());
    var cart = localStorage.getItem("cart");
    var pcart = JSON.parse(cart) != null ? JSON.parse(cart) : [];
    //get index of the json array where the productid is there ...
    var present_or_not = pcart.findIndex(item => item.productid == pid);
    //if the item not presnt , is null
    if (cart == null || present_or_not == null || present_or_not == -1) {
        var product = {
            productid: pid,
            productname: pname,
            productImg: pimg,
            pprice: price,
            quantity: pquantity
        };
        pcart.push(product);
        localStorage.setItem("cart", JSON.stringify(pcart));

    } else {
        //get the the json from index...
        var actual_stored_product = pcart[present_or_not];
        pcart.splice(present_or_not, 1); //remove the json array 
        //get the qty which was already prsnt
        var actual_qty = actual_stored_product.quantity == null || actual_stored_product.quantity == "" ? 0 : actual_stored_product.quantity;
        //update the value
        actual_stored_product.quantity = parseInt(actual_qty) + parseInt($("#quantity").val());
        //now..we have updated value..push obj again..
        pcart.push(actual_stored_product);
        //store the json in local Storage
        localStorage.setItem("cart", JSON.stringify(pcart));
    }
    $('#add-cart').css("background-color", "black");
    updateLenghtCart();
    renderCart();
}

function getAllCmt() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8120/api/comments/' + id,
        success: function (data) {
            console.log(data);
            if (data.length > 0) {
                var comments = data.map(function (cmt) {
                    var stars = [];
                    for (let i = 1; i <= 5; i++) {
                        if (i <= cmt.rate) {
                            stars.push('<i class="fa fa-star checked"></i>');
                        }
                    }
                    return `
                <h4 class="review-mini-title"><i style="font-size: 30px;" class="fas fa-user-alt"></i> ${cmt.fullname}</h4>
                <li>
                    `+ stars.join('') + `
                    <p>${cmt.content}</p>
                </li>
                <hr style="border: 1px solid; margin: 20px 0px;">
                `;
                })
                $('#list-comment').html(comments.join(''));
            }
        },
        error: function (res) {
        }
    })
}

$(document).ready(function () {
    $('#cmt-form').on('submit', function (e) {

        e.preventDefault();
        var comment = {
            fullname: $('#sure-name').val(),
            phoneNumber: $('#sure-phone').val(),
            content: $('#comments').val(),
            rate: parseInt($('#example').val())
        }

        console.log(JSON.stringify(comment));
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: 'http://localhost:8120/api/comments/' + id,
            data: JSON.stringify(comment),
            success: function (data) {
                console.log(data);
                var stars = [];
                for (let i = 1; i <= 5; i++) {
                    if (i <= data.rate) {
                        stars.push('<i class="fa fa-star checked"></i>');
                    }
                }
                comments = `
                    <h4 class="review-mini-title"><i style="font-size: 30px;" class="fas fa-user-alt"></i> ${data.fullname}</h4>
                    <li>
                        `+ stars.join('') + `
                        <p>${data.content}</p>
                    </li>
                    <hr style="border: 1px solid; margin: 20px 0px;">
                    `;
                $('#list-comment').append(comments);
                $('#reviews').hide();
            },
            error: function (res) {
            }
        })
    })
})


$(function () {
    $('#example').barrating({
        theme: 'bars-movie'
    });
});

getProduct();
getAllCmt();