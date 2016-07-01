<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Error.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Error" %>
<%@ Register TagPrefix="cc1" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.Common" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>Si è verificato un errore inatteso</title>

	<link href="~/css/blank.css" type="text/css" rel="stylesheet" id="cssIncludeLink" runat="server" />
	<link href="~/css/Stili.css" type="text/css" rel="stylesheet" id="cssLink" runat="server" />

	
	<script type="text/javascript" src="<%= ResolveClientUrl("~/Scripts/less.js")%>"></script>

    <script type="text/javascript">
    	function goToLastPage() {
    		document.location.replace( '<%= Request.QueryString["lastUrl"] %>' );
    	}
    </script>
</head>
<body>
    <form id="form1" runat="server">
	<div>
		<div id="intestazionePagina">
			<div id="hrTop">
			</div>
			<div id="logo">
				<cc1:ImmagineFrontoffice runat="server" ID="imgLogo" IdRisorsa="logo_suap" />
			</div>

			<div id="nomeComuneV2">
				<h1>Area riservata</h1>
				<h2><asp:Label runat="server" ID="lblNomeComune2">Nome Comune</asp:Label></h2>
			</div>

			<div id="datiUtenteV2">
				Utente connesso:
				<asp:Label runat="server" ID="lblNomeUtente2">Nome utente</asp:Label>
			</div>

			<div id="nomeComune">
				<img src='<%=ResolveClientUrl("~/Images/DotMenuSx.gif") %>' alt="." id="preNomeComune" />
				<asp:Label runat="server" ID="lblNomeComune">Nome Comune</asp:Label>
				<img src='<%=ResolveClientUrl("~/Images/DotMenuSx.gif") %>' alt="." id="postNomeComune" />
			</div>
			<div class="clear">
			</div>
			<div id="loginAreaRiservata">
				<div id="logoAreaRiservata">
				</div>
			</div>
			<div id="spacer">
			</div>
		</div>
		<div id="contenitorePrincipale">
			<div id="menuNavigazione">
				<div id="#contenutiMenu">
					<%--<ul>
							<li>&nbsp;</li>
						</ul>--%>
				</div>
			</div>
			<div id="contenuti">
				<br />
				<br />
				<br />
				<div class="inputForm">
					<fieldset>
						<legend>Si è verificato un errore inatteso</legend>
						Si è verificato un errore inatteso durante l'esecuzione dell'ultimo step. Si prega di contattare il servizio di assistenza comunicando i seguenti dettagli:<br />
						Data errore: <%= DateTime.Now.ToString() %><br />
						Dettagli errore: <%= Request.QueryString["errMsg"] %><br />
						
						<div class="bottoni">
							<input type="submit" onclick="goToLastPage();return false;" value="Torna alla pagina precedente" />
						</div>
					</fieldset>
				</div>
			</div>
		</div>
		<div class="clear">
		</div>
		<div id="piedePagina">
		</div>
	</div>
    </form>
</body>
</html>
