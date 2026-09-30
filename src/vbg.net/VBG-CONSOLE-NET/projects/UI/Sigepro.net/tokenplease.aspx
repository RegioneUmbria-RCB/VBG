<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="tokenplease.aspx.cs" Inherits="Sigepro.net.tokenplease" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
</head>
<body>
    <form id="form1" runat="server">
        <div>
            <label>Alias:</label>
            <asp:TextBox runat="server" ID="txtAlias"></asp:TextBox>
        </div>

        <asp:Button runat="server" ID="cmdStacca" OnClick="cmdStacca_Click" Text="Stacca" />

        <div>
            <asp:Label runat="server" ID="lblOutput"></asp:Label>
        </div>
    </form>
</body>
</html>
