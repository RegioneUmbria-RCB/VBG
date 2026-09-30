<%@ Page Async="true" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="IstanzePresentate.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.IstanzePresentate"
    Title="Le mie pratiche" %>

<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<%@ Register TagPrefix="cc1" Namespace="Init.Sigepro.FrontEnd.WebControls.Visura" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<%@ Register TagPrefix="cc2" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils.Web" %>
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

        function recuperaUuid() {
            let uuid = [];
            document.querySelectorAll('table>tbody>tr').forEach(tr => {
                uuid.push(tr.dataset.uuid);
            });

            const hidden = document.getElementById('<%=hidUuidPratiche.ClientID%>');
            hidden.value = JSON.stringify(uuid);
            console.log('fatto');
            return true;
        }

        $(function () {
            var ricercaintervento = new ArAutocomplete('.ricerca-intervento', ricercaTestualeIntervento);
            var ricercaStradario = new ArAutocomplete('.ricerca-stradario', ricercaTestualeStradario);

            ricercaintervento.svuotaCampi();
            ricercaStradario.svuotaCampi();

            $('#<%=cmdSearch.ClientID%>').on('click', function () {
                $('#<%=bmAttendere.ClientID%>').modal('show');
            });
        });

        $(() => {
            const contesto = $('.campo-input-anno > input[type=text]');
            contesto.on('keypress', (event) => {
                var regex = new RegExp("[0-9]");
                var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
                if (!regex.test(key)) {
                    event.preventDefault();
                    return false;
                }

                if (contesto.val().length === 4) {
                    event.preventDefault();
                    return false;
                }
            });

            contesto.on("blur", () => {
                contesto.val(contesto.val().padEnd(4, "0"));
            })
        });
    </script>
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
        <asp:View runat="server" ID="ricercaView">
            <div class="inputForm">
                <cc1:FiltriVisuraControl ID="FiltriVisura" runat="server"></cc1:FiltriVisuraControl>
                <asp:Button ID="cmdSearch" CssClass="btn btn-primary" runat="server" Text="Cerca" OnClick="cmdSearch_Click" />
            </div>

            <ar:BootstrapModal runat="server" ID="bmAttendere" Title="Caricamento dei dati in corso, si prega di attendere..." ShowKoButton="false" ShowOkButton="false">
                <ModalBody>
                    <div class="progress progress-striped active" style="margin-bottom: 0;">
                        <div class="progress-bar" style="width: 100%"></div>
                    </div>
                </ModalBody>
            </ar:BootstrapModal>
        </asp:View>
        <asp:View runat="server" ID="listaView">
            <asp:HiddenField runat="server" ID="hidUuidPratiche" Value=""  />
            <script type="text/javascript">
                $(function () {
                    $('.tabella-risultati table').DataTable({
                        "order": [
                            <%= (dglistaPratiche.IndiceColonnaDataIstanza != -1)? "[" + dglistaPratiche.IndiceColonnaDataIstanza + ", 'desc']" : ""%>
                        ],
                        "columnDefs": [
                            <%= (dglistaPratiche.IndiceColonnaDataIstanza != -1 || dglistaPratiche.IndiceColonnaDataProtocollo != -1) ?
                                    "{ 'type': 'data', 'targets': [" + dglistaPratiche.IndiceColonnaDataIstanza + "," + dglistaPratiche.IndiceColonnaDataProtocollo + "]}," :
                                    ""
                            %>
                            { "targets": [<%=dglistaPratiche.Columns.Count -1 %>], "searchable": false, "orderable": false }
                        ]
                    });
                })

            </script>

            <div class="tabella-risultati lista-visura table-responsive">
                <cc1:ListaVisuraDataGrid runat="server" DataKeyNames="Uuid" ID="dglistaPratiche" PermettiUsaComeModello="true" NavigateUrlFormatString='<%#GetNavigateUrlFormatString() %>' CssClass="table" EmptyDataText="Non sono state trovate pratiche corrispondenti ai criteri di ricerca">
                </cc1:ListaVisuraDataGrid>
            </div>


            <asp:Button ID="cmdNewSearch" runat="server" CssClass="btn btn-default" Text="Nuova Ricerca" OnClick="cmdNewSearch_Click" />
            <asp:Button ID="cmdMostraInMappa" runat="server" CssClass="btn btn-default mostra-mappa" Text="Mostra in mappa" OnClientClick="recuperaUuid();" OnClick="cmdMostraInMappa_Click"/>
        </asp:View>
    </asp:MultiView>
</asp:Content>
