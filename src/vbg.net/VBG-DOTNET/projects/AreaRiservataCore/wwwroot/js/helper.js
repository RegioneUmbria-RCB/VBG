async function downloadFileFromStream(fileName, contentStreamReference) {
    const arrayBuffer = await contentStreamReference.arrayBuffer();
    const blob = new Blob([arrayBuffer]);
    const url = URL.createObjectURL(blob);

    triggerFileDownload(fileName, url);

    URL.revokeObjectURL(url);
}

function triggerFileDownload(fileName, url) {
    const anchorElement = document.createElement('a');
    anchorElement.href = url;
    anchorElement.download = fileName ?? '';
    anchorElement.click();
    anchorElement.remove();
}

function scrollTo(selector, options, findById = true) {
    const element = findById ? document.getElementById(selector) : document.querySelector(selector);

    if (element && element.focus) {
        element.focus();
    }

    if (element) {
        let opt = {
            behavior: "smooth",
            block: "start",
        };

        if (options) {
            opt = { ...opt, ...options };
        }
        element.scrollIntoView(opt);
    }
}

function scrollTo_withOffset(selector, selectorToCalcOffsetTo) {
    const element = document.getElementById(selector);

    if (element && element.focus) {
        element.focus();
    }    

    if (element && element.getBoundingClientRect()) {
        let top = element.getBoundingClientRect().top + window.scrollY;

        if (selectorToCalcOffsetTo) {
            top -= parseFloat(calculateOffset(selectorToCalcOffsetTo));
        }

        window.scroll({
            top:  top,
            behavior: 'smooth'
        });
    }
}

function calculateOffset(selector) {
    const element = document.getElementById(selector);

    if (element) {
        return element.offsetHeight;
    }

    return 0;
}

function getPageTitle() { return document.title; }

function removeClassFromAllElements(className) {
    var elements = document.getElementsByClassName(className);

    if (elements != null && elements.length> 0) {
        for (let i = 0; i < elements.length; i++) {
            if (elements[i].classList.contains(className)) {
                elements[i].classList.remove(className);
            }
        }
    }
}


//function onPageLoad() {
//    (function initStickyNavMenu() {
//        window.onscroll = function () { setStickyNavMenu() };




//        function setStickyNavMenu() {
//            return;
//            const header = document.getElementById("navMenu");

//            if (header) {

//                var sticky = header.offsetTop;

//                if (window.pageYOffset > sticky) {
//                    document.getElementById("navMenu").classList.remove("affix-top");
//                    document.getElementById("navMenu").classList.add("affix");
//                } else {
//                    document.getElementById("navMenu").classList.add("affix-top");
//                    document.getElementById("navMenu").classList.remove("affix");
//                }
//            }
//        }


//    })();
//}


//window.addEventListener('load', onPageLoad());