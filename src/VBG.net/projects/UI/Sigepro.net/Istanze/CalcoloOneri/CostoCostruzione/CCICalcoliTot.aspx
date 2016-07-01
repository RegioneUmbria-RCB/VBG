<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="True" Inherits="Istanze_CalcoloOneri_CostoCostruzione_CCICalcoliTot"
	Title="Calcolo del contributo relativo al costo di costruzione" Codebehind="CCICalcoliTot.aspx.cs" %>

<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>


<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>





<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
<script type="text/javascript">
	function ModificaRiduzioni( token , tcontributo)
	{
		var url		= "CCModificaRiduzioni.aspx?Token=" + token + "&tcontributo=" + tcontributo ;
		var feats	= "dialogHeight:500px;dialogWidth:700px;";
		
		var w = window.showModalDialog( url , "" , feats );
		
		return w == undefined ? false : w;
	}
	
	function ModificaNote( token , tcontributo)
	{
		var url		= "CCModificaNote.aspx?Token=" + token + "&tcontributo=" + tcontributo ;
		var feats	= "dialogHeight:200px;dialogWidth:400px;scroll:no;status:no";
		
		var w = window.showModalDialog( url , "" , feats );
		
		return w == undefined ? false : w;
	}
</script>


	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
		<asp:View runat="server" ID="risultato">
			<asp:GridView runat="server" AutoGenerateColumns="false" DataKeyNames="Id" ID="gvLista" 
							OnSelectedIndexChanged="gvLista_SelectedIndexChanged" 
							OnRowDataBound="gvLista_RowDataBound"
							OnRowDeleting="gvLista_RowDeleting">
				<AlternatingRowStyle CssClass="RigaAlternata" />
				<RowStyle CssClass="Riga" />
				<HeaderStyle CssClass="IntestazioneTabella" />
				<EmptyDataRowStyle CssClass="NessunRecordTrovato" />
				<Columns>
					<asp:ButtonField CommandName="Select" DataTextField="Id" HeaderText="Codice" Text="Button" />
					<asp:BoundField DataField="Descrizione" HeaderText="Descrizione" />
					<asp:TemplateField>
						<ItemTemplate>
							<asp:ImageButton ID="cmdElimina" runat="server" CommandName="Delete" ImageUrl="~/images/Delete.gif" />
						</ItemTemplate>
					</asp:TemplateField>
				</Columns>
				<EmptyDataTemplate>
					<asp:Label ID="Label6" runat="server"></asp:Label>
				</EmptyDataTemplate>
			</asp:GridView>
			<fieldset>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdNuovo"  Text="Nuovo" IdRisorsa="NUOVO" OnClick="cmdNuovo_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
				</div>
			</fieldset>
		</asp:View>
		<asp:View runat="server" ID="inserimento">
			<asp:ScriptManager ID="ScriptManager1" runat="server">
			</asp:ScriptManager>
			<fieldset>
				<asp:UpdatePanel ID="UpdatePanel1" runat="server">
					<ContentTemplate>
						<div>
							<asp:Label runat="server" ID="label2" Text="*Data" AssociatedControlID="txtData"></asp:Label>
							<init:DateTextBox runat="server" ID="txtData" AutoPostBack="True" OnTextChanged="txtData_TextChanged"></init:DateTextBox>
						</div>
						<div>
							<asp:Label runat="server" ID="lblFkCCVCID" Text="*Listino coefficienti" AssociatedControlID="ddlFkCCVCID"></asp:Label>
							<asp:DropDownList runat="server" ID="ddlFkCCVCID" DataTextField="Descrizione" DataValueField="Id" />
						</div>
						<div>
							<asp:Label runat="server" ID="Label1" Text="*Tipo intervento" AssociatedControlID="ddlFfOCCBTIID"></asp:Label>
							<asp:DropDownList runat="server" ID="ddlFfOCCBTIID" DataTextField="Intervento" DataValueField="Id" AutoPostBack="True" OnSelectedIndexChanged="RicalcolaTipiCalcolo" />
						</div>
						<div>
							<asp:Label runat="server" ID="Label3" Text="*Destinazione" AssociatedControlID="ddlFkOCCBDEID"></asp:Label>
							<asp:DropDownList runat="server" ID="ddlFkOCCBDEID" DataTextField="Destinazione" DataValueField="Id" AutoPostBack="True" OnSelectedIndexChanged="RicalcolaTipiCalcolo" />
						</div>
						<div>
							<asp:Label runat="server" ID="Label4" Text="*Tipo calcolo" AssociatedControlID="ddlFkBCCTCID"></asp:Label>
							<asp:DropDownList runat="server" ID="ddlFkBCCTCID" DataTextField="Tipocalcolo" DataValueField="Id" />
						</div>
						<div>
							<asp:Label runat="server" ID="Label5" Text="*Descrizione" AssociatedControlID="txtDescrizione"></asp:Label>
							<asp:TextBox runat="server" ID="txtDescrizione" Columns="100" MaxLength="200" />
						</div>
						<div class="DescrizioneCampo">
							Se lasciato vuoto verrà valorizzato con <b>Destinazione - Tipo Intervento</b>
						</div>
					</ContentTemplate>
				</asp:UpdatePanel>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdInserisci"  Text="Inserisci" IdRisorsa="INSERISCI" OnClick="cmdInserisci_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudiInserimento" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiInserimento_Click" />
				</div>
			</fieldset>
		</asp:View>
		<asp:View ID="dettaglio" runat="server">
			<fieldset>
				<div>
					<asp:Label runat="server" ID="label13" Text="Codice" AssociatedControlID="lblId"></asp:Label>
					<asp:Label runat="server" ID="lblId" Text=""></asp:Label>
				</div>
				<div>
					<asp:Label runat="server" ID="label7" Text="Data" AssociatedControlID="lblData"></asp:Label>
					<asp:Label runat="server" ID="lblData" Text=""></asp:Label>
				</div>
				<div>
					<asp:Label runat="server" ID="Label8" Text="Listino coefficienti" AssociatedControlID="lblListino"></asp:Label>
					<asp:Label runat="server" ID="lblListino" Text=""></asp:Label>
				</div>
				<div>
					<asp:Label runat="server" ID="Label9" Text="Tipo intervento" AssociatedControlID="lblTipoIntervento"></asp:Label>
					<asp:Label runat="server" ID="lblTipoIntervento" Text=""></asp:Label>
				</div>
				<div>
					<asp:Label runat="server" ID="Label10" Text="Destinazione" AssociatedControlID="lblDestinazione"></asp:Label>
					<asp:Label runat="server" ID="lblDestinazione" Text=""></asp:Label>
				</div>
				<div>
					<asp:Label runat="server" ID="Label11" Text="Tipo calcolo" AssociatedControlID="lblTipoCalcolo"></asp:Label>
					<asp:Label runat="server" ID="lblTipoCalcolo" Text=""></asp:Label>
				</div>
				<div>
					<asp:Label runat="server" ID="Label12" Text="*Descrizione" AssociatedControlID="txtEditDescrizione"></asp:Label>
					<asp:TextBox runat="server" ID="txtEditDescrizione" Columns="100" MaxLength="200" />
				</div>
				<div class="Bottoni">
					<init:SigeproButton runat="server" id="cmdAggiornaDescrizione" Text="Aggiorna descrizione" IdRisorsa="AGGIORNADESCRIZIONE" OnClick="cmdAggiornaDescrizione_Click" />
					<init:SigeproButton runat="server" ID="cmdEliminaCalcolo" Text="Elimina calcolo" IdRisorsa="ELIMINACALCOLO" OnClick="cmdEliminaCalcolo_Click" />
				</div>
			</fieldset>
			<% if (MostraTabellaContributo)
	  { %>
			<table>
				<colgroup width="20%" />
				<colgroup width="15%" align="center" />
				<colgroup width="20%" align="center"/>
				<colgroup width="10%" align="right" />
				<colgroup width="15%" align="center" />
				<colgroup width="20%" align="right" />
				<tr>
					<th>&nbsp;</th>
					<th>Costo di costruzione</th>
					<th>Percentuale contributo</th>
					<th>Quota Contributo</th>
					<th>Variazione</th>
					<th>Quota Contributo</th>
				</tr>
				<% if (MostraRigaContributoProgetto)
		 { %>
				<tr>
					<th>Stato di progetto</th>
					<td>€&nbsp;
						<init:DoubleTextBox runat="server" ID="txtCostoEdificioProgetto" Columns="10"  />
						<asp:ImageButton runat="server" ID="cmdDettagliCostoEdificioProgetto" ImageUrl="~/images/detail.gif" AlternateText="Visualizza il calcolo del costo di costruzione"
							OnClick="ApriDettagli" />
					</td>
					<td>
						<init:DoubleTextBox runat="server" ID="txtCoefficenteProgetto"  Columns="4" />%
						<asp:ImageButton runat="server" ID="cmdDettagliContributoProgetto" ImageUrl="~/images/detail.gif" AlternateText="Visualizza il calcolo della percentuale del contributo"
							OnClick="ApriDettagliContributo" />
					</td>
					<td>€&nbsp;<asp:Label runat="server" ID="lblQuotaProgetto">asd</asp:Label>
					</td>
					<td>€&nbsp;
						<init:DoubleTextBox runat="server" ID="txtVariazioneProgetto" Columns="6" MaxLength="7"  />
						<asp:ImageButton runat="server" ID="lnkEditVariazioneProgetto" ImageUrl="~/Images/edit.gif" OnClick="lnkEditVariazione_Click"  ></asp:ImageButton>
						<init:HelpIcon runat="server" ID="hlpiVariazioneProgetto" HelpControl="hlpVariazioneProgetto" ></init:HelpIcon>
						<init:HelpDiv runat="server" ID="hlpVariazioneProgetto" >test</init:HelpDiv>
					</td>
					<td>€&nbsp;
						<init:DoubleTextBox runat="server" ID="txtQuotaContributoProgetto" ReadOnly="True"  Columns="10" />
					</td>
				</tr>
				<% }//if (MostraRigaContributoProgetto)%>
				<% if (MostraRigaContributoAttuale)
		 { %>
				<tr>
					<th>
						Stato attuale</th>
					<td>€&nbsp;
						<init:DoubleTextBox runat="server" ID="txtCostoEdificioAttuale" Columns="10" />
						<asp:ImageButton runat="server" ID="cmdDettagliCostoEdificioAttuale" ImageUrl="~/images/detail.gif" AlternateText="Visualizza il calcolo del costo di costruzione"
							OnClick="ApriDettagli" />
					</td>
					<td>
						<init:DoubleTextBox runat="server" ID="txtCoefficenteAttuale" Columns="4" />%
						<asp:ImageButton runat="server" ID="cmdDettagliContributoAttuale" ImageUrl="~/images/detail.gif" AlternateText="Visualizza il calcolo della percentuale del contributo"
							OnClick="ApriDettagliContributo" />
					</td>
										<td>€&nbsp;<asp:Label runat="server" ID="lblQuotaAttuale">asd</asp:Label>
					</td>
										<td>€&nbsp;
						<init:DoubleTextBox runat="server" ID="txtVariazioneAttuale" Columns="6" MaxLength="7" />
						<asp:ImageButton runat="server" ID="lnkEditVariazioneAttuale" ImageUrl="~/Images/edit.gif"  OnClick="lnkEditVariazione_Click"></asp:ImageButton>
						<init:HelpIcon runat="server" ID="hlpiVariazioneAttuale" HelpControl="hlpVariazioneAttuale" ></init:HelpIcon>
						<init:HelpDiv runat="server" ID="hlpVariazioneAttuale" >test</init:HelpDiv>
					</td>
					<td>€&nbsp;
						<init:DoubleTextBox runat="server" ID="txtQuotaContributoAttuale" ReadOnly="True" Columns="10" />
					</td>
				</tr>
				<% } //if (MostraRigaContributoAttuale)%>
				<tr>
					<td colspan="5" style="text-align: right; font-weight: bold">
						Totale contributo</td>
					<td>€&nbsp;
						<init:DoubleTextBox runat="server" ID="txtQuotaContributoTotale" ReadOnly="True" Columns="10" />
					</td>
				</tr>
				<tr>
					<td colspan="5">&nbsp;
					</td>
					<td>
						<div class="Bottoni">
							<init:SigeproButton runat="server" id="cmdCopiaOneri" Text="Riporta il totale nella gestione oneri dell'istanza" IdRisorsa="COPIAONERI" OnClick="cmdRiportaValore_Click" />
						</div>
					</td>
				</tr>
			</table>
			<fieldset>
				<!--<div>-->
				<!--</div>-->
				<div class="Bottoni">
					<init:SigeproButton runat="server" id="cmdSalvaContributo" Text="Salva contributo" IdRisorsa="SALVACONTRIBUTO" OnClick="cmdSalvaContributo_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click" />
				</div>
			</fieldset>
			<% } //if (MostraTabellaContributo) %>
		</asp:View>
	</asp:MultiView>
</asp:Content>
