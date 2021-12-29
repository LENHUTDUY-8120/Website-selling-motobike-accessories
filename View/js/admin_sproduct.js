var id = GetParameterValues('id');
var isUpdate = false;
var isSave = true;

$('#title').text(id == null ? 'Thêm mới' : 'Chỉnh sửa')

function getspchung() {
    $.ajax({
        dataType: 'json',
        url: 'http://localhost:8120/api/category',
        success: function (data) {
            var htmls = data.map(function (item) {
                return `<option value='${item.code}'>${item.title}</option>`;
            })
            $('#spchung').append(htmls.join(''));
        }
    });
}

function getsprieng(code, subCode) {
    $.ajax({
        dataType: 'json',
        url: 'http://localhost:8120/api/sub-category',
        data: {
            code: code
        },
        success: function (data) {
            var htmls = data.map(function (item) {
                return `<option value='${item.code}'>${item.title}</option>`;
            })
            $('#sprieng').append(htmls.join(''));
            if (subCode != null) {
                $("#sprieng").val(subCode).change();
                isUpdate = false;
            }
        }
    });
}

function getAllXe() {
    $.ajax({
        dataType: 'json',
        url: 'http://localhost:8120/api/xe',
        success: function (data) {
            var htmls = data.map(function (item) {
                return `<li class="item-xe"><input class="xecheck" value="${item.codeXe}" type="checkbox" />${item.tenXe}</li>`;
            })
            $('#list-xe').html(htmls.join(''));
        }
    });
}

function clearSelected() {
    $('#sprieng').html('<option value="0">-- Chọn loại sản phẩm riêng</option>');
}

getspchung();
getAllXe();

$('#spchung').on('change', function () {
    if (isUpdate == false) {
        clearSelected();
        getsprieng($(this).val(), null);
    }
})

if (id !== null) {
    initValue(getsprieng);
}

$('#product-form').on('submit', function (e) {

    e.preventDefault();
    var formData = new FormData();
    var total_file = $('#file-input').get(0).files.length;
    for (let i = 0; i < total_file; i++) {
        formData.append('images', $('#file-input').get(0).files[i]);
    }
    var listXe = [];
    $('ul.items').find("input:checkbox:checked").each(function () {
        listXe.push($(this).val());
    });
    var product = {
        id:id,
        title: $('#ptitle').val(),
        productCode: $('#pcode').val(),
        donVi: $('#don-vi').val(),
        price: $('#price').val(),
        subCategoryCode: $('#sprieng').val(),
        describes: editor.getData(),
        listXe: listXe
    }
    data = JSON.stringify(product);
    formData.append('product', data);
    if (isSave == true) {
        console.log("save");
        $.ajax({
            type: 'POST',
            contentType: false,
            processData: false,
            url: 'http://localhost:8120/api/admin/products',
            data: formData,
            success: function (res) {
                alert("Thêm sản phẩm mới thành công!");
                window.location.href = "product.html";
            },
            error: function (res) {
                alert("Thêm sản phẩm mới thất bại!");
            }
        });
    } else {
        console.log("update");
        $.ajax({
            type: 'PUT',
            contentType: false,
            processData: false,
            url: 'http://localhost:8120/api/admin/products/' + id,
            data: formData,
            success: function (res) {
                isUpdate = true;
                alert("Cập nhật sản phẩm thành công!");
                window.location.href = "product.html";
            },
            error: function (res) {
                alert("Cập nhật sản phẩm thất bại!");
            }
        });
    }
})

function initValue(sprieng) {

    isSave = false;
    isUpdate = true;
    $.ajax({
        dataType: 'json',
        url: 'http://localhost:8120/api/admin/products/' + id,
        success: function (data) {
            $('#ptitle').val(`${data.title}`);
            $('#pcode').val(data.productCode);
            $('#don-vi').val(`${data.donVi}`).change();
            $('#price').val(`${data.price}`);
            $("#spchung").val(data.categoryCode).change();
            sprieng(data.categoryCode, data.subCategoryCode);
            editor.setData(`${data.describes}`);
            $('.xecheck').each(function () {
                data.listXe.forEach(element => {
                    if (element == $(this).val()) {
                        $(this).prop('checked', true);
                    }
                });
            })
            var preview = document.querySelector('#preview');
            data.images.forEach(function (img) {
                var image = new Image();
                image.height = 100;
                image.src = "http://localhost:8120/images/productImages/" + img;
                preview.appendChild(image);
            });
        }
    });
}

function getUrl() {
    var url = "http://localhost:8082/api/admin/products"
    if (id == null) {
        return url;
    } else {
        return url + "?id=" + id;
    }
}

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
