<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" ValidateRequest="false" AutoEventWireup="True" Inherits="Archivi_DatiDinamici_Dyn2ModelliCampi"
    Title="Campi della scheda" CodeBehind="Dyn2ModelliCampi.aspx.cs" %>

<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>


<%@ Register TagPrefix="init" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils" %>

<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.UI" Assembly="SIGePro.WebControls" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.Ajax" Assembly="SIGePro.WebControls" %>

<asp:Content runat="server" ContentPlaceHolderID="headPagina">

    <style>
        select, input {
            margin: auto;
        }


    </style>

    <script type="Text/javascript">
        function MostraDettaglioCampo(idCampo) {
            var url = "Dyn2Campi.aspx?Popup=true&Token=<%=Token %>&Software=<%=Software%>&IdCampo=" + idCampo;
            var winName = "dettagliCampo";
            var feats = "menubar=no,status=no,width=1010,height=600,scrollbars=yes";
            window.open(url, winName, feats);
        }

        function MostraAnteprima(idModello) {
            var url = "Dyn2ModelliPreview.aspx?Token=<%=Token %>&IdModello=" + idModello;
            var winName = "anteprima";
            var feats = "menubar=no,status=no,width=1000,height=800,scrollbars=yes";
            window.open(url, winName, feats);
        }
		<%if (!String.IsNullOrEmpty(ScrollTo)) {%>

        $(function onLoad() {
            function scrollToElement(aid){
                var el = $("a."+ aid);
                $('html,body').animate({scrollTop: el.offset().top},'slow');
            }

            scrollToElement('<%=ScrollTo%>');
        })

        <%}%>
    </script>
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <asp:ScriptManager ID="ScriptManager1" runat="server">
    </asp:ScriptManager>



    <asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
        <asp:View runat="server" ID="listaView">
            <fieldset>
                <div>
                    <init:GridViewEx runat="server" ID="gvLista" AutoGenerateColumns="False" OnSelectedIndexChanged="gvLista_SelectedIndexChanged" DatabindOnFirstLoad="True"
                        DataSourceID="ObjectDataSource1"
                        DefaultSortDirection="Ascending"
                        DefaultSortExpression=""
                        DataKeyNames="Id" OnDataBound="gvLista_DataBound"
                        OnRowDataBound="gvLista_RowDataBound">
                        <AlternatingRowStyle CssClass="RigaAlternata" />
                        <RowStyle CssClass="Riga" />
                        <HeaderStyle CssClass="IntestazioneTabella" />
                        <EmptyDataRowStyle CssClass="NessunRecordTrovato" />
                        <EmptyDataTemplate>
                            <asp:Label ID="Label6" runat="server">Non è stato trovato nessun record corrispondente ai criteri di ricerca.</asp:Label>
                        </EmptyDataTemplate>
                        <Columns>
                            <asp:TemplateField HeaderText="Id">
                                <ItemTemplate>

                                    <asp:LinkButton runat="server" CommandName="Select" CommandArgument='<%#Eval("Id") %>' Text='<%#Eval("Id") %>' CssClass='<%#Eval("Id") %>' />
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:BoundField DataField="Posverticale" HeaderText="Riga" SortExpression="Posverticale" />
                            <asp:BoundField DataField="Posorizzontale" HeaderText="Colonna" SortExpression="Posorizzontale" />
                            <asp:TemplateField HeaderText="Campo dinamico" SortExpression="CampoDinamico">
                                <ItemTemplate>
                                    <a href='javascript:MostraDettaglioCampo(<%# DataBinder.Eval( Container.DataItem , "FkD2cId") %>)'>
                                        <asp:Label runat="server" Text='<%# Bind("CampoDinamico") %>' ID="Label1"></asp:Label>
                                    </a>
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:BoundField DataField="CampoTestuale" HeaderText="Testo" SortExpression="CampoTestuale" />
                            <asp:TemplateField HeaderText="Tipo riga" SortExpression="FlgMultiplo">
                                <ItemTemplate>

                                    <%--									<asp:Checkbox runat="server" id="chkMultiplo" 
													AutoPostback="true" 
													Checked='<%# ((int?)DataBinder.Eval( Container.DataItem , "FlgMultiplo")).GetValueOrDefault(0) == 1 %>' 
													OnCheckedChanged="RigaMultiplaCheckedChanged" />--%>
                                    <asp:DropDownList runat="server" ID="ddlMultiplo"
                                        AutoPostBack="true"
                                        OnSelectedIndexChanged="ddlMultiplo_SelectedIndexChanged">
                                        <asp:ListItem Text="Riga singola" Value="0" />
                                        <asp:ListItem Text="Riga multipla" Value="1" />
                                        <asp:ListItem Text="Multipla non modificabile" Value="2" />
                                    </asp:DropDownList>

                                    <asp:HiddenField runat="server" ID="hidRiga" Value='<%# DataBinder.Eval(Container.DataItem,"Posverticale") %>' />
                                </ItemTemplate>
                            </asp:TemplateField>

                            <asp:TemplateField SortExpression="FlgSpezzaTabella">
                                <HeaderTemplate>
                                    <div title="Gli elementi successivi ad un elemento selezionato verranno inseriti in un nuovo raggruppamento. 
                                        Utilizzare questa funzionalità per organizzare lo spazio occupato dai controlli">
                                        Termina tabella 
                                    </div>


                                </HeaderTemplate>
                                <ItemTemplate>

                                    <asp:CheckBox runat="server" ID="chkSpezzaTabella"
                                        AutoPostBack="true"
                                        Checked='<%# ((int?)DataBinder.Eval( Container.DataItem , "FlgSpezzaTabella")).GetValueOrDefault(0) == 1 %>'
                                        OnCheckedChanged="chkSpezzaTabella_CheckedChanged" />

                                </ItemTemplate>
                            </asp:TemplateField>
                        </Columns>
                    </init:GridViewEx>
                    <asp:ObjectDataSource ID="ObjectDataSource1" runat="server" OldValuesParameterFormatString="original_{0}" SelectMethod="Find" TypeName="Init.SIGePro.Manager.Dyn2ModelliDMgr">
                        <SelectParameters>
                            <asp:QueryStringParameter Name="token" QueryStringField="Token" Type="String" />
                            <asp:QueryStringParameter Name="idModello" QueryStringField="IdModello" Type="Int32" />
                        </SelectParameters>
                    </asp:ObjectDataSource>
                </div>
                <div class="Bottoni">
                    <init:SigeproButton runat="server" ID="cmdNuovo" Text="Nuovo" IdRisorsa="NUOVO" OnClick="cmdNuovo_Click" />
                    <init:SigeproButton runat="server" ID="cmdPreview" Text="Anteprima" IdRisorsa="ANTEPRIMAMODELLO" />
                    <init:SigeproButton runat="server" ID="cmdRicalcolaNumerazione" Text="Ricalcola numerazione" IdRisorsa="RICALCOLANUMERAZIONE" OnClick="cmdRicalcolaNumerazione_Click" />
                    <asp:Button runat="server" ID="cmdVerificaCampi" Text="Verifica HTML campi" OnClick="cmdVerificaCampi_Click" />
                    <init:SigeproButton runat="server" ID="ImageButton1" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiLista_Click" />
                </div>
            </fieldset>
        </asp:View>
        <asp:View runat="server" ID="dettaglioView">
            <script type="text/javascript">
                function impostaCampo(datiCampo) {
                    var el = $find("autoComlpeteCampoDinamico");

                    el.get_element().value = datiCampo.codice;
                    $get(el.get_DescriptionControlID()).value = datiCampo.descrizione;
                }


                function CreaCampo() {

                    var url = '<%= ResolveClientUrl( "~/Archivi/DatiDinamici/Dyn2Campi.aspx?Popup=true&Token=" ) + Token + "&Software=" + Software + "&PopupCreaNuovo=true"%>';
                    var width = 900;
                    var height = 700;
                    var left = parseInt((screen.availWidth / 2) - (width / 2));
                    var top = parseInt((screen.availHeight / 2) - (height / 2));
                    var feats = "resizable,width=" + width + ",height=" + height + ",left=" + left + ",top=" + top + "," +
                        "screenX=" + left + ",screenY=" + top + ",scrollbars=yes";

                    var w = window.open(url, 'nuovoControllo', feats, "POS");

                    return false;

					/*
					var url = '<%= ResolveClientUrl( "~/Archivi/DatiDinamici/Dyn2Campi.aspx?Popup=true&Token=" ) + Token + "&Software=" + Software + "&PopupCreaNuovo=true"%>';
                    var feats = "dialogWidth: 800px;dialogHeight: 400px;scroll:1;resizable :1";

                    var rVal = showModalDialog(url, '', feats);

                    if (!rVal)
                        return false;

                    var el = $find("autoComlpeteCampoDinamico");

                    el.get_element().value = rVal.codice;
                    $get(el.get_DescriptionControlID()).value = rVal.descrizione;

                    return false;
					*/
                }

                function ConfermaEliminazione() {
                    return confirm("Eliminare il record selezionato?");
                }

                function MostraDettaglioCampo(idCampo) {
                    var url = "Dyn2Campi.aspx?Popup=true&Token=<%=Token %>&Software=<%=Software%>&IdCampo=" + idCampo;
                    var winName = "dettagliCampo";
                    var feats = "menubar=no,status=no,width=1010,height=600,scrollbars=yes";
                    window.open(url, winName, feats);

                    return false;
                }
            </script>

            <fieldset>
                <init:LabeledLabel runat="server" ID="lblId" Descrizione="Codice" />
                <init:LabeledIntTextBox ID="txtPosVerticale" HelpControl="hdPosVerticale" runat="server" Descrizione="*Riga" Item-Columns="4" Item-MaxLength="3" />
                <init:HelpDiv runat="server" ID="hdPosVerticale">
					Se lasciato vuoto verrà utilizzata la prima riga disponibile
                </init:HelpDiv>
                <init:LabeledIntTextBox ID="txtPosOrizzontale" HelpControl="hdPosOrizzontale" runat="server" Descrizione="*Colonna" Item-Columns="2" Item-MaxLength="1" />
                <init:HelpDiv runat="server" ID="hdPosOrizzontale">
					Se lasciato vuoto verrà utilizzata la prima colonna disponibile della riga indicata
                </init:HelpDiv>
                <init:LabeledDropDownList ID="ddlTipoCampo" Descrizione="Tipo campo" runat="server" OnValueChanged="ddlTipoCampo_ValueChanged" Item-AutoPostBack="true" />
                <asp:Panel runat="server" ID="pnlCampoTesto">
                    <init:LabeledDropDownList ID="ddlBaseTipoTesto" runat="server" Descrizione="Tipo testo" Item-DataTextField="Tipotesto" Item-DataValueField="Id" />
                    <init:LabeledTextBox ID="txtTestoEsteso" runat="server" Item-TextMode="MultiLine" Descrizione="Testo" Item-Columns="100" Item-Rows="10" />
                </asp:Panel>
                <asp:Panel runat="server" ID="pnlCampoDinamico">
                    <asp:Label runat="server" ID="Label23" AssociatedControlID="rplCampoDinamico" Text="Campo dinamico" />
                    <init:RicerchePlusCtrl ID="rplCampoDinamico" runat="server" ColonneCodice="4" ColonneDescrizione="50" CompletionInterval="300" CompletionListCssClass="RicerchePlusLista"
                        CompletionListHighlightedItemCssClass="RicerchePlusElementoSelezionatoLista" CompletionListItemCssClass="RicerchePlusElementoLista" CompletionSetCount="10"
                        DataClassType="Init.SIGePro.Data.Dyn2Campi" DescriptionPropertyNames="Nomecampo" LoadingIcon="~/Images/ajaxload.gif" MaxLengthCodice="10" MaxLengthDescrizione="150"
                        MinimumPrefixLength="1" ServiceMethod="GetCompletionList" TargetPropertyName="Id" ServicePath="" AutoSelect="True"
                        BehaviorID="autoComlpeteCampoDinamico" />
                    <init:SigeproButton runat="server" ID="cmdCreaCampo" OnClientClick="return CreaCampo();" IdRisorsa="NUOVO" />
                </asp:Panel>
                <%--				<asp:Panel runat="server" ID="pnlSolaLettura" Visible="false">
					<div>
						<asp:Label runat="server" ID="Label200" AssociatedControlID="lblTipoCampoRo" Text="Tipo campo" />
						<asp:Label runat="server" ID="lblTipoCampoRo" />
					</div>
					
					<div>
						<asp:Label runat="server" ID="Label3" AssociatedControlID="lblCampoDinamicoRo" Text="Campo dinamico" />
						<asp:Label runat="server"  ID="lblCampoDinamicoRo" />
					</div>
				</asp:Panel>--%>
				&nbsp;&nbsp;
				<div class="Bottoni">
                    <init:SigeproButton runat="server" ID="cmdSalva" Text="Salva" IdRisorsa="SALVA" OnClick="cmdSalva_Click" />
                    <asp:Button runat="server" ID="cmdModificaDettagli" Text="Dettagli Campo" />
                    <init:SigeproButton runat="server" ID="cmdElimina" Text="Elimina" IdRisorsa="ELIMINA" OnClick="cmdElimina_Click" OnClientClick="return ConfermaEliminazione();" />
                    <init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click" />
                </div>
            </fieldset>
        </asp:View>
    </asp:MultiView>
</asp:Content>
