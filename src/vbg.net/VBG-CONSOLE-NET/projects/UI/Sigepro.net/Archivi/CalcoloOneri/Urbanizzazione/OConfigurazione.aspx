<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" Inherits="Archivi_CalcoloOneri_Urbanizzazione_OConfigurazione"
	Title="Configurazione oneri" Codebehind="OConfigurazione.aspx.cs" %>

<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>


<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
		<asp:View runat="server" ID="dettaglioView">
			<fieldset>
				<init:LabeledDropDownList ID="ddlTipiAreeCodiceZto" HelpControl="hdDdlTipiAreeCodiceZto" Descrizione="Tipo area ZTO" runat="server" Item-DataValueField="CODICETIPOAREA" Item-DataTextField="TIPOAREA" />
				<init:HelpDiv runat="server" ID="hdDdlTipiAreeCodiceZto">
					Codice del tipo area che rappresenta le "Zone territoriali omogenee". Viene impostata se è determinante per il calcolo degli oneri di urbanizzazione.
				</init:HelpDiv>
				
				<init:LabeledDropDownList ID="ddlTipiAreeCodicePrg" HelpControl="hdDdlTipiAreeCodicePrg" Descrizione="Tipo area PRG" runat="server" Item-DataValueField="CODICETIPOAREA" Item-DataTextField="TIPOAREA" />
				<init:HelpDiv runat="server" ID="hdDdlTipiAreeCodicePrg">
					Codice del tipo area che rappresenta le "Zone del Piano Regolatore". Viene impostata se è determinante per il calcolo degli oneri di urbanizzazione.
				</init:HelpDiv>

				<init:LabeledDropDownList ID="ddlUnitaMisuraMq" HelpControl="hdDdlUnitaMisuraMq" Descrizione="*Unità di misura in mq" runat="server" Item-DataValueField="UmId" Item-DataTextField="UmDescrbreve" />
				<init:HelpDiv runat="server" ID="hdDdlUnitaMisuraMq">
					Codice dell'unità di misura che rappresenta i metri quadri.
				</init:HelpDiv>

				<init:LabeledDropDownList ID="ddlUnitaMisuraMc" HelpControl="hdDdlUnitaMisuraMc" Descrizione="*Unità di misura in mc" runat="server" Item-DataValueField="UmId" Item-DataTextField="UmDescrbreve" />
				<init:HelpDiv runat="server" ID="hdDdlUnitaMisuraMc">
					Codice dell'unità di misura che rappresenta i metri cubi.
				</init:HelpDiv>
				<init:LabeledCheckBox ID="chkUsaDettaglioSup" Descrizione="Imputare superfici/cubature" runat="server" HelpControl="hdChkUsaDettaglioSup" />
				<init:HelpDiv runat="server" ID="hdChkUsaDettaglioSup">
					Se spuntato in fase di calcolo permette di imputare superfici/cubature in maniera dettagliata
				</init:HelpDiv>
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdSalva" Text="Salva" IdRisorsa="OK" OnClick="cmdSalva_Click" />
					<init:SigeproButton runat="server" ID="cmdAltriDati" Text="Altre configurazioni" IdRisorsa="ALTRECONFIGURAZIONI" OnClick="cmdAltriDati_Click" />
					<init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click" />
				</div>
			</fieldset>
		</asp:View>
	</asp:MultiView>
</asp:Content>
