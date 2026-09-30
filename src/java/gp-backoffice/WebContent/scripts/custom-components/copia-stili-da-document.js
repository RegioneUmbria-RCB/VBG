export const copiaStiliDaDocument = async (root) => {

    const blacklist = [
        'css/cart/smoothness/jquery-ui-1.11.1.custom.css',
        'css/layouts/layout.css',
        'css/styles/standard.css',
        'css/superfish/superfish.css'
    ].map(x => x.toUpperCase());

    [...document.querySelectorAll('link[rel=stylesheet]')]
        .filter(x => {

            for (const el of blacklist) {
                if (x.href.toUpperCase().indexOf(el) >= 0) {
                    //console.log(`${x.href} scartato`);
                    return false;
                }
            }

            //console.log(`${x.href} incluso`);

            return true;
        })
        .forEach(style => root.appendChild(style.cloneNode(true)));
}