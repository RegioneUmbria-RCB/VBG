<%@ Page Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="IstanzePresentate.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.IstanzePresentate"
    Title="Le mie pratiche" %>

<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>


<%@ Register TagPrefix="cc1" Namespace="Init.Sigepro.FrontEnd.WebControls.Visura" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<asp:Content runat="server" ContentPlaceHolderID="headPagina">

    <%=LoadScript("~/js/app/ar-autocomplete.js") %>

    <script type="text/javascript">

        function ricercaTestualeStradario(term, successCallback) {
            var url = '<%=ResolveClientUrl("~/Public/WebServices/AutocompleteStradario.asmx") %>/AutocomlpeteStradario';

                var codiceComune = $('.codice-comune');

                $.ajax({
                    url: url,
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    data: JSON.stringify({
                        idComune: '<%=IdComune%>',
                    codiceComune: codiceComune.length == 0 ? '' : codiceComune.val(),
                    comuneLocalizzazione: '',
                    match: term
                }),
                success: function (data) {
                    var d = {
                        d: data.d.Items
                    };

                    successCallback(d);
                }
            });
        }


        function ricercaTestualeIntervento(term, successCallback) {
            $.ajax({
                url: '<%=ResolveClientUrl("~/Public/WebServices/InterventiJsService.asmx")%>/RicercaTestuale',
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    data: JSON.stringify({
                        aliasComune: '<%=IdComune%>',
                        software: '<%=Software%>',
                    matchParziale: term,
                    matchCount: 9999,
                    modoRicerca: '',
                    tipoRicerca: '',
                    areaRiservata: true,
                    utenteTester: false
                }),
                success: function (data) {
                    successCallback(data);
                }
            });
        };

        $(function () {
            var ricercaintervento = new ArAutocomplete('.ricerca-intervento', ricercaTestualeIntervento);
            var ricercaStradario = new ArAutocomplete('.ricerca-stradario', ricercaTestualeStradario);

            ricercaintervento.svuotaCampi();
            ricercaStradario.svuotaCampi();
        });
    </script>
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
        <asp:View runat="server" ID="ricercaView">
            <div class="inputForm">
                <cc1:FiltriVisuraControl ID="FiltriVisura" runat="server"></cc1:FiltriVisuraControl>
                <fieldset>
                    <div class="bottoni">
                        <asp:Button ID="cmdSearch" runat="server" Text="Cerca" OnClick="cmdSearch_Click" />
                    </div>
                </fieldset>
            </div>
        </asp:View>
        <asp:View runat="server" ID="listaView">

            <script type="text/javascript">
                $(function () {
                    $('.tabella-risultati table').DataTable({
                        "order": []
                    });
                })
            </script>

            <div class="tabella-risultati">
                <cc1:ListaVisuraDataGrid runat="server" ID="dglistaPratiche" CssClass="table" OnIstanzaSelezionata="dglistaPratiche_IstanzaSelezionata">
                </cc1:ListaVisuraDataGrid>
            </div>


            <asp:Button ID="cmdNewSearch" runat="server" CssClass="btn btn-default" Text="Nuova Ricerca" OnClick="cmdNewSearch_Click" />
        </asp:View>
    </asp:MultiView>
</asp:Content>
