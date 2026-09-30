<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="True" Inherits="Sigepro.net.Istanze.CalcoloOneri.Istanze_CalcoloOneri_Rateizzazione" CodeBehind="Rateizzazione.aspx.cs" Title="Rateizzazione oneri" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>


<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">
    <br />
	<div class="Informazioni">
		<div>
			<asp:Label CssClass="Etichetta" ID="lblTitolo1" runat="server" Text="Raggruppamento:"></asp:Label>
			<asp:Label CssClass="Valore" ID="lblRaggruppamento" runat="server"></asp:Label>
		</div>
    
		<div>
			<asp:Label CssClass="Etichetta" ID="lblImportoEtichetta" runat="server" Text="Importo:"></asp:Label>
			<asp:Label CssClass="Valore" ID="lblImporto" runat="server" Text=""></asp:Label>
			<asp:Label CssClass="Etichetta" ID="lblCausale" runat="server" Text=""></asp:Label>
		</div>
		<div >
			<asp:Label CssClass="Etichetta" ID="lblImportoIstrEtichetta" runat="server" Text="Importo istruttoria:"></asp:Label>
			<asp:Label CssClass="Valore" ID="lblImportoIstr" runat="server"></asp:Label>
		</div>
	</div>
    <br />
    <br />
    <asp:ScriptManager ID="ScriptManager1" runat="server"></asp:ScriptManager>
    	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
		<asp:View runat="server" ID="dettaglioView">
    		 
                <asp:UpdatePanel ID="UpdatePanel1" runat="server">
                    <ContentTemplate>
                    <fieldset>
                        <init:LabeledDropDownList ID="ddlTipiRateizzazione" runat="server" Descrizione="Tipi rateizzazioni" Item-DataValueField="tiporateizzazione" Item-DataTextField="descrizione" OnValueChanged="ddlTipiRateizzazione_ValueChanged" Item-AutoPostBack=true/>
                        <init:LabeledIntTextBox ID="txNumeroRate" runat="server" Descrizione="Numero rate" Item-Columns="5" OnValueChanged="txNumeroRate_ValueChanged"  Item-AutoPostBack=true Item-ReadOnly=true />
                        <init:LabeledTextBox ID="txRipartizioneRate" runat="server" Descrizione="Ripartizione rate (%)" Item-Columns="20" HelpControl="divRipartizioneRate" Item-ReadOnly=true/>
                        <init:LabeledDateTextBox ID="txDataInizio" runat="server" Descrizione="*Data inizio rateizzazione" Item-Columns="10"/>
                        <init:LabeledTextBox ID="txGiorniScadenze" runat="server" Descrizione="Frequenza rate (gg)" Item-Columns="20" HelpControl="divGiorniScadenze" Item-ReadOnly=true/>
                        <init:LabeledDropDownList ID="ddlComportamentoScadenza" runat="server" Descrizione="Scadenza rate" HelpControl="divComportamentoScadenza" Item-DataValueField="Id" Item-DataTextField="Descrizione" Item-Enabled=false />
                        <init:LabeledTextBox ID="txPercInteressi" runat="server" Descrizione="Interessi (%)"  HelpControl="divPercInteressi" Item-Columns="5" Item-ReadOnly=true/>
                        <init:LabeledCheckBox ID="chkInteressiLegali" runat="server" HelpControl="divInteressiLegali" Descrizione="Interessi legali" Item-Enabled="false"/>
                        <asp:DropDownList ID="ddlInteressiLegali" runat="server" Enabled="false">
                            <asp:ListItem Value="0">senza anatocismo</asp:ListItem>
                        </asp:DropDownList>
                        <init:LabeledDateTextBox ID="TxDataInizioIntLegali" runat="server" Descrizione="Data di inizio" Item-Columns="10"/>
                   </fieldset>
                   </ContentTemplate>
                </asp:UpdatePanel>
                <fieldset>
                <div class="Bottoni">
	    			<init:SigeproButton runat="server" ID="cmdRateizza1" Text="Rateizza" IdRisorsa="RATEIZZA" OnClick="cmdRateizza_Click"/>
	    			<init:SigeproButton runat="server" ID="cmdDerateizza1" Text="Deateizza" IdRisorsa="DERATEIZZA" OnClick="cmdDerateizza_Click"/>
                    <init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
				</div>
				</fieldset>
			
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
	<init:HelpDiv ID="divInteressiLegali" runat="server">
    Se il flag interessi legali viene selezionato allora gli interessi vengono calcolati sulla base della tabella interessi legali, e l’utente deve obbligatoriamente imputare le date del periodo sul quale calcolare gli interessi<br/>
    La data di inizio rappresenta la data da cui deve partire il calcolo degli interessi legali<br/>
	<b>ATTENZIONE: </b> la data di inizio rateizzazione rappresenta la data finale
	</init:HelpDiv>
</asp:Content>