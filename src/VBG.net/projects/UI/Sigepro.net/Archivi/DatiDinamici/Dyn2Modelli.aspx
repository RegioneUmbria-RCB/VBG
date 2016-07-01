<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="True" Inherits="Archivi_DatiDinamici_Dyn2Modelli" Title="Schede" Codebehind="Dyn2Modelli.aspx.cs" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>


<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>



<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">
	<asp:ScriptManager ID="ScriptManager1" runat="server">
	</asp:ScriptManager>

	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
		<asp:View runat="server" ID="ricercaView">
			<script type="text/javascript" src='<%=ResolveClientUrl("~/js/konami.js") %>'></script>
			<script type="text/javascript" >
				$(function () {
					$(document).konami(function () {
						$('#sound_el').html(
							"<embed src='<%=ResolveClientUrl("~/Utilita/jingle.mp3") %>' hidden=true autostart=true loop=false>");
					});
				});
			</script>


			<fieldset>
				<init:LabeledTextBox ID="txtSrcId" runat="server" Descrizione="Id" Item-Columns="8" />
				<init:LabeledTextBox ID="txtSrcCodice" runat="server" Descrizione="Codice" Item-Columns="50" />
				<init:LabeledTextBox ID="txtSrcDescrizione" runat="server" Descrizione="Descrizione" Item-Columns="80" />
				<init:LabeledDropDownList ID="ddlSrcContesto" runat="server" Descrizione="Contesto" />

				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdCerca"  Text="Cerca" IdRisorsa="CERCA" OnClick="cmdCerca_Click" />
					<init:SigeproButton runat="server" ID="cmdNuovo"  Text="Nuovo" IdRisorsa="NUOVO" OnClick="cmdNuovo_Click" />
					<asp:Button runat="server" ID="cmdNuovoComeCopia"  Text="Nuovo come copia" OnClick="cmdNuovoComeCopia_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
				</div>
			</fieldset>
			<div id="sound_el" style="display:none"></div>
		</asp:View>
		
		<asp:View runat="server" ID="listaView">
			
			<fieldset>
			<div>
			    <init:GridViewEx AllowSorting="false" 
								 runat="server" 
								 ID="gvLista" 
								 AutoGenerateColumns="False" 
								 OnSelectedIndexChanged="gvLista_SelectedIndexChanged" 
								 DatabindOnFirstLoad="False" 
								 DataSourceID="ObjectDataSource1" 
								 DefaultSortDirection="Ascending" 
								 DataKeyNames="Id" 
								 DefaultSortExpression="Id" >
				    <AlternatingRowStyle CssClass="RigaAlternata" />
				    <RowStyle CssClass="Riga" />
				    <HeaderStyle CssClass="IntestazioneTabella" />
				    <EmptyDataRowStyle CssClass="NessunRecordTrovato"/>
				    
				    <EmptyDataTemplate>
					    <asp:Label ID="Label6" runat="server">Non è stato trovato nessun record corrispondente ai criteri di ricerca.</asp:Label>
				    </EmptyDataTemplate>
					<Columns>
						<asp:ButtonField DataTextField="Id" HeaderText="Codice" SortExpression="Id"  CommandName="Select"/>						
						<asp:TemplateField HeaderText="Contesto" SortExpression="FkD2bcId">
							<itemtemplate>
								<asp:Label runat="server" Text='<%# TraduciContesto( DataBinder.Eval(Container.DataItem,"FkD2bcId")) %>' id="Label1"></asp:Label>
							</itemtemplate>
						</asp:TemplateField>
						<asp:BoundField DataField="CodiceScheda" HeaderText="Codice" SortExpression="CodiceScheda" />
						<asp:BoundField DataField="Descrizione" HeaderText="Descrizione" SortExpression="Descrizione" />
					</Columns>
			    </init:GridViewEx>
				<asp:ObjectDataSource ID="ObjectDataSource1" runat="server" OldValuesParameterFormatString="original_{0}" SelectMethod="Find" SortParameterName="sortExpression"
					TypeName="Init.SIGePro.Manager.Dyn2ModelliTMgr">
					<SelectParameters>
						<asp:QueryStringParameter Name="token" 
												  QueryStringField="Token" 
												  Type="String" />
						<asp:QueryStringParameter Name="software" 
												  QueryStringField="Software" 
												  Type="String" />
						<asp:ControlParameter ControlID="txtSrcId" 
											  Name="id" 
											  PropertyName="Value" 
											  Type="String" />
						<asp:ControlParameter ControlID="txtSrcCodice" 
											  Name="codice" 
											  PropertyName="Value" 
											  Type="String" />
						<asp:ControlParameter ControlID="txtSrcDescrizione" 
											  Name="descrizione" 
											  PropertyName="Value" 
											  Type="String" />
						<asp:ControlParameter ControlID="ddlSrcContesto" 
											  Name="contesto" 
											  PropertyName="Value" 
											  Type="String" />
						<asp:Parameter Name="sortExpression" Type="String" />
					</SelectParameters>
				</asp:ObjectDataSource>
			    
			</div>
				<div class="Bottoni">
                    <init:SigeproButton runat="server" ID="ImageButton1" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiLista_Click" />					
				</div>
			</fieldset>
		</asp:View>
		
		<asp:View runat="server" ID="dettaglioView">
			<fieldset>
				<init:LabeledLabel runat="server" ID="lblId" Descrizione="Id" />
				<init:LabeledTextBox runat="server" ID="txtCodiceScheda" Descrizione="Codice" Item-MaxLength="50" Item-Columns="55" />
				<init:LabeledTextBox ID="txtDescrizione" runat="server" Descrizione="*Descrizione" Item-Columns="100" Item-MaxLength="150"/>
				<init:LabeledDropDownList ID="ddlContesto" runat="server" Descrizione="Contesto" />
				<init:LabeledCheckBox ID="chkMultiplo" runat="server" Descrizione="Supporta molteplicità" HelpControl="hlpMultiplo" />
				<init:HelpDiv runat="server" ID="hlpMultiplo">
					<b>Funzionalità obsoleta. Utilizzare la molteplicità a livello di campi e i blocchi multipli.</b>
					Se il flag è impostato la scheda sarà di tipo multiplo (potranno essere create numerose istanze della stessa scheda).<br />
					La molteplicità è supportata solo a livello di backoffice ed è ignorata nelle schede visualizzate nel frontoffice.
				</init:HelpDiv>

				<init:LabeledCheckBox ID="chkStoricizza" runat="server" Descrizione="Storicizza modello" />
				<init:LabeledCheckBox ID="chkReadOnly" runat="server" Descrizione="Sola lettura" HelpControl="hlpReadOnly" />
				<init:HelpDiv runat="server" ID="hlpReadOnly">
					Se il flag è impostato il campo è in sola lettura nell'interfaccia web. Non sarà quindi possibile ne'salvare ne' eliminare la scheda nell'istanza
				</init:HelpDiv>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdSalva" Text="Salva" IdRisorsa="OK" OnClick="cmdSalva_Click" />
					<init:SigeproButton runat="server" ID="cmdFormule" Text="Formule" IdRisorsa="FORMULE" OnClick="cmdFormule_Click" />
					<init:SigeproButton runat="server" ID="cmdGestioneCampi" Text="Gestione campi" IdRisorsa="GESTIONECAMPI" OnClick="cmdGestioneCampi_Click" />					
					<init:SigeproButton runat="server" ID="cmdElimina" Text="Elimina" IdRisorsa="ELIMINA" OnClick="cmdElimina_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click" />
				</div>
			</fieldset>
		</asp:View>
		
		<asp:View runat="server" ID="copyFromView">
			<fieldset>
				<div>&nbsp;</div>
				<div style="width:100%;text-align:center">
					<b>
						Attenzione, la copia duplicherà la struttura del modello ma non duplicherà i campi a cui il modello fa riferimento che resteranno condivisi tra il modello di origine e il modello di destinazione
					</b>
				</div>
				<div>&nbsp;</div>
				<div>
					<!-- cerca modelli -->
					<asp:Label runat="server" ID="Label23" AssociatedControlID="rplModelloDinamico" Text="Modello da copiare" />
					<init:RicerchePlusCtrl ID="rplModelloDinamico" runat="server" 
																   ColonneCodice="4" 
																   ColonneDescrizione="50" 
																   CompletionInterval="300" 
																   CompletionListCssClass="RicerchePlusLista" 
																   CompletionListHighlightedItemCssClass="RicerchePlusElementoSelezionatoLista" 
																   CompletionListItemCssClass="RicerchePlusElementoLista" 
																   CompletionSetCount="10" 
																   DataClassType="Init.SIGePro.Data.Dyn2ModelliT" 
																   DescriptionPropertyNames="Descrizione" 
																   LoadingIcon="~/Images/ajaxload.gif" 
																   MaxLengthCodice="10" 
																   MaxLengthDescrizione="150" 
																   MinimumPrefixLength="1" 
																   ServiceMethod="GetCompletionList" 
																   TargetPropertyName="Id" 
																   ServicePath="" 
																   AutoSelect="True" 
																   BehaviorID="autoComlpeteCampoDinamico"/>
				</div>
				<div>
					<!-- checkbox copia formule -->
					<init:LabeledCheckBox runat="server" ID="chkCopiaFormule" Descrizione="Copia anche le formule" />
				</div>
				<div class="Bottoni">
					<asp:Button runat="server" ID="cmdConfermaCopia" Text="Procedi" OnClick="cmdConfermaCopia_Click" />
					<asp:Button runat="server" ID="cmdAnnulla" Text="Annulla" OnClick="cmdChiudiLista_Click"/>
				</div>
			</fieldset>
		</asp:View>

	</asp:MultiView>
</asp:Content>

