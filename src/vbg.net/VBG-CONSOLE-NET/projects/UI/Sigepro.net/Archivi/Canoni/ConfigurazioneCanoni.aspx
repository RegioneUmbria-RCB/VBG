<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="True" CodeBehind="ConfigurazioneCanoni.aspx.cs" Inherits="Sigepro.net.Archivi.Canoni.ConfigurazioneCanoni" Title="Configurazione del calcolo dei canoni" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
<script language="javascript" type="text/javascript">
function AggiungiCoefficiente( token, software, anno )
{
    
    var url = "AreeDettagliOMI.aspx?Token=" + token + "&Software=" + software + "&Anno=" + anno;
    var features = "";
    
    var w = window.open( url, "Zone", features );
}
</script>
<asp:ScriptManager ID="ScriptManager1" runat="server"></asp:ScriptManager>
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
		<asp:View runat="server" ID="listaView">
			<fieldset>
			    <div>
                    <init:GridViewEx AllowSorting="true" runat="server" ID="gvLista" AutoGenerateColumns="False" DataKeyNames="Anno" DataSourceID="CanoniConfigDataSource" DefaultSortDirection="Ascending" DefaultSortExpression="Anno" OnSelectedIndexChanged="gvLista_SelectedIndexChanged" DatabindOnFirstLoad="True">
				        <AlternatingRowStyle CssClass="RigaAlternata" />
				        <RowStyle CssClass="Riga" />
				        <HeaderStyle CssClass="IntestazioneTabella" />
				        <EmptyDataRowStyle CssClass="NessunRecordTrovato"/>
				        <Columns>
					        <asp:ButtonField CommandName="Select" DataTextField="Anno" HeaderText="Anno" Text="Button" SortExpression="Anno" />
				        </Columns>
				        <EmptyDataTemplate>
					        <asp:Label ID="Label6" runat="server">Non è stato trovato nessun record corrispondente ai criteri di ricerca.</asp:Label>
				        </EmptyDataTemplate>
			        </init:GridViewEx>
                    <asp:ObjectDataSource ID="CanoniConfigDataSource" runat="server" SortParameterName="sortexpression" OldValuesParameterFormatString="original_{0}" SelectMethod="Find" TypeName="Init.SIGePro.Manager.CanoniConfigurazioneMgr">
                        <SelectParameters>
                            <asp:QueryStringParameter Name="token" QueryStringField="token" Type="String" />
                            <asp:QueryStringParameter Name="software" QueryStringField="software" Type="String" />
                            <asp:Parameter Name="sortExpression" Type="String" />
                        </SelectParameters>
                    </asp:ObjectDataSource>
			    </div>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdNuovo"  Text="Nuovo" IdRisorsa="NUOVO" OnClick="cmdNuovo_Click"/>
					<init:SigeproButton runat="server" ID="cmdCloseList" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdCloseList_Click"/>
				</div>
			</fieldset>
		</asp:View>		
		<asp:View runat="server" ID="dettaglioView">
			<fieldset>
		        <init:LabeledIntTextBox runat="server" ID="txAnno" Descrizione="*Anno di riferimento" Item-Columns="5" Item-MaxLength="4" />
			    <div>
			        <asp:Label runat="server" ID="Label2" Text="*Causale onere totale" AssociatedControlID="rplCausaleTotale"/>
			        <init:RicerchePlusCtrl runat="server" ID="rplCausaleTotale" ColonneCodice="5" ColonneDescrizione="50" CompletionInterval="300" CompletionListCssClass="RicerchePlusLista" CompletionListHighlightedItemCssClass="RicerchePlusElementoSelezionatoLista" CompletionListItemCssClass="RicerchePlusElementoLista" CompletionSetCount="10" LoadingIcon="~/Images/ajaxload.gif" MaxLengthCodice="10" MaxLengthDescrizione="150" MinimumPrefixLength="1" ServicePath="" AutoSelect="True" ServiceMethod="GetCompletionListCausaliOneri" DataClassType="Init.SIGePro.Data.TipiCausaliOneri" DescriptionPropertyNames="CoDescrizione" RicercaSoftwareTT="True" TargetPropertyName="CoId"/>
			        <init:HelpIcon ID="hiCausaleTotale" runat="server" HelpControl="hdCausaleTotale" />
			        <init:HelpDiv runat="server" ID="hdCausaleTotale">E' la causale onere utilizzata per registrare il totale del calcolo tra gli oneri dell'istanza</init:HelpDiv>
			    </div>
			    <init:LabeledDoubleTextBox runat="server" ID="txCanoneMinimo" Descrizione="Canone minimo" Item-Columns="10" HelpControl="hdCanoneMinimo" />
			    <init:HelpDiv runat="server" ID="hdCanoneMinimo">Se il calcolo del canone è inferiore a questo valore, verrà visualizzato un messaggio di avviso all'operatore</init:HelpDiv>
             	<legend>Parametri OMI</legend>
             	<init:LabeledDoubleTextBox runat="server" ID="txValoreBaseOMI" Descrizione="Coefficiente base OMI" Item-Columns="5" HelpControl="hdValoreBaseOMI" />
			    <init:HelpDiv runat="server" ID="hdValoreBaseOMI">Media valori fornita dall'Istituto Mobiliare Italiano.<br/>Serve per il calcolo del canone delle pertinenze</init:HelpDiv>
			    <init:LabeledDropDownList runat="server" ID="ddlTipoRipartizioneOmi" Descrizione="Tipo di ripartizione OMI" HelpControl="hdTipoRipartizioneOmi" />
			    <init:HelpDiv runat="server" ID="hdTipoRipartizioneOmi" Width="600px">
	                <table border="0" cellpadding="0" cellspacing="0">
	                    <tr>
	                        <td colspan="2"><b>Se viene selezionato “Superficie complessiva”</b>, allora alle pertinenze si applica il canone complessivo <b>C</b> così determinato:</td>
	                    </tr>
	                    <tr>
	                        <td width="20px"></td>
	                        <td>C = Valore base OMI * Coefficiente OMI * <b>Superficie complessiva</b></td>
	                    </tr>
	                    <tr>
	                        <td colspan="2">Gli importi così ottenuti, sono successivamente abbattuti in relazione alla classe di riduzione OMI stabilita dalla superficie totale.</td>
	                    </tr>
	                    <tr>
	                        <td colspan="2"><b>Se invece viene selezionato “Superficie parziale”</b>, allora alle pertinenze si applica il canone complessivo <b>C</b> determinato sommando tutti i canoni parziali <b>CP</b> così ottenuti:</td>
                        </tr>
	                    <tr>
	                        <td></td>
	                        <td>CP = ( Valore base OMI * Coefficiente OMI * <b>Superficie parziale</b>) - Percentuale di riduzione OMI</td>
	                    </tr>
	                </table>
			    </init:HelpDiv>
			    <div>
			        <asp:Label runat="server" ID="lbl1" Text="Canone OMI" AssociatedControlID="rptConfigAree"></asp:Label>
                    <asp:Repeater runat="server" ID="rptConfigAree" DataSourceID="CanoniConfigAreeDataSource" OnItemCreated="rptConfigAree_ItemCreated">
						<HeaderTemplate>
						    <table style="width:400px">
						        <tr>
						            <th style="width:300px">Zona</th>
						            <th style="width:50px">Canone </th>
						            <th style="width:50px">&nbsp;</th>
						        </tr>
						</HeaderTemplate>
						<AlternatingItemTemplate>
					            <tr class="RigaAlternata">
					                <td><asp:Label runat="server" ID="label1a" Text='<%# Bind( "Aree" ) %>' /></td>
					                <td><asp:Label runat="server" ID="label3b" Text='<%# Bind( "CoefficienteOMI" ) %>' /></td>
					                <td><asp:ImageButton runat="server" id="imgCancellaZona" CommandArgument='<%# Bind( "Id" ) %>' ImageUrl="~/Images/cancel.gif" ToolTip="Cancella la riga corrente" OnClick="imgCancellaZona_Click" /></td>
					            </tr>
						</AlternatingItemTemplate>
						<ItemTemplate>
					            <tr class="Riga">
					                <td><asp:Label runat="server" ID="label1b" Text='<%# Bind( "Aree" ) %>' /></td>
					                <td><asp:Label runat="server" ID="label3b" Text='<%# Bind( "CoefficienteOMI" ) %>' /></td>
					                <td><asp:ImageButton runat="server" id="imgCancellaZona" CommandArgument='<%# Bind( "Id" ) %>' ImageUrl="~/Images/cancel.gif" ToolTip="Cancella la riga corrente" OnClick="imgCancellaZona_Click" /></td>
 					            </tr>
						</ItemTemplate>
						<FooterTemplate>
                            </table>
						</FooterTemplate>
					</asp:Repeater>
					<init:HelpDiv runat="server" ID="hdCoefficienteOMI">Coefficiente fornito dall'Istituto Mobiliare Italiano.<br/>Serve per il calcolo del canone delle pertinenze</init:HelpDiv>
			    </div>
                <asp:ObjectDataSource ID="CanoniConfigAreeDataSource" runat="server" SortParameterName="sortExpression" OldValuesParameterFormatString="original_{0}" SelectMethod="Find" TypeName="Init.SIGePro.Manager.CanoniConfigAreeMgr">
                    <SelectParameters>
                        <asp:QueryStringParameter Name="token" QueryStringField="token" Type="String" />
                        <asp:ControlParameter ControlID="txAnno" Name="anno" PropertyName="Value" Type="Int32" />
                        <asp:Parameter Name="sortExpression" Type="String" DefaultValue="Aree" />
                    </SelectParameters>
                </asp:ObjectDataSource>
			    <div class="Bottoni">
			        <init:SigeproButton runat="server" ID="cmdAggiungiCoefficiente" Text="Nuovo coefficiente OMI" IdRisorsa="NUOVOCOEFFICIENTEOMI"/>
			    </div>
                <legend>Addizionale regionale</legend>
		        <init:LabeledDoubleTextBox runat="server" ID="txPercAddizRegionale" Descrizione="Percentuale addizionale regionale" Item-Columns="5" HelpControl="hdPercAddizRegionale" />
		        <init:HelpDiv runat="server" ID="hdPercAddizRegionale">E' la percentuale da imputare al totale del calcolo come "Addizionale Regionale"</init:HelpDiv>
			    <div>
			        <asp:Label runat="server" ID="lblMercato" Text="Causale onere addizionale regionale" AssociatedControlID="rplCausaleAddRegionale"/>
			        <init:RicerchePlusCtrl runat="server" ID="rplCausaleAddRegionale" ColonneCodice="5" ColonneDescrizione="50" CompletionInterval="300" CompletionListCssClass="RicerchePlusLista" CompletionListHighlightedItemCssClass="RicerchePlusElementoSelezionatoLista" CompletionListItemCssClass="RicerchePlusElementoLista" CompletionSetCount="10" LoadingIcon="~/Images/ajaxload.gif" MaxLengthCodice="10" MaxLengthDescrizione="150" MinimumPrefixLength="1" ServicePath="" AutoSelect="True" ServiceMethod="GetCompletionListCausaliOneri" DataClassType="Init.SIGePro.Data.TipiCausaliOneri" DescriptionPropertyNames="CoDescrizione" RicercaSoftwareTT="True" TargetPropertyName="CoId"/>
                    <init:HelpIcon ID="hiCausaleAddRegionale" runat="server" HelpControl="hdCausaleAddRegionale" />
			        <init:HelpDiv runat="server" ID="hdCausaleAddRegionale">E' la causale onere utilizzata per registrare l'addizionale regionale tra gli oneri dell'istanza</init:HelpDiv>
			    </div>
			    <legend>Addizionale comunale</legend>
			    <init:LabeledDoubleTextBox runat="server" ID="txPercAddizComunale" Descrizione="Percentuale addizionale comunale" Item-Columns="5" HelpControl="hdPercAddizComunale" />
                <init:HelpDiv runat="server" ID="hdPercAddizComunale">E' la percentuale da imputare al totale del calcolo come "Addizionale Comunale"</init:HelpDiv>
			    <div>
			        <asp:Label runat="server" ID="Label1c" Text="Causale onere Addizionale Comunale" AssociatedControlID="rplCausaleAddComunale"/>
			        <init:RicerchePlusCtrl runat="server" ID="rplCausaleAddComunale" ColonneCodice="5" ColonneDescrizione="50" CompletionInterval="300" CompletionListCssClass="RicerchePlusLista" CompletionListHighlightedItemCssClass="RicerchePlusElementoSelezionatoLista" CompletionListItemCssClass="RicerchePlusElementoLista" CompletionSetCount="10" LoadingIcon="~/Images/ajaxload.gif" MaxLengthCodice="10" MaxLengthDescrizione="150" MinimumPrefixLength="1" ServicePath="" AutoSelect="True" ServiceMethod="GetCompletionListCausaliOneri" DataClassType="Init.SIGePro.Data.TipiCausaliOneri" DescriptionPropertyNames="CoDescrizione" RicercaSoftwareTT="True" TargetPropertyName="CoId"/>
                    <init:HelpIcon ID="hiCausaleAddComunale" runat="server" HelpControl="hdCausaleAddComunale" />
			        <init:HelpDiv runat="server" ID="hdCausaleAddComunale">E' la causale onere utilizzata per registrare l'addizionale comunale tra gli oneri dell'istanza</init:HelpDiv>
			    </div>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdSalva" Text="Salva" IdRisorsa="OK" OnClick="cmdSalva_Click"/>
					<init:SigeproButton runat="server" ID="cmdCopiaConfigurazione" Text="Copia configurazione" IdRisorsa="COPIACONFIGURAZIONE" OnClick="cmdCopiaConfigurazione_Click"/>
					<init:SigeproButton runat="server" ID="cmdConfigurazioneCanoni" Text="Importi unitari superfici" IdRisorsa="CONFIGURAZIONETIPISUPERFICI" OnClick="cmdConfigurazioneCanoni_Click" />
					<init:SigeproButton runat="server" ID="cmdConfigurazionePertinenze" Text="Riduzioni Classi OMI" IdRisorsa="CONFIGURAZIONEPERTINENZE" OnClick="cmdConfigurazionePertinenze_Click" />
					<init:SigeproButton runat="server" ID="cmdElimina" Text="Elimina" IdRisorsa="ELIMINA" OnClick="cmdElimina_Click"/>
					<init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click"/>
				</div>
            </fieldset>
			<asp:Panel runat="server" id="pnlConfigurazione" Visible="false">
			    <fieldset>
				    <init:LabeledDropDownList runat="server" ID="ddlConfigurazione" Descrizione="*Configurazione da copiare" HelpControl="hdConfigurazioneCopiata" />
				    <init:HelpDiv runat="server" ID="hdConfigurazioneCopiata">E' la configurazione dalla quale verranno recuperate le informazioni</init:HelpDiv>
				    <init:LabeledIntTextBox runat="server" ID="txNuovoAnno" Descrizione="*Anno della nuova configurazione" Item-Columns="5" HelpControl="hdNuovaConfigurazione" />
				    <init:HelpDiv runat="server" ID="hdNuovaConfigurazione">E' l'anno di riferimento per la nuova configurazione</init:HelpDiv>
				    <div class="Bottoni">
					    <init:SigeproButton runat="server" ID="cmdOk" Text="Ok" IdRisorsa="OK" OnClick="cmdOk_Click"/>
					    <init:SigeproButton runat="server" ID="cmdAnnulla" Text="Annulla" IdRisorsa="ANNULLA" OnClick="cmdAnnulla_Click"/>
				    </div>
				</fieldset>
			</asp:Panel>
            <asp:Panel runat="server" id="pnlConfigTipiSuperfici" Visible="false">
			    <fieldset>
                    <asp:GridView runat="server" ID="gvCoefficienti" OnRowDataBound="gvCoefficienti_RowDataBound">
				        <AlternatingRowStyle CssClass="RigaAlternata" />
				        <RowStyle CssClass="Riga" />
				        <HeaderStyle CssClass="IntestazioneTabella" />
				        <EmptyDataRowStyle CssClass="NessunRecordTrovato"/>
			        </asp:GridView>
			        <div class="Bottoni">
                        <init:SigeproButton runat="server" ID="cmdSalvaCoefficienti" Text="Salva" IdRisorsa="SALVA" OnClick="cmdSalvaCoefficienti_Click" />
                    </div>
                </fieldset>
            </asp:Panel>
	        <asp:Panel runat="server" ID="pnlConfigPertinenze" Visible="false">
	            <fieldset>
		            <legend>Configurazione delle pertinenze tramite metodo OMI</legend>
			        <init:LabeledDropDownList runat="server" ID="ddlPertinenze" Descrizione="Tipo superficie" OnValueChanged="ddlPertinenze_ValueChanged"/>
		            <asp:GridView runat="server" ID="gvPertinenze" OnRowDataBound="gvPertinenze_RowDataBound">
				        <AlternatingRowStyle CssClass="RigaAlternata" />
				        <RowStyle CssClass="Riga" />
				        <HeaderStyle CssClass="IntestazioneTabella" />
				        <EmptyDataRowStyle CssClass="NessunRecordTrovato"/>
                        <Columns>
                        </Columns>
			        </asp:GridView>
			        <div class="Bottoni">
                        <init:SigeproButton runat="server" ID="cmdSalvaPertinenze" Text="Salva" IdRisorsa="SALVA" OnClick="cmdSalvaPertinenze_Click"/>
                    </div>
                </fieldset>
            </asp:Panel>
		</asp:View>
	</asp:MultiView>
</asp:Content>