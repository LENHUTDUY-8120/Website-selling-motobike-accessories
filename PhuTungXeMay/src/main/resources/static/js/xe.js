function showAll() {
    $.ajax({
        dataType: 'json',
        url: 'http://localhost:8120/api/xe',
        success: function (data) {
            var htmls = data.map(function (item) {
                return `
                <tr>
                    <td style="width:20%">${item.codeXe}</td>
                    <td style="width:60%">
                        <h5>${item.tenXe}</h5>
                    </td>
                    <td style="width:20%">
                        <button>
                            <i class="fas fa-trash" onclick="deleteXe(${item.id})"></i></a>
                    </td>
                </tr>
                `;
            })
            $('#content-all-brand').html(htmls.join(''));
        }
    });
}

function deleteXe(id) {
    $.ajax({
        type: 'DELETE',
        url: 'http://localhost:8120/api/xe/'+id,
        success: function (data) { 
            showAll();      
        },
        error: function(res){
        }
    });
}

function addXe(){
    xe = {
        tenXe:$('#tenXe').val(),
        codeXe:$('#codeXe').val()
    }
    $.ajax({
        dataType: 'json',
        contentType: 'application/json',
        type: 'POST',
        data: JSON.stringify(xe),
        url: 'http://localhost:8120/api/xe',
        success: function (data) {
            showAll();
            $('#exampleModal').modal('toggle');
        },
        error: function(res){
        }
    });
}

function closeModal(){
    $('#exampleModal').modal('toggle');
}

$('#exampleModal').on('hidden.bs.modal', function () {
    $(this).find('form').trigger('reset');
})

showAll();