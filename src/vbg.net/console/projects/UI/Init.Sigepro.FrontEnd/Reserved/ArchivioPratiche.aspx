<%@ Page Title="Archivio pratiche" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="ArchivioPratiche.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.ArchivioPratiche" %>
<%@ Register TagPrefix="cc1" Namespace="Init.Sigepro.FrontEnd.WebControls.Visura" Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<%@ MasterType VirtualPath="~/AreaRiservataMaster.Master" %>

<asp:Content runat="server" ID="header" ContentPlaceHolderID="headPagina">
<script type="text/javascript">

        require(['app/ar-autocomplete', 'jquery', 'jquery.ui'], function (ArAutocomplete, $) {

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
        });
    </script>
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
		<asp:View runat="server" ID="ricercaView">
			<div class="inputForm">
				<cc1:FiltriArchivioIstanzeControl ID="FiltriVisura" runat="server" />
				<fieldset>
					<div class="bottoni">
						<asp:Button ID="cmdSearch" runat="server" Text="Cerca" OnClick="cmdSearch_Click" /></div>
				</fieldset>
			</div>
		</asp:View>
		<asp:View runat="server" ID="listaView">
			<div class="inputForm">
				<fieldset>
					<div>
						<cc1:ListaArchivioIstanzeDataGrid runat="server" ID="dglistaPratiche" Width="100%" OnIstanzaSelezionata="dglistaPratiche_IstanzaSelezionata">
						</cc1:ListaArchivioIstanzeDataGrid>
					</div>
					<div class="bottoni">
						<asp:Button ID="cmdNewSearch" runat="server" Text="Nuova Ricerca" OnClick="cmdNewSearch_Click" />
					</div>
				</fieldset>
			</div>
		</asp:View>
	</asp:MultiView>
</asp:Content>
