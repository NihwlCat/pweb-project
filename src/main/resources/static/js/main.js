const cardCount = () => {
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

const makeRequest = ({
                         type = 'POST',
                         url,
                         contentType = 'application/json',
                         data,
                         success,
                         error}) => {
    $.ajax({type, url, contentType, data, success, error})
}

const changeItemCardButton = (e) => {
    const parent = e.target.parentNode
    $(parent).addClass('product-added').text('ADICIONADO')
}


const clearCart = () => {
    $.removeCookie("cart")
    cardCount()
    alert("Carrinho esvaziado")
}
