<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" Inherits="Archivi_CalcoloOneri_CostoCostruzione_CCConfigurazione"
	Title="Configurazione del costo di costruzione" Codebehind="CCConfigurazione.aspx.cs" %>
<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>

<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" OnActiveViewChanged="multiView_ActiveViewChanged">
		<asp:View runat="server" ID="dettaglioView">
			<fieldset>
				<init:LabeledDropDownList runat="server" ID="ddlTab1FkTsId" Descrizione="*Superficie utile abitabile" HelpControl="hdTab1FkTsId1" Item-DataValueField="ID" Item-DataTextField="DESCRIZIONE" />
				<init:HelpDiv runat="server" ID="hdTab1FkTsId1">
					Tipologia di superficie che identifica le superfici abitabili nella Tabella 1 del MODELLO 1 D.M. 10/5/77.
				</init:HelpDiv>

				<init:LabeledDropDownList runat="server" ID="ddlTab2FkTsId" Descrizione="*Superficie residenziale per servizi accessori" HelpControl="hdTab2FkTsId" Item-DataValueField="ID" Item-DataTextField="DESCRIZIONE" />
				<init:HelpDiv runat="server" ID="hdTab2FkTsId">
					Tipologia di superficie che identifica le superfici per servizi accessori relative alla parte residenziale (art.2) nella Tabella 2 del MODELLO 1 D.M.
					10/5/77.
				</init:HelpDiv>

				<init:LabeledDropDownList runat="server" ID="ddlArt9SuFkTsId" HelpControl="hdArt9SuFkTsId" Descrizione="*Superficie attività turistico/commerciale" Item-DataValueField="ID" Item-DataTextField="DESCRIZIONE" />
				<init:HelpDiv runat="server" ID="hdArt9SuFkTsId">
					Tipologia di superficie che identifica le superfici per attività turistiche, commerciali e direzionali (art. 9) nel MODELLO 1 D.M. 10/5/77.
				</init:HelpDiv>
				
				<init:LabeledDropDownList runat="server" ID="ddlArt9SaFkTsId" HelpControl="hdArt9SaFkTsId" Descrizione="*Superficie accessori turistico/commerciale" Item-DataValueField="ID" Item-DataTextField="DESCRIZIONE" />
				<init:HelpDiv runat="server" ID="hdArt9SaFkTsId">
					Tipologia di superficie che identifica le superfici accessori per attività turistiche commerciali, e direzionali (art. 9) nel MODELLO 1 D.M. 10/5/77..
				</init:HelpDiv>
				
				<init:LabeledDropDownList runat="server" ID="ddlFkCoId" Descrizione="Causale onere" Item-DataValueField="CoId" Item-DataTextField="CoDescrizione" />
				
				<init:LabeledDropDownList runat="server" ID="ddlUsaDettaglioSup" HelpControl="hdUsaDettaglioSup" Descrizione="Imputazione superfici/cubature" Item-DataValueField="Key" Item-DataTextField="Value" />
				<init:HelpDiv runat="server" ID="hdUsaDettaglioSup">
					<b>"Imputazione dettagliata UI":</b> permette di specificare le superfici/cubature di ogni unità immobiliare in maniera dettagliata oppure imputando i totali<br />
					<b>"Imputazione dati sul modello":</b> permette di visualizzare una maschera di immissione simile al modello 1
				</init:HelpDiv>

				<legend>Parametri per la determinazione del contributo</legend>

				<init:LabeledDropDownList runat="server" ID="ddlFkTipiAreeCodice" HelpControl="hdFkTipiAreeCodice" Descrizione="Tipo area" Item-DataValueField="CODICETIPOAREA" Item-DataTextField="TIPOAREA" />
				<init:HelpDiv runat="server" ID="hdFkTipiAreeCodice">
					Tipo area che rappresenta le Zone Territoriali Omogenee
				</init:HelpDiv>

				<init:LabeledDropDownList runat="server" ID="ddlSettori" HelpControl="hdSettori" Descrizione="Settori" Item-DataValueField="FkSeCodicesettore" Item-DataTextField="Settore" />
				<init:HelpDiv runat="server" ID="hdSettori">
					Questa voce permette di configurare un ulteriore fattore per il calcolo del coefficiente del contributo del costo di costruzione oltre alla destinazione, intervento ed eventuale Tipo area. Per aggiungere voci utilizzare il pulsante 'Altre Configurazioni'
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
