<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" CodeBehind="TipiRateizzazione.aspx.cs" Inherits="Sigepro.net.Archivi.CalcoloOneri.Archivi_CalcoloOneri_TipiRateizzazione" Title="Tipi rateizzazione" %>

<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register TagPrefix="init" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils" %>

<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.UI" Assembly="SIGePro.WebControls" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.Ajax" Assembly="SIGePro.WebControls" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <script type="text/javascript" src="<%=ResolveClientUrl("~/js/xlsx.core.min.js") %>"></script>
    <script type="text/javascript" src="<%=ResolveClientUrl("~/js/FileSaver.min.js") %>"></script>
    <script type="text/javascript" src="<%=ResolveClientUrl("~/js/html2canvas.min.js") %>"></script>
    <script type="text/javascript" src="<%=ResolveClientUrl("~/js/tableExport.min.js") %>"></script>
    <script type="text/javascript" src="<%=ResolveClientUrl("~/js/jspdf.min.js") %>"></script>
    <script type="text/javascript" src="<%=ResolveClientUrl("~/js/jspdf.plugin.autotable.js") %>"></script>

    <script type="text/javascript">

        $(function () {
            $('#iconaPdf').on('click', function (e) {
                e.preventDefault();
                ExportToPdf();
            });

            $('#iconaExcel').on('click', function (e) {
                e.preventDefault();
                ExportToXls();
            });
        });

        function ExportToXls() {
            $('#<%=gvListaPreview.ClientID%>').tableExport({
                fileName: "<%=TxDescrizione.Value%>_preview",
                type: 'xlsx'
            });
        }

        function ExportToPdf() {
            $('#<%=gvListaPreview.ClientID%>').tableExport({
                fileName: "<%=TxDescrizione.Value%>_preview",
                type: 'pdf'
            });
        }

        //function ExportToPdf2() {
        //    var pdf = new jsPDF('p', 'pt', 'letter');
        //    source = $('#divPreview')[0];
        //    // we support special element handlers. Register them with jQuery-style 
        //    // ID selector for either ID or node name. ("#iAmID", "div", "span" etc.)
        //    // There is no support for any other type of selectors 
        //    // (class, of compound) at this time.
        //    specialElementHandlers = {
        //        // element with id of "bypass" - jQuery style selector
        //        '#bypassme': function (element, renderer) {
        //            // true = "handled elsewhere, bypass text extraction"
        //            return true
        //        }
        //    };
        //    margins = {
        //        top: 80,
        //        bottom: 60,
        //        left: 40,
        //        width: 522
        //    };
        //    // all coords and widths are in jsPDF instance's declared units
        //    // 'inches' in this case
        //    pdf.fromHTML(
        //        source, // HTML string or DOM elem ref.
        //        margins.left, // x coord
        //        margins.top, { // y coord
        //            'width': margins.width, // max width of content on PDF
        //            'elementHandlers': specialElementHandlers
        //        },

        //        function (dispose) {
        //            // dispose: object with X, Y of the last line add to the PDF 
        //            //          this allow the insertion of new lines after html
        //            pdf.save('Test.pdf');
        //        }, margins);
        //}

        function visualizzaPreview(id) {
            if (document.getElementById(id).value == "") {
                alert("Attenzione!Occorre prima inserire un importo di test!");
                return false;
            }
        }
    </script>
    <asp:ScriptManager ID="ScriptManager1" runat="server"></asp:ScriptManager>
    <asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
        <asp:View runat="server" ID="risultatoView">
            <fieldset>
                <div>
                    <asp:GridView runat="server" ID="gvLista" AutoGenerateColumns="False" DataKeyNames="Tiporateizzazione" OnSelectedIndexChanged="gvLista_SelectedIndexChanged" DefaultSortDirection="Ascending" DefaultSortExpression="" OnRowDataBound="gvLista_RowDataBound" OnRowDeleting="gvLista_RowDeleting">
                        <AlternatingRowStyle CssClass="RigaAlternata" />
                        <RowStyle CssClass="Riga" />
                        <HeaderStyle CssClass="IntestazioneTabella" />
                        <EmptyDataRowStyle CssClass="NessunRecordTrovato" />
                        <EmptyDataTemplate>
                            <asp:Label ID="Label6" runat="server"></asp:Label>
                        </EmptyDataTemplate>
                        <Columns>
                            <asp:ButtonField CommandName="Select" DataTextField="Tiporateizzazione" HeaderText="Codice" />
                            <asp:BoundField DataField="Descrizione" HeaderText="Descrizione" />
                            <asp:TemplateField>
                                <ItemTemplate>
                                    <asp:ImageButton ID="cmdElimina" runat="server" CommandName="Delete" ImageUrl="~/images/Delete.gif" />
                                </ItemTemplate>
                            </asp:TemplateField>
                        </Columns>
                    </asp:GridView>
                </div>
                <div class="Bottoni">
                    <init:SigeproButton runat="server" ID="cmdNuovo" Text="Nuovo" IdRisorsa="NUOVO" OnClick="cmdNuovo_Click" />
                    <init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
                </div>
            </fieldset>
        </asp:View>
        <asp:View runat="server" ID="dettaglioView">
            <asp:UpdatePanel ID="UpdatePanel1" runat="server">
                <ContentTemplate>
                    <fieldset>
                        <div>
                            <asp:Label runat="server" ID="lblTipoRateizzazioneEt" Text="Codice" AssociatedControlID="lblTipoRateizzazione"></asp:Label>
                            <asp:Label runat="server" ID="lblTipoRateizzazione" Text=""></asp:Label>
                        </div>
                        <init:LabeledTextBox ID="TxDescrizione" runat="server" Descrizione="*Descrizione" Item-Columns="100" />
                        <init:LabeledIntTextBox ID="TxNumerorate" runat="server" Descrizione="*Numero rate" Item-Columns="5" OnValueChanged="TxNumerorate_ValueChanged" Item-AutoPostBack="true" />
                        <init:LabeledTextBox ID="TxRipartizioneRate" runat="server" Descrizione="*Ripartizione rate (%)" Item-Columns="20" HelpControl="divRipartizioneRate" />
                        <init:LabeledDropDownList ID="ddlDataInizioRateizzazione" runat="server" Descrizione="Data inizio rateizzazione" Item-DataValueField="Key" Item-DataTextField="Value" OnValueChanged="ddlDataInizioRateizzazione_ValueChanged" Item-AutoPostBack="true" />
                        <init:LabeledDropDownList ID="ddlMovimento" runat="server" Descrizione="Movimento" Item-DataValueField="Tipomovimento" Item-DataTextField="Movimento" />


                        <init:LabeledTextBox ID="TxFrequenzaRate" runat="server" Descrizione="*Frequenza rate (gg)" Item-Columns="20" HelpControl="divGiorniScadenze" />
                        <init:LabeledDropDownList ID="ddlFrequenzaRateAmmortamentoFrancese" runat="server" Descrizione="*Frequenza rate" Item-DataValueField="Key" Item-DataTextField="Value" OnValueChanged="ddlFrequenzaRateAmmortamentoFrancese_ValueChanged" Item-AutoPostBack="false" />


                        <init:LabeledDropDownList ID="ddlComportamentoScadenza" runat="server" Descrizione="Scadenza rate" HelpControl="divComportamentoScadenza" Item-DataValueField="Id" Item-DataTextField="Descrizione" />
                        <init:LabeledTextBox ID="TxInteressiRate" runat="server" Descrizione="Interessi (%)" HelpControl="divPercInteressi" Item-Columns="5" />
                        <init:LabeledTextBox ID="txtSpeseRateizzazione" runat="server" Descrizione="Spese Rateizzazione" HelpControl="divSpeseRateizzazione" />
                        <init:LabeledCheckBox ID="chkInteressiLegali" runat="server" HelpControl="divInteressiLegali" Descrizione="Interessi legali" OnValueChanged="chkInteressiLegali_ValueChanged" Item-AutoPostBack="true" />
                        <asp:DropDownList ID="ddlInteressiLegali" runat="server">
                            <asp:ListItem Value="0">senza anatocismo</asp:ListItem>
                        </asp:DropDownList>
                        <asp:ImageButton ID="imgBtnDetail" runat="server" ImageUrl="~/Images/detail.gif"
                            OnClick="imgBtnDetail_Click" AlternateText="Visualizza gli interessi legali" />
                        <asp:ImageButton ID="imgBtnCancel" runat="server" ImageUrl="~/Images/cancel.gif"
                            OnClick="ingBtnCancel_Click" AlternateText="Chiude gli interessi legali" />



                        <init:LabeledDropDownList ID="ddlTipoRateizzazioni" runat="server" Descrizione="Tipo Rateizzazione" Item-DataValueField="Key" Item-DataTextField="Value" OnValueChanged="ddlTipoRateizzazioni_ValueChanged" Item-AutoPostBack="true" />


                        <asp:GridView runat="server" ID="gvListaInteressiLegali" AutoGenerateColumns="False">
                            <AlternatingRowStyle CssClass="RigaAlternata" />
                            <RowStyle CssClass="Riga" />
                            <HeaderStyle CssClass="IntestazioneTabella" />
                            <EmptyDataRowStyle CssClass="NessunRecordTrovato" />
                            <Columns>
                                <asp:BoundField DataField="id" HeaderText="Id" />
                                <asp:BoundField DataField="datainizio" HeaderText="Data inizio">
                                    <ItemStyle HorizontalAlign="Right" />
                                </asp:BoundField>
                                <asp:BoundField DataField="datafine" HeaderText="Data fine">
                                    <ItemStyle HorizontalAlign="Right" />
                                </asp:BoundField>
                                <asp:BoundField DataField="tassopercentuale" HeaderText="Tasso percentuale">
                                    <ItemStyle HorizontalAlign="Right" />
                                </asp:BoundField>
                                <asp:BoundField DataField="disposizionenormativa" HeaderText="Disposizione normativa" />
                            </Columns>
                        </asp:GridView>
                    </fieldset>
                </ContentTemplate>
            </asp:UpdatePanel>
            <fieldset>
                <div class="Bottoni">
                    <init:SigeproButton runat="server" ID="cmdSalva" Text="Salva" IdRisorsa="OK" OnClick="cmdSalva_Click" />
                    <init:SigeproButton runat="server" ID="cmdEliminaTipoRateizzazione" Text="Elimina" IdRisorsa="ELIMINA" OnClick="cmdEliminaTipoRateizzazione_Click" />
                    <init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click" />
                </div>
            </fieldset>
            <br />
            <asp:Panel runat="server" ID="PanelPreview">
                <fieldset>
                    <init:LabeledLabel ID="LabeledLabel1" Value="Preview rateizzazione" runat="server" />
                    <init:LabeledDoubleTextBox ID="TxImportoTest" runat="server" Descrizione="Importo da rateizzare" Item-Columns="5" />
                    <init:LabeledDateTextBox ID="TxDataInizioIntLegaliTest" runat="server" Descrizione="Data di inizio" Item-Columns="10" />
                    <init:LabeledDateTextBox ID="TxDataInizioTest" runat="server" Descrizione="Data di inizio rateizzazione" Item-Columns="10" />
                    <div class="Bottoni">
                        <init:SigeproButton runat="server" ID="cmdPreview" Text="Preview" IdRisorsa="PREVIEW" OnClick="cmdPreview_Click" />
                    </div>
                </fieldset>

                <asp:Panel runat="server" ID="PanelGridPreview" Visible="false">
                    <div style="width:762px; text-align:right;margin-bottom:2px;padding-right:20px">
                        <i class="fa fa-file-pdf-o" id="iconaPdf" style="font-size: 24px; cursor: pointer; color: darkred;"></i>
                        <i class="fa fa-file-excel-o" id="iconaExcel" style="font-size: 24px; cursor: pointer; color: green;"></i>
                    </div>
                    <div id="divPreview">
                        <asp:GridView runat="server" ID="gvListaPreview" AutoGenerateColumns="False" Width="764px">
                            <AlternatingRowStyle CssClass="RigaAlternata" />
                            <RowStyle CssClass="Riga" />
                            <HeaderStyle CssClass="IntestazioneTabella" />
                            <EmptyDataRowStyle CssClass="NessunRecordTrovato" />
                            <Columns>
                                <asp:BoundField DataField="numerorata" HeaderText="Numero rata" />
                                <asp:BoundField DataField="datascadenza" HeaderText="Data pagamento" DataFormatString="{0:d}"></asp:BoundField>
                                <asp:BoundField DataField="QuotaCapitale" HeaderText="Quota capitale"></asp:BoundField>
                                <asp:BoundField DataField="ImportoInteressi" HeaderText="Quota interessi"></asp:BoundField>
                                <asp:BoundField DataField="prezzo" HeaderText="Rata"></asp:BoundField>
                                <asp:BoundField DataField="CapitaleResiduo" HeaderText="Residuo"></asp:BoundField>
                            </Columns>
                        </asp:GridView>
                    </div>
                </asp:Panel>
            </asp:Panel>
        </asp:View>
    </asp:MultiView>
    <init:HelpDiv ID="divGiorniScadenze" runat="server">
    Specificare i giorni da aggiungere alla "<b>Data inizio rateizzazione</b>" per determinare la scadenza di una rata.<br />
    Gli intervalli dei giorni vanno specificati utilizzando "<b>;</b>" come separatore<br />
    <i>Esempio</i><br/>
    &nbsp;&nbsp;<b>20;365</b><br />
    <b>ATTENZIONE: </b>Se il numero degli intervalli è minore del numero delle rate, verrà utilizzato l'ultimo intervallo specificato per impostare la scadenza delle restanti rate.
    </init:HelpDiv>

    <init:HelpDiv ID="divRipartizioneRate" runat="server">
    Specificare le percentuali di ripartizione delle rate separate da "<b>;</b>"<br />
    <i>Esempio</i><br/>
    &nbsp;&nbsp;<b>40;20;20;20</b><br />
    <b>ATTENZIONE: </b>Se il numero delle percentuali è minore del numero delle rate, verrà utilizzata l'ultima percentuale specificata per impostare le restanti rate.
    </init:HelpDiv>

    <init:HelpDiv ID="divComportamentoScadenza" runat="server">
    <b>Valori ammessi:</b>
    <ol>
    <li><i>Fine mese: </i>Dopo aver calcolato la scadenza della rata, la sposta alla fine del mese.</li>
    <li><i>15 del mese: </i>Dopo aver calcolato la scadenza della rata, la imposta al prossimo 15 del mese.</li>
    <li><i>Fine mese (esclusa la 1° rata): </i>Sposta la scadenza della rata alla fine del mese.<b>Non modifica la scadenza della prima rata.</b></li>
    <li><i>15 del mese (esclusa la 1° rata): </i>Sposta la scadenza della rata al prossimo 15 del mese.<b>Non modifica la scadenza della prima rata.</b></li>
    <li><i>Lascia inalterato: </i>Lascia inalterata la scadenza della rata calcolata.</li>
    <li><i>15 del mese successivo: </i>Dopo aver calcolato la scadenza della rata, la imposta al 15 del mese successivo.</li>
    <li><i>15 del mese successivo (esclusa la 1° rata): </i>Sposta la scadenza della rata al 15 del mese successivo.<b>Non modifica la scadenza della prima rata.</b></li>
    </ol>
    </init:HelpDiv>

    <init:HelpDiv ID="divPercInteressi" runat="server">
    Se gli interessi variano da scadenza a scadenza allora vanno specificati utilizzando "<b>;</b>" come separatore<br />
    <i>Esempio</i> RATE=3 INTERESSI=2;4;5 significa che la prima rata ha interesse 2%, la seconda 4% e la terza 5%<br/>
    <b>ATTENZIONE: </b>Se il numero degli interessi è minore del numero delle rate, verrà utilizzato l'ultimo interesse specificato 
    </init:HelpDiv>
    <init:HelpDiv ID="divSpeseRateizzazione" runat="server">
    L'importo sarà aggiunto nella prima rata
    </init:HelpDiv>


    <init:HelpDiv ID="divInteressiLegali" runat="server">
    Se il flag interessi legali viene selezionato allora gli interessi vengono calcolati sulla base della tabella interessi legali, e l’utente deve obbligatoriamente imputare le date del periodo sul quale calcolare gli interessi<br/>
    La data di inizio rappresenta la data da cui deve partire il calcolo degli interessi legali<br/>
	<b>ATTENZIONE: </b> la data di inizio rateizzazione rappresenta la data finale
    </init:HelpDiv>
</asp:Content>

