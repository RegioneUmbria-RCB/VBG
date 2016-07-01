<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="AdminLoginControl.ascx.cs" Inherits="Sigepro.net.AdminSecurity.AdminLoginControl" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>

<div style="margin:auto">
			<div class="Errori">
				Attenzione, l'accesso alla pagina è riservato agli amministratori di sistema
			</div>
			
			<fieldset>
				<init:LabeledTextBox ID="txtAdminUsername" Descrizione="Nome utente" runat="server" />
				<init:LabeledTextBox ID="txtAdminPassword" Descrizione="Password" runat="server" Item-TextMode="Password" />
				
				<div class="Bottoni">
					<init:SigeproButton runat="server" ID="cmdOk" Text="Ok" IdRisorsa="OK" OnClick="cmdOk_Click" />
					<init:SigeproButton runat="server" ID="cmdAnnulla" Text="Annulla" IdRisorsa="ANNULLA" OnClick="cmdAnnulla_Click" />
					
				</div>
			</fieldset>

</div>