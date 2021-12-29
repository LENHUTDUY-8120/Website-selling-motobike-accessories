
/*================================================
[  Table of contents  ]
==================================================
 1. Newsletter Popup
 2. Mobile Menu Activation
 3 Checkout Page Activation
 4. NivoSlider Activation
 5. New Products Activation
 6. New Upsell Product Activation
 7. Side Product Activation
 8. Best Seller Activation
 9. Hand Tool Activation
 10. Brand Banner Activation
 11. Blog Activation
 12. Blog two Activation
 13. WOW Js Activation
 14. ScrollUp Activation
 15. Sticky-Menu Activation
 16. Price Slider Activation
 17. Testimonial Slick Carousel
 18. Best Seller Activation
 19. Best Product Activation
 20. Blog Realted Post activation
 21.Best Seller  Unique Activation



================================================*/

(function ($) {
    "use Strict";
    /*--------------------------
    1. Newsletter Popup
    ---------------------------*/
    setTimeout(function () {
        $('.popup_wrapper').css({
            "opacity": "1",
            "visibility": "visible"
        });
        $('.popup_off').on('click', function () {
            $(".popup_wrapper").fadeOut(500);
        })
    }, 2500);

    /*----------------------------
    2. Mobile Menu Activation
    -----------------------------*/
    jQuery('.mobile-menu nav').meanmenu({
        meanScreenWidth: "991",
    });

    /*----------------------------
    3 Checkout Page Activation
    -----------------------------*/
    $('.categorie-title').on('click', function () {
        $('.vertical-menu-list').slideToggle();
    });

    $('#showlogin').on('click', function () {
        $('#checkout-login').slideToggle();
    });
    $('#showcoupon').on('click', function () {
        $('#checkout_coupon').slideToggle();
    });
    $('#cbox').on('click', function () {
        $('#cbox_info').slideToggle();
    });
    $('#ship-box').on('click', function () {
        $('#ship-box-info').slideToggle();
    });

    /*----------------------------
    4. NivoSlider Activation
    -----------------------------*/
    $('#slider').nivoSlider({
        effect: 'random',
        animSpeed: 300,
        pauseTime: 5000,
        directionNav: false,
        manualAdvance: true,
        controlNavThumbs: false,
        pauseOnHover: true,
        controlNav: true,
        prevText: "<i class='zmdi zmdi-chevron-left'></i>",
        nextText: "<i class='zmdi zmdi-chevron-right'></i>"
    });

    /*----------------------------------------------------
    5. New Products Activation
    -----------------------------------------------------*/
    $('.new-pro-active').owlCarousel({
        loop: false,
        nav: true,
        dots: false,
        smartSpeed: 1000,

        navText: ["<i class='fa fa-angle-left'></i>", "<i class='fa fa-angle-right'></i>"],
        margin: 30,
        responsive: {
            0: {
                items: 1,
                autoplay: true
            },
            480: {
                items: 2
            },
            768: {
                items: 2
            },
            1000: {
                items: 2
            },
            1200: {
                items: 3
            }
        }

    })
    /*----------------------------------------------------
    6. New Upsell Product Activation
    -----------------------------------------------------*/
    $('.new-upsell-pro').owlCarousel({
        loop: false,
        nav: true,
        dots: false,
        smartSpeed: 1000,

        navText: ["<i class='fa fa-angle-left'></i>", "<i class='fa fa-angle-right'></i>"],
        margin: 30,
        responsive: {
            0: {
                items: 1,
                autoplay: true
            },
            480: {
                items: 2
            },
            768: {
                items: 2
            },
            1000: {
                items: 3
            },
            1200: {
                items: 4
            }
        }

    })

    /*----------------------------------------------------
    7. Side Product Activation
    -----------------------------------------------------*/
    $('.side-product-list-active')
        .on('changed.owl.carousel initialized.owl.carousel', function (event) {
            $(event.target)
                .find('.owl-item').removeClass('last')
                .eq(event.item.index + event.page.size - 1).addClass('last');
        }).owlCarousel({
            loop: false,
            nav: true,
            dots: false,
            smartSpeed: 1500,
            navText: ["<i class='fa fa-angle-left'></i>", "<i class='fa fa-angle-right'></i>"],
            margin: 1,
            responsive: {
                0: {
                    items: 1,
                    autoplay: true
                },
                480: {
                    items: 2
                },
                768: {
                    items: 2
                },
                991: {
                    items: 1
                }
            }
        })

    /*----------------------------------------------------
    8. Best Seller Activation
    -----------------------------------------------------*/
    $('.best-seller-pro-active')
        .on('changed.owl.carousel initialized.owl.carousel', function (event) {
            $(event.target)
                .find('.owl-item').removeClass('last')
                .eq(event.item.index + event.page.size - 1).addClass('last');
        }).owlCarousel({
            loop: false,
            nav: true,
            dots: false,
            smartSpeed: 1200,
            navText: ["<i class='fa fa-angle-left'></i>", "<i class='fa fa-angle-right'></i>"],
            margin: 1,
            responsive: {
                0: {
                    items: 1,
                    autoplay: true
                },
                480: {
                    items: 2
                },
                768: {
                    items: 2
                },
                992: {
                    items: 3
                },
                1200: {
                    items: 4
                }
            }
        })

    /*----------------------------------------------------
    9. Hand Tool Activation
    -----------------------------------------------------*/
    $('.hand-tool-active').owlCarousel({
        loop: false,
        nav: true,
        dots: false,
        smartSpeed: 1200,
        navText: ["<i class='fa fa-angle-left'></i>", "<i class='fa fa-angle-right'></i>"],
        margin: 30,
        responsive: {
            0: {
                items: 1,
                autoplay: true
            },
            480: {
                items: 2
            },
            768: {
                items: 3
            },
            992: {
                items: 3
            },
            1200: {
                items: 4
            }
        }
    })
    /*----------------------------------------------------
    10. Brand Banner Activation
    -----------------------------------------------------*/
    $('.brand-banner').on('changed.owl.carousel initialized.owl.carousel', function (event) {
        $(event.target)

            .find('.owl-item').removeClass('last')
            .eq(event.item.index + event.page.size - 1).addClass('last');

        $(event.target)
            .find('.owl-item').removeClass('first')
            .eq(event.item.index).addClass('first')


    }).owlCarousel({
        loop: false,
        nav: false,
        dots: false,
        smartSpeed: 1200,
        margin: 1,
        responsive: {
            0: {
                items: 1,
                autoplay: true
            },
            480: {
                items: 3
            },
            768: {
                items: 4
            },
            1000: {
                items: 5
            }
        }
    })

    /*----------------------------------------------------
    11. Blog Activation
    -----------------------------------------------------*/
    $('.blog-active').owlCarousel({
        loop: false,
        nav: true,
        dots: false,
        smartSpeed: 1000,
        navText: ["<i class='fa fa-angle-left'></i>", "<i class='fa fa-angle-right'></i>"],
        margin: 30,
        responsive: {
            0: {
                items: 1,
                autoplay: true
            },
            768: {
                items: 2
            },
            1000: {
                items: 3
            }
        }
    })
    /*----------------------------------------------------
    12. Blog two Activation
    -----------------------------------------------------*/
    $('.blog-active2').owlCarousel({
        loop: false,
        nav: true,
        dots: false,
        smartSpeed: 1000,
        navText: ["<i class='fa fa-angle-left'></i>", "<i class='fa fa-angle-right'></i>"],
        margin: 30,
        responsive: {
            0: {
                items: 1,
                autoplay: true
            },
            768: {
                items: 2
            },
            1000: {
                items: 2
            }
        }
    })
    /*----------------------------
    13. WOW Js Activation
    -----------------------------*/
    new WOW().init();

    /*----------------------------
    14. ScrollUp Activation
    -----------------------------*/
    $.scrollUp({
        scrollName: 'scrollUp', // Element ID
        topDistance: '550', // Distance from top before showing element (px)
        topSpeed: 1000, // Speed back to top (ms)
        animation: 'fade', // Fade, slide, none
        scrollSpeed: 900,
        animationInSpeed: 1000, // Animation in speed (ms)
        animationOutSpeed: 1000, // Animation out speed (ms)
        scrollText: '<i class="fa fa-angle-up"></i>', // Text for element
        activeOverlay: false // Set CSS color to display scrollUp active point, e.g '#00FFFF'
    });

    /*----------------------------
    15. Sticky-Menu Activation
    ------------------------------ */
    $(window).on('scroll', function () {
        if ($(this).scrollTop() > 150) {
            $('.header-sticky').addClass("sticky");
        } else {
            $('.header-sticky').removeClass("sticky");
        }
    });

    /*----------------------------
    16. Price Slider Activation
    -----------------------------*/
    $("#slider-range").slider({
        range: true,
        min: 0,
        max: 80,
        values: [0, 74],
        slide: function (event, ui) {
            $("#amount").val("$" + ui.values[0] + "  $" + ui.values[1]);
        }
    });
    $("#amount").val("$" + $("#slider-range").slider("values", 0) +
        "  $" + $("#slider-range").slider("values", 1));

    /*--------------------------------
    17. Testimonial Slick Carousel
    -----------------------------------*/
    $('.testext_active').owlCarousel({
        loop: false,
        navText: ["<i class='fa fa-angle-left'></i>", "<i class='fa fa-angle-right'></i>"],
        margin: 15,
        smartSpeed: 1000,
        nav: true,
        dots: true,
        responsive: {
            0: {
                items: 1
            },
            600: {
                items: 1
            },
            1000: {
                items: 1
            }
        }
    })

    /*----------------------------------------------------
    18. Best Seller Activation
    -----------------------------------------------------*/
    $('.best-seller-pro').on('changed.owl.carousel initialized.owl.carousel', function (event) {
        $(event.target)
            .find('.owl-item').removeClass('last')
            .eq(event.item.index + event.page.size - 1).addClass('last');
    }).owlCarousel({
        loop: false,
        nav: true,
        dots: false,
        smartSpeed: 1000,
        navText: ["<i class='fa fa-angle-left'></i>", "<i class='fa fa-angle-right'></i>"],
        margin: 0,
        responsive: {
            0: {
                items: 1,
                autoplay: true
            },
            480: {
                items: 2
            },
            768: {
                items: 2
            },
            1000: {
                items: 1
            }
        }
    })
    /*----------------------------------------------------
    19. Best Product Activation
    -----------------------------------------------------*/
    $('.best-seller-pro-two')
        .owlCarousel({
            loop: false,
            nav: false,
            dots: false,
            smartSpeed: 1200,
            margin: 0,
            responsive: {
                0: {
                    items: 1,
                    autoplay: true
                },
                768: {
                    items: 3
                },
                1000: {
                    items: 1
                }
            }
        })

    /*-------------------------------------
    20. Blog Realted Post activation
    --------------------------------------*/
    $('.blog-related-post-active').owlCarousel({
        loop: false,
        margin: 30,
        smartSpeed: 1000,
        nav: false,
        dots: false,
        items: 2,
        responsive: {
            0: {
                items: 1,
                autoplay: true
            },
            480: {
                items: 1
            },
            768: {
                items: 2
            },
            992: {
                margin: 29,
                items: 2
            },
            1200: {
                items: 2
            }
        }
    })

    /*----------------------------------------------------
    21.Best Seller  Unique Activation
    -----------------------------------------------------*/
    $('.best-seller-unique').on('changed.owl.carousel initialized.owl.carousel', function (event) {
        $(event.target)
            .find('.owl-item').removeClass('last')
            .eq(event.item.index + event.page.size - 1).addClass('last');
    }).owlCarousel({
        loop: false,
        nav: true,
        dots: false,
        smartSpeed: 1000,
        navText: ["<i class='fa fa-angle-left'></i>", "<i class='fa fa-angle-right'></i>"],
        margin: 0,
        responsive: {
            0: {
                items: 1,
                autoplay: true
            },
            768: {
                items: 2
            },
            1000: {
                items: 1
            }
        }
    })


})(jQuery);


$('#search-bar').on('keyup change', function () {

    var isSearch = $(this).val();
    if (isSearch != "") {
        $('#view-search').show();
    } else {
        $('#view-search').hide();
    }
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8120/api/admin/products',
        data: {
            title: $(this).val()
        },
        success: function (data) {
            var htmls = data.products.map(function (item) {
                var pprice = formatCash(item.price+'')
                return `
                <li><a class="sub-result" onclick="disable();" href="details.html?id=${item.id}"
                    title="${item.title}"><img class="result-image"
                        src="http://localhost:8120/images/productImages/${item.images}">
                    <div class="result-text">
                        <div class="ct-s-s-title">${item.title}</div><span
                            class="ct-s-s-price">${pprice}₫</span>
                    </div>
                </a></li>
                `
            })
            $('.result-list').html(htmls.join(''));
        },
        error: function (res) {
        }
    })
})

function navBar() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8120/api/nav/category',
        success: function (data) {
            var htmls = data.map(function (category) {
                var str = category.listSubCategory.map(function (subcategory) {
                    return `
                        <li><a href="shop.html?subCategory=${subcategory.code}">${subcategory.title}</a></li>
                    `;
                })
                return `
                <li><a href="shop.html?category=${category.code}">${category.title}</a>
                <!-- Start Two Step -->
                <ul id="sub-menu" class="ht-dropdown dropdown-style-two sub-menu">
                    ${str.join('')}
                </ul>
            </li>
                `
            })
            $('#menu').html(htmls.join(''));
        },
        error: function (res) {
        }
    })
}

navBar();

function remove(pid) {
    var cart = localStorage.getItem("cart");
    var pcart = JSON.parse(cart) != null ? JSON.parse(cart) : [];
    var present_or_not = pcart.findIndex(item => item.productid == pid);
    pcart.splice(present_or_not, 1);
    localStorage.setItem("cart", JSON.stringify(pcart));
    renderCart();
    updateLenghtCart();
    renderAllCart();
}

function updateLenghtCart(){
    var cartstring = localStorage.getItem("cart");
    var cart = JSON.parse(cartstring);
    if (cart == null || cart.length == 0) {
        $("#cart-counter").hide();
    } else {
        // there is some item in the cart
        $("#cart-counter").html(cart.length);
    }
}

function renderCart() {
    var cartstring = localStorage.getItem("cart");
    var cart = JSON.parse(cartstring);
    if (cart == null || cart.length == 0) {
        var html = `
            <h4>Bạn chưa có sản phẩm trong giỏ</h4>
        `;
        $('#main-cart').html(html);
    } else {
        var total = 0;
        var cartitems = cart.map(function (item) {
            total += item.pprice*item.quantity;
            return `
            <!-- Cart Box Start -->
            <div class="single-cart-box">
                <div class="cart-img">
                    <a href="#"><img src="http://localhost:8120/images/productImages/${item.productImg}" alt="cart-image"></a>
                </div>
                <div class="cart-content">
                    <h6><a href="details.html?id=${item.productid}">${item.productname}</a></h6>
                    <span>${item.quantity} × `+formatCash(item.pprice+'')+`</span>
                </div>
                <a onclick="remove(${item.productid})" style="margin-top:20px;" class="del-icone" href="#"><i class="fas fa-times"></i></a>
            </div>
            <!-- Cart Box End -->
            `;
        })
        var carts = `
        <li>
            `+cartitems.join('')+`
            <!-- Cart Footer Inner Start -->
            <div class="cart-footer fix">
                <h5>Tổng:<span class="f-right">`+formatCash(total+'')+`</span></h5>
                <div class="cart-actions">
                    <a class="checkout" id="cart-checkout" href="checkout.html">Đặt hàng</a>
                </div>
            </div>
            <!-- Cart Footer Inner End -->
        </li>
        `
        $('#main-cart').html(carts);
    }
}


function formatCash(str) {
    return str.split('').reverse().reduce((prev, next, index) => {
        return ((index % 3) ? next : (next + ',')) + prev
    })
}

renderCart();
updateLenghtCart();
