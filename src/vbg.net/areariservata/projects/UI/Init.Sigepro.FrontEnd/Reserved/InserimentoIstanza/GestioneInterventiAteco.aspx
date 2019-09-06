<%@ Page Title="Untitled page" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="GestioneInterventiAteco.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneInterventiAteco" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="cc1" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.Interventi" %>
<%@ Register TagPrefix="ar" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" %>
<%@ Register TagPrefix="cc2" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.Ateco" %>

<%@ Register Assembly="Init.Utils" Namespace="Init.Utils.Web.UI" TagPrefix="cc3" %>

<asp:Content ID="Content2" ContentPlaceHolderID="head" runat="server">

    <link href='<%=ResolveClientUrl("~/css/contenuti/dettagliIntervento.css") %>' type="text/css" rel="stylesheet" />

    <%=LoadScripts(new[]{
        "~/js/app/alberointerventi.js",
        "~/js/app/wrapDescrizioneNodiPadre.js",
    }) %>


    <script type="text/Javascript">

        window.alberoInterventi = new AlberoInterventi();
        window.alberoInterventi.dialogDettaglioInterventiOpened = wrapDescrizioneNodiPadre;

        window.alberoAteco = new AlberoInterventi();
        window.alberoAteco.dialogDettaglioInterventiOpened = wrapDescrizioneNodiPadre;

        function inizializzaContenutoAccordion(elName) {
            $(elName + ' #accordion').accordion({ header: "h3", heightStyle: 'content' });
            $(elName + ' #accordion table TR:even').addClass('AlternatingItemStyle');
            $(elName + ' #accordion table TR:odd').addClass('ItemStyle');
        }

        $(function () {

            var el = $("#dettagliIntervento");
            var el2 = $("#dettagliEndo");

            if (el.length == 0)
                $('#contenuti').append("<div id='dettagliIntervento'></div>");

            if (el2.length == 0)
                $('#contenuti').append("<div id='dettagliEndo'></div>");

            // preparo il dialog per i dettagli dell'endo
            $("#dettagliEndo").dialog({
                width: 600,
                height: 500,
                title: "Dettagli dell\'endoprocedimento",
                modal: true,
                autoOpen: false,
                open: function () {
                    inizializzaContenutoAccordion('#dettagliEndo');
                }
            });

            $('#dettagliIntervento').dialog({
                height: 500,
                width: 800,
                title: "Dettagli dell\'intervento",
                modal: true,
                autoOpen: false,
                open: function () {
                    inizializzaContenutoAccordion('#dettagliIntervento');

                    $('.linkDettagliendo').click(function (e) {
                        e.preventDefault();

                        var url = $(this).attr('href');

                        $("#dettagliEndo").load(url, null, function () {
                            $(this).dialog('open');
                        });
                    });
                }
            });
        });

        function mostraDettagli(sender, id) {
            var url = '<%= ResolveClientUrl("~/Public/MostraDettagliIntervento.aspx")%>?IdComune=<%=IdComune %>&Software=<%=Software%>&fromAreaRiservata=true&Id=' + id;

                var oldHtml = $(sender).html();
                var clickElement = $(sender);

                clickElement.html('<img src=\'<%= ResolveClientUrl("~/Images/ajax-loader.gif") %>\' />');
            $("#dettagliIntervento").html('Caricamento in corso...');
            $("#dettagliIntervento").load(url, null, function (responseText, textStatus, XMLHttpRequest) {
                clickElement.html(oldHtml);
                $(this).dialog('open');
            });
        }

        window.mostraDettagli = mostraDettagli;
    </script>

</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
    <div class="step-interventi">
        <asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_activeViewChanged">

            <asp:View runat="server" ID="introduzioneView">
                <div>
                    <asp:Literal runat="server" ID="ltrTestoIntroduzione"></asp:Literal>
                </div>

                <div class="bottoni">
                    <asp:Button runat="server" ID="cmdRicercaAteco" Text="Ricerca per classificazione ATECO" OnClick="cmdRicercaAteco_Click" />
                    <asp:Button runat="server" ID="cmdRicercaIntervento" Text="Ricerca per classificazione attività produttiva" OnClick="cmdRicercaIntervento_Click" />
                    <asp:Button runat="server" ID="cmdAnnullaRicerca" Text="Annulla" OnClick="AnnullaRicercaClick" />
                </div>
            </asp:View>

            <asp:View runat="server" ID="atecoView">
                <fieldset>
                    <legend>
                        <asp:Literal runat="server" ID="ltrIntestazioneRicercaAteco" /></legend>
                    <asp:Literal runat="server" ID="ltrTestoRicercaAteco" />
                    <a href="#" id="lnkRicerca">[Ricerca testuale]</a>
                </fieldset>

                <cc2:AlberoAtecoJs runat="server"
                    ID="alberoAteco"
                    ClientIdLinkRicerca="lnkRicerca"
                    OnFogliaSelezionata="BindAlberoInterventi" AreaRiservata="true" />

                <div class="bottoni">
                    <asp:Button runat="server" ID="cmdAnnullaAteco" Text="Annulla" OnClick="AnnullaRicercaClick" />
                </div>
            </asp:View>

            <asp:View runat="server" ID="alberoView">
                <h2>
                    <asp:Literal runat="server" ID="ltrIntestazioneRicercaIntervento" />

                </h2>
                <asp:Literal runat="server" ID="ltrTestoRicercaIntervento" />
                <a href="#" id="lnkRicerca">[Ricerca testuale]</a>

                <div class="albero-interventi">
                    <cc1:AlberoInterventiJs EvidenziaVociAttivabiliDaAreaRiservata="false" runat="server" AreaRiservata="true" ID="alberoInterventi" OnFogliaSelezionata="InterventoSelezionato" />
                </div>

                <div class="bottoni">
                    <asp:Button runat="server" ID="cmdAnnullaAlbero" Text="Annulla" OnClick="AnnullaRicercaClick" />
                </div>

            </asp:View>

            <asp:View runat="server" ID="dettaglioView">

                <script type="text/javascript">

                    $(function () {

                        var contieneDati = <%=VerificaEsistenzaDatiStepSuccessivi().ToString().ToLower() %>;

                        $('#<%= cmdModificaIntervento.ClientID%>').click(function (e) {

                            if (!contieneDati)
                                return;

                            $('#<%=bmConfermaVariazione.ClientID%>').modal("show");

                            e.preventDefault();
                        });

                    });

                </script>



                <h3>
                    <asp:Literal runat="server" ID="ltrIntestazioneDettaglio" />
                </h3>
                <asp:Literal runat="server" ID="ltrTestoDettaglio" />


                <cc1:InterventiTreeRenderer runat="server"
                    ID="treeRendererDettaglio"
                    StartCollapsed="false"
                    CssClass="treeView"
                    UrlIconaHelp="~/Images/help_interventi.gif"
                    UrlDettagliIntervento="javascript:mostraDettagli(this,{0});" />

                <asp:Button runat="server" ID="cmdModificaIntervento" CssClass="btn btn-default modifica-intervento" Text="Cambia l’intervento selezionato" OnClick="cmdSelezionaIntervento_Click" />

                <ar:BootstrapModal runat="server" ID="bmConfermaVariazione" Title="Attenzione" OnOkClicked="cmdSelezionaIntervento_Click" OkText="Seleziona un intervento differente" KoText="Mantieni l'intervento corrente">
                    <ModalBody>
                        Modificando l'intervento selezionato eventuali endoprocedimenti ed allegati inseriti nei passaggi successivi verranno eliminati.<br />
                        <br />
                        La scelta NON è reversibile, si desidera proseguire? 
                    </ModalBody>
                </ar:BootstrapModal>
            </asp:View>

            <asp:View runat="server" ID="erroreAutenticazioneView">
                <fieldset>
                    <div style="display: none">
                        <asp:Literal runat="server" ID="modelloErroreAutenticazione">
                            Il servizio che si sta tentando di attivare prevede una autenticazione di tipo:
                            <ul>
                                <li>{0}</li>
                            </ul>
                            L’utente attualmente connesso ha un livello di autenticazione che è il seguente:
                            <ul>
                                <li>{1}</li>
                            </ul>
                            <u>Non è possibile proseguire.</u>
                            <br /><br />
                
                            Informazioni:
                            <ul>
                        	    <li><b>Identità anonima:</b> l’utente ha acceduto al sistema senza digitare credenziali</li>
                        	    <li><b>Identità verificata:</b> l’utente ha acceduto al sistema con le sue credenziali che sono associate ad un dispositivo di CNS o di CRS oppure è stato riconosciuto de VISU, dopo la presentazione di apposito documento d’identità, dal personale addetto al riconoscimento (non tutti i sistemi di autenticazione prevedono questo tipo di riconoscimento)</li>
                        	    <li><b>Identità non verificata:</b> l’utente ha acceduto al sistema con le sue credenziale ma non ha attivato le procedure atte a certificare la sua identità (vedi Identità verificata)</li>
                            </ul>
                        </asp:Literal>
                    </div>

                    <div class="descrizioneStep">
                        <asp:Literal runat="server" ID="ltrErroreAutenticazione">
                            
                        </asp:Literal>
                    </div>

                    <div class="bottoni">
                        <asp:Button runat="server" ID="cmdCambiaIntervento" Text="Cambia intervento" OnClick="cmdCambiaIntervento_Click" />
                    </div>
                </fieldset>
            </asp:View>

        </asp:MultiView>
    </div>
    <div id="debugDiv"></div>
</asp:Content>
