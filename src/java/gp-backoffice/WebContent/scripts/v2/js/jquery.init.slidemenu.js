(function ($) {
    'use strict';

    $.fn.initslidemenu = function (options) {
        this.each(function () {
            var settings = $.extend({
                //menuContainer: $("#menu"),
                pageContent: $('#page-content'),
                toggleMenuButton: $(".open-menu"),
                url: ''
            }, options);

            var $menu = $(this);//settings.menuContainer;


            function loadRemote(parentMenuId) {

                console.log('Caricamento submenu ', parentMenuId);

                $.ajax({
                    url: settings.url + parentMenuId,
                    dataType: 'json'
                }).done(function (data) {
                    setTimeout(function () {
                        var addItems = data;

                        var $addTo = $menu.multilevelpushmenu('activemenu').first();
                        // passando -1 come ultimo parametro va ad accodare l'elemento aggiunto a quelli esistenti
                        $menu.multilevelpushmenu("additems", addItems, $addTo, -1);
                    }, 500);
                });
            }



            function inizializzaMenu() {

                $.ajax({
                    url: settings.url,
                    dataType: 'json'
                }).done(function (data) {
                    var rootMenu = [{
                        "title": "Menu",
                        "id": "menuID",
                        //"icon": "fa fa-reorder",
                        "items": data
                    }];


                    $menu.multilevelpushmenu({
                        menu: rootMenu,
                        containersToPush: [settings.pageContent],
                        backText: 'Indietro',
                        collapsed: true,
                        overlapWidth: 20,
                        menuWidth: 350,
                        onMenuReady: function (element) {
                            console.log("Menu ready");

                            $menu.find("h2>i").css("display", "none");

                            $("#inserisci-nel-menu").css("display", "block");
                            $("#inserisci-nel-menu").prependTo($(".multilevelpushmenu_wrapper>.levelHolderClass "));

                            $(".menu-utente ul").animate({
                                left: -300
                            });

                            //loadSubmenu("0");
                        },
                        onGroupItemClick: function () {
                            // First argument is original event object
                            var event = arguments[0],
                                // Second argument is menu level object containing clicked item (<div> element)
                                $menuLevelHolder = arguments[1],
                                // Third argument is clicked item (<li> element)
                                $item = arguments[2],
                                // Fourth argument is instance settings/options object
                                options = arguments[3];

                            if ($item.find("ul:first>li").length === 0) {
                                loadRemote($item.attr("id").replace("menu-", ""), $menuLevelHolder);
                            }
                        },
                        onCollapseMenuEnd: function () {

                            if ($menu.multilevelpushmenu('activemenu').attr('data-level') !== undefined) {
                                return;
                            }

                            $(".menu-utente ul").animate({
                                left: -300
                            });
                        },
                        onExpandMenuStart: function () {
                            $(".menu-utente ul").animate({
                                left: 0
                            }, 1000);

                        },
                        onItemClick: function () {
                        	var $item = arguments[2];
                        	
                        	// Anchor href
                            var itemHref = $item.find( 'a:first' ).attr( 'href' );
                            
                            if (itemHref && itemHref.indexOf("javascript") >= 0) {
                            	
                            	arguments[0].preventDefault();
                                // Redirecting the page
                                eval(decodeURIComponent(itemHref));	
                            }                           	
                            	
                        }
                        //fullCollapse: true
                    });

                })
                    .fail(function (jqXHR, textStatus) {
                        console.error(jqXHR);
                        console.error(textStatus);
                    });
            }


            settings.toggleMenuButton.on("click", function () {
                if ($menu.multilevelpushmenu('activemenu').attr('data-level') == undefined) {
                    $menu.multilevelpushmenu('expand');
                } else {
                    $menu.multilevelpushmenu('collapse');
                }
            });

            inizializzaMenu();
        });
    };
}(jQuery));