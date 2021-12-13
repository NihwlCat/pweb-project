
const makeRequest = ({type = 'POST', url, contentType = 'application/json', data, success, error}) => {
    $.ajax({
        type,
        url,
        contentType,
        data,
        success,
        error
    })
}

const updateCartAmount = () => {
    let cookie = $.cookie("cart")

    let count = 0;
    if(cookie){
        for (let i = 0; i < cookie.length; i++) {
            if(cookie[i] === ":"){
                count++
            }
        }
    }
    $('.menu-client-cart > div').text(count)
}

const changeItemCardButton = (e) => {
    const parent = e.target.parentNode
    $(parent).addClass('product-added').text('ADICIONADO')
}


jQuery(document).ready(function () {
    $('[data-quantity="plus"]').click(function (e) {
        e.preventDefault();
        field = $(this).attr('item-target');
        var currentVal = parseInt($('input[name=' + field + ']').val());
        if (!isNaN(currentVal)) {
            $('input[name=' + field + ']').val(currentVal + 1);
        } else {
            $('input[name=' + field + ']').val(1);
        }
    });
    $('[data-quantity="minus"]').click(function (e) {
        e.preventDefault();
        field = $(this).attr('item-target');
        var currentVal = parseInt($('input[name=' + field + ']').val());
        if (!isNaN(currentVal) && currentVal > 1) {
            $('input[name=' + field + ']').val(currentVal - 1);
        } else {
            $('input[name=' + field + ']').val(1);
        }
    });

    $('.menu-client-logout > p').click(e => {
        $.removeCookie("token")
        window.location.replace("/")
    })

    $('.menu-client-cart > img').click(e => {
        makeRequest({
            url: '/create-order',
            data: $.cookie("cart"),
            success: () => {console.log('DEU CERTO')},
            error: () => {console.log('ERRO')}
        })

        $.removeCookie("cart")
    })



    $('.product-card-buy > button').click(e => {
        e.preventDefault();

        let field = e.target.id;
        const currentVal = parseInt($('input[name=' + field + ']').val());

        makeRequest({
            url: '/',
            data: field + ',' + currentVal,
            success: () => {updateCartAmount(); changeItemCardButton(e)},
            error: () => {console.log('ERRO')}
        })
    })
});