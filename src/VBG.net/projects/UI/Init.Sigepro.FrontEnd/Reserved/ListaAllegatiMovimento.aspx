<%@ Page Language="C#" AutoEventWireup="true" EnableViewState="false" CodeBehind="ListaAllegatiMovimento.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.ListaAllegatiMovimento" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title></title>
</head>
<body>
    <form id="form1" runat="server">
    <div>
    <asp:Panel runat="server" ID="pnlContenuti">

		<asp:Repeater runat="server" ID="rptListaAllegati">
			<HeaderTemplate>
				<ul>
			</HeaderTemplate>
			
			<ItemTemplate>
				<li><b><asp:HyperLink runat="server" ID="hlDownload" target="_blank" Text='<%# Eval("DESCRIZIONE") %>' NavigateUrl='<%# GeneraUrlDownload(DataBinder.Eval(Container,"DataItem")) %>' /></b><br />
						Nome file: <asp:Literal runat="server" ID="ltrNomeFile" Text='<%# Eval("Oggetto.NOMEFILE") %>' />
				</li>
			</ItemTemplate>
			
			<FooterTemplate>
				</ul>
			</FooterTemplate>
		</asp:Repeater>
		
    </asp:Panel>
    </div>
    </form>
</body>
</html>
