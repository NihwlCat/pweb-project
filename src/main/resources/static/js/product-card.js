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

    $('.menu-client-cart > img').click(() => {

        if($.cookie("cart")){
            const value = confirm("Deseja criar o pedido?")

            if(value){
                makeRequest({
                    url: '/create-order',
                    data: $.cookie("cart"),
                    success: () => {
                        $.removeCookie("cart")
                        cardCount()
                    },
                    error: () => {
                        console.log('ERRO')
                    }
                })
            }
        } else {
            alert("Nenhum pedido no carrinho")
        }

    })

    $('.product-card-buy > button').click(e => {
        e.preventDefault();

        let field = e.target.id;
        const currentVal = parseInt($('input[name=' + field + ']').val());

        makeRequest({
            url: '/',
            data: field + ',' + currentVal,
            success: () => {
                cardCount();
                changeItemCardButton(e)
            },
            error: () => {
                console.log('ERRO')
            }
        })
    })
});