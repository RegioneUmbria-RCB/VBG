<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" Inherits="Istanze_CalcoloOneri_Urbanizzazione_OICalcoloContribT"
	Title="Configurazione parametri per il calcolo degli oneri di urbanizzazione" Codebehind="OICalcoloContribT.aspx.cs" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>


<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>




<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
	<script type="text/javascript">
		function ModificaRiduzioni( token , idContribT , idDestinazione)
		{
			var url		= "OIModificaRiduzioni.aspx?Token=" + token + "&IdContribT=" + idContribT + "&IdDestinazione=" + idDestinazione;
			var feats	= "dialogHeight:500px;dialogWidth:850px;status:no;";
			
			var w = window.showModalDialog( url , "" , feats );
			
			return w == undefined ? false : w;
		}
		
		function ModificaNote( token , idContribT , idDestinazione , idTipoOnere)
		{
			var url		= "OIModificaNote.aspx?Token=" + token + "&IdContribT=" + idContribT + "&IdDestinazione=" + idDestinazione + "&IdTipoOnere=" + idTipoOnere;
			var feats	= "dialogHeight:190px;dialogWidth:600px;status:no;scroll:no";
			
			var w = window.showModalDialog( url , "" , feats );
			
			return w == undefined ? false : w;
		}
	</script>


	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
		<asp:View ID="individuazioneParametri" runat="server">
			<asp:ScriptManager ID="ScriptManager1" runat="server">
			</asp:ScriptManager>
			<fieldset>
				<asp:UpdatePanel ID="UpdatePanel1" runat="server">
					<ContentTemplate>
						<div>
							<asp:Label runat="server" ID="label1" AssociatedControlID="lbltipoDestinazione" Text="Tipo destinazione di base" />
							<asp:Label runat="server" ID="lbltipoDestinazione" Text="" />
						</div>
						<asp:Panel runat="server" ID="pnlZonaOmogenea">
							<asp:Label runat="server" ID="labelZonaOmogenea1" AssociatedControlID="ddlZonaOmogenea" Text="Zona omogenea" />
							<asp:DropDownList runat="server" ID="ddlZonaOmogenea" AutoPostBack="True" DataTextField="Value" DataValueField="Key" OnSelectedIndexChanged="ddlZonaOmogenea_SelectedIndexChanged" />
							<asp:Panel runat="server" ID="pnlErroreZonaOmogenea" CssClass="ErroreCampo"></asp:Panel>
						</asp:Panel>
						<asp:Panel runat="server" ID="pnlZonaPrg">
							<asp:Label runat="server" ID="labelZonaPrg1" AssociatedControlID="ddlZonaPrg" Text="Zona PRG" />
							<asp:DropDownList runat="server" ID="ddlZonaPrg" AutoPostBack="True" DataTextField="Value" DataValueField="Key" OnSelectedIndexChanged="ddlZonaPrg_SelectedIndexChanged" />
							<asp:Panel runat="server" ID="pnlErroreZonaPrg" CssClass="ErroreCampo"></asp:Panel>
						</asp:Panel>
						<asp:Panel runat="server" ID="pnlTipiIntervento">
							<asp:Label runat="server" ID="label3" AssociatedControlID="ddlTipoIntervento" Text="Tipo intervento" />
							<asp:DropDownList runat="server" ID="ddlTipoIntervento" AutoPostBack="True" DataTextField="Value" DataValueField="Key" OnSelectedIndexChanged="ddlTipoIntervento_SelectedIndexChanged" />
							<asp:Panel runat="server" ID="pnlErroreTipoIntervento" CssClass="ErroreCampo"></asp:Panel>
						</asp:Panel>
						<asp:Panel runat="server" ID="pnlIndiciTerritoriali">
							<asp:Label runat="server" ID="label5" AssociatedControlID="ddlIndiciTerritoriali" Text="Indici territoriali" />
							<asp:DropDownList runat="server" ID="ddlIndiciTerritoriali" DataTextField="Value" DataValueField="Key" />
							<asp:Panel runat="server" ID="pnlErroreIndiciTerritoriali" CssClass="ErroreCampo"></asp:Panel>
						</asp:Panel>
						
						<asp:Panel runat="server" ID="pnlInterventiTabD">
							<asp:Label runat="server" ID="label2" AssociatedControlID="ddlInterventiTabD" Text="Tipo intervento" />
							<asp:DropDownList runat="server" ID="ddlInterventiTabD" DataTextField="Value" DataValueField="Key" OnSelectedIndexChanged="ddlInterventiTabD_SelectedIndexChanged" />
							<asp:Panel runat="server" ID="pnlErroreInterventiTabD" CssClass="ErroreCampo"></asp:Panel>
						</asp:Panel>
						
						
						<asp:Panel runat="server" ID="pnlClassiAdddetti">
							<asp:Label runat="server" ID="label6" AssociatedControlID="ddlAddetti" Text="Addetti" />
							<asp:DropDownList runat="server" ID="ddlAddetti" DataTextField="Value" DataValueField="Key" />
							<asp:Panel runat="server" ID="pnlErroreClassiAddetti" CssClass="ErroreCampo"></asp:Panel>
						</asp:Panel>
					</ContentTemplate>
				</asp:UpdatePanel>
				<div class="Bottoni">
										<init:SigeproButton runat="server" ID="SigeproButton1"  Text="Procedi" IdRisorsa="PROCEDI" OnClick="cmdProcedi_Click" />
						<init:SigeproButton runat="server" ID="cmdIndietro"  Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click" />
				</div>
			</fieldset>
		</asp:View>
		<asp:View runat="server" ID="calcoloContributo">
			<fieldset>
				<div>
					<asp:Label runat="server" ID="label7" AssociatedControlID="lblEdtTipoDestinazione" Text="Tipo destinazione di base" />
					<asp:Label runat="server" ID="lblEdtTipoDestinazione" Text="" />
				</div>
				<asp:Panel runat="server" ID="pnlEdtZonaOmogenea">
					<asp:Label runat="server" ID="labelZonaOmogenea2" AssociatedControlID="lblZonaOmogenea" Text="Zona omogenea" />
					<asp:Label runat="server" ID="lblZonaOmogenea" Text="" />
				</asp:Panel>
				<asp:Panel runat="server" ID="pnlEdtZonaPrg">
					<asp:Label runat="server" ID="labelZonaPrg2" AssociatedControlID="lblZonaPrg" Text="Zona PRG" />
					<asp:Label runat="server" ID="lblZonaPrg" Text="" />
				</asp:Panel>
				<asp:Panel runat="server" ID="pnlEdtTipoIntervento">
					<asp:Label runat="server" ID="label10" AssociatedControlID="lblTipoIntervento" Text="Tipo intervento" />
					<asp:Label runat="server" ID="lblTipoIntervento" Text="" />
				</asp:Panel>
				<asp:Panel runat="server" ID="pnlEdtIndiciTerritoriali">
					<asp:Label runat="server" ID="label11" AssociatedControlID="lblIndiciTerritoriali" Text="Indici territoriali" />
					<asp:Label runat="server" ID="lblIndiciTerritoriali" Text="" />
				</asp:Panel>
				
				<asp:Panel runat="server" ID="pnlEdtInterventiTabd">
					<asp:Label runat="server" ID="label4" AssociatedControlID="lblInterventiTabd" Text="Tipo intervento" />
					<asp:Label runat="server" ID="lblInterventiTabd" Text="" />
				</asp:Panel>
				
				<asp:Panel runat="server" ID="pnlEdtClassiAddetti">
					<asp:Label runat="server" ID="label12" AssociatedControlID="lblClassiAddetti" Text="Addetti" />
					<asp:Label runat="server" ID="lblClassiAddetti" Text="" />
				</asp:Panel>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdModificaTestata" Text="Modifica" IdRisorsa="MODIFICA" OnClick="cmdMOdificaTestata_Click"/>
				</div>
				</fieldset>
				<div class="TabellaOneri">
					<asp:DataGrid ShowFooter="true" DataKeyField="IdDestinazione" runat="server" ID="dgDettagliCalcolo" AutoGenerateColumns="true" CellSpacing="-1" GridLines="None" OnItemDataBound="dgDettagliCalcolo_ItemDataBound" >
						<HeaderStyle CssClass="IntestazioneTabella" />
					</asp:DataGrid>
				</div>
				<fieldset>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdSalva" Text="Salva" IdRisorsa="SALVA" OnClick="cmdSalva_Click"/>
					<init:SigeproButton runat="server" ID="cmdChiudiCalcolo" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiCalcolo_Click"/>
				</div>
			</fieldset>
		</asp:View>
	</asp:MultiView>
</asp:Content>
