var wrappers = 
{
    object:
        {
            /**
             * verifica se un elmento HTML e' visible oppure no
             * @param {object} htmlElement
             */
            isVisible:
                function(htmlElement)
                {
                    var isVisible = undefined;
                    if(config_ol.html_page_elments.use_jquery)
                    {
                        isVisible = htmlElement.is(':visible');
                    }
                    else
                    {
                        if(htmlElement.style.display == "none")
                        {
                            isVisible = false;
                        }
                        else
                        {
                            isVisible = true;
                        }
                        
                    }
                    return isVisible;
                },
            /**
             * Ritorna uno o piu elementi del DOM in base al selettore utilizzato
             * @param {string} selector 
             */
            getObjectFromDOMbySelector:
                function(selector)
                {
                    var htmlElement = undefined;
                    if(config_ol.html_page_elments.use_jquery)
                    {
                        htmlElement = $(selector);
                    }
                    else
                    {
                        htmlElement = document.querySelector(selector);
                    }
                    return htmlElement;
                },

            /**
             * Metodo wrapper per ritornare i figli di un elemento DOM filtrati per TAG HTML
             * @param {object} parent 
             * @param {string} tag 
             */
            getChildrenByTag:
                function(parent, tag)
                {
                    var children = undefined;
                    if(config_ol.html_page_elments.use_jquery)
                    {
                        children = parent.children(tag);
                    }
                    else
                    {
                        children = parent.getElementsByTagName(tag);
                    }
                    return children;
                },
            
            /**
             * inietta dell'html nella parte TEXT dell'elemento 
             * @param {object} htmlElement
             * @param {string} value
             */
            setInnerHtmlValue:
                function(htmlElement, value)
                {
                    if(config_ol.html_page_elments.use_jquery)
                    {
                        htmlElement.html(value);
                    }
                    else
                    {
                        htmlElement.innerHTML	=	value;
                    }
                },
            
            /**
             * Funzione che elimina tutti i children di un elemento DOM
             * @param {object} htmlElement
             */
            empty:
                function(htmlElement)
                {
                    if(config_ol.html_page_elments.use_jquery)
                    {
                        htmlElement.empty();
                    }
                    else
                    {
                        while (htmlElement.firstChild)
                        {
                            htmlElement.removeChild(htmlElement.firstChild);
                        }
                    }
                },
            
            /**
             * nasconde un elemento HTML
             * @param {object} htmlElement 
             */
            hide:
                function(htmlElement)
                {
                    if(config_ol.html_page_elments.use_jquery)
                    {
                        htmlElement.hide();
                    }
                    else
                    {
                        htmlElement.style.display	=	'none';
                    }
                },
            
            /**
             * mostra un elemento HTML
             * @param {object} htmlElement 
             */
            show:
                function(htmlElement)
                {
                    if(config_ol.html_page_elments.use_jquery)
                    {
                        htmlElement.show();
                    }
                    else
                    {
                        htmlElement.style.display	=	'block';
                    }
                },
            
            /**
             * elimina tutti gli elementi children di un oggetto filtrati in base al tag html e al valore di un data-attribute
             * @param {object} htmlElement 
             * @param {string} childrenTag 
             * @param {string} data_attribute_key 
             * @param {string} data_attribute_value 
             */
            removeChildrenByTagAndDataAttribute:
                function(htmlElement, childrenTag, data_attribute_key, data_attribute_value)
                {
                    var selettoreHTML = childrenTag+'['+data_attribute_key+'="'+data_attribute_value+'"]';

                    if(config_ol.html_page_elments.use_jquery)
                    {
                        htmlElement.children(selettoreHTML).remove();
                    }
                    else
                    {
                        var elementiLiDaEliminare = document.querySelectorAll(selettoreHTML);
                        for(var i=0; i < elementiLiDaEliminare.length; i++) {
                            htmlElement.removeChild(elementiLiDaEliminare[i]);
                        }
                    }	
                }
        },

    dropdown:   
        {

            /**
             * Data una lista dropdown e un valore, seleziona l'item corrispondente a quel valore
             * se si usa plain-js si rimanda a stessa funzione di gruppo object
             * 
             * @param {object} dropDownList 
             * @param {string} itemValue 
             */
            selectItemByValue:
                function(dropDownList, itemValue)
                {
                    if(config_ol.html_page_elments.use_jquery)
                    {
                        if(config_ol.html_page_elments.use_bootstrap_select)
                        {
                            dropDownList.selectpicker('val', itemValue);
                        }
                        else
                        {
                            dropDownList.val(itemValue);
                        }
                    }
                    else
                    {
                        //cercare la option corrispondente e impostarla come valore selezionato
                        for(var i = 0; i < dropDownList.options.length; i++)
                        {
                            if(dropDownList.options[i].value == itemValue)
                            {
                                dropDownList.selectedIndex = dropDownList.options[i].index;
                                break;
                            }
                        }
                    }
                },
            
            /**
             * seleziona elemento null se lista vuota o primo elemento se lista ha valori
             * se si usa plain-js si rimanda a stessa funzione di gruppo object
             * @param {object} dropDownList 
             */
            selectFirstItem:
                function(dropDownList)
                {
                
                    if(config_ol.html_page_elments.use_jquery)
                    {
                        dropDownList.prop("selectedIndex", 0).change();
                        if(config_ol.html_page_elments.use_bootstrap_select)
                        {
                            dropDownList.selectpicker('refresh');
                        }
                    }
                    else
                    {
                        if(dropDownList.hasChildNodes())
                        {
                            dropDownList.selectedIndex = 0;
                        }
                        else
                        {
                            dropDownList.selectedIndex = -1;
                        }
                    }
                },
            
            /**
             * Aggiunge un item alla lista dropdown.
             * se uso bootstrap-select dovrei eseguire il refresh dopo l'aggiunta
             * della option per poterla visualizzare.
             * nel caso di popolamento massivo di una select potrei voler fare refresh alla fine
             * per motivi di performance, quindi metto il flag per disabilitare opzione
             * 
             * se uso plain js questo parametro viene ignorato
             * 
             * di default il parametro vale true
             * 
             * @param {object} dropDownList 
             * @param {object} optionItem 
             * @param {boolean} [refresh=true]
             */
            addItem:
                function(dropDownList, optionItem, refresh)
                {
                    if(config_ol.html_page_elments.use_jquery)
                    {
                        //questo non funziona in internet explorer
                        //dropDownList.append(optionItem);

                        //questo funziona anche in internet explorer
                        var options = dropDownList.prop('options');
                        options[options.length] = optionItem;

                        if(config_ol.html_page_elments.use_bootstrap_select && refresh)
                        {
                            dropDownList.selectpicker('refresh');
                        }
                    }
                    else
                    {
                        //al metodo add si puo passare anche un indice per indicare la posizione in cui voglio 
                        //inserire la option. se non passo nulla viene aggiunta in coda
                        dropDownList.add(optionItem);
                    }
                },

            /**
             * Data un oggetto rappresentante una lista DropDown, ritorna il valore selezionato
             * @param {object} dropDownList 
             */
            getSelectedValue:
                function(dropDownList)
                {
                    var selectedValue = undefined;
                    if(config_ol.html_page_elments.use_jquery)
                    {
                        selectedValue	=   dropDownList.children("option").filter(":selected").val();
                    }
                    else
                    {
                        selectedValue	=   dropDownList.options[dropDownList.selectedIndex].value;
                    }
                    return selectedValue;
                },

            /**
             * elimina tutti i children di un elemento DropDown
             * se si usa plain-js si rimanda a stessa funzione di gruppo object
             * @param {object} dropDownList
             */
            empty:
                function(dropDownList)
                {
                    if(config_ol.html_page_elments.use_jquery)
                    {
                        dropDownList.empty();
                        if(config_ol.html_page_elments.use_bootstrap_select)
                        {
                            //dropDownList.selectpicker('refresh');
                            dropDownList.selectpicker('render');
                        }
                    }
                    else
                    {
                        wrappers.object.empty(dropDownList);
                    }
                },
            
            /**
             * nasconde un elemento dropdown
             * se si usa plain-js si rimanda a stessa funzione di gruppo object
             * @param {object} dropDownList 
             */
            hide:
                function(dropDownList)
                {
                    if(config_ol.html_page_elments.use_jquery)
                    {
                        dropDownList.hide();
                        if(config_ol.html_page_elments.use_bootstrap_select)
                        {
                            dropDownList.selectpicker('hide');
                        }
                    }
                    else
                    {
                        wrappers.object.hide(dropDownList);
                    }
                },

            /**
             * mostra un elemento HTML
             * @param {object} dropDownList 
             */
            show:
                function(dropDownList)
                {
                    if(config_ol.html_page_elments.use_jquery)
                    {
                        dropDownList.show();
                        if(config_ol.html_page_elments.use_bootstrap_select)
                        {
                            dropDownList.selectpicker('show');
                        }
                    }
                    else
                    {
                        wrappers.object.show(dropDownList);
                    }		
                },
            
            /**
             * Metodo attualmente utilizzato solo se si usa 
             * bootstrap-select, negli altri casi non fa nulla
             * usato per richiamare solo alla fine di un popolamento 
             * massivo di una select il metodo refresh, 
             * per migliorare le performance
             * @param {object} dropDownList 
             */
            refreshAfterUpdate:
                function(dropDownList)
                {
                    if(config_ol.html_page_elments.use_jquery && config_ol.html_page_elments.use_bootstrap_select)
                    {
                        dropDownList.selectpicker('refresh');
                    }
                }
        },

    html_list:
    {
        /**
         * Aggiunge un elemento a una lista HTML
         * @param {object} htmlList 
         * @param {object} item 
         */
        addItem:
            function(htmlList, item)
            {
                if(config_ol.html_page_elments.use_jquery)
                {
                    htmlList.append(item);
                }
                else
                {
                    htmlList.appendChild(item);
                }
            }
    },
    event:
    {
        /**
         * attiva un listener per evento click su elemento DOM.
         * quando l'evento viene scatenato, viene eseguita la funzione
         * associata
         * @param {object} htmlElement 
         * @param {function} method 
         */
        addClickEventListener:
            function(htmlElement, method)
            {
                if(config_ol.html_page_elments.use_jquery)
                {
                    htmlElement.click(method);
                }
                else
                {
                    htmlElement.addEventListener('click', method);
                }
            },

        /**
         * attiva un listener per evento change su elemento DOM.
         * quando l'evento viene scatenato, viene eseguita la funzione
         * associata
         * @param {object} htmlElement 
         * @param {function} method 
         */
        addChangeEventListener:
            function(htmlElement, method)
            {
                if(config_ol.html_page_elments.use_jquery)
                {
                    htmlElement.change(method);
                }
                else
                {
                    htmlElement.addEventListener('change', method);
                }
            }
    }

};