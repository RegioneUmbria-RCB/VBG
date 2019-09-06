<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="SilverBrowserController.aspx.cs" Inherits="Sigepro.net.Istanze.Sit.silverbrowser.SilverBrowserController" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>



    <script src='<%=this.UrlSilverBrowser %>'></script>

    <script type="text/javascript">

        <%if (Modo == "M")
        {%>
        SilverBrowser.call('showEntity', { entityId: ' |<%=Foglio%>|<%=Particella%>:catasto.Particella', 'viewerName': 'MapBrowser' }, function () { self.close(); });
        <%}
        else
        {%>
        SilverBrowser.call('showEntity', { entityId: '<%=CodiceVia%>-<%=Civico%><%=Esponente%>:stradario.Civico', 'viewerName': 'MapBrowser' }, function () { self.close(); });
        <%}%>
</script>
</head>
<body>
    <form id="form1" runat="server">
        <div>
        </div>
    </form>
</body>
</html>
