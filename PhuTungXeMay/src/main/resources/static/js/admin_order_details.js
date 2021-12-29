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
var id = GetParameterValues('id');
// Show all order by id
function show(){
    $.ajax({
        dataType: 'json',
        url: 'http://localhost:8120/api/admin/orders/' + id,
        success: function (data) {   
            var totalPrice = formatCash(data.total+'');
            var status;
            if(data.orderState == 'DELIVERY'){
                status = "Đang giao";
                $('#btnNot').append(
                    `<button onclick="setState(${data.id},'NOT')" style="float: right;" class="btn-danger btn-sm ">Hủy</button>`
                );
            } else if(data.orderState == 'DELIVERED'){
                status = "Đã giao";
            } else {
                status = "Đã hủy";
            }
            var htmls = `
                <h4>ID: ${data.id}</h4>
                <h4>Họ và tên: ${data.fullname}</h4>
                <h4>Email: ${data.email}</h4>
                <h4>Số điện thoại: ${data.phoneNumber}</h4>
                <h4>Địa chỉ giao hàng: ${data.address}</h4>
                <h4>Ngày đặt: ${data.createdDate}</h4>
                <h4>Tổng tiền: ${totalPrice}</h4>
                <h4>Ghi chú: ${data.note}</h4>
                <h4>Trạng thái: ${status}</h4>
                `;
            $('#info').html(htmls);

            var litsItem = data.listItem.map(function (item) {
                var pprice = formatCash(item.price+'')
                return `
                <tr>
                    <th scope="row">${item.productId}</th>
                    <td><img style="height: 50px; width: 50px;" src="http://localhost:8120/images/productImages/${item.image}"></td>
                    <td>${item.name}</td>
                    <td>${pprice}</td>
                    <td>${item.quantity}</td>
                </tr>
                `;
            })
            $('#productByOrder').html(litsItem.join(''));
        }
    });
}

function formatCash(str) {
    return str.split('').reverse().reduce((prev, next, index) => {
        return ((index % 3) ? next : (next + ',')) + prev
    })
}

function setState(orderId,status){
    $.ajax({
        type:'PUT',
        url: 'http://localhost:8120/api/admin/orders/'+orderId,
        data:{
            status:status
        },
        success: function (data) { 
            show();
            $('#btnNot').html('');
        },
        error: function (res){
        }
    })
}

show();


