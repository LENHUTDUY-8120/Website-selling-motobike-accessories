function showliststore() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8120/api/store',
        success: function (data) {
            var htmls = data.map(function (item) {
                return `
                <div class="info"><i class="fa fa-map-marker" aria-hidden="true"></i>${item.address}<br><span
                style="font-weight: normal;"><a rel="nofollow" style="color: inherit;"
                    href="#">${item.hotline}</a></span><a class="ct-view-map"
                href="${item.map}" target="_blank"
                rel="nofollow">Xem bản đồ</a></div>
                `
            })
            $('#list-store').html(htmls.join(''));
        },
        error: function (res) {
        }
    })
}



showliststore();

