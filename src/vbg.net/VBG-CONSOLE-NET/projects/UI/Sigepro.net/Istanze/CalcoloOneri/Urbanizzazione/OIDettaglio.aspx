<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" Inherits="Istanze_CalcoloOneri_Urbanizzazione_OIDettaglio" Title="Calcolo delle superfici utili" Codebehind="OIDettaglio.aspx.cs" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>



<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">

	<asp:ScriptManager ID="ScriptManager1" runat="server">
	</asp:ScriptManager>
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
		<asp:View runat="server" ID="lista">
			<fieldset>
				<legend>Superfici utili</legend>
				<div>
					<asp:GridView runat="server" ID="gvTestata" DataKeyNames="Id" AutoGenerateColumns="False" DataSourceID="OICalcoloDettaglioTDataSource" OnRowCommand="gvTestata_RowCommand"
						OnSelectedIndexChanged="gvTestata_SelectedIndexChanged" OnRowDataBound="gvTestata_RowDataBound">
						<AlternatingRowStyle CssClass="RigaAlternata" />
						<RowStyle CssClass="Riga" />
						<HeaderStyle CssClass="IntestazioneTabella" />
						<EmptyDataRowStyle CssClass="NessunRecordTrovato" />
						<SelectedRowStyle CssClass="RigaSelezionata" />
						<Columns>
							<asp:BoundField DataField="Id" HeaderText="N.ro ordine" SortExpression="Id" ReadOnly="true" />
							<asp:TemplateField HeaderText="Destinazione del corpo da cubare">
								<ItemTemplate>
									<asp:Label ID="lblBaseDestinazione" runat="server" /></ItemTemplate>
							</asp:TemplateField>
							<asp:TemplateField HeaderText="Destinazione del corpo da cubare">
								<ItemTemplate>
									<asp:Label ID="lblDestinazione" runat="server" /></ItemTemplate>
							</asp:TemplateField>
							<asp:BoundField DataField="Descrizione" HeaderText="Descrizione" ReadOnly="True" />
							<asp:TemplateField HeaderText="Cubatura del fabbricato" ItemStyle-HorizontalAlign="Right">
								<ItemTemplate>
									<asp:Label ID="Label13" Text='<%# DataBinder.Eval( Container.DataItem , "Totale" , "{0:N2}") %>' runat="server" />
								</ItemTemplate>
							</asp:TemplateField>
							<asp:TemplateField>
								<ItemTemplate>
								    <asp:ImageButton ID="ibSelect" runat="server" CommandName="Select" ImageUrl="~/images/detail.gif" AlternateText="Visualizza il dettaglio delle superfici per questa superficie utile" />
									<asp:ImageButton ID="lbEdit" runat="server" CommandName="EditTestata" CommandArgument='<%# Bind("Id") %>' ImageUrl="~/images/edit.gif" AlternateText="Modifica la superficie utile" />
								</ItemTemplate>
							</asp:TemplateField>
						</Columns>
						<EmptyDataTemplate>
							<asp:Label ID="Label6" runat="server"></asp:Label>
						</EmptyDataTemplate>
					</asp:GridView>
					<div class="Bottoni">
						<init:SigeproButton runat="server" ID="cmdNuovo"  Text="Nuovo" IdRisorsa="NUOVO" OnClick="cmdNuovo_Click" />
						<init:SigeproButton runat="server" ID="cmdProcedi"  Text="Procedi" IdRisorsa="PROCEDI" OnClick="cmdProcedi_Click" />
						<init:SigeproButton runat="server" ID="cmdIndietro"  Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdIndietro_Click" />
					</div>
					<asp:ObjectDataSource ID="OICalcoloDettaglioTDataSource" runat="server" SelectMethod="Find" TypeName="Init.SIGePro.Manager.OICalcoloDettaglioTMgr">
						<SelectParameters>
							<asp:QueryStringParameter Name="token" QueryStringField="Token" Type="String" />
							<asp:QueryStringParameter Name="idCalcolo" QueryStringField="IdCalcolo" Type="Int32" />
						</SelectParameters>
					</asp:ObjectDataSource>
				</div>
			</fieldset>
			<br />
			<br />
			<asp:Panel runat="server" ID="pnlDettaglioSuperfici">
				<fieldset>
					<legend>Dettaglio superfici</legend>
					<div>
						<asp:GridView runat="server" ID="gvDettaglio" DataKeyNames="Id" AutoGenerateColumns="False" DataSourceID="OICalcoloDettaglioRDataSource" OnRowCommand="gvDettaglio_RowCommand">
							<AlternatingRowStyle CssClass="RigaAlternata" />
							<RowStyle CssClass="Riga" />
							<HeaderStyle CssClass="IntestazioneTabella" />
							<EmptyDataRowStyle CssClass="NessunRecordTrovato" />
							<SelectedRowStyle CssClass="RigaSelezionata" />
							<Columns>
								<asp:BoundField DataField="Id" HeaderText="Codice" ReadOnly="True" />
								<asp:BoundField DataField="Qta" HeaderText="N.ro" ReadOnly="True" ItemStyle-HorizontalAlign="Right" />
								<asp:BoundField DataField="Lung" HeaderText="Lungh." ReadOnly="True" ItemStyle-HorizontalAlign="Right" DataFormatString="{0:N2}" HtmlEncode="false" />
								<asp:BoundField DataField="Larg" HeaderText="Largh." ReadOnly="True" ItemStyle-HorizontalAlign="Right" DataFormatString="{0:N2}" HtmlEncode="false" />
								<asp:BoundField DataField="Alt" HeaderText="Altez." ReadOnly="True" ItemStyle-HorizontalAlign="Right" DataFormatString="{0:N2}" HtmlEncode="false" />
								<asp:BoundField DataField="Totale" HeaderText="Totale" ReadOnly="True" ItemStyle-HorizontalAlign="Right" DataFormatString="{0:N2}" HtmlEncode="false" />
								<asp:TemplateField>
									<ItemTemplate>
										<asp:ImageButton ID="lbEdit" runat="server" CommandName="EditDettaglio" CommandArgument='<%# Bind("Id") %>' ImageUrl="~/images/edit.gif" AlternateText="Modifica il dettaglio superficie" />
									</ItemTemplate>
								</asp:TemplateField>
							</Columns>
							<EmptyDataTemplate>
								<asp:Label ID="Label6" runat="server"></asp:Label>
							</EmptyDataTemplate>
						</asp:GridView>
						<asp:ObjectDataSource ID="OICalcoloDettaglioRDataSource" runat="server" SelectMethod="Find" TypeName="Init.SIGePro.Manager.OICalcoloDettaglioRMgr" OnSelecting="CCICalcoloDettaglioRDataSOurce_Selecting">
							<SelectParameters>
								<asp:QueryStringParameter Name="token" QueryStringField="Token" Type="String" />
								<asp:Parameter Name="idTestata" Type="Int32" />
							</SelectParameters>
						</asp:ObjectDataSource>
						<br />
						<asp:ImageButton runat="server" ID="cmdNuovoDettaglio" ImageUrl="~/../Common/images/Nuovo.gif" AlternateText="Nuovo dettaglio" OnClick="cmdNuovoDettaglio_Click" />
					</div>
				</fieldset>
			</asp:Panel>
		</asp:View>
		<asp:View runat="server" ID="editTestata">
			<fieldset>
				<asp:UpdatePanel ID="UpdatePanel1" runat="server">
					<ContentTemplate>
						<div>
							<asp:Label ID="Label1" runat="server" Text="Codice" AssociatedControlID="lblId" />
							<asp:Label ID="lblId" runat="server" Text="Label" />
						</div>
						<div>
							<asp:Label ID="Label3" runat="server" Text="Tipologia superficie" AssociatedControlID="ddlBaseDestinazione"></asp:Label>
							<asp:DropDownList ID="ddlBaseDestinazione" runat="server" AutoPostBack="True" DataTextField="Destinazione" DataValueField="Id" OnSelectedIndexChanged="ddlBaseDestinazione_SelectedIndexChanged" />
						</div>
						<div>
							<asp:Label ID="Label4" runat="server" Text="*Dettaglio superficie" AssociatedControlID="ddlDestinazione" />
							<asp:DropDownList ID="ddlDestinazione" runat="server" DataTextField="Destinazione" DataValueField="Id" />
						</div>
						<div>
							<asp:Label ID="Label5" runat="server" Text="Descrizione" AssociatedControlID="txtDescrizione" />
							<asp:TextBox ID="txtDescrizione" runat="server" MaxLength="200" Columns="100"></asp:TextBox>
						</div>
						
						<asp:Panel runat="server" ID="pnlEditValoreTestata">
							<asp:Label runat="server" Text="Valore" AssociatedControlID="txtValoreTestata"></asp:Label>
							<init:DoubleTextBox runat="server" ID="txtValoreTestata"></init:DoubleTextBox>
						</asp:Panel>
					</ContentTemplate>
				</asp:UpdatePanel>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdSalva" Text="Salva" IdRisorsa="SALVA" OnClick="cmdSalva_Click" />
					<init:SigeproButton runat="server" ID="cmdElimina" Text="Elimina" IdRisorsa="ELIMINA" OnClick="cmdElimina_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
				</div>
			</fieldset>
		</asp:View>
		<asp:View runat="server" ID="editDettaglio">
			<fieldset>
				<div>
					<asp:Label ID="Label2" runat="server" Text="Codice" AssociatedControlID="lblIdDettaglio" />
					<asp:Label ID="lblIdDettaglio" runat="server" Text="Label" />
				</div>
				<div>
					<asp:Label ID="Label8" runat="server" Text="*Numero" AssociatedControlID="txtNumero" />
					<init:IntTextBox ID="txtNumero" runat="server" calcoloTotale="numero" Columns="5" />
				</div>
				<div>
					<asp:Label ID="Label9" runat="server" Text="*Lunghezza" AssociatedControlID="txtLunghezza" />
					<init:DoubleTextBox ID="txtLunghezza" runat="server" calcoloTotale="lunghezza" Columns="5" />
				</div>
				<div>
					<asp:Label ID="Label10" runat="server" Text="*Larghezza" AssociatedControlID="txtLunghezza" />
					<init:DoubleTextBox ID="txtLarghezza" runat="server" calcoloTotale="larghezza" Columns="5" />
				</div>

				<asp:Panel runat="server" ID="pnlAltezza">
					<asp:Label ID="Label7" runat="server" Text="Altezza" AssociatedControlID="txtAltezza" />
					<init:DoubleTextBox ID="txtAltezza" runat="server" calcoloTotale="altezza" Columns="5" />
				</asp:Panel>

				<div>
					<asp:Label ID="Label11" runat="server" Text="Totale" AssociatedControlID="txtTotale" />
					<init:DoubleTextBox ID="txtTotale" runat="server" calcoloTotale="totale" Columns="5" ReadOnly="True" />
				</div>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdSalvaDettaglio" Text="Salva" IdRisorsa="SALVA" OnClick="cmdSalvaDettaglio_Click" />
					<init:SigeproButton runat="server" ID="cmdEliminaDettaglio" Text="Elimina" IdRisorsa="ELIMINA" OnClick="cmdEliminaDettaglio_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click" />
				</div>
			</fieldset>
		</asp:View>
	</asp:MultiView>
	
	<script type="text/javascript">

		var m_numero = null;
		var m_lunghezza = null;
		var m_larghezza = null;
		var m_altezza = null;
		var m_totale = null;
		
		var els = document.getElementsByTagName("input");
		
		for( var i = 0 ; i< els.length ; i++ )
		{
			if ( !els[i].calcoloTotale ) continue;
			
			if (els[i].calcoloTotale == "numero")
				m_numero = els[i];
			
			if (els[i].calcoloTotale == "larghezza")
				m_larghezza = els[i];
				
			if (els[i].calcoloTotale == "lunghezza")
				m_lunghezza = els[i];
				
			if (els[i].calcoloTotale == "altezza")
				m_altezza = els[i];
				
			if (els[i].calcoloTotale == "totale")
				m_totale = els[i];
				
			if ( els[i].onblur )
			{
				els[i].onblurOld = els[i].onblur;
			}
			
			els[i].onblur = function()
			{
				if (this.onblurOld)
					this.onblurOld( this );
				
				RicalcolaTotali();
			}
		}
		

		function RicalcolaTotali()
		{
			var numero = ConvertiFloat( m_numero.value );
			var lunghezza = ConvertiFloat( m_lunghezza.value );
			var larghezza = ConvertiFloat( m_larghezza.value );
			
			var altezza = m_altezza ? ConvertiFloat( m_altezza.value ) : 1;
		
			m_totale.value = (numero * lunghezza * larghezza * altezza).toString().replace(".",",");
		}	
		
		function ConvertiFloat( val )
		{
			if (val == "") return 0;
			
			try
			{
				val = val.replace(".","");
				val = val.replace(",",".");
			
				return parseFloat(val);
			}
			catch(ex)
			{
				alert(ex);
			}
		}
	</script>

</asp:Content>

