<%@ Page Title="Gestione endo console" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="GestioneEndoConsole.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneEndoConsole" %>

<%@ Register Src="~/Reserved/InserimentoIstanza/GestioneEndoConsole_EndoView.ascx" TagPrefix="uc1" TagName="GestioneEndoConsole_EndoView" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>


<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <style>
        .ricerca-hidden {
            display: none;
        }

        .dettaglio-sub-endo>li.ricerca-hidden {
            display:block;
        }

        .ricerca-aperto > ul{
            display: block !important;
        }

        span[data-id-endo] {
            display:flex;
        }

        .gestione-endo-console .fa
        {
                margin-left: 5px;
        }

        .ricerca-testuale small {
            font-weight: normal;
            font-size: 75%;
        }
    </style>

    <script type="text/javascript" src='<%= ResolveClientUrl("~/js/lib/jquery.ui.js")%>'></script>

    <script type="text/javascript">
        var endoAttivati = [];


        $(function () {
            var endoDictionary = {};

            function espandiElemento($el) {
                $el.removeClass("albero-chiuso").addClass("albero-aperto");
            }

            function comprimiElemento($el) {
                $el.removeClass("albero-aperto").addClass("albero-chiuso");
            }

            // Inizializzo gli eventuali endo attivati in precedenti passaggi
            (function inizializzaEndoAttivati() {
                $('input[type=checkbox]').each(function (idx, item) {
                    var $item = $(this),
                        idEndo = $item.parent().data("idEndo");

                    endoDictionary[idEndo] = $item;
                });

                for (var i = 0; i < endoAttivati.length; i++) {
                    var endo = endoDictionary[endoAttivati[i]];

                    if (endo) {
                        endo.prop("checked", true);
                        onEndoprocedimentoClick(endo);
                    }
                }
            })();


            // Gestione del click per aprire/chiudere l'albero
            $(".gestione-endo-console li").on("click", function (e) {

                if ((e.target.tagName != "LI" && e.target.tagName != "SPAN") || !$(this).hasClass("ramo-albero")) {
                    return;
                }

                if ($(this).hasClass("albero-aperto")) {
                    comprimiElemento($(this));
                } else if ($(this).hasClass("albero-chiuso")) {
                    espandiElemento($(this));
                }

                return false;
            });



            $('input[type=checkbox]').each(function (idx, item) {
                var $item = $(item);

                if (!$item.is(':checked')) {
                    return;
                }

                var closest = $item.closest("li.ramo-albero");

                while (closest.length) {
                    espandiElemento(closest);

                    closest = closest.parent().closest("li.ramo-albero");
                }
            });

            function onEndoprocedimentoClick($el) {
                // è un sub endo?
                if ($el.parent().hasClass("chk-sub-endo")) {
                    if ($el.is(":checked")) {
                        // Se è selezionato allora seleziono tutti i nodi padre
                        $el.parent().closest(".endoprocedimento-check").find(">span>input[type=checkbox]").each(function (idx, item) {
                            $(item).prop("checked", true);
                        });

                        $el.parent().closest(".dettaglio-sub-endo").find(">li>span>input[type=checkbox]").each(function (idx, item) {
                            if ($(item).parent().data("richiesto") == "True") {
                                $(item).prop("checked", true);
                            }
                        });
                    } else {
                        if ($el.parent().data("richiesto") === "True") {
                            //$el.closest("li").find('input[type=checkbox]').prop("checked", false);
                            $el.closest("ul").closest("li").find('input[type=checkbox]').prop("checked", false);
                        }
                    }
                } else {

                    if ($el.is(":checked")) {
                        // Se è un endo padre attivo tutti i sub endo richiesti
                        $el.parent().parent().find("ul input[type=checkbox]").each(function (idx, item) {
                            if ($(item).parent().data("richiesto") == "True") {
                                $(item).prop("checked", true);
                            }
                        });
                    } else {
                        $el.parent().parent().find("ul input[type=checkbox]").each(function (idx, item) {
                            $(item).prop("checked", false);
                        });
                    }
                }
            }

            $('input[type=checkbox]').on("click", function onCheckboxClick() {
                var $el = $(this);

                onEndoprocedimentoClick($el);
            });

            // ricerca testuale

            function filtraSottonodi($roots, testo) {
                $roots.toArray().forEach(function (ul) {
                    var $ul = $(ul),
                        $liList = $ul.find(">li");

                    $liList.toArray().forEach(function (li) {
                        var $li = $(li);

                        if ($li.data("testi").filter(x => x.toLowerCase().indexOf(testo) !== -1) == 0) {
                            $li.addClass("ricerca-hidden");
                        } else {
                            if ($li.hasClass("ramo-albero")) {
                                //espandiElemento($li);
                                $li.addClass("ricerca-aperto");
                            }

                            filtraSottonodi($li.find(">ul"), testo);
                        }
                    });
                });
            }

            var cmdTerminaRicerca = $("#cmdTerminaRicerca");

            cmdTerminaRicerca.on("click", function (e) {
                $("#txtRicercaTestuale").val('');
                $("#txtRicercaTestuale").trigger("keyup");

                e.preventDefault();
            });

            function filtraEndo(testo) {
                var $ul = $("#<%=pnlAltriEndo.ClientID%>>div.albero-endo-console>ul.famiglie-endo");

                $(".ricerca-hidden").removeClass("ricerca-hidden");
                $(".ricerca-aperto").removeClass("ricerca-aperto");
                
                if (testo.length < 2) {
                    return;
                }

                cmdTerminaRicerca.show();

                filtraSottonodi($ul, testo.toLowerCase());
            }


            function inizializzaIndiceNodi($roots, accumulatoreTesti) {

                $roots.toArray().forEach(function (ul) {
                    var $ul = $(ul),
                        $liList = $ul.find(">li");

                    $liList.toArray().forEach(function (li) {
                        var $span = $(li).find(">span");
                        var txt = $span.text(),
                            testi = [];

                        testi.push(txt);

                        inizializzaIndiceNodi($(li).find(">ul"), testi);

                        for (var i = 0; i < testi.length; i++) {
                            accumulatoreTesti.push(testi[i]);
                        }

                        $(li).data("testi", testi);
                    });
                });
            }

            inizializzaIndiceNodi($("#<%=pnlAltriEndo.ClientID%>>div.albero-endo-console>ul.famiglie-endo"), []);


            $("#txtRicercaTestuale").on("keyup", function (e) {
                var $el = $("#txtRicercaTestuale");

                if (e.key === "Escape") {
                    cmdTerminaRicerca.trigger("click");
                    return;
                }

                filtraEndo($el.val());
            });



            // POPUP dettagli endoprocedimento
            var modal = $("#<%=bmDettagliEndo.ClientID%>");

            function mostraDettagliEndo(sender, id) {
                var url = '<%= ResolveClientUrl("~/Public/MostraDettagliEndoB.aspx") + "?idComune=" + IdComune + "&software=" + Software + "&_ts=" + DateTime.Now.Millisecond + "&Id="%>' + id;

                sender.removeClass('fa-question-circle').addClass('fa-spinner').addClass('roteante');

                modal.find(".modal-body>div").load(url, null, function (responseText, textStatus, XMLHttpRequest) {
                    sender.removeClass('roteante')
                        .removeClass('fa-spinner')
                        .addClass('fa-question-circle');

                    $(this).find('#accordion').accordion({ header: "h3", heightStyle: 'content' });

                    modal.modal('show');
                });
            }

            $('.gestione-endo-console .fa-question-circle').each(function (idx, item) {
                var $i = $(item);

                $i.parent().find(">span>label").append($i);
            });

            $('.gestione-endo-console .fa-question-circle').click(function () {
                var idEndo = $(this).data('idEndo');

                mostraDettagliEndo($(this), idEndo);
            });
        })
    </script>

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="stepContent" runat="server">

    <div class="gestione-endo-console">





        <h3>
            <asp:Literal runat="server" ID="ltrTitoloEndoPrincipale" Text="Procedimento principale" />
        </h3>
        <%--<uc1:GestioneEndoConsole_EndoView runat="server" ID="endoViewPrincipali" ForzaSelezioneRichiesti="false" />--%>
        <uc1:GestioneEndoConsole_EndoView runat="server" ID="endoViewPrincipali" />

        <asp:Panel runat="server" ID="pnlEndoAttivati">
            <h3>
                <asp:Literal runat="server" ID="ltrTitoloEndoAttivati" Text="Procedimenti attivabili" />
            </h3>
            <uc1:GestioneEndoConsole_EndoView runat="server" ID="endoViewAttivati" />
        </asp:Panel>

        <asp:Panel runat="server" ID="pnlEndoAttivabili">
            <h3>
                <asp:Literal runat="server" ID="ltrTitoloEndoAttivabili" Text="Altri endoprocedimenti attivati"></asp:Literal>
            </h3>
            <uc1:GestioneEndoConsole_EndoView runat="server" ID="endoViewRicorrenti" />
        </asp:Panel>

        <asp:Panel runat="server" ID="pnlAltriEndo">
            <h3>
                <asp:Literal runat="server" ID="ltrTitoloAltriEndo" Text="Altri endoprocedimenti"></asp:Literal>
            </h3>

            <div class="row">
                <div class="col-md-6">
                    <div class="ricerca-testuale">
                        <div class="form-group">
                            <label>Inserisci il testo da cercare<br/> <small>Premi esc per terminare la ricerca</small></label>
                            <div class="input-group">
                                <%--<span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>--%>
                                <input type="text" class="form-control" id="txtRicercaTestuale" />
                                <span class="input-group-addon"><i class="glyphicon glyphicon-remove" id="cmdTerminaRicerca" style="cursor:pointer"></i></span>
                            </div>
                        </div>
                    </div>
                </div>

            </div>


            <uc1:GestioneEndoConsole_EndoView runat="server" ID="endoViewAltriEndo" />
        </asp:Panel>
    </div>

    <ar:BootstrapModal runat="server" ID="bmDettagliEndo" ShowOkButton="false" Title="Dettagli endoprocedimento"></ar:BootstrapModal>


</asp:Content>
