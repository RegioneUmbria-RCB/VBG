<%@ Page Language="c#" Codebehind="Login.aspx.cs" AutoEventWireup="True" Inherits="Init.Sigepro.FrontEnd.Login" %>
<%@ Register TagPrefix="cc1" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.Common" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Accesso all'area riservata</title>
	<link href="css/Stili.css" type="text/css" rel="stylesheet" />
</head>
<body>
	<form id="Form1" method="post" runat="server" defaultbutton="cmdEnter">
		<div>
			<div id="intestazionePagina">
				<div id="hrTop">
				</div>
				<div id="logo">
				<cc1:ImmagineFrontoffice runat="server" ID="imgLogo" IdRisorsa="logo_suap" />
				</div>
				<div id="nomeComune">
					<img src='<%=ResolveClientUrl("~/Images/DotMenuSx.gif") %>' alt="." id="preNomeComune" />
					<asp:Label runat="server" ID="lblNomeComune">Nome Comune</asp:Label>
					<img src='<%=ResolveClientUrl("~/Images/DotMenuSx.gif") %>' alt="." id="postNomeComune" />
					<cc1:ImmagineFrontofficeButton runat="server" ID="cmdHome" ImageUrl="~/Images/img_home.gif" IdRisorsa="but_home" OnClick="cmdHome_Click" />
				</div>
				<div class="clear"></div>
				<div id="loginAreaRiservata">
					<div id="logoAreaRiservata">
					</div>
				</div>
				<div id="spacer">
				</div>
			</div>
			<div id="contenitorePrincipale" >
				<div id="menuNavigazione">
					<div id="#contenutiMenu">
						<%--<ul>
							<li>&nbsp;</li>
						</ul>--%>
					</div>
				</div>
				<div id="contenuti">
					<asp:Label ID="Label2" runat="server">
						Area riservata per presentare istanze on line <br />
						e che permette successivamente  di verificare lo stato dell'iter procedimentale.
					</asp:Label>
					<br />
					<br />
					<br />
					<div class="inputForm">
						<fieldset>
							<legend>Per accedere inserire codice fiscale/partita IVA e password</legend>
							<div>
								<asp:Label runat="server" ID="lblCodiceFiscale" AssociatedControlID="txtUsername">Codice Fiscale / Partita IVA:</asp:Label>
								<asp:TextBox runat="server" ID="txtUsername" Width="200px"></asp:TextBox>
							</div>
							<div>
								<asp:Label runat="server" ID="Label4" AssociatedControlID="txtPassword">Password:</asp:Label>
								<asp:TextBox runat="server" ID="txtPassword" TextMode="Password" Width="200px"></asp:TextBox>
							</div>
							<div>
								<asp:Label runat="server" ID="lblErrori" CssClass="errori"></asp:Label>
							</div>
							<div class="bottoni">
								<asp:Button ID="cmdEnter" runat="server" Text="Entra" OnClick="cmdEnter_Click"></asp:Button>
							</div>
						</fieldset>
						
					</div>
					
				</div>
			</div>
			<div class="clear"></div>
			<div id="piedePagina">
			</div>
		</div>
	</form>
</body>
</html>
