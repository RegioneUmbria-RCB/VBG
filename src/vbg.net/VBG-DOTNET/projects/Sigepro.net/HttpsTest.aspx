<%@ Page %>


<script language="c#" runat="server">
public void Page_Load(object sender, EventArgs e)
{
  Request.ServerVariables["HTTPS"] = "on";
}
</script>

<%=Request.Url.AbsoluteUri %>