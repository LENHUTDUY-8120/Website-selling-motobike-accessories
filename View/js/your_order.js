
$('#search-form').on('submit', function(e){
    e.preventDefault();
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8120/api/orders',
        data : {
            phone: $('#phoneNum').val(),
        },
        success: function (data) {
            var htmls = data.map(function (order) {
                if(order.orderState=='DELIVERY'){
                    var state="Đang giao";
                } else if (order.orderState== 'DELIVERED'){
                    var state="Đã giao";
                } else {
                    var state="Đã hủy";
                }
                return `
                <tr>
                    <td>${order.id}</td>
                    <td>${order.createdDate}</td>
                    <td>${state}</td>
                    <td>${order.total} </td>
                    <td><a class="view" href="review-order.html?id=${order.id}">view</a></td>
                </tr>
                `;
            })
            $('#list-order').html(htmls.join(''));
        },
        error: function (res) {
        }
    })
})

