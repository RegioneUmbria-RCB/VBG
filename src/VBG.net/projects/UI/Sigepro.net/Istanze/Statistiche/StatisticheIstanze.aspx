<%@ Page Language="C#" AutoEventWireup="True" MasterPageFile="~/SigeproNetMaster.master" Codebehind="StatisticheIstanze.aspx.cs" Inherits="Sigepro.net.Istanze.Statistiche.StatisticheIstanze"
	Title="Statistiche istanze" %>

<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<%@ Register Src="../../CustomControls/FiltriStatisticheDatiDinamici.ascx" TagName="FiltriStatisticheDatiDinamici" TagPrefix="uc1" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>



<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
	<script type="text/javascript">
		function ApriPaginaIstanze(codice)
		{
			var url='<%=BuildVbg2Path( "/istanze/view.htm" ) %>?codice=' + codice;
			var feats = "scrollbars=1,resizable=1,width=1000,height=590";
			window.open( url , "istanze",feats );
		}
	</script>
	<asp:ScriptManager ID="ScriptManager1" runat="server">
	</asp:ScriptManager>
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
		<asp:View runat="server" ID="ricercaView">
			<fieldset>
		<legend>Dati dell'istanza</legend>
		<div class="Intervallo">
			<div class="Da">
				<asp:Label runat="server" ID="label7" Text="Dalla data" AssociatedControlID="txtDataDa" />
				<init:DateTextBox runat="server" ID="txtDataDa" />
			</div>
			<div class="A">
				<asp:Label CssClass="StessaRiga" runat="server" ID="label8" Text="alla data:" AssociatedControlID="txtDataA" />
				<init:DateTextBox runat="server" ID="txtDataA" />
			</div>
		</div>
		<div>
			<asp:Label runat="server" ID="label1" Text="Operatore" AssociatedControlID="rpOperatore" />
			<init:RicerchePlusCtrl ID="rpOperatore" runat="server" AutoSelect="True" ColonneCodice="10" ColonneDescrizione="60" CompletionInterval="300" CompletionListCssClass="RicerchePlusLista"
				CompletionListHighlightedItemCssClass="RicerchePlusElementoSelezionatoLista" CompletionListItemCssClass="RicerchePlusElementoLista" CompletionSetCount="1"
				DataClassType="Init.SIGePro.Data.Responsabili" DescriptionPropertyNames="RESPONSABILE" LoadingIcon="~/Images/ajaxload.gif" MaxLengthCodice="10" MaxLengthDescrizione="100"
				MinimumPrefixLength="1" ServiceMethod="GetCompletionList" ServicePath="~/WebServices/WsSiGePro/RicerchePlus.asmx" TargetPropertyName="CODICERESPONSABILE" />
		</div>
		<div>
			<asp:Label runat="server" ID="label2" Text="Richiedente" AssociatedControlID="rpRichiedente" />
			<init:RicerchePlusCtrl ID="rpRichiedente" runat="server" ColonneCodice="10" ColonneDescrizione="60" MaxLengthCodice="10" MaxLengthDescrizione="100" AutoSelect="True"
				CompletionInterval="300" CompletionListCssClass="RicerchePlusLista" CompletionListHighlightedItemCssClass="RicerchePlusElementoSelezionatoLista" CompletionListItemCssClass="RicerchePlusElementoLista"
				CompletionSetCount="1" DataClassType="Init.SIGePro.Data.Anagrafe" DescriptionPropertyNames="NOMINATIVO,PARTITAIVA,CODICEFISCALE,NOME" LoadingIcon="~/Images/ajaxload.gif"
				MinimumPrefixLength="1" ServiceMethod="GetCompletionList" ServicePath="~/WebServices/WsSiGePro/RicerchePlus.asmx" TargetPropertyName="CODICEANAGRAFE" />
		</div>
		<div>
			<asp:Label runat="server" ID="label3" Text="Tecnico" AssociatedControlID="rpTecnico" />
			<init:RicerchePlusCtrl ID="rpTecnico" runat="server" ColonneCodice="10" ColonneDescrizione="60" MaxLengthCodice="10" MaxLengthDescrizione="100" AutoSelect="True"
				CompletionInterval="300" CompletionSetCount="1" DataClassType="Init.SIGePro.Data.Anagrafe" DescriptionPropertyNames="NOMINATIVO,CODICEFISCALE,NOME"
				MinimumPrefixLength="3" ServiceMethod="GetCompletionListTecnico" ServicePath="" TargetPropertyName="CODICEANAGRAFE" CompletionListCssClass="RicerchePlusLista"
				CompletionListHighlightedItemCssClass="RicerchePlusElementoSelezionatoLista" CompletionListItemCssClass="RicerchePlusElementoLista" LoadingIcon="~/Images/ajaxload.gif" />
		</div>
		<div>
			<asp:Label runat="server" ID="label4" Text="Tipologia intervento" AssociatedControlID="rpTipologiaIntervento" />
			<init:RicerchePopup runat="server" ID="rpTipologiaIntervento" ColonneCodice="10" ColonneDescrizione="60" PopupWidth="800" PopupUrl="~/Istanze/Statistiche/PopupAlberoProcedimenti.aspx"></init:RicerchePopup>
		</div>
		<div>
			<asp:Label runat="server" ID="label5" Text="Tipo procedura" AssociatedControlID="rpTipologiaProcedura" />
			<asp:UpdatePanel ID="UpdatePanel1" runat="server" UpdateMode="Conditional">
				<ContentTemplate>
					<init:RicerchePlusCtrl ID="rpTipologiaProcedura" runat="server" ColonneCodice="10" ColonneDescrizione="60" MaxLengthCodice="10" MaxLengthDescrizione="100"
						AutoSelect="True" CompletionInterval="300" CompletionSetCount="1" DataClassType="Init.SIGePro.Data.TipiProcedure" DescriptionPropertyNames="PROCEDURA"
						MinimumPrefixLength="1" ServiceMethod="GetCompletionList" ServicePath="~/WebServices/WsSIGePro/RicerchePlus.asmx" TargetPropertyName="CODICEPROCEDURA"
						RicercaSoftwareTT="True" />
				</ContentTemplate>
				<Triggers>
					<asp:AsyncPostBackTrigger ControlID="rpTipologiaIntervento" EventName="ValueChanged" />
				</Triggers>
			</asp:UpdatePanel>
		</div>
		<div>
			<asp:Label runat="server" ID="label6" Text="Zonizzazione" AssociatedControlID="rpZonizzazione" />
			<init:RicerchePlusCtrl ID="rpZonizzazione" runat="server" ColonneCodice="10" ColonneDescrizione="60" MaxLengthCodice="10" MaxLengthDescrizione="100" AutoSelect="True"
				CompletionInterval="300" CompletionSetCount="1" DataClassType="Init.SIGePro.Data.Aree" DescriptionPropertyNames="DENOMINAZIONE" MinimumPrefixLength="1"
				ServiceMethod="GetCompletionListAree" ServicePath="" TargetPropertyName="CODICEAREA" RicercaSoftwareTT="False" />
		</div>
		<div>
			<asp:Label runat="server" ID="label9" Text="Tipo informazione" AssociatedControlID="rpTipoInformazione" />
			<init:RicerchePlusCtrl ID="rpTipoInformazione" runat="server" ColonneCodice="10" ColonneDescrizione="60" MaxLengthCodice="10" MaxLengthDescrizione="100"
				AutoSelect="True" CompletionInterval="300" CompletionSetCount="1" DataClassType="Init.SIGePro.Data.Settori" DescriptionPropertyNames="SETTORE" MinimumPrefixLength="1"
				OnValueChanged="rpTipoInformazione_ValueChanged" RicercaSoftwareTT="False" ServiceMethod="GetCompletionList" ServicePath="~/WebServices/WsSiGePro/RicerchePlus.asmx"
				TargetPropertyName="CODICESETTORE" AutoPostBack="True" />
		</div>
		<div>
			<asp:Label runat="server" ID="label10" Text="Dettaglio informazione" AssociatedControlID="rpDettaglioInformazione" />
			<asp:UpdatePanel ID="UpdatePanel2" runat="server" UpdateMode="Conditional">
				<ContentTemplate>
					<init:RicerchePlusCtrl ID="rpDettaglioInformazione" runat="server" ColonneCodice="10" ColonneDescrizione="60" MaxLengthCodice="10" MaxLengthDescrizione="100"
						AutoSelect="True" CompletionInterval="300" CompletionSetCount="1" DataClassType="Init.SIGePro.Data.Attivita" DescriptionPropertyNames="ISTAT" MinimumPrefixLength="1"
						RicercaSoftwareTT="False" ServiceMethod="GetCompletionListDettaglioInformazioni" ServicePath="" TargetPropertyName="CodiceIstat" />
				</ContentTemplate>
				<Triggers>
					<asp:AsyncPostBackTrigger ControlID="rpTipoInformazione" EventName="ValueChanged" />
				</Triggers>
			</asp:UpdatePanel>
		</div>
		
		<init:LabeledDropDownList ID="ddlTipologiaRegistro" runat="server" Item-DataTextField="TR_DESCRIZIONE" Item-DataValueField="TR_ID" Descrizione="Registro Provv.Autorizzativi" />
		
		<init:LabeledDropDownList ID="ddlStatiIstanza" runat="server" Item-DataTextField="Stato" Item-DataValueField="Codicestato" Descrizione="Stato" />
		
		<legend>Dati dinamici</legend>
		<uc1:FiltriStatisticheDatiDinamici ID="ctrlFiltriDatiDinamici" Contesto="IS" runat="server" />
		<div class="Bottoni">
			<init:SigeproButton ID="cmdCerca" Text="Cerca" IdRisorsa="CERCA" runat="server" OnClick="cmdCerca_Click" />
			<init:SigeproButton ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" runat="server" OnClick="cmdChiudi_Click" />
		</div>
	</fieldset>
		</asp:View>
		
		<asp:View runat="server" ID="listaView">
			<div class="Informazioni">
				<asp:Label runat="server" ID="lblTitoloNumeroIstanze" CssClass="Etichetta" Text="Numero istanze trovate:" />
				<asp:Label runat="server" ID="lblnumeroIstanze" CssClass="Valore"/>
			</div>
			<asp:GridView ID="gvRisultato" runat="server" AutoGenerateColumns="False" >
		<AlternatingRowStyle CssClass="RigaAlternata" />
		<RowStyle CssClass="Riga" />
		<HeaderStyle CssClass="IntestazioneTabella" />
		<EmptyDataRowStyle CssClass="NessunRecordTrovato"/>
		<Columns>
			<asp:TemplateField HeaderText="Numero istanza">
				<ItemTemplate>
					<a href='javascript:ApriPaginaIstanze(<%# DataBinder.Eval(Container.DataItem , "CodiceIstanza")%> )'><%# DataBinder.Eval(Container.DataItem , "NUMEROISTANZA") %></a>
					
				</ItemTemplate>
			</asp:TemplateField>
			<asp:BoundField DataField="DATA" HeaderText="Data" DataFormatString="{0:dd/MM/yyyy}" HtmlEncode="False"/>
			<asp:TemplateField HeaderText="Richiedente">
				<ItemTemplate>
					<asp:Label ID="Label1" runat="server" Text='<%# GeneraStringaRichiedente( DataBinder.Eval( Container , "DataItem" ) ) %>'></asp:Label>
				</ItemTemplate>
			</asp:TemplateField>
			<asp:TemplateField HeaderText="Localizzazione">
				<ItemTemplate>
					<asp:Label ID="Label2" runat="server" Text='<%# Bind("StradarioPrimario") %>'></asp:Label>
				</ItemTemplate>
			</asp:TemplateField>
			<asp:TemplateField HeaderText="Altri indirizzi">
				<ItemTemplate>
					<asp:Label ID="Label3" runat="server" Text='<%# TraduciAltriIndirizzi( DataBinder.Eval( Container.DataItem , "AltriIndirizzi") ) %>'></asp:Label>
				</ItemTemplate>
			</asp:TemplateField>
			<asp:TemplateField HeaderText="Intervento">
				<ItemTemplate>
					<asp:Label ID="Label4" runat="server" Text='<%# Bind("Intervento") %>'></asp:Label>
				</ItemTemplate>
			</asp:TemplateField>
			<asp:TemplateField HeaderText="Pos. archivio">
				<ItemTemplate>
					<asp:Label ID="Label5" runat="server" Text='<%# Bind("PosizioneArchivio") %>'></asp:Label>
				</ItemTemplate>
			</asp:TemplateField>
			<asp:TemplateField HeaderText="Stato">
				<ItemTemplate>
					<asp:Label ID="Label6" runat="server" Text='<%# Bind("Stato") %>'></asp:Label>
				</ItemTemplate>
			</asp:TemplateField>
		</Columns>
	</asp:GridView>
	<fieldset>
	<div class="Bottoni">
	<init:SigeproButton ID="cmdChiudiLista" Text="Chiudi" IdRisorsa="CHIUDI" runat="server" OnClick="cmdChiudiLista_Click" />
	</div>
	</fieldset>
		</asp:View></asp:MultiView>

	

</asp:Content>
