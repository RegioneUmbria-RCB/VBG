<%@ Page Title=" " Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="gestione-localizzazioni-modena.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.localizzazioni_modena.gestione_localizzazioni_modena" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.2/css/bootstrap-select.min.css">
    <link rel="stylesheet" href="https://cdn.rawgit.com/openlayers/openlayers.github.io/master/en/v5.3.0/css/ol.css" type="text/css" />
    <!-- stili generale per progetti mappe openlayers del comune di Modena (minificato - produzione) -->
    <link rel="stylesheet" href='<%=ResolveClientUrl("~/reserved/inserimentoistanza/localizzazioni-modena/resources/src/css/stile_base_template.css") %>' type="text/css" />
    <!-- stili di progetto -->
    <link rel="stylesheet" href='<%=ResolveClientUrl("~/reserved/inserimentoistanza/localizzazioni-modena/resources/src/css/current_project_bootstrap.css") %>' type="text/css" />

    <style>
        .badge-danger {
            background-color: #d9534f !important;
        }

        .contenitore-mappa label {
            margin-top: 5px;
        }
    </style>

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="stepContent" runat="server">
    <asp:MultiView ActiveViewIndex="1" ID="multiView" runat="server">
        <asp:View runat="server">
            <!-- Vista Lista -->
            <asp:GridView runat="server" ID="gvLocalizzazioni" GridLines="None" AutoGenerateColumns="false" CssClass="table">
                <Columns>
                    <asp:BoundField HeaderText="Foglio" DataField="Foglio" />
                    <asp:BoundField HeaderText="Mappale" DataField="Particella" />
                </Columns>
            </asp:GridView>

            <asp:Button Text="Modifica" runat="server" ID="cmdModifica" OnClick="cmdModifica_Click" CssClass="btn btn-default" />
        </asp:View>

        <asp:View runat="server">
            <!-- Vista Dettaglio/modifica vie -->

            <%--<h3>Seleziona particelle</h3>--%>
            <div class="contenitore-mappa">
                <div class="row">
                    <div class="col-sm-9 col-md-10 col-lg-8 col-xl-7">
                        <div class="form-group row">

                            <div class="col-md-4">
                                <asp:LinkButton runat="server" ID="lbTornaAllaLista" CssClass="btn btn-default" OnClick="cmdTornaAllaLista_Click">
                                    <i class="glyphicon glyphicon-chevron-left"></i>
                                    Torna alla lista
                                </asp:LinkButton>
                            </div>

                            <div class="col-md-2">
                                <label class="col-form-label pull-right" for="lista_fogli">
                                    Foglio
                                </label>
                            </div>

                            <div class="col-md-2">
                                <select id="lista_fogli" class="form-control align_right selectpicker"
                                    data-live-search="true" data-width="10em">
                                </select>
                            </div>

                            <div class="col-md-2">
                                <label class="col-form-label pull-right" for="lista_mappali_select" id="lista_mappali_label">
                                    Mappale
                                </label>
                            </div>


                            <div class="col-md-2" id="particella_area">
                                <select id="lista_mappali_select" class="form-control align_right selectpicker"
                                    data-live-search="true" data-width="10em" style="display: none">
                                </select>
                                <select id="lista_mappali_alt_text" class="form-control align_right selectpicker"
                                    data-width="10em">
                                    <option value="">--</option>
                                </select>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="row">
                    <div class="col-sm-8 col-md-9 col-lg-10">
                        <div id="map" class="map"></div>
                    </div>
                    <div class="col-sm-4 col-md-3 col-lg-2">

                        <div class="panel panel-default" id="selection_block">

                            <div class="panel-heading">
                                <span id="foglio_selezionato"></span>
                                <a class="badge badge-danger pull-right" href="#" id="annulla_selezione">X</a>
                            </div>

                            <div class="" style="overflow-y: auto; overflow-x: hidden; /* display: none; */height: 20em;">
                                <ul id="lista_particelle_selezionate" class="list-group list-group-flush dropdown"></ul>
                            </div>

                            <div class="panel-footer">
                                <a class="btn btn-primary form-control" href="javascript:function __a(){return false;}" id="conferma_selezione">Conferma
                                </a>
                            </div>
                        </div>


                    </div>
                </div>
            </div>




            <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
                integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
                crossorigin="anonymous"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.2/js/bootstrap-select.min.js"></script>
            <script crossorigin="anonymous"
                src="https://polyfill.io/v3/polyfill.min.js?features=default%2Cfetch%2CrequestAnimationFrame%2CElement.prototype.classList%2CURL%2CArray.prototype.includes"></script>
            <!-- LIBRERIA OPENLAYERS E PROJ4JS -->
            <script type="text/javascript"
                src="https://cdn.rawgit.com/openlayers/openlayers.github.io/master/en/v5.3.0/build/ol.js"></script>
            <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/proj4js/2.5.0/proj4.js"></script>


            <!-- FILE DI CONFIGURAZIONE APPLICAZIONE -->
            <script type="text/javascript" src="resources/config_modena_bootstrap.js"></script>

            <!-- INIZIO FILES JS SORGENTI (sviluppo) -->
            <%=LoadScripts(new[] {
                "~/reserved/inserimentoistanza/localizzazioni-modena/resources/config_modena_bootstrap.js",
                "~/reserved/inserimentoistanza/localizzazioni-modena/resources/src/js/commonFunctions/wrappers.js",
                "~/reserved/inserimentoistanza/localizzazioni-modena/resources/src/js/commonFunctions/commonUtils.js",
                "~/reserved/inserimentoistanza/localizzazioni-modena/resources/src/js/commonFunctions/gisUtils.js",
                "~/reserved/inserimentoistanza/localizzazioni-modena/resources/src/js/commonFunctions/catastoUtils.js",
                "~/reserved/inserimentoistanza/localizzazioni-modena/resources/src/js/projectFunctions.js",
                "~/reserved/inserimentoistanza/localizzazioni-modena/resources/src/js/main.js",
            }) %>
            <script type="text/javascript">
                (function () {

                    function postToHandler(localizzazioni) {
                        var handler = '<%=ResolveClientUrl("~/reserved/inserimentoistanza/localizzazioni-modena/handlers/localizzazionihandler.asmx") + "/ModificaLocalizzazioni" + "?idcomune=" + this.IdComune + "&software=" + this.Software %>';
                        var data = {
                            idDomanda: '<%=this.IdDomanda%>',
                            opzioniDefault: {
                                idStradarioDefault: <%=this.IdStradarioDefault%>,
                                idCatastoDefault: '<%=this.IdCatastoDefault%>',
                                nomeCatastoDefault: '<%=this.NomeCatastoDefault%>'
                            },
                            localizzazioni: localizzazioni,
                        };

                        mostraModalCaricamento();

                        $.ajax({
                            url: handler,
                            method: 'POST',
                            contentType: "application/json; charset=utf-8",
                            dataType: "json",
                            data: JSON.stringify(data)
                        })
                        .done(function () {
                            //__doPostBack('<%=lbTornaAllaLista.UniqueID%>', '');
                            location.replace(window.location);
                        })
                        .fail(function (jqXHR, textStatus, errorThrown) {

                            nascondiModalCaricamento();

                            console.error(jqXHR, textStatus, errorThrown);
                        });
                    }




                    // Inizializzazione delle particelle già selezionate:
                    // window.arrayParticelleInput = ["F257   18  181", "F257   18  187", "F257   18  206"];
                    window.arrayParticelleInput = [<%= GetArrayJsPerInizializzazione() %>];

                    //FUNZIONE PERSONALIZZABILE PER INTEGRAZIONE CON VBG
                    //IN INPUT ARRIVA UN ARRAY DI PARTKEY ANALOGO A QUELLO 
                    //DA PASSARE PER INIZIALIZZARE LA MAPPA
                    window.callBack = function (arrayParticelleOutput) {
                        const mapped = arrayParticelleOutput.map(function (x) {
                            const codCatastale = x.slice(0, 4);
                            const foglio = x.slice(4, 9).trim();
                            const particella = x.slice(9, 14).trim();

                            return {
                                codCatastale: codCatastale,
                                foglio: foglio,
                                particella: particella
                            };
                        });

                        console.log(mapped);
                        postToHandler(mapped);
                        //return false;
                    };

                    //a caricamento pagina completato, avvio applicazione
                    // $(run); // ???? dovrebbe essere lo stesso
                    $(document).ready(
                        function () {
                            run();
                        }
                    );
                }());


            </script>


        </asp:View>
    </asp:MultiView>
</asp:Content>
