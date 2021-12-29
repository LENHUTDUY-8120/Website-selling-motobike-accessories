
$('#form-login').on('submit', function (e) {
    e.preventDefault();
    acc = {
        username: $('#username').val(),
        password: $('#password').val()
    }
    $.ajax({
        type: 'POST',
        contentType: "application/json",
        url: 'http://localhost:8120/api/auth/signin',
        data: JSON.stringify(acc),
        success: function (data) {
            let accessToken = data.type + ' ' + data.token;
            localStorage.setItem('accessToken', accessToken);
            window.location.href = "product.html";

        },
        error: function (res) {
            $('#error').html("Tên đăng nhập hoặc mật khẩu chưa đúng");
        }
    })
})

