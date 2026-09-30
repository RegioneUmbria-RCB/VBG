<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="fv.aspx.cs" Inherits="Init.Sigepro.FrontEnd.fv" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
	<style>
	html, body
		{
			height: 100%;
			padding: 0px; margin: 0px;
		}

body {
  min-height: 100%;
}
	</style>
</head>
<body>
    <form id="form1" runat="server">
		<asp:TextBox runat="server" TextMode="MultiLine" ID="txtTesto" style="width:100%; min-height:600px" />
		<asp:Button runat="server" ID="cmdOk" Text="copia" OnClick="cmdOk_click" />
		<asp:Button runat="server" ID="cmdRicarica" Text="ricarica" OnClick="cmdRicarica_click" />
    </form>
</body>
</html>
