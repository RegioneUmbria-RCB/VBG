<%@ Page Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" MaintainScrollPositionOnPostback="true" AutoEventWireup="true" CodeBehind="GestioneAllegatiIntervento.aspx.cs"
    Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneAllegatiIntervento" Title="Untitled Page" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>

<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>


<%@ Register TagPrefix="cc1" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils" %>
<%@ Register TagPrefix="cc2" Namespace="Init.Sigepro.FrontEnd.WebControls.Common" Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<%@ Register TagPrefix="uc1" Src="~/Reserved/InserimentoIstanza/Allegati/GrigliaAllegati.ascx" TagName="GrigliaAllegati" %>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">

    <%=LoadScript("~/js/app/uploadAllegati.js") %>

    <script type="text/javascript">
        $(function () {
            var loaderUrl = '<%=ResolveClientUrl("~/Images/ajax-loader.gif")%>',
                    urlBaseNote = '<%=ResolveClientUrl("~/Reserved/InserimentoIstanza/GestioneAllegati_Note.ashx")%>',
                    idComune = '<%=IdComune %>',
                    token = '<%=UserAuthenticationResult.Token %>',
                    software = '<%=Software %>',
                    idDomanda = '<%=IdDomanda %>',
                    provenienza = 'I',
                    modalAggiungiAllegato = $("#<%=bmAggiungiAllegato.ClientID%>"),
                    ddlNumeroPagine = $("#<%=ddlNumeroPagine.Inner.ClientID%>"),
                    ddlFormato = $("#<%=ddlFormato.Inner.ClientID%>");


                new UploadAllegati(loaderUrl, urlBaseNote, idComune, token, software, idDomanda, provenienza);

                modalAggiungiAllegato.on("hidden.bs.modal shown.bs.modal", function () {
                    updateValidators();
                });

                var dimensioniFiles = [];
                <%foreach (var it in this._parametriAllegatiLiberi.Parametri.FormatiAllegatiLiberi){%>
                dimensioniFiles[<%=it.Id%>] = <%=it.DimensioneMaxPagina%>;
                <%}%>

            function toHumanReadable(len) {
                var sizes = ["B", "KB", "MB", "GB", "TB"];
                var order = 0;

                while (len >= 1024 && order < sizes.length - 1) {
                    order++;
                    len = len / 1024;
                }

                // Adjust the format string to your preferences. For example "{0:0.#}{1}" would
                // show a single decimal place, and no space.
                return len.toFixed(2).toString() + " " + sizes[order];
            }


            function aggiornaDimensioneTotale() {
                var numeroPagine = parseInt(ddlNumeroPagine.val()),
                    dimensionePagina = dimensioniFiles[parseInt(ddlFormato.val())],
                    calcolato = toHumanReadable(numeroPagine * 1024 * dimensionePagina);

                if (ddlFormato.val() === '') {
                    calcolato = 'Specificare il numero di pagine e il formato del file caricato';
                }

                $(".label-dimensione-massima .form-control").text(calcolato);

            }

            ddlFormato.on("change", aggiornaDimensioneTotale);
            ddlNumeroPagine.on("change", aggiornaDimensioneTotale);

            $(".cmdNuovoAllegato").on("click", function (e) {
                modalAggiungiAllegato.modal("show");

                if (dimensioniFiles.length) {
                    ddlNumeroPagine.val("1");
                    ddlFormato.val(ddlFormato.find("option:first").val());

                    aggiornaDimensioneTotale();
                }

                e.preventDefault();
            });

        });
    </script>


    <ar:AttributiAllegato runat="server" ID="ltrLegendaAttributi" Legenda="true" />

    <asp:Repeater runat="server" ID="rptCategorie" OnItemDataBound="OnItemDataBound">
        <ItemTemplate>
            <div style='font-weight: bold; text-transform: uppercase <%= (NumeroCategorie > 1) ? "" : ";display:none"%>'>
                <asp:Literal runat="server" ID="ltrCategoria" Text='<%# Bind("Descrizione") %>' Visible="<%# NumeroCategorie > 0 %>" />
            </div>

            <uc1:GrigliaAllegati runat="server"
                ID="grigliaAllegati"
                OnAllegaDocumento="OnAllegaDocumento"
                OnCompilaDocumento="OnCompilaDocumento"
                OnFirmaDocumento="OnFirmaDocumento"
                OnRimuoviDocumento="OnRimuoviDocumento"
                OnErrore="ErroreGriglia"
                OnAllegaDocumentiMultipli="AllegaDocumentiMultipli"
                OnGetValidationSpecification="grigliaAllegati_GetValidationSpecification" />
        </ItemTemplate>
    </asp:Repeater>

    <div style="text-align: right">
        <a href="#" class="btn btn-default cmdNuovoAllegato" runat="server" id="cmdNuovoAllegato">Aggiungi allegato libero</a>
    </div>

    <ar:BootstrapModal runat="server" ID="bmNoteAllegato" ExtraCssClass="note-allegato" Title="Note dell'allegato" ShowOkButton="false">
    </ar:BootstrapModal>


    <div id="noteAllegato"></div>

    <ar:BootstrapModal runat="server" ID="bmAggiungiAllegato" Title="Nuovo allegato libero" OnOkClicked="cmdAggiungi_Click" OkText="Aggiungi allegato" KoText="Annulla">
        <ModalBody>
            <ar:DropDownList runat="server" ID="ddlTipoAllegato" Label="Categoria" DataTextField="Descrizione" Required="true" DataValueField="Id" />
            <ar:TextBox runat="server" ID="txtDescrizioneAllegato" Label="Descrizione" Required="true" />
            <ar:ArFileUpload runat="server" ID="fuUploadNuovo" Label="File" Required="true" />

            <asp:Panel runat="server" ID="pnlDimensioniFile">
                <h3>Informazioni sul file</h3>

                <ar:DropDownList runat="server" ID="ddlNumeroPagine" Label="Numero pagine" Required="true"></ar:DropDownList>
                <ar:DropDownList runat="server" ID="ddlFormato" Label="Formato" DataTextField="Formato" DataValueField="Id" Required="true"></ar:DropDownList>
                <ar:LabeledLabel runat="server" ID="lblFormato" Label="Dimensione massima" CssClass="label-dimensione-massima" />

            </asp:Panel>
        </ModalBody>
    </ar:BootstrapModal>

</asp:Content>
