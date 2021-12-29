var id = GetParameterValues('id');

$.ajax({
    type: 'GET',
    url: 'http://localhost:8120/api/admin/orders/'+ id,
    success: function (data) {
        $('#order-number').html(`Order #${data.id}`);
        $('#order-date').html(`Đơn hàng được đặt vào ngày ${data.createdDate}`);
        $('#fullname').html(data.fullname);
        $('#address').html(data.address);
        $('#phone').html(data.phoneNumber);
        if(data.orderState=='DELIVERY'){
            $('#order-state').html('Trạng thái: Đang giao');
        } else if(data.orderState== 'DELIVERED'){
            $('#order-state').html('Trạng thái: Đã giao');
        } else {
            $('#order-state').html('Trạng thái: Đã hủy');
        }
        $('#total-price').html(`Tổng tiền: `+formatCash(data.total+'')+`đ`)
        var listP = data.listItem.map(function (item) {
            var pprice = formatCash(item.price+'');
            return `
            <div class="order-item" style="margin-top:20px; width: 500px; width: 100%; height: 80px; background-color: #f4f4f4">
            <div class="item-pic" style="width: 33.33%; float: left;"  data-spm="list_image"><a href="#"><img width="80px" height="80px" 
               style="padding:5px;" src="http://localhost:8120/images/productImages/${item.image}"></a>
            </div>
            <div class="item-main ">
                <div>
                    <div class="text title item-title"
                        data-spm="details_title"><a href="#">${item.name}</a>
                    </div>
                </div>
            </div>
            <div class="item-status" style="width: 33.33%; float: left;">
                <div class="item-price text bold">₫ ${pprice}</div>
            </div>
            <div class="item-quantity" style="width: 33.33%; float: left;"><span><span
                        class="text desc info multiply">số lượng: </span><span
                        class="text"> ${item.quantity}</span></span></div>
            <div class="clear"></div>
        </div>
        <br>
            `;
        }) 
        $('#package-body').append(listP.join(''));
    },
    error: function (res) {
    }
})


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