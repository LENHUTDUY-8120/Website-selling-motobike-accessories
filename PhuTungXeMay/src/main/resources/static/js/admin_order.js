// Show all order
function showOrder(){

    var status = $('#filterStatus').val();

    $.ajax({
        dataType: 'json',
        url: 'http://localhost:8120/api/admin/orders',
        data:{
            status:status
        },
        success: function (data) {     
            var htmls = data.map(function (item) {
                var totalprice = formatCash(item.total+'');

                if(item.orderState == 'DELIVERY'){
                    html = `
                    <li><a class="dropdown-item" onclick="setState(${item.id},'DELIVERED')" href="#">Đã giao</a></li>
                    <li><a class="dropdown-item" onclick="setState(${item.id},'NOT')" href="#">Hủy đơn</a></li>
                    `
                } else if(item.orderState == 'DELIVERED'){
                    html = `
                    <li><a class="dropdown-item" onclick="setState(${item.id},'DELIVERY')" href="#">Đang giao</a></li>
                    <li><a class="dropdown-item" onclick="setState(${item.id},'NOT')" href="#">Hủy đơn</a></li>
                    `
                } else {
                    html = '';
                }
                return `
                <tr>
                    <th scope="row">${item.id}</th>
                    <td>${item.fullname}</td>
                    <td>${item.phoneNumber}</td>
                    <td>${item.email}</td>
                    <td>${item.address}</td>
                    <td>${totalprice} VND</td>
                    <td>${item.createdDate}</td>
                    <td>
                        <div class="btn-group">
                        <button type="button"
                                class="btn btn-danger dropdown-toggle"
                                data-bs-toggle="dropdown"
                                aria-expanded="false">
                                <i class="far fa-cog"></i>
                        </button>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="order_details.html?id=${item.id}">Chi tiết</a></li>
                            ${html}
                        </ul>
                    </td>
                </tr>
                `;
            })
            $('#order-list').html(htmls.join(''));
        }
    });
}

$('#filterStatus').on('change', function(e) {
    showOrder();
});

function setState(orderId,status){
    $.ajax({
        type:'PUT',
        url: 'http://localhost:8120/api/admin/orders/'+orderId,
        data:{
            status:status
        },
        success: function (data) { 
            showOrder();
        },
        error: function (res){
        }
    })
}

function formatCash(str) {
    return str.split('').reverse().reduce((prev, next, index) => {
        return ((index % 3) ? next : (next + ',')) + prev
    })
}


showOrder();

