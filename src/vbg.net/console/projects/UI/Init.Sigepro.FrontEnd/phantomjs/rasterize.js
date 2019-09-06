"use strict";
var page = require('webpage').create(),
    system = require('system'),
    address, output, size;

if (system.args.length < 3 || system.args.length > 5) {
    console.log('Usage: rasterize.js URL filename [paperwidth*paperheight|paperformat] [zoom]');
    console.log('  paper (pdf output) examples: "5in*7.5in", "10cm*20cm", "A4", "Letter"');
    console.log('  image (png/jpg output) examples: "1920px" entire page, window width 1920px');
    console.log('                                   "800px*600px" window, clipped to 800x600');
    phantom.exit(1);
} else {
    address = system.args[1];
    output = system.args[2];
    page.viewportSize = { width: 600, height: 600 };
    if (system.args.length > 3 && system.args[2].substr(-4) === ".pdf") {
        size = system.args[3].split('*');
        page.paperSize = size.length === 2 ? { width: size[0], height: size[1], margin: '0px' }
            : { format: system.args[3], orientation: 'portrait', margin: '1cm' };
    } else if (system.args.length > 3 && system.args[3].substr(-2) === "px") {
        size = system.args[3].split('*');
        if (size.length === 2) {
            pageWidth = parseInt(size[0], 10);
            pageHeight = parseInt(size[1], 10);
            page.viewportSize = { width: pageWidth, height: pageHeight };
            page.clipRect = { top: 0, left: 0, width: pageWidth, height: pageHeight };
        } else {
            console.log("size:", system.args[3]);
            pageWidth = parseInt(system.args[3], 10);
            pageHeight = parseInt(pageWidth * 3 / 4, 10); // it's as good an assumption as any
            console.log("pageHeight:", pageHeight);
            page.viewportSize = { width: pageWidth, height: pageHeight };
        }
    }
    if (system.args.length > 4) {
        page.zoomFactor = system.args[4];
    }

    var basePaperSize = {
        format: 'A4',
        orientation: 'portrait',

        margin: {
            top: "1.5cm",
            bottom: "1cm",
            left: "1cm",
            right: "1cm"
        },

        header: {
            height: "0cm",
            contents: phantom.callback(function (pageNum, numPages) {
                if (pageNum == 1) {
                    return "";
                }
                return "<h1>Header <span style='float:right'>" + pageNum + " / " + numPages + "</span></h1>";
            })
        },

        footer: {
            height: "0cm",
            contents: phantom.callback(function (pageNum, numPages) {
                if (pageNum == 1) {
                    return "";
                }
                return "<span style='float:right'>" + pageNum + " / " + numPages + "</span>";
            })
        }
    };

    page.paperSize = basePaperSize;

    page.open(address, function (status) {
        if (status !== 'success') {
            console.log('Unable to load the address!');
            phantom.exit(1);
        } else {

            if (page.evaluate(function () { return typeof PhantomJSPrinting == "object"; })) {

                var paperSize = basePaperSize;
                // Leggo l'altezza dell'header dalla pagina usata come template
                paperSize.header.height = page.evaluate(function () {
                    if (PhantomJSPrinting.header && PhantomJSPrinting.header.height) {
                        return PhantomJSPrinting.header.height;
                    }


                    return "0cm";
                });

                // Leggo il contenuto dell'header dalla pagina usata come template
                paperSize.header.contents = phantom.callback(function headerContentsCallback(pageNum, numPages) {
                    return page.evaluate(function (pageNum, numPages) {

                        if (PhantomJSPrinting.header && PhantomJSPrinting.header.contents) {
                            return PhantomJSPrinting.header.contents(pageNum, numPages);
                        }

                        return "";

                    }, pageNum, numPages);
                });

                // Leggo l'altezza del footer dalla pagina usata come template
                paperSize.footer.height = page.evaluate(function evaluateFooterHeight() {

                    if (PhantomJSPrinting.footer && PhantomJSPrinting.footer.height) {
                        return PhantomJSPrinting.footer.height;
                    }

                    return "0cm";
                });

                paperSize.footer.contents = phantom.callback(function footerContentsCallback(pageNum, numPages) {
                    return page.evaluate(function (pageNum, numPages) {

                        if (PhantomJSPrinting.footer && PhantomJSPrinting.footer.contents) {
                            return PhantomJSPrinting.footer.contents(pageNum, numPages);
                        }

                    }, pageNum, numPages);
                });

                page.paperSize = paperSize;
            }

            window.setTimeout(function () {
                page.render(output);
                phantom.exit();
            }, 200);
        }
    });

}
