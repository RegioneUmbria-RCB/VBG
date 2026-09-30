<%@ Control Language="C#" AutoEventWireup="true" Inherits="SIGePro.Net.CustomControls.CustomControls_ControlloIntestazione" Codebehind="ControlloIntestazione.ascx.cs" %>

<div class="titolo">
	<asp:Label runat="server" ID="lblTitolo"></asp:Label>
</div>

<ul class="tipoScheda">
	<li><span class='<%=ClasseCssRicerca%>'>Ricerca</span></li>
	<li class="inner"><span class='<%=ClasseCssRisultato%>'>Risultato</span></li>
	<li class="inner"><span class='<%=ClasseCssScheda%>'>Scheda</span></li>
</ul>
