var deleteId = 0;
function showAllproduct() {
    var status = $('#filterbyStatus').val();
    var title = $('#inlineFormInputGroupUsername2').val();
    $.ajax({
        dataType: 'json',
        url: getUrl(status, title),
        success: function (data) {     
            var htmls = data.products.map(function (item) {
                var fortmatPrice = formatCash(item.price+'');
                var status,html;
                if(item.quantity == 0){
                    status = "Hết hàng";
                    html = `
                    <li><a class="dropdown-item" onclick="setStock(${item.id},1)" href="#">Còn hàng</a></li>
                    `
                } else {
                    status = "Còn hàng";
                    html = `
                    <li><a class="dropdown-item" onclick="setStock(${item.id},0)" href="#">Hết hàng</a></li>
                    `
                }
                return `
                <tr>
                    <td>${item.id}</td>
                    <td><img src="http://localhost:8120/images/productImages/${item.images}"></td>
                    <td>
                        <h5>${item.title}</h5>
                    </td>
                    <td>
                        <h5>${item.donVi}</h5>
                    </td>
                    <td>
                        <h5>${fortmatPrice}</h5>
                    </td>
                    <td>
                        <h5>${item.subCategoryName}</h5>
                    </td>
                    <td>
                        <h5>${status}</h5>
                    </td>
                    <td>
                        <div class="btn-group">
                        <button type="button"
                                class="btn btn-danger dropdown-toggle"
                                data-bs-toggle="dropdown"
                                aria-expanded="false">
                                <i class="far fa-cog"></i>
                        </button>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="product_detail.html?id=${item.id}">Chỉnh sửa</a></li>
                            ${html}
                            <li><a class="dropdown-item" href="#" onclick="confirmDelete(${item.id})" data-bs-toggle="modal" data-bs-target="#confirmModal">Xóa</a></li>
                        </ul>
                    </td>
                </tr>
                `;
            })
            $('#content-product').html(htmls.join(''));
        }
    });

}

$('#filterbyStatus').on('change', function(e) {
    showAllproduct();
});

$('#inlineFormInputGroupUsername2').on('keyup', function(e) {
    showAllproduct();
})
showAllproduct();

function getUrl(status,title) {
    var apiUrl = "http://localhost:8120/api/admin/products/";
    if (status == "deactive"){
        if(title != ""){
            return apiUrl +"?status="+status+"&title="+title;
        } else {
            return apiUrl +"?status="+status
        }
    } else {
        if(title != ""){
            return apiUrl +"?title="+title;
        } else {
            return apiUrl;
        }
    }
}

function formatCash(str) {
    return str.split('').reverse().reduce((prev, next, index) => {
        return ((index % 3) ? next : (next + ',')) + prev
    })
}

function confirmDelete(id){
    deleteId = id;
}

function deleteProduct() {
    $.ajax({
        type:'DELETE',
        url: 'http://localhost:8120/api/admin/products/'+deleteId,
        success: function (data) { 
            showAllproduct();
            closeModal();
        },
        error: function (res){
        }
    })
}

function setStock(id,quantity){
    $.ajax({
        type:'PUT',
        url: 'http://localhost:8120/api/admin/products/'+id,
        data:{
            quantity:quantity
        },
        success: function (data) { 
            showAllproduct();
        },
        error: function (res){
        }
    })
}

function closeModal(){
    $('#confirmModal').modal('toggle');
}

$('#confirmModal').on('hidden.bs.modal', function () {
    $(this).find('form').trigger('reset');
    
})