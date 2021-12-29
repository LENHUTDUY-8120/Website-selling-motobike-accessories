var idCH = 0;

function showStore(){
    $.ajax({
        dataType: 'json',
        type:'GET',
        url: 'http://localhost:8120/api/store',
        success: function (data) {     
            var htmls = data.map(function (item) {
                return `
                <tr>
                                <td>${item.address}</td>
                                <td>${item.map}</td>
                                <td>${item.hotline}</td>
                                <td>
                                    <div class="btn-group">
                                        <button type="button"
                                                class="btn btn-danger dropdown-toggle"
                                                data-bs-toggle="dropdown"
                                                aria-expanded="false">
                                                <i class="far fa-cog"></i>
                                        </button>
                                        <ul class="dropdown-menu">
                                            <li><a class="dropdown-item" href="#" onclick="show(${item.id})">Chỉnh sửa</a></li>
                                            <li><a class="dropdown-item" href="#" onclick="deleteStore(${item.id})">Xóa</a></li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                `;
            })
            $('#content-store').html(htmls.join(''));
        }
    });
}

function formAction(){
    if(idCH==0){
        addStore();
    } else {
        update(idCH);
    }
}

function addStore() {

    store={
        address:$('#address').val(),
        map:$('#map').val(),
        hotline: $('#hotline').val()
    }
    $.ajax({
        dataType: 'json',
        contentType: 'application/json',
        type:'POST',
        url: 'http://localhost:8120/api/store',
        data: JSON.stringify(store),
        success: function (data) {  
            closeModal();   
            showStore();
        },
        error: function (res){

        }
    });
}

function update(id){
    store={
        address:$('#address').val(),
        map:$('#map').val(),
        hotline: $('#hotline').val()
    }
    $.ajax({
        dataType: 'json',
        contentType: 'application/json',
        type:'PUT',
        url: 'http://localhost:8120/api/store/'+id,
        data: JSON.stringify(store),
        success: function (data) {  
            closeModal();   
            showStore();
            idCH = 0;
        },
        error: function (res){

        }
    });
}

function show(id){
    $.ajax({
        dataType: 'json',
        type:'GET',
        url: 'http://localhost:8120/api/store/'+id,
        success: function (data) {
            $('#exampleModal').modal('toggle');
            $('#address').val(data.address);
            $('#map').val(data.map);
            $('#hotline').val(data.hotline);
            idCH = data.id;    
        },
        error: function (res){}
    });
}

function deleteStore() {
    console.log('deleting')
    $.ajax({
        type:'DELETE',
        url: 'http://localhost:8120/api/store/'+id,
        success: function (data) { 
            showStore();
        },
        error: function (res){
        }
    })
}

function closeModal(){
    $('#exampleModal').modal('toggle');
}

$('#exampleModal').on('hidden.bs.modal', function () {
    $(this).find('form').trigger('reset');
    idCH = 0;
})

showStore();

