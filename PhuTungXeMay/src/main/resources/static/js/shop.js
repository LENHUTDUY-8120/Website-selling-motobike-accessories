var url = "http://localhost:8120/api/products";
var pageParam = GetParameterValues('page');
var codeXeParam = GetParameterValues('codeXe');
var categoryParam = GetParameterValues('category');
var subCategoryParam = GetParameterValues('subCategory');
var sortType = GetParameterValues('sort');

if (sortType != null) {
    $('#sorter').val(sortType);
} else {
    $('#sorter').val('random');
}

function addtocart(pid, pimg, price, pname) {
    var pquantity = 1;
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
    renderCart();
    updateLenghtCart()
}

function showXeList() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8120/api/nav-xe',
        success: function (data) {
            var htmls = data.map(function (xe) {
                return `
                    <li><a href="shop.html?codeXe=${xe.codeXe}">${xe.tenXe} (${xe.numP})</a></li>
                `
            })
            $('#xe-list').html(htmls.join(''));
        },
        error: function (res) {
        }
    })
}

function getProductApi(renderProduct, page) {
    console.log(getUrli());
    $.ajax({
        type: 'GET',
        url: getUrli(),
        success: function (data) {
            renderProduct(data, page);
        },
        error: function (res) {
        }
    })
}


function renderProduct(data, pg) {
    var temp = 0;
    var rows = [];
    var htmls = data.products.map(function (item) {
        temp += 1;
        var pprice = formatCash(item.price + '');
        var image1 = item.images[0];
        var image2 = item.images[1];
        var stars = [];
        for (let i = 1; i <= 5; i++) {
            if (i <= item.rate) {
                stars.push('<i class="fa fa-star checked"></i>');
            } else {
                stars.push('<i class="fa fa-star"></i>');
            }
        }
        var rate = stars.join('');
        var single = `
        <div class="col-lg-4 col-sm-6">
            <div class="single-product">
                <!-- Product Image Start -->
                <div class="pro-img">
                    <a href="details.html?id=${item.id}">
                        <img class="primary-img" src="http://localhost:8120/images/productImages/${image1}"
                            alt="single-product">
                        <img class="secondary-img" src="http://localhost:8120/images/productImages/${image2}"
                            alt="single-product">
                    </a>
                </div>
                <!-- Product Image End -->
                <!-- Product Content Start -->
                <div class="pro-content">
                    <div class="product-rating">
                        ${rate}
                    </div>
                    <h4><a href="details.html?id=${item.id}">${item.title}</a></h4>
                    <p style="color:red;"><span id="pprice" class="price">${pprice}đ</span></p>
                    <div class="pro-actions">
                        <div class="actions-secondary">
                            <a class="add-cart" onclick="addtocart(${item.id},'${image1}',${item.price},'${item.title}')" href="#" data-toggle="tooltip"
                                title="Thêm vào giỏ">Thêm vào giỏ</a>
                        </div>
                    </div>
                </div>
                <!-- Product Content End -->
            </div>
        </div>
        `
        rows.push(single);
        if (temp % 3 == 0) {
            htmlrow = rows.join('');
            rows = [];
            return `
            <div class="row">
                ${htmlrow}
            </div>
            `
        }
    })
    var nl = `
        <div class="row">
            `+ rows.join('') + `
        </div>
    `
    htmls.push(nl);
    $('#grid-view').html(htmls.join(''));
    if (temp > 0) {
        if (data.totalPages > 1) {
            if (pageParam === null || pageParam == 1) {
                $('#numItem').html(`
                    <li><span class="grid-item-list"> Sản phẩm 1-`+ (1 * 9) + ` trong ` + (data.totalPages * 9) + `</span></li>
                `);
                pg(data.totalPages, 0);
            } else {
                $('#numItem').html(`
                    <li><span class="grid-item-list"> Sản phẩm `+ ((pageParam - 1) * 9 + 1) + `-` + ((pageParam - 1) * 9 + 9) + ` trong ` + (data.totalPages * 9) + `</span></li>
                `);
                pg(data.totalPages, pageParam - 1);
            }
        }
    }
    else {
        $('#all-product').html(`
            <h3 style="margin-left:20%;">Hiện tại chưa có sản phẩm phù hợp :))</h3>
        `);
    }
}


function getUrli() {
    var uri = getUrl();
    var sorter = $('#sorter').val();
    if (pageParam == null && codeXeParam == null && categoryParam == null && subCategoryParam == null) {
        if (sorter == 'des') {
            uri = uri + '?sort=des';
        } else if (sorter == 'asc') {
            uri = uri + '?sort=asc';
        }
        return uri;
    } else {
        if (sorter == 'des') {
            uri = uri + '&sort=des';
        } else if (sorter == 'asc') {
            uri = uri + '&sort=asc';
        }
        return uri;
    }
    
}

function getUrl() {
    if (codeXeParam == null) {
        if (categoryParam == null) {
            if (subCategoryParam == null) {
                if (pageParam != null) {
                    return url + '?page=' + (pageParam - 1);
                } else if (pageParam == null) {
                    return url;
                }
            } else {
                if (subCategoryParam != null && pageParam != null) {
                    return url + '?page=' + (pageParam - 1) + '&subCategory=' + subCategoryParam + '';
                } else if (subCategoryParam != null && pageParam == null) {
                    return url + '?subCategory=' + subCategoryParam + '';
                } else if (pageParam != null) {
                    return url + '?page=' + (pageParam - 1) + '';
                } else {
                    return url;
                }
            }
        } else {
            if (categoryParam != null && pageParam != null) {
                return url + '?page=' + (pageParam - 1) + '&category=' + categoryParam + '';
            } else if (categoryParam != null && pageParam == null) {
                return url + '?category=' + categoryParam + '';
            } else if (pageParam != null) {
                return url + '?page=' + (pageParam - 1) + '';
            } else {
                return url;
            }
        }
    } else {
        if (codeXeParam != null && pageParam != null) {
            return url + '?page=' + (pageParam - 1) + '&codeXe=' + codeXeParam + '';
        } else if (codeXeParam != null && pageParam == null) {
            return url + '?codeXe=' + codeXeParam + '';
        } else if (pageParam != null) {
            return url + '?page=' + (pageParam - 1) + '';
        } else {
            return url;
        }
    }
}
var sorter = $('#sorter').val();
function page(totalPages, currentPage) {
    current = currentPage + 1;
    var page = pagination(current, totalPages);

    var sortparam = '';
    if (sorter == 'des') {
        sortparam = '&sort=des';
    } else if (sorter == 'asc') {
        sortparam = '&sort=asc';
    }

    if (codeXeParam == null) {
        if (categoryParam == null) {
            if (subCategoryParam == null) {
                if (current === 1) {
                    var html = page.map(pg => {
                        if (pg == currentPage + 1) {
                            return `
                        <li class="active"><a href="#" disabled="disabled">${pg}</a></li>
                        `
                        } else if (pg == '...') {
                            return `
                            <li><a href="#" disabled="disabled">${pg}</a></li>
                            `
                        } else {
                            return `
                        <li><a href="shop.html?page=${pg}` + sortparam + `" >${pg}</a></li>
                        `
                        }
                    })
                    var htmlNext = `
                        <li><a href="shop.html?page=`+ (current + 1) + sortparam + `"><i class="fa fa-angle-right"></i></a></li>
                    `;
                    var htmls = html.join('') + htmlNext;
                } else if (current >= totalPages) {
                    var htmlPre = `
                    <li><a href="shop.html?page=`+ (current - 1) + sortparam + `"><i class="fa fa-angle-left"></i></a></li>
                    `;
                    html = page.map(pg => {
                        if (pg == currentPage + 1) {
                            return `
                        <li class="active"><a href="#" disabled="disabled">${pg}</a></li>
                        `
                        } else if (pg == '...') {
                            return `
                            <li><a href="#" disabled="disabled">${pg}</a></li>
                            `
                        } else {
                            return `
                        <li><a href="shop.html?page=${pg}` + sortparam + `" >${pg}</a></li>
                        `
                        }
                    })
                    var htmls = htmlPre + html.join('');
                } else {
                    var htmlPre = `
                    <li><a href="shop.html?page=`+ (current - 1) + sortparam + `"><i class="fa fa-angle-left"></i></a></li>
                    `;
                    html = page.map(pg => {
                        if (pg == currentPage + 1) {
                            return `
                        <li class="active"><a href="#" disabled="disabled">${pg}</a></li>
                        `
                        } else if (pg == '...') {
                            return `
                            <li><a href="#" disabled="disabled">${pg}</a></li>
                            `
                        } else {
                            return `
                        <li><a href="shop.html?page=${pg}` + sortparam + `" >${pg}</a></li>
                        `
                        }
                    })
                    var htmlNext = `
                    <li><a href="shop.html?page=`+ (current + 1) + sortparam + `"><i class="fa fa-angle-right"></i></a></li>
                    `;
                    var htmls = htmlPre + html.join('') + htmlNext;
                }
            } else {
                if (current === 1) {
                    html = page.map(pg => {
                        if (pg == currentPage + 1) {
                            return `
                            <li class="active"><a href="#" disabled="disabled">${pg}</a></li>
                        `
                        } else if (pg == '...') {
                            return `
                            <li><a href="#" disabled="disabled">${pg}</a></li>
                            `
                        } else {
                            return `
                        <li><a href="shop.html?page=${pg}&subCategory=${subCategoryParam}" >${pg}</a></li>
                        `
                        }
                    })
                    var htmlNext = `
                        <li><a href="shop.html?page=`+ (current + 1) + sortparam + `&subCategory=${subCategoryParam}"><i class="fa fa-angle-right"></i></a></li>
                        `;
                    var htmls = html.join('') + htmlNext;
                } else if (current >= totalPages) {
                    var htmlPre = `
                    <li><a href="shop.html?page=`+ (current - 1) + sortparam + `&subCategory=${subCategoryParam}"><i class="fa fa-angle-left"></i></a></li>
                    `;
                    html = page.map(pg => {
                        if (pg == currentPage + 1) {
                            return `
                            <li class="active"><a href="#" disabled="disabled">${pg}</a></li>
                        `
                        } else if (pg == '...') {
                            return `
                            <li><a href="#" disabled="disabled">${pg}</a></li>
                            `
                        } else {
                            return `
                        <li><a href="shop.html?page=${pg}&subCategory=${subCategoryParam}" >${pg}</a></li>
                        `
                        }
                    })
                    var htmls = htmlPre + html.join('');
                } else {
                    var htmlPre = `
                    <li><a href="shop.html?page=`+ (current - 1) + sortparam + `&subCategory=${subCategoryParam}"><i class="fa fa-angle-left"></i></a></li>
                    `;
                    html = page.map(pg => {
                        if (pg == currentPage + 1) {
                            return `
                            <li class="active"><a href="#" disabled="disabled">${pg}</a></li>
                        `
                        } else if (pg == '...') {
                            return `
                            <li><a href="#" disabled="disabled">${pg}</a></li>
                            `
                        } else {
                            return `
                        <li><a href="shop.html?page=${pg}&subCategory=${subCategoryParam}" >${pg}</a></li>
                        `
                        }
                    })
                    var htmlNext = `
                    <li><a href="shop.html?page=`+ (current + 1) + sortparam + `&subCategory=${subCategoryParam}"><i class="fa fa-angle-right"></i></a></li>
                    `;
                    var htmls = htmlPre + html.join('') + htmlNext;
                }
            }
        } else {
            if (current === 1) {
                html = page.map(pg => {
                    if (pg == currentPage + 1) {
                        return `
                        <li class="active"><a href="#" disabled="disabled">${pg}</a></li>
                    `
                    } else if (pg == '...') {
                        return `
                        <li><a href="#" disabled="disabled">${pg}</a></li>
                        `
                    } else {
                        return `
                    <li><a href="shop.html?page=${pg}&category=${categoryParam}" >${pg}</a></li>
                    `
                    }
                })
                var htmlNext = `
                    <li><a href="shop.html?page=`+ (current + 1) + sortparam + `&category=${categoryParam}"><i class="fa fa-angle-right"></i></a></li>
                    `;
                var htmls = html.join('') + htmlNext;
            } else if (current >= totalPages) {
                var htmlPre = `
                <li><a href="shop.html?page=`+ (current - 1) + sortparam + `&category=${categoryParam}"><i class="fa fa-angle-left"></i></a></li>
                `;
                html = page.map(pg => {
                    if (pg == currentPage + 1) {
                        return `
                        <li class="active"><a href="#" disabled="disabled">${pg}</a></li>
                    `
                    } else if (pg == '...') {
                        return `
                        <li><a href="#" disabled="disabled">${pg}</a></li>
                        `
                    } else {
                        return `
                    <li><a href="shop.html?page=${pg}&category=${categoryParam}" >${pg}</a></li>
                    `
                    }
                })
                var htmls = htmlPre + html.join('');
            } else {
                var htmlPre = `
                <li><a href="shop.html?page=`+ (current - 1) + sortparam + `&category=${categoryParam}"><i class="fa fa-angle-left"></i></a></li>
                `;
                html = page.map(pg => {
                    if (pg == currentPage + 1) {
                        return `
                        <li class="active"><a href="#" disabled="disabled">${pg}</a></li>
                    `
                    } else if (pg == '...') {
                        return `
                        <li><a href="#" disabled="disabled">${pg}</a></li>
                        `
                    } else {
                        return `
                    <li><a href="shop.html?page=${pg}&category=${categoryParam}" >${pg}</a></li>
                    `
                    }
                })
                var htmlNext = `
                <li><a href="shop.html?page=`+ (current + 1) + sortparam + `&category=${categoryParam}"><i class="fa fa-angle-right"></i></a></li>
                `;
                var htmls = htmlPre + html.join('') + htmlNext;
            }
        }
    } else {
        if (current === 1) {
            html = page.map(pg => {
                if (pg == currentPage + 1) {
                    return `
                    <li class="active"><a href="#" disabled="disabled">${pg}</a></li>
                `
                } else if (pg == '...') {
                    return `
                    <li><a href="#" disabled="disabled">${pg}</a></li>
                    `
                } else {
                    return `
                <li><a href="shop.html?page=${pg}&codeXe=${codeXeParam}" >${pg}</a></li>
                `
                }
            })
            var htmlNext = `
                <li><a href="shop.html?page=`+ (current + 1) + sortparam + `&codeXe=${codeXeParam}"><i class="fa fa-angle-right"></i></a></li>
                `;
            var htmls = html.join('') + htmlNext;
        } else if (current >= totalPages) {
            var htmlPre = `
            <li><a href="shop.html?page=`+ (current - 1) + sortparam + `&codeXe=${codeXeParam}"><i class="fa fa-angle-left"></i></a></li>
            `;
            html = page.map(pg => {
                if (pg == currentPage + 1) {
                    return `
                    <li class="active"><a href="#" disabled="disabled">${pg}</a></li>
                `
                } else if (pg == '...') {
                    return `
                    <li><a href="#" disabled="disabled">${pg}</a></li>
                    `
                } else {
                    return `
                <li><a href="shop.html?page=${pg}&codeXe=${codeXeParam}" >${pg}</a></li>
                `
                }
            })
            var htmls = htmlPre + html.join('');
        } else {
            var htmlPre = `
            <li><a href="shop.html?page=`+ (current - 1) + sortparam + `&codeXe=${codeXeParam}"><i class="fa fa-angle-left"></i></a></li>
            `;
            html = page.map(pg => {
                if (pg == currentPage + 1) {
                    return `
                    <li class="active"><a href="#" disabled="disabled">${pg}</a></li>
                `
                } else if (pg == '...') {
                    return `
                    <li><a href="#" disabled="disabled">${pg}</a></li>
                    `
                } else {
                    return `
                <li><a href="shop.html?page=${pg}&codeXe=${codeXeParam}" >${pg}</a></li>
                `
                }
            })
            var htmlNext = `
            <li><a href="shop.html?page=`+ (current + 1) + sortparam + `&codeXe=${codeXeParam}"><i class="fa fa-angle-right"></i></a></li>
            `;
            var htmls = htmlPre + html.join('') + htmlNext;
        }
    }

    var paginationHtml = `
    <div class="pagination-box fix">
        <ul class="blog-pagination" style="margin:auto;">
            ${htmls}
        </ul>
    </div>
    `;
    $('#all-product').append(paginationHtml);
    console.log(paginationHtml);
}

function pagination(c, m) {
    var current = c,
        last = m,
        delta = 2,
        left = current - delta,
        right = current + delta + 1,
        range = [],
        rangeWithDots = [],
        l;

    for (let i = 1; i <= last; i++) {
        if (i == 1 || i == last || i >= left && i < right) {
            range.push(i);
        }
    }

    for (let i of range) {
        if (l) {
            if (i - l === 2) {
                rangeWithDots.push(l + 1);
            } else if (i - l !== 1) {
                rangeWithDots.push('...');
            }
        }
        rangeWithDots.push(i);
        l = i;
    }

    return rangeWithDots;
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

$('#sorter').on('change', function () {
    var nextLink = window.location.href;
    var typeSort = $(this).val();
    console.log(window.location.href);
    if (sortType == null) {
        console.log("asdasd")
        if (pageParam == null && codeXeParam == null && categoryParam == null && subCategoryParam == null) {
            if (typeSort == 'random') {
                window.location = nextLink
            } else {
                window.location = nextLink + `?sort=${typeSort}`;
            }
        } else {
            if (typeSort == 'random') {
                window.location = nextLink;
            } else {
                window.location = nextLink+ `&sort=${typeSort}`;
            }
        }
    } else {
        console.log("cos")
        if (typeSort == 'random') {
            if (sortType == 'des') {
                nextLink = nextLink.replace(/&sort=des/i, '');
            } else if (sortType == 'asc') {
                nextLink = nextLink.replace(/&sort=asc/i, '');
            } else {
                nextLink = nextLink.replace(/asc|des/i, "");
            }
        } else {
            if (sortType == 'des') {
                nextLink = nextLink.replace(/des/i, typeSort);
            } else if (sortType == 'asc') {
                nextLink = nextLink.replace(/asc/i, typeSort);
            }
        }
        window.location = nextLink;
    }
})

getProductApi(renderProduct, page);
showXeList();

